package RUT.smart_home.controller;

import RUT.smart_home.assemblers.CommandModelAssembler;
import RUT.smart_home.service.CommandService;
import RUT.smart_home_contract.api.dto.CommandRequest;
import RUT.smart_home_contract.api.dto.CommandResponse;
import RUT.smart_home_contract.api.dto.PagedResponse;
import RUT.smart_home_contract.api.endpoints.CommandApi;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommandController implements CommandApi {
    private final CommandService commandService;
    private final CommandModelAssembler commandModelAssembler;
    private final PagedResourcesAssembler<CommandResponse> pagedResourcesAssembler;

    public CommandController(CommandService commandService, CommandModelAssembler commandModelAssembler,
                             PagedResourcesAssembler<CommandResponse> pagedResourcesAssembler) {
        this.commandService = commandService;
        this.commandModelAssembler = commandModelAssembler;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
    }

    @Override
    public PagedModel<EntityModel<CommandResponse>> getAllCommands(Long deviceId, int page, int size) {
        PagedResponse<CommandResponse> pagedResponse = commandService.getAll(deviceId, page, size);
        Page<CommandResponse> commandPage = new PageImpl<>(
                pagedResponse.content(),
                PageRequest.of(pagedResponse.pageNumber(), pagedResponse.pageSize()),
                pagedResponse.totalElements()
        );

        return pagedResourcesAssembler.toModel(commandPage, commandModelAssembler);
    }

    @Override
    public EntityModel<CommandResponse> getCommandById(Long id) {
        CommandResponse command = commandService.getById(id);
        return commandModelAssembler.toModel(command);
    }

    @Override
    public ResponseEntity<EntityModel<CommandResponse>> createCommand(@Valid CommandRequest request) {
        CommandResponse createdCommand = commandService.create(request);
        EntityModel<CommandResponse> entityModel = commandModelAssembler.toModel(createdCommand);

        return ResponseEntity
                .created(entityModel.getRequiredLink("self").toUri())
                .body(entityModel);
    }
}