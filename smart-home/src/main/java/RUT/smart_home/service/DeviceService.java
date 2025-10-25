package RUT.smart_home.service;

import RUT.smart_home_contract.api.dto.*;

import java.util.List;

public interface DeviceService {

    List<DeviceResponse> getAll();

    DeviceResponse getById(Long id);

    DeviceResponse create(DeviceRequest request);

    DeviceResponse updateStatus(Long id, UpdateDeviceStatusRequest request);

    void delete(Long id);
}