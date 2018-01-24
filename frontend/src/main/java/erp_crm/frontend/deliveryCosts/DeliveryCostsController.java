package main.java.erp_crm.frontend.deliveryCosts;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import main.java.erp_crm.backend.api.erp.DeliveryCostControllerApi;
import main.java.erp_crm.backend.model.DBData;
import main.java.erp_crm.backend.model.erp.DeliveryCost;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class DeliveryCostsController implements Initializable{
    @FXML
    private TableView<DeliveryCost> deliveryCostTable;
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
    private AddEditDeliveryCostController addEditDeliveryCostController;
    private DeliveryCostControllerApi controller = new DeliveryCostControllerApi();

    private FXMLLoader loader;

    public DeliveryCostsController(){
        try {
            loader = new FXMLLoader(getClass().getResource("/fxmlFiles/erp/addEditDeliveryCost.fxml"));
            loader.load();
            addEditDeliveryCostController = loader.getController();
            addEditDeliveryCostController.setDeliveryCostsController(this);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        deliveryCostTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        weightFromColumn.setCellValueFactory(new PropertyValueFactory<>("weightFrom"));
        weightToColumn.setCellValueFactory(new PropertyValueFactory<>("weightTo"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        setEvents();
        refresh();
        Bindings.bindContent(deliveryCostTable.getItems(), DBData.getDeliveryCosts());
    }

    private void setEvents() {
        addBtn.setOnAction(e -> addEditDeliveryCostController.show());

        editBtn.setOnAction(e -> {
            DeliveryCost item = deliveryCostTable.getSelectionModel().getSelectedItem();
            if(item!=null)
                addEditDeliveryCostController.show(item);
        });
        deleteBtn.setOnAction(e -> {
            DeliveryCost item = deliveryCostTable.getSelectionModel().getSelectedItem();
            if(item!=null) {
                controller.deleteDeliveryCost(item);
                refresh();
            }
        });
    }


    public void refresh() {
        deliveryCostTable.getItems().clear();
        controller.getDeliveryCosts();
    }
}
