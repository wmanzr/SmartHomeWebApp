package RUT.smart_home_analytics_service;

import RUT.smart_home_contract.api.dto.SensorType;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class SensorAnalyticsServiceImpl extends SensorAnalyticsServiceGrpc.SensorAnalyticsServiceImplBase {
    private final TemperatureAnalyzer temperatureAnalyzer = new TemperatureAnalyzer();
    private final HumidityAnalyzer humidityAnalyzer = new HumidityAnalyzer();
    private final MotionAnalyzer motionAnalyzer = new MotionAnalyzer();
    private final LightLevelAnalyzer lightLevelAnalyzer = new LightLevelAnalyzer();

    @Override
    public void analyzeSensorData(SensorDataRequest request, StreamObserver<DecisionResponse> responseObserver) {
        try {
            SensorDataValidator.validate(request);

            SensorAnalyzer analyzer = getAnalyzer(request.getSensorType());
            boolean shouldExecute = analyzer.shouldExecuteCommand(request.getValue());
            
            DecisionResponse response = DecisionResponse.newBuilder()
                    .setShouldExecute(shouldExecute)
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (StatusRuntimeException e) {
            responseObserver.onError(e);
        } catch (IllegalArgumentException e) {
            responseObserver.onError(Status.INVALID_ARGUMENT
                    .withDescription("Invalid request: " + e.getMessage())
                    .withCause(e)
                    .asRuntimeException());
        } catch (Exception e) {
            responseObserver.onError(Status.INTERNAL
                    .withDescription("Internal error during analysis: " + e.getMessage())
                    .withCause(e)
                    .asRuntimeException());
        }
    }

    private SensorAnalyzer getAnalyzer(String sensorType) {
        SensorType type = SensorType.valueOf(sensorType.toUpperCase());
        return switch (type) {
            case TEMPERATURE -> temperatureAnalyzer;
            case HUMIDITY -> humidityAnalyzer;
            case MOTION -> motionAnalyzer;
            case LIGHT_LEVEL -> lightLevelAnalyzer;
        };
    }
}