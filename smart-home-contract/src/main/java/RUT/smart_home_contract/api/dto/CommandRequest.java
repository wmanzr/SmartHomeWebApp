package RUT.smart_home_contract.api.dto;

import jakarta.validation.constraints.NotNull;

public record CommandRequest(
        @NotNull(message = "ID устройства обязателен") Long deviceId,
        @NotNull(message = "Тип команды обязателен") CommandAction action,
        String value
) {}