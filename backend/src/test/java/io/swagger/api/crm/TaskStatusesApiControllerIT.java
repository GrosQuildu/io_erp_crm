package io.swagger.api.crm;


import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;
import io.swagger.Swagger2SpringBoot;
import io.swagger.api.ITHelper;
import io.swagger.model.common.Employee;
import io.swagger.model.crm.TaskStatus;
import io.swagger.model.crm.TaskStatusRepository;
import org.apache.http.HttpStatus;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItems;

@RunWith(SpringJUnit4ClassRunner.class)
@Configuration
@ContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = Swagger2SpringBoot.class)
public class TaskStatusesApiControllerIT {

    private static final String RESOURCE = "/api/crm/taskStatuses";

    private static String adminToken = "";
    private static String crmToken = "";
    private static String erpToken = "";

    private static TaskStatus status1;
    private static TaskStatus status2;

    private static String invalidToken;

    @Autowired
    private TaskStatusRepository repository;

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

        status1 = new TaskStatus(null, "status 0");
        status2 = new TaskStatus(null, "status 1");

        invalidToken = "XXX-9a20-4b49-97a9-YYY";
    }

    @After
    public void clear() {
        repository.deleteAll();
    }
    @Test
    public void createTaskStatusWithoutAuthShouldReturn401Error() {
        given()
                .contentType("application/json")
                .when()
                .body(status1)
                .post(RESOURCE)
                .then()
                .statusCode(HttpStatus.SC_UNAUTHORIZED);
    }

    @Test
    public void createTaskStatusWithWrongUserTokenShouldReturnAuthError() {
        given()
                .header("Authorization", "Bearer " + erpToken)
                .contentType("application/json")
                .when()
                .body(status1)
                .post(RESOURCE)
                .then()
                .statusCode(HttpStatus.SC_FORBIDDEN);
    }

    @Test
    public void createTaskStatusWithProperUserTokenShouldReturnIdAndSaveObject() {
        Response response = given()
                .header("Authorization", "Bearer " + crmToken)
                .contentType("application/json")
                .when()
                .body(status1)
                .post(RESOURCE)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract().response();

        Integer newId = Integer.parseInt(new String(response.asByteArray()));
        TaskStatus createdTaskStatus = repository.findById(newId);

        TaskStatus toCompare = given()
                .header("Authorization", "Bearer " + crmToken)
                .contentType("application/json")
                .when()
                .get(RESOURCE + "/" + newId)
                .as(TaskStatus.class);
        assert toCompare.equals(createdTaskStatus);
    }


    @Test
    public void createTaskStatusWithExistingTaskStatusIdShouldCreateNewTaskStatus() {
        status1 = repository.save(status1);
        status2.setId(status1.getId());

        Response response = given()
                .header("Authorization", "Bearer " + crmToken)
                .contentType("application/json")
                .when()
                .body(status2)
                .post(RESOURCE)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract().response();

        Integer newId = Integer.parseInt(new String(response.asByteArray()));
        assert !newId.equals(status1.getId());
        assert repository.findById(status1.getId()) != null;
        assert repository.findById(newId) != null;
    }

    @Test
    public void createTaskStatusWithoutRequiredFieldsShouldReturnError() {
        status1.setDescription("");
        given()
                .header("Authorization", "Bearer " + crmToken)
                .contentType("application/json")
                .when()
                .body(status1)
                .post(RESOURCE)
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST);
        status1.setDescription("Status 1");
    }

    @Test
    public void deleteTaskStatusShouldDeleteTaskStatus() {
        status1 = repository.save(status1);
        given()
                .header("Authorization", "Bearer " + crmToken)
                .contentType("application/json")
                .when()
                .delete(RESOURCE + "/" + status1.getId())
                .then()
                .statusCode(HttpStatus.SC_OK);
        assert repository.findById(status1.getId()) == null;
    }

    @Test
    public void deleteNonExistingTaskStatusShouldReturnError() {
        given()
                .header("Authorization", "Bearer " + crmToken)
                .contentType("application/json")
                .when()
                .delete(RESOURCE + "/" + 321)
                .then()
                .statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
    }

    @Test
    public void updateTaskStatusWithWrongPathBodyIdShouldReturnError() {
        status1 = repository.save(status1);
        given()
                .header("Authorization", "Bearer " + crmToken)
                .contentType("application/json")
                .when()
                .body(status1)
                .put(RESOURCE + "/" + status1.getId() + 3)
                .then()
                .statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
    }

    @Test
    public void updateTaskStatusShouldUpdate() {
        status1.setId(null);
        status1 = repository.save(status1);
        status1.setDescription("xxasdasd");

        given()
                .header("Authorization", "Bearer " + crmToken)
                .contentType("application/json")
                .when()
                .body(status1)
                .put(RESOURCE + "/" + status1.getId())
                .then()
                .statusCode(HttpStatus.SC_OK);

        TaskStatus toCompare = given()
                .header("Authorization", "Bearer " + crmToken)
                .contentType("application/json")
                .when()
                .get(RESOURCE + "/" + status1.getId())
                .as(TaskStatus.class);
        assert toCompare.equals(status1);
    }

    @Test
    public void getTaskStatusesShouldReturnAllTaskStatuses() {
        status1.setId(null);
        status2.setId(null);
        status1 = repository.save(status1);
        status2 = repository.save(status2);

        given()
                .header("Authorization", "Bearer " + crmToken)
                .contentType("application/json")
                .when()
                .get(RESOURCE)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("id", hasItems(status1.getId(), status2.getId()));
    }
}
