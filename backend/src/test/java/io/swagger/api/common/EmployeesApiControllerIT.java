package io.swagger.api.common;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;
import io.swagger.configuration.EmployeeService;
import io.swagger.model.common.Employee;
import io.swagger.model.common.EmployeeRepository;
import org.apache.http.HttpStatus;
import org.apache.http.util.EntityUtils;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static com.jayway.restassured.RestAssured.get;
import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItems;

@RunWith(SpringJUnit4ClassRunner.class)
@Configuration
@ContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@Transactional
public class EmployeesApiControllerIT {
	private static final String GET_TOKEN = "/api/oauth/token";
	private static final String ADMIN_PASS = "81J3V6V9SQMT";
	private static final String ERP_PASS = "2G8UI6F0UVJC";
	private static final String CRM_PASS = "AM3MR3F0JNG4";

	private static final String GET_ALL_RESOURCE = "/api/employees";
	private static final String GET_RESOURCE = "/api/employees/{id}";

	private static String adminToken = "";

	@Autowired
	private EmployeeRepository repository;

	@LocalServerPort
	private int serverPort;

	private static Employee admin;
	private static Employee crm;
	private static Employee erp;

	@Before
	public void setUp() {
		if(!adminToken.isEmpty())
			return;
		RestAssured.port = serverPort;

		Response response =
				given()
					.auth().basic("javafx-client", "R3GMPX2DQHD1234VFG929ABW")
					.parameters("grant_type", "password", "username", "admin@io_erp_crm.com", "password", ADMIN_PASS)
				.when()
					.post(GET_TOKEN)
				.then()
					.extract().response();
		adminToken = response.path("access_token");
	}

	@Test
	public void getEmployeesShouldReturnDefaultEmployees() {
		Response response = given()
				.header("Authorization", "Bearer " + adminToken)
			.contentType("application/json\r\n")
		.when()
				.get(GET_ALL_RESOURCE)
				.then()
				.statusCode(HttpStatus.SC_OK)
				.body("name", hasItems("admin", "main_erp", "main_crm"))
				.body("visibility", hasItems(true, true, true))
		.extract().response();
		System.out.println(response.getBody().toString());
	}


}