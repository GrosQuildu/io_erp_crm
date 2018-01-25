package main.java.erp_crm.frontend.meetings;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import main.java.erp_crm.backend.api.crm.MeetingsApi;
import main.java.erp_crm.backend.model.DBData;
import main.java.erp_crm.backend.model.crm.Meeting;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;

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
    private MeetingsApi meetingsApi = new MeetingsApi();



    private void loadControllers() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmlFiles/crm/addEditMeeting.fxml"));
            loader.load();
            addEditMeetingController = loader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadControllers();
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
        deleteMeetingBtn.setOnAction(e -> delete());
    }

    private void delete() {
        Meeting selected = meetingsTableView.getSelectionModel().getSelectedItem();
        if(selected!=null) {
            meetingsApi.deleteMeeting(selected);
        }
    }

    private void refresh() {
        meetingsApi.getMeetings();
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
