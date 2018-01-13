/**
 * NOTE: This class is auto generated by the swagger code generator program (2.2.3).
 * https://github.com/swagger-api/swagger-codegen
 * Do not edit the class manually.
 */
package io.swagger.api.crm;

import io.swagger.model.Error;
import io.swagger.model.crm.TaskNote;

import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import javax.validation.Valid;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-11-15T00:41:28.115Z")

@Api(value = "taskNotes", description = "the task notes API")
public interface TaskNotesApi {

    @ApiOperation(value = "Create new TaskNote for given task", notes = "", response = Integer.class, authorizations = {
            @Authorization(value = "Authorization")
    }, tags={ "CRM - task notes", })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "", response = Integer.class),
            @ApiResponse(code = 401, message = "Unauthorized - API key is missing or invalid", response = Error.class),
            @ApiResponse(code = 500, message = "Server error", response = Error.class) })

    @RequestMapping(value = "/crm/tasks/{taskId}/notes",
            produces = { "application/json" },
            consumes = { "application/json" },
            method = RequestMethod.POST)
    ResponseEntity<Integer> createTaskNotes(@ApiParam(value = "",required=true ) @PathVariable("taskId") Integer taskId,
                                            @ApiParam(value = "TaskNote to create"  )  @Valid @RequestBody TaskNote order);

    @ApiOperation(value = "Delete TaskNote", notes = "", response = Void.class, authorizations = {
            @Authorization(value = "Authorization")
    }, tags={ "CRM - task notes", })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Deleted", response = Void.class),
            @ApiResponse(code = 401, message = "Unauthorized - API key is missing or invalid", response = Error.class),
            @ApiResponse(code = 500, message = "Server error", response = Error.class) })

    @RequestMapping(value = "/crm/tasks/{taskId}/notes/{TaskNoteId}",
            produces = { "application/json" },
            consumes = { "application/json" },
            method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteTaskNote(@ApiParam(value = "",required=true ) @PathVariable("taskId") Integer taskId,
                                        @ApiParam(value = "",required=true ) @PathVariable("TaskNoteId") Integer taskNoteId);

    @ApiOperation(value = "Returns note belonging to given task", notes = "", response = TaskNote.class, authorizations = {
            @Authorization(value = "Authorization")
    }, tags={ "CRM - task notes", })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "", response = TaskNote.class),
            @ApiResponse(code = 401, message = "Unauthorized - API key is missing or invalid", response = Error.class),
            @ApiResponse(code = 500, message = "Server error", response = Error.class) })

    @RequestMapping(value = "/crm/tasks/{taskId}/notes/{TaskNoteId}",
            produces = { "application/json" },
            consumes = { "application/json" },
            method = RequestMethod.GET)
    ResponseEntity<TaskNote> getTaskNote(@ApiParam(value = "",required=true ) @PathVariable("taskId") Integer taskId,
                                         @ApiParam(value = "",required=true ) @PathVariable("TaskNoteId") Integer taskNoteId);


    @ApiOperation(value = "Returns notes belonging to given task", notes = "", response = TaskNote.class, responseContainer = "List", authorizations = {
            @Authorization(value = "Authorization")
    }, tags={ "CRM - task notes", })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "", response = TaskNote.class, responseContainer = "List"),
            @ApiResponse(code = 401, message = "Unauthorized - API key is missing or invalid", response = Error.class),
            @ApiResponse(code = 500, message = "Server error", response = Error.class) })

    @RequestMapping(value = "/crm/tasks/{taskId}/notes",
            produces = { "application/json" },
            consumes = { "application/json" },
            method = RequestMethod.GET)
    ResponseEntity<List<TaskNote>> getTaskNotes(@ApiParam(value = "",required=true ) @PathVariable("taskId") Integer taskId);

    @ApiOperation(value = "Update existing TaskNote", notes = "", response = Void.class, authorizations = {
            @Authorization(value = "Authorization")
    }, tags={ "CRM - task notes", })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Updated", response = Void.class),
            @ApiResponse(code = 401, message = "Unauthorized - API key is missing or invalid", response = Error.class),
            @ApiResponse(code = 500, message = "Server error", response = Error.class) })

    @RequestMapping(value = "/crm/tasks/{taskId}/notes/{TaskNoteId}",
            produces = { "application/json" },
            consumes = { "application/json" },
            method = RequestMethod.PUT)
    ResponseEntity<Void> updateTaskNote(@ApiParam(value = "",required=true ) @PathVariable("taskId") Integer taskId,
                                        @ApiParam(value = "",required=true ) @PathVariable("TaskNoteId") Integer taskNoteId,
                                        @ApiParam(value = "TaskNote to update"  )  @Valid @RequestBody TaskNote taskNote);

}
