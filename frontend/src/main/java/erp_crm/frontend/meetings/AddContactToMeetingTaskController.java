package main.java.erp_crm.frontend.meetings;

import javafx.beans.binding.Bindings;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.java.erp_crm.Main;
import main.java.erp_crm.backend.model.DBData;
import main.java.erp_crm.backend.model.crm.Contact;
import main.java.erp_crm.frontend.tasks.AddEditTaskController;

import java.net.URL;
import java.util.ResourceBundle;

public class AddContactToMeetingTaskController implements Initializable{
    public VBox mainBox;
    public TableView<Contact> contactTableView;
    public TableColumn<Contact, String> nameColumn;
    public TableColumn<Contact, String> nipColumn;
    public TableColumn<Contact, String> mailColumn;
    public Button addBtn;
    public Button cancelBtn;


    private Stage stage = new Stage();
    private AddEditMeetingController addEditMeetingController;
    private AddEditTaskController addEditTaskController;

    private void setEvents() {
        addBtn.setOnAction(e -> {
            Contact item = contactTableView.getSelectionModel().getSelectedItem();
            if(item!=null){
                if(addEditMeetingController!=null){
                    addEditMeetingController.addContact(item);
                } else if(addEditTaskController!=null){
                    addEditTaskController.addContact(item);
                }
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
        Bindings.bindContent(contactTableView.getItems(), DBData.getContacts());
    }

    private void setColumns() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        mailColumn.setCellValueFactory(new PropertyValueFactory<>("mail"));
        nipColumn.setCellValueFactory(new PropertyValueFactory<>("nip"));
    }


    public void setAddEditTaskController(AddEditTaskController addEditTaskController) {
        this.addEditTaskController = addEditTaskController;
    }

    public void show() {
        stage.show();
    }

    public void setAddEditMeetingController(AddEditMeetingController addEditMeetingController) {
        this.addEditMeetingController = addEditMeetingController;
    }
}
