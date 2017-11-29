package io.swagger.api.common;

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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

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
        Employee employee = employeeRepository.findById(employeeId);
        if(!userService.matches(changePassword.getOldPassword(), employee.getPassword()))
            throw new Error("Wrong old password");

        employee.setPassword(changePassword.getNewPassword());
        userService.save(employee);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public ResponseEntity<Integer> createEmployee(@ApiParam(value = "Employee to create")  @Valid @RequestBody Employee employee) {
        userService.save(employee);
        return new ResponseEntity<Integer>(employee.getId(), HttpStatus.OK);
    }

    public ResponseEntity<Void> deleteEmployee(@ApiParam(value = "",required=true ) @PathVariable("employeeId") Integer employeeId) {
        Employee employee = BaseModel.getModelHelper(employeeRepository, employeeId);
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
        if(employeeId != employee.getId())
            throw new Error("Wrong id");
        employee = BaseModel.combineWithOld(employeeRepository, employee);

        // do not change password with this method
        String password = employeeRepository.findById(employeeId) .getPassword();
        employee.setPassword(password);

        employee = employeeRepository.save(employee);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

}
