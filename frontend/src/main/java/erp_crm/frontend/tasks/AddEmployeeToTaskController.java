package main.java.erp_crm.frontend.tasks;

import javafx.beans.binding.Bindings;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.java.erp_crm.Main;
import main.java.erp_crm.backend.model.DBData;
import main.java.erp_crm.backend.model.common.Employee;
import main.java.erp_crm.frontend.meetings.AddEditMeetingController;

import java.net.URL;
import java.util.ResourceBundle;

public class AddEmployeeToTaskController implements Initializable {
    public TableView<Employee> employeeTableView;
    public VBox mainBox;
    public TableColumn nameColumn;
    public TableColumn mailColumn;
    public Button addBtn;
    public Button cancelBtn;

    private Stage stage = new Stage();
    private AddEditTaskController addEditTaskController;
    private AddEditMeetingController addEditMeetingController;

    private void setEvents() {
        addBtn.setOnAction(e -> {
            save();
            close();
        });
        cancelBtn.setOnAction(e -> close());
    }

    private void close() {
        stage.close();
    }

    private void save() {
        Employee item = employeeTableView.getSelectionModel().getSelectedItem();
        if (item != null) {
            if(addEditTaskController != null) {
                addEditTaskController.addEmployeeCommissioned(item);
            } else if(addEditMeetingController != null){
                addEditMeetingController.addEmployee(item);
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Scene scene = new Scene(mainBox);
        scene.getStylesheets().add(Main.css);
        stage.setScene(scene);
        setEvents();
        setColumns();
        Bindings.bindContent(employeeTableView.getItems(), DBData.getEmployees());
    }

    private void setColumns() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        mailColumn.setCellValueFactory(new PropertyValueFactory<>("mail"));
    }

    public void setAddEditTaskController(AddEditTaskController addEditTaskController) {
        this.addEditTaskController = addEditTaskController;
    }

    public void setAddEditMeetingController(AddEditMeetingController addEditMeetingController) {
        this.addEditMeetingController = addEditMeetingController;
    }

    public void show() {
        stage.show();
    }
}
