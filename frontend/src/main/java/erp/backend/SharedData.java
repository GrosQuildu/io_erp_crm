package main.java.erp.backend;


import main.java.erp.backend.model.common.Employee;

public class SharedData {
    private static String mail;
    private static String initials;
    private static String imie;


    public static void setHaslo(String haslo) {
        SharedData.haslo = haslo;
    }

    private static String nazwisko;
    private static String phone;
    private static Boolean admin;
    private static String haslo;
    private static String mailServer;
    private static String mailAddress;
    private static String proformyPath = ".";
    private static String zamowieniaPath = ".";
    private static String skanPath = "";
    private static String protokolyPath = "";
    private static String dw="";

    public static String getNadawca() {
        return nadawca;
    }

    private static String nadawca="";

    public static String getDw() {
        return dw;
    }

    public static String getZamowieniaPath() {
        return zamowieniaPath;
    }

    public static void setZamowieniaPath(String zamowieniaPath) {
        SharedData.zamowieniaPath = zamowieniaPath;
    }

    public static String getProformyPath() {
        return proformyPath;
    }

    public static void setProformyPath(String proformyPath) {
        SharedData.proformyPath = proformyPath;
    }

    public static String getMailServer() {
        return mailServer;
    }


    public static String getMailAddress() {
        return mailAddress;
    }

    public static void setMailAddress(String mailAddress) {
        SharedData.mailAddress = mailAddress;
    }

    public static String getMailPass() {
        return mailPass;
    }

    public static void setMailPass(String mailPass) {
        SharedData.mailPass = mailPass;
    }

    public static String getMailPort() {
        return mailPort;
    }

    public static void setMailPort(String mailPort) {
        SharedData.mailPort = mailPort;
    }

    private static String mailPass;
    private static String mailPort;

    public static void update(Employee employee){
        mail = employee.getMail();
        imie= employee.getName();
        phone= employee.getTelephone();
        admin= false ; //TODO: ADMIN/uzytkownik
        initials = employee.getName().split(" ")[0].substring(0,1)+ (employee.getName().split(",").length>1 ? employee.getName().split(" ")[1].substring(0,1) : "");
        haslo= employee.getPassword();
    }

    public static String getImie() {
        return imie;
    }


    public static String getNazwisko() {
        return nazwisko;
    }


    public static String getTelefon() {
        return phone;
    }


    public static void setMail(String l){
        mail =l;
    }
    public static String getMail(){
        return mail;
    }
    public static String getInitials() {
        return initials;
    }

    public static Boolean getAdmin() {
        return admin;
    }

    public static String getHaslo() {
        return haslo;
    }


    public static void setSkanPath(String skanPath) {
        SharedData.skanPath = skanPath;
    }

    public static String getSkanPath() {
        return skanPath;
    }


    public static void setProtokolyPath(String protokolyPath) {
        SharedData.protokolyPath = protokolyPath;
    }

    public static void setDw(String dw) {
        SharedData.dw = dw;
    }

    public static void setNadawca(String nadawca) {
        SharedData.nadawca = nadawca;
    }
}
