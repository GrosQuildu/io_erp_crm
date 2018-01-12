package main.java.erp.backend.api.common;

import io.swagger.annotations.ApiParam;
import main.java.erp.backend.model.BaseModel;
import main.java.erp.backend.model.common.Client;
import main.java.erp.backend.model.common.ClientRepository;
import main.java.erp.backend.model.common.ClientType;
import main.java.erp.backend.model.common.ClientTypeRepository;
import main.java.erp.backend.model.erp.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-11-15T00:41:28.115Z")

@Controller
public class ClientsApiController implements ClientsApi {

    /** Dependent:
        * orders (soft, do nothing on delete)
     * Depends on:
        * client types (not null)
     */
    @Autowired
    ClientRepository clientRepository;
    @Autowired
    ClientTypeRepository clientTypeRepository;
    @Autowired
    OrderRepository orderRepository;

    public ResponseEntity<Integer> createClient(@ApiParam(value = "Client to create"  )  @Valid @RequestBody Client client) {
        client = BaseModel.dependsOn(ClientType.class, clientTypeRepository, client);
        client = clientRepository.save(client);
        return new ResponseEntity<Integer>(client.getId(), HttpStatus.OK);
    }

    public ResponseEntity<Void> deleteClient(@ApiParam(value = "",required=true ) @PathVariable("clientId") Integer clientId) {
        Client client = BaseModel.getModelHelper(clientRepository, clientId);
//        BaseModel.dependent(orderRepository, client);
        clientRepository.delete(clientId);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public ResponseEntity<Client> getClient(@ApiParam(value = "",required=true ) @PathVariable("clientId") Integer clientId) {
        Client client = BaseModel.getModelHelper(clientRepository, clientId);
        return new ResponseEntity<Client>(client, HttpStatus.OK);
    }

    public ResponseEntity<List<Client>> getClients() {
        List<Client> clients = (List<Client>) clientRepository.findAll();
        return new ResponseEntity<List<Client>>(clients, HttpStatus.OK);
    }

    public ResponseEntity<Void> updateClient(@ApiParam(value = "",required=true ) @PathVariable("clientId") Integer clientId,
        @ApiParam(value = "Client to update"  )  @Valid @RequestBody Client client) {
        if(client.getId() != null && clientId != client.getId())
            throw new Error("Wrong id");
        client = BaseModel.combineWithOld(clientRepository, client);
        client = BaseModel.dependsOn(ClientType.class, clientTypeRepository, client);
        client = clientRepository.save(client);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

}