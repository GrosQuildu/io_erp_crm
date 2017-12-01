package io.swagger.api.crm;

import io.swagger.model.BaseModel;
import io.swagger.model.crm.ContactGroup;

import io.swagger.annotations.*;

import io.swagger.model.crm.ContactGroupRepository;
import io.swagger.model.crm.ContactRepository;
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
public class ContactGroupsApiController implements ContactGroupsApi {

    /** Dependent:
        * contacts (hard, block on delete)
     * Depends on:
        * none
     */
    @Autowired
    ContactGroupRepository contactGroupRepository;
    @Autowired
    ContactRepository clientRepository;

    public ResponseEntity<Integer> createContactGroup(@ApiParam(value = "ContactGroup to create"  )  @Valid @RequestBody ContactGroup contactGroup) {
        contactGroup = contactGroupRepository.save(contactGroup);
        return new ResponseEntity<Integer>(contactGroup.getId(), HttpStatus.OK);
    }

    public ResponseEntity<Void> deleteContactGroup(@ApiParam(value = "",required=true ) @PathVariable("contactGroupId") Integer contactGroupId) {
        ContactGroup contactGroup = BaseModel.getModelHelper(clientRepository, contactGroupId);
        BaseModel.dependent(clientRepository, contactGroup);
        contactGroupRepository.delete(contactGroupId);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public ResponseEntity<ContactGroup> getContactGroup(@ApiParam(value = "",required=true ) @PathVariable("contactGroupId") Integer contactGroupId) {
        ContactGroup contactGroup = BaseModel.getModelHelper(clientRepository, contactGroupId);
        return new ResponseEntity<ContactGroup>(contactGroup, HttpStatus.OK);
    }

    public ResponseEntity<List<ContactGroup>> getContactGroups() {
        List<ContactGroup> contactGroups = (List<ContactGroup> ) contactGroupRepository.findAll();
        return new ResponseEntity<List<ContactGroup>>(contactGroups, HttpStatus.OK);
    }

    public ResponseEntity<Void> updateContactGroup(@ApiParam(value = "",required=true ) @PathVariable("contactGroupId") Integer contactGroupId,
        @ApiParam(value = "ContactGroup to create"  )  @Valid @RequestBody ContactGroup contactGroup) {
        if(contactGroup.getId() != null && contactGroupId != contactGroup.getId())
            throw new Error("Wrong id");
        contactGroup = BaseModel.combineWithOld(contactGroupRepository, contactGroup);
        contactGroup = contactGroupRepository.save(contactGroup);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

}
