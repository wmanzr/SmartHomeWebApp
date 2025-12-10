package RUT.smart_home.service.impl;

import RUT.smart_home.config.RabbitMQConfig;
import RUT.smart_home.entity.Device;
import RUT.smart_home.repository.DeviceRepository;
import RUT.smart_home.service.DeviceService;
import RUT.smart_home_contract.api.dto.*;
import RUT.smart_home_contract.api.exception.ResourceAlreadyExistsException;
import RUT.smart_home_contract.api.exception.ResourceNotFoundException;
import RUT.smart_home_events_contract.events.DeviceCreatedEvent;
import RUT.smart_home_events_contract.events.DeviceDeletedEvent;
import RUT.smart_home_events_contract.events.DeviceStatusUpdatedEvent;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DeviceServiceImpl implements DeviceService {

    private final DeviceRepository deviceRepository;
    private final ModelMapper modelMapper;
    private final RabbitTemplate rabbitTemplate;

    public DeviceServiceImpl(DeviceRepository deviceRepository, ModelMapper modelMapper, RabbitTemplate rabbitTemplate) {
        this.deviceRepository = deviceRepository;
        this.modelMapper = modelMapper;
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public List<DeviceResponse> getAll() {
        return deviceRepository.findAll().stream()
                .map(device -> modelMapper.map(device, DeviceResponse.class))
                .toList();
    }

    @Override
    public DeviceResponse getById(Long id) {
        Device device = deviceRepository.findById(id);
        if (device == null) {
            throw new ResourceNotFoundException("Device", id);
        }
        return modelMapper.map(device, DeviceResponse.class);
    }

    @Override
    @Transactional
    public DeviceResponse create(DeviceRequest request) {
        boolean exists = deviceRepository.findAll().stream()
                .anyMatch(d -> d.getName().equalsIgnoreCase(request.name()));
        if (exists) {
            throw new ResourceAlreadyExistsException("Device", request.name());
        }

        DeviceType deviceType = request.type();

        Device device = new Device(
                request.name(),
                deviceType,
                request.location(),
                request.metadata(),
                DeviceStatus.ON
        );

        deviceRepository.create(device);
        DeviceCreatedEvent event = new DeviceCreatedEvent(
                device.getId(),
                device.getName(),
                device.getType().toString(),
                device.getLocation(),
                device.getMetadata(),
                device.getStatus().toString()
        );
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, RabbitMQConfig.ROUTING_KEY_DEVICE_CREATED, event);

        return modelMapper.map(device, DeviceResponse.class);
    }

    @Override
    @Transactional
    public DeviceResponse updateStatus(Long id, UpdateDeviceStatusRequest request) {
        Device device = deviceRepository.findById(id);
        device.setStatus(request.status());
        Device updated = deviceRepository.update(device);
        DeviceStatusUpdatedEvent event = new DeviceStatusUpdatedEvent(
                updated.getId(),
                updated.getStatus().toString()
        );
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, RabbitMQConfig.ROUTING_KEY_DEVICE_STATUS_UPDATED, event);

        return modelMapper.map(updated, DeviceResponse.class);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Device device = deviceRepository.findById(id);
        deviceRepository.deleteById(id);
        DeviceDeletedEvent event = new DeviceDeletedEvent(
                device.getId(),
                device.getName()
        );
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, RabbitMQConfig.ROUTING_KEY_DEVICE_DELETED, event);
    }
}