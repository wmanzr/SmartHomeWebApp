package RUT.smart_home_command_service;

import RUT.smart_home_contract.api.dto.SensorType;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class CommandServiceImpl extends CommandServiceGrpc.CommandServiceImplBase {
    private final TemperatureCommandBuilder temperatureBuilder = new TemperatureCommandBuilder();
    private final HumidityCommandBuilder humidityBuilder = new HumidityCommandBuilder();
    private final MotionCommandBuilder motionBuilder = new MotionCommandBuilder();
    private final LightLevelCommandBuilder lightLevelBuilder = new LightLevelCommandBuilder();

    @Override
    public void buildCommand(CommandRequest request, StreamObserver<CommandResponse> responseObserver) {
        try {
            CommandBuilder builder = getBuilder(request.getSensorType());
            CommandData command = builder.build(request.getValue());

            CommandResponse response = CommandResponse.newBuilder()
                    .setCommandAction(command.action())
                    .setCommandValue(command.value())
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(e);
        }
    }

    private CommandBuilder getBuilder(String sensorType) {
        SensorType type = SensorType.valueOf(sensorType.toUpperCase());
        return switch (type) {
            case TEMPERATURE -> temperatureBuilder;
            case HUMIDITY -> humidityBuilder;
            case MOTION -> motionBuilder;
            case LIGHT_LEVEL -> lightLevelBuilder;
        };
    }
}