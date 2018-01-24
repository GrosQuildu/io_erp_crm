package main.java.erp_crm.frontend.clients;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import main.java.erp_crm.backend.api.common.ClientsControllerApi;
import main.java.erp_crm.backend.model.DBData;
import main.java.erp_crm.backend.model.common.Client;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ClientsController implements Initializable {

    public TableColumn<Client, String> nameColumn;
    public TableColumn<Client, String> telephoneColumn;
    public TableColumn<Client, String> mailColumn;
    public TableColumn<Client, String> streetColumn;
    public TableColumn<Client, String> postCodeColumn;
    public TableColumn<Client, String> cityColumn;
    public TableColumn<Client, String> nipColumn;
    public TableColumn<Client, String> nameDeliveryColumn;
    public TableColumn<Client, String> streetDeliveryColumn;
    public TableColumn<Client, String> cityDeliveryColumn;
    public TableColumn<Client, String> postCodeDeliveryColumn;
    public TableColumn<Client, String> clientType;
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
    private Button refreshBtn;

    private ClientsControllerApi controller = new ClientsControllerApi();

    private FXMLLoader loader;
    private AddEditClientController addEditClientController;

    public ClientsController(){
        try {
            loader = new FXMLLoader(getClass().getResource("/fxmlFiles/erp/addEditClient.fxml"));
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
        Bindings.bindContent(clientsTable.getItems(), DBData.getClients());
    }

    private void createColumns() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        telephoneColumn.setCellValueFactory(new PropertyValueFactory<>("telephone"));
        mailColumn.setCellValueFactory(new PropertyValueFactory<>("mail"));
        streetColumn.setCellValueFactory(new PropertyValueFactory<>("street"));
        postCodeColumn.setCellValueFactory(new PropertyValueFactory<>("post"));
        cityColumn.setCellValueFactory(new PropertyValueFactory<>("city"));
        nipColumn.setCellValueFactory(new PropertyValueFactory<>("nip"));
        nameDeliveryColumn.setCellValueFactory(new PropertyValueFactory<>("nameDelivery"));
        streetDeliveryColumn.setCellValueFactory(new PropertyValueFactory<>("streetDelivery"));
        cityDeliveryColumn.setCellValueFactory(new PropertyValueFactory<>("cityDelivery"));
        postCodeDeliveryColumn.setCellValueFactory(new PropertyValueFactory<>("postCodeDelivery"));
        clientType.setCellValueFactory(new PropertyValueFactory<>("clientType"));
    }

    private void setEvents() {
        addClientBtn.setOnAction(e -> addEditClientController.show());
        refreshBtn.setOnAction(e -> refresh());
        clientsTable.setOnMouseClicked(e -> {
                if(e.getClickCount() == 2)
                    editClient();
            }
        );
        editClientBtn.setOnAction(e -> editClient());

        deleteClientBtn.setOnAction(e -> {
            Client selected = clientsTable.getSelectionModel().getSelectedItem();
            if(selected != null){
                controller.deleteClient(selected);
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
        controller.getClients();
    }
}
