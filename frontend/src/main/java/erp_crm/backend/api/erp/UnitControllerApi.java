package main.java.erp_crm.backend.api.erp;

import com.fatboyindustrial.gsonjodatime.Converters;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import main.java.erp_crm.backend.api.ConnectionApi;
import main.java.erp_crm.backend.model.DBData;
import main.java.erp_crm.backend.model.common.Unit;
import main.java.erp_crm.frontend.login.Login;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UnitControllerApi {

    private ConnectionApi connection = Login.connection;
    private Gson gson = Converters.registerLocalDate(new GsonBuilder()).create();


    public void getUnits(){
        List<Unit> units = new ArrayList<>();
        units.addAll(
                Arrays.asList( gson.fromJson(connection.getObjects(ConnectionApi.ObjectType.UNITS), Unit[].class) )
        );
        DBData.setUnits(units);
    }

    public Integer createUnit(Unit unit){
        return connection.createObject(unit.serialize(), ConnectionApi.ObjectType.UNITS);
    }

    public void deleteUnit(Unit item) {
        connection.deleteObject(item.getId(), ConnectionApi.ObjectType.UNITS);
    }
}
