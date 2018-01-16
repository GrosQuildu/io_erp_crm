package io.swagger.api.crm;


import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;
import io.swagger.Swagger2SpringBoot;
import io.swagger.api.ITHelper;
import io.swagger.model.common.*;
import io.swagger.model.crm.Contact;
import io.swagger.model.crm.ContactGroup;
import io.swagger.model.crm.ContactGroupRepository;
import io.swagger.model.crm.ContactRepository;
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
public class ContactApiControllerIT {
    private static final String RESOURCE = "/api/crm/contacts";

    private static String adminToken = "";
    private static String crmToken = "";
    private static String erpToken = "";

    private static Employee employee1;
    private static Employee employee2;

    private static ClientType clientType1;
    private static ClientType clientType2;

    private static Client client1;
    private static Client client2;

    private static ContactGroup contactGroup1;
    private static ContactGroup contactGroup2;

    private static Contact contact1;
    private static Contact contact2;

    private static String invalidToken;

    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private ClientTypeRepository clientTypeRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private ContactGroupRepository contactGroupRepository;
    @Autowired
    private ContactRepository repository;

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

        clear();

        employee1 = new Employee(null, "employee1", "employee111@test.com", "password123", Employee.Role.CRM);
        employee1 = employeeRepository.save(employee1);
        employee2 = new Employee(null, "employee2", "employee222@test.com", "password123", Employee.Role.ERP);
        employee2 = employeeRepository.save(employee2);

        clientType1 = new ClientType(null, "clientType1");
        clientType2 = new ClientType(null, "clientType2");
        clientType1 = clientTypeRepository.save(clientType1);
        clientType2 = clientTypeRepository.save(clientType2);

        client1 = new Client(null, "client1", "client111@test.com", clientType1);
        client1 = clientRepository.save(client1);
        client2 = new Client(null, "client2", "client222@test.com", clientType2);
        client2 = clientRepository.save(client2);

        contactGroup1 = new ContactGroup(null, "Contact Group 1");
        contactGroup1 = contactGroupRepository.save(contactGroup1);
        contactGroup2 = new ContactGroup(null, "Contact Group 2");
        contactGroup2 = contactGroupRepository.save(contactGroup2);

        contact1 = new Contact(null, employee1, contactGroup1, "Contact1", "contact11@test.com");
        contact2 = new Contact(null, employee2, contactGroup2, "Contact2", "contact22@test.com");

        invalidToken = "XXX-9a20-4b49-97a9-YYY";
    }

    @After
    public void clear() {
        for (Employee employee : employeeRepository.findAll()) {
            if(employee.getId() > 3)
                employeeRepository.delete(employee.getId());
        }
        contactGroupRepository.deleteAll();
        clientRepository.deleteAll();
        clientTypeRepository.deleteAll();
        repository.deleteAll();
    }

    @Test
    public void createContactWithoutAuthShouldReturn401Error() {
        given()
                .contentType("application/json")
                .when()
                .body(contact1)
                .post(RESOURCE)
                .then()
                .statusCode(HttpStatus.SC_UNAUTHORIZED);
    }

    @Test
    public void createContactWithWrongUserTokenShouldReturnAuthError() {
        given()
                .header("Authorization", "Bearer " + erpToken)
                .contentType("application/json")
                .when()
                .body(contact1)
                .post(RESOURCE)
                .then()
                .statusCode(HttpStatus.SC_FORBIDDEN);
    }

    @Test
    public void createContactWithProperUserTokenShouldReturnIdAndSaveObject() {
        Response response = given()
                .header("Authorization", "Bearer " + crmToken)
                .contentType("application/json")
                .when()
                .body(contact1)
                .post(RESOURCE)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract().response();

        Integer newId = Integer.parseInt(new String(response.asByteArray()));
        Contact createdContact = repository.findById(newId);

        Contact toCompare = given()
                .header("Authorization", "Bearer " + crmToken)
                .contentType("application/json")
                .when()
                .get(RESOURCE + "/" + newId)
                .as(Contact.class);
        assert toCompare.equals(createdContact);
    }


    @Test
    public void createContactWithExistingContactIdShouldCreateNewContact() {
        contact1 = repository.save(contact1);
        contact2.setId(contact1.getId());

        Response response = given()
                .header("Authorization", "Bearer " + crmToken)
                .contentType("application/json")
                .when()
                .body(contact2)
                .post(RESOURCE)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract().response();

        Integer newId = Integer.parseInt(new String(response.asByteArray()));
        assert !newId.equals(contact1.getId());
        assert repository.findById(contact1.getId()) != null;
        assert repository.findById(newId) != null;
    }

    @Test
    public void createContactWithoutRequiredFieldsShouldReturnError() {
        contact1.setName("");
        given()
                .header("Authorization", "Bearer " + crmToken)
                .contentType("application/json")
                .when()
                .body(contact1)
                .post(RESOURCE)
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST);
        contact1.setName("Contact1");
    }

    @Test
    public void deleteContactShouldDeleteContact() {
        contact1 = repository.save(contact1);
        given()
                .header("Authorization", "Bearer " + crmToken)
                .contentType("application/json")
                .when()
                .delete(RESOURCE + "/" + contact1.getId())
                .then()
                .statusCode(HttpStatus.SC_OK);
        assert repository.findById(contact1.getId()) == null;
    }

    @Test
    public void deleteNonExistingContactShouldReturnError() {
        given()
                .header("Authorization", "Bearer " + crmToken)
                .contentType("application/json")
                .when()
                .delete(RESOURCE + "/" + 321)
                .then()
                .statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
    }

    @Test
    public void updateContactWithWrongPathBodyIdShouldReturnError() {
        contact1 = repository.save(contact1);
        given()
                .header("Authorization", "Bearer " + crmToken)
                .contentType("application/json")
                .when()
                .body(contact1)
                .put(RESOURCE + "/" + contact1.getId() + 3)
                .then()
                .statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
    }

    @Test
    public void updateContactShouldUpdate() {
        contact1 = repository.save(contact1);
        contact1.setName("Ulala");

        given()
                .header("Authorization", "Bearer " + crmToken)
                .contentType("application/json")
                .when()
                .body(contact1)
                .put(RESOURCE + "/" + contact1.getId())
                .then()
                .statusCode(HttpStatus.SC_OK);

        Contact toCompare = given()
                .header("Authorization", "Bearer " + crmToken)
                .contentType("application/json")
                .when()
                .get(RESOURCE + "/" + contact1.getId())
                .as(Contact.class);
        toCompare.setName(contact1.getName());
        assert toCompare.equals(contact1);
    }

    @Test
    public void getContactsShouldReturnAllContacts() {
        contact1 = repository.save(contact1);
        contact2 = repository.save(contact2);

        given()
                .header("Authorization", "Bearer " + crmToken)
                .contentType("application/json")
                .when()
                .get(RESOURCE)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("id", hasItems(contact1.getId(), contact2.getId()));
    }



}
