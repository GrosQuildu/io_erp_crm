/*
package sample;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Locale;

public class ZamowienieDialog {
    private BorderPane mainPane = new BorderPane();
    private ZamowienieDialogKlient klientDialog = new ZamowienieDialogKlient(this);
    private ZamowienieKlientData klientData = new ZamowienieKlientData();
    private Button klientBtn = new Button("Klient");
    private Button towaryBtn = new Button("Towary");
    private Button terminUwagiBtn = new Button("Termin, uwagi");
    private Button podsumowanieBtn = new Button("Podsumowanie");
    private VBox sideBar = new VBox();
    private Button anulujBtn = new Button("Anuluj");
    private Button zapiszBtn = new Button("Zapisz");
    private HBox bottomBox = new HBox();
    private HBox topBox = new HBox();
    private Label nrZamowieniaLabel = new Label("Zamówienie numer ");
    private Label nrZamowieniaContentLabel = new Label();
    private Label dataZamowieniaLabel = new Label("Data zamówienia: ");
    private DatePicker dataZamowieniaPicker = new DatePicker();
    private Stage parent;
    private Boolean edit=false;
    private Order order = new Order();
    private ZamowienieDialogTowary towaryDialog = new ZamowienieDialogTowary(this, null);
    private ZamowienieDialogTermin terminDialog = new ZamowienieDialogTermin(this);
    private ZamowienieDialogPodsumowanie podsumowanieDialog = new ZamowienieDialogPodsumowanie(this, null);
    private Float nettoTowarow =0f;
    private ZamowieniaController zamowieniaController;
    public ZamowienieDialog(Stage parent, ZamowieniaController zamowieniaController, Boolean edit){
        this.parent = parent;
        this.zamowieniaController=zamowieniaController;
        this.edit = edit;
        parent.setTitle("Zamówienie");
        //mainPane.setPadding(new Insets(10));
        sideBar.getChildren().addAll(klientBtn, towaryBtn, terminUwagiBtn, podsumowanieBtn);
        sideBar.setSpacing(2);
        sideBar.setMinWidth(200);
        sideBar.setPrefWidth(200);
        mainPane.setLeft(sideBar);
        setCenter(Content.KLIENT);
        bottomBox.setSpacing(10);
        bottomBox.setPadding(new Insets(10));
        topBox.setSpacing(10);
        topBox.setPadding(new Insets(10));
        topBox.getChildren().addAll(nrZamowieniaLabel, nrZamowieniaContentLabel, dataZamowieniaLabel, dataZamowieniaPicker);
        bottomBox.getChildren().addAll(anulujBtn, zapiszBtn);
        mainPane.setTop(topBox);
        mainPane.setBottom(bottomBox);
        anulujBtn.setOnAction(e -> {
            parent.close();
            this.order =null;
        });
        zapiszBtn.setOnAction(e -> {
            //zapis
        });
        sideBar.getStyleClass().add("sidebar");
        klientBtn.getStyleClass().add("sidebarBtn");
        klientBtn.setPrefHeight(40);
        klientBtn.setMinHeight(40);
        towaryBtn.getStyleClass().add("sidebarBtn");
        towaryBtn.setPrefHeight(40);
        towaryBtn.setMinHeight(40);
        terminUwagiBtn.getStyleClass().add("sidebarBtn");
        terminUwagiBtn.setPrefHeight(40);
        terminUwagiBtn.setMinHeight(40);
        podsumowanieBtn.getStyleClass().add("sidebarBtn");
        podsumowanieBtn.setPrefHeight(40);
        podsumowanieBtn.setMinHeight(40);
        klientBtn.setMaxWidth(Double.MAX_VALUE);
        towaryBtn.setMaxWidth(Double.MAX_VALUE);
        terminUwagiBtn.setMaxWidth(Double.MAX_VALUE);
        podsumowanieBtn.setMaxWidth(Double.MAX_VALUE);

        klientBtn.setPrefHeight(25);
        towaryBtn.setPrefHeight(25);
        terminUwagiBtn.setPrefHeight(25);
        podsumowanieBtn.setPrefHeight(25);

        klientBtn.setMaxHeight(25);
        towaryBtn.setMaxHeight(25);
        terminUwagiBtn.setMaxHeight(25);
        podsumowanieBtn.setMaxHeight(25);

        mainPane.getStyleClass().add("backgroundElement");
        if(!edit){
            String nrZamowienia = generateNumerZamowienia();
            nrZamowieniaContentLabel.setText(nrZamowienia);
            order.setOrderNumber(nrZamowienia);
            dataZamowieniaPicker.setValue(LocalDate.now());
            order.setEmployeeId(Bridge.getImie()+" "+Bridge.getNazwisko());
            order.setCzyje(Bridge.getLogin());
        }

        try {
            nrZamowieniaContentLabel.setFont(Font.loadFont(new FileInputStream(new File("calibrib.ttf")), 16));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        setEvents();
    }

    public ZamowienieDialog(Stage parent, ZamowieniaController zamowieniaController, Order order){
        this(parent, zamowieniaController, true);
        this.order = order;
        mainPane.getStyleClass().add("mainBackground2");
        nrZamowieniaContentLabel.setText(order.getOrderNumber());
        dataZamowieniaPicker.setValue(order.getOrderDate().toLocalDate());
        podsumowanieDialog = new ZamowienieDialogPodsumowanie(this, order);
        klientData = new ZamowienieKlientData(); //pobranie z serwera zamiast konstruktora
        klientDialog = new ZamowienieDialogKlient(this, klientData);
        towaryDialog = new ZamowienieDialogTowary(this, order);
        terminDialog = new ZamowienieDialogTermin(this, order);
        nettoTowarow = order.getCenaNetto();
        podsumowanieDialog.update(order);
        setCenter(Content.KLIENT);}

    private enum Content {KLIENT, TOWARY, TERMIN, PODSUMOWANIE}

    public Boolean getEdit() {
        return edit;
    }


    private void setEvents() {
        klientBtn.setOnAction(e -> setCenter(Content.KLIENT));
        towaryBtn.setOnAction(e -> setCenter(Content.TOWARY));
        terminUwagiBtn.setOnAction(e -> setCenter(Content.TERMIN));
        podsumowanieBtn.setOnAction(e -> setCenter(Content.PODSUMOWANIE));
        dataZamowieniaPicker.setOnAction(e -> order.setOrderDate(Date.valueOf(dataZamowieniaPicker.getValue())));
    }
    private void setValues(){
        Float nettoPrev = 0f;
        for(OrderedArticle i : Arrays.asList(new OrderedArticle(), new OrderedArticle())){ //tu: lista pobrana z bazy
            nettoPrev+=i.getNetPrice();
        }
        nettoTowarow = nettoPrev;
        Float dostawa;
        if(nettoPrev<5000 && podsumowanieDialog.isAutoDostawa()) {
            dostawa = 0f; //pobranie stawki bazowej z serwera
            dostawa+=0f; // pobranie stawki wg zakresu wagi
            order.setAutoDostawa(true);
        } else dostawa=0f;
        order.setCenaNetto(nettoTowarow+dostawa);
        order.setCenaBrutto(Math.round((order.getCenaNetto()*100f+ order.getCenaNetto()* order.getVat()))/100f);
        order.setDeliveryCost(dostawa);
        if(order.getZaliczkaProcent().contains("%")){
            Float procent = Float.parseFloat(order.getZaliczkaProcent().replace("%",""))/100;
            order.setAdvance(procent* order.getCenaNetto()+procent* order.getCenaNetto()* order.getVat()/100);
        }
        podsumowanieDialog.update(order);
    }

    public void updatePodsumowanieData(String status, String zaplacono, Boolean autoDostawa, Boolean podpisane) {
        order.setState(status);
        order.setIsPaid(zaplacono);
        order.setAutoDostawa(autoDostawa);
        order.setIsSigned(podpisane);
        setValues();
        podsumowanieDialog.setOrder(order);
    }

    public void updateTowary(String towary, Float netto, Float waga){
        order.setTowary(towary);
        nettoTowarow = netto;
        order.setCenaNetto(netto);
        order.setWaga(waga);
        setValues();
        podsumowanieDialog.update(order);
    }
    public void updateTermin(String warunki, String uwagi,
                             String termin, Boolean isProcent,
                             String zaliczkaProcent, Float zaliczka, Float vat){
        order.setWarunkiPlatnosci(warunki);
        order.setComments(uwagi);
        order.setTerminRealizacji(termin);
        if(isProcent){
            order.setZaliczkaProcent(zaliczkaProcent);
            Float procent = Float.parseFloat(zaliczkaProcent.replace("%",""))/100;
            order.setAdvance(procent*nettoTowarow+procent*nettoTowarow*vat/100);
        } else {
            order.setZaliczkaProcent("0");
            order.setAdvance(zaliczka);
        }
        order.setVat(vat);
        setValues();
        podsumowanieDialog.update(order);
    }

    public void updateKlientData(ZamowienieKlientData zkd){
        order.setClientId(zkd.getClient().getId());
        order.setOdbiorcaZamowienia(zkd.getClient().getName());
        order.setDeliveryAddress(zkd.getInnyAdres());
        order.setInnyAdres(zkd.getIsInnyAdres());
        this.klientData = zkd;
    }
    public Order getOrder(){
        return order;
    }
    private void setCenter(Content content){
        klientBtn.getStyleClass().remove("active");
        towaryBtn.getStyleClass().remove("active");
        terminUwagiBtn.getStyleClass().remove("active");
        podsumowanieBtn.getStyleClass().remove("active");
        switch(content){
            case KLIENT:
                mainPane.setCenter(klientDialog.getMainBox());
                klientBtn.getStyleClass().add("active");
                break;
            case TOWARY:
                mainPane.setCenter(towaryDialog.getMainBox());
                towaryBtn.getStyleClass().add("active");
                break;
            case TERMIN:
                mainPane.setCenter(terminDialog.getMainBox());
                terminUwagiBtn.getStyleClass().add("active");
                break;
            case PODSUMOWANIE:
                mainPane.setCenter(podsumowanieDialog.getMainBox());
                podsumowanieBtn.getStyleClass().add("active");
                break;
        }
    }

    public BorderPane getMainPane(){
        return this.mainPane;
    }
    private String generateNumerZamowienia(){
        String pattern = "yyyyMMdd";
        StringBuilder sb = new StringBuilder();
        SimpleDateFormat formatter = new SimpleDateFormat(pattern, Locale.getDefault());
        String numer = formatter.format(Date.valueOf(LocalDate.now()));
        sb.append(numer);
        sb.append("/");
        sb.append(Bridge.getInicjaly());
        String prev = sb.toString();
        sb.append("1");
        String test = sb.toString();
        int count=2;
        int dupa = 3;
        while(dupa-->0){ //dopóki istnieje już taki numer zamowienia
            sb.delete(0,sb.length());
            sb.append(prev);
            sb.append(count);
            count++;
            test=sb.toString();
        }
        return sb.toString();
    }
}
*/
