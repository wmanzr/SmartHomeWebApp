package RUT.smart_home_command_service;

import RUT.smart_home_contract.api.thresholds.SensorThresholds;

class HumidityCommandBuilder implements CommandBuilder {

    @Override
    public CommandData build(double humidity) {
        if (humidity > SensorThresholds.HUMIDITY_HIGH) {
            return new CommandData("TURN_OFF", "");
        }
        return new CommandData("TURN_ON", "");
    }
}