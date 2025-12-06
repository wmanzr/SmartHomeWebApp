package RUT.smart_home_analytics_service;

import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
@GrpcService
public class SensorAnalyticsServiceImpl extends SensorAnalyticsServiceGrpc.SensorAnalyticsServiceImplBase {
    @Override
    public void analyzeSensorData(SensorDataRequest request, StreamObserver<SensorDecisionResponse> responseObserver) {

        long readingId = request.getReadingId();
        long sensorId = request.getSensorId();
        String type = request.getSensorType();
        double value = request.getValue();

        boolean shouldExecute = false;
        String commandAction = "";
        String commandValue = "";

        switch (type.toLowerCase()) {

            case "temperature" -> {
                if (value > 26) {
                    shouldExecute = true;
                    commandAction = CommandAction.SET_TEMPERATURE.name();
                    commandValue = "21";
                } else if (value < 18) {
                    shouldExecute = true;
                    commandAction = CommandAction.SET_TEMPERATURE.name();
                    commandValue = "23";
                } else {
                    shouldExecute = false;
                }
            }

            case "humidity" -> {
                if (value > 60) {
                    shouldExecute = true;
                    commandAction = CommandAction.TURN_OFF.name();
                } else if (value < 35) {
                    shouldExecute = true;
                    commandAction = CommandAction.TURN_ON.name();
                } else {
                    shouldExecute = false;
                }
            }

            case "smoke" -> {
                if (value > 0.5) {
                    shouldExecute = true;
                    commandAction = CommandAction.LOCK.name();
                } else {
                    shouldExecute = false;
                }
            }

            default -> {
                shouldExecute = false;
                commandAction = CommandAction.CUSTOM.name();
            }
        }

        SensorDecisionResponse response = SensorDecisionResponse.newBuilder()
                .setShouldExecute(shouldExecute)
                .setSensorId(sensorId)
                .setSensorType(type)
                .setCommandAction(commandAction)
                .setCommandValue(commandValue)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}