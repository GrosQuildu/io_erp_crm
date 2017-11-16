package io.swagger.api.erp;

import io.swagger.model.erp.Proforma;

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
public class ProformasApiController implements ProformasApi {



    public ResponseEntity<Integer> createProforma(@ApiParam(value = "Proforma to create"  )  @Valid @RequestBody Proforma proforma) {
        // do some magic!
        return new ResponseEntity<Integer>(HttpStatus.OK);
    }

    public ResponseEntity<Void> deleteProforma(@ApiParam(value = "",required=true ) @PathVariable("proformaId") Integer proformaId) {
        // do some magic!
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public ResponseEntity<Proforma> getProforma(@ApiParam(value = "",required=true ) @PathVariable("proformaId") Integer proformaId) {
        // do some magic!
        return new ResponseEntity<Proforma>(HttpStatus.OK);
    }

    public ResponseEntity<List<Proforma>> getProformas() {
        // do some magic!
        return new ResponseEntity<List<Proforma>>(HttpStatus.OK);
    }

    public ResponseEntity<Void> updateProforma(@ApiParam(value = "",required=true ) @PathVariable("proformaId") Integer proformaId,
        @ApiParam(value = "Proforma to create"  )  @Valid @RequestBody Proforma proforma) {
        // do some magic!
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

}
