package RUT.smart_home.service.impl;

import RUT.smart_home.config.RabbitMQConfig;
import RUT.smart_home.entity.Sensor;
import RUT.smart_home.entity.SensorReading;
import RUT.smart_home.repository.SensorReadingRepository;
import RUT.smart_home.service.SensorReadingService;
import RUT.smart_home.service.SensorService;
import RUT.smart_home_contract.api.dto.*;
import RUT.smart_home_contract.api.exception.ResourceNotFoundException;
import RUT.smart_home_events_contract.events.SensorReadingCreatedEvent;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Stream;

@Service
public class SensorReadingServiceImpl implements SensorReadingService {

    private final SensorReadingRepository sensorReadingRepository;
    private final SensorService sensorService;
    private final ModelMapper modelMapper;
    private final RabbitTemplate rabbitTemplate;

    public SensorReadingServiceImpl(
            SensorReadingRepository sensorReadingRepository,
            SensorService sensorService,
            ModelMapper modelMapper, RabbitTemplate rabbitTemplate
    ) {
        this.sensorReadingRepository = sensorReadingRepository;
        this.sensorService = sensorService;
        this.modelMapper = modelMapper;
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public SensorReadingResponse getById(Long id) {
        SensorReading reading = sensorReadingRepository.findById(id);
        if (reading == null) {
            throw new ResourceNotFoundException("Reading", id);
        }
        return modelMapper.map(reading, SensorReadingResponse.class);
    }

    @Override
    public PagedResponse<SensorReadingResponse> getAll(Long sensorId, int page, int size) {
        Stream<SensorReading> readingStream = sensorReadingRepository.findAll().stream()
                .sorted((r1, r2) -> r1.getId().compareTo(r2.getId()));

        if (sensorId != null) {
            readingStream = readingStream.filter(reading ->
                    reading.getSensor() != null && reading.getSensor().getId().equals(sensorId));
        }

        List<SensorReadingResponse> allReadings = readingStream
                .map(r -> modelMapper.map(r, SensorReadingResponse.class))
                .toList();

        int totalElements = allReadings.size();
        int totalPages = (int) Math.ceil((double) totalElements / size);
        int fromIndex = page * size;
        int toIndex = Math.min(fromIndex + size, totalElements);

        List<SensorReadingResponse> pageContent =
                (fromIndex > toIndex) ? List.of() : allReadings.subList(fromIndex, toIndex);

        return new PagedResponse<>(pageContent, page, size, totalElements, totalPages, page >= totalPages - 1);
    }

    @Override
    @Transactional
    public SensorReadingResponse create(SensorReadingRequest request) {
        sensorService.getById(request.sensorId());

        SensorReading reading = new SensorReading(
                modelMapper.map(sensorService.getById(request.sensorId()), Sensor.class),
                request.value(),
                request.unit(),
                request.timestamp()
        );

        sensorReadingRepository.create(reading);
        SensorReadingCreatedEvent event = new SensorReadingCreatedEvent(
                reading.getId(),
                reading.getSensor().getId(),
                reading.getValue(),
                reading.getUnit(),
                reading.getTimestamp()
        );
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, RabbitMQConfig.ROUTING_KEY_SENSOR_READING_CREATED, event);

        return modelMapper.map(reading, SensorReadingResponse.class);
    }
}