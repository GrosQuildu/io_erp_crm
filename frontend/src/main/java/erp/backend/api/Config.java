package main.java.erp.backend.api;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Config {
    private String address;
    private String port;
    public Config(){}
    public Config(boolean init){
        ConfigReader reader = new ConfigReader();
        Config config = reader.readConfig();
        address = config.getAddress();
        port = config.getPort();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }
    class ConfigReader {
        Config readConfig(){
            Config config = null;
            Gson gson = new Gson();
            try (JsonReader reader = new JsonReader(new FileReader(new File("config.json")))) {
                config = gson.fromJson(reader, Config.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return config;
        }
    }

}
