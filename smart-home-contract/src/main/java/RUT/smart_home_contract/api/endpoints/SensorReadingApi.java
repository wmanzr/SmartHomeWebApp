package RUT.smart_home_contract.api.endpoints;

import RUT.smart_home_contract.api.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "sensor-readings", description = "API для работы с показаниями датчиков")
@RequestMapping("/api/readings")
public interface SensorReadingApi {
    @Operation(summary = "Получить все показания")
    @ApiResponse(responseCode = "200", description = "Список показаний")
    @GetMapping
    PagedModel<EntityModel<SensorReadingResponse>> getAllReadings(
            @Parameter(description = "Фильтр по ID датчика") @RequestParam(required = false) Long sensorId,
            @Parameter(description = "Номер страницы (0..N)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Размер страницы") @RequestParam(defaultValue = "10") int size
    );

    @Operation(summary = "Получить показание датчика по ID")
    @ApiResponse(responseCode = "200", description = "Показание найдено")
    @ApiResponse(responseCode = "404", description = "Показание не найдено", content = @Content(schema = @Schema(implementation = StatusResponse.class)))
    @GetMapping("/{id}")
    EntityModel<SensorReadingResponse> getSensorReadingById(@PathVariable Long id);

    @Operation(summary = "Создать новое показание")
    @ApiResponse(responseCode = "201", description = "Показание сохранено")
    @ApiResponse(responseCode = "400", description = "Ошибка валидации", content = @Content(schema = @Schema(implementation = StatusResponse.class)))
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    ResponseEntity<EntityModel<SensorReadingResponse>> createReading(@Valid @RequestBody SensorReadingRequest request);
}