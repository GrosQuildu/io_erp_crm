package main.java.erp_crm.backend.api.common;

import com.fatboyindustrial.gsonjodatime.Converters;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import main.java.erp_crm.backend.api.ConnectionApi;
import main.java.erp_crm.backend.model.DBData;
import main.java.erp_crm.backend.model.crm.ContactGroup;
import main.java.erp_crm.frontend.login.Login;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ContactGroupApi {

    private ConnectionApi connection = Login.connection;
    private Gson gson = Converters.registerDateTime(new GsonBuilder()).create();


    public void getContactGroups(){
        List<ContactGroup> clientTypes = new ArrayList<>();
        clientTypes.addAll(
                Arrays.asList( gson.fromJson(connection.getObjects(ConnectionApi.ObjectType.CONTACT_GROUPS), ContactGroup[].class) )
        );
        DBData.setContactGroups(clientTypes);
    }

    public Integer createContactGroup(ContactGroup clientType){
        return connection.createObject(clientType.serialize(), ConnectionApi.ObjectType.CONTACT_GROUPS);
    }

    public void deleteContactGroup(ContactGroup item) {
        connection.deleteObject(item.getId(), ConnectionApi.ObjectType.CONTACT_GROUPS);
    }
}
