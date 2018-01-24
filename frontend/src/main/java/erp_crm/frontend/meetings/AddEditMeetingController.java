package main.java.erp_crm.frontend.meetings;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.java.erp_crm.Main;
import main.java.erp_crm.backend.api.crm.ContactsControllerApi;
import main.java.erp_crm.backend.api.crm.MeetingsControllerApi;
import main.java.erp_crm.backend.model.DBData;
import main.java.erp_crm.backend.model.common.Employee;
import main.java.erp_crm.backend.model.crm.Contact;
import main.java.erp_crm.backend.model.crm.Meeting;
import main.java.erp_crm.backend.model.crm.MeetingNote;
import main.java.erp_crm.backend.model.crm.TaskNote;
import main.java.erp_crm.frontend.tasks.AddEmployeeToTaskController;
import main.java.erp_crm.frontend.tasks.SingleTaskNoteController;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

public class AddEditMeetingController implements Initializable {
    public DatePicker meetingDatePicker;


    public TableView<Employee> employeesTableView;
    public TableColumn<Employee, String> employeeNameColumn;
    public TableColumn<Employee, String> employeeMailColumn;


    public Button addEmployeeBtn;
    public Button remEmployeeBtn;


    public TableView<Contact> contactsTableView;
    public TableColumn<Contact, String> contactNameColumn;
    public TableColumn<Contact, String> contactMailColumn;


    public Button addContactBtn;
    public Button remContactBtn;
    public DatePicker nextMeetingPicker;
    public TextArea descriptionArea;
    public TextArea purposeArea;
    public Label mainEmployeeLabel;
    public CheckBox telephoneMeetingCheckBox;
    public VBox notesContainer;
    public Button addNoteBtn;
    public GridPane mainPane;
    public Button saveBtn;
    public Button cancelBtn;

    private AddContactToMeetingTaskController addContactToMeetingTaskController;
    private AddMeetingNoteController addMeetingNoteController;
    private AddEmployeeToTaskController addEmployeeToTaskController;
    private ObservableList<SingleMeetingNoteController> notes = FXCollections.observableArrayList();

    private Stage stage = new Stage();
    private MeetingsController meetingsController;
    private Meeting meeting;
    private MeetingsControllerApi meetingsControllerApi = new MeetingsControllerApi();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmlFiles/crm/addContactToMeetingTask.fxml"));
            loader.load();
            addContactToMeetingTaskController = loader.getController();
            addContactToMeetingTaskController.setAddEditMeetingController(this);



            loader = new FXMLLoader(getClass().getResource("/fxmlFiles/crm/addMeetingNote.fxml"));
            loader.load();
            addMeetingNoteController = loader.getController();
            addMeetingNoteController.setAddEditMeetingController(this);


