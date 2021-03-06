package main.java.erp_crm.frontend.tasks;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;
import main.java.erp_crm.Main;
import main.java.erp_crm.backend.Utils;
import main.java.erp_crm.backend.api.crm.TasksApi;
import main.java.erp_crm.backend.model.DBData;
import main.java.erp_crm.backend.model.common.Employee;
import main.java.erp_crm.backend.model.crm.*;
import main.java.erp_crm.frontend.meetings.AddContactToMeetingTaskController;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.Objects;
import java.util.ResourceBundle;

public class AddEditTaskController implements Initializable{
    public TextArea descriptionArea;
    public VBox notesContainer;
    public Button addNoteBtn;
    public GridPane mainPane;
    public DatePicker beginningPicker;
    public TextField titleField;
    public ColorPicker backgroundPicker;
    public TableView<Contact> contactsTableView;
    public TableColumn contactNameColumn;
    public TableColumn contactMailColumn;
    public Button addContactBtn;
    public Button remContactBtn;
    public Label mainEmployeeLabel;
    public Button saveBtn;
    public Button cancelBtn;
    public DatePicker deadlinePicker;
    public ComboBox<TaskStatus> statusComboBox;
    public Button commissionBtn;
    public Button remCommissionBtn;
    public TextField commissionedField;
    public VBox commentsContainer;
    public Button addCommentBtn;
    public CheckBox archiveCheckBox;

    private Stage stage = new Stage();
    private TasksController tasksController;

    private AddContactToMeetingTaskController addContactToMeetingTaskController;

    private AddCommentController addCommentController;
    private AddTaskNoteController addTaskNoteController;
    private AddEmployeeToTaskController addEmployeeToTaskController;
    private TasksApi tasksApi = new TasksApi();

