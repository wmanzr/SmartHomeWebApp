package RUT.smart_home_analytics_service;

class HumidityAnalyzer implements SensorAnalyzer {
    private static final double HIGH_THRESHOLD = 60.0;
    private static final double LOW_THRESHOLD = 40.0;

    @Override
    public boolean shouldExecuteCommand(double humidity) {
        return humidity > HIGH_THRESHOLD || humidity < LOW_THRESHOLD;
    }
}