            loader = new FXMLLoader(getClass().getResource("/fxmlFiles/crm/addEmployeeToTask.fxml"));
            loader.load();
            addEmployeeToTaskController = loader.getController();
            addEmployeeToTaskController.setAddEditMeetingController(this);

        }  catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(mainPane);
        scene.getStylesheets().add(Main.css);
        stage.setScene(scene);
        setColumns();
        setEvents();
        meetingDatePicker.setValue(LocalDate.now());
        nextMeetingPicker.setValue(LocalDate.now());
    }

    private void setEvents() {
        saveBtn.setOnAction(e -> {
            if(meeting==null){
                meeting = new Meeting();
                meeting.setNotes(new ArrayList<>());
                fillMeeting();
                meetingsControllerApi.createMeeting(meeting);
            }  else {
                fillMeeting();
                meetingsControllerApi.updateMeeting(meeting);
            }
            close();
        });
        cancelBtn.setOnAction(e -> stage.close());
        addContactBtn.setOnAction(e -> addContactToMeetingTaskController.show());
        remContactBtn.setOnAction(e -> remContact());
        addNoteBtn.setOnAction(e -> addMeetingNoteController.show());
        remEmployeeBtn.setOnAction(e -> {
            Employee selected = employeesTableView.getSelectionModel().getSelectedItem();
            if(selected != null){
                if(meeting!=null) {
                    meetingsControllerApi.updateMeeting(meeting);
                }
                employeesTableView.getItems().remove(selected);
            }
        });
        addEmployeeBtn.setOnAction(e -> addEmployeeToTaskController.show());
    }

    private void remContact() {
        Contact selected = contactsTableView.getSelectionModel().getSelectedItem();
        if(selected!=null) {
            contactsTableView.getItems().remove(selected);
        }
    }

    private void close() {
        meeting = null;
        stage.close();
    }

    private void fillMeeting() {
        meeting.setMeetingDate(org.joda.time.LocalDate.fromDateFields(Date.valueOf(meetingDatePicker.getValue())));
        meeting.setNextMeetingDate(org.joda.time.LocalDate.fromDateFields(Date.valueOf(nextMeetingPicker.getValue())));
        meeting.setDescription(descriptionArea.getText());
        notes.forEach(note -> meeting.getNotes().add(note.getNote()));
        meeting.setEmployees(employeesTableView.getItems());
        meeting.setMainEmployee(meeting.getMainEmployee() != null ? meeting.getMainEmployee() : DBData.getLoggedUser());
        meeting.setContacts(contactsTableView.getItems());
        meeting.setPurpose(purposeArea.getText());
        meeting.setTelephoneMeeting(telephoneMeetingCheckBox.isSelected());
    }

    private void setColumns() {
        employeeMailColumn.setCellValueFactory(new PropertyValueFactory<>("mail"));
        employeeNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        contactMailColumn.setCellValueFactory(new PropertyValueFactory<>("mail"));
        contactNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
    }

    public void addContact(Contact item) {
        contactsTableView.getItems().add(item);
    }

    public void updateNote(MeetingNote note) {
        if(meeting!=null){
            for(MeetingNote i : meeting.getNotes()){
                if(Objects.equals(i.getId(), note.getId())){
                    i.setContent(note.getContent());
                }
            }
            meetingsControllerApi.updateNote(meeting, note);
        }
    }

    public void deleteNote(MeetingNote note) {
        if(meeting!=null){
            meeting.getNotes().remove(note);
            meetingsControllerApi.deleteNote(meeting, note);
        }
        SingleMeetingNoteController toRemove = null;
        for(SingleMeetingNoteController i : notes){
            if(i.getNote().getContent().equals(note.getContent())){
                notesContainer.getChildren().remove(i.getMainBox());
                toRemove = i;
            }
        }
        if(toRemove!=null)
            notes.remove(toRemove);
    }

    public void addEmployee(Employee employee){
        employeesTableView.getItems().add(employee);
    }

    public void addNote(MeetingNote note) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmlFiles/crm/singleMeetingNote.fxml"));
            loader.load();
            SingleMeetingNoteController noteController = loader.getController();
            noteController.setAddEditMeetingController(this);
            noteController.setNote(note);
            notes.add(noteController);
            notesContainer.getChildren().add(noteController.getMainBox());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setMeetingsController(MeetingsController meetingsController) {
        this.meetingsController = meetingsController;
    }

    public void show() {
        stage.show();
    }
    public void show(Meeting meeting) {
        this.meeting = meeting;
        fillFields();
        stage.show();
    }

    private void fillFields() {
        meetingDatePicker.setValue(LocalDate.of(meeting.getMeetingDate().getYear(), meeting.getMeetingDate().getMonthOfYear(), meeting.getMeetingDate().getDayOfMonth()));
        nextMeetingPicker.setValue(LocalDate.of(meeting.getNextMeetingDate().getYear(), meeting.getNextMeetingDate().getMonthOfYear(), meeting.getNextMeetingDate().getDayOfMonth()));
        descriptionArea.setText(meeting.getDescription());
        meeting.getNotes().forEach(this::addNote);
        employeesTableView.getItems().setAll(meeting.getEmployees());
        mainEmployeeLabel.setText("Employee: " + meeting.getMainEmployee().getName());
        contactsTableView.getItems().setAll(meeting.getContacts());
        purposeArea.setText(meeting.getPurpose());
        telephoneMeetingCheckBox.setSelected(meeting.getTelephoneMeeting());

    }

}