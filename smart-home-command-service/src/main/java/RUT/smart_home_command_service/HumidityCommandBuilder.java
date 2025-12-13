package RUT.smart_home_command_service;

class HumidityCommandBuilder implements CommandBuilder {
    private static final double HIGH_THRESHOLD = 60.0;
    private static final double LOW_THRESHOLD = 40.0;

    @Override
    public CommandData build(double humidity) {
        if (humidity > HIGH_THRESHOLD) {
            return new CommandData("TURN_OFF", "");
        }
        return new CommandData("TURN_ON", "");
    }
}