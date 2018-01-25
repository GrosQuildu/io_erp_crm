package main.java.erp_crm.frontend.orders;

import com.lowagie.text.DocumentException;
import javafx.beans.binding.Bindings;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import main.java.erp_crm.backend.SharedData;
import main.java.erp_crm.backend.api.erp.OrdersApi;
import main.java.erp_crm.backend.model.DBData;
import main.java.erp_crm.backend.model.common.Client;
import main.java.erp_crm.backend.model.erp.Order;
import main.java.erp_crm.backend.pdf.PdfGenerator;
import main.java.erp_crm.frontend.alert.AlertWindow;
import main.java.erp_crm.frontend.loading.LoadingDialog;
import main.java.erp_crm.frontend.proformas.AddProformaController;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;


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
    private OrdersApi ordersApiController = new OrdersApi();

    private WebView previewView = new WebView();

    private AddEditOrderController addEditOrderController;
    private SendMailController sendMailController;
    private OrderPreviewController orderPreviewController;


    private void loadControllers() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmlFiles/erp/addEditOrder.fxml"));
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
        loadControllers();
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
        addBtn.setOnAction(e-> addOrder());
        editBtn.setOnAction(e -> editOrder());
        refreshBtn.setOnAction(e -> refresh());
        deleteBtn.setOnAction(e -> deleteOrder());

        proformaBtn.setOnAction(e -> addProforma());
        sendBtn.setOnAction(e -> sendOrder());
        exportBtn.setOnAction(e -> prepareExport());

        previewBtn.setOnAction(e -> previewOrder());

        orderTable.setOnMouseClicked(event -> {
            if(event.getClickCount() == 2){
                previewOrder();
            }
        });
    }

    private void previewOrder() {
        if(!orderTable.getSelectionModel().isEmpty())
            orderPreviewController.show(orderTable.getSelectionModel().getSelectedItem());
    }

    private void sendOrder() {
        sendMailController.show(orderTable.getSelectionModel().getSelectedItem());
    }

    private void addProforma() {
        addProformaController.show(orderTable.getSelectionModel().getSelectedItem());
    }

    private void deleteOrder() {
        Order toDelete = orderTable.getSelectionModel().getSelectedItem();
        if(toDelete != null) {
            ordersApiController.deleteOrder(toDelete);
            refresh();
        }
    }

    private void addOrder() {
        addEditOrderController.show();
    }

    private void editOrder() {
        Order item = orderTable.getSelectionModel().getSelectedItem();
        if(item!=null){
            addEditOrderController.show(item);
        }
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


    public void refresh() {
        ordersApiController.getOrders();
    }




}
