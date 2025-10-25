package RUT.smart_home.repository;

import RUT.smart_home.entity.SensorReading;

import java.util.List;

public interface SensorReadingRepository {
    void create(SensorReading sensorReading);
    SensorReading  findById(Long id);
    List<SensorReading> findAll();
}