package io.swagger.api.crm;



import io.swagger.annotations.ApiParam;
import io.swagger.model.BaseModel;
import io.swagger.model.common.Employee;
import io.swagger.model.common.EmployeeRepository;
import io.swagger.model.crm.*;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-11-15T00:41:28.115Z")

@Controller
public class TasksApiController implements TasksApi {

    /** Dependent:
        * taskNotes (cascade, remove all on delete)
        * taskComments (cascade, remove all on delete)
     * Depends on:
        * taskStatus (not null)
        * contacts (may be null, list)
        * comments (may be null, list)
        * notes (may be null, list)
        * employee (not null)
        * employeeCommissioned (may be null)
     */
    @Autowired
    TaskRepository taskRepository;
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    TaskStatusRepository taskStatusRepository;
    @Autowired
    ContactRepository contactRepository;
    @Autowired
    TaskCommentRepository taskCommentRepository;
    @Autowired
    TaskNoteRepository taskNoteRepository;
    
    public ResponseEntity<Integer> createTask(@ApiParam(value = "Task to create"  )  @RequestBody Task task) {
        task = BaseModel.dependsOn(Employee.class, employeeRepository, task, "EmployeeCommissioned");
        if(task.getEmployee() != null)
            task = BaseModel.dependsOn(Employee.class, employeeRepository, task);
        task = BaseModel.dependsOn(TaskStatus.class, taskStatusRepository, task);

        if(task.getContacts() != null) {
            ArrayList<Contact> contacts = new ArrayList<Contact>();
            for (Contact contact : task.getContacts()) {
                Contact contactTmp = contactRepository.findById(contact.getId());
                if (contactTmp != null) {
                    contacts.add(contactTmp);
                } else {
                    throw new Error("Contact not found");
                }
            }
            task.setContacts(contacts);
        }

        if(task.getComments() != null) {
            ArrayList<TaskComment> comments = new ArrayList<TaskComment>();
            for (TaskComment comment : task.getComments()) {
                TaskComment commentTmp = taskCommentRepository.findById(comment.getId());
                if (commentTmp != null) {
                    comments.add(commentTmp);
                } else {
                    throw new Error("Comment not found");
                }
            }
            task.setComments(comments);
        }

        if(task.getNotes() != null) {
            ArrayList<TaskNote> notes = new ArrayList<TaskNote>();
            for (TaskNote note : task.getNotes()) {
                TaskNote noteTmp = taskNoteRepository.findById(note.getId());
                if (noteTmp != null) {
                    notes.add(noteTmp);
                } else {
                    throw new Error("Note not found");
                }
            }
            task.setNotes(notes);
        }

        task = taskRepository.save(task);
        return new ResponseEntity<Integer>(task.getId(), HttpStatus.OK);
    }

    public ResponseEntity<Void> deleteTask(@ApiParam(value = "",required=true ) @PathVariable("taskId") Integer taskId) {
        Task task = BaseModel.getModelHelper(taskRepository, taskId);
        taskNoteRepository.delete(task.getNotes());
        taskCommentRepository.delete(task.getComments());
        taskRepository.delete(taskId);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public ResponseEntity<Task> getTask(@ApiParam(value = "",required=true ) @PathVariable("taskId") Integer taskId) {
        Task task = BaseModel.getModelHelper(taskRepository, taskId);
        return new ResponseEntity<Task>(task, HttpStatus.OK);
    }

    public ResponseEntity<List<Task>> getTasks() {
        List<Task> tasks = (List<Task>) taskRepository.findAll();
        return new ResponseEntity<List<Task>>(tasks, HttpStatus.OK);
    }

    public ResponseEntity<Void> updateTask(@ApiParam(value = "",required=true ) @PathVariable("taskId") Integer taskId,
        @ApiParam(value = "Task to update"  )  @RequestBody Task task) {
        if(task.getId() != null && taskId != task.getId())
            throw new Error("Wrong id");

        task = BaseModel.combineWithOld(taskRepository, task);

        task = BaseModel.dependsOn(Employee.class, employeeRepository, task, "EmployeeCommissioned");
        if(task.getEmployee() != null)
            task = BaseModel.dependsOn(Employee.class, employeeRepository, task);
        task = BaseModel.dependsOn(TaskStatus.class, taskStatusRepository, task);

        if(task.getContacts() != null) {
            ArrayList<Contact> contacts = new ArrayList<Contact>();
            for (Contact contact : task.getContacts()) {
                Contact contactTmp = contactRepository.findById(contact.getId());
                if (contactTmp != null) {
                    contacts.add(contactTmp);
                } else {
                    throw new Error("Contact not found");
                }
            }
            task.setContacts(contacts);
        }

        if(task.getComments() != null) {
            ArrayList<TaskComment> comments = new ArrayList<TaskComment>();
            for (TaskComment comment : task.getComments()) {
                TaskComment commentTmp = taskCommentRepository.findById(comment.getId());
                if (commentTmp != null) {
                    comments.add(commentTmp);
                } else {
                    throw new Error("Comment not found");
                }
            }
            task.setComments(comments);
        }

        if(task.getNotes() != null) {
            ArrayList<TaskNote> notes = new ArrayList<TaskNote>();
            for (TaskNote note : task.getNotes()) {
                TaskNote noteTmp = taskNoteRepository.findById(note.getId());
                if (noteTmp != null) {
                    notes.add(noteTmp);
                } else {
                    throw new Error("Note not found");
                }
            }
            task.setNotes(notes);
        }

        taskRepository.save(task);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

}
