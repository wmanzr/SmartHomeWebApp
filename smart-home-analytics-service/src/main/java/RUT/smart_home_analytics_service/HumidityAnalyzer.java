package RUT.smart_home_analytics_service;

class HumidityAnalyzer implements SensorAnalyzer {
    private static final double HIGH_THRESHOLD = 60.0;
    private static final double LOW_THRESHOLD = 40.0;

    @Override
    public Decision analyze(double humidity) {
        if (humidity > HIGH_THRESHOLD) {
            return Decision.execute("TURN_OFF");
        }

        if (humidity < LOW_THRESHOLD) {
            return Decision.execute("TURN_ON");
        }

        return Decision.noAction();
    }
}