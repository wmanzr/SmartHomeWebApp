package RUT.smart_home_analytics_service;

interface SensorAnalyzer {
    boolean shouldExecuteCommand(double value);
}