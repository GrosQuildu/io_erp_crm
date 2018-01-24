package io.swagger.api.crm;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;
import io.swagger.Swagger2SpringBoot;
import io.swagger.api.ITHelper;
import io.swagger.model.common.Employee;
import io.swagger.model.common.EmployeeRepository;
import io.swagger.model.crm.Task;
import io.swagger.model.crm.TaskRepository;
import io.swagger.model.crm.TaskStatus;
import io.swagger.model.crm.TaskStatusRepository;
import org.apache.http.HttpStatus;
import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItems;

@RunWith(SpringJUnit4ClassRunner.class)
@Configuration
@ContextConfiguration
@TestPropertySource("classpath:application-test.properties")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = Swagger2SpringBoot.class)
public class TasksApiControllerIT {

    private static final String RESOURCE = "/api/crm/tasks";

    private static String adminToken = "";
    private static String crmToken = "";
    private static String erpToken = "";

    private static Employee employee1;
    private static Employee employee2;

    private static TaskStatus taskStatus1;
    private static TaskStatus taskStatus2;

    private static LocalDate endDate;
    private static LocalDate startDate;

    private static Task task1;
    private static Task task2;

    private static String invalidToken;

    @Autowired
    private TaskStatusRepository taskStatusRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private TaskRepository repository;

    @LocalServerPort
    private int serverPort;

    @Before
    public void setUp() {
        RestAssured.port = serverPort;
        if (adminToken.isEmpty()) {
            adminToken = ITHelper.getToken(Employee.Role.ADMIN);
            crmToken = ITHelper.getToken(Employee.Role.CRM);
            erpToken = ITHelper.getToken(Employee.Role.ERP);
        }

        employee1 = new Employee(null, "employee1", "employee111@test.com", "password123", Employee.Role.CRM);
        employee1 = employeeRepository.save(employee1);
        employee2 = new Employee(null, "employee2", "employee222@test.com", "password123", Employee.Role.ERP);
        employee2 = employeeRepository.save(employee2);

        taskStatus1 = new TaskStatus(null, "status 0");
        taskStatus1 = taskStatusRepository.save(taskStatus1);
        taskStatus2 = new TaskStatus(null, "status 1");
        taskStatus2 = taskStatusRepository.save(taskStatus2);

        endDate = new LocalDate("2017-12-12");
        startDate = new LocalDate("2015-02-01");

        task1 = new Task(null, "Task 1", taskStatus1, employee1, "black", endDate, startDate);
        task2 = new Task(null, "Task 2", taskStatus2, employee2, "black", endDate, startDate);

        invalidToken = "XXX-9a20-4b49-97a9-YYY";
    }

    @After
    public void clear() {
        repository.deleteAll();
        for (Employee employee : employeeRepository.findAll()) {
            if(employee.getId() > 3)
                employeeRepository.delete(employee.getId());
        }
        taskStatusRepository.deleteAll();
    }
    @Test
    public void createTaskWithoutAuthShouldReturn401Error() {
        given()
                .contentType("application/json")
                .when()
                .body(task1)
                .post(RESOURCE)
                .then()
                .statusCode(HttpStatus.SC_UNAUTHORIZED);
    }

    @Test
    public void createTaskWithWrongUserTokenShouldReturnAuthError() {
        given()
                .header("Authorization", "Bearer " + erpToken)
                .contentType("application/json")
                .when()
                .body(task1)
                .post(RESOURCE)
                .then()
                .statusCode(HttpStatus.SC_FORBIDDEN);
    }

    @Test
    public void createTaskWithProperUserTokenShouldReturnIdAndSaveObject() {
        Response response = given()
                .header("Authorization", "Bearer " + crmToken)
                .contentType("application/json")
                .when()
                .body(task1)
                .post(RESOURCE)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract().response();

        Integer newId = Integer.parseInt(new String(response.asByteArray()));
        Task createdTask = repository.findById(newId);

        Task toCompare = given()
                .header("Authorization", "Bearer " + crmToken)
                .contentType("application/json")
                .when()
                .get(RESOURCE + "/" + newId)
                .as(Task.class);
        assert toCompare.getId().equals(createdTask.getId());
        assert toCompare.getBackgroundColor().equals(createdTask.getBackgroundColor());
    }


    @Test
    public void createTaskWithExistingTaskIdShouldCreateNewTask() {
        task1 = repository.save(task1);
        task2.setId(task1.getId());

        Response response = given()
                .header("Authorization", "Bearer " + crmToken)
                .contentType("application/json")
                .when()
                .body(task2)
                .post(RESOURCE)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract().response();

        Integer newId = Integer.parseInt(new String(response.asByteArray()));
        assert !newId.equals(task1.getId());
        assert repository.findById(task1.getId()) != null;
        assert repository.findById(newId) != null;
    }

    @Test
    public void createTaskWithoutRequiredFieldsShouldReturnError() {
        task1.title(null);
        given()
                .header("Authorization", "Bearer " + crmToken)
                .contentType("application/json")
                .when()
                .body(task1)
                .post(RESOURCE)
                .then()
                .statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
    }

    @Test
    public void deleteTaskShouldDeleteTask() {
        task1 = repository.save(task1);
        given()
                .header("Authorization", "Bearer " + crmToken)
                .contentType("application/json")
                .when()
                .delete(RESOURCE + "/" + task1.getId())
                .then()
                .statusCode(HttpStatus.SC_OK);
        assert repository.findById(task1.getId()) == null;
    }

    @Test
    public void deleteNonExistingTaskShouldReturnError() {
        given()
                .header("Authorization", "Bearer " + crmToken)
                .contentType("application/json")
                .when()
                .delete(RESOURCE + "/" + 321)
                .then()
                .statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
    }

    @Test
    public void updateTaskWithWrongPathBodyIdShouldReturnError() {
        task1 = repository.save(task1);
        given()
                .header("Authorization", "Bearer " + crmToken)
                .contentType("application/json")
                .when()
                .body(task1)
                .put(RESOURCE + "/" + task1.getId() + 3)
                .then()
                .statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
    }

    @Test
    public void updateTaskShouldUpdate() {
        task1 = repository.save(task1);
        task1.setEmployee(employee2);
        task1.setBackgroundColor("white");

        given()
                .header("Authorization", "Bearer " + crmToken)
                .contentType("application/json")
                .when()
                .body(task1)
                .put(RESOURCE + "/" + task1.getId())
                .then()
                .statusCode(HttpStatus.SC_OK);

        Task toCompare = given()
                .header("Authorization", "Bearer " + crmToken)
                .contentType("application/json")
                .when()
                .get(RESOURCE + "/" + task1.getId())
                .as(Task.class);
        assert toCompare.getId().equals(task1.getId());
        assert toCompare.getBackgroundColor().equals(task1.getBackgroundColor());
        assert toCompare.getEmployee().equals(task1.getEmployee());
    }

    @Test
    public void getTasksShouldReturnAllTasks() {
        task1 = repository.save(task1);
        task2 = repository.save(task2);

        given()
                .header("Authorization", "Bearer " + crmToken)
                .contentType("application/json")
                .when()
                .get(RESOURCE)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("id", hasItems(task1.getId(), task1.getId()));
    }
}
