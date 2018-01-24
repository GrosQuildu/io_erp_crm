package main.java.erp_crm.frontend.tasks;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import main.java.erp_crm.backend.api.crm.TaskControllerApi;
import main.java.erp_crm.backend.model.DBData;
import main.java.erp_crm.backend.model.common.Employee;
import main.java.erp_crm.backend.model.crm.Meeting;
import main.java.erp_crm.backend.model.crm.Task;
import main.java.erp_crm.backend.model.crm.TaskStatus;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class TasksController  implements Initializable{
    public VBox mainBox;
    public TableView<Task> tasksTableView;
    public TableColumn<Task, String> titleColumn;
    public TableColumn<Task, LocalDate> startDateColumn;
    public TableColumn<Task, LocalDate> endDateColumn;
    public TableColumn<Task, Employee> employeeColumn;
    public TableColumn<Task, Employee> employeeCommissionedColumn;
    public TableColumn<Task, TaskStatus> statusColumn;
    public Button addTaskBtn;
    public Button deleteTaskBtn;
    public Button editTaskBtn;
    public Button refreshBtn;
    public TableColumn<Task, String> colorColumn;

    private AddEditTaskController addEditTaskController;

    private FXMLLoader loader;
    private TaskControllerApi tasksControllerApi = new TaskControllerApi();

    public TasksController(){

        try {
            loader = new FXMLLoader(getClass().getResource("/fxmlFiles/crm/addEditTask.fxml"));
            loader.load();
            addEditTaskController = loader.getController();
            addEditTaskController.setTasksController(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        setColumns();
        setEvents();
        Bindings.bindContent(tasksTableView.getItems(), DBData.getTasks());
        refresh();
    }

    private void setEvents() {
        addTaskBtn.setOnAction(e -> addEditTaskController.show());
        deleteTaskBtn.setOnAction(e -> {
            Task selected = tasksTableView.getSelectionModel().getSelectedItem();
            if(selected!=null){
                tasksControllerApi.deleteTask(selected);
                refresh();
            }
        });
        editTaskBtn.setOnAction(e -> {
            Task selected = tasksTableView.getSelectionModel().getSelectedItem();
            if(selected!=null){
                addEditTaskController.show(selected);
            }
        });
        refreshBtn.setOnAction(e -> refresh());
    }

    private void setColumns() {
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        startDateColumn.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        startDateColumn.setCellFactory(column -> new TableCell<Task, LocalDate>() {
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

        endDateColumn.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        endDateColumn.setCellFactory(column -> new TableCell<Task, LocalDate>() {
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

        employeeColumn.setCellValueFactory(new PropertyValueFactory<>("employee"));
        employeeColumn.setCellFactory(column -> new TableCell<Task, Employee>() {
            @Override
            protected void updateItem(Employee item, boolean empty) {
                super.updateItem(item, empty);

                if (item == null || empty) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(item.getName());
                }
            }
        });
        employeeCommissionedColumn.setCellValueFactory(new PropertyValueFactory<>("employeeCommissioned"));
        employeeCommissionedColumn.setCellFactory(column -> new TableCell<Task, Employee>() {
            @Override
            protected void updateItem(Employee item, boolean empty) {
                super.updateItem(item, empty);

                if (item == null || empty) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(item.getName());
                }
            }
        });

        statusColumn.setCellValueFactory(new PropertyValueFactory<>("taskStatus"));
        statusColumn.setCellFactory(column -> new TableCell<Task, TaskStatus>() {
            @Override
            protected void updateItem(TaskStatus item, boolean empty) {
                super.updateItem(item, empty);

                if (item == null || empty) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(item.getDescription());
                }
            }
        });

        colorColumn.setCellValueFactory(new PropertyValueFactory<>("backgroundColor"));
        colorColumn.setCellFactory(column -> new TableCell<Task, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);

                if (item == null || empty) {
                    setText(null);
                    setStyle("");
                } else {
                    TableRow currentRow = getTableRow();

                    if (!item.isEmpty() && currentRow!=null) {
                        currentRow.setStyle("-fx-background-color: " + item);
                    }
                    setText(item);
                }
            }
        });
    }

    public void refresh() {
        tasksControllerApi.getTasks();
    }
}
