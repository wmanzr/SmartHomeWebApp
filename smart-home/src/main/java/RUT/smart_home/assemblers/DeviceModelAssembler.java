package RUT.smart_home.assemblers;

import RUT.smart_home.controller.CommandController;
import RUT.smart_home.controller.DeviceController;
import RUT.smart_home_contract.api.dto.DeviceResponse;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class DeviceModelAssembler implements RepresentationModelAssembler<DeviceResponse, EntityModel<DeviceResponse>> {
    @Override
    public EntityModel<DeviceResponse> toModel(DeviceResponse device) {
        return EntityModel.of(device,
                linkTo(methodOn(DeviceController.class).getDeviceById(device.getId())).withSelfRel(),
                linkTo(methodOn(CommandController.class).getAllCommands(device.getId(),0,10)).withSelfRel(),
                linkTo(methodOn(DeviceController.class).getAllDevices()).withRel("collection"));
    }

    @Override
    public CollectionModel<EntityModel<DeviceResponse>> toCollectionModel(Iterable<? extends DeviceResponse> entities) {
        return RepresentationModelAssembler.super.toCollectionModel(entities)
                .add(linkTo(methodOn(DeviceController.class).getAllDevices()).withSelfRel());
    }
}