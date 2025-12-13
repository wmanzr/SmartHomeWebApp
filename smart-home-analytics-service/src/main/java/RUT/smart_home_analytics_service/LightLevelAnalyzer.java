package RUT.smart_home_analytics_service;

class LightLevelAnalyzer implements SensorAnalyzer {
    private static final double BRIGHT_THRESHOLD = 70.0;
    private static final double DARK_THRESHOLD = 30.0;

    @Override
    public boolean shouldExecuteCommand(double lightLevel) {
        return lightLevel > BRIGHT_THRESHOLD || lightLevel < DARK_THRESHOLD;
    }
}