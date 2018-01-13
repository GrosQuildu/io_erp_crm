package io.swagger.api.crm;


import io.swagger.model.BaseModel;
import io.swagger.model.crm.*;

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
public class TaskCommentsApiController implements TaskCommentsApi {

    /** Dependent:
         * none
     * Depends on:
        * task (not null)
     */
    @Autowired
    TaskCommentRepository taskCommentRepository;
    @Autowired
    TaskRepository taskRepository;
    
    private void checkTask(Integer taskId, TaskComment taskComment) {
        Task task = taskRepository.findById(taskId);
        if(task == null)
            throw new Error("Task not found");
        if(taskId != taskComment.getTask().getId())
            throw new Error("Wrong task id");
    }
    
    public ResponseEntity<Integer> createTaskComments(@ApiParam(value = "",required=true ) @PathVariable("taskId") Integer taskId,
                                                      @ApiParam(value = "TaskComment to create"  )  @Valid @RequestBody TaskComment taskComment) {
        checkTask(taskId, taskComment);
        taskComment = BaseModel.dependsOn(Task.class, taskRepository, taskComment);
        taskComment = taskCommentRepository.save(taskComment);
        return new ResponseEntity<Integer>(taskComment.getId(), HttpStatus.OK);
    }

    public ResponseEntity<Void> deleteTaskComment(@ApiParam(value = "",required=true ) @PathVariable("taskId") Integer taskId,
                                                  @ApiParam(value = "",required=true ) @PathVariable("TaskCommentId") Integer taskCommentId) {
        TaskComment taskComment = BaseModel.getModelHelper(taskCommentRepository, taskCommentId);
        checkTask(taskId, taskComment);
        taskCommentRepository.delete(taskComment);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public ResponseEntity<TaskComment> getTaskComment(@ApiParam(value = "",required=true ) @PathVariable("taskId") Integer taskId,
                                                      @ApiParam(value = "",required=true ) @PathVariable("TaskCommentId") Integer taskCommentId) {
        TaskComment taskComment = BaseModel.getModelHelper(taskCommentRepository, taskCommentId);
        checkTask(taskId, taskComment);
        return new ResponseEntity<TaskComment>(taskComment, HttpStatus.OK);
    }

    public ResponseEntity<List<TaskComment>> getTaskComments(@ApiParam(value = "",required=true ) @PathVariable("taskId") Integer taskId) {
        Task task = BaseModel.getModelHelper(taskRepository, taskId);
        List<TaskComment> taskComments = task.getComments();
        return new ResponseEntity<List<TaskComment>>(taskComments, HttpStatus.OK);
    }

    public ResponseEntity<Void> updateTaskComment(@ApiParam(value = "",required=true ) @PathVariable("taskId") Integer taskId,
                                                  @ApiParam(value = "",required=true ) @PathVariable("TaskCommentId") Integer taskCommentId,
                                                  @ApiParam(value = "TaskComment to update"  )  @Valid @RequestBody TaskComment taskComment) {
        if(taskComment.getId() != null && taskCommentId != taskComment.getId())
            throw new Error("Wrong id");

        TaskComment taskCommentOld = BaseModel.getModelHelper(taskCommentRepository, taskCommentId);
        checkTask(taskId, taskComment);

        taskComment = BaseModel.combineWithOld(taskCommentRepository, taskComment);
        taskComment = BaseModel.dependsOn(Task.class, taskRepository, taskComment);

        taskCommentRepository.save(taskComment);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }
}
