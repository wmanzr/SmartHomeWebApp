package RUT.smart_home.service.impl;

import RUT.smart_home.service.CommandExecutionService;
import RUT.smart_home.service.CommandService;
import RUT.smart_home.service.DeviceService;
import RUT.smart_home_contract.api.dto.CommandAction;
import RUT.smart_home_contract.api.dto.CommandRequest;
import RUT.smart_home_contract.api.dto.CommandStatus;
import RUT.smart_home_contract.api.dto.DeviceResponse;
import RUT.smart_home_contract.api.dto.DeviceStatus;
import RUT.smart_home_contract.api.dto.DeviceType;
import RUT.smart_home_contract.api.dto.UpdateCommandStatusRequest;
import RUT.smart_home_contract.api.dto.UpdateDeviceStatusRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class CommandExecutionServiceImpl implements CommandExecutionService {
    private final CommandService commandService;
    private final DeviceService deviceService;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public CommandExecutionServiceImpl(CommandService commandService, DeviceService deviceService) {
        this.commandService = commandService;
        this.deviceService = deviceService;
    }

    @Override
    @Transactional
    public void executeCommand(Long commandId) {
        try {
            var command = commandService.getById(commandId);
            var device = deviceService.getById(command.getDevice().getId());
            DeviceType deviceType = device.getType();
            CommandAction action = command.getAction();
            String value = command.getValue();

            DeviceStatus newStatus = determineDeviceStatus(action, deviceType);
            String metadata = (action == CommandAction.SET_TEMPERATURE && value != null && !value.isEmpty()) ? value : null;

            if (isDeviceAlreadyInTargetState(device, newStatus, metadata)) {
                commandService.updateStatus(commandId, new UpdateCommandStatusRequest(CommandStatus.SUCCESS, "Устройство уже находится в требуемом состоянии"));
                return;
            }

            commandService.updateStatus(commandId, new UpdateCommandStatusRequest(CommandStatus.IN_PROGRESS, "Выполнение команды..."));

            deviceService.updateStatus(device.getId(), new UpdateDeviceStatusRequest(newStatus, metadata));

            commandService.updateStatus(commandId, new UpdateCommandStatusRequest(CommandStatus.SUCCESS, null));

            if (deviceType == DeviceType.ALARM && action == CommandAction.ACTIVATE_ALARM) {
                scheduler.schedule(() -> {
                    commandService.create(new CommandRequest(device.getId(), CommandAction.DEACTIVATE_ALARM, null));
                }, 2, TimeUnit.MINUTES);
            }
        } catch (Exception e) {
            commandService.updateStatus(commandId, new UpdateCommandStatusRequest(CommandStatus.FAILED, "Ошибка выполнения: " + e.getMessage()));
            throw e;
        }
    }

    private boolean isDeviceAlreadyInTargetState(DeviceResponse device, DeviceStatus targetStatus, String targetMetadata) {
        if (!device.getStatus().equals(targetStatus)) {
            return false;
        }

        String deviceMetadata = device.getMetadata();
        if (targetMetadata == null && deviceMetadata == null) {
            return true;
        }
        if (targetMetadata == null || deviceMetadata == null) {
            return false;
        }

        return targetMetadata.equals(deviceMetadata);
    }

    private DeviceStatus determineDeviceStatus(CommandAction action, DeviceType deviceType) {
        if (deviceType == DeviceType.BLINDS) {
            return switch (action) {
                case OPEN_BLINDS -> DeviceStatus.OPEN;
                case CLOSE_BLINDS -> DeviceStatus.CLOSED;
                default -> throw new IllegalArgumentException("Invalid action for BLINDS: " + action);
            };
        }

        if (deviceType == DeviceType.ALARM) {
            return switch (action) {
                case ACTIVATE_ALARM -> DeviceStatus.ACTIVE;
                case DEACTIVATE_ALARM -> DeviceStatus.INACTIVE;
                default -> throw new IllegalArgumentException("Invalid action for ALARM: " + action);
            };
        }

        return switch (action) {
            case TURN_ON -> DeviceStatus.ON;
            case TURN_OFF -> DeviceStatus.OFF;
            case SET_TEMPERATURE -> DeviceStatus.ON;
            case RESTART -> DeviceStatus.STANDBY;
            default -> throw new IllegalArgumentException("Unknown action: " + action);
        };
    }
}