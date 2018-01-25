package main.java.erp_crm.frontend.deliveryCosts;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import main.java.erp_crm.backend.api.erp.DeliveryCostApi;
import main.java.erp_crm.backend.model.DBData;
import main.java.erp_crm.backend.model.erp.DeliveryCost;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class DeliveryCostsController implements Initializable{
    public TableView<DeliveryCost> deliveryCostTable;
    public TableColumn<DeliveryCost, Float> weightFromColumn;
    public TableColumn<DeliveryCost, Float> weightToColumn;
    public TableColumn<DeliveryCost, Float> priceColumn;
    public Button addBtn;
    public Button editBtn;
    public Button deleteBtn;


    private AddEditDeliveryCostController addEditDeliveryCostController;
    private DeliveryCostApi controller = new DeliveryCostApi();



    private void loadControllers() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmlFiles/erp/addEditDeliveryCost.fxml"));
            loader.load();
            addEditDeliveryCostController = loader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadControllers();
        setColumns();
        setEvents();
        refresh();
        Bindings.bindContent(deliveryCostTable.getItems(), DBData.getDeliveryCosts());
    }

    private void setColumns() {
        deliveryCostTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        weightFromColumn.setCellValueFactory(new PropertyValueFactory<>("weightFrom"));
        weightToColumn.setCellValueFactory(new PropertyValueFactory<>("weightTo"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
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
