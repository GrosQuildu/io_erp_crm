package main.java.erp_crm.frontend.contacts;

import javafx.beans.binding.Bindings;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;
import main.java.erp_crm.Main;
import main.java.erp_crm.backend.api.crm.ContactsControllerApi;
import main.java.erp_crm.backend.model.DBData;
import main.java.erp_crm.backend.model.common.Client;
import main.java.erp_crm.backend.model.common.Employee;
import main.java.erp_crm.backend.model.crm.Contact;
import main.java.erp_crm.backend.model.crm.ContactGroup;

import java.net.URL;
import java.util.ResourceBundle;

public class AddEditContactController implements Initializable{
    public VBox mainBox;
    private Stage stage = new Stage();
    public TextField nameField;
    public TextField streetField;
    public TextField cityField;
    public TextField postCodeField;
    public TextField telephoneField;
    public TextField mailField;
    public ComboBox<ContactGroup> contactGroupBox;
    public Button saveBtn;
    public Button cancelBtn;
    public ComboBox<Employee> employeeBox;
    public ComboBox<Client> clientComboBox;
    public CheckBox vipBox;
    private ContactsController contactsController;
    private ContactsControllerApi controller = new ContactsControllerApi();
    private Contact contact;
    

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        setEvents();
        Scene scene = new Scene(mainBox);
        scene.getStylesheets().add(Main.css);
        stage.setWidth(300);
        stage.setScene(scene);
        Bindings.bindContent(contactGroupBox.getItems(), DBData.getContactGroups());
        Bindings.bindContent(clientComboBox.getItems(), DBData.getClients());
        Bindings.bindContent(employeeBox.getItems(), DBData.getEmployees());
        refresh();
        if(contactGroupBox.getItems().size()>0){
            contactGroupBox.getSelectionModel().select(0);
        }
        if(clientComboBox.getItems().size()>0){
            clientComboBox.getSelectionModel().select(0);
        }
        if(employeeBox.getItems().size()>0){
            employeeBox.getSelectionModel().select(0);
        }
        employeeBox.setCellFactory(new Callback<ListView<Employee>,ListCell<Employee>>(){
            @Override
            public ListCell<Employee> call(ListView<Employee> l){
                return new ListCell<Employee>(){
                    @Override
                    protected void updateItem(Employee item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item == null || empty) {
                            setGraphic(null);
                        } else {
                            setText(item.getName());
                        }
                    }
                } ;
            }
        });
        employeeBox.setConverter(new StringConverter<Employee>() {
            @Override
            public String toString(Employee status) {
                if (status == null) {
                    return null;
                } else {
                    return status.getName();
                }
            }

            @Override
            public Employee fromString(String string) {
                return DBData.getEmployee(string);
            }
        });
        contactGroupBox.setCellFactory(new Callback<ListView<ContactGroup>,ListCell<ContactGroup>>(){
            @Override
            public ListCell<ContactGroup> call(ListView<ContactGroup> l){
                return new ListCell<ContactGroup>(){
                    @Override
                    protected void updateItem(ContactGroup item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item == null || empty) {
                            setGraphic(null);
                        } else {
                            setText(item.getDescription());
                        }
                    }
                } ;
            }
        });
        contactGroupBox.setConverter(new StringConverter<ContactGroup>() {
            @Override
            public String toString(ContactGroup status) {
                if (status == null) {
                    return null;
                } else {
                    return status.getDescription();
                }
            }

            @Override
            public ContactGroup fromString(String string) {
                return DBData.getContactGroup(string);
            }
        });
    }

    private void refresh() {
        controller.getContacts();
    }

    private void setEvents() {
        saveBtn.setOnAction(e -> {
            if(contact!=null){
                fillContact();
                controller.updateContact(contact);
            } else {
                contact = new Contact();
                fillContact();
                controller.createContact(contact);
            }
            contactsController.refresh();
            stage.close();
        });
        cancelBtn.setOnAction(e -> stage.close());
    }

    public void setContactsController(ContactsController contactsController) {
        this.contactsController = contactsController;
    }

    private void fillFields(Contact contact) {
        this.contact = contact;
        nameField.setText(contact.getName());
        streetField.setText(contact.getStreet());
        cityField.setText(contact.getCity());
        postCodeField.setText(contact.getPostCode());
        
        telephoneField.setText(contact.getTelephone());
        mailField.setText(contact.getMail());

        if(contact.getContactGroup()!=null)
            contactGroupBox.setValue(contact.getContactGroup());
        if(contact.getClient()!=null)
            clientComboBox.setValue(contact.getClient());
        vipBox.setSelected(contact.getVip());
        
    }
    private void fillContact() {
        contact.setName(nameField.getText());
        contact.setStreet(streetField.getText());
        contact.setCity(cityField.getText());
        contact.setPostCode(postCodeField.getText());
        contact.setTelephone(telephoneField.getText());
        contact.setMail(mailField.getText());
        contact.setContactGroup(contactGroupBox.getValue());
        contact.setVip(vipBox.isSelected());
        contact.setClient(clientComboBox.getValue());
        contact.setEmployee(employeeBox.getValue());
    }
    public void show() {
        stage.show();
    }
    public void show(Contact contact) {
        this.contact = contact;
        fillFields(contact);
        stage.show();
    }
    private void close(){
        contact = null;
        stage.close();
    }
}
