package main.java.erp.backend.api.common;

import io.swagger.annotations.ApiParam;
import main.java.erp.backend.model.BaseModel;
import main.java.erp.backend.model.common.Unit;
import main.java.erp.backend.model.common.UnitRepository;
import main.java.erp.backend.model.erp.ArticleRepository;
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
        if(unit.getId() != null && unitId != unit.getId())
            throw new Error("Wrong id");
        unit = BaseModel.combineWithOld(unitRepository, unit);
        unit = unitRepository.save(unit);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

}
