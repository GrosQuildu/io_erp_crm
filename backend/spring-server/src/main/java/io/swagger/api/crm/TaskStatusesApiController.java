package io.swagger.api.crm;

import io.swagger.model.crm.TaskStatus;

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
public class TaskStatusesApiController implements TaskStatusesApi {



    public ResponseEntity<Integer> createTaskStatuse(@ApiParam(value = "TaskStatuse to create"  )  @Valid @RequestBody TaskStatus taskStatuse) {
        // do some magic!
        return new ResponseEntity<Integer>(HttpStatus.OK);
    }

    public ResponseEntity<Void> deleteTaskStatuse(@ApiParam(value = "",required=true ) @PathVariable("taskStatuseId") Integer taskStatuseId) {
        // do some magic!
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public ResponseEntity<TaskStatus> getTaskStatuse(@ApiParam(value = "",required=true ) @PathVariable("taskStatuseId") Integer taskStatuseId) {
        // do some magic!
        return new ResponseEntity<TaskStatus>(HttpStatus.OK);
    }

    public ResponseEntity<List<TaskStatus>> getTaskStatuses() {
        // do some magic!
        return new ResponseEntity<List<TaskStatus>>(HttpStatus.OK);
    }

    public ResponseEntity<Void> updateTaskStatuse(@ApiParam(value = "",required=true ) @PathVariable("taskStatuseId") Integer taskStatuseId,
        @ApiParam(value = "TaskStatuse to create"  )  @Valid @RequestBody TaskStatus taskStatuse) {
        // do some magic!
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

}
