package io.swagger.api.crm;


import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;
import io.swagger.Swagger2SpringBoot;
import io.swagger.api.ITHelper;
import io.swagger.model.common.*;
import io.swagger.model.crm.ContactGroup;
import io.swagger.model.crm.ContactGroupRepository;
import org.apache.http.HttpStatus;
import org.junit.After;
import org.junit.AfterClass;
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
public class ContactGroupsApiControllerIT {
    private static final String RESOURCE = "/api/crm/contactGroups";

    private static String adminToken = "";
    private static String crmToken = "";
    private static String erpToken = "";

    private static ContactGroup group1;
    private static ContactGroup group2;

    private static String invalidToken;

    @Autowired
    private ContactGroupRepository repository;

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

        group1 = new ContactGroup(null, "Grupa o indeksie 0");
        group2 = new ContactGroup(null, "Grupa o indeksie 1");

        invalidToken = "XXX-9a20-4b49-97a9-YYY";
    }

    @After
    public void clear() {
        repository.deleteAll();
    }
    @Test
    public void createContactGroupWithoutAuthShouldReturn401Error() {
        given()
                .contentType("application/json")
                .when()
                .body(group1)
                .post(RESOURCE)
                .then()
                .statusCode(HttpStatus.SC_UNAUTHORIZED);
    }

    @Test
    public void createContactGroupWithWrongUserTokenShouldReturnAuthError() {
        given()
                .header("Authorization", "Bearer " + erpToken)
                .contentType("application/json")
                .when()
                .body(group1)
                .post(RESOURCE)
                .then()
                .statusCode(HttpStatus.SC_FORBIDDEN);
    }

    @Test
    public void createContactGroupWithProperUserTokenShouldReturnIdAndSaveObject() {
        Response response = given()
                .header("Authorization", "Bearer " + crmToken)
                .contentType("application/json")
                .when()
                .body(group1)
                .post(RESOURCE)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract().response();

        Integer newId = Integer.parseInt(new String(response.asByteArray()));
        ContactGroup createdGroup = repository.findById(newId);

        ContactGroup toCompare = given()
                .header("Authorization", "Bearer " + crmToken)
                .contentType("application/json")
                .when()
                .get(RESOURCE + "/" + newId)
                .as(ContactGroup.class);
        assert toCompare.equals(createdGroup);
    }


    @Test
    public void createContactGroupithExistingContactGroupIdShouldCreateNewContactGroup() {
        group1 = repository.save(group1);
        group2.setId(group1.getId());

        Response response = given()
                .header("Authorization", "Bearer " + crmToken)
                .contentType("application/json")
                .when()
                .body(group2)
                .post(RESOURCE)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract().response();

        Integer newId = Integer.parseInt(new String(response.asByteArray()));
        assert !newId.equals(group1.getId());
        assert repository.findById(group1.getId()) != null;
        assert repository.findById(newId) != null;
    }

    @Test
    public void createContactGroupWithoutRequiredFieldsShouldReturnError() {
        group1.setDescription("");
        given()
                .header("Authorization", "Bearer " + crmToken)
                .contentType("application/json")
                .when()
                .body(group1)
                .post(RESOURCE)
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST);
        group1.setDescription("Grupa o indeksie 0");
    }

    @Test
    public void deleteContactGroupShouldDeleteContactGroup() {
        group1 = repository.save(group1);
        given()
                .header("Authorization", "Bearer " + crmToken)
                .contentType("application/json")
                .when()
                .delete(RESOURCE + "/" + group1.getId())
                .then()
                .statusCode(HttpStatus.SC_OK);
        assert repository.findById(group1.getId()) == null;
    }

    @Test
    public void deleteNonExistingContactGroupShouldReturnError() {
        given()
                .header("Authorization", "Bearer " + crmToken)
                .contentType("application/json")
                .when()
                .delete(RESOURCE + "/" + 321)
                .then()
                .statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
    }

    @Test
    public void updateContactGroupWithWrongPathBodyIdShouldReturnError() {
        group1 = repository.save(group1);
        given()
                .header("Authorization", "Bearer " + crmToken)
                .contentType("application/json")
                .when()
                .body(group1)
                .put(RESOURCE + "/" + group1.getId() + 3)
                .then()
                .statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
    }

    @Test
    public void updateContactGroupShouldUpdate() {
        group1.setId(null);
        group1 = repository.save(group1);
        group1.setDescription("xxasdasd");

        given()
                .header("Authorization", "Bearer " + crmToken)
                .contentType("application/json")
                .when()
                .body(group1)
                .put(RESOURCE + "/" + group1.getId())
                .then()
                .statusCode(HttpStatus.SC_OK);

        ContactGroup toCompare = given()
                .header("Authorization", "Bearer " + crmToken)
                .contentType("application/json")
                .when()
                .get(RESOURCE + "/" + group1.getId())
                .as(ContactGroup.class);
        assert toCompare.equals(group1);
    }

    @Test
    public void getContactGroupsShouldReturnAllContactGroups() {
        group1.setId(null);
        group2.setId(null);
        group1 = repository.save(group1);
        group2 = repository.save(group2);

        given()
                .header("Authorization", "Bearer " + crmToken)
                .contentType("application/json")
                .when()
                .get(RESOURCE)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("id", hasItems(group1.getId(), group2.getId()));
    }

}
