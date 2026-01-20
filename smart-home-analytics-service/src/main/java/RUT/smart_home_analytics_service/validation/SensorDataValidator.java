package RUT.smart_home_analytics_service.validation;

import RUT.smart_home_analytics_service.SensorDataRequest;
import RUT.smart_home_contract.api.dto.SensorType;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;

public class SensorDataValidator {

    // Разумные диапазоны для каждого типа сенсора
    private static final double TEMPERATURE_MIN = -50.0;
    private static final double TEMPERATURE_MAX = 60.0;
    private static final double HUMIDITY_MIN = 0.0;
    private static final double HUMIDITY_MAX = 100.0;
    private static final double LIGHT_LEVEL_MIN = 0.0;
    private static final double LIGHT_LEVEL_MAX = 100.0;
    private static final double MOTION_MIN = 0.0;
    private static final double MOTION_MAX = 1.0;

    public static void validate(SensorDataRequest request) {
        validateBasicFields(request);
        validateSensorType(request);
        validateValueBySensorType(request);
    }

    private static void validateBasicFields(SensorDataRequest request) {
        if (request.getSensorId() <= 0) {
            throw new StatusRuntimeException(
                    Status.INVALID_ARGUMENT.withDescription("Sensor ID must be positive, got: " + request.getSensorId())
            );
        }

        if (request.getReadingId() <= 0) {
            throw new StatusRuntimeException(
                    Status.INVALID_ARGUMENT.withDescription("Reading ID must be positive, got: " + request.getReadingId())
            );
        }

        if (request.getSensorType() == null || request.getSensorType().isBlank()) {
            throw new StatusRuntimeException(
                    Status.INVALID_ARGUMENT.withDescription("Sensor type cannot be null or empty")
            );
        }

        if (Double.isNaN(request.getValue()) || Double.isInfinite(request.getValue())) {
            throw new StatusRuntimeException(
                    Status.INVALID_ARGUMENT.withDescription("Sensor value cannot be NaN or Infinite, got: " + request.getValue())
            );
        }

        if (request.getTimestamp() == null || request.getTimestamp().isBlank()) {
            throw new StatusRuntimeException(
                    Status.INVALID_ARGUMENT.withDescription("Timestamp cannot be null or empty")
            );
        }
    }

    private static void validateSensorType(SensorDataRequest request) {
        try {
            SensorType.valueOf(request.getSensorType().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new StatusRuntimeException(
                    Status.INVALID_ARGUMENT.withDescription(
                            "Invalid sensor type: " + request.getSensorType() + 
                            ". Valid types: TEMPERATURE, HUMIDITY, MOTION, LIGHT_LEVEL"
                    )
            );
        }
    }

    private static void validateValueBySensorType(SensorDataRequest request) {
        SensorType sensorType;
        try {
            sensorType = SensorType.valueOf(request.getSensorType().toUpperCase());
        } catch (IllegalArgumentException e) {
            // Уже проверено в validateSensorType, но на всякий случай
            return;
        }

        double value = request.getValue();

        switch (sensorType) {
            case TEMPERATURE:
                if (value < TEMPERATURE_MIN || value > TEMPERATURE_MAX) {
                    throw new StatusRuntimeException(
                            Status.INVALID_ARGUMENT.withDescription(
                                    String.format("Temperature value out of range [%.1f, %.1f]°C, got: %.1f",
                                            TEMPERATURE_MIN, TEMPERATURE_MAX, value)
                            )
                    );
                }
                break;

            case HUMIDITY:
                if (value < HUMIDITY_MIN || value > HUMIDITY_MAX) {
                    throw new StatusRuntimeException(
                            Status.INVALID_ARGUMENT.withDescription(
                                    String.format("Humidity value out of range [%.1f, %.1f]%%, got: %.1f",
                                            HUMIDITY_MIN, HUMIDITY_MAX, value)
                            )
                    );
                }
                break;

            case LIGHT_LEVEL:
                if (value < LIGHT_LEVEL_MIN || value > LIGHT_LEVEL_MAX) {
                    throw new StatusRuntimeException(
                            Status.INVALID_ARGUMENT.withDescription(
                                    String.format("Light level value out of range [%.1f, %.1f]%%, got: %.1f",
                                            LIGHT_LEVEL_MIN, LIGHT_LEVEL_MAX, value)
                            )
                    );
                }
                break;

            case MOTION:
                if (value != MOTION_MIN && value != MOTION_MAX) {
                    throw new StatusRuntimeException(
                            Status.INVALID_ARGUMENT.withDescription(
                                    String.format("Motion value must be 0.0 or 1.0 (boolean), got: %.1f", value)
                            )
                    );
                }
                break;
        }
    }
}
