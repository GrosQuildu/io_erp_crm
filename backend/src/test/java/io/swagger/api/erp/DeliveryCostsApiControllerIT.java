package io.swagger.api.erp;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;
import io.swagger.Swagger2SpringBoot;
import io.swagger.api.ITHelper;
import io.swagger.model.common.Employee;
import io.swagger.model.erp.DeliveryCost;
import io.swagger.model.erp.DeliveryCostRepository;
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

import java.math.BigDecimal;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItems;


@RunWith(SpringJUnit4ClassRunner.class)
@Configuration
@ContextConfiguration
@TestPropertySource("classpath:application-test.properties")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = Swagger2SpringBoot.class)
public class DeliveryCostsApiControllerIT {
    private static final String RESOURCE = "/api/erp/deliveryCosts";

	private static String adminToken = "";
	private static String crmToken = "";
	private static String erpToken = "";


	private static DeliveryCost deliveryCost1;
	private static DeliveryCost deliveryCost2;
	private static DeliveryCost deliveryCost3;

	private static String invalidToken;

	@Autowired
	private DeliveryCostRepository repository;


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

        deliveryCost1 = new DeliveryCost(null, 10.0f, 20.0f, new BigDecimal(12.13));
        deliveryCost2 = new DeliveryCost(null, 112.0f, 43.3f, new BigDecimal(154.1));
        deliveryCost3 = new DeliveryCost(null, 103.0f, 123.0f, new BigDecimal(43.1));

        invalidToken = "XXX-9a20-4b49-97a9-YYY";
    }

    @After
	public void clear() {
		repository.deleteAll();
	}

	@Test
	public void createDeliveryCostWithoutAuthShouldReturn401Error() {
		given()
			.contentType("application/json")
		.when()
			.body(deliveryCost1)
			.post(RESOURCE)
		.then()
			.statusCode(HttpStatus.SC_UNAUTHORIZED);
	}

	@Test
	public void createDeliveryCostWithWrongUserTokenShouldReturnAuthError() {
		given()
			.header("Authorization", "Bearer " + crmToken)
			.contentType("application/json")
		.when()
			.body(deliveryCost1)
			.post(RESOURCE)
		.then()
			.statusCode(HttpStatus.SC_FORBIDDEN);
	}

	@Test
	public void createDeliveryCostWithProperUserTokenShouldReturnIdAndSaveObject() {
		Response response = given()
								.header("Authorization", "Bearer " + erpToken)
								.contentType("application/json")
							.when()
								.body(deliveryCost1)
								.post(RESOURCE)
							.then()
								.statusCode(HttpStatus.SC_OK)
								.extract().response();

		Integer newId = Integer.parseInt(new String(response.asByteArray()));
		DeliveryCost createdDeliveryCost = repository.findById(newId);

		DeliveryCost toCompare = given()
			.header("Authorization", "Bearer " + adminToken)
			.contentType("application/json")
		.when()
			.get(RESOURCE + "/" + newId)
			.as(DeliveryCost.class);
		assert toCompare.equals(createdDeliveryCost);
	}
	
	@Test
	public void createDeliveryCostWithExistingDeliveryCostIdShouldCreateNewDeliveryCost() {
		deliveryCost1 = repository.save(deliveryCost1);
		deliveryCost2.setId(deliveryCost1.getId());

		Response response = given()
								.header("Authorization", "Bearer " + adminToken)
								.contentType("application/json")
							.when()
								.body(deliveryCost2)
								.post(RESOURCE)
							.then()
								.statusCode(HttpStatus.SC_OK)
								.extract().response();

		Integer newId = Integer.parseInt(new String(response.asByteArray()));
		assert !newId.equals(deliveryCost1.getId());
		assert repository.findById(deliveryCost1.getId()) != null;
		assert repository.findById(newId) != null;
	}

	@Test
	public void createDeliveryCostWithoutRequiredFieldsShouldReturnError() {
		deliveryCost1.setPrice(null);
		given()
			.header("Authorization", "Bearer " + erpToken)
			.contentType("application/json")
		.when()
			.body(deliveryCost1)
			.post(RESOURCE)
		.then()
			.statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
	}

	@Test
	public void deleteDeliveryCostShouldDeleteDeliveryCost() {
        deliveryCost1.setId(null);
        deliveryCost1 = repository.save(deliveryCost1);
		given()
			.header("Authorization", "Bearer " + erpToken)
			.contentType("application/json")
		.when()
			.delete(RESOURCE + "/" + deliveryCost1.getId())
		.then()
			.statusCode(HttpStatus.SC_OK);
		assert repository.findById(deliveryCost1.getId()) == null;
	}

	@Test
	public void deleteNonexistingDeliveryCostShouldReturnError() {
		given()
			.header("Authorization", "Bearer " + erpToken)
			.contentType("application/json")
		.when()
			.delete(RESOURCE + "/" + 321)
		.then()
			.statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
	}

	@Test
	public void updateDeliveryCostWithWrongPathBodyIdShouldReturnError() {
	    deliveryCost1 = repository.save(deliveryCost1);
		given()
			.header("Authorization", "Bearer " + erpToken)
			.contentType("application/json")
		.when()
			.body(deliveryCost1)
			.put(RESOURCE + "/" + deliveryCost1.getId() + 3)
		.then()
			.statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
	}

	@Test
	public void updateDeliveryCostShouldUpdate() {
		deliveryCost1 = repository.save(deliveryCost1);
		deliveryCost1.setWeightFrom(54.2f);
		deliveryCost1.setPrice(new BigDecimal(123.3));

		given()
			.header("Authorization", "Bearer " + erpToken)
			.contentType("application/json")
		.when()
			.body(deliveryCost1)
			.put(RESOURCE + "/" + deliveryCost1.getId())
		.then()
			.statusCode(HttpStatus.SC_OK);

		DeliveryCost toCompare = given()
			.header("Authorization", "Bearer " + erpToken)
			.contentType("application/json")
		.when()
			.get(RESOURCE + "/" + deliveryCost1.getId())
			.as(DeliveryCost.class);
		toCompare.setPrice(deliveryCost1.getPrice());
		assert toCompare.equals(deliveryCost1);
	}

	@Test
	public void getDeliveryCostsShouldReturnAllDeliveryCosts() {
		deliveryCost1 = repository.save(deliveryCost1);
		deliveryCost2 = repository.save(deliveryCost2);

		given()
			.header("Authorization", "Bearer " + erpToken)
			.contentType("application/json")
		.when()
			.get(RESOURCE)
		.then()
			.statusCode(HttpStatus.SC_OK)
			.body("id", hasItems(deliveryCost1.getId(), deliveryCost2.getId()));
	}
}
