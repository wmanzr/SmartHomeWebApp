package RUT.smart_home.assemblers;

import RUT.smart_home.controller.SensorController;
import RUT.smart_home_contract.api.dto.SensorResponse;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class SensorModelAssembler implements RepresentationModelAssembler<SensorResponse, EntityModel<SensorResponse>> {
    @Override
    public EntityModel<SensorResponse> toModel(SensorResponse sensor) {
        return EntityModel.of(sensor,
                linkTo(methodOn(SensorController.class).getSensorById(sensor.getId())).withSelfRel(),
                linkTo(methodOn(SensorController.class).getAllSensors()).withRel("collection"));
    }

    @Override
    public CollectionModel<EntityModel<SensorResponse>> toCollectionModel(Iterable<? extends SensorResponse> entities) {
        return RepresentationModelAssembler.super.toCollectionModel(entities)
                .add(linkTo(methodOn(SensorController.class).getAllSensors()).withSelfRel());
    }
}