package io.swagger.api.common;


import io.swagger.configuration.EmployeeService;
import io.swagger.model.common.Employee;
import io.swagger.model.common.EmployeeRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class EmployeeApiControllerTest {
    private static final Employee EMPLOYEE1 = new Employee(1, "employee1", "test1@email.com",
            "password", Employee.Role.ADMIN);
    private static final Employee EMPLOYEE2 = new Employee(2, "employee2", "test2@email.com",
            "password2", Employee.Role.CRM);
    private static final Employee EMPLOYEE3 = new Employee(3, "employee3", "test3@email.com",
            "password3", Employee.Role.ERP);

    @InjectMocks
    private EmployeesApiController controller;
    @Mock
    private EmployeeRepository repository;
    @Mock
    EmployeeService employeeService;

    @Test
    public void whenFindingEmployeesItShouldReturnAllEmployees() {
        given(repository.findAll()).willReturn(Arrays.asList(EMPLOYEE1, EMPLOYEE2));
        assertThat(controller.getEmployees().getBody())
                .containsOnly(EMPLOYEE1, EMPLOYEE2);
    }
}

