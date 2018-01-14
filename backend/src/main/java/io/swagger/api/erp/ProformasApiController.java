package io.swagger.api.erp;

import io.swagger.ModelHelper;
import io.swagger.model.BaseModel;
import io.swagger.model.erp.*;

import io.swagger.annotations.*;

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
public class ProformasApiController implements ProformasApi {

    /** Dependent:
        * none
     * Depends on:
        * order (not null)
     */
    @Autowired
    ProformaRepository proformaRepository;
    @Autowired
    OrderRepository orderRepository;


    public ResponseEntity<Integer> createProforma(@ApiParam(value = "Proforma to create"  )  @Valid @RequestBody Proforma proforma) {
        proforma = BaseModel.dependsOn(Order_.class, orderRepository, proforma, "Order");
        proforma = proformaRepository.save(proforma);
        return new ResponseEntity<Integer>(HttpStatus.OK);
    }

    public ResponseEntity<Void> deleteProforma(@ApiParam(value = "",required=true ) @PathVariable("proformaId") Integer proformaId) {
        Proforma proforma = BaseModel.getModelHelper(proformaRepository, proformaId);
        proformaRepository.delete(proforma);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public ResponseEntity<Proforma> getProforma(@ApiParam(value = "",required=true ) @PathVariable("proformaId") Integer proformaId) {
        Proforma proforma = BaseModel.getModelHelper(proformaRepository, proformaId);
        return new ResponseEntity<Proforma>(proforma, HttpStatus.OK);
    }

    public ResponseEntity<List<Proforma>> getProformas() {
        List<Proforma> proformas = (List<Proforma>) proformaRepository.findAll();
        return new ResponseEntity<List<Proforma>>(proformas, HttpStatus.OK);
    }

    public ResponseEntity<Void> updateProforma(@ApiParam(value = "",required=true ) @PathVariable("proformaId") Integer proformaId,
        @ApiParam(value = "Proforma to create"  )  @Valid @RequestBody Proforma proforma) {
        if(proforma.getId() != null && !proformaId.equals(proforma.getId()))
            throw new Error("Wrong id");
        proforma = BaseModel.combineWithOld(proformaRepository, proforma, proformaId);
        proforma = BaseModel.dependsOn(Order_.class, orderRepository, proforma, "Order");
        proformaRepository.save(proforma);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

}
