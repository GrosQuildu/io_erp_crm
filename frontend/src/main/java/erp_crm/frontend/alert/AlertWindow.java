package main.java.erp_crm.frontend.alert;

import javafx.scene.control.Alert;
import javafx.scene.layout.Region;

public class AlertWindow {
    private static Alert alert = new Alert(Alert.AlertType.INFORMATION);
    public static void show(String message){
        alert.setHeaderText("Error");
        alert.setTitle("Error");
        alert.setContentText(message);
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.show();
    }
}