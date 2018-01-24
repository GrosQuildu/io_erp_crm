package main.java.erp_crm.backend.api.crm;

import com.fatboyindustrial.gsonjodatime.Converters;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import main.java.erp_crm.backend.api.ConnectionApi;
import main.java.erp_crm.backend.model.DBData;
import main.java.erp_crm.backend.model.crm.Contact;
import main.java.erp_crm.frontend.login.Login;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ContactsControllerApi {

    private ConnectionApi connection = Login.connection;
    private Gson gson = Converters.registerDateTime(new GsonBuilder()).create();



    public Integer createContact(Contact contact){
        return connection.createObject(contact.serialize(), ConnectionApi.ObjectType.CONTACTS);
    }

    public void deleteContact(Contact item) {
        connection.deleteObject(item.getId(), ConnectionApi.ObjectType.CONTACTS);
    }

    public void getContacts() {
        List<Contact> contacts = new ArrayList<>();
        contacts.addAll(
                Arrays.asList( gson.fromJson(connection.getObjects(ConnectionApi.ObjectType.CONTACTS), Contact[].class) )
        );
        DBData.setContacts(contacts);
    }

    public void updateContact(Contact contact) {
        connection.updateObject(contact.getId(), gson.toJson(contact), ConnectionApi.ObjectType.CONTACTS);
    }
}
