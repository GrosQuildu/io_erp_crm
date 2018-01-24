package main.java.erp_crm.frontend.tasks;

import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.java.erp_crm.Main;
import main.java.erp_crm.backend.api.crm.TaskStatusControllerApi;
import main.java.erp_crm.backend.model.crm.TaskStatus;
import main.java.erp_crm.frontend.settings.SettingsController;

import java.net.URL;
import java.util.ResourceBundle;

public class AddTaskStatusController implements Initializable{
    private Stage stage = new Stage();
    public VBox mainBox;
    public TextField descriptionField;
    public Button saveBtn;
    public Button cancelBtn;
    private SettingsController settingsController;
    private TaskStatusControllerApi controller = new TaskStatusControllerApi();
    private TaskStatus status;


    private void setEvents() {
        saveBtn.setOnAction(e -> {
            status = new TaskStatus();
            fillUnit();
            controller.createTaskStatus(status);
            settingsController.refresh();
            stage.close();
        });
        cancelBtn.setOnAction(e -> stage.close());
    }

    public void show(){
        stage.show();
    }

    private void fillUnit() {
        status.setDescription(descriptionField.getText());
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
