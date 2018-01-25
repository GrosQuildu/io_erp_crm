package main.java.erp_crm.frontend.clients;

import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.java.erp_crm.Main;
import main.java.erp_crm.backend.api.common.ClientTypesApi;
import main.java.erp_crm.backend.model.common.ClientType;
import main.java.erp_crm.frontend.settings.SettingsController;

import java.net.URL;
import java.util.ResourceBundle;

public class AddClientTypeController implements Initializable{
    public TextField descriptionField;
    public VBox mainBox;
    public Button saveBtn;
    public Button cancelBtn;


    private Stage stage = new Stage();
    private ClientType clientType;
    private SettingsController settingsController;
    private ClientTypesApi controller = new ClientTypesApi();


    private void setEvents() {
        saveBtn.setOnAction(e -> {
            save();
            stage.close();
        });
        cancelBtn.setOnAction(e -> stage.close());
    }

    private void save() {
        clientType = new ClientType();
        fillClientType();
        controller.createClientType(clientType);
        settingsController.refresh();
    }

    public void show(){
        initializeFields();
        stage.show();
    }

    private void initializeFields() {
        clientType = null;
        descriptionField.setText("");
    }

    private void fillClientType() {
        clientType.setDescription(descriptionField.getText());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setEvents();
        Scene scene = new Scene(mainBox);
        scene.getStylesheets().add(Main.css);
        stage.setWidth(300);
        stage.setScene(scene);
    }

    public void setSettingsController(SettingsController settingsController) {
        this.settingsController = settingsController;
    }
}
