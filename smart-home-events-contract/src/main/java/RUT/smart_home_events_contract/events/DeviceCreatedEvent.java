package RUT.smart_home_events_contract.events;

import java.io.Serializable;

public record DeviceCreatedEvent(
        Long deviceId,
        String name,
        String type,
        String location,
        String metadata,
        String status
) implements Serializable {}