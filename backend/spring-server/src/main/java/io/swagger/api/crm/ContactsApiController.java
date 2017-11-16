package io.swagger.api.crm;

import io.swagger.model.crm.Contact;

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
public class ContactsApiController implements ContactsApi {



    public ResponseEntity<Integer> createContact(@ApiParam(value = "Contact to create"  )  @Valid @RequestBody Contact contact) {
        // do some magic!
        return new ResponseEntity<Integer>(HttpStatus.OK);
    }

    public ResponseEntity<Void> deleteContact(@ApiParam(value = "",required=true ) @PathVariable("contactId") Integer contactId) {
        // do some magic!
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public ResponseEntity<Contact> getContact(@ApiParam(value = "",required=true ) @PathVariable("contactId") Integer contactId) {
        // do some magic!
        return new ResponseEntity<Contact>(HttpStatus.OK);
    }

    public ResponseEntity<List<Contact>> getContacts() {
        // do some magic!
        return new ResponseEntity<List<Contact>>(HttpStatus.OK);
    }

    public ResponseEntity<Void> updateContact(@ApiParam(value = "",required=true ) @PathVariable("contactId") Integer contactId,
        @ApiParam(value = "Contact to create"  )  @Valid @RequestBody Contact contact) {
        // do some magic!
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

}
