package RUT.smart_home.controller;

import RUT.smart_home.assemblers.SensorReadingModelAssembler;
import RUT.smart_home.service.SensorReadingService;
import RUT.smart_home_contract.api.dto.*;
import RUT.smart_home_contract.api.endpoints.SensorReadingApi;
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
public class SensorReadingController implements SensorReadingApi {
    private final SensorReadingService sensorReadingService;
    private final SensorReadingModelAssembler sensorReadingModelAssembler;
    private final PagedResourcesAssembler<SensorReadingResponse> pagedResourcesAssembler;

    public SensorReadingController(SensorReadingService sensorReadingService, SensorReadingModelAssembler sensorReadingModelAssembler,
                                   PagedResourcesAssembler<SensorReadingResponse> pagedResourcesAssembler) {
        this.sensorReadingService = sensorReadingService;
        this.sensorReadingModelAssembler = sensorReadingModelAssembler;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
    }

    @Override
    public EntityModel<SensorReadingResponse> getSensorReadingById(Long id) {
        SensorReadingResponse reading = sensorReadingService.getById(id);
        return sensorReadingModelAssembler.toModel(reading);
    }

    @Override
    public PagedModel<EntityModel<SensorReadingResponse>> getAllReadings(Long sensorId, int page, int size) {
        PagedResponse<SensorReadingResponse> pagedResponse = sensorReadingService.getAll(sensorId, page, size);
        Page<SensorReadingResponse> readingPage = new PageImpl<>(
                pagedResponse.content(),
                PageRequest.of(pagedResponse.pageNumber(), pagedResponse.pageSize()),
                pagedResponse.totalElements()
        );

        return pagedResourcesAssembler.toModel(readingPage, sensorReadingModelAssembler);
    }

    @Override
    public ResponseEntity<EntityModel<SensorReadingResponse>> createReading(@Valid SensorReadingRequest request) {
        SensorReadingResponse createdReading = sensorReadingService.create(request);
        EntityModel<SensorReadingResponse> entityModel = sensorReadingModelAssembler.toModel(createdReading);

        return ResponseEntity
                .created(entityModel.getRequiredLink("self").toUri())
                .body(entityModel);
    }
}