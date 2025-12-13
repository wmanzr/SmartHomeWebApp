package RUT.smart_home_analytics_service;

import RUT.smart_home_command_service.CommandServiceGrpc;
import RUT.smart_home_command_service.CommandRequest;
import RUT.smart_home_command_service.CommandResponse;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import net.devh.boot.grpc.client.inject.GrpcClient;

@GrpcService
public class SensorAnalyticsServiceImpl extends SensorAnalyticsServiceGrpc.SensorAnalyticsServiceImplBase {
    @GrpcClient("command-service")
    private CommandServiceGrpc.CommandServiceBlockingStub commandStub;

    private final TemperatureAnalyzer temperatureAnalyzer = new TemperatureAnalyzer();
    private final HumidityAnalyzer humidityAnalyzer = new HumidityAnalyzer();
    private final MotionAnalyzer motionAnalyzer = new MotionAnalyzer();
    private final LightLevelAnalyzer lightLevelAnalyzer = new LightLevelAnalyzer();


    @Override
    public void analyzeSensorData(SensorDataRequest request, StreamObserver<DecisionResponse> responseObserver) {
        try {
            SensorAnalyzer analyzer = getAnalyzer(request.getSensorType());
            boolean shouldExecute = analyzer.shouldExecuteCommand(request.getValue());
            DecisionResponse.Builder responseBuilder = DecisionResponse.newBuilder()
                    .setShouldExecute(shouldExecute)
                    .setSensorType(request.getSensorType());

            if (shouldExecute) {
                CommandRequest commandRequest = CommandRequest.newBuilder()
                        .setSensorId(request.getSensorId())
                        .setSensorType(request.getSensorType())
                        .setValue(request.getValue())
                        .build();

                CommandResponse commandResponse = commandStub.buildCommand(commandRequest);
                CommandData commandData = CommandData.newBuilder()
                        .setSensorId(request.getSensorId())
                        .setCommandAction(commandResponse.getCommandAction())
                        .setCommandValue(commandResponse.getCommandValue())
                        .build();

                responseBuilder.setCommandData(commandData);
            }
            responseObserver.onNext(responseBuilder.build());
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
}