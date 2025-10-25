package RUT.smart_home_contract.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SensorRequest(
        @NotBlank(message = "Название не может быть пустым")
        String name,
        @NotNull(message = "Тип не может быть пустым")
        SensorType type,
        @NotBlank(message = "Место не может быть пустым")
        String location
) {}