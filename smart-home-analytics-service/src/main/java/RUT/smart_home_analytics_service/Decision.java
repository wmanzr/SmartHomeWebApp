package RUT.smart_home_analytics_service;

record Decision(boolean shouldExecute, String commandAction, String commandValue) {

    public static Decision execute(String action, String value) {
        return new Decision(true, action, value);
    }

    public static Decision execute(String action) {
        return new Decision(true, action, "");
    }

    public static Decision noAction() {
        return new Decision(false, "", "");
    }
}