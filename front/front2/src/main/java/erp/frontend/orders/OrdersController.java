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
import main.java.erp.backend.api.ConnectionApi;
import main.java.erp.backend.api.erp.OrderControllerApi;
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
    private OrderControllerApi ordersApiController = new OrderControllerApi();


    private enum CurrentScene {DATE_RECEIVER, CONDITIONS_DEADLINE}

    private WebView previewView = new WebView();
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
        setColumns();
        previewView.setPrefHeight(300);
        previewView.setMaxHeight(Double.MAX_VALUE);
        setEvents();
        refresh();
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
            refresh();
        });
        deleteBtn.setOnAction(e -> {
            //usuwanie zamówienia
            Order toDelete = orderTable.getSelectionModel().getSelectedItem();
            if(toDelete != null) {
                ordersApiController.deleteOrder(toDelete);
                refresh();
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
                refresh();
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
    private String getClientAddress() {
        return client.getNameDelivery()+
                "\n"+ client.getStreetDelivery()+
                ", "+ client.getPostCodeDelivery()+
                " "+ client.getCityDelivery();
    }


    private void find(String s) {
        //wyszukiwanie
    }



    public void refresh() {
        orderTable.getItems().setAll(ordersApiController.getOrders());
    }




}
