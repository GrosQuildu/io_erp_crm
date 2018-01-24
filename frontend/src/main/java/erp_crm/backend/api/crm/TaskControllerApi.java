package main.java.erp_crm.backend.api.crm;

import com.fatboyindustrial.gsonjodatime.Converters;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import main.java.erp_crm.backend.api.ConnectionApi;
import main.java.erp_crm.backend.model.DBData;
import main.java.erp_crm.backend.model.crm.Task;
import main.java.erp_crm.backend.model.crm.TaskComment;
import main.java.erp_crm.backend.model.crm.TaskNote;
import main.java.erp_crm.frontend.login.Login;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static main.java.erp_crm.backend.api.ConnectionApi.*;

public class TaskControllerApi {
    private ConnectionApi connection = Login.connection;
    private Gson gson = Converters.registerLocalDate(new GsonBuilder()).create();


    public void getTasks(){
        List<Task> tasks = new ArrayList<>();
        tasks.addAll(
                Arrays.asList( gson.fromJson(connection.getObjects(ObjectType.TASKS), Task[].class) )
        );
        DBData.setTasks(tasks);
    }

    public Integer createTask(Task task){
        return connection.createObject(task.serialize(), ObjectType.TASKS);
    }

    public void updateTask(Task item){
        connection.updateObject(item.getId(), item.serialize(), ObjectType.TASKS);
    }

    public void deleteTask(Task item) {
        connection.deleteObject(item.getId(), ObjectType.TASKS);
    }
    public void deleteComment(Task task, TaskComment item) {
        connection.deleteObject(task.getId(), ObjectType.TASK_COMMENTS, item.getId(), ObjectType.TASK_COMMENTS_SECOND);
        task.getComments().remove(item);
        updateTask(task);
    }

    public void updateComment(Task task, TaskComment comment) {
        connection.updateObject(task.getId(), comment.serialize(), ObjectType.TASK_COMMENTS, comment.getId(), ObjectType.TASK_COMMENTS_SECOND);
    }

    public void updateNote(Task task, TaskNote note) {
        connection.updateObject(task.getId(), note.serialize(), ObjectType.TASK_NOTES, note.getId(), ObjectType.TASK_NOTES_SECOND);
    }

    public void deleteNote(Task task, TaskNote note) {
        connection.deleteObject(task.getId(), ObjectType.TASK_NOTES, note.getId(), ObjectType.TASK_NOTES_SECOND);
        task.getNotes().remove(note);
        updateTask(task);
    }

    public void createNote(Task meeting, TaskNote note){
        connection.createObject(note.serialize(), ConnectionApi.ObjectType.TASK_NOTES, meeting.getId(), ConnectionApi.ObjectType.TASK_NOTES_SECOND);
    }
}
