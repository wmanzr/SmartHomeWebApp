package RUT.smart_home.service.impl;

import RUT.smart_home.config.RabbitMQConfig;
import RUT.smart_home.entity.Command;
import RUT.smart_home.entity.Device;
import RUT.smart_home.repository.CommandRepository;
import RUT.smart_home.service.CommandService;
import RUT.smart_home.service.DeviceService;
import RUT.smart_home_contract.api.dto.*;
import RUT.smart_home_contract.api.exception.ResourceNotFoundException;
import RUT.smart_home_events_contract.events.CommandCreatedEvent;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Stream;

@Service
public class CommandServiceImpl implements CommandService {
    private final CommandRepository commandRepository;
    private final ModelMapper modelMapper;
    private final DeviceService deviceService;
    private final RabbitTemplate rabbitTemplate;

    public CommandServiceImpl(
            CommandRepository commandRepository,
            ModelMapper modelMapper,
            DeviceService deviceService,
            RabbitTemplate rabbitTemplate
    ) {
        this.commandRepository = commandRepository;
        this.modelMapper = modelMapper;
        this.deviceService = deviceService;
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public PagedResponse<CommandResponse> getAll(Long deviceId, int page, int size) {
        Stream<Command> commandsStream = commandRepository.findAll().stream()
                .sorted((r1, r2) -> r1.getId().compareTo(r2.getId()));

        if (deviceId != null) {
            commandsStream = commandsStream.filter(command ->
                    command.getDevice() != null && command.getDevice().getId().equals(deviceId));
        }

        List<Command> filteredCommands = commandsStream.toList();

        List<CommandResponse> allCommands = filteredCommands.stream()
                .map(command -> modelMapper.map(command, CommandResponse.class))
                .toList();

        int totalElements = allCommands.size();
        int totalPages = (int) Math.ceil((double) totalElements / size);
        int fromIndex = page * size;
        int toIndex = Math.min(fromIndex + size, totalElements);

        List<CommandResponse> pageContent =
                (fromIndex > toIndex) ? List.of() : allCommands.subList(fromIndex, toIndex);

        return new PagedResponse<>(pageContent, page, size, totalElements, totalPages, page >= totalPages - 1);
    }

    @Override
    public CommandResponse getById(Long id) {
        Command command = commandRepository.findById(id);
        if (command == null) {
            throw new ResourceNotFoundException("Command", id);
        }
        return modelMapper.map(command, CommandResponse.class);
    }

    @Override
    @Transactional
    public CommandResponse create(CommandRequest request) {
        deviceService.getById(request.deviceId());

        CommandAction action = request.action();
        String value = request.value() == null ? "Без значения" : request.value();

        Command cmd = new Command(
                modelMapper.map(deviceService.getById(request.deviceId()), Device.class),
                action,
                value,
                CommandStatus.PENDING,
                "Сообщение по умолчанию"
        );

        commandRepository.create(cmd);
        CommandCreatedEvent event = new CommandCreatedEvent(
                cmd.getId(),
                cmd.getDevice().getId(),
                cmd.getAction().toString(),
                cmd.getValue(),
                cmd.getStatus().toString(),
                cmd.getMessage()
        );
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, RabbitMQConfig.ROUTING_KEY_COMMAND_CREATED, event);

        return modelMapper.map(cmd, CommandResponse.class);
    }
}