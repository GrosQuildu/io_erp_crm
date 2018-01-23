package io.swagger.api.crm;

import io.swagger.model.BaseModel;
import io.swagger.model.common.Client;
import io.swagger.model.common.ClientRepository;
import io.swagger.model.common.Employee;
import io.swagger.model.common.EmployeeRepository;
import io.swagger.model.crm.*;

import io.swagger.annotations.*;

import io.swagger.model.crm.Contact;
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
public class ContactsApiController implements ContactsApi {

    /** Dependent:
        * meetings (hard, block on delete)
        * tasks (hard, block on delete)
     * Depends on:
        * contact group (not null)
        * employee (not null)
        * client (may be null)
     */
    @Autowired
    ContactRepository contactRepository;
    @Autowired
    ContactGroupRepository contactGroupRepository;
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    MeetingRepository meetingRepository;
    @Autowired
    TaskRepository taskRepository;
    @Autowired
    ClientRepository clientRepository;

    public ResponseEntity<Integer> createContact(@ApiParam(value = "Contact to create"  )  @Valid @RequestBody Contact contact) {
        contact = BaseModel.dependsOn(ContactGroup.class, contactGroupRepository, contact);
        contact = BaseModel.dependsOn(Employee.class, employeeRepository, contact);
        if(contact.getClient() != null)
            contact = BaseModel.dependsOn(Client.class, clientRepository, contact);

        contact.setId(null);
        contact = contactRepository.save(contact);
        return new ResponseEntity<Integer>(contact.getId(), HttpStatus.OK);
    }

    public ResponseEntity<Void> deleteContact(@ApiParam(value = "",required=true ) @PathVariable("contactId") Integer contactId) {
        Contact contact = BaseModel.getModelHelper(contactRepository, contactId);
//        BaseModel.dependent(meetingRepository, contact);
//        BaseModel.dependent(taskRepository, contact);
        contactRepository.delete(contactId);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public ResponseEntity<Contact> getContact(@ApiParam(value = "",required=true ) @PathVariable("contactId") Integer contactId) {
        Contact contact = BaseModel.getModelHelper(contactRepository, contactId);
        return new ResponseEntity<Contact>(contact, HttpStatus.OK);
    }

    public ResponseEntity<List<Contact>> getContacts() {
        List<Contact> contacts = (List<Contact>) contactRepository.findAll();
        return new ResponseEntity<List<Contact>>(contacts, HttpStatus.OK);
    }

    public ResponseEntity<Void> updateContact(@ApiParam(value = "",required=true ) @PathVariable("contactId") Integer contactId,
        @ApiParam(value = "Contact to create"  )  @Valid @RequestBody Contact contact) {
        if(contact.getId() != null && !contactId.equals(contact.getId()))
            throw new Error("Wrong id");

        contact = BaseModel.combineWithOld(contactRepository, contact, contactId);
        contact = BaseModel.dependsOn(ContactGroup.class, contactGroupRepository, contact);
        contact = BaseModel.dependsOn(Employee.class, employeeRepository, contact);
        if(contact.getClient() != null)
            contact = BaseModel.dependsOn(Client.class, clientRepository, contact);

        contact = contactRepository.save(contact);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

}
