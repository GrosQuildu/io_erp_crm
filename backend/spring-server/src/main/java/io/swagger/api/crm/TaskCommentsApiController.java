package io.swagger.api.crm;


import io.swagger.model.crm.TaskComment;

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
public class TaskCommentsApiController implements TaskCommentsApi {
    public ResponseEntity<Integer> createTaskComments(@ApiParam(value = "",required=true ) @PathVariable("taskId") Integer taskId,
                                                      @ApiParam(value = "TaskComment to create"  )  @Valid @RequestBody TaskComment order) {
        // do some magic!
        return new ResponseEntity<Integer>(HttpStatus.OK);
    }

    public ResponseEntity<Void> deleteTaskComment(@ApiParam(value = "",required=true ) @PathVariable("taskId") Integer taskId,
                                                  @ApiParam(value = "",required=true ) @PathVariable("TaskCommentId") Integer taskCommentId) {
        // do some magic!
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public ResponseEntity<TaskComment> getTaskComment(@ApiParam(value = "",required=true ) @PathVariable("taskId") Integer taskId,
                                                      @ApiParam(value = "",required=true ) @PathVariable("TaskCommentId") Integer taskCommentId) {
        // do some magic!
        return new ResponseEntity<TaskComment>(HttpStatus.OK);
    }

    public ResponseEntity<List<TaskComment>> getTaskComments(@ApiParam(value = "",required=true ) @PathVariable("taskId") Integer taskId) {
        // do some magic!
        return new ResponseEntity<List<TaskComment>>(HttpStatus.OK);
    }

    public ResponseEntity<Void> updateTaskComment(@ApiParam(value = "",required=true ) @PathVariable("taskId") Integer taskId,
                                                  @ApiParam(value = "",required=true ) @PathVariable("TaskCommentId") Integer taskCommentId,
                                                  @ApiParam(value = "TaskComment to update"  )  @Valid @RequestBody TaskComment taskComment) {
        // do some magic!
        return new ResponseEntity<Void>(HttpStatus.OK);
    }
}
