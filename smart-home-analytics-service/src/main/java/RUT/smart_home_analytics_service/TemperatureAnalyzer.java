package RUT.smart_home_analytics_service;

class TemperatureAnalyzer implements SensorAnalyzer {
    @Override
    public boolean shouldExecuteCommand(double temperature) {
        return true;
    }
}