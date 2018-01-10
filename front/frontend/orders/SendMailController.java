package main.java.erp.frontend.orders;

import com.lowagie.text.DocumentException;
import javafx.concurrent.Task;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import main.java.erp.Mail;
import main.java.erp.Main;
import main.java.erp.backend.Utils;
import main.java.erp.backend.model.erp.Order;
import main.java.erp.backend.model.erp.Proforma;
import main.java.erp.backend.pdf.PdfGenerator;
import main.java.erp.frontend.loading.LoadingDialog;
import main.java.erp.frontend.settings.SettingsController;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class SendMailController implements Initializable{
    public GridPane mainPane;
    private Stage stage = new Stage(StageStyle.UNDECORATED);

    public TextField visibleAddressesField;
    public TextField hiddenAddressesField;
    public TextField subjectField;
    public CheckBox orderBox;
    public CheckBox proformaBox;
    public ListView<File> attachmentList;
    public Button addAttachmentBtn;
    public Button deleteAttachmentBtn;
    public TextArea contentArea;
    public Button sendBtn;
    public Button cancelBtn;
    private OrdersController ordersController;
    private Order order;

    public void show(Order item) {
        order = item;
        fillFields();
        show();
    }
    public void show() {
        order = null;
        stage.show();
    }

    private void fillFields() {
        if(order!=null){
            visibleAddressesField.setText(order.getClient().getMail());
            subjectField.setText("Order nr. " + order.getOrderNumber());
        }
    }

    public void setOrdersController(OrdersController ordersController) {
        this.ordersController = ordersController;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Scene scene = new Scene(mainPane);
        scene.getStylesheets().add(Main.css);
        stage.setScene(scene);
        setEvents();
    }

    private void setEvents() {
        sendBtn.setOnAction(e -> {
            sendMail();
            stage.close();
        });
        cancelBtn.setOnAction(e -> stage.close());
        addAttachmentBtn.setOnAction(e -> chooseFile());
        deleteAttachmentBtn.setOnAction(e -> {
            File item = attachmentList.getSelectionModel().getSelectedItem();
            if(item!=null) attachmentList.getItems().remove(item);
        });
    }

    private void chooseFile() {
        File file = new FileChooser().showOpenDialog(stage);
        attachmentList.getItems().add(file);
    }

    private void sendMail() {
        ArrayList<String> filesList = new ArrayList<>();
        ArrayList<InternetAddress> visibleAddresses = new ArrayList<>();
        ArrayList<InternetAddress> hiddenAddresses = new ArrayList<>();
        for(String i : visibleAddressesField.getText().split("[;,]")){
            try {
                visibleAddresses.add(new InternetAddress(i));
            } catch (AddressException e) {
                e.printStackTrace();
            }
        }
        for(String i : hiddenAddressesField.getText().split("[;,]")){
            try {
                hiddenAddresses.add(new InternetAddress(i));
            } catch (AddressException e) {
                e.printStackTrace();
            }
        }
        attachmentList.getItems().forEach(file -> filesList.add(file.getName()));
        if(proformaBox.isSelected()){
            Proforma proforma = new Proforma();
            //get proforma from db
            try {
                File file = new File(SettingsController.getUserDataDirectory()+"tmp");
                if(PdfGenerator.proforma(order.getOrderNumber(), file.getPath() + Utils.normalizeString(proforma.getProformaNumber().replace("/", "-")) + ".pdf")){
                    filesList.add(file.getPath() + Utils.normalizeString(proforma.getProformaNumber().replace("/", "-")) + ".pdf");
                }
            } catch (IOException | DocumentException e) {
                e.printStackTrace();
            }
        }
        if(orderBox.isSelected()){
            try {
                File file = new File(SettingsController.getUserDataDirectory()+"tmp");
                if(!file.exists()) file.mkdir();
                if(PdfGenerator.order(order.getOrderNumber(), file.getPath() + Utils.normalizeString(order.getOrderNumber().replace("/", "-")) + ".pdf")){
                    filesList.add(file.getPath() + Utils.normalizeString(order.getOrderNumber().replace("/", "-")) + ".pdf");
                }
            } catch (IOException | DocumentException e) {
                e.printStackTrace();
            }
        }
        Mail mail = new Mail(
                visibleAddresses,
                hiddenAddresses,
                subjectField.getText(),
                contentArea.getText(),
                filesList
                );
        LoadingDialog ld = new LoadingDialog("Sending your message");
        Task<Boolean> sendTask = new Task<Boolean>(){

            @Override
            protected Boolean call() throws Exception {
                return mail.send();
            }
        };
        sendTask.setOnRunning(event -> ld.show());
        sendTask.setOnSucceeded(event -> {
            ld.close();
            stage.close();
        });
        sendTask.setOnFailed(event -> {
            ld.close();
            stage.close();
            Alert al = new Alert(Alert.AlertType.ERROR);
            al.setTitle("An error occurred while sending");
            al.setHeaderText(null);
            al.setContentText("Please, check your mail settings.");
            al.getDialogPane().getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
            al.show();
        });
        new Thread(sendTask).start();
    }
}
