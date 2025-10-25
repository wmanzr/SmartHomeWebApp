package RUT.smart_home.repository.impl;

import RUT.smart_home.entity.Device;
import RUT.smart_home.repository.BaseRepository;
import RUT.smart_home.repository.DeviceRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DeviceRepositoryImpl extends BaseRepository<Device, Long> implements DeviceRepository {
    public DeviceRepositoryImpl() {
        super(Device.class);
    }
    @Override
    public void create(Device device) {
        super.create(device);
    }
    @Override
    public Device findById(Long id) {
        return super.findById(id);
    }
    @Override
    public Device update(Device device) {
        return super.update(device);
    }
    @Override
    public List<Device> findAll() {
        return super.findAll();
    }
    @Override
    public void deleteById(Long id) {
        super.deleteById(id);
    }
}