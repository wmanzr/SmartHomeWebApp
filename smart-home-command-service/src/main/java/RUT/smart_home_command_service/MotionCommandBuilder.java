package RUT.smart_home_command_service;

class MotionCommandBuilder implements CommandBuilder {
    @Override
    public CommandData build(double motionValue) {
        if (motionValue >= 1.0) {
            return new CommandData("ACTIVATE_ALARM", "");
        }
        return new CommandData("DEACTIVATE_ALARM", "");
    }
}