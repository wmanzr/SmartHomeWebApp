package RUT.smart_home_events_contract.events;

import java.io.Serializable;

public record DeviceStatusUpdatedEvent(
        Long deviceId,
        String deviceName,
        String deviceType,
        String newStatus,
        String metadata
) implements Serializable {}