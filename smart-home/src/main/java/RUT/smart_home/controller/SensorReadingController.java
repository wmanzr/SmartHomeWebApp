package RUT.smart_home.controller;

import RUT.smart_home.CommandData;
import RUT.smart_home.SensorAnalyticsServiceGrpc;
import RUT.smart_home.SensorDataRequest;
import RUT.smart_home.DecisionResponse;
import RUT.smart_home.assemblers.SensorReadingModelAssembler;
import RUT.smart_home.config.RabbitMQConfig;
import RUT.smart_home.service.SensorReadingService;
import RUT.smart_home_contract.api.dto.*;
import RUT.smart_home_contract.api.endpoints.SensorReadingApi;
import RUT.smart_home_events_contract.events.CallCommandEventFromSensorReading;
import jakarta.validation.Valid;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SensorReadingController implements SensorReadingApi {
    private final SensorReadingService sensorReadingService;
    private final SensorReadingModelAssembler sensorReadingModelAssembler;
    private final PagedResourcesAssembler<SensorReadingResponse> pagedResourcesAssembler;
    private final RabbitTemplate rabbitTemplate;

    public SensorReadingController(SensorReadingService sensorReadingService, SensorReadingModelAssembler sensorReadingModelAssembler,
                                   PagedResourcesAssembler<SensorReadingResponse> pagedResourcesAssembler, RabbitTemplate rabbitTemplate) {
        this.sensorReadingService = sensorReadingService;
        this.sensorReadingModelAssembler = sensorReadingModelAssembler;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public EntityModel<SensorReadingResponse> getSensorReadingById(Long id) {
        SensorReadingResponse reading = sensorReadingService.getById(id);
        return sensorReadingModelAssembler.toModel(reading);
    }

    @Override
    public PagedModel<EntityModel<SensorReadingResponse>> getAllReadings(Long sensorId, int page, int size) {
        PagedResponse<SensorReadingResponse> pagedResponse = sensorReadingService.getAll(sensorId, page, size);
        Page<SensorReadingResponse> readingPage = new PageImpl<>(
                pagedResponse.content(),
                PageRequest.of(pagedResponse.pageNumber(), pagedResponse.pageSize()),
                pagedResponse.totalElements()
        );

        return pagedResourcesAssembler.toModel(readingPage, sensorReadingModelAssembler);
    }

    @Override
    public ResponseEntity<EntityModel<SensorReadingResponse>> createReading(@Valid SensorReadingRequest request) {
        SensorReadingResponse createdReading = sensorReadingService.create(request);
        EntityModel<SensorReadingResponse> entityModel = sensorReadingModelAssembler.toModel(createdReading);
        callCommand(createdReading.getId());

        return ResponseEntity
                .created(entityModel.getRequiredLink("self").toUri())
                .body(entityModel);
    }

    @GrpcClient("analytics-service")
    private SensorAnalyticsServiceGrpc.SensorAnalyticsServiceBlockingStub analyticsStub;

    @PostMapping("/{id}/call-command")
    public String callCommand(@PathVariable Long id) {
        var reading = sensorReadingService.getById(id);

        SensorDataRequest request = SensorDataRequest.newBuilder()
                .setReadingId(id)
                .setSensorId(reading.getSensor().getId())
                .setSensorType(reading.getSensor().getType().toString())
                .setValue(reading.getValue())
                .setTimestamp(reading.getTimestamp().toString())
                .build();
        try {
            DecisionResponse decision = analyticsStub.analyzeSensorData(request);

            if (decision.getShouldExecute()) {
                CommandData commandData = decision.getCommandData();
                CallCommandEventFromSensorReading event = sensorReadingService.callCommand(decision, reading);
                rabbitTemplate.convertAndSend(RabbitMQConfig.FANOUT_EXCHANGE, "", event);

                return "Analytics decision: execute=" + decision.getShouldExecute()
                        + ", action=" + commandData.getCommandAction()
                        + ", value=" + commandData.getCommandValue();
            } else {
                return "No action required";
            }

        } catch (io.grpc.StatusRuntimeException e) {
            return "ERROR: Analytics service is unavailable. gRPC call failed: "
                    + e.getStatus().getCode() + " - " + e.getStatus().getDescription();
        } catch (Exception e) {
            return "ERROR: Failed to analyze sensor data: " + e.getMessage();
        }
    }
}