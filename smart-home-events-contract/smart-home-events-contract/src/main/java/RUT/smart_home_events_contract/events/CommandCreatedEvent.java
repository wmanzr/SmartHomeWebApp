package RUT.smart_home_events_contract.events;

import java.io.Serializable;

public record CommandCreatedEvent(
        Long commandId,
        Long deviceId,
        String action,
        String value,
        String status,
        String message
) implements Serializable {}