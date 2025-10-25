package RUT.smart_home_contract.api.dto;

import jakarta.validation.constraints.NotBlank;

public record UpdateSensorRequest(
        @NotBlank(message = "Название не может быть пустым")
        String name,
        @NotBlank(message = "Место не может быть пустым")
        String location
) {}