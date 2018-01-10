package main.java.erp.frontend.clients;

import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.java.erp.Main;
import main.java.erp.backend.api.common.ClientsApiController;
import main.java.erp.backend.model.common.Client;

import java.net.URL;
import java.util.ResourceBundle;

public class AddEditClientController implements Initializable {
    public VBox mainBox;
    private Stage stage = new Stage();
    private Scene scene;
    public TextField nameField;
    public TextField streetField;
    public TextField cityField;
    public TextField postCodeField;
    public TextField nameDeliveryField;
    public TextField streetDeliveryField;
    public TextField postCodeDeliveryField;
    public TextField cityDeliveryField;
    public TextField nipField;
    public TextField telephoneField;
    public TextField mailField;
    public ComboBox clientTypeBox;
    public Button saveBtn;
    public Button cancelBtn;
    private ClientsController clientsController;
    private ClientsApiController controller = new ClientsApiController();
    private Client client = null;

    /*
    *   public TextField nameField;
        public TextField streetField;
        public TextField cityField;
        public TextField postCodeField;
        public TextField nameDeliveryField;
        public TextField streetDeliveryField;
        public TextField postCodeDeliveryField;
        public TextField cityDeliveryField;
        public TextField nipField;
        public TextField telephoneField;
        public TextField mailField;
        public ComboBox clientTypeBox;
    * */
    private void setEvents() {
        saveBtn.setOnAction(e -> {
            if(client!=null){
                fillClient();
                controller.updateClient(client.getId(), client);
            } else {
                client = new Client();
                fillClient();
                controller.createClient(client);
            }
            clientsController.refresh();
            stage.close();
        });
        cancelBtn.setOnAction(e -> stage.close());
    }

    private void fillClient() {
        client.setName(nameField.getText());
        client.setStreet(streetField.getText());
        client.setCity(cityField.getText());
        client.setPostCode(postCodeField.getText());
        client.setNameDelivery(nameDeliveryField.getText());
        client.setStreetDelivery(streetDeliveryField.getText());
        client.setPostCodeDelivery(postCodeDeliveryField.getText());
        client.setCityDelivery(cityDeliveryField.getText());
        client.setNip(nipField.getText());
        client.setTelephone(telephoneField.getText());
        client.setMail(mailField.getText());
    }

    public void show(){
        this.client = null;
        stage.show();
    }
    public void show(Client client){
        fillFields(client);
        show();
    }
    private void fillFields(Client client) {
        this.client = client;
        nameField.setText(client.getName());
        streetField.setText(client.getStreet());
        cityField.setText(client.getCity());
        postCodeField.setText(client.getPostCode());
        nameDeliveryField.setText(client.getNameDelivery());
        streetDeliveryField.setText(client.getStreetDelivery());
        postCodeDeliveryField.setText(client.getPostCodeDelivery());
        cityDeliveryField.setText(client.getCityDelivery());
        nipField.setText(client.getNip());
        telephoneField.setText(client.getTelephone());
        mailField.setText(client.getMail());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setEvents();
        scene = new Scene(mainBox);
        scene.getStylesheets().add(Main.css);
        stage.setWidth(300);
        stage.setScene(scene);
    }

    public void setClientsController(ClientsController clientsController) {
        this.clientsController = clientsController;
    }
}
