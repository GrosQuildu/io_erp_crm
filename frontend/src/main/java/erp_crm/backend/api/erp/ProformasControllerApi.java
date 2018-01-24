package main.java.erp_crm.backend.api.erp;

import com.fatboyindustrial.gsonjodatime.Converters;
import com.google.gson.*;
import main.java.erp_crm.backend.api.ConnectionApi;
import main.java.erp_crm.backend.model.DBData;
import main.java.erp_crm.backend.model.erp.Proforma;
import main.java.erp_crm.frontend.login.Login;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProformasControllerApi {

    private ConnectionApi connection = Login.connection;
    private Gson gson = Converters.registerLocalDate(new GsonBuilder()).create();



    public void getProformas(){
        List<Proforma> proformas = new ArrayList<>();
        proformas.addAll(
                Arrays.asList( gson.fromJson(connection.getObjects(ConnectionApi.ObjectType.PROFORMAS), Proforma[].class) )
        );
        DBData.setProformas(proformas);
    }

    public Integer createProforma(Proforma proforma){
        return connection.createObject(proforma.serialize(), ConnectionApi.ObjectType.PROFORMAS);
    }

    public void deleteProforma(Proforma item) {
        connection.deleteObject(item.getId(), ConnectionApi.ObjectType.PROFORMAS);
    }

    public void updateProforma(Integer id, Proforma proforma) {
        connection.updateObject(id, gson.toJson(proforma), ConnectionApi.ObjectType.PROFORMAS);
    }
}
