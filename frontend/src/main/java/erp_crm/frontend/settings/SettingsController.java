package main.java.erp_crm.frontend.settings;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import main.java.erp_crm.backend.SharedData;
import main.java.erp_crm.backend.api.Config;
import main.java.erp_crm.backend.api.common.ClientTypesApi;
import main.java.erp_crm.backend.api.common.ContactGroupApi;
import main.java.erp_crm.backend.api.crm.TaskStatusControllerApi;
import main.java.erp_crm.backend.api.erp.UnitControllerApi;
import main.java.erp_crm.backend.model.DBData;
import main.java.erp_crm.backend.model.common.ClientType;
import main.java.erp_crm.backend.model.common.Unit;
import main.java.erp_crm.backend.model.crm.ContactGroup;
import main.java.erp_crm.backend.model.crm.TaskStatus;
import main.java.erp_crm.frontend.clients.AddClientTypeController;
import main.java.erp_crm.frontend.contacts.AddContactGroupController;
import main.java.erp_crm.frontend.tasks.AddTaskStatusController;
import main.java.erp_crm.frontend.units.AddUnitController;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SettingsController implements Initializable {
    public TableView<ClientType> clientTypesTableView;
    public Button addClientTypeBtn;
    public Button deleteClientTypeBtn;
    public Button addUnitBtn;
    public Button deleteUnitBtn;
    public TableView<Unit> unitsTableView;
    public TableColumn clientTypeDescriptionColumn;
    public TableColumn unitNameColumn;
    public TableColumn unitNameShortColumn;
    public TextArea bankArea;
    public TextArea providerArea;
    public TextField logoField;
    public TableView<TaskStatus> statusesTableView;
    public TableColumn descriptionColumn;
    public Button addStatusBtn;
    public Button deleteStatusBtn;
    public TableView<ContactGroup> contactGroupTableView;
    public TableColumn contactGroupDescriptionColumn;
    public Button addContactGroupBtn;
    public Button deleteContactGroupBtn;
    @FXML
    private TextField senderField;
    @FXML
    private TextField ccField;
    @FXML
    private Button saveSettingsBtn;
    @FXML
    private TextField mailAddressField;
    @FXML
    private TextField serverField;
    @FXML
    private TextField portField;
    @FXML
    private TextField mailPasswordField;
    @FXML
    private TextField firstNameField;
    @FXML
    private TextField nameField;
    @FXML
    private TextField mailField;
    @FXML
    private TextField telephoneField;
    @FXML
    private PasswordField passField;
    @FXML
    private Button saveProfileBtn;
    @FXML
    private TextArea ordersArea, commentsArea, mailContentArea;
    @FXML
    private Button saveDefaultBtn;
    @FXML
    private TextField proformaPathField, ordersPathField;
    @FXML
    private TextField scanField;

    private ClientTypesApi clientTypesApi = new ClientTypesApi();
    private UnitControllerApi unitControllerApi = new UnitControllerApi();

    private AddClientTypeController addClientTypeController;

    private AddUnitController addUnitController;
    private AddTaskStatusController addTaskStatusController;
    private TaskStatusControllerApi taskStatusControllerApi = new TaskStatusControllerApi();
    private AddContactGroupController addContactGroupController;
    private ContactGroupApi contactGroupApi = new ContactGroupApi();

    public static String getUserDataDirectory() {
        return System.getProperty("user.home") + "/.ERP-MB/";
    }

    public void refresh() {
        unitControllerApi.getUnits();
        clientTypesApi.getClientTypes();
        taskStatusControllerApi.getTaskStatuses();
        contactGroupApi.getContactGroups();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        setEvents();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmlFiles/erp/addUnit.fxml"));
            loader.load();
            addUnitController = loader.getController();
            addUnitController.setSettingsController(this);

            loader = new FXMLLoader(getClass().getResource("/fxmlFiles/erp/addClientType.fxml"));
            loader.load();
            addClientTypeController = loader.getController();
            addClientTypeController.setSettingsController(this);

            loader = new FXMLLoader(getClass().getResource("/fxmlFiles/crm/addTaskStatus.fxml"));
            loader.load();
            addTaskStatusController = loader.getController();
            addTaskStatusController.setSettingsController(this);

            loader = new FXMLLoader(getClass().getResource("/fxmlFiles/erp/addContactGroup.fxml"));
            loader.load();
            addContactGroupController = loader.getController();
            addContactGroupController.setSettingsController(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Bindings.bindContent(unitsTableView.getItems(), DBData.getUnits());
        Bindings.bindContent(clientTypesTableView.getItems(), DBData.getClientTypes());
        Bindings.bindContent(statusesTableView.getItems(), DBData.getTaskStatuses());
        Bindings.bindContent(contactGroupTableView.getItems(), DBData.getContactGroups());
        refresh();
        fillFields();

    }

    private void fillFields() {
        Config config = SharedData.getConfig();
        //Profile
        firstNameField.setText(config.getEmployeeFirstName());

        nameField.setText(config.getEmployeeName());
        mailField.setText(config.getEmployeeMail());
        telephoneField.setText(config.getEmployeeTelephone());
        passField.setText(config.getEmployeePassword());
        scanField.setText(config.getSignatureScanPath());


        //Mail
        serverField.setText(config.getMailServerAddress());
        portField.setText(config.getMailPort());
        mailAddressField.setText(config.getMailAddress());
        mailPasswordField.setText(config.getMailPassword());
        ccField.setText(config.getDefaultCC());
        senderField.setText(config.getDefaultSenderName());


        //Default values
        ordersArea.setText(config.getOrdersConditions());
        commentsArea.setText(config.getOrdersComments());
        mailContentArea.setText(config.getMailContent());
        ordersPathField.setText(config.getDefaultOrderPdfPath());
        proformaPathField.setText(config.getDefaultProformaPdfPath());
    }

    private void setEvents() {
        deleteClientTypeBtn.setOnAction(e -> {
            ClientType item = clientTypesTableView.getSelectionModel().getSelectedItem();
            if(item!=null) {
                clientTypesApi.deleteClientType(item);
                refresh();
            }
        });
        deleteUnitBtn.setOnAction(e ->{
            Unit item = unitsTableView.getSelectionModel().getSelectedItem();
            if(item!=null) {
                unitControllerApi.deleteUnit(item);
                refresh();
            }
        });
        addClientTypeBtn.setOnAction(e -> addClientTypeController.show());
        addContactGroupBtn.setOnAction(e -> addContactGroupController.show());
        deleteContactGroupBtn.setOnAction(e -> {
            ContactGroup item = contactGroupTableView.getSelectionModel().getSelectedItem();
            if(item!=null) {
                contactGroupApi.deleteContactGroup(item);
                refresh();
            }

        });
        addUnitBtn.setOnAction(e -> addUnitController.show());

        addStatusBtn.setOnAction(e -> addTaskStatusController.show());
        deleteStatusBtn.setOnAction(e -> {
            TaskStatus item = statusesTableView.getSelectionModel().getSelectedItem();
            if(item!=null) {
                taskStatusControllerApi.deleteTaskStatus(item);
                refresh();
            }

        });

        saveSettingsBtn.setOnAction(e -> saveConfig());
        saveProfileBtn.setOnAction(e -> saveConfig());
        proformaPathField.setOnMouseClicked(event -> {
            proformaPathField.setText(showFolderChooser());
        });
        ordersPathField.setOnMouseClicked(event -> {
            ordersPathField.setText(showFolderChooser());
        });

        scanField.setOnMouseClicked(e -> {
            scanField.setText(showFolderChooser());
        });
        logoField.setOnMouseClicked(e -> {
            logoField.setText(showFolderChooser());
        });
        saveDefaultBtn.setOnAction(e -> saveConfig());
    }

    private String showFolderChooser() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        return directoryChooser.showDialog(new Stage()).getPath();
    }

    private void saveConfig() {
        Config config = SharedData.getConfig();
        //profile
        config.setEmployeeFirstName(firstNameField.getText());
        config.setEmployeeName(nameField.getText());
        config.setEmployeeMail(mailField.getText());
        config.setEmployeeTelephone(telephoneField.getText());
        config.setEmployeePassword(passField.getText());
        config.setSignatureScanPath(scanField.getText());

        //Mail
        config.setMailServerAddress(serverField.getText());
        config.setMailPort(portField.getText());
        config.setMailAddress(mailAddressField.getText());
        config.setMailPassword(mailPasswordField.getText());
        config.setDefaultCC(ccField.getText());
        config.setDefaultSenderName(senderField.getText());

        //Default values
        config.setOrdersConditions(ordersArea.getText());
        config.setOrdersComments(commentsArea.getText());
        config.setMailContent(mailContentArea.getText());
        config.setDefaultOrderPdfPath(ordersPathField.getText());
        config.setDefaultProformaPdfPath(proformaPathField.getText());
        config.setBank(bankArea.getText());
        config.setProviderData(providerArea.getText());
        config.setCompanyLogoPath(logoField.getText());

        //Save & set new config
        config.saveConfig(config);
        SharedData.setConfig(config);
    }

}
