package RUT.smart_home_events_contract.events;

import java.io.Serializable;

public record DeviceStatusUpdatedEvent(
        Long deviceId,
        String newStatus
) implements Serializable {}