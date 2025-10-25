package RUT.smart_home_contract.api.endpoints;

import RUT.smart_home_contract.api.dto.CommandRequest;
import RUT.smart_home_contract.api.dto.CommandResponse;
import RUT.smart_home_contract.api.dto.StatusResponse;
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

@Tag(name = "commands", description = "API для управления командами устройств")
@RequestMapping("/api/commands")
public interface CommandApi {
    @Operation(summary = "Получить список всех команд")
    @ApiResponse(responseCode = "200", description = "Список команд")
    @GetMapping
    PagedModel<EntityModel<CommandResponse>> getAllCommands(
            @Parameter(description = "Фильтр по ID устройства") @RequestParam(required = false) Long deviceId,
            @Parameter(description = "Номер страницы (0..N)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Размер страницы") @RequestParam(defaultValue = "10") int size
    );

    @Operation(summary = "Получить команду по ID")
    @ApiResponse(responseCode = "200", description = "Команда найдена")
    @ApiResponse(responseCode = "404", description = "Команда не найдена", content = @Content(schema = @Schema(implementation = StatusResponse.class)))
    @GetMapping("/{id}")
    EntityModel<CommandResponse> getCommandById(@PathVariable Long id);

    @Operation(summary = "Создать новую команду для устройства")
    @ApiResponse(responseCode = "201", description = "Команда успешно создана")
    @ApiResponse(responseCode = "400", description = "Ошибка валидации", content = @Content(schema = @Schema(implementation = StatusResponse.class)))
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    ResponseEntity<EntityModel<CommandResponse>> createCommand(@Valid @RequestBody CommandRequest request);
}