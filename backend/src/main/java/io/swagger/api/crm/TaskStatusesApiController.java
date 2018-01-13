package io.swagger.api.crm;

import io.swagger.model.BaseModel;
import io.swagger.model.crm.TaskRepository;
import io.swagger.model.crm.TaskStatus;

import io.swagger.annotations.*;

import io.swagger.model.crm.TaskStatusRepository;
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
public class TaskStatusesApiController implements TaskStatusesApi {
    /** Dependent:
        * tasks (hard, block on delete)
     * Depends on:
         * none
     */
    @Autowired
    TaskStatusRepository taskStatusRepository;
    @Autowired
    TaskRepository taskRepository;

    public ResponseEntity<Integer> createTaskStatus(@ApiParam(value = "TaskStatus to create"  )  @Valid @RequestBody TaskStatus taskStatus) {
        taskStatus = taskStatusRepository.save(taskStatus);
        return new ResponseEntity<Integer>(taskStatus.getId(), HttpStatus.OK);
    }

    public ResponseEntity<Void> deleteTaskStatus(@ApiParam(value = "",required=true ) @PathVariable("taskStatusId") Integer taskStatusId) {
        TaskStatus taskStatus = BaseModel.getModelHelper(taskStatusRepository, taskStatusId);
        BaseModel.dependent(taskRepository, taskStatus);
        taskStatusRepository.delete(taskStatusId);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public ResponseEntity<TaskStatus> getTaskStatus(@ApiParam(value = "",required=true ) @PathVariable("taskStatusId") Integer taskStatusId) {
        TaskStatus taskStatus = BaseModel.getModelHelper(taskStatusRepository, taskStatusId);
        return new ResponseEntity<TaskStatus>(taskStatus, HttpStatus.OK);
    }

    public ResponseEntity<List<TaskStatus>> getTaskStatuses() {
        List<TaskStatus> taskStatuses = (List<TaskStatus> ) taskStatusRepository.findAll();
        return new ResponseEntity<List<TaskStatus>>(taskStatuses, HttpStatus.OK);
    }

    public ResponseEntity<Void> updateTaskStatus(@ApiParam(value = "",required=true ) @PathVariable("taskStatusId") Integer taskStatusId,
        @ApiParam(value = "TaskStatus to create"  )  @Valid @RequestBody TaskStatus taskStatus) {
        if(taskStatus.getId() == null || taskStatusId != taskStatus.getId())
            throw new Error("Wrong id");
        taskStatus = BaseModel.combineWithOld(taskStatusRepository, taskStatus);
        taskStatus = taskStatusRepository.save(taskStatus);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

}
