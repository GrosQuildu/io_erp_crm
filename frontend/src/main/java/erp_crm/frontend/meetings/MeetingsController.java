package main.java.erp_crm.frontend.meetings;

import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import main.java.erp_crm.backend.api.crm.MeetingsControllerApi;
import main.java.erp_crm.backend.model.DBData;
import main.java.erp_crm.backend.model.crm.Contact;
import main.java.erp_crm.backend.model.crm.Meeting;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MeetingsController implements Initializable{
    public TableView<Meeting> meetingsTableView;
    public TableColumn<Meeting, LocalDate> meetingDateColumn;
    public TableColumn<Meeting, String> purposeColumn;
    public TableColumn<Meeting, String> descriptionColumn;
    public TableColumn<Meeting, LocalDate> nextMeetingDateColumn;
    public Button addMeetingBtn;
    public Button deleteMeetingBtn;
    public Button editMeetingBtn;
    public Button refreshBtn;
    public VBox mainBox;

    private AddEditMeetingController addEditMeetingController;
    private MeetingsControllerApi meetingsControllerApi = new MeetingsControllerApi();

    private FXMLLoader loader;

    public MeetingsController(){

        try {
            loader = new FXMLLoader(getClass().getResource("/fxmlFiles/crm/addEditMeeting.fxml"));
            loader.load();
            addEditMeetingController = loader.getController();
            addEditMeetingController.setMeetingsController(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        setColumns();
        setEvents();
        Bindings.bindContent(meetingsTableView.getItems(), DBData.getMeetings());
        refresh();
    }

    private void setEvents() {
        addMeetingBtn.setOnAction(e -> addEditMeetingController.show());
        refreshBtn.setOnAction(e -> refresh());
        editMeetingBtn.setOnAction(e -> {
            Meeting selected = meetingsTableView.getSelectionModel().getSelectedItem();
            if(selected!=null) {
                addEditMeetingController.show(selected);
            }
        });
    }

    private void refresh() {
        meetingsControllerApi.getMeetings();
    }

    private void setColumns() {
        meetingDateColumn.setCellValueFactory(new PropertyValueFactory<>("meetingDate"));
        meetingDateColumn.setCellFactory(column -> new TableCell<Meeting, LocalDate>() {
            @Override
            protected void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);

                if (item == null || empty) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(item.toString(DateTimeFormat.forPattern("dd.MM.yyyy").print(item)));
                }
            }
        });
        nextMeetingDateColumn.setCellValueFactory(new PropertyValueFactory<>("nextMeetingDate"));
        nextMeetingDateColumn.setCellFactory(column -> new TableCell<Meeting, LocalDate>() {
            @Override
            protected void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);

                if (item == null || empty) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(item.toString(DateTimeFormat.forPattern("dd.MM.yyyy").print(item)));
                }
            }
        });
        purposeColumn.setCellValueFactory(new PropertyValueFactory<>("purpose"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

    }
}
