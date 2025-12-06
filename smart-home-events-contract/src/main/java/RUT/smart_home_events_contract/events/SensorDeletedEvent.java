package RUT.smart_home_events_contract.events;

import java.io.Serializable;

public record SensorDeletedEvent(
        Long sensorId,
        String name
) implements Serializable {}