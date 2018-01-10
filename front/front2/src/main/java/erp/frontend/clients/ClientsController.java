package main.java.erp.frontend.clients;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import main.java.erp.backend.api.common.ClientsApiController;
import main.java.erp.backend.model.common.Client;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ClientsController implements Initializable {

    public TableColumn nameColumn;
    public TableColumn telephoneColumn;
    public TableColumn mailColumn;
    public TableColumn streetColumn;
    public TableColumn postCodeColumn;
    public TableColumn cityColumn;
    public TableColumn nipColumn;
    public TableColumn nameDeliveryColumn;
    public TableColumn streetDeliveryColumn;
    public TableColumn cityDeliveryColumn;
    public TableColumn postCodeDeliveryColumn;
    public TableColumn clientType;
    public TableView<Client> clientsTable;
    @FXML
    public static VBox clientsBox;
    @FXML
    private Button addClientBtn;
    @FXML
    private Button deleteClientBtn;
    @FXML
    private Button editClientBtn;
    @FXML
    private Button xBtn, findBtn, refreshBtn;
    @FXML
    private TextField findField;

    private ClientsApiController controller = new ClientsApiController();

    private FXMLLoader loader;
    private AddEditClientController addEditClientController;

    public ClientsController(){
        try {
            loader = new FXMLLoader(getClass().getResource("/fxmlFiles/addEditClient.fxml"));
            loader.load();
            addEditClientController = loader.getController();
            addEditClientController.setClientsController(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        createColumns();
        setEvents();
        refresh();
    }

    private void createColumns() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        telephoneColumn.setCellValueFactory(new PropertyValueFactory<>("telephone"));
        mailColumn.setCellValueFactory(new PropertyValueFactory<>("mailField"));
        streetColumn.setCellValueFactory(new PropertyValueFactory<>("street"));
        postCodeColumn.setCellValueFactory(new PropertyValueFactory<>("post"));
        cityColumn.setCellValueFactory(new PropertyValueFactory<>("city"));
        nipColumn.setCellValueFactory(new PropertyValueFactory<>("nip"));
        nameDeliveryColumn.setCellValueFactory(new PropertyValueFactory<>("nameDelivery"));
        streetDeliveryColumn.setCellValueFactory(new PropertyValueFactory<>("streetDelivery"));
        cityDeliveryColumn.setCellValueFactory(new PropertyValueFactory<>("cityDeliveryField"));
        postCodeDeliveryColumn.setCellValueFactory(new PropertyValueFactory<>("postCodeDelivery"));
        clientType.setCellValueFactory(new PropertyValueFactory<>("clientType"));
    }

    private void setEvents() {
        addClientBtn.setOnAction(e -> addEditClientController.show());
        refreshBtn.setOnAction(e -> refresh());
        clientsTable.setOnMouseClicked(e -> editClient());

        editClientBtn.setOnAction(e -> editClient());

        deleteClientBtn.setOnAction(e -> {
            Client selected = clientsTable.getSelectionModel().getSelectedItem();
            if(selected != null){
                controller.deleteClient(selected.getId());
            }
        });
    }

    private void editClient() {
        Client selected = clientsTable.getSelectionModel().getSelectedItem();
        if(selected != null){
            addEditClientController.show(selected);
        }
    }


    void refresh() {
      //clientsTable.getItems().setAll(controller.getClients().getBody());
    }
}
