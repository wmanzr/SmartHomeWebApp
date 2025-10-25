package RUT.smart_home.service;

import RUT.smart_home_contract.api.dto.PagedResponse;
import RUT.smart_home_contract.api.dto.SensorReadingRequest;
import RUT.smart_home_contract.api.dto.SensorReadingResponse;

public interface SensorReadingService {

    SensorReadingResponse getById(Long id);

    PagedResponse<SensorReadingResponse> getAll(Long sensorId, int page, int size);

    SensorReadingResponse create(SensorReadingRequest request);
}