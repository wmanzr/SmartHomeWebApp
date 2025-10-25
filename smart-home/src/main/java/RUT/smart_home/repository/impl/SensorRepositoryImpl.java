package RUT.smart_home.repository.impl;

import RUT.smart_home.entity.Sensor;
import RUT.smart_home.repository.BaseRepository;
import RUT.smart_home.repository.SensorRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SensorRepositoryImpl extends BaseRepository<Sensor, Long> implements SensorRepository {
    public SensorRepositoryImpl() {
        super(Sensor.class);
    }
    @Override
    public void create(Sensor sensor) {
        super.create(sensor);
    }
    @Override
    public Sensor findById(Long id) {
        return super.findById(id);
    }
    @Override
    public Sensor update(Sensor sensor) {
        return super.update(sensor);
    }
    @Override
    public List<Sensor> findAll() {
        return super.findAll();
    }
    @Override
    public void deleteById(Long id) {
        super.deleteById(id);
    }
}