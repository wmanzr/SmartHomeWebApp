package RUT.smart_home_events_contract.events;

import java.io.Serializable;

public record SensorCreatedEvent(
        Long sensorId,
        String name,
        String type,
        String location
) implements Serializable {}