    private Employee commissioned = null;
    private Task task = null;
    private ObservableList<SingleCommentController> comments = FXCollections.observableArrayList();
    private ObservableList<SingleTaskNoteController> notes = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadControllers();
        Scene scene = new Scene(mainPane);
        scene.getStylesheets().add(Main.css);
        stage.setScene(scene);
        setEvents();
        setColumns();
        Bindings.bindContent(statusComboBox.getItems(), DBData.getTaskStatuses());
        initializeFields();
    }

    private void setColumns() {
        contactNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        contactNameColumn.setCellValueFactory(new PropertyValueFactory<>("mail"));
    }


    private void loadControllers() {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmlFiles/crm/addContactToMeetingTask.fxml"));
            loader.load();
            addContactToMeetingTaskController = loader.getController();
            addContactToMeetingTaskController.setAddEditTaskController(this);

            loader = new FXMLLoader(getClass().getResource("/fxmlFiles/crm/addComment.fxml"));
            loader.load();
            addCommentController = loader.getController();
            addCommentController.setAddEditTaskController(this);


            loader = new FXMLLoader(getClass().getResource("/fxmlFiles/crm/addTaskNote.fxml"));
            loader.load();
            addTaskNoteController = loader.getController();
            addTaskNoteController.setAddEditTaskController(this);


            loader = new FXMLLoader(getClass().getResource("/fxmlFiles/crm/addEmployeeToTask.fxml"));
            loader.load();
            addEmployeeToTaskController = loader.getController();
            addEmployeeToTaskController.setAddEditTaskController(this);

        }  catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initializeFields() {
        task = null;
        commissioned = null;
        prepareStatusBox();
        beginningPicker.setValue(LocalDate.now());
        deadlinePicker.setValue(LocalDate.now());
        commissionedField.setText("");
        titleField.setText("");
        mainEmployeeLabel.setText("");
        descriptionArea.setText("");
        archiveCheckBox.setSelected(false);
        backgroundPicker.setValue(Color.WHITE);
        contactsTableView.getItems().clear();
        if(statusComboBox.getItems().size()>0) {
            statusComboBox.getSelectionModel().select(0);
        }
        comments.clear();
        notes.clear();
        commentsContainer.getChildren().clear();
        notesContainer.getChildren().clear();
    }

    private void prepareStatusBox() {
        if(statusComboBox.getItems().size()>0){
            statusComboBox.getSelectionModel().select(0);
        }
        statusComboBox.setCellFactory(new Callback<ListView<TaskStatus>,ListCell<TaskStatus>>(){
            @Override
            public ListCell<TaskStatus> call(ListView<TaskStatus> l){
                return new ListCell<TaskStatus>(){
                    @Override
                    protected void updateItem(TaskStatus item, boolean empty) {
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
        statusComboBox.setConverter(new StringConverter<TaskStatus>() {
                                        @Override
                                        public String toString(TaskStatus status) {
                                            if (status == null) {
                                                return null;
                                            } else {
                                                return status.getDescription();
                                            }
                                        }

                                        @Override
                                        public TaskStatus fromString(String string) {
                                            return DBData.getTaskStatus(string);
                                        }
                                    });
    }

    private void setEvents() {
        commissionedField.setEditable(false);
        saveBtn.setOnAction(e -> {
            save();
            close();
        });
        cancelBtn.setOnAction(e -> close());
        addContactBtn.setOnAction(e -> addContact());
        remContactBtn.setOnAction(e -> remContact());
        addCommentBtn.setOnAction(e -> addComment());
        addNoteBtn.setOnAction(e -> addNote());
        remCommissionBtn.setOnAction(e -> removeCommission());
        commissionBtn.setOnAction(e -> addEmployee());
    }

    private void addEmployee() {
        addEmployeeToTaskController.show();
    }

    private void removeCommission() {
        commissioned = null;
        if(task != null){
            task.setEmployeeCommissioned(null);
            tasksApi.updateTask(task);
        }
    }

    private void addNote() {
        addTaskNoteController.show();
    }

    private void addComment() {
        addCommentController.show();
    }

    private void addContact() {
        addContactToMeetingTaskController.show();
    }

    private void save() {
        if(task==null){
            task = new Task();
            fillTask();
            tasksApi.createTask(task);
        }  else {
            fillTask();
            tasksApi.updateTask(task);
        }
        tasksController.refresh();
    }

    private void remContact() {
        Contact selected = contactsTableView.getSelectionModel().getSelectedItem();
        if(selected!=null){
            if(task!=null) {
                tasksApi.updateTask(task);
                task.getContacts().remove(selected);
            }
            contactsTableView.getItems().remove(selected);
        }
    }

    private void close() {
        task = null;
        commissioned = null;
        stage.close();
    }

    public void addContact(Contact contact) {
        contactsTableView.getItems().addAll(contact);
    }

    public void setTasksController(TasksController tasksController) {
        this.tasksController = tasksController;
    }

    public void show() {
        initializeFields();
        stage.show();
    }
    public void show(Task task){
        initializeFields();
        this.task = task;
        fillFields();
        stage.show();
    }

    private void fillTask() {
        task.setBackgroundColor(Utils.color2rgb(backgroundPicker.getValue()));
        LinkedList<TaskComment> taskComments = new LinkedList<>();
        for(SingleCommentController commentController : comments){
            taskComments.add(commentController.getComment());
        }
        task.setComments(taskComments);
        LinkedList<TaskNote> taskNotes = new LinkedList<>();
        for(SingleTaskNoteController noteController : notes){
            taskNotes.add(noteController.getNote());
        }
        task.setNotes(taskNotes);
        task.setContacts(contactsTableView.getItems());
        task.setEmployee(DBData.getLoggedUser());
        if(commissioned != null){
            task.setEmployeeCommissioned(commissioned);
        }
        task.setDescription(descriptionArea.getText());
        task.setIsArchived(archiveCheckBox.isSelected());
        task.setEndDate(org.joda.time.LocalDate.fromDateFields(Date.valueOf(deadlinePicker.getValue())));
        task.setStartDate(org.joda.time.LocalDate.fromDateFields(Date.valueOf(beginningPicker.getValue())));
        task.setTitle(titleField.getText());
        task.setTaskStatus(statusComboBox.getValue());
    }

    private void fillFields() {
        titleField.setText(task.getTitle());
        statusComboBox.setValue(task.getTaskStatus());
        task.getComments().forEach(this::addComment);
        task.getNotes().forEach(this::addNote);
        contactsTableView.getItems().setAll(task.getContacts());
        mainEmployeeLabel.setText(task.getEmployee().getName());
        if(task.getEmployeeCommissioned()!=null){
            commissionedField.setText(task.getEmployeeCommissioned().getName());
        }
        backgroundPicker.setValue(Color.valueOf(task.getBackgroundColor()));
        descriptionArea.setText(task.getDescription());
        archiveCheckBox.setSelected(task.getIsArchived());
        deadlinePicker.setValue(LocalDate.of(task.getEndDate().getYear(), task.getEndDate().getMonthOfYear(), task.getEndDate().getDayOfMonth()));
        beginningPicker.setValue(LocalDate.of(task.getStartDate().getYear(), task.getStartDate().getMonthOfYear(), task.getStartDate().getDayOfMonth()));
    }


    public void addNote(TaskNote taskNote) {
        for(SingleTaskNoteController n : notes){
            if(n.getNote().getContent().equals(taskNote.getContent()) && n.getNote().getBackgroundColor().equals(taskNote.getBackgroundColor())
                    && notesContainer.getChildren().contains(n.getMainBox())
                    ) return;
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmlFiles/crm/singleTaskNote.fxml"));
            loader.load();
            SingleTaskNoteController noteController = loader.getController();
            noteController.setAddEditTaskController(this);
            noteController.setNote(taskNote);
            notes.add(noteController);
            notesContainer.getChildren().add(noteController.getMainBox());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addComment(TaskComment comment) {
        for(SingleCommentController n : comments){
            if(n.getComment().getContent().equals(comment.getContent())
                    && commentsContainer.getChildren().contains(n.getMainBox())
                    ) return;
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmlFiles/crm/singleComment.fxml"));
            loader.load();
            SingleCommentController commentController = loader.getController();
            commentController.setAddEditTaskController(this);
            commentController.setComment(comment);
            comments.add(commentController);
            commentsContainer.getChildren().add(commentController.getMainBox());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteComment(TaskComment comment) {

        if(task!=null){
            task.getComments().remove(comment);
            tasksApi.deleteComment(task, comment);
        }
        SingleCommentController toRemove = null;
        for(SingleCommentController i : comments){
            if(i.getComment().getContent().equals(comment.getContent())){
                commentsContainer.getChildren().remove(i.getMainBox());
                toRemove = i;
            }
        }
        if(toRemove!=null)
            comments.remove(toRemove);
    }

    public void updateComment(TaskComment comment) {
        if(task!=null){
            for(TaskComment i : task.getComments()){
                if(Objects.equals(i.getId(), comment.getId())){
                   i.setContent(comment.getContent());
                }
            }
            tasksApi.updateComment(task, comment);
        }
    }

    public void deleteNote(TaskNote note) {
        if(task!=null){
            task.getNotes().remove(note);
            tasksApi.deleteNote(task, note);
        }
        SingleTaskNoteController toRemove = null;
        for(SingleTaskNoteController i : notes){
            if(i.getNote().getContent().equals(note.getContent())){
                notesContainer.getChildren().remove(i.getMainBox());
                toRemove = i;
            }
        }
        if(toRemove!=null)
            notes.remove(toRemove);
    }

    public void updateNote(TaskNote note) {
        if(task!=null){
            for(TaskNote i : task.getNotes()){
                if(Objects.equals(i.getId(), note.getId())){
                    i.setContent(note.getContent());
                }
            }
            tasksApi.updateNote(task, note);
        }
    }

    public Task getTask() {
        return task;
    }

    public void addEmployeeCommissioned(Employee item) {
        commissioned = item;
        commissionedField.setText(commissioned.getName());
    }
}
