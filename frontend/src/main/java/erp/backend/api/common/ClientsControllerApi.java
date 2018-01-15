package main.java.erp.backend.api.common;

import com.google.gson.Gson;
import main.java.erp.backend.api.ConnectionApi;
import main.java.erp.backend.model.DBData;
import main.java.erp.backend.model.common.Client;
import main.java.erp.backend.model.common.ClientType;
import main.java.erp.backend.model.erp.Article;
import main.java.erp.frontend.login.Login;
import org.apache.http.entity.StringEntity;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ClientsControllerApi {
    private ConnectionApi connection = Login.connection;
    private Gson gson = new Gson();


    public void getClients(){
        List<Client> clients = new ArrayList<>();
        clients.addAll(
                Arrays.asList( gson.fromJson(connection.getObjects(ConnectionApi.ObjectType.CLIENTS), Client[].class) )
        );
        DBData.setClients(clients);
    }

    public Integer createClient(Client client){
        return connection.createObject(client.serialize(), ConnectionApi.ObjectType.CLIENTS);
    }

    public void getClientTypes() {
        new ClientTypesApi().getClientTypes();
    }

    public void deleteClient(Client item) {
        connection.deleteObject(item.getId(), ConnectionApi.ObjectType.CLIENTS);
    }
}
