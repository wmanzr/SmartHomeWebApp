package RUT.smart_home_command_service;

class TemperatureCommandBuilder implements CommandBuilder {
    private static final double HIGH_THRESHOLD = 25.0;
    private static final double LOW_THRESHOLD = 19.0;

    @Override
    public CommandData build(double temperature) {
        if (temperature > HIGH_THRESHOLD) {
            return new CommandData("SET_TEMPERATURE", "21.0");
        }
        if (temperature < LOW_THRESHOLD) {
            return new CommandData("SET_TEMPERATURE", "22.0");
        }
        return new CommandData("TURN_OFF", "");
    }
}