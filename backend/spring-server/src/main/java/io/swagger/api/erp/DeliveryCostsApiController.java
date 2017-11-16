package io.swagger.api.erp;

import io.swagger.model.erp.DeliveryCost;

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
public class DeliveryCostsApiController implements DeliveryCostsApi {



    public ResponseEntity<Integer> createDeliveryCost(@ApiParam(value = "DeliveryCost to create"  )  @Valid @RequestBody DeliveryCost deliveryCost) {
        // do some magic!
        return new ResponseEntity<Integer>(HttpStatus.OK);
    }

    public ResponseEntity<Void> deleteDeliveryCost(@ApiParam(value = "",required=true ) @PathVariable("deliveryCostId") Integer deliveryCostId) {
        // do some magic!
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public ResponseEntity<DeliveryCost> getDeliveryCost(@ApiParam(value = "",required=true ) @PathVariable("deliveryCostId") Integer deliveryCostId) {
        // do some magic!
        return new ResponseEntity<DeliveryCost>(HttpStatus.OK);
    }

    public ResponseEntity<List<DeliveryCost>> getDeliveryCosts() {
        // do some magic!
        return new ResponseEntity<List<DeliveryCost>>(HttpStatus.OK);
    }

    public ResponseEntity<Void> updateDeliveryCost(@ApiParam(value = "",required=true ) @PathVariable("deliveryCostId") Integer deliveryCostId,
        @ApiParam(value = "DeliveryCost to create"  )  @Valid @RequestBody DeliveryCost deliveryCost) {
        // do some magic!
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

}
