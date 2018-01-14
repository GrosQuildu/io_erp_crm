package io.swagger.api.common;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;
import io.swagger.Swagger2SpringBoot;
import io.swagger.api.ITHelper;
import io.swagger.model.common.Employee;
import io.swagger.model.common.Unit;
import io.swagger.model.common.UnitRepository;
import org.apache.http.HttpStatus;
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
public class UnitsApiControllerIT {
    private static final String RESOURCE = "/api/units";

	private static String adminToken = "";
	private static String crmToken = "";
	private static String erpToken = "";

	private static Unit unit1;
	private static Unit unit2;
	private static Unit unit3;

	private static String invalidToken;

	@Autowired
	private UnitRepository repository;

	@LocalServerPort
	private int serverPort;

	@Before
	public void setUp() {
        if (!adminToken.isEmpty())
            return;
        RestAssured.port = serverPort;

        adminToken = ITHelper.getToken(Employee.Role.ADMIN);
        crmToken = ITHelper.getToken(Employee.Role.CRM);
        erpToken = ITHelper.getToken(Employee.Role.ERP);

        unit1 = new Unit(null, "unit1", "unit1Short");
        unit2 = new Unit(null, "unit2", "unit1Short2");
        unit3 = new Unit(null, "unit3", "unit1Short3");

        invalidToken = "XXX-9a20-4b49-97a9-YYY";
    }

	@Test
	public void createUnitWithoutAuthShouldReturn401Error() {
		given()
			.contentType("application/json")
		.when()
			.body(unit1)
			.post(RESOURCE)
		.then()
			.statusCode(HttpStatus.SC_UNAUTHORIZED);
	}

	@Test
	public void createUnitWithWrongUserTokenShouldReturn500Error() {
		given()
			.header("Authorization", "Bearer " + invalidToken)
			.contentType("application/json")
		.when()
			.body(unit1)
			.post(RESOURCE)
		.then()
			.statusCode(HttpStatus.SC_UNAUTHORIZED);
	}

	@Test
	public void createUnitWithProperUserTokenShouldReturnIdAndSaveObject() {
		Response response = given()
								.header("Authorization", "Bearer " + adminToken)
								.contentType("application/json")
							.when()
								.body(unit1)
								.post(RESOURCE)
							.then()
								.statusCode(HttpStatus.SC_OK)
								.extract().response();

		Integer newId = Integer.parseInt(new String(response.asByteArray()));
		Unit createdUnit = repository.findById(newId);

		Unit toCompare = given()
			.header("Authorization", "Bearer " + adminToken)
			.contentType("application/json")
		.when()
			.get(RESOURCE + "/" + newId)
			.as(Unit.class);
		assert toCompare.equals(createdUnit);
		repository.deleteAll();
	}

	@Test
	public void createUnitWithoutRequiredFieldsShouldReturnError() {
		unit1.setName("");
		given()
			.header("Authorization", "Bearer " + adminToken)
			.contentType("application/json")
		.when()
			.body(unit1)
			.post(RESOURCE)
		.then()
			.statusCode(HttpStatus.SC_BAD_REQUEST);

		unit1.setName("unit1");
		unit1.setNameShort("");
		given()
			.header("Authorization", "Bearer " + adminToken)
			.contentType("application/json")
		.when()
			.body(unit1)
			.post(RESOURCE)
		.then()
			.statusCode(HttpStatus.SC_BAD_REQUEST);
		unit1.setNameShort("unit1Short");
	}

	@Test
	public void deleteUnitShouldDeleteUnit() {
		unit1 = repository.save(unit1);
		given()
			.header("Authorization", "Bearer " + adminToken)
			.contentType("application/json")
		.when()
			.delete(RESOURCE + "/" + unit1.getId())
		.then()
			.statusCode(HttpStatus.SC_OK);
		assert repository.findById(unit1.getId()) == null;
	}

	@Test
	public void deleteNonexistingUnitShouldReturnError() {
		given()
			.header("Authorization", "Bearer " + adminToken)
			.contentType("application/json")
		.when()
			.delete(RESOURCE + "/" + 321)
		.then()
			.statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
	}

	@Test
	public void updateUnitWithWrongPathBodyIdShouldReturnError() {
	    unit1 = repository.save(unit1);

		given()
			.header("Authorization", "Bearer " + adminToken)
			.contentType("application/json")
		.when()
			.body(unit1)
			.put(RESOURCE + "/" + unit1.getId() + 3)
		.then()
			.statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
		repository.deleteAll();
	}

	@Test
	public void updateUnitShouldUpdate() {
		unit1 = repository.save(unit1);
		unit1.setNameShort("asdasd");

		given()
			.header("Authorization", "Bearer " + adminToken)
			.contentType("application/json")
		.when()
			.body(unit1)
			.put(RESOURCE + "/" + unit1.getId())
		.then()
			.statusCode(HttpStatus.SC_OK);

		Unit toCompare = given()
			.header("Authorization", "Bearer " + adminToken)
			.contentType("application/json")
		.when()
			.get(RESOURCE + "/" + unit1.getId())
			.as(Unit.class);
		assert toCompare.equals(unit1);
		repository.deleteAll();
	}

	@Test
	public void getUnitsShouldReturnAllUnits() {
	    unit1.setId(null);
	    unit2.setId(null);
		unit1 = repository.save(unit1);
		unit2 = repository.save(unit2);

		given()
			.header("Authorization", "Bearer " + adminToken)
			.contentType("application/json")
		.when()
			.get(RESOURCE)
		.then()
			.statusCode(HttpStatus.SC_OK)
			.body("id", hasItems(unit1.getId(), unit2.getId()));
		repository.deleteAll();
	}

}
