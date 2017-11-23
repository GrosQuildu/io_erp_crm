/**
 * NOTE: This class is auto generated by the swagger code generator program (2.2.3).
 * https://github.com/swagger-api/swagger-codegen
 * Do not edit the class manually.
 */
package io.swagger.api.crm;

import io.swagger.model.crm.Contact;
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

@Api(value = "contacts", description = "the contacts API")
public interface ContactsApi {

    @ApiOperation(value = "Create new contact", notes = "", response = Integer.class, authorizations = {
        @Authorization(value = "APIKeyHeader")
    }, tags={ "CRM - contacts", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "", response = Integer.class),
        @ApiResponse(code = 401, message = "Unauthorized - API key is missing or invalid", response = Error.class),
        @ApiResponse(code = 500, message = "Server error", response = Error.class) })
    
    @RequestMapping(value = "/crm/contacts",
        produces = { "application/json" }, 
        consumes = { "application/json" },
        method = RequestMethod.POST)
    ResponseEntity<Integer> createContact(@ApiParam(value = "Contact to create"  )  @Valid @RequestBody Contact contact);


    @ApiOperation(value = "Delete contact", notes = "", response = Void.class, authorizations = {
        @Authorization(value = "APIKeyHeader")
    }, tags={ "CRM - contacts", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Deleted", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized - API key is missing or invalid", response = Error.class),
        @ApiResponse(code = 500, message = "Server error", response = Error.class) })
    
    @RequestMapping(value = "/crm/contacts/{contactId}",
        produces = { "application/json" }, 
        consumes = { "application/json" },
        method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteContact(@ApiParam(value = "",required=true ) @PathVariable("contactId") Integer contactId);


    @ApiOperation(value = "Returns Contact", notes = "", response = Contact.class, authorizations = {
        @Authorization(value = "APIKeyHeader")
    }, tags={ "CRM - contacts", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "", response = Contact.class),
        @ApiResponse(code = 401, message = "Unauthorized - API key is missing or invalid", response = Error.class),
        @ApiResponse(code = 500, message = "Server error", response = Error.class) })
    
    @RequestMapping(value = "/crm/contacts/{contactId}",
        produces = { "application/json" }, 
        consumes = { "application/json" },
        method = RequestMethod.GET)
    ResponseEntity<Contact> getContact(@ApiParam(value = "",required=true ) @PathVariable("contactId") Integer contactId);


    @ApiOperation(value = "Returns list of Contacts", notes = "", response = Contact.class, responseContainer = "List", authorizations = {
        @Authorization(value = "APIKeyHeader")
    }, tags={ "CRM - contacts", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "", response = Contact.class, responseContainer = "List"),
        @ApiResponse(code = 401, message = "Unauthorized - API key is missing or invalid", response = Error.class),
        @ApiResponse(code = 500, message = "Server error", response = Error.class) })
    
    @RequestMapping(value = "/crm/contacts",
        produces = { "application/json" }, 
        consumes = { "application/json" },
        method = RequestMethod.GET)
    ResponseEntity<List<Contact>> getContacts();


    @ApiOperation(value = "Update existing contact", notes = "", response = Void.class, authorizations = {
        @Authorization(value = "APIKeyHeader")
    }, tags={ "CRM - contacts", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Updated", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized - API key is missing or invalid", response = Error.class),
        @ApiResponse(code = 500, message = "Server error", response = Error.class) })
    
    @RequestMapping(value = "/crm/contacts/{contactId}",
        produces = { "application/json" }, 
        consumes = { "application/json" },
        method = RequestMethod.PUT)
    ResponseEntity<Void> updateContact(@ApiParam(value = "",required=true ) @PathVariable("contactId") Integer contactId,@ApiParam(value = "Contact to create"  )  @Valid @RequestBody Contact contact);

}
