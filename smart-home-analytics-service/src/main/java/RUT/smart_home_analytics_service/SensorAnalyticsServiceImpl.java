package RUT.smart_home_analytics_service;

import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
@GrpcService

public class SensorAnalyticsServiceImpl extends SensorAnalyticsServiceGrpc.SensorAnalyticsServiceImplBase {
    private final TemperatureAnalyzer temperatureAnalyzer;
    private final HumidityAnalyzer humidityAnalyzer;
    private final MotionAnalyzer motionAnalyzer;
    private final LightLevelAnalyzer lightLevelAnalyzer;

    public SensorAnalyticsServiceImpl() {
        this.temperatureAnalyzer = new TemperatureAnalyzer();
        this.humidityAnalyzer = new HumidityAnalyzer();
        this.motionAnalyzer = new MotionAnalyzer();
        this.lightLevelAnalyzer = new LightLevelAnalyzer();
    }

    @Override
    public void analyzeSensorData(SensorDataRequest request, StreamObserver<SensorDecisionResponse> responseObserver) {
        try {
            SensorAnalyzer analyzer = getAnalyzer(request.getSensorType());
            Decision decision = analyzer.analyze(request.getValue());

            SensorDecisionResponse response = buildResponse(request, decision);

            responseObserver.onNext(response);
            responseObserver.onCompleted();

        } catch (Exception e) {
            responseObserver.onError(e);
        }
    }

    private SensorAnalyzer getAnalyzer(String sensorType) {
        return switch (sensorType.toUpperCase()) {
            case "TEMPERATURE" -> temperatureAnalyzer;
            case "HUMIDITY" -> humidityAnalyzer;
            case "MOTION" -> motionAnalyzer;
            case "LIGHT_LEVEL" -> lightLevelAnalyzer;
            default -> throw new IllegalArgumentException("Unknown sensor type: " + sensorType);
        };
    }

    private SensorDecisionResponse buildResponse(SensorDataRequest request, Decision decision) {
        return SensorDecisionResponse.newBuilder()
                .setShouldExecute(decision.shouldExecute())
                .setSensorId(request.getSensorId())
                .setSensorType(request.getSensorType().toUpperCase())
                .setCommandAction(decision.commandAction())
                .setCommandValue(decision.commandValue())
                .build();
    }
}