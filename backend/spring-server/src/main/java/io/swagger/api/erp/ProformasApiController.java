package io.swagger.api.erp;

import io.swagger.ModelHelper;
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

    @Autowired
    ProformaRepository proformaRepository;
    @Autowired
    OrderRepository orderRepository;

    private Proforma getProformatHelper(Integer id) {
        Proforma proforma = proformaRepository.findById(id);
        if(proforma == null)
            throw new Error("Proforma not found");
        return proforma;
    }

    private void checkOrder(Proforma proforma) {
        Order_ order = orderRepository.findById(proforma.getOrder().getId());
        if(order == null)
            throw new Error("Order not found");
    }

    public ResponseEntity<Integer> createProforma(@ApiParam(value = "Proforma to create"  )  @Valid @RequestBody Proforma proforma) {
        checkOrder(proforma);
        proforma = proformaRepository.save(proforma);
        return new ResponseEntity<Integer>(HttpStatus.OK);
    }

    public ResponseEntity<Void> deleteProforma(@ApiParam(value = "",required=true ) @PathVariable("proformaId") Integer proformaId) {
        proformaRepository.delete(proformaId);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public ResponseEntity<Proforma> getProforma(@ApiParam(value = "",required=true ) @PathVariable("proformaId") Integer proformaId) {
        Proforma proforma = proformaRepository.findById(proformaId);
        return new ResponseEntity<Proforma>(proforma, HttpStatus.OK);
    }

    public ResponseEntity<List<Proforma>> getProformas() {
        List<Proforma> proformas = (List<Proforma>) proformaRepository.findAll();
        return new ResponseEntity<List<Proforma>>(proformas, HttpStatus.OK);
    }

    public ResponseEntity<Void> updateProforma(@ApiParam(value = "",required=true ) @PathVariable("proformaId") Integer proformaId,
        @ApiParam(value = "Proforma to create"  )  @Valid @RequestBody Proforma proforma) {
        if(proformaId != proforma.getId())
            throw new Error("Wrong proforma id");

        Proforma proformaOld = getProformatHelper(proformaId);

        try {
            ModelHelper.combine(proformaOld, proforma);
        } catch (Exception e) {
            throw new Error("Wrong proforma object");
        }

        Order_ order = orderRepository.findById(proforma.getOrder().getId());
        if(order == null)
            throw new Error("Order not found");
        proforma.setOrder(order);

        proformaRepository.save(proforma);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

}
