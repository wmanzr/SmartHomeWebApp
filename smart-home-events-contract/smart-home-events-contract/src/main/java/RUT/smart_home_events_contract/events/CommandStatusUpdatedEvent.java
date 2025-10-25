package RUT.smart_home_events_contract.events;

import java.io.Serializable;

public record CommandStatusUpdatedEvent(
        Long commandId,
        String oldStatus,
        String newStatus
) implements Serializable {}