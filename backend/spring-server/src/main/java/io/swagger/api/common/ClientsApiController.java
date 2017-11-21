package io.swagger.api.common;

import io.swagger.ModelHelper;
import io.swagger.model.BaseModel;
import io.swagger.model.common.Client;

import io.swagger.annotations.*;

import io.swagger.model.common.ClientRepository;
import io.swagger.model.common.ClientType;
import io.swagger.model.common.ClientTypeRepository;
import io.swagger.model.erp.OrderRepository;
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
public class ClientsApiController implements ClientsApi {

    /** Dependent:
        * orders
     * Depends on:
        * client types
     */
    @Autowired
    ClientRepository clientRepository;
    @Autowired
    ClientTypeRepository clientTypeRepository;
    @Autowired
    OrderRepository orderRepository;

//    private Client getClientHelper(Integer id) {
//        Client client = clientRepository.findById(id);
//        if(client == null)
//            throw new Error("Client not found");
//        return client;
//    }

//    private Client dependsOnClientTypes(Client client) {
//        ClientType clientTypeFromModel = client.getClientType();
//        if(clientTypeFromModel == null) {
//            throw new Error("Client type is null");
//        }
//        ClientType clientType = clientTypeRepository.findById(clientTypeFromModel.getId());
//        if(clientType == null)
//            throw new Error("Client type not found");
//        client.setClientType(clientType);
//        return client;
//    }

//    private void dependentOrders(Integer clientId) {
//        Integer orderedArticlesAssigned = orderRepository.findAllByClientId(clientId).size();
//        if(orderedArticlesAssigned != 0)
//            throw new Error(orderedArticlesAssigned + " orders are assigned to this client");
//    }

//    private Client combineWithOld(Client client) {
//        Client articleOld = BaseModel.getModelHelper(clientRepository, client.getId());
//        try {
//            ModelHelper.combine(articleOld, client);
//        } catch (Exception e) {
//            throw new Error("Wrong client object");
//        }
//        return client;
//    }

    public ResponseEntity<Integer> createClient(@ApiParam(value = "Client to create"  )  @Valid @RequestBody Client client) {
        client = BaseModel.dependsOn(ClientType.class, client, clientTypeRepository);
        client = clientRepository.save(client);
        return new ResponseEntity<Integer>(client.getId(), HttpStatus.OK);
    }

    public ResponseEntity<Void> deleteClient(@ApiParam(value = "",required=true ) @PathVariable("clientId") Integer clientId) {
        BaseModel.getModelHelper(clientRepository, clientId);
        BaseModel.dependent(orderRepository, Client.class, clientId);
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
        @ApiParam(value = "Client to create"  )  @Valid @RequestBody Client client) {
        if(client.getId() != null && clientId != client.getId())
            throw new Error("Wrong article id");
        client = BaseModel.combineWithOld(clientRepository, client);
        client = BaseModel.dependsOn(ClientType.class, client, clientTypeRepository);
        client = clientRepository.save(client);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

}
