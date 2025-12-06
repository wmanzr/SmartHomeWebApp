package RUT.smart_home_events_contract.events;

import java.io.Serializable;
public record CallCommandEventFromSensorReading(
    Long deviceId,
    String sensorType,
    String value,
    Boolean shouldExecute,
    String commandAction,
    Long timestamp
) implements Serializable {}