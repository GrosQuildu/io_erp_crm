package main.java.erp.frontend.orders;

import javafx.beans.binding.Bindings;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.java.erp.Main;
import main.java.erp.backend.api.common.ClientsControllerApi;
import main.java.erp.backend.model.DBData;
import main.java.erp.backend.model.common.Client;

import java.net.URL;
import java.util.ResourceBundle;

public class AddClientToOrderController implements Initializable{
    private Stage stage = new Stage();
    public VBox mainBox;
    public TableView<Client> clientTableView;
    public TableColumn nameColumn;
    public TableColumn nipColumn;
    public TableColumn mailColumn;
    public Button addBtn;
    public Button cancelBtn;
    private AddEditOrderController addEditOrderController;
    private ClientsControllerApi controller = new ClientsControllerApi();

    private void setEvents() {
        addBtn.setOnAction(e -> {
            Client item = clientTableView.getSelectionModel().getSelectedItem();
            if(item!=null){
                addEditOrderController.addClient(item);
            }
            stage.close();
        });
        cancelBtn.setOnAction(e -> stage.close());
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Scene scene = new Scene(mainBox);
        scene.getStylesheets().add(Main.css);
        stage.setScene(scene);
        setEvents();
        setColumns();
        controller.getClients();
        Bindings.bindContent(clientTableView.getItems(), DBData.getClients());
    }

    private void setColumns() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        mailColumn.setCellValueFactory(new PropertyValueFactory<>("mail"));
        nipColumn.setCellValueFactory(new PropertyValueFactory<>("nip"));
    }

    public void setAddEditOrderController(AddEditOrderController addEditOrderController) {
        this.addEditOrderController = addEditOrderController;
    }

    public void show() {
        stage.show();
    }
}
