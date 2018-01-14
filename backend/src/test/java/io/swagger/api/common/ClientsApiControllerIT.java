package io.swagger.api.common;


import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;
import io.swagger.Swagger2SpringBoot;
import io.swagger.api.ITHelper;
import io.swagger.model.common.*;
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
public class ClientsApiControllerIT {
    private static final String RESOURCE = "/api/clients";

	private static String adminToken = "";
	private static String crmToken = "";
	private static String erpToken = "";

	private static ClientType clientType1;
	private static ClientType clientType2;

	private static Client client1;
	private static Client client2;
	private static Client client3;

	private static String invalidToken;

	@Autowired
	private ClientRepository repository;
	@Autowired
	private ClientTypeRepository clientTypeRepository;

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


        clientType1 = new ClientType(null, "clienType1");
        clientType2 = new ClientType(null, "clienType1");
        clientType1 = clientTypeRepository.save(clientType1);
        clientType2 = clientTypeRepository.save(clientType2);

        client1 = new Client(null, "client1", "test@test.com", clientType1);
        client2 = new Client(null, "client2", "test2@test.com", clientType2);

        invalidToken = "XXX-9a20-4b49-97a9-YYY";
    }

    @After
	public void clear() {
		repository.deleteAll();
		clientTypeRepository.deleteAll();
	}

	@Test
	public void createClientWithoutAuthShouldReturn401Error() {
		given()
			.contentType("application/json")
		.when()
			.body(client1)
			.post(RESOURCE)
		.then()
			.statusCode(HttpStatus.SC_UNAUTHORIZED);
	}

	@Test
	public void createClientWithWrongUserTokenShouldReturnAuthError() {
		given()
			.header("Authorization", "Bearer " + invalidToken)
			.contentType("application/json")
		.when()
			.body(client1)
			.post(RESOURCE)
		.then()
			.statusCode(HttpStatus.SC_UNAUTHORIZED);
	}

	@Test
	public void createClientWithProperUserTokenShouldReturnIdAndSaveObject() {
		Response response = given()
								.header("Authorization", "Bearer " + adminToken)
								.contentType("application/json")
							.when()
								.body(client1)
								.post(RESOURCE)
							.then()
								.statusCode(HttpStatus.SC_OK)
								.extract().response();

		Integer newId = Integer.parseInt(new String(response.asByteArray()));
		Client createdClient = repository.findById(newId);

		Client toCompare = given()
			.header("Authorization", "Bearer " + adminToken)
			.contentType("application/json")
		.when()
			.get(RESOURCE + "/" + newId)
			.as(Client.class);
		assert toCompare.equals(createdClient);
	}

	@Test
	public void createClientWithoutRequiredFieldsShouldReturnError() {
		client1.setMail("");
		given()
			.header("Authorization", "Bearer " + adminToken)
			.contentType("application/json")
		.when()
			.body(client1)
			.post(RESOURCE)
		.then()
			.statusCode(HttpStatus.SC_BAD_REQUEST);
	}

	@Test
	public void deleteClientShouldDeleteClient() {
        client1 = repository.save(client1);
		given()
			.header("Authorization", "Bearer " + adminToken)
			.contentType("application/json")
		.when()
			.delete(RESOURCE + "/" + client1.getId())
		.then()
			.statusCode(HttpStatus.SC_OK);
		assert repository.findById(client1.getId()) == null;
	}

	@Test
	public void deleteNonexistingClientShouldReturnError() {
		given()
			.header("Authorization", "Bearer " + adminToken)
			.contentType("application/json")
		.when()
			.delete(RESOURCE + "/" + 321)
		.then()
			.statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
	}

	@Test
	public void updateClientWithWrongPathBodyIdShouldReturnError() {
	    client1.setId(null);
	    client1 = repository.save(client1);
		given()
			.header("Authorization", "Bearer " + adminToken)
			.contentType("application/json")
		.when()
			.body(client1)
			.put(RESOURCE + "/" + client1.getId() + 3)
		.then()
			.statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
	}

	@Test
	public void updateClientShouldUpdate() {
		client1 = repository.save(client1);
		client1.setCityDelivery("asdasd");
		client1.setNameDelivery("wae awe awe ");

		given()
			.header("Authorization", "Bearer " + adminToken)
			.contentType("application/json")
		.when()
			.body(client1)
			.put(RESOURCE + "/" + client1.getId())
		.then()
			.statusCode(HttpStatus.SC_OK);

		Client toCompare = given()
			.header("Authorization", "Bearer " + adminToken)
			.contentType("application/json")
		.when()
			.get(RESOURCE + "/" + client1.getId())
			.as(Client.class);
		assert toCompare.equals(client1);
	}

	@Test
	public void getClientsShouldReturnAllClients() {
		client1 = repository.save(client1);
		client2 = repository.save(client2);

		given()
			.header("Authorization", "Bearer " + adminToken)
			.contentType("application/json")
		.when()
			.get(RESOURCE)
		.then()
			.statusCode(HttpStatus.SC_OK)
			.body("id", hasItems(client1.getId(), client2.getId()));
	}
}
