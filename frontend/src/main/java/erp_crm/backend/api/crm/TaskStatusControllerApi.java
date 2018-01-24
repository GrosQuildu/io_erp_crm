package main.java.erp_crm.backend.api.crm;

import com.fatboyindustrial.gsonjodatime.Converters;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import main.java.erp_crm.backend.api.ConnectionApi;
import main.java.erp_crm.backend.model.DBData;
import main.java.erp_crm.backend.model.crm.TaskStatus;
import main.java.erp_crm.frontend.login.Login;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TaskStatusControllerApi {
    private ConnectionApi connection = Login.connection;
    private Gson gson = Converters.registerLocalDate(new GsonBuilder()).create();

    public void getTaskStatuses(){
        List<TaskStatus> statuses = new ArrayList<>();
        statuses.addAll(
                Arrays.asList( gson.fromJson(connection.getObjects(ConnectionApi.ObjectType.TASK_STATUSES), TaskStatus[].class) )
        );
        DBData.setTaskStatuses(statuses);
    }

    public Integer createTaskStatus(TaskStatus status){
        return connection.createObject(status.serialize(), ConnectionApi.ObjectType.TASK_STATUSES);
    }

    public void deleteTaskStatus(TaskStatus item) {
        connection.deleteObject(item.getId(), ConnectionApi.ObjectType.TASK_STATUSES);
    }
}
