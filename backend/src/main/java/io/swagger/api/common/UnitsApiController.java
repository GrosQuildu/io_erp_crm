package io.swagger.api.common;

import io.swagger.ModelHelper;
import io.swagger.model.BaseModel;
import io.swagger.model.common.Unit;

import io.swagger.annotations.*;

import io.swagger.model.common.UnitRepository;
import io.swagger.model.erp.ArticleRepository;
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
public class UnitsApiController implements UnitsApi {

    /** Dependent:
        * articles (hard, block on delete)
     * Depends on:
        * none
     */
    @Autowired
    UnitRepository unitRepository;
    @Autowired
    ArticleRepository articlesRepository;


    public ResponseEntity<Integer> createUnit(@ApiParam(value = "Unit to create"  )  @Valid @RequestBody Unit unit) {
        unit = unitRepository.save(unit);
        return new ResponseEntity<Integer>(unit.getId(), HttpStatus.OK);
    }

    public ResponseEntity<Void> deleteUnit(@ApiParam(value = "",required=true ) @PathVariable("unitId") Integer unitId) {
        Unit unit = BaseModel.getModelHelper(unitRepository, unitId);
        BaseModel.dependent(articlesRepository, unit);
        unitRepository.delete(unitId);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public ResponseEntity<Unit> getUnit(@ApiParam(value = "",required=true ) @PathVariable("unitId") Integer unitId) {
        Unit unit = BaseModel.getModelHelper(unitRepository, unitId);
        return new ResponseEntity<Unit>(unit, HttpStatus.OK);
    }

    public ResponseEntity<List<Unit>> getUnits() {
        List<Unit> units = (List<Unit> ) unitRepository.findAll();
        return new ResponseEntity<List<Unit>>(units, HttpStatus.OK);
    }

    public ResponseEntity<Void> updateUnit(@ApiParam(value = "",required=true ) @PathVariable("unitId") Integer unitId,
        @ApiParam(value = "Unit to update"  )  @Valid @RequestBody Unit unit) {
        if(unit.getId() != null && !unitId.equals(unit.getId()))
            throw new Error("Wrong id");
        unit = BaseModel.combineWithOld(unitRepository, unit, unitId);
        unit = unitRepository.save(unit);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

}
