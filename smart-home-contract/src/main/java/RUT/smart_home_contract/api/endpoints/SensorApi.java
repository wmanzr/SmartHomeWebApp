package RUT.smart_home_contract.api.endpoints;

import RUT.smart_home_contract.api.dto.SensorRequest;
import RUT.smart_home_contract.api.dto.SensorResponse;
import RUT.smart_home_contract.api.dto.StatusResponse;
import RUT.smart_home_contract.api.dto.UpdateSensorRequest;
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

import java.util.List;

@Tag(name = "sensors", description = "API для работы с датчиками")
@RequestMapping("/api/sensors")
public interface SensorApi {
    @Operation(summary = "Получить все датчики")
    @ApiResponse(responseCode = "200", description = "Список датчиков")
    @GetMapping
    CollectionModel<EntityModel<SensorResponse>> getAllSensors();

    @Operation(summary = "Получить датчик по ID")
    @ApiResponse(responseCode = "200", description = "Датчик найден")
    @ApiResponse(responseCode = "404", description = "Датчик не найден", content = @Content(schema = @Schema(implementation = StatusResponse.class)))
    @GetMapping("/{id}")
    EntityModel<SensorResponse> getSensorById(@PathVariable Long id);

    @Operation(summary = "Создать новый датчик")
    @ApiResponse(responseCode = "201", description = "Датчик успешно создан")
    @ApiResponse(responseCode = "400", description = "Ошибка валидации", content = @Content(schema = @Schema(implementation = StatusResponse.class)))
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    ResponseEntity<EntityModel<SensorResponse>> createSensor(@Valid @RequestBody SensorRequest request);

    @Operation(summary = "Обновить датчик")
    @ApiResponse(responseCode = "200", description = "Датчик обновлен")
    @ApiResponse(responseCode = "404", description = "Датчик не найден", content = @Content(schema = @Schema(implementation = StatusResponse.class)))
    @PutMapping("/{id}")
    EntityModel<SensorResponse> updateSensor(@PathVariable Long id, @Valid @RequestBody UpdateSensorRequest request);

    @Operation(summary = "Удалить датчик")
    @ApiResponse(responseCode = "204", description = "Датчик удален")
    @ApiResponse(responseCode = "404", description = "Датчик не найден")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteSensor(@PathVariable Long id);
}