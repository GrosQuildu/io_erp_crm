package main.java.erp.backend.api.erp;

import io.swagger.annotations.ApiParam;
import main.java.erp.backend.model.BaseModel;
import main.java.erp.backend.model.erp.DeliveryCost;
import main.java.erp.backend.model.erp.DeliveryCostRepository;
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
public class DeliveryCostsApiController implements DeliveryCostsApi {

    /** Dependent:
        * none
     * Depends on:
        * none
     */
    @Autowired
    DeliveryCostRepository deliveryCostRepository;

    public ResponseEntity<Integer> createDeliveryCost(@ApiParam(value = "DeliveryCost to create"  )  @Valid @RequestBody DeliveryCost deliveryCost) {
        deliveryCost = deliveryCostRepository.save(deliveryCost);
        return new ResponseEntity<Integer>(deliveryCost.getId(), HttpStatus.OK);
    }

    public ResponseEntity<Void> deleteDeliveryCost(@ApiParam(value = "",required=true ) @PathVariable("deliveryCostId") Integer deliveryCostId) {
        DeliveryCost deliveryCost = BaseModel.getModelHelper(deliveryCostRepository, deliveryCostId);
        deliveryCostRepository.delete(deliveryCost);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public ResponseEntity<DeliveryCost> getDeliveryCost(@ApiParam(value = "",required=true ) @PathVariable("deliveryCostId") Integer deliveryCostId) {
        DeliveryCost deliveryCost = BaseModel.getModelHelper(deliveryCostRepository, deliveryCostId);
        return new ResponseEntity<DeliveryCost>(deliveryCost, HttpStatus.OK);
    }

    public ResponseEntity<List<DeliveryCost>> getDeliveryCosts() {
        List<DeliveryCost> deliveryCosts = (List<DeliveryCost> ) deliveryCostRepository.findAll();
        return new ResponseEntity<List<DeliveryCost>>(deliveryCosts, HttpStatus.OK);
    }

    public ResponseEntity<Void> updateDeliveryCost(@ApiParam(value = "",required=true ) @PathVariable("deliveryCostId") Integer deliveryCostId,
        @ApiParam(value = "DeliveryCost to create"  )  @Valid @RequestBody DeliveryCost deliveryCost) {
        if(deliveryCost.getId() != null && deliveryCostId != deliveryCost.getId())
            throw new Error("Wrong id");
        deliveryCost = BaseModel.combineWithOld(deliveryCostRepository, deliveryCost);
        deliveryCost = deliveryCostRepository.save(deliveryCost);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

}
