package io.swagger.api.common;

import io.swagger.configuration.CustomUserDetails;
import io.swagger.configuration.EmployeeService;
import io.swagger.model.BaseModel;
import io.swagger.model.ChangePassword;
import io.swagger.model.common.Employee;

import io.swagger.annotations.*;

import io.swagger.model.common.EmployeeRepository;
import io.swagger.model.erp.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.security.Principal;
import java.util.List;

import javax.validation.Valid;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-11-15T00:41:28.115Z")

@Controller
public class EmployeesApiController implements EmployeesApi {

    /** Dependent:
        * orders (soft, do nothing on delete)
     * Depends on:
        * none
     */
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    EmployeeService userService;

    public ResponseEntity<Void> changePassword(@ApiParam(value = "",required=true ) @PathVariable("employeeId") Integer employeeId,
        @ApiParam(value = ""  )  @Valid @RequestBody ChangePassword changePassword) {
        Employee currentEmployee = userService.getCurrentUser();
        Employee employee = employeeRepository.findById(employeeId);

        // can change passwords only if currently logged in user is admin or employee is chaning own password
        if(currentEmployee.getRole() != Employee.Role.ADMIN && currentEmployee.getId() != employee.getId())
            throw new Error("Can't edit this user");

        // have to send old password unless admin
        if(currentEmployee.getRole() != Employee.Role.ADMIN && !userService.matches(changePassword.getOldPassword(), employee.getPassword()))
            throw new Error("Wrong old password");

        employee.setPassword(changePassword.getNewPassword());
        userService.save(employee);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public ResponseEntity<Integer> createEmployee(@ApiParam(value = "Employee to create")  @Valid @RequestBody Employee employee) {
        Employee currentEmployee = userService.getCurrentUser();
        if(currentEmployee.getRole() != Employee.Role.ADMIN)
            throw new Error("Only admin can create employees");

        employee = userService.save(employee);
        return new ResponseEntity<Integer>(employee.getId(), HttpStatus.OK);
    }

    public ResponseEntity<Void> deleteEmployee(@ApiParam(value = "",required=true ) @PathVariable("employeeId") Integer employeeId) {
        Employee currentEmployee = userService.getCurrentUser();
        if(currentEmployee.getRole() != Employee.Role.ADMIN)
            throw new Error("Only admin can delete employees");

//        Employee employee = BaseModel.getModelHelper(employeeRepository, employeeId);
//        BaseModel.dependent(orderRepository, employee);
        employeeRepository.delete(employeeId);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public ResponseEntity<Employee> getEmployee(@ApiParam(value = "",required=true ) @PathVariable("employeeId") Integer employeeId) {
        Employee employee =  BaseModel.getModelHelper(employeeRepository, employeeId);
        employee.setPassword("***");
        return new ResponseEntity<Employee>(employee, HttpStatus.OK);
    }

    public ResponseEntity<List<Employee>> getEmployees() {
        List<Employee> employees = (List<Employee> ) employeeRepository.findAll();
        employees.stream().forEach(employee -> employee.setPassword("***"));
        return new ResponseEntity<List<Employee>>(employees, HttpStatus.OK);
    }

    public ResponseEntity<Void> updateEmployee(@ApiParam(value = "",required=true ) @PathVariable("employeeId") Integer employeeId,
        @ApiParam(value = "Employee to update"  )  @Valid @RequestBody Employee employee) {
        if(employee.getId() != null && employeeId != employee.getId())
            throw new Error("Wrong id");

        employee = BaseModel.combineWithOld(employeeRepository, employee);
        Employee currentEmployee = userService.getCurrentUser();

        // can update only if currently logged in user is admin or employee is updating himself
        if(currentEmployee.getRole() != Employee.Role.ADMIN && currentEmployee.getId() != employee.getId())
            throw new Error("Can't edit this user");

        // check if (perhaps) new mail is not used
        if(employeeRepository.findById(employeeId).getMail() != employee.getMail() &&
                employeeRepository.findByMail(employee.getMail()) != null)
            throw new Error("Mail already in use");

        // do not change password with this method
        String password = employeeRepository.findById(employeeId).getPassword();
        employee.setPassword(password);

        employee = employeeRepository.save(employee);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

}
