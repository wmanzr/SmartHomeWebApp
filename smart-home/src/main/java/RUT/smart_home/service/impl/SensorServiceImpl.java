package RUT.smart_home.service.impl;

import RUT.smart_home.config.RabbitMQConfig;
import RUT.smart_home.entity.Sensor;
import RUT.smart_home.repository.SensorRepository;
import RUT.smart_home.service.SensorService;
import RUT.smart_home_contract.api.dto.*;
import RUT.smart_home_contract.api.exception.ResourceAlreadyExistsException;
import RUT.smart_home_contract.api.exception.ResourceNotFoundException;
import RUT.smart_home_events_contract.events.SensorCreatedEvent;
import RUT.smart_home_events_contract.events.SensorDeletedEvent;
import RUT.smart_home_events_contract.events.SensorUpdatedEvent;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SensorServiceImpl implements SensorService {

    private final SensorRepository sensorRepository;
    private final ModelMapper modelMapper;
    private final RabbitTemplate rabbitTemplate;

    public SensorServiceImpl(SensorRepository sensorRepository, ModelMapper modelMapper, RabbitTemplate rabbitTemplate) {
        this.sensorRepository = sensorRepository;
        this.modelMapper = modelMapper;
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public List<SensorResponse> getAll() {
        return sensorRepository.findAll().stream()
                .map(sensor -> modelMapper.map(sensor, SensorResponse.class))
                .toList();
    }

    @Override
    public SensorResponse getById(Long id) {
        Sensor sensor = sensorRepository.findById(id);
        if (sensor == null) {
            throw new ResourceNotFoundException("Sensor", id);
        }
        return modelMapper.map(sensor, SensorResponse.class);
    }

    @Override
    @Transactional
    public SensorResponse create(SensorRequest request) {
        boolean exists = sensorRepository.findAll().stream()
                .anyMatch(s -> s.getName().equalsIgnoreCase(request.name()));
        if (exists) {
            throw new ResourceAlreadyExistsException("Sensor", request.name());
        }

        Sensor sensor = new Sensor(
                request.name(),
                request.type(),
                request.location()
        );

        sensorRepository.create(sensor);
        SensorCreatedEvent event = new SensorCreatedEvent(
                sensor.getId(),
                sensor.getName(),
                sensor.getType().toString(),
                sensor.getLocation()
        );
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, RabbitMQConfig.ROUTING_KEY_SENSOR_CREATED, event);

        return modelMapper.map(sensor, SensorResponse.class);
    }

    @Override
    @Transactional
    public SensorResponse update(Long id, UpdateSensorRequest request) {
        Sensor existing = sensorRepository.findById(id);

        existing.setName(request.name());
        existing.setLocation(request.location());

        Sensor updated = sensorRepository.update(existing);
        SensorUpdatedEvent event = new SensorUpdatedEvent(
                updated.getId(),
                updated.getName(),
                updated.getLocation()
        );
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, RabbitMQConfig.ROUTING_KEY_SENSOR_UPDATED, event);

        return modelMapper.map(updated, SensorResponse.class);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Sensor sensor = sensorRepository.findById(id);
        sensorRepository.deleteById(id);
        SensorDeletedEvent event = new SensorDeletedEvent(
                sensor.getId(),
                sensor.getName()
        );
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, RabbitMQConfig.ROUTING_KEY_SENSOR_DELETED, event);
    }
}