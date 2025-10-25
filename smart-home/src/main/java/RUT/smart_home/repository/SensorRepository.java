package RUT.smart_home.repository;

import RUT.smart_home.entity.Sensor;

import java.util.List;

public interface SensorRepository {
    void create(Sensor sensor);
    Sensor findById(Long id);
    Sensor update(Sensor sensor);
    List<Sensor> findAll();
    void deleteById(Long id);
}