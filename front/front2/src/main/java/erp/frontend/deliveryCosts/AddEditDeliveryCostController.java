package main.java.erp.frontend.deliveryCosts;

import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.java.erp.Main;
import main.java.erp.backend.api.erp.ArticlesApiController;
import main.java.erp.backend.api.erp.DeliveryCostControllerApi;
import main.java.erp.backend.api.erp.DeliveryCostsApi;
import main.java.erp.backend.model.erp.DeliveryCost;

import java.math.BigDecimal;
import java.net.URL;
import java.util.ResourceBundle;

public class AddEditDeliveryCostController implements Initializable{
    private Stage stage = new Stage();
    private Scene scene;
    public VBox mainBox;
    public TextField fromField;
    public TextField toField;
    public TextField priceField;
    public Button saveBtn;
    public Button cancelBtn;
    private DeliveryCost deliveryCost;
    private DeliveryCostsController deliveryCostsController;
    private DeliveryCostControllerApi controller = new DeliveryCostControllerApi();


    private void setEvents() {
        saveBtn.setOnAction(e -> {
            if(deliveryCost!=null){
                fillDeliveryCost();
                controller.updateDeliveryCost(deliveryCost.getId(), deliveryCost);
            } else {
                deliveryCost = new DeliveryCost();
                fillDeliveryCost();
                controller.createDeliveryCost(deliveryCost);
            }
            deliveryCostsController.refresh();
            stage.close();
        });
        cancelBtn.setOnAction(e -> stage.close());
    }

    public void show(){
        this.deliveryCost = null;
        stage.show();
    }
    public void show(DeliveryCost deliveryCost){
        show();
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

    public void setDeliveryCostsController(DeliveryCostsController deliveryCostsController) {
        this.deliveryCostsController = deliveryCostsController;
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setEvents();
        scene = new Scene(mainBox);
        scene.getStylesheets().add(Main.css);
        stage.setWidth(300);
        stage.setScene(scene);
    }

}
