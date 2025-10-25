package RUT.smart_home_contract.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DeviceRequest(
        @NotBlank(message = "Имя устройства не может быть пустым")
        String name,
        @NotNull(message = "Тип устройства не может быть пустым")
        DeviceType type,
        @NotBlank(message = "Местоположение не может быть пустым")
        String location,
        String metadata
) {}