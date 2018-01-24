package main.java.erp_crm.backend.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import main.java.erp_crm.backend.SharedData;
import main.java.erp_crm.backend.model.erp.Order;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

//TODO: USTAWIENIA -> zapis/odczyt do/z configa
public class Config {

    //connection
    private String address="";
    private String port="";

    //profile
    private String employeeFirstName="";
    private String employeeName="";
    private String employeeMail="";
    private String employeeTelephone="";
    private String employeePassword="";
    private String signatureScanPath="";

    //mail
    private String mailServerAddress="";
    private String mailPort="";
    private String mailAddress="";
    private String mailPassword="";
    private String defaultCC="";
    private String defaultSenderName="";

    //default values
    private String ordersConditions="";
    private String ordersComments="";
    private String mailContent="";
    private String defaultOrderPdfPath="";
    private String defaultProformaPdfPath="";
    private String bank="";
    private String providerData="";
    private String companyLogoPath="";


    private Config(){

    }
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

    public String getEmployeeFirstName() {
        return employeeFirstName;
    }

    public void setEmployeeFirstName(String employeeFirstName) {
        this.employeeFirstName = employeeFirstName;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getEmployeeMail() {
        return employeeMail;
    }

    public void setEmployeeMail(String employeeMail) {
        this.employeeMail = employeeMail;
    }

    public String getEmployeeTelephone() {
        return employeeTelephone;
    }

    public void setEmployeeTelephone(String employeeTelephone) {
        this.employeeTelephone = employeeTelephone;
    }

    public String getEmployeePassword() {
        return employeePassword;
    }

    public void setEmployeePassword(String employeePassword) {
        this.employeePassword = employeePassword;
    }

    public String getSignatureScanPath() {
        return signatureScanPath;
    }

    public void setSignatureScanPath(String signatureScanPath) {
        this.signatureScanPath = signatureScanPath;
    }

    public String getMailServerAddress() {
        return mailServerAddress;
    }

    public void setMailServerAddress(String mailServerAddress) {
        this.mailServerAddress = mailServerAddress;
    }

    public String getMailPort() {
        return mailPort;
    }

    public void setMailPort(String mailPort) {
        this.mailPort = mailPort;
    }

    public String getMailAddress() {
        return mailAddress;
    }

    public void setMailAddress(String mailAddress) {
        this.mailAddress = mailAddress;
    }

    public String getMailPassword() {
        return mailPassword;
    }

    public void setMailPassword(String mailPassword) {
        this.mailPassword = mailPassword;
    }

    public String getDefaultCC() {
        return defaultCC;
    }

    public void setDefaultCC(String defaultCC) {
        this.defaultCC = defaultCC;
    }

    public String getDefaultSenderName() {
        return defaultSenderName;
    }

    public void setDefaultSenderName(String defaultSenderName) {
        this.defaultSenderName = defaultSenderName;
    }

    public String getOrdersConditions() {
        return ordersConditions;
    }

    public void setOrdersConditions(String ordersConditions) {
        this.ordersConditions = ordersConditions;
    }

    public String getOrdersComments() {
        return ordersComments;
    }

    public void setOrdersComments(String ordersComments) {
        this.ordersComments = ordersComments;
    }

    public String getMailContent() {
        return mailContent;
    }

    public void setMailContent(String mailContent) {
        this.mailContent = mailContent;
    }

    public String getDefaultOrderPdfPath() {
        return defaultOrderPdfPath;
    }

    public void setDefaultOrderPdfPath(String defaultOrderPdfPath) {
        this.defaultOrderPdfPath = defaultOrderPdfPath;
    }

    public String getDefaultProformaPdfPath() {
        return defaultProformaPdfPath;
    }

    public void setDefaultProformaPdfPath(String defaultProformaPdfPath) {
        this.defaultProformaPdfPath = defaultProformaPdfPath;
    }

    public String getCompanyLogoPath() {
        return companyLogoPath;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getProviderData() {
        return providerData;
    }

    public void setProviderData(String providerData) {
        this.providerData = providerData;
    }

    public void saveConfig(Config config){
        try {
            saveToFile(config);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveToFile(Config config) throws IOException {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setPrettyPrinting();
        final Gson gson = gsonBuilder.create();


        String jsonObject = gson.toJson(config);


        RandomAccessFile stream = new RandomAccessFile("config.json", "rw");
        FileChannel channel = stream.getChannel();
        byte[] strBytes = jsonObject.getBytes();
        ByteBuffer buffer = ByteBuffer.allocate(strBytes.length);
        buffer.put(strBytes);
        buffer.flip();
        channel.write(buffer);
        stream.close();
        channel.close();
    }

    private Config(Config config){
        //connection
        this.address = config.getAddress();
        this.port = config.getPort();

        //profile
        this.employeeFirstName = config.getEmployeeFirstName();
        this.employeeName = config.getEmployeeName();
        this.employeeMail = config.getEmployeeMail();
        this.employeeTelephone = config.getEmployeeTelephone();
        this.employeePassword = config.getEmployeePassword();
        this.signatureScanPath = config.getSignatureScanPath();

        //mail
        this.mailServerAddress = config.getMailServerAddress();
        this.mailPort = config.getMailPort();
        this.mailAddress = config.getMailAddress();
        this.mailPassword = config.getMailPassword();
        this.defaultCC = config.getDefaultCC();
        this.defaultSenderName = config.getDefaultSenderName();

        //default values
        this.ordersConditions = config.getOrdersConditions();
        this.ordersComments = config.getOrdersComments();
        this.mailContent = config.getMailContent();
        this.defaultOrderPdfPath = config.getDefaultOrderPdfPath();
        this.defaultProformaPdfPath = config.getDefaultProformaPdfPath();
        this.bank = config.getBank();
        this.companyLogoPath = config.getCompanyLogoPath();
        this.providerData = config.getProviderData();


    }

    public void setCompanyLogoPath(String companyLogoPath) {
        this.companyLogoPath = companyLogoPath;
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
