/**
 * NOTE: This class is auto generated by the swagger code generator program (2.2.3).
 * https://github.com/swagger-api/swagger-codegen
 * Do not edit the class manually.
 */
package io.swagger.api.common;

import io.swagger.model.ChangePassword;
import io.swagger.model.common.Employee;
import io.swagger.model.Error;

import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import javax.validation.Valid;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-11-15T00:41:28.115Z")

@Api(value = "employees", description = "the employees API")
public interface EmployeesApi {

    @ApiOperation(value = "Change employee's password (admin or self only)", notes = "", response = Void.class, authorizations = {
        @Authorization(value = "APIKeyHeader")
    }, tags={ "employees", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Updated", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized - API key is missing or invalid", response = Error.class),
        @ApiResponse(code = 500, message = "Server error", response = Error.class) })
    
    @RequestMapping(value = "/employees/{employeeId}/changePassword",
        produces = { "application/json" }, 
        consumes = { "application/json" },
        method = RequestMethod.PUT)
    ResponseEntity<Void> changePassword(@ApiParam(value = "",required=true ) @PathVariable("employeeId") Integer employeeId,
                                        @ApiParam(value = ""  )  @Valid @RequestBody ChangePassword changePassword);


    @ApiOperation(value = "Create new employee (admin only)", notes = "", response = Integer.class, authorizations = {
        @Authorization(value = "APIKeyHeader")
    }, tags={ "employees", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "", response = Integer.class),
        @ApiResponse(code = 401, message = "Unauthorized - API key is missing or invalid", response = Error.class),
        @ApiResponse(code = 500, message = "Server error", response = Error.class) })
    
    @RequestMapping(value = "/employees",
        produces = { "application/json" }, 
        consumes = { "application/json" },
        method = RequestMethod.POST)
    ResponseEntity<Integer> createEmployee(@ApiParam(value = "Employee to create"  )  @Valid @RequestBody Employee employee);


    @ApiOperation(value = "Delete employee (admin only)", notes = "", response = Void.class, authorizations = {
        @Authorization(value = "APIKeyHeader")
    }, tags={ "employees", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Deleted", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized - API key is missing or invalid", response = Error.class),
        @ApiResponse(code = 500, message = "Server error", response = Error.class) })
    
    @RequestMapping(value = "/employees/{employeeId}",
        produces = { "application/json" }, 
        consumes = { "application/json" },
        method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteEmployee(@ApiParam(value = "",required=true ) @PathVariable("employeeId") Integer employeeId);


    @ApiOperation(value = "Returns Employee", notes = "", response = Employee.class, authorizations = {
        @Authorization(value = "APIKeyHeader")
    }, tags={ "employees", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "", response = Employee.class),
        @ApiResponse(code = 401, message = "Unauthorized - API key is missing or invalid", response = Error.class),
        @ApiResponse(code = 500, message = "Server error", response = Error.class) })
    
    @RequestMapping(value = "/employees/{employeeId}",
        produces = { "application/json" }, 
        consumes = { "application/json" },
        method = RequestMethod.GET)
    ResponseEntity<Employee> getEmployee(@ApiParam(value = "",required=true ) @PathVariable("employeeId") Integer employeeId);


    @ApiOperation(value = "Returns list of Employees", notes = "", response = Employee.class, responseContainer = "List", authorizations = {
        @Authorization(value = "APIKeyHeader")
    }, tags={ "employees", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "", response = Employee.class, responseContainer = "List"),
        @ApiResponse(code = 401, message = "Unauthorized - API key is missing or invalid", response = Error.class),
        @ApiResponse(code = 500, message = "Server error", response = Error.class) })
    
    @RequestMapping(value = "/employees",
        produces = { "application/json" }, 
        consumes = { "application/json" },
        method = RequestMethod.GET)
    ResponseEntity<List<Employee>> getEmployees();


    @ApiOperation(value = "Update existing employee (admin or self only)", notes = "", response = Void.class, authorizations = {
        @Authorization(value = "APIKeyHeader")
    }, tags={ "employees", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Updated", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized - API key is missing or invalid", response = Error.class),
        @ApiResponse(code = 500, message = "Server error", response = Error.class) })
    
    @RequestMapping(value = "/employees/{employeeId}",
        produces = { "application/json" }, 
        consumes = { "application/json" },
        method = RequestMethod.PUT)
    ResponseEntity<Void> updateEmployee(@ApiParam(value = "",required=true ) @PathVariable("employeeId") Integer employeeId,
                                        @ApiParam(value = "Employee to create"  )  @Valid @RequestBody Employee employee);

}
