package main.java.erp;


import main.java.erp.backend.model.common.Client;

public class ZamowienieKlientData {
    private Client client = null;
    private String innyAdres = "";
    private Boolean isInnyAdres = false;
    public ZamowienieKlientData(){

    }
    public ZamowienieKlientData(Client client, String innyAdres, Boolean isInnyAdres){
        this.client = client;
        this.innyAdres = innyAdres;
        this.isInnyAdres = isInnyAdres;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public String getInnyAdres() {
        return innyAdres;
    }

    public void setInnyAdres(String innyAdres) {
        this.innyAdres = innyAdres;
    }

    public Boolean getIsInnyAdres() {
        return isInnyAdres;
    }

    public void setIsInnyAdres(Boolean isInnyAdres) {
        this.isInnyAdres = isInnyAdres;
    }
}
