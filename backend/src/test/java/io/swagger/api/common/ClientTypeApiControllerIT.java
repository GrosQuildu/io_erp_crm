package io.swagger.api.common;


import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;
import io.swagger.Swagger2SpringBoot;
import io.swagger.api.ITHelper;
import io.swagger.model.common.ClientType;
import io.swagger.model.common.ClientTypeRepository;
import io.swagger.model.common.Employee;
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
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItems;

@RunWith(SpringJUnit4ClassRunner.class)
@Configuration
@ContextConfiguration
@TestPropertySource("classpath:application-test.properties")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = Swagger2SpringBoot.class)
public class ClientTypeApiControllerIT {
    private static final String RESOURCE = "/api/clientTypes";

	private static String adminToken = "";
	private static String crmToken = "";
	private static String erpToken = "";

	private static ClientType clientType1;
	private static ClientType clientType2;
	private static ClientType clientType3;

	private static String invalidToken;

	@Autowired
	private ClientTypeRepository repository;

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

        clientType1 = new ClientType(null, "clientType1");
        clientType2 = new ClientType(null, "clientType2");
        clientType3 = new ClientType(null, "clientType3");

        invalidToken = "XXX-9a20-4b49-97a9-YYY";
    }

    @After
	public void clear() {
		repository.deleteAll();
	}

	@Test
	public void createClientTypeWithoutAuthShouldReturn401Error() {
		given()
			.contentType("application/json")
		.when()
			.body(clientType1)
			.post(RESOURCE)
		.then()
			.statusCode(HttpStatus.SC_UNAUTHORIZED);
	}

	@Test
	public void createClientTypeWithWrongUserTokenShouldReturnAuthError() {
		given()
			.header("Authorization", "Bearer " + invalidToken)
			.contentType("application/json")
		.when()
			.body(clientType1)
			.post(RESOURCE)
		.then()
			.statusCode(HttpStatus.SC_UNAUTHORIZED);
	}

	@Test
	public void createClientTypeWithProperUserTokenShouldReturnIdAndSaveObject() {
		Response response = given()
								.header("Authorization", "Bearer " + adminToken)
								.contentType("application/json")
							.when()
								.body(clientType1)
								.post(RESOURCE)
							.then()
								.statusCode(HttpStatus.SC_OK)
								.extract().response();

		Integer newId = Integer.parseInt(new String(response.asByteArray()));
		ClientType createdClientType = repository.findById(newId);

		ClientType toCompare = given()
			.header("Authorization", "Bearer " + adminToken)
			.contentType("application/json")
		.when()
			.get(RESOURCE + "/" + newId)
			.as(ClientType.class);
		assert toCompare.equals(createdClientType);
	}
	
	@Test
	public void createClientTypeWithExistingClientTypeIdShouldCreateNewClientType() {
		clientType1 = repository.save(clientType1);
		clientType2.setId(clientType1.getId());

		Response response = given()
								.header("Authorization", "Bearer " + adminToken)
								.contentType("application/json")
							.when()
								.body(clientType2)
								.post(RESOURCE)
							.then()
								.statusCode(HttpStatus.SC_OK)
								.extract().response();

		Integer newId = Integer.parseInt(new String(response.asByteArray()));
		assert !newId.equals(clientType1.getId());
		assert repository.findById(clientType1.getId()) != null;
		assert repository.findById(newId) != null;
	}

	@Test
	public void createClientTypeWithoutRequiredFieldsShouldReturnError() {
		clientType1.setDescription("");
		given()
			.header("Authorization", "Bearer " + adminToken)
			.contentType("application/json")
		.when()
			.body(clientType1)
			.post(RESOURCE)
		.then()
			.statusCode(HttpStatus.SC_BAD_REQUEST);

		clientType1.setDescription("clientType1");
	}

	@Test
	public void deleteClientTypeShouldDeleteClientType() {
		clientType1 = repository.save(clientType1);
		given()
			.header("Authorization", "Bearer " + adminToken)
			.contentType("application/json")
		.when()
			.delete(RESOURCE + "/" + clientType1.getId())
		.then()
			.statusCode(HttpStatus.SC_OK);
		assert repository.findById(clientType1.getId()) == null;
	}

	@Test
	public void deleteNonexistingClientTypeShouldReturnError() {
		given()
			.header("Authorization", "Bearer " + adminToken)
			.contentType("application/json")
		.when()
			.delete(RESOURCE + "/" + 321)
		.then()
			.statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
	}

	@Test
	public void updateClientTypeWithWrongPathBodyIdShouldReturnError() {
	    clientType1.setId(null);
	    clientType1 = repository.save(clientType1);

		given()
			.header("Authorization", "Bearer " + adminToken)
			.contentType("application/json")
		.when()
			.body(clientType1)
			.put(RESOURCE + "/" + clientType1.getId() + 3)
		.then()
			.statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
	}

	@Test
	public void updateClientTypeShouldUpdate() {
		clientType1 = repository.save(clientType1);
		clientType1.setDescription("asdasd");

		given()
			.header("Authorization", "Bearer " + adminToken)
			.contentType("application/json")
		.when()
			.body(clientType1)
			.put(RESOURCE + "/" + clientType1.getId())
		.then()
			.statusCode(HttpStatus.SC_OK);

		ClientType toCompare = given()
			.header("Authorization", "Bearer " + adminToken)
			.contentType("application/json")
		.when()
			.get(RESOURCE + "/" + clientType1.getId())
			.as(ClientType.class);
		assert toCompare.equals(clientType1);
	}

	@Test
	public void getClientTypesShouldReturnAllClientTypes() {
		clientType1 = repository.save(clientType1);
		clientType2 = repository.save(clientType2);

		given()
			.header("Authorization", "Bearer " + adminToken)
			.contentType("application/json")
		.when()
			.get(RESOURCE)
		.then()
			.statusCode(HttpStatus.SC_OK)
			.body("id", hasItems(clientType1.getId(), clientType2.getId()));
	}
}
