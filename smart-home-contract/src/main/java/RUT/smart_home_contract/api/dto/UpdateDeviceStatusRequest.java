package RUT.smart_home_contract.api.dto;

import jakarta.validation.constraints.NotNull;

public record UpdateDeviceStatusRequest(
        @NotNull(message = "Статус обязателен")
        DeviceStatus status,
        String metadata
) {}