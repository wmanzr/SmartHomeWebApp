package RUT.smart_home.service;

import RUT.smart_home_contract.api.dto.*;

import java.util.List;

public interface SensorService {

    List<SensorResponse> getAll();

    SensorResponse getById(Long id);

    SensorResponse create(SensorRequest request);

    SensorResponse update(Long id, UpdateSensorRequest request);

    void delete(Long id);
}