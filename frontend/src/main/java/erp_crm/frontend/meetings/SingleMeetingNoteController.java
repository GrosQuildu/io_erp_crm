package main.java.erp_crm.frontend.meetings;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import main.java.erp_crm.backend.model.crm.MeetingNote;

import java.net.URL;
import java.util.ResourceBundle;

public class SingleMeetingNoteController implements Initializable{
    public Button deleteBtn;
    public Button saveBtn;
    public VBox mainBox;
    public TextArea contentArea;
    private AddEditMeetingController addEditMeetingController;
    private MeetingNote note;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setEvents();
    }

    private void setEvents() {

        deleteBtn.setOnAction(e -> {
            if(note!=null) {
                addEditMeetingController.deleteNote(note);
            }
        });
        saveBtn.setOnAction(e -> {
            if(note!=null) {
                addEditMeetingController.updateNote(note);
            }

        });
    }

    public void setAddEditMeetingController(AddEditMeetingController addEditMeetingController) {
        this.addEditMeetingController = addEditMeetingController;
    }

    private void fillFields() {
        contentArea.setText(note.getContent());
    }

    public VBox getMainBox() {
        return mainBox;
    }

    public void setNote(MeetingNote note) {
        this.note = note;
        fillFields();
        mainBox.setStyle("-fx-background-color: "+ note.getBackgroundColor() +";");
    }

    public MeetingNote getNote() {
        return note;
    }
}
