package main.java.erp.frontend.orders;

import com.lowagie.text.DocumentException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import main.java.erp.backend.SharedData;
import main.java.erp.backend.Utils;
import main.java.erp.backend.api.erp.OrdersApiController;
import main.java.erp.backend.model.common.Client;
import main.java.erp.backend.model.erp.Order;
import main.java.erp.backend.model.erp.OrderedArticle;
import main.java.erp.backend.model.erp.Proforma;
import main.java.erp.backend.pdf.PdfGenerator;
import main.java.erp.frontend.loading.LoadingDialog;
import main.java.erp.frontend.proformas.AddEditProformaController;
import main.java.erp.frontend.settings.SettingsController;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;


public class OrdersController implements Initializable {
    public VBox ordersBox;
    public TableView<Order> orderTable;
    public TableColumn<Order, String> orderNumberColumn;
    public TableColumn<Order, String> clientColumn;
    public TableColumn<Order, Boolean> signedColumn;
    public TableColumn<Order, String> realizationDeadlineColumn;
    public TableColumn<Order, Date> dateColumn;
    public TableColumn<Order, String> stateColumn;
    public TableColumn<Order, String> paidColumn;
    public Button addBtn, editBtn, deleteBtn, exportBtn, proformaBtn;
    public Button sendBtn;
    public TextField findField;
    public Button findBtn;
    public Button xBtn;
    public Button previewBtn, refreshBtn;
    private AddEditProformaController addEditProformaController;
    private OrdersApiController ordersApiController = new OrdersApiController();

    public void refresh() {
        updateTable();
    }

    private enum CurrentScene {DATE_RECEIVER, CONDITIONS_DEADLINE}

    private WebView previewView = new WebView();
    private CheckBox autoDeliveryCost = new CheckBox("Automatic delivery costs calculation");
    private Label advanceLabel = new Label("Advance");
    private RadioButton halfBtn = new RadioButton();
    private RadioButton fullBtn = new RadioButton();
    private RadioButton otherBtn = new RadioButton();
    private RadioButton percentBtn = new RadioButton();
    private RadioButton zl = new RadioButton();
    private Label nettoLabel = new Label("");
    private Label bruttoLabel = new Label("");
    private Label orderNumberContent = new Label("");
    private TextField vatField = new TextField();
    private VBox deliveryCostBox = new VBox();
    private Label deliveryCostContentLabel = new Label("");
    private ListView<String> receiverList = new ListView<>();
    private ObservableList<Order> data = FXCollections.observableArrayList();
    private ObservableList<OrderedArticle> articlesList = FXCollections.observableArrayList();
    private Client client;
    private WebView receiverView = new WebView();
    private WebView summarizeView = new WebView();
    private TextArea addressArea = new TextArea();
    private CurrentScene current=CurrentScene.DATE_RECEIVER;
    private TableView<OrderedArticle> articleTableView = new TableView<>();
    private TextField advanceField = new TextField();
    private Float brutto=0f, netto=0f,vat=23f, advance =0f, deliveryCost =0f, weight =0f;

    private AddEditOrderController addEditOrderController;
    private SendMailController sendMailController;
    private OrderPreviewController orderPreviewController;
    private FXMLLoader loader;

