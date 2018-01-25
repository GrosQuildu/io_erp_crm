package main.java.erp_crm.frontend.contacts;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import main.java.erp_crm.backend.api.common.ContactsApi;
import main.java.erp_crm.backend.model.DBData;
import main.java.erp_crm.backend.model.crm.Contact;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ContactsController implements Initializable {

    public TableColumn<Contact, String> nameColumn;
    public TableColumn<Contact, String> telephoneColumn;
    public TableColumn<Contact, String> mailColumn;
    public TableColumn<Contact, String> streetColumn;
    public TableColumn<Contact, Boolean> vipColumn;
    public TableColumn<Contact, String> cityColumn;
    public TableView<Contact> contactsTable;
    public VBox contactsBox;
    public Button addContactBtn;
    public Button deleteContactBtn;
    public Button editContactBtn;
    public Button refreshBtn;

    private ContactsApi controller = new ContactsApi();

    private AddEditContactController addEditContactController;


    private void loadControllers() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmlFiles/erp/addEditContact.fxml"));
            loader.load();
            addEditContactController = loader.getController();
            addEditContactController.setContactsController(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadControllers();
        createColumns();
        setEvents();
        refresh();
        Bindings.bindContent(contactsTable.getItems(), DBData.getContacts());
    }

    private void createColumns() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        telephoneColumn.setCellValueFactory(new PropertyValueFactory<>("telephone"));
        mailColumn.setCellValueFactory(new PropertyValueFactory<>("mail"));
        streetColumn.setCellValueFactory(new PropertyValueFactory<>("street"));
        cityColumn.setCellValueFactory(new PropertyValueFactory<>("city"));
        vipColumn.setCellValueFactory(new PropertyValueFactory<>("vip"));
        vipColumn.setCellFactory(column -> new TableCell<Contact, Boolean>() {
            @Override
            protected void updateItem(Boolean item, boolean empty) {
                super.updateItem(item, empty);

                if (item == null || empty) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(item ? "yes" : "no");
                }
            }
        });
    }

    private void setEvents() {
        addContactBtn.setOnAction(e -> addEditContactController.show());
        refreshBtn.setOnAction(e -> refresh());
        contactsTable.setOnMouseClicked(e -> {
                if(e.getClickCount() == 2)
                    editContact();
            }
        );
        editContactBtn.setOnAction(e -> editContact());

        deleteContactBtn.setOnAction(e -> {
            Contact selected = contactsTable.getSelectionModel().getSelectedItem();
            if(selected != null){
                controller.deleteContact(selected);
            }
        });
    }

    private void editContact() {
        Contact selected = contactsTable.getSelectionModel().getSelectedItem();
        if(selected != null){
            addEditContactController.show(selected);
        }
    }


    void refresh() {
        controller.getContacts();
    }
}
