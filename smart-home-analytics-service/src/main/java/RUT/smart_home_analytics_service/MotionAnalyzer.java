package RUT.smart_home_analytics_service;

class MotionAnalyzer implements SensorAnalyzer {
    private static final double MOTION_DETECTED = 1.0;

    @Override
    public Decision analyze(double motionValue) {
        if (motionValue >= MOTION_DETECTED) {
            return Decision.execute("ACTIVATE_ALARM");
        }

        return Decision.execute("DEACTIVATE_ALARM");
    }
}