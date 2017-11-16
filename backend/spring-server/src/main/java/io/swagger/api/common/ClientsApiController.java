package io.swagger.api.common;

import io.swagger.model.common.Client;

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
public class ClientsApiController implements ClientsApi {



    public ResponseEntity<Integer> createClient(@ApiParam(value = "Client to create"  )  @Valid @RequestBody Client client) {
        // do some magic!
        return new ResponseEntity<Integer>(HttpStatus.OK);
    }

    public ResponseEntity<Void> deleteClient(@ApiParam(value = "",required=true ) @PathVariable("clientId") Integer clientId) {
        // do some magic!
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public ResponseEntity<Client> getClient(@ApiParam(value = "",required=true ) @PathVariable("clientId") Integer clientId) {
        // do some magic!
        return new ResponseEntity<Client>(HttpStatus.OK);
    }

    public ResponseEntity<List<Client>> getClients() {
        // do some magic!
        return new ResponseEntity<List<Client>>(HttpStatus.OK);
    }

    public ResponseEntity<Void> updateClient(@ApiParam(value = "",required=true ) @PathVariable("clientId") Integer clientId,
        @ApiParam(value = "Client to create"  )  @Valid @RequestBody Client client) {
        // do some magic!
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

}
