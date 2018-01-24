package main.java.erp_crm.backend.api.common;

import com.fatboyindustrial.gsonjodatime.Converters;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import main.java.erp_crm.backend.api.ConnectionApi;
import main.java.erp_crm.backend.model.DBData;
import main.java.erp_crm.backend.model.common.Client;
import main.java.erp_crm.frontend.login.Login;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class ClientsControllerApi {
    private ConnectionApi connection = Login.connection;
    private Gson gson = Converters.registerDateTime(new GsonBuilder()).create();


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

    public void updateClient(Client client) {
        connection.updateObject(client.getId(), gson.toJson(client), ConnectionApi.ObjectType.CLIENTS);
    }
}
