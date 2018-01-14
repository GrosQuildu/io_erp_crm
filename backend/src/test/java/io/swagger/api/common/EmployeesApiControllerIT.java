package io.swagger.api.common;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;
import io.swagger.Swagger2SpringBoot;
import io.swagger.api.ITHelper;
import io.swagger.model.common.Employee;
import io.swagger.model.common.EmployeeRepository;
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
public class EmployeesApiControllerIT {
	private static final String RESOURCE = "/api/employees";

	private static String adminToken = "";
	private static String crmToken = "";
	private static String erpToken = "";

	private static Employee employee1;
	private static Employee employee2;
	private static Employee employee3;

	@Autowired
	private EmployeeRepository repository;

	@LocalServerPort
	private int serverPort;

	@Before
	public void setUp() {
		if(!adminToken.isEmpty())
			return;
		RestAssured.port = serverPort;

		adminToken = ITHelper.getToken(Employee.Role.ADMIN);
		crmToken = ITHelper.getToken(Employee.Role.CRM);
		erpToken = ITHelper.getToken(Employee.Role.ERP);

		employee1 = new Employee(null, "employee1", "test@test.com", "password123", Employee.Role.ADMIN);
		employee2 = new Employee(null, "employee2", "test2@test.com", "password123", Employee.Role.ERP);
		employee3 = new Employee(null, "employee3", "test3@test.com", "password123", Employee.Role.CRM);
	}

	@Test
	public void createEmployeeWithoutAuthShouldReturn401Error() {
		given()
			.contentType("application/json")
		.when()
			.body(employee1)
			.post(RESOURCE)
		.then()
			.statusCode(HttpStatus.SC_UNAUTHORIZED);
	}

	@Test
	public void createEmployeeWithWrongUserTokenShouldReturn500Error() {
		given()
			.header("Authorization", "Bearer " + erpToken)
			.contentType("application/json")
		.when()
			.body(employee1)
			.post(RESOURCE)
		.then()
			.statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
	}

	@Test
	public void createEmployeeWithProperUserTokenShouldReturnIdAndSaveObject() {
		Response response = given()
								.header("Authorization", "Bearer " + adminToken)
								.contentType("application/json")
							.when()
								.body(employee1)
								.post(RESOURCE)
							.then()
								.statusCode(HttpStatus.SC_OK)
								.extract().response();

		Integer newId = Integer.parseInt(new String(response.asByteArray()));
		Employee createdEmployee = repository.findById(newId);
		createdEmployee.setPassword("***");
		Employee toCompare = given()
			.header("Authorization", "Bearer " + adminToken)
			.contentType("application/json")
		.when()
			.get(RESOURCE + "/" + newId)
			.as(Employee.class);
		assert toCompare.equals(createdEmployee);
		repository.delete(newId);
	}

	@Test
	public void createEmployeeWithoutRequiredFieldsShouldReturnError() {
		employee1.setName("");
		given()
			.header("Authorization", "Bearer " + adminToken)
			.contentType("application/json")
		.when()
			.body(employee1)
			.post(RESOURCE)
		.then()
			.statusCode(HttpStatus.SC_BAD_REQUEST);

		employee1.setName("employee1");
		employee1.setMail("");
		given()
			.header("Authorization", "Bearer " + adminToken)
			.contentType("application/json")
		.when()
			.body(employee1)
			.post(RESOURCE)
		.then()
			.statusCode(HttpStatus.SC_BAD_REQUEST);
		employee1.setMail("test@test.com");
	}

	@Test
	public void deleteEmployeeShouldDeleteEmployee() {
		employee1.setId(null);
		employee1 = repository.save(employee1);
		given()
			.header("Authorization", "Bearer " + adminToken)
			.contentType("application/json")
		.when()
			.delete(RESOURCE + "/" + employee1.getId())
		.then()
			.statusCode(HttpStatus.SC_OK);
		assert repository.findById(employee1.getId()) == null;
	}

	@Test
	public void deleteNonexistingEmployeeShouldReturnError() {
		given()
			.header("Authorization", "Bearer " + adminToken)
			.contentType("application/json")
		.when()
			.delete(RESOURCE + "/" + 321)
		.then()
			.statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
	}

	@Test
	public void updateEmployeeWithWrongPathBodyIdShouldReturnError() {
		employee1.setId(null);
		employee1 = repository.save(employee1);

		given()
			.header("Authorization", "Bearer " + adminToken)
			.contentType("application/json")
		.when()
			.body(employee1)
			.put(RESOURCE + "/" + employee1.getId() + 3)
		.then()
			.statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);

		repository.delete(employee1.getId());
	}

	@Test
	public void updateEmployeeShouldUpdate() {
		employee1.setId(null);
		System.out.println("----------------");
		System.out.println(employee1);
		System.out.println(repository.findAll());
		employee1 = repository.save(employee1);
		employee1.setMail("another@mail.com");

		given()
			.header("Authorization", "Bearer " + adminToken)
			.contentType("application/json")
		.when()
			.body(employee1)
			.put(RESOURCE + "/" + employee1.getId())
		.then()
			.statusCode(HttpStatus.SC_OK);

		employee1.setPassword("***");
		Employee toCompare = given()
			.header("Authorization", "Bearer " + adminToken)
			.contentType("application/json")
		.when()
			.get(RESOURCE + "/" + employee1.getId())
			.as(Employee.class);
		assert toCompare.equals(employee1);
		employee1.setPassword("password123");
		repository.delete(employee1.getId());
	}

	@Test
	public void getEmployeesShouldReturnAllEmployees() {
		employee1.setId(null);
		employee2.setId(null);
		employee1 = repository.save(employee1);
		employee2 = repository.save(employee2);

		given()
			.header("Authorization", "Bearer " + adminToken)
			.contentType("application/json")
		.when()
			.get(RESOURCE)
		.then()
			.statusCode(HttpStatus.SC_OK)
			.body("id", hasItems(1,2,3,employee1.getId()));
		repository.delete(employee1.getId());
		repository.delete(employee2.getId());
	}


	@Test
	public void getEmployeesShouldReturnDefaultEmployees() {
		Response response = given()
					.header("Authorization", "Bearer " + adminToken)
					.contentType("application/json")
				.when()
					.get(RESOURCE)
				.then()
					.statusCode(HttpStatus.SC_OK)
					.body("name", hasItems("admin", "main_erp", "main_crm"))
					.body("visibility", hasItems(true, true, true))
		.extract().response();
		System.out.println("-------------");
		System.out.println(response.getBody().print());
	}


}