package RUT.smart_home_events_contract.events;

import java.io.Serializable;

public record SensorUpdatedEvent(
        Long sensorId,
        String newName,
        String newLocation
) implements Serializable {}