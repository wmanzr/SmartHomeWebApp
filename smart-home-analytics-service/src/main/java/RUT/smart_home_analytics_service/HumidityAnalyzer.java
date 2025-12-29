package RUT.smart_home_analytics_service;

import RUT.smart_home_contract.api.thresholds.SensorThresholds;

class HumidityAnalyzer implements SensorAnalyzer {

    @Override
    public boolean shouldExecuteCommand(double humidity) {
        return humidity > SensorThresholds.HUMIDITY_HIGH 
            || humidity < SensorThresholds.HUMIDITY_LOW;
    }
}