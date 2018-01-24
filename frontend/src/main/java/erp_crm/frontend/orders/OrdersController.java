package main.java.erp_crm.frontend.orders;

import com.lowagie.text.DocumentException;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import main.java.erp_crm.backend.SharedData;
import main.java.erp_crm.backend.Utils;
import main.java.erp_crm.backend.api.erp.OrderControllerApi;
import main.java.erp_crm.backend.model.DBData;
import main.java.erp_crm.backend.model.common.Client;
import main.java.erp_crm.backend.model.erp.Order;
import main.java.erp_crm.backend.model.erp.OrderedArticle;
import main.java.erp_crm.backend.pdf.PdfGenerator;
import main.java.erp_crm.frontend.alert.AlertWindow;
import main.java.erp_crm.frontend.loading.LoadingDialog;
import main.java.erp_crm.frontend.proformas.AddProformaController;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;
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
    public Button previewBtn, refreshBtn;
    private AddProformaController addProformaController;
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
            loader = new FXMLLoader(getClass().getResource("/fxmlFiles/erp/addEditOrder.fxml"));
            loader.load();
            addEditOrderController = loader.getController();
            addEditOrderController.setOrdersController(this);

            loader = new FXMLLoader(getClass().getResource("/fxmlFiles/erp/sendMail.fxml"));
            loader.load();
            sendMailController = loader.getController();

            loader = new FXMLLoader(getClass().getResource("/fxmlFiles/erp/orderPreview.fxml"));
            loader.load();
            orderPreviewController = loader.getController();

            loader = new FXMLLoader(getClass().getResource("/fxmlFiles/erp/addProforma.fxml"));
            loader.load();
            addProformaController = loader.getController();
            addProformaController.setOrdersController(this);
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
        Bindings.bindContent(orderTable.getItems(), DBData.getOrders());
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
            addProformaController.show(orderTable.getSelectionModel().getSelectedItem());
        });
        sendBtn.setOnAction(e -> {
            sendMailController.show(orderTable.getSelectionModel().getSelectedItem());
        });
        exportBtn.setOnAction(e -> prepareExport());

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
            fc.setTitle("Order export");
            fc.setInitialDirectory(new File(SharedData.getConfig().getDefaultOrderPdfPath().isEmpty()?".": SharedData.getConfig().getDefaultOrderPdfPath()));
            fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF", "*.pdf"));
            Order order = orderTable.getSelectionModel().getSelectedItem();
            Client client = order.getClient();
            fc.setInitialFileName("Order for "+
                    client.getName().replace("&", " and ")+
                    " no "+
                    order.getOrderNumber().replace("/","-")+
                    ".pdf");
            Stage fStage = new Stage(StageStyle.DECORATED);
            fStage.setMaximized(false);
            File file = fc.showSaveDialog(fStage);

            LoadingDialog ld = new LoadingDialog("Exporting to pdf");
            Task<Boolean> eksportTask = new Task<Boolean>() {
                @Override
                protected Boolean call() throws Exception {
                    if (file != null)
                        try {
                            return PdfGenerator.order(order,file.toPath().toString(), true);
                        } catch (IOException | DocumentException e1) {
                            e1.printStackTrace();
                            System.out.println(e1.getMessage());
                        }
                    return true;
                }
            };
            eksportTask.setOnRunning(eve -> ld.show());
            eksportTask.setOnSucceeded(eve -> {
                ld.close();
                refresh();
            });
            eksportTask.setOnFailed(eve -> {
                AlertWindow.show("Something went wrong when writing PDF file.");
                ld.close();
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


    public void refresh() {
        ordersApiController.getOrders();
    }




}
