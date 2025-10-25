package RUT.smart_home.assemblers;

import RUT.smart_home.controller.SensorController;
import RUT.smart_home.controller.SensorReadingController;
import RUT.smart_home_contract.api.dto.SensorReadingResponse;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class SensorReadingModelAssembler implements RepresentationModelAssembler<SensorReadingResponse, EntityModel<SensorReadingResponse>> {
    @Override
    public EntityModel<SensorReadingResponse> toModel(SensorReadingResponse reading) {
        return EntityModel.of(reading,
                linkTo(methodOn(SensorReadingController.class).getSensorReadingById(reading.getId())).withSelfRel(),
                linkTo(methodOn(SensorController.class).getSensorById(reading.getSensor().getId())).withRel("sensor"),
                linkTo(methodOn(SensorReadingController.class).getAllReadings(reading.getSensor().getId(), 0, 10)).withRel("collection"));
    }

    @Override
    public CollectionModel<EntityModel<SensorReadingResponse>> toCollectionModel(Iterable<? extends SensorReadingResponse> entities) {
        return RepresentationModelAssembler.super.toCollectionModel(entities)
                .add(linkTo(methodOn(SensorReadingController.class).getAllReadings(null,0,10)).withSelfRel());
    }
}