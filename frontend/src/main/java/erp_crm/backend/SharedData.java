package main.java.erp_crm.backend;


import main.java.erp_crm.backend.api.Config;

public class SharedData {

    private static volatile Config config = new Config(true);

    public static Config getConfig() {
        return config;
    }

    public static void setConfig(Config config) {
        SharedData.config = config;
    }



}
