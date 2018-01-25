package main.java.erp_crm.backend.api.common;

import com.fatboyindustrial.gsonjodatime.Converters;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import main.java.erp_crm.backend.api.ConnectionApi;
import main.java.erp_crm.backend.model.DBData;
import main.java.erp_crm.backend.model.common.Employee;
import main.java.erp_crm.backend.model.common.Employee.Role;
import main.java.erp_crm.backend.model.crm.ContactGroup;
import main.java.erp_crm.frontend.login.Login;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class ContactGroupApi {
    private final static List<Role> roles = new LinkedList<>(Arrays.asList(Role.ADMIN, Role.ERP, Role.CRM));

    private ConnectionApi connection = Login.connection;
    private Gson gson = Converters.registerLocalDate(new GsonBuilder()).create();
    private boolean canSend = false;


    public ContactGroupApi(){
        canSend = roles.contains(DBData.getLoggedUser().getRole());
    }
    public void getContactGroups(){

        if(canSend){
            List<ContactGroup> clientTypes = new ArrayList<>();
            clientTypes.addAll(
                    Arrays.asList( gson.fromJson(connection.getObjects(ConnectionApi.ObjectType.CONTACT_GROUPS), ContactGroup[].class) )
            );
            DBData.setContactGroups(clientTypes);
        }

    }

    public Integer createContactGroup(ContactGroup clientType){
        return canSend ? connection.createObject(clientType.serialize(), ConnectionApi.ObjectType.CONTACT_GROUPS) : -1;
    }

    public void deleteContactGroup(ContactGroup item) {

        if(canSend){
            connection.deleteObject(item.getId(), ConnectionApi.ObjectType.CONTACT_GROUPS);
        }

    }
}
