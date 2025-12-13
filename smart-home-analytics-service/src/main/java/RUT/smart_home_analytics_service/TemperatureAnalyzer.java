package RUT.smart_home_analytics_service;

class TemperatureAnalyzer implements SensorAnalyzer {
    private static final double HIGH_THRESHOLD = 25.0;
    private static final double LOW_THRESHOLD = 19.0;

    @Override
    public boolean shouldExecuteCommand(double temperature) {
        return temperature > HIGH_THRESHOLD ||
                temperature < LOW_THRESHOLD ||
                (temperature >= LOW_THRESHOLD && temperature <= HIGH_THRESHOLD);
    }
}