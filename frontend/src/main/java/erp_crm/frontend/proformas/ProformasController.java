package main.java.erp_crm.frontend.proformas;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXMLLoader;
import main.java.erp_crm.backend.Utils;
import main.java.erp_crm.backend.api.erp.ProformasControllerApi;
import main.java.erp_crm.backend.model.DBData;
import main.java.erp_crm.backend.model.erp.Order;
import main.java.erp_crm.backend.model.erp.OrderedArticle;
import main.java.erp_crm.backend.model.erp.Proforma;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by marcin on 07.09.16.
 */
public class ProformasController implements Initializable{
    @FXML
    private TableView<Proforma> proformasTable;
    @FXML
    private TableColumn<Proforma, String> proformaNumberColumn;
    @FXML
    private TableColumn<Proforma, Order> clientColumn;
    @FXML
    private TableColumn<Proforma, Order> netColumn;
    @FXML
    private TableColumn<Proforma, String> paymentMethodColumn;
    @FXML
    private TableColumn<Proforma, Order> orderNumberColumn;
    @FXML
    private Button pdfBtn,refBtn;
    @FXML
    private Button deleteBtn;
    private AddProformaController addProformaController;
    private ProformasControllerApi controller = new ProformasControllerApi();
    private ProformasControllerApi proformasControllerApi = new ProformasControllerApi();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmlFiles/erp/addProforma.fxml"));
            loader.load();
            addProformaController = loader.getController();
            addProformaController.setProformasController(this);
        } catch (IOException e) {
            e.printStackTrace();
        }

        proformaNumberColumn.setCellValueFactory(new PropertyValueFactory<>("proformaNumber"));
        clientColumn.setCellValueFactory(new PropertyValueFactory<>("order"));
        clientColumn.setCellFactory(column -> new TableCell<Proforma, Order>() {
            @Override
            protected void updateItem(Order item, boolean empty) {
                super.updateItem(item, empty);

                if (item == null || empty) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(item.getClient().getName());

                }
            }
        });
        netColumn.setCellValueFactory(new PropertyValueFactory<>("order"));
        netColumn.setCellFactory(column -> new TableCell<Proforma, Order>() {
            @Override
            protected void updateItem(Order item, boolean empty) {
                super.updateItem(item, empty);

                if (item == null || empty) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(Utils.formatPln(calculateOrderNet(item.getOrderedArticles())));
                }
            }
        });
        paymentMethodColumn.setCellValueFactory(new PropertyValueFactory<>("paymentMethod"));
        orderNumberColumn.setCellValueFactory(new PropertyValueFactory<>("order"));
        orderNumberColumn.setCellFactory(column -> new TableCell<Proforma, Order>() {
            @Override
            protected void updateItem(Order item, boolean empty) {
                super.updateItem(item, empty);

                if (item == null || empty) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(item.getOrderNumber());
                }
            }
        });
        proformasTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        KeyCombination pdfCombination = new KeyCodeCombination(KeyCode.E,KeyCombination.CONTROL_DOWN);
        proformasTable.addEventHandler(KeyEvent.KEY_RELEASED, event -> {
            if(pdfCombination.match(event)){
                exportToPdf();
            }
        });
        updateTable();
        setEvents();
        Bindings.bindContent(proformasTable.getItems(), DBData.getProformas());
    }

    private float calculateOrderNet(List<OrderedArticle> orderedArticles) {
        float res = 0f;
        for(OrderedArticle o : orderedArticles){
            res+= o.getNetPrice().floatValue();
        }
        return res;
    }

    private void setEvents() {
        refBtn.setOnAction(e->updateTable());
        pdfBtn.setOnAction(e -> exportToPdf());


        deleteBtn.setOnAction(e -> {
            Proforma selected = proformasTable.getSelectionModel().getSelectedItem();
            if(selected!=null) {
                proformasControllerApi.deleteProforma(selected);
            }
        });
    }

    private void exportToPdf() {
        Proforma item = proformasTable.getSelectionModel().getSelectedItem();
        if(item!=null)
            addProformaController.exportToPDF(item, false);
    }




    void updateTable() {
        controller.getProformas();
    }

    
}
