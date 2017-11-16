package io.swagger.api.crm;

import io.swagger.model.crm.ContactGroup;

import io.swagger.annotations.*;

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



    public ResponseEntity<Integer> createContactGroup(@ApiParam(value = "ContactGroup to create"  )  @Valid @RequestBody ContactGroup contactType) {
        // do some magic!
        return new ResponseEntity<Integer>(HttpStatus.OK);
    }

    public ResponseEntity<Void> deleteContactGroup(@ApiParam(value = "",required=true ) @PathVariable("contactGroupId") Integer contactGroupId) {
        // do some magic!
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public ResponseEntity<ContactGroup> getContactGroup(@ApiParam(value = "",required=true ) @PathVariable("contactGroupId") Integer contactGroupId) {
        // do some magic!
        return new ResponseEntity<ContactGroup>(HttpStatus.OK);
    }

    public ResponseEntity<List<ContactGroup>> getContactGroups() {
        // do some magic!
        return new ResponseEntity<List<ContactGroup>>(HttpStatus.OK);
    }

    public ResponseEntity<Void> updateContactGroup(@ApiParam(value = "",required=true ) @PathVariable("contactGroupId") Integer contactGroupId,
        @ApiParam(value = "ContactGroup to create"  )  @Valid @RequestBody ContactGroup contactType) {
        // do some magic!
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

}
