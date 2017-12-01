package io.swagger.api.crm;


import io.swagger.model.crm.Task;
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
public class TasksApiController implements TasksApi {

    public ResponseEntity<Integer> createTask(@ApiParam(value = "Task to create"  )  @Valid @RequestBody Task task) {
        // do some magic!
        return new ResponseEntity<Integer>(HttpStatus.OK);
    }

    public ResponseEntity<Void> deleteTask(@ApiParam(value = "",required=true ) @PathVariable("taskId") Integer taskId) {
        // do some magic!
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public ResponseEntity<Task> getTask(@ApiParam(value = "",required=true ) @PathVariable("taskId") Integer taskId) {
        // do some magic!
        return new ResponseEntity<Task>(HttpStatus.OK);
    }

    public ResponseEntity<List<Task>> getTasks() {
        // do some magic!
        return new ResponseEntity<List<Task>>(HttpStatus.OK);
    }

    public ResponseEntity<Void> updateTask(@ApiParam(value = "",required=true ) @PathVariable("taskId") Integer taskId,
        @ApiParam(value = "Task to update"  )  @Valid @RequestBody Task task) {
        // do some magic!
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

}
