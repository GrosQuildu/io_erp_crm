package io.swagger.api.crm;


import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;
import io.swagger.Swagger2SpringBoot;
import io.swagger.api.ITHelper;
import io.swagger.model.common.Employee;
import io.swagger.model.common.EmployeeRepository;
import io.swagger.model.crm.*;
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
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItems;


@RunWith(SpringJUnit4ClassRunner.class)
@Configuration
@ContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = Swagger2SpringBoot.class)
public class MeetingsApiControllerIT {
    private static final String RESOURCE = "/api/crm/meetings";

    private static String adminToken = "";
    private static String crmToken = "";
    private static String erpToken = "";

    private static Employee employee1;
    private static Employee employee2;

    private static Contact contact1;
    private static Contact contact2;

    private static ContactGroup contactGroup1;
    private static ContactGroup contactGroup2;

    private static Meeting meeting1;
    private static Meeting meeting2;

    private static LocalDate meetingDay = new LocalDate();
    private static LocalDate updatedDay = new LocalDate("10");

    private static String invalidToken;

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private ContactGroupRepository contactGroupRepository;
    @Autowired
    private ContactRepository contactRepository;
    @Autowired
    private MeetingRepository repository;

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
        repository.deleteAll();

        employee1 = new Employee(null, "employee1", "employee111@test.com", "password123", Employee.Role.CRM);
        employee1 = employeeRepository.save(employee1);
        employee2 = new Employee(null, "employee2", "employee222@test.com", "password123", Employee.Role.ERP);
        employee2 = employeeRepository.save(employee2);

        List<Employee> employees = new ArrayList<>();
        employees.add(employee1);
        employees.add(employee2);

        contactGroup1 = new ContactGroup(null, "Contact Group 1");
        contactGroup1 = contactGroupRepository.save(contactGroup1);
        contactGroup2 = new ContactGroup(null, "Contact Group 2");
        contactGroup2 = contactGroupRepository.save(contactGroup2);

        contact1 = new Contact(null, employee1, contactGroup1, "Contact 1", "contact1@io.com");
        contact1 = contactRepository.save(contact1);
        contact2 = new Contact(null, employee2, contactGroup2, "Contact 2", "contact2@io.com");
        contact2 = contactRepository.save(contact2);

        List<Contact> contacts= new ArrayList<>();
        contacts.add(contact1);
        contacts.add(contact2);

        meeting1 = new Meeting(null, meetingDay, employee1, contacts, employees);
        meeting1 = repository.save(meeting1);
        meeting2 = new Meeting(null, meetingDay, employee2, contacts, employees);
        meeting2 = repository.save(meeting2);

        invalidToken = "XXX-9a20-4b49-97a9-YYY";
    }

    @After
    public void clear() {
        for (Employee employee : employeeRepository.findAll()) {
            if(employee.getId() > 3)
                employeeRepository.delete(employee.getId());
        }
        contactGroupRepository.deleteAll();
        contactRepository.deleteAll();
        repository.deleteAll();
    }

    @Test
    public void createMeetingWithoutAuthShouldReturn401Error() {
        given()
                .contentType("application/json")
                .when()
                .body(meeting1)
                .post(RESOURCE)
                .then()
                .statusCode(HttpStatus.SC_UNAUTHORIZED);
    }

    @Test
    public void createMeetingWithWrongUserTokenShouldReturnAuthError() {
        given()
                .header("Authorization", "Bearer " + erpToken)
                .contentType("application/json")
                .when()
                .body(meeting1)
                .post(RESOURCE)
                .then()
                .statusCode(HttpStatus.SC_FORBIDDEN);
    }

    @Test
    public void createMeetingWithProperUserTokenShouldReturnIdAndSaveObject() {
        Response response = given()
                .header("Authorization", "Bearer " + crmToken)
                .contentType("application/json")
                .when()
                .body(meeting1)
                .post(RESOURCE)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract().response();

        Integer newId = Integer.parseInt(new String(response.asByteArray()));
        Meeting createdMeeting = repository.findById(newId);

        Meeting toCompare = given()
                .header("Authorization", "Bearer " + crmToken)
                .contentType("application/json")
                .when()
                .get(RESOURCE + "/" + newId)
                .as(Meeting.class);
        assert toCompare.equals(createdMeeting);
    }


    @Test
    public void createMeetingWithExistingMeetingIdShouldCreateNewMeeting() {
        meeting1 = repository.save(meeting1);
        meeting2.setId(meeting1.getId());

        Response response = given()
                .header("Authorization", "Bearer " + crmToken)
                .contentType("application/json")
                .when()
                .body(meeting1)
                .post(RESOURCE)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract().response();

        Integer newId = Integer.parseInt(new String(response.asByteArray()));
        assert !newId.equals(meeting1.getId());
        assert repository.findById(meeting1.getId()) != null;
        assert repository.findById(newId) != null;
    }

    @Test
    public void createMeetingWithoutRequiredFieldsShouldReturnError() {
        meeting1.setMeetingDate(null);
        given()
                .header("Authorization", "Bearer " + crmToken)
                .contentType("application/json")
                .when()
                .body(meeting1)
                .post(RESOURCE)
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST);
        meeting1.setMeetingDate(meetingDay);
    }

    @Test
    public void deleteMeetingShouldDeleteContact() {
        meeting1 = repository.save(meeting1);
        given()
                .header("Authorization", "Bearer " + crmToken)
                .contentType("application/json")
                .when()
                .delete(RESOURCE + "/" + meeting1.getId())
                .then()
                .statusCode(HttpStatus.SC_OK);
        assert repository.findById(meeting1.getId()) == null;
    }

    @Test
    public void deleteNonExistingMeetingShouldReturnError() {
        given()
                .header("Authorization", "Bearer " + crmToken)
                .contentType("application/json")
                .when()
                .delete(RESOURCE + "/" + 321)
                .then()
                .statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
    }

    @Test
    public void updateMeetingWithWrongPathBodyIdShouldReturnError() {
        meeting1 = repository.save(meeting1);
        given()
                .header("Authorization", "Bearer " + crmToken)
                .contentType("application/json")
                .when()
                .body(meeting1)
                .put(RESOURCE + "/" + meeting1.getId() + 3)
                .then()
                .statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
    }

    @Test
    public void updateMeetingShouldUpdate() {
        meeting1.setId(null);
        meeting1 = repository.save(meeting1);
        meeting1.setMeetingDate(updatedDay);

        given()
                .header("Authorization", "Bearer " + crmToken)
                .contentType("application/json")
                .when()
                .body(meeting1)
                .put(RESOURCE + "/" + meeting1.getId())
                .then()
                .statusCode(HttpStatus.SC_OK);

        Meeting toCompare = given()
                .header("Authorization", "Bearer " + crmToken)
                .contentType("application/json")
                .when()
                .get(RESOURCE + "/" + meeting1.getId())
                .as(Meeting.class);
        toCompare.setMeetingDate(meeting1.getMeetingDate());
        assert toCompare.equals(meeting1);
    }

    @Test
    public void getMeetingsShouldReturnAllMeetings() {
        meeting1.setId(null);
        meeting1.setId(null);
        meeting1 = repository.save(meeting1);
        meeting1 = repository.save(meeting1);

        given()
                .header("Authorization", "Bearer " + crmToken)
                .contentType("application/json")
                .when()
                .get(RESOURCE)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("id", hasItems(meeting1.getId(), meeting1.getId()));
    }



}
