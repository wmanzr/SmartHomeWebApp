package RUT.smart_home_analytics_service;

import RUT.smart_home_contract.api.thresholds.SensorThresholds;

class TemperatureAnalyzer implements SensorAnalyzer {

    @Override
    public boolean shouldExecuteCommand(double temperature) {
        return temperature > SensorThresholds.TEMPERATURE_HIGH 
            || temperature < SensorThresholds.TEMPERATURE_LOW;
    }
}