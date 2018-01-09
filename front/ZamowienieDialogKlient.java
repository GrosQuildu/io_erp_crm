/*
package sample;


import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;

import java.util.concurrent.ExecutionException;

public class ZamowienieDialogKlient {
    private VBox mainBox = new VBox();
    private WebView szczegolyView = new WebView();
    private CheckBox innyAdresBox = new CheckBox("Inny adres");
    private TextArea innyAdresArea = new TextArea("");
    private Client client = new Client();
    private ZamowienieDialog parent;
    public ZamowienieDialogKlient(ZamowienieDialog parent){
        this.parent = parent;
        mainBox.setSpacing(10);
        mainBox.setPadding(new Insets(10));
        HBox upperBox = new HBox();
        upperBox.setSpacing(10);
        Button nowyOdbiorcaBtn = new Button("Nowy odbiorca");
        Button odbiorcaZBazyBtn = new Button("Odbiorca z bazy");
        upperBox.getChildren().addAll(nowyOdbiorcaBtn, odbiorcaZBazyBtn);
        HBox bottomBox = new HBox();
        bottomBox.setSpacing(10);
        Label adresDostawyLabel = new Label("Adres dostawy");
        bottomBox.getChildren().addAll(adresDostawyLabel, innyAdresBox);
        innyAdresArea.setEditable(false);
        innyAdresArea.setMinHeight(100);
        innyAdresBox.setSelected(false);
        innyAdresArea.setOnKeyReleased(e -> updateParent());
        mainBox.getChildren().addAll(upperBox,szczegolyView,bottomBox,innyAdresArea);
        nowyOdbiorcaBtn.setOnAction(e -> generatePopupDodajKlienta());
        odbiorcaZBazyBtn.setOnAction(e -> dodajOdbiorceZBazy());
        innyAdresBox.setOnAction(e -> {
            innyAdresArea.setEditable(innyAdresBox.isSelected());
            if(!innyAdresBox.isSelected()){
                innyAdresArea.setText(getKlientAdres());
            }
            updateParent();
        });
    }

    public ZamowienieDialogKlient(ZamowienieDialog parent, ZamowienieKlientData zkd){
        this(parent);
        if(zkd.getIsInnyAdres()){
            innyAdresBox.setSelected(true);
            innyAdresArea.setText(zkd.getInnyAdres());
        }
        client = zkd.getClient();
        dodajDoZamowienia();
    }
    public VBox getMainBox(){
        return this.mainBox;
    }

    private void updateParent(){
        parent.updateKlientData(new ZamowienieKlientData(client, innyAdresArea.getText(), innyAdresBox.isSelected()));
    }
    private void generatePopupDodajKlienta() {
        //popup - dodawanie klienta
    }

    private void dodajDoZamowienia(){
        String head="<!DOCTYPE html><html><head><meta charset='UTF-8'>" +
                "<title>Podgląd zamówienia</title></head>" +
                "<body>" +
                "<style>" +
                "@font-face {font-family: 'Calibri Bold'; src: url('calibrib.ttf') format('truetype');} " +
                "body{font-family: 'Calibri Bold', sans-serif;} " +
                "h2{font-size:14px; color:#284B63;} p{font-size: 12px; color: #454545;} " +
                "</style>";
        String klientDetails = "<h2>Dane ogólne</h2>" +
                "<p>Nip: "+ client.getNip()+
                "<br>Mail: "+ client.getMail()+
                "</p><h2>Dane do faktury</h2>"+
                "<p><b>Nazwa: </b>" + client.getName() + "<br>"+
                "<b>Adres: </b><br>" + client.getStreet() +
                ",<br>" + client.getPostCode() + " "+
                client.getCity() + "</p><p><b>Typ klienta: " + client.getClientType()+"</b></p>";
        szczegolyView.getEngine().loadContent(head + klientDetails + "</body></html>");
        if(!innyAdresBox.isSelected())
            innyAdresArea.setText(getKlientAdres());
        updateParent();
    }
    private String getKlientAdres() {
        return client.getNameDelivery()+
                "\n"+ client.getStreetDelivery()+
                ", "+ client.getPostCodeDelivery()+
                " "+ client.getCityDelivery();
    }
    private void dodajOdbiorceZBazy() {
        //wg nazwy
    }

    private void findKlient(String s, ObservableList<Client> klienciTable, ObservableList<Client> klienci) throws ExecutionException, InterruptedException {
        //wyszukiwanie
    }

}
*/
