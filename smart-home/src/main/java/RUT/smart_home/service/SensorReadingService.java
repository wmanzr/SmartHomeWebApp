package RUT.smart_home.service;

import RUT.smart_home.CommandHomeResponse;
import RUT.smart_home_contract.api.dto.PagedResponse;
import RUT.smart_home_contract.api.dto.SensorReadingRequest;
import RUT.smart_home_contract.api.dto.SensorReadingResponse;
import RUT.smart_home_events_contract.events.CallCommandEventFromSensorReading;

public interface SensorReadingService {

    SensorReadingResponse getById(Long id);

    PagedResponse<SensorReadingResponse> getAll(Long sensorId, int page, int size);

    SensorReadingResponse create(SensorReadingRequest request);

    CallCommandEventFromSensorReading callCommand(CommandHomeResponse response, SensorReadingResponse reading);
}