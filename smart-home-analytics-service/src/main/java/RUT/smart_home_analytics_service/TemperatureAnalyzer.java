package RUT.smart_home_analytics_service;

class TemperatureAnalyzer implements SensorAnalyzer {
    private static final double HIGH_THRESHOLD = 25.0;
    private static final double LOW_THRESHOLD = 19.0;
    private static final double TARGET_COOL = 21.0;
    private static final double TARGET_HEAT = 22.0;

    @Override
    public Decision analyze(double temperature) {
        if (temperature > HIGH_THRESHOLD) {
            return Decision.execute("SET_TEMPERATURE", String.valueOf(TARGET_COOL));
        }

        if (temperature < LOW_THRESHOLD) {
            return Decision.execute("SET_TEMPERATURE", String.valueOf(TARGET_HEAT));
        }

        return Decision.execute("TURN_OFF");
    }
}