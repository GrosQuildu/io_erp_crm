package main.java.erp.backend.api.common;

import com.google.gson.Gson;
import main.java.erp.backend.api.ConnectionApi;
import main.java.erp.backend.model.common.ClientType;
import main.java.erp.frontend.login.Login;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ClientTypesApi {

    private ConnectionApi connection = Login.connection;
    private Gson gson = new Gson();


    public List<ClientType> getClientTypes(){
        List<ClientType> clientTypes = new ArrayList<>();
        clientTypes.addAll(
                Arrays.asList( gson.fromJson(connection.getObjects(ConnectionApi.ObjectType.CLIENT_TYPES), ClientType[].class) )
        );
        return clientTypes;
    }

    public Integer createClientType(ClientType clientType){
        return connection.createObject(clientType.serialize(), ConnectionApi.ObjectType.CLIENT_TYPES);
    }
}
