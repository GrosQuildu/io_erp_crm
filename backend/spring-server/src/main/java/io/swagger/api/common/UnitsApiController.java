package io.swagger.api.common;

import io.swagger.model.common.Unit;

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
public class UnitsApiController implements UnitsApi {



    public ResponseEntity<Integer> createUnit(@ApiParam(value = "Unit to create"  )  @Valid @RequestBody Unit unit) {
        // do some magic!
        return new ResponseEntity<Integer>(HttpStatus.OK);
    }

    public ResponseEntity<Void> deleteUnit(@ApiParam(value = "",required=true ) @PathVariable("unitId") Integer unitId) {
        // do some magic!
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public ResponseEntity<Unit> getUnit(@ApiParam(value = "",required=true ) @PathVariable("unitId") Integer unitId) {
        // do some magic!
        return new ResponseEntity<Unit>(HttpStatus.OK);
    }

    public ResponseEntity<List<Unit>> getUnits() {
        // do some magic!
        return new ResponseEntity<List<Unit>>(HttpStatus.OK);
    }

    public ResponseEntity<Void> updateUnit(@ApiParam(value = "",required=true ) @PathVariable("unitId") Integer unitId,
        @ApiParam(value = "Unit to update"  )  @Valid @RequestBody Unit unit) {
        // do some magic!
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

}
