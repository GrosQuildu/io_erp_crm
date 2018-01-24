package main.java.erp_crm.backend.api.common;

import com.fatboyindustrial.gsonjodatime.Converters;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import main.java.erp_crm.backend.api.ConnectionApi;
import main.java.erp_crm.backend.model.DBData;
import main.java.erp_crm.backend.model.common.ClientType;
import main.java.erp_crm.frontend.login.Login;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ClientTypesApi {

    private ConnectionApi connection = Login.connection;
    private Gson gson = Converters.registerDateTime(new GsonBuilder()).create();


    public void getClientTypes(){
        List<ClientType> clientTypes = new ArrayList<>();
        clientTypes.addAll(
                Arrays.asList( gson.fromJson(connection.getObjects(ConnectionApi.ObjectType.CLIENT_TYPES), ClientType[].class) )
        );
        DBData.setClientTypes(clientTypes);
    }

    public Integer createClientType(ClientType clientType){
        return connection.createObject(clientType.serialize(), ConnectionApi.ObjectType.CLIENT_TYPES);
    }

    public void deleteClientType(ClientType item) {
        connection.deleteObject(item.getId(), ConnectionApi.ObjectType.CLIENT_TYPES);
    }
}
