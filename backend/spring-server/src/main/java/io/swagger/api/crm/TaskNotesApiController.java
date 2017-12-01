package io.swagger.api.crm;


import io.swagger.model.crm.TaskNote;

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
public class TaskNotesApiController implements TaskNotesApi {
    public ResponseEntity<Integer> createTaskNotes(@ApiParam(value = "",required=true ) @PathVariable("taskId") Integer taskId,
                                                   @ApiParam(value = "TaskNote to create"  )  @Valid @RequestBody TaskNote order) {
        // do some magic!
        return new ResponseEntity<Integer>(HttpStatus.OK);
    }

    public ResponseEntity<Void> deleteTaskNote(@ApiParam(value = "",required=true ) @PathVariable("taskId") Integer taskId,
                                               @ApiParam(value = "",required=true ) @PathVariable("TaskNoteId") Integer taskNoteId) {
        // do some magic!
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public ResponseEntity<TaskNote> getTaskNote(@ApiParam(value = "",required=true ) @PathVariable("taskId") Integer taskId,
                                                @ApiParam(value = "",required=true ) @PathVariable("TaskNoteId") Integer taskNoteId) {
        // do some magic!
        return new ResponseEntity<TaskNote>(HttpStatus.OK);
    }

    public ResponseEntity<List<TaskNote>> getTaskNotes(@ApiParam(value = "",required=true ) @PathVariable("taskId") Integer taskId) {
        // do some magic!
        return new ResponseEntity<List<TaskNote>>(HttpStatus.OK);
    }

    public ResponseEntity<Void> updateTaskNote(@ApiParam(value = "",required=true ) @PathVariable("taskId") Integer taskId,
                                               @ApiParam(value = "",required=true ) @PathVariable("TaskNoteId") Integer taskNoteId,
                                               @ApiParam(value = "TaskNote to update"  )  @Valid @RequestBody TaskNote taskNote) {
        // do some magic!
        return new ResponseEntity<Void>(HttpStatus.OK);
    }
}
