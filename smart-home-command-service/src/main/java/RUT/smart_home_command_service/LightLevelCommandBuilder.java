package RUT.smart_home_command_service;

class LightLevelCommandBuilder implements CommandBuilder {
    private static final double BRIGHT_THRESHOLD = 70.0;

    @Override
    public CommandData build(double lightLevel) {
        if (lightLevel > BRIGHT_THRESHOLD) {
            return new CommandData("CLOSE_BLINDS", "");
        }
        return new CommandData("OPEN_BLINDS", "");
    }
}