package RUT.smart_home_events_contract.events;

import java.io.Serializable;
import java.time.LocalDateTime;

public record SensorReadingCreatedEvent(
        Long readingId,
        Long sensorId,
        Double value,
        String unit,
        LocalDateTime timestamp
) implements Serializable {}