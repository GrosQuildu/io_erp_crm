package main.java.erp_crm.frontend.contacts;

import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.java.erp_crm.Main;
import main.java.erp_crm.backend.api.common.ContactGroupApi;
import main.java.erp_crm.backend.model.crm.ContactGroup;
import main.java.erp_crm.frontend.settings.SettingsController;

import java.net.URL;
import java.util.ResourceBundle;

public class AddContactGroupController implements Initializable{
    private Stage stage = new Stage();
    public VBox mainBox;
    public TextField descriptionField;
    public Button saveBtn;
    public Button cancelBtn;
    private SettingsController settingsController;
    private ContactGroupApi controller = new ContactGroupApi();
    private ContactGroup contactGroup;


    private void setEvents() {
        saveBtn.setOnAction(e -> {
            contactGroup = new ContactGroup();
            fillUnit();
            controller.createContactGroup(contactGroup);
            settingsController.refresh();
            stage.close();
        });
        cancelBtn.setOnAction(e -> stage.close());
    }

    public void show(){
        stage.show();
    }

    private void fillUnit() {
        contactGroup.setDescription(descriptionField.getText());
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
