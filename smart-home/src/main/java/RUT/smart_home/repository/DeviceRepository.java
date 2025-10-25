package RUT.smart_home.repository;

import RUT.smart_home.entity.Device;

import java.util.List;

public interface DeviceRepository {
    void create(Device device);
    Device findById(Long id);
    Device update(Device device);
    List<Device> findAll();
    void deleteById(Long id);
}