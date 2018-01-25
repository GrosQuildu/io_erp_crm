package main.java.erp_crm.frontend.deliveryCosts;

import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.java.erp_crm.Main;
import main.java.erp_crm.backend.api.erp.DeliveryCostApi;
import main.java.erp_crm.backend.model.erp.DeliveryCost;

import java.math.BigDecimal;
import java.net.URL;
import java.util.ResourceBundle;

public class AddEditDeliveryCostController implements Initializable{
    public VBox mainBox;
    public TextField fromField;
    public TextField toField;
    public TextField priceField;
    public Button saveBtn;
    public Button cancelBtn;


    private Stage stage = new Stage();
    private DeliveryCost deliveryCost;
    private DeliveryCostApi controller = new DeliveryCostApi();


    private void setEvents() {
        saveBtn.setOnAction(e -> {
            save();
            stage.close();
        });
        cancelBtn.setOnAction(e -> stage.close());
    }

    private void save() {
        if(deliveryCost!=null){
            fillDeliveryCost();
            controller.updateDeliveryCost(deliveryCost.getId(), deliveryCost);
        } else {
            deliveryCost = new DeliveryCost();
            fillDeliveryCost();
            controller.createDeliveryCost(deliveryCost);
        }
        controller.getDeliveryCosts();
    }

    public void show(){
        initializeFields();
        stage.show();
    }

    private void initializeFields() {
        this.deliveryCost = null;
        priceField.setText("");
        fromField.setText("");
        toField.setText("");
        
    }

    public void show(DeliveryCost deliveryCost){
        stage.show();
        fillFields(deliveryCost);
    }

    private void fillFields(DeliveryCost deliveryCost) {
        priceField.setText(deliveryCost.getPrice().toString());
        fromField.setText(deliveryCost.getWeightFrom().toString());
        toField.setText(deliveryCost.getWeightTo().toString());
    }

    private void fillDeliveryCost() {
        deliveryCost.setPrice(new BigDecimal(priceField.getText()));
        deliveryCost.setWeightFrom(new BigDecimal(fromField.getText()).floatValue());
        deliveryCost.setWeightTo(new BigDecimal(toField.getText()).floatValue());
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setEvents();
        Scene scene = new Scene(mainBox);
        scene.getStylesheets().add(Main.css);
        stage.setWidth(300);
        stage.setScene(scene);
    }

}
