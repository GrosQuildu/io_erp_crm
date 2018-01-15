package main.java.erp.frontend.contacts;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class AddEditContactController implements Initializable{
    public TextField cityField;
    public TextField postCodeField;
    public TextField nameField;
    public TextField nameDeliveryField;
    public TextField postCodeDeliveryField;
    public TextField streetDeliveryField;
    public TextField cityDeliveryField;
    public TextField nipField;
    public TextField telephoneField;
    public TextField mailField;
    public TextField streetField;
    public ComboBox contactGroupBox;
    public Button saveBtn;
    public Button cancelBtn;
    public ComboBox employeeBox;
    public CheckBox vipBox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
