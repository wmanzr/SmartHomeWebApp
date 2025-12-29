package RUT.smart_home_command_service;

import RUT.smart_home_contract.api.thresholds.SensorThresholds;

class LightLevelCommandBuilder implements CommandBuilder {

    @Override
    public CommandData build(double lightLevel) {
        if (lightLevel > SensorThresholds.LIGHT_LEVEL_BRIGHT) {
            return new CommandData("CLOSE_BLINDS", "");
        }
        if (lightLevel < SensorThresholds.LIGHT_LEVEL_DARK) {
            return new CommandData("OPEN_BLINDS", "");
        }
        return new CommandData("OPEN_BLINDS", "");
    }
}