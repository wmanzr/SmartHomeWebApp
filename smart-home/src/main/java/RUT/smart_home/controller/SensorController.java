package RUT.smart_home.controller;

import RUT.smart_home.assemblers.SensorModelAssembler;
import RUT.smart_home.service.SensorService;
import RUT.smart_home_contract.api.dto.SensorRequest;
import RUT.smart_home_contract.api.dto.SensorResponse;
import RUT.smart_home_contract.api.dto.UpdateSensorRequest;
import RUT.smart_home_contract.api.endpoints.SensorApi;
import jakarta.validation.Valid;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SensorController implements SensorApi {
    private final SensorService sensorService;
    private final SensorModelAssembler sensorModelAssembler;
    public SensorController(SensorService sensorService, SensorModelAssembler sensorModelAssembler) {
        this.sensorService = sensorService;
        this.sensorModelAssembler = sensorModelAssembler;
    }
    @Override
    public CollectionModel<EntityModel<SensorResponse>> getAllSensors() {
        List<SensorResponse> sensors = sensorService.getAll();
        return sensorModelAssembler.toCollectionModel(sensors);
    }

    @Override
    public EntityModel<SensorResponse> getSensorById(Long id) {
        SensorResponse sensor = sensorService.getById(id);
        return sensorModelAssembler.toModel(sensor);
    }

    @Override
    public ResponseEntity<EntityModel<SensorResponse>> createSensor(@Valid SensorRequest request) {
        SensorResponse createdSensor = sensorService.create(request);
        EntityModel<SensorResponse> entityModel = sensorModelAssembler.toModel(createdSensor);

        return ResponseEntity
                .created(entityModel.getRequiredLink("self").toUri())
                .body(entityModel);
    }

    @Override
    public EntityModel<SensorResponse> updateSensor(Long id, @Valid UpdateSensorRequest request) {
        SensorResponse updatedSensor = sensorService.update(id, request);
        return sensorModelAssembler.toModel(updatedSensor);
    }

    @Override
    public void deleteSensor(Long id) {
        sensorService.delete(id);
    }
}