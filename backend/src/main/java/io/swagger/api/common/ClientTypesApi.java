/**
 * NOTE: This class is auto generated by the swagger code generator program (2.2.3).
 * https://github.com/swagger-api/swagger-codegen
 * Do not edit the class manually.
 */
package io.swagger.api.common;

import io.swagger.model.common.ClientType;
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

@Api(value = "clientTypes", description = "the clientTypes API")
public interface ClientTypesApi {

    @ApiOperation(value = "Create new clientType", notes = "", response = Integer.class, authorizations = {
        @Authorization(value = "Authorization")
    }, tags={ "common - clientTypes", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "", response = Integer.class),
        @ApiResponse(code = 401, message = "Unauthorized - API key is missing or invalid", response = Error.class),
        @ApiResponse(code = 500, message = "Server error", response = Error.class) })
    
    @RequestMapping(value = "/clientTypes",
        produces = { "application/json" }, 
        consumes = { "application/json" },
        method = RequestMethod.POST)
    ResponseEntity<Integer> createClientType(@ApiParam(value = "ClientType to create"  )  @RequestBody ClientType clientType);


    @ApiOperation(value = "Delete clientType", notes = "", response = Void.class, authorizations = {
        @Authorization(value = "Authorization")
    }, tags={ "common - clientTypes", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Deleted", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized - API key is missing or invalid", response = Error.class),
        @ApiResponse(code = 500, message = "Server error", response = Error.class) })
    
    @RequestMapping(value = "/clientTypes/{clientTypeId}",
        produces = { "application/json" }, 
        consumes = { "application/json" },
        method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteClientType(@ApiParam(value = "",required=true ) @PathVariable("clientTypeId") Integer clientTypeId);


    @ApiOperation(value = "Returns ClientType", notes = "", response = ClientType.class, authorizations = {
        @Authorization(value = "Authorization")
    }, tags={ "common - clientTypes", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "", response = ClientType.class),
        @ApiResponse(code = 401, message = "Unauthorized - API key is missing or invalid", response = Error.class),
        @ApiResponse(code = 500, message = "Server error", response = Error.class) })
    
    @RequestMapping(value = "/clientTypes/{clientTypeId}",
        produces = { "application/json" }, 
        consumes = { "application/json" },
        method = RequestMethod.GET)
    ResponseEntity<ClientType> getClientType(@ApiParam(value = "",required=true ) @PathVariable("clientTypeId") Integer clientTypeId);


    @ApiOperation(value = "Returns list of ClientTypes", notes = "", response = ClientType.class, responseContainer = "List", authorizations = {
        @Authorization(value = "Authorization")
    }, tags={ "common - clientTypes", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "", response = ClientType.class, responseContainer = "List"),
        @ApiResponse(code = 401, message = "Unauthorized - API key is missing or invalid", response = Error.class),
        @ApiResponse(code = 500, message = "Server error", response = Error.class) })
    
    @RequestMapping(value = "/clientTypes",
        produces = { "application/json" }, 
        consumes = { "application/json" },
        method = RequestMethod.GET)
    ResponseEntity<List<ClientType>> getClientTypes();


    @ApiOperation(value = "Update existing clientType", notes = "", response = Void.class, authorizations = {
        @Authorization(value = "Authorization")
    }, tags={ "common - clientTypes", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Updated", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized - API key is missing or invalid", response = Error.class),
        @ApiResponse(code = 500, message = "Server error", response = Error.class) })
    
    @RequestMapping(value = "/clientTypes/{clientTypeId}",
        produces = { "application/json" }, 
        consumes = { "application/json" },
        method = RequestMethod.PUT)
    ResponseEntity<Void> updateClientType(@ApiParam(value = "",required=true ) @PathVariable("clientTypeId") Integer clientTypeId,@ApiParam(value = "ClientType to create"  )  @RequestBody ClientType clientType);

}
