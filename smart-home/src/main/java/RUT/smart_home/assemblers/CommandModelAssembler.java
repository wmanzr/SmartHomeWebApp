package RUT.smart_home.assemblers;

import RUT.smart_home.controller.CommandController;
import RUT.smart_home.controller.DeviceController;
import RUT.smart_home_contract.api.dto.CommandResponse;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CommandModelAssembler implements RepresentationModelAssembler<CommandResponse, EntityModel<CommandResponse>> {

    @Override
    public EntityModel<CommandResponse> toModel(CommandResponse command) {
        return EntityModel.of(command,
                linkTo(methodOn(CommandController.class).getCommandById(command.getId())).withSelfRel(),
                linkTo(methodOn(DeviceController.class).getDeviceById(command.getDevice().getId())).withRel("device"),
                linkTo(methodOn(CommandController.class).getAllCommands(command.getDevice().getId(),0,10)).withRel("collection"));
    }

    @Override
    public CollectionModel<EntityModel<CommandResponse>> toCollectionModel(Iterable<? extends CommandResponse> entities) {
        return RepresentationModelAssembler.super.toCollectionModel(entities)
                .add(linkTo(methodOn(CommandController.class).getAllCommands(null, 0,10)).withSelfRel());
    }
}