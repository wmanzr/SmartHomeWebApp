package RUT.smart_home_contract.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record SensorReadingRequest(
        @NotNull(message = "ID сенсора обязателен")
        Long sensorId,
        @NotNull(message = "Значение обязательно")
        Double value,
        @NotBlank(message = "Единица измерения обязательна")
        String unit,
        @NotNull(message = "Время измерения обязательно")
        LocalDateTime timestamp
) {}