package RUT.smart_home.controller;

import RUT.smart_home.assemblers.DeviceModelAssembler;
import RUT.smart_home.service.DeviceService;
import RUT.smart_home_contract.api.dto.*;
import RUT.smart_home_contract.api.endpoints.DeviceApi;
import jakarta.validation.Valid;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DeviceController implements DeviceApi {
    private final DeviceService deviceService;
    private final DeviceModelAssembler deviceModelAssembler;

    public DeviceController(DeviceService deviceService, DeviceModelAssembler deviceModelAssembler) {
        this.deviceService = deviceService;
        this.deviceModelAssembler = deviceModelAssembler;
    }

    @Override
    public CollectionModel<EntityModel<DeviceResponse>> getAllDevices() {
        List<DeviceResponse> devices =  deviceService.getAll();
        return deviceModelAssembler.toCollectionModel(devices);
    }

    @Override
    public EntityModel<DeviceResponse> getDeviceById(Long id) {
        DeviceResponse device = deviceService.getById(id);
        return deviceModelAssembler.toModel(device);
    }

    @Override
    public ResponseEntity<EntityModel<DeviceResponse>> createDevice(@Valid DeviceRequest request) {
        DeviceResponse createdDevice = deviceService.create(request);
        EntityModel<DeviceResponse> entityModel = deviceModelAssembler.toModel(createdDevice);

        return ResponseEntity
                .created(entityModel.getRequiredLink("self").toUri())
                .body(entityModel);
    }

    @Override
    public EntityModel<DeviceResponse> updateDeviceStatus(Long id, @Valid UpdateDeviceStatusRequest request) {
        DeviceResponse updatedDevice = deviceService.updateStatus(id, request);
        return deviceModelAssembler.toModel(updatedDevice);
    }

    @Override
    public void deleteDevice(Long id) {
        deviceService.delete(id);
    }
}