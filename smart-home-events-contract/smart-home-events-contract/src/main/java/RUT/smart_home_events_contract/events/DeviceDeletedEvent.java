package RUT.smart_home_events_contract.events;

import java.io.Serializable;

public record DeviceDeletedEvent(
        Long deviceId,
        String name
) implements Serializable {}