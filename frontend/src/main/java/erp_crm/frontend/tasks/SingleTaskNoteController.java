package main.java.erp_crm.frontend.tasks;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import main.java.erp_crm.backend.model.crm.TaskNote;

import java.net.URL;
import java.util.ResourceBundle;

public class SingleTaskNoteController implements Initializable{
    public Button deleteBtn;
    public Button saveBtn;
    public VBox mainBox;
    public TextArea contentArea;


    private AddEditTaskController addEditTaskController;
    private TaskNote note;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setEvents();
    }

    private void setEvents() {

        deleteBtn.setOnAction(e -> delete());
        saveBtn.setOnAction(e -> save());
    }

    private void save() {
        if(note!=null) {
            addEditTaskController.updateNote(note);
        }
    }

    private void delete() {
        if(note!=null) {
            addEditTaskController.deleteNote(note);
        }
    }

    public void setAddEditTaskController(AddEditTaskController addEditTaskController) {
        this.addEditTaskController = addEditTaskController;
    }

    private void fillFields() {
        contentArea.setText(note.getContent());
    }

    public VBox getMainBox() {
        return mainBox;
    }

    public void setNote(TaskNote note) {
        this.note = note;
        fillFields();
        mainBox.setStyle(generateBackgroundString(note));
    }

    private String generateBackgroundString(TaskNote note) {
        return "-fx-background-color: "+ note.getBackgroundColor() +";";
    }

    public TaskNote getNote() {
        return note;
    }
}
