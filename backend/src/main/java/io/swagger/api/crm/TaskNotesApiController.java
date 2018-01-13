package io.swagger.api.crm;


import io.swagger.model.BaseModel;
import io.swagger.model.crm.Task;
import io.swagger.model.crm.TaskNote;

import io.swagger.annotations.*;

import io.swagger.model.crm.TaskNoteRepository;
import io.swagger.model.crm.TaskRepository;
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
public class TaskNotesApiController implements TaskNotesApi {

    /** Dependent:
        * none
     * Depends on:
        * task (not null)
     */
    @Autowired
    TaskNoteRepository taskNoteRepository;
    @Autowired
    TaskRepository taskRepository;

    private void checkTask(Integer taskId, TaskNote taskNote) {
        Task task = taskRepository.findById(taskId);
        if(task == null)
            throw new Error("Task not found");
        if(taskId != taskNote.getTask().getId())
            throw new Error("Wrong task id");
    }

    public ResponseEntity<Integer> createTaskNotes(@ApiParam(value = "",required=true ) @PathVariable("taskId") Integer taskId,
                                                   @ApiParam(value = "TaskNote to create"  )  @RequestBody TaskNote taskNote) {
        checkTask(taskId, taskNote);
        taskNote = BaseModel.dependsOn(Task.class, taskRepository, taskNote);
        taskNote = taskNoteRepository.save(taskNote);
        return new ResponseEntity<Integer>(taskNote.getId(), HttpStatus.OK);
    }

    public ResponseEntity<Void> deleteTaskNote(@ApiParam(value = "",required=true ) @PathVariable("taskId") Integer taskId,
                                               @ApiParam(value = "",required=true ) @PathVariable("TaskNoteId") Integer taskNoteId) {
        TaskNote taskNote = BaseModel.getModelHelper(taskNoteRepository, taskNoteId);
        checkTask(taskId, taskNote);
        taskNoteRepository.delete(taskNote);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public ResponseEntity<TaskNote> getTaskNote(@ApiParam(value = "",required=true ) @PathVariable("taskId") Integer taskId,
                                                @ApiParam(value = "",required=true ) @PathVariable("TaskNoteId") Integer taskNoteId) {
        TaskNote taskNote = BaseModel.getModelHelper(taskNoteRepository, taskNoteId);
        checkTask(taskId, taskNote);
        return new ResponseEntity<TaskNote>(taskNote, HttpStatus.OK);
    }

    public ResponseEntity<List<TaskNote>> getTaskNotes(@ApiParam(value = "",required=true ) @PathVariable("taskId") Integer taskId) {
        Task task = BaseModel.getModelHelper(taskRepository, taskId);
        List<TaskNote> taskNotes = task.getNotes();
        return new ResponseEntity<List<TaskNote>>(taskNotes, HttpStatus.OK);
    }

    public ResponseEntity<Void> updateTaskNote(@ApiParam(value = "",required=true ) @PathVariable("taskId") Integer taskId,
                                               @ApiParam(value = "",required=true ) @PathVariable("TaskNoteId") Integer taskNoteId,
                                               @ApiParam(value = "TaskNote to update"  )  @RequestBody TaskNote taskNote) {
        if(taskNote.getId() != null && taskNoteId != taskNote.getId())
            throw new Error("Wrong id");

        TaskNote taskNoteOld = BaseModel.getModelHelper(taskNoteRepository, taskNoteId);
        checkTask(taskId, taskNote);

        taskNote = BaseModel.combineWithOld(taskNoteRepository, taskNote);
        taskNote = BaseModel.dependsOn(Task.class, taskRepository, taskNote);

        taskNoteRepository.save(taskNote);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }
}
