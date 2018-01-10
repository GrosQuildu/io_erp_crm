package main.java.erp.frontend.deliveryCosts;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import main.java.erp.backend.api.erp.DeliveryCostsApi;
import main.java.erp.backend.model.erp.DeliveryCost;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class DeliveryCostsController implements Initializable{
    @FXML
    private TableView<DeliveryCost> transportTable;
    @FXML
    private TextField basePriceField;
    @FXML
    private TableColumn<DeliveryCost, Float> weightFromColumn;
    @FXML
    private TableColumn<DeliveryCost, Float> weightToColumn;
    @FXML
    private TableColumn<DeliveryCost, Float> priceColumn;
    @FXML
    private Button addBtn;
    @FXML
    private Button editBtn;
    @FXML
    private Button deleteBtn;
    private ObservableList<DeliveryCost> data = FXCollections.observableArrayList();
    private AddEditDeliveryCostController addEditDeliveryCostController;
    private DeliveryCostsApi controller = new DeliveryCostsApi();

    private FXMLLoader loader;

    public DeliveryCostsController(){
        try {
            loader = new FXMLLoader(getClass().getResource("/fxmlFiles/addEditDeliveryCost.fxml"));
            loader.load();
            addEditDeliveryCostController = loader.getController();
            addEditDeliveryCostController.setDeliveryCostsController(this);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        transportTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        weightFromColumn.setCellValueFactory(new PropertyValueFactory<>("wagaOd"));
        weightToColumn.setCellValueFactory(new PropertyValueFactory<>("wagaDo"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("cena"));
        updateTable();
        setEvents();
    }

    private void setEvents() {
        addBtn.setOnAction(e -> addEditDeliveryCostController.show());

        editBtn.setOnAction(e -> {
            DeliveryCost item = transportTable.getSelectionModel().getSelectedItem();
            if(item!=null)
                addEditDeliveryCostController.show();
        });
        deleteBtn.setOnAction(e -> {
            DeliveryCost item = transportTable.getSelectionModel().getSelectedItem();
            if(item!=null)
                controller.deleteDeliveryCost(item);
        });
        basePriceField.setOnAction(e -> {
            //aktualizacja stawki wg wartosci stawkaField
            //controller.updateBasePrice(Double.parseDouble(basePriceField.getText()));
        });
    }

    private void updateTable() {
        DeliveryCost toUpdate = transportTable.getSelectionModel().getSelectedItem();
        controller.updateDeliveryCost(toUpdate.getId(),toUpdate);
    }

    public void refresh() {
        transportTable.getItems().setAll(controller.getDeliveryCosts().getBody());
    }
}
