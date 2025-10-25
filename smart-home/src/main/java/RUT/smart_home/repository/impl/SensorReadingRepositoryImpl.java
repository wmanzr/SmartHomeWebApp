package RUT.smart_home.repository.impl;

import RUT.smart_home.entity.SensorReading;
import RUT.smart_home.repository.BaseRepository;
import RUT.smart_home.repository.SensorReadingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SensorReadingRepositoryImpl extends BaseRepository<SensorReading, Long> implements SensorReadingRepository {
    public SensorReadingRepositoryImpl() {
        super(SensorReading.class);
    }
    @Override
    public void create(SensorReading sensorReading) {
        super.create(sensorReading);
    }
    @Override
    public SensorReading findById(Long id) {
        return super.findById(id);
    }
    @Override
    public List<SensorReading> findAll() {
        return super.findAll();
    }
}