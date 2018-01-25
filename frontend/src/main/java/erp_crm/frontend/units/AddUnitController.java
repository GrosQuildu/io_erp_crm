package main.java.erp_crm.frontend.units;

import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.java.erp_crm.Main;
import main.java.erp_crm.backend.api.erp.UnitsApi;
import main.java.erp_crm.backend.model.common.Unit;
import main.java.erp_crm.frontend.settings.SettingsController;

import java.net.URL;
import java.util.ResourceBundle;

public class AddUnitController implements Initializable{

    public VBox mainBox;
    public TextField nameField;
    public TextField shortNameField;
    public Button saveBtn;
    public Button cancelBtn;


    private Stage stage = new Stage();
    private SettingsController settingsController;
    private UnitsApi controller = new UnitsApi();


    private Unit unit;


    private void setEvents() {
        saveBtn.setOnAction(e -> save());
        cancelBtn.setOnAction(e -> stage.close());
    }

    private void save() {
        unit = new Unit();
        fillUnit();
        controller.createUnit(unit);
        settingsController.refresh();
        stage.close();
    }

    public void show(){
        initializeFields();
        stage.show();
    }

    private void initializeFields() {
        nameField.setText("");
        shortNameField.setText("");
    }

    private void fillUnit() {
        unit.setNameShort(shortNameField.getText());
        unit.setName(nameField.getText());
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
