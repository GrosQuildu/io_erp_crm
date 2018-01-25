package main.java.erp_crm.frontend.tasks;

import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.java.erp_crm.Main;
import main.java.erp_crm.backend.model.DBData;
import main.java.erp_crm.backend.model.crm.TaskComment;
import org.joda.time.LocalDate;

import java.net.URL;
import java.util.ResourceBundle;

public class AddCommentController implements Initializable{
    public VBox mainBox;
    public TextArea commentContentArea;
    public Button addBtn;
    public Button cancelBtn;


    private AddEditTaskController addEditTaskController;
    private Stage stage = new Stage();

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
            TaskComment comment = createComment();
            addEditTaskController.addComment(comment);
            close();
        });
    }

    private TaskComment createComment() {
        TaskComment comment = new TaskComment();
        comment.setContent(commentContentArea.getText());
        comment.setTime(LocalDate.now());
        comment.setEmployee(DBData.getLoggedUser());
        return comment;
    }

    private void close() {
        commentContentArea.clear();
        stage.close();
    }

    public void setAddEditTaskController(AddEditTaskController addEditTaskController) {
        this.addEditTaskController = addEditTaskController;
    }

    public void show() {
        initializeFields();
        stage.show();
    }

    private void initializeFields() {
        commentContentArea.setText("");
    }
}
