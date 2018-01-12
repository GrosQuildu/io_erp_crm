package main.java.erp.backend.api.erp;

import com.google.gson.Gson;
import main.java.erp.backend.api.ConnectionApi;
import main.java.erp.backend.model.common.ClientType;
import main.java.erp.backend.model.common.Unit;
import main.java.erp.frontend.login.Login;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UnitControllerApi {

    private ConnectionApi connection = Login.connection;
    private Gson gson = new Gson();


    public List<Unit> getUnits(){
        List<Unit> units = new ArrayList<>();
        units.addAll(
                Arrays.asList( gson.fromJson(connection.getObjects(ConnectionApi.ObjectType.UNITS), Unit[].class) )
        );
        return units;
    }

    public Integer createUnit(Unit unit){
        return connection.createObject(unit.serialize(), ConnectionApi.ObjectType.UNITS);
    }
}
