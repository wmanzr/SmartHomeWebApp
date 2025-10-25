package RUT.smart_home_contract.api.endpoints;

import RUT.smart_home_contract.api.dto.DeviceRequest;
import RUT.smart_home_contract.api.dto.DeviceResponse;
import RUT.smart_home_contract.api.dto.StatusResponse;
import RUT.smart_home_contract.api.dto.UpdateDeviceStatusRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "devices", description = "API для работы с устройствами")
@RequestMapping("/api/devices")
public interface DeviceApi {
    @Operation(summary = "Получить список всех устройств")
    @ApiResponse(responseCode = "200", description = "Список устройств")
    @GetMapping
    CollectionModel<EntityModel<DeviceResponse>> getAllDevices();

    @Operation(summary = "Получить устройство по ID")
    @ApiResponse(responseCode = "200", description = "Устройство найдено")
    @ApiResponse(responseCode = "404", description = "Устройство не найдено", content = @Content(schema = @Schema(implementation = StatusResponse.class)))
    @GetMapping("/{id}")
    EntityModel<DeviceResponse> getDeviceById(@PathVariable Long id);

    @Operation(summary = "Создать новое устройство")
    @ApiResponse(responseCode = "201", description = "Устройство успешно создано")
    @ApiResponse(responseCode = "400", description = "Ошибка валидации", content = @Content(schema = @Schema(implementation = StatusResponse.class)))
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    ResponseEntity<EntityModel<DeviceResponse>> createDevice(@Valid @RequestBody DeviceRequest request);

    @Operation(summary = "Обновить статус устройства")
    @ApiResponse(responseCode = "200", description = "Статус обновлён")
    @ApiResponse(responseCode = "404", description = "Устройство не найдено", content = @Content(schema = @Schema(implementation = StatusResponse.class)))
    @PutMapping("/{id}/status")
    EntityModel<DeviceResponse> updateDeviceStatus(@PathVariable Long id, @Valid @RequestBody UpdateDeviceStatusRequest request);

    @Operation(summary = "Удалить устройство")
    @ApiResponse(responseCode = "204", description = "Устройство удалено")
    @ApiResponse(responseCode = "404", description = "Устройство не найдено")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteDevice(@PathVariable Long id);
}