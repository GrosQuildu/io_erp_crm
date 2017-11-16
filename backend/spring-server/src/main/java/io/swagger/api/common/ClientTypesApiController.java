package io.swagger.api.common;

import io.swagger.model.common.ClientType;

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
public class ClientTypesApiController implements ClientTypesApi {



    public ResponseEntity<Integer> createClientType(@ApiParam(value = "ClientType to create"  )  @Valid @RequestBody ClientType clientType) {
        // do some magic!
        return new ResponseEntity<Integer>(HttpStatus.OK);
    }

    public ResponseEntity<Void> deleteClientType(@ApiParam(value = "",required=true ) @PathVariable("clientTypeId") Integer clientTypeId) {
        // do some magic!
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public ResponseEntity<ClientType> getClientType(@ApiParam(value = "",required=true ) @PathVariable("clientTypeId") Integer clientTypeId) {
        // do some magic!
        return new ResponseEntity<ClientType>(HttpStatus.OK);
    }

    public ResponseEntity<List<ClientType>> getClientTypes() {
        // do some magic!
        return new ResponseEntity<List<ClientType>>(HttpStatus.OK);
    }

    public ResponseEntity<Void> updateClientType(@ApiParam(value = "",required=true ) @PathVariable("clientTypeId") Integer clientTypeId,
        @ApiParam(value = "ClientType to create"  )  @Valid @RequestBody ClientType clientType) {
        // do some magic!
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

}