    public OrdersController() {
        try {
            loader = new FXMLLoader(getClass().getResource("/fxmlFiles/addEditOrder.fxml"));
            loader.load();
            addEditOrderController = loader.getController();
            addEditOrderController.setOrdersController(this);

            loader = new FXMLLoader(getClass().getResource("/fxmlFiles/sendMail.fxml"));
            loader.load();
            sendMailController = loader.getController();
            sendMailController.setOrdersController(this);

            loader = new FXMLLoader(getClass().getResource("/fxmlFiles/orderPreview.fxml"));
            loader.load();
            orderPreviewController = loader.getController();
            sendMailController.setOrdersController(this);

            loader = new FXMLLoader(getClass().getResource("/fxmlFiles/addEditProforma.fxml"));
            loader.load();
            addEditProformaController = loader.getController();
            addEditProformaController.setOrdersController(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        autoDeliveryCost.setSelected(true);
        setColumns();
        bruttoLabel.setStyle("-fx-text-fill: #22cc22;");
        previewView.setPrefHeight(300);
        previewView.setMaxHeight(Double.MAX_VALUE);
        updateTable();
        //addBtn.setOnAction(e-> generatePopup(false));
        setEvents();
    }

    private void setColumns() {
        orderNumberColumn.setCellValueFactory(new PropertyValueFactory<>("orderNumber"));
        clientColumn.setCellValueFactory(new PropertyValueFactory<>("client"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("orderDate"));
        stateColumn.setCellValueFactory(new PropertyValueFactory<>("state"));
        realizationDeadlineColumn.setCellValueFactory(new PropertyValueFactory<>("realizationDeadline"));
        signedColumn.setCellValueFactory(new PropertyValueFactory<>("isSigned"));
        paidColumn.setCellValueFactory(new PropertyValueFactory<>("isPaid"));
    }

    private void setEvents() {
        addBtn.setOnAction(e-> {
            addEditOrderController.show();
        });
        editBtn.setOnAction(e -> {
            Order item = orderTable.getSelectionModel().getSelectedItem();
            if(item!=null){
                addEditOrderController.show(item);
            }
        });
        refreshBtn.setOnAction(e -> {
            updateTable();
        });
        deleteBtn.setOnAction(e -> {
            //usuwanie zamówienia
            Order toDelete = orderTable.getSelectionModel().getSelectedItem();
            if(toDelete != null) {
                ordersApiController.deleteOrder(toDelete.getId());
                updateTable();
            }
        });

        proformaBtn.setOnAction(e -> {
            addEditProformaController.show(orderTable.getSelectionModel().getSelectedItem());
        });
        sendBtn.setOnAction(e -> {
            sendMailController.show(orderTable.getSelectionModel().getSelectedItem());
        });
        exportBtn.setOnAction(e -> prepareExport());

        findBtn.setOnAction(e -> find(findField.getText().isEmpty() ? "" : findField.getText()));
        xBtn.setOnAction(e -> {
            findField.clear();
            find("");
        });
        findField.setOnKeyReleased(e -> {
            if(e.getCode() == KeyCode.ENTER) find(findField.getText().isEmpty() ? "" : findField.getText());
        });

        previewBtn.setOnAction(e -> {
            if(!orderTable.getSelectionModel().isEmpty())
                orderPreviewController.show(orderTable.getSelectionModel().getSelectedItem());
        });

        orderTable.setOnMouseClicked(event -> {
            if(event.getClickCount() == 2){
                orderPreviewController.show(orderTable.getSelectionModel().getSelectedItem());
            }
        });
    }



    private void generatePopupAddProforma() {
        Label dataSprzedazyLabel = new Label("Data sprzedaży");
        Label dataWystawieniaLabel = new Label("Data wystawienia");
        Label terminZaplatyLabel = new Label("Termin zapłaty");
        Label sposobZaplatyLabel = new Label("Sposób zapłaty");
        DatePicker dataSprzedazyPicker=new DatePicker();
        DatePicker dataWystawieniaPicker=new DatePicker();
        DatePicker terminZaplatyPicker=new DatePicker();
        dataSprzedazyPicker.setValue(LocalDate.now());
        dataWystawieniaPicker.setValue(LocalDate.now());
        terminZaplatyPicker.setValue(LocalDate.now());
        ComboBox<String> sposobZaplatyBox=new ComboBox<>();
        sposobZaplatyBox.getItems().addAll("Przelew", "Gotówka","Rekompensata");
        sposobZaplatyBox.getSelectionModel().select("Przelew");
        Button zatwierdzBtn = new Button("Zatwierdź");
        Button anulujBtn = new Button("Anuluj");
        Stage st=new Stage(StageStyle.DECORATED);
        GridPane main = new GridPane();
        main.setPadding(new Insets(10));
        main.setHgap(10);
        main.setVgap(10);
        main.add(dataSprzedazyLabel,0,0);
        main.add(dataSprzedazyPicker,1,0);
        main.add(dataWystawieniaLabel,0,1);
        main.add(dataWystawieniaPicker,1,1);
        main.add(terminZaplatyLabel,0,2);
        main.add(terminZaplatyPicker,1,2);
        main.add(sposobZaplatyLabel,0,3);
        main.add(sposobZaplatyBox,1,3);
        main.add(zatwierdzBtn,0,4);
        main.add(anulujBtn,1,4);
        main.setPrefHeight(250);
        main.setPrefWidth(350);
        Scene sc = new Scene(main);
        sc.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
        st.setScene(sc);
        st.setTitle("Generowanie proformy");
        st.show();
        anulujBtn.setOnAction(ev -> st.close());
        zatwierdzBtn.setOnAction(ev -> {
            if(!orderTable.getSelectionModel().isEmpty()){
            Proforma p = new Proforma();
                Order tmp = new Order(); //pobranie z bazy wg id
                p.setSaleDate(new org.joda.time.LocalDate(dataSprzedazyPicker.getValue()));
                p.setIssueDate(new org.joda.time.LocalDate(dataWystawieniaPicker.getValue()));
                p.setPaymentMethod(sposobZaplatyBox.getValue());
                p.setPaymentDate(new org.joda.time.LocalDate(terminZaplatyPicker.getValue()));
                p.setOrder(tmp);
                p.setProformaNumber(OrderUtils.generateProformaNumber(tmp.getOrderNumber()));
                //zapis proformy


            st.close();
            Alert al = new Alert(Alert.AlertType.CONFIRMATION);
            al.setTitle("Eksport do PDF");
            al.setHeaderText(null);
            al.setContentText("Czy wyeksportować proformę nr "+p.getProformaNumber()+" do pliku PDF?");
            al.getDialogPane().getStylesheets().add(getClass().getResource("style.css").toExternalForm());
            Optional<ButtonType> res = al.showAndWait();
            if(res.get()==ButtonType.OK){
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setHeaderText(null);
                ButtonType btKoncowa = new ButtonType("Końcowa");
                ButtonType btZaliczkowa = new ButtonType("Zaliczkowa");
                alert.getButtonTypes().setAll(btKoncowa,btZaliczkowa);
                alert.getDialogPane().getStylesheets().add(getClass().getResource("style.css").toExternalForm());
                alert.setContentText("Wybierz rodzaj proformy");
                Optional<ButtonType> result = alert.showAndWait();
                if(result.get()==btKoncowa){
                    FileChooser fc = new FileChooser();
                    fc.setTitle("Eksport proformy");
                    fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF", "*.pdf"));
                    String numerZamowienia=tmp.getOrderNumber();
                    fc.setInitialFileName( "Proforma Vesstige dla "+orderTable.getSelectionModel().getSelectedItem().getClient().getName().replace("&"," and ")+" nr "+p.getProformaNumber().replace("/","-")+".pdf");
                    fc.setInitialDirectory(new File(SharedData.getProformyPath().isEmpty()?".": SharedData.getProformyPath()));
                    Stage fStage = new Stage(StageStyle.DECORATED);
                    fStage.setMaximized(false);
                    File file = fc.showSaveDialog(fStage);

                    LoadingDialog ld = new LoadingDialog("Eksportuję do pdf");
                    Task<Boolean> eksportTask = new Task<Boolean>() {
                        @Override
                        protected Boolean call() throws Exception {
                            if (file != null)
                                try {
                                    return PdfGenerator.proforma(numerZamowienia, file.toPath().toString(), true);
                                } catch (IOException | DocumentException e1) {
                                    e1.printStackTrace();
                                }
                            return true;
                        }
                    };
                    eksportTask.setOnRunning(eve -> ld.show());
                    eksportTask.setOnSucceeded(eve -> {
                        ld.close();
                        updateTable();
                    });
                    new Thread(eksportTask).start();
                } else if(result.get()==btZaliczkowa){
                    FileChooser fc = new FileChooser();
                    fc.setTitle("Eksport proformy");
                    fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF", "*.pdf"));
                    String numerZamowienia=tmp.getOrderNumber();
                    fc.setInitialFileName( "Proforma zaliczkowa Vesstige dla "+orderTable.getSelectionModel().getSelectedItem().getClient().getName().replace("&"," and ")+" nr "+p.getProformaNumber().replace("/","-")+".pdf");
                    fc.setInitialDirectory(new File(SharedData.getProformyPath().isEmpty()?".": SharedData.getProformyPath()));
                    Stage fStage = new Stage(StageStyle.DECORATED);
                    fStage.setMaximized(false);
                    File file = fc.showSaveDialog(fStage);

                    LoadingDialog ld = new LoadingDialog("Eksportuję do pdf");
                    Task<Boolean> eksportTask = new Task<Boolean>() {
                        @Override
                        protected Boolean call() throws Exception {
                            if (file != null)
                                try {
                                    return PdfGenerator.proforma(numerZamowienia, file.toPath().toString(), true);
                                } catch (IOException | DocumentException e1) {
                                    e1.printStackTrace();
                                }
                            return true;
                        }
                    };
                    eksportTask.setOnRunning(eve -> ld.show());
                    eksportTask.setOnSucceeded(eve -> ld.close());
                    new Thread(eksportTask).start();

                }
            }
            }
        });
    }

    private void prepareExport() {
        if(!orderTable.getSelectionModel().isEmpty()){
            FileChooser fc = new FileChooser();
            fc.setTitle("Eksport zamówienia");
            fc.setInitialDirectory(new File(SharedData.getZamowieniaPath().isEmpty()?".": SharedData.getZamowieniaPath()));
            fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF", "*.pdf"));
            String numerZamowienia= orderTable.getSelectionModel().getSelectedItem().getOrderNumber();
            /*Client client = connection.getClient(zamowieniaTable.getSelectionModel().getSelectedItem().getClientId());
            fc.setInitialFileName("Zamówienie Vesstige dla "+client.getName().replace("&", " and ")+" nr "+numerZamowienia.replace("/","-")+".pdf");
            */
            Stage fStage = new Stage(StageStyle.DECORATED);
            fStage.setMaximized(false);
            File file = fc.showSaveDialog(fStage);

            LoadingDialog ld = new LoadingDialog("Eksportuję do pdf");
            Task<Boolean> eksportTask = new Task<Boolean>() {
                @Override
                protected Boolean call() throws Exception {
                    if (file != null)
                        try {
                            return PdfGenerator.order(numerZamowienia,file.toPath().toString(), true);
                        } catch (IOException | DocumentException e1) {
                            e1.printStackTrace();
                        }
                    return true;
                }
            };
            eksportTask.setOnRunning(eve -> ld.show());
            eksportTask.setOnSucceeded(eve -> {
                ld.close();
                updateTable();
            });
            new Thread(eksportTask).start();

        }
    }


    //HTML używany przy pokazywaniu podglądu zamówienia w WebView
    private void buildHtmlSummary() {
        String html = "<!DOCTYPE html><html><head><meta charset='UTF-8'><title>Podgląd zamówienia</title></head><body><style>@font-face {font-family: 'Calibri Bold'; src: url('calibrib.ttf') format('truetype');} body{font-family: 'Calibri Bold', sans-serif;} h2{font-size:14px; color:#284B63;} p{font-size: 12px; color: #454545;} </style>";
        html = html + h2("Podsumowanie");
        html = html + boldRegular("Waga:",Utils.formatKg(weight));
        html = html + boldRegular("Dostawa (netto):", Utils.formatPln(deliveryCost));
        html = html + boldRegular("Netto:", Utils.formatPln(netto));
        html = html + boldRegular("Vat:",vat.toString()+"%");
        html = html + boldRegular("Zaliczka (brutto):",Utils.formatPln(advance));
        html = html + h2("Brutto: "+Utils.formatPln(netto+netto*vat/100f));
        html = html + "</body></html>";
        summarizeView.getEngine().loadContent(html);
    }
    private String h2(String s){
        return "<h2>"+s+"</h2>";
    }
    private String boldRegular(String bold, String reg){
        return "<p><b>"+bold+"</b> "+reg+"</p>";
    }

    private void dodajOdbiorceZBazy() {
       //dodawanie odbiorcy z bazy
    }

    private void findclient(String s, ObservableList<String> klienciTable, ObservableList<Client> klienci) throws ExecutionException, InterruptedException {
        //filtr - szukanie clienta

    }
/*
    private void addToOrder() {
        String head="<!DOCTYPE html><html><head><meta charset='UTF-8'><title>Podgląd zamówienia</title></head>" +
                "<body>" +
                "<style>" +
                "@font-face {font-family: 'Calibri Bold'; src: url('calibrib.ttf') format('truetype');} " +
                "body{font-family: 'Calibri Bold', sans-serif;} h2{font-size:14px; color:#284B63;} p{font-size: 12px; color: #454545;} " +
                "</style>";
        String clientDetails = "<h2>Dane ogólne</h2>" +
                "<p>Nip: "+ client.getNip()+
                "<br>Mail: "+ client.getMail()+
                "</p><h2>Dane do faktury</h2>"+
                "<p><b>Nazwa: </b>" + client.getName() + "<br>"+
                "<b>Adres: </b><br>" + client.getStreet() +
                ",<br>" + client.getPostCode() + " "+
                client.getCity() + "</p><p><b>Typ clienta: " + client.getClientType()+"</b></p>";
        receiverView.getEngine().loadContent(head + clientDetails + "</body></html>");
        orderNumberContent.setText(generateOrderNumber());

        addressArea.setText(getClientAddress());
    }*/

    private String getClientAddress() {
        return client.getNameDelivery()+
                "\n"+ client.getStreetDelivery()+
                ", "+ client.getPostCodeDelivery()+
                " "+ client.getCityDelivery();
    }


    private void find(String s) {
        //wyszukiwanie
    }



    public void updateTable() {
        orderTable.getItems().setAll(ordersApiController.getOrders().getBody());
    }




}
