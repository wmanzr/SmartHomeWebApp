package RUT.smart_home_analytics_service;

class MotionAnalyzer implements SensorAnalyzer {
    @Override
    public boolean shouldExecuteCommand(double motionValue) {
        return true;
    }
}