/*
package sample;

import javafx.geometry.Insets;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;

*/
/**
 * Created by marcin on 26.02.17.
 *//*

public class ZamowienieDialogPodsumowanie {
    private Label zaplaconoLabel = new Label("Zapłacono");
    private Label statusLabel = new Label("Status");
    private ComboBox<String> zaplaconoCombo = new ComboBox<>();
    private ComboBox<String> statusCombo = new ComboBox();
    private CheckBox podpisaneCheck = new CheckBox("Podpisane przez Klienta");
    private CheckBox autoDostawaCheck = new CheckBox("Automatyczne naliczanie dostawy");
    private VBox mainBox = new VBox();
    private GridPane topPane = new GridPane();
    private WebView podsumowanieView = new WebView();
    private Order order = new Order();
    private ZamowienieDialog parent;
    private Boolean edit=false;
    public ZamowienieDialogPodsumowanie(ZamowienieDialog parent, Order order){
        this.parent = parent;
        mainBox.setPadding(new Insets(10));
        mainBox.setSpacing(30);
        topPane.setHgap(10);
        topPane.setVgap(20);
        topPane.add(zaplaconoLabel, 0, 0);
        topPane.add(zaplaconoCombo, 1, 0);
        topPane.add(statusLabel, 2, 0);
        topPane.add(statusCombo, 3, 0);
        topPane.add(podpisaneCheck, 0, 1);
        topPane.add(autoDostawaCheck, 2, 1);
        GridPane.setColumnSpan(podpisaneCheck, 2);
        GridPane.setColumnSpan(autoDostawaCheck, 2);
        mainBox.getChildren().addAll(topPane, podsumowanieView);
        autoDostawaCheck.setOnAction(e -> updateParent());
        zaplaconoCombo.getItems().addAll("Nie","Zaliczka","Całość");
        zaplaconoCombo.setValue("Nie");
        zaplaconoCombo.setOnAction(e -> updateParent());
        statusCombo.setOnAction(e -> updateParent());
        podpisaneCheck.setOnAction(e -> updateParent());
        if(order !=null){
            edit=true;
            this.order = order;
            statusCombo.setValue(order.getState());
            podpisaneCheck.setSelected(order.getIsSigned());
            zaplaconoCombo.setValue(order.getIsPaid());
        } else {
            autoDostawaCheck.setSelected(true);

        }
    }

    public VBox getMainBox(){
        return this.mainBox;
    }
    public void update(Order order){
        this.order = order;
        if(edit){
            statusCombo.setValue(order.getState());
            zaplaconoCombo.setValue(order.getIsPaid());
        }
        buildHtml();
    }
    public Boolean isAutoDostawa(){
        return autoDostawaCheck.isSelected();
    }
    private void buildHtml(){
        String html = "<!DOCTYPE html><html><head><meta charset='UTF-8'>" +
                "<title>Podgląd zamówienia</title></head><body>" +
                "<style>" +
                "@font-face {font-family: 'Calibri Bold'; src: url('calibrib.ttf') format('truetype');} " +
                "body{font-family: 'Calibri Bold', sans-serif;} h2{font-size:14px; color:#284B63;} " +
                "p{font-size: 12px; color: #454545;} " +
                "</style>";
        html = html + h2("Podsumowanie");
        html = html + boldRegular("Waga:",Utils.formatKg(order.getWaga()));
        html = html + boldRegular("Dostawa (netto):", Utils.formatPln(order.getDeliveryCost()));
        html = html + boldRegular("Netto:", Utils.formatPln(1f)); //toDo: suma cen towarów
        html = html + boldRegular("Vat:", order.getVat().toString()+"%");
        html = html + boldRegular("Zaliczka (brutto):",Utils.formatPln(order.getAdvance()));
        html = html + h2("Brutto: "+Utils.formatPln(*/
/*order.getCenaNetto()+ order.getCenaNetto()* order.getVat()/100f*//*
 1f)); //toDo: przeliczenie
        html = html + "</body></html>";
        podsumowanieView.getEngine().loadContent(html);
    }
    private String h2(String s){
        return "<h2>"+s+"</h2>";
    }
    private String boldRegular(String bold, String reg){
        return "<p><b>"+bold+"</b> "+reg+"</p>";
    }
    private void updateParent(){
        parent.updatePodsumowanieData(statusCombo.getValue(),zaplaconoCombo.getValue(), autoDostawaCheck.isSelected(), podpisaneCheck.isSelected());
    }

    public void setOrder(Order order) {
        this.order = order;
        buildHtml();
    }
}
*/
