package main.java.erp.frontend.units;

import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.java.erp.Main;
import main.java.erp.backend.api.erp.UnitControllerApi;
import main.java.erp.backend.model.common.Unit;
import main.java.erp.frontend.settings.SettingsController;

import java.net.URL;
import java.util.ResourceBundle;

public class AddUnitController implements Initializable{

    private Stage stage = new Stage();
    private Scene scene;
    public VBox mainBox;
    public TextField nameField;
    public TextField shortNameField;
    public Button saveBtn;
    public Button cancelBtn;
    private Unit unit;
    private SettingsController settingsController;
    private UnitControllerApi controller = new UnitControllerApi();


    private void setEvents() {
        saveBtn.setOnAction(e -> {
            unit = new Unit();
            fillUnit();
            controller.createUnit(unit);
            settingsController.refresh();
            stage.close();
        });
        cancelBtn.setOnAction(e -> stage.close());
    }

    public void show(){
        stage.show();
    }

    private void fillUnit() {
        unit.setNameShort(shortNameField.getText());
        unit.setName(nameField.getText());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setEvents();
        scene = new Scene(mainBox);
        scene.getStylesheets().add(Main.css);
        stage.setWidth(300);
        stage.setScene(scene);
    }

    public void setSettingsController(SettingsController settingsController) {
        this.settingsController = settingsController;
    }
}
