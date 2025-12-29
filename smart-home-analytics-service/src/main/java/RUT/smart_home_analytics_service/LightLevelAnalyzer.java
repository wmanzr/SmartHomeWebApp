package RUT.smart_home_analytics_service;

import RUT.smart_home_contract.api.thresholds.SensorThresholds;

class LightLevelAnalyzer implements SensorAnalyzer {

    @Override
    public boolean shouldExecuteCommand(double lightLevel) {
        return lightLevel > SensorThresholds.LIGHT_LEVEL_BRIGHT 
            || lightLevel < SensorThresholds.LIGHT_LEVEL_DARK;
    }
}