package main.java.erp_crm.frontend.tasks;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import main.java.erp_crm.backend.model.crm.TaskComment;

import java.net.URL;
import java.util.ResourceBundle;

public class SingleCommentController implements Initializable{
    public VBox mainBox;
    public Button deleteBtn;
    public Button saveBtn;
    public TextArea contentArea;
    public Label authorLabel;
    public Label dateLabel;
    private AddEditTaskController addEditTaskController;
    private TaskComment comment;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setEvents();
    }

    private void setEvents() {

        deleteBtn.setOnAction(e -> {
            if(comment!=null) {
                addEditTaskController.deleteComment(comment);
            }
        });
        saveBtn.setOnAction(e -> {
            if(comment!=null) {
                addEditTaskController.updateComment(comment);
            }

        });
    }

    public void setAddEditTaskController(AddEditTaskController addEditTaskController) {
        this.addEditTaskController = addEditTaskController;
    }

    public void setComment(TaskComment comment) {
        this.comment = comment;
        fillFields();
    }

    private void fillFields() {
//        authorLabel.setText(comment.getEmployee().getName());
        dateLabel.setText(comment.getTime().toString());
        contentArea.setText(comment.getContent());
    }

    public TaskComment getComment() {
        return comment;
    }

    public VBox getMainBox() {
        return mainBox;
    }
}
