package RUT.smart_home_command_service;

import RUT.smart_home_contract.api.thresholds.SensorThresholds;

class TemperatureCommandBuilder implements CommandBuilder {

    @Override
    public CommandData build(double temperature) {
        if (temperature > SensorThresholds.TEMPERATURE_HIGH) {
            return new CommandData("SET_TEMPERATURE", "21.0");
        }
        if (temperature < SensorThresholds.TEMPERATURE_LOW) {
            return new CommandData("SET_TEMPERATURE", "22.0");
        }
        return new CommandData("TURN_OFF", "");
    }
}