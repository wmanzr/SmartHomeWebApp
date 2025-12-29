package RUT.smart_home_contract.api.dto;

import jakarta.validation.constraints.NotNull;

public record UpdateCommandStatusRequest(
        @NotNull(message = "Статус обязателен")
        CommandStatus status,
        String message
) {}

