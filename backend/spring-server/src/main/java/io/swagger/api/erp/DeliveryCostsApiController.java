package io.swagger.api.erp;

import io.swagger.ModelHelper;
import io.swagger.model.erp.DeliveryCost;

import io.swagger.annotations.*;

import io.swagger.model.erp.DeliveryCostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.core.DelegatingRelProvider;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

import javax.validation.Valid;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-11-15T00:41:28.115Z")

@Controller
public class DeliveryCostsApiController implements DeliveryCostsApi {

    @Autowired
    DeliveryCostRepository deliveryCostRepository;

    private DeliveryCost getDeliveryCostHelper(Integer id) {
        DeliveryCost deliveryCost = deliveryCostRepository.findById(id);
        if(deliveryCost == null)
            throw new Error("Delivery cost not found");
        return deliveryCost;
    }

    public ResponseEntity<Integer> createDeliveryCost(@ApiParam(value = "DeliveryCost to create"  )  @Valid @RequestBody DeliveryCost deliveryCost) {
        deliveryCost = deliveryCostRepository.save(deliveryCost);
        return new ResponseEntity<Integer>(deliveryCost.getId(), HttpStatus.OK);
    }

    public ResponseEntity<Void> deleteDeliveryCost(@ApiParam(value = "",required=true ) @PathVariable("deliveryCostId") Integer deliveryCostId) {
        getDeliveryCostHelper(deliveryCostId);
        deliveryCostRepository.delete(deliveryCostId);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public ResponseEntity<DeliveryCost> getDeliveryCost(@ApiParam(value = "",required=true ) @PathVariable("deliveryCostId") Integer deliveryCostId) {
        getDeliveryCostHelper(deliveryCostId);
        DeliveryCost deliveryCost = deliveryCostRepository.findById(deliveryCostId);
        return new ResponseEntity<DeliveryCost>(deliveryCost, HttpStatus.OK);
    }

    public ResponseEntity<List<DeliveryCost>> getDeliveryCosts() {
        List<DeliveryCost> deliveryCosts = (List<DeliveryCost> ) deliveryCostRepository.findAll();
        return new ResponseEntity<List<DeliveryCost>>(deliveryCosts, HttpStatus.OK);
    }

    public ResponseEntity<Void> updateDeliveryCost(@ApiParam(value = "",required=true ) @PathVariable("deliveryCostId") Integer deliveryCostId,
        @ApiParam(value = "DeliveryCost to create"  )  @Valid @RequestBody DeliveryCost deliveryCost) {
        if(deliveryCostId != deliveryCost.getId())
            throw new Error("Wrong id");

        DeliveryCost deliveryCostOld = getDeliveryCostHelper(deliveryCostId);
        try {
            ModelHelper.combine(deliveryCostOld, deliveryCost);
        } catch (Exception e) {
            throw new Error("Wrong object");
        }

        deliveryCost = deliveryCostRepository.save(deliveryCost);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

}
