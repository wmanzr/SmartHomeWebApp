package RUT.smart_home_analytics_service;

class LightLevelAnalyzer implements SensorAnalyzer {
    private static final double BRIGHT_THRESHOLD = 70.0;
    private static final double DARK_THRESHOLD = 30.0;

    @Override
    public Decision analyze(double lightLevel) {
        if (lightLevel > BRIGHT_THRESHOLD) {
            return Decision.execute("CLOSE_BLINDS");
        }

        if (lightLevel < DARK_THRESHOLD) {
            return Decision.execute("OPEN_BLINDS");
        }

        return Decision.noAction();
    }
}