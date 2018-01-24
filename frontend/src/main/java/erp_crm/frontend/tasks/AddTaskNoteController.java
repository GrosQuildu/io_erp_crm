package main.java.erp_crm.frontend.tasks;

import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import main.java.erp_crm.Main;
import main.java.erp_crm.backend.Utils;
import main.java.erp_crm.backend.model.crm.TaskNote;

import java.net.URL;
import java.util.ResourceBundle;

public class AddTaskNoteController implements Initializable {
    public TextArea noteContentArea;
    public Button addBtn;
    public Button cancelBtn;
    public VBox mainBox;
    public ColorPicker backgroundColorPicker;
    private Stage stage = new Stage();
    private AddEditTaskController addEditTaskController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Scene scene = new Scene(mainBox);
        scene.getStylesheets().add(Main.css);
        stage.setScene(scene);
        setEvents();

    }

    private void setEvents() {
        cancelBtn.setOnAction(e -> close());
        addBtn.setOnAction(e -> {
            TaskNote note = createNote();
            addEditTaskController.addNote(note);
            close();
        });
    }

    private TaskNote createNote() {
        TaskNote note = new TaskNote();
        note.setContent(noteContentArea.getText());
        Color color = backgroundColorPicker.getValue();
        note.setBackgroundColor(Utils.color2rgb(color));
        return note;
    }


    private void close() {
        noteContentArea.clear();
        stage.close();
    }

    public void setAddEditTaskController(AddEditTaskController addEditTaskController) {
        this.addEditTaskController = addEditTaskController;
    }

    public void show() {
        stage.show();
    }
}
