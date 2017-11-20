package io.swagger.api.common;

import io.swagger.ModelHelper;
import io.swagger.model.common.ClientRepository;
import io.swagger.model.common.ClientType;

import io.swagger.annotations.*;

import io.swagger.model.common.ClientTypeRepository;
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
public class ClientTypesApiController implements ClientTypesApi {

    /** Dependent:
        * clients
     * Depends on:
        * none
     */
    @Autowired
    ClientTypeRepository clientTypeRepository;
    @Autowired
    ClientRepository clientRepository;

    private ClientType getClientTypeHelper(Integer id) {
        ClientType clientType = clientTypeRepository.findById(id);
        if(clientType == null)
            throw new Error("Client type not found");
        return clientType;
    }

    public ResponseEntity<Integer> createClientType(@ApiParam(value = "ClientType to create"  )  @Valid @RequestBody ClientType clientType) {
        clientType = clientTypeRepository.save(clientType);
        return new ResponseEntity<Integer>(clientType.getId(), HttpStatus.OK);
    }

    public ResponseEntity<Void> deleteClientType(@ApiParam(value = "",required=true ) @PathVariable("clientTypeId") Integer clientTypeId) {
        getClientTypeHelper(clientTypeId);

        Integer articlesAssigned = clientRepository.findAllByClientTypeId(clientTypeId).size();
        if(articlesAssigned != 0)
            throw new Error(articlesAssigned + " clients are assigned to this client type");

        clientTypeRepository.delete(clientTypeId);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public ResponseEntity<ClientType> getClientType(@ApiParam(value = "",required=true ) @PathVariable("clientTypeId") Integer clientTypeId) {
        getClientTypeHelper(clientTypeId);
        ClientType clientType= clientTypeRepository.findById(clientTypeId);
        return new ResponseEntity<ClientType>(clientType, HttpStatus.OK);
    }

    public ResponseEntity<List<ClientType>> getClientTypes() {
        List<ClientType> clientTypes = (List<ClientType> ) clientTypeRepository.findAll();
        return new ResponseEntity<List<ClientType>>(clientTypes, HttpStatus.OK);
    }

    public ResponseEntity<Void> updateClientType(@ApiParam(value = "",required=true ) @PathVariable("clientTypeId") Integer clientTypeId,
        @ApiParam(value = "ClientType to create"  )  @Valid @RequestBody ClientType clientType) {
        if(clientTypeId != clientType.getId())
            throw new Error("Wrong id");

        ClientType clientTypeOld = getClientTypeHelper(clientTypeId);
        try {
            ModelHelper.combine(clientTypeOld, clientType);
        } catch (Exception e) {
            throw new Error("Wrong object");
        }

        clientType = clientTypeRepository.save(clientType);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

}
