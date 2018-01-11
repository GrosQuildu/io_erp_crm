package main.java.erp.backend.api.erp;

import com.google.gson.Gson;
import main.java.erp.backend.api.ConnectionApi;
import main.java.erp.backend.model.erp.DeliveryCost;
import main.java.erp.frontend.login.Login;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DeliveryCostControllerApi {
    private ConnectionApi connection = Login.connection;
    private Gson gson = new Gson();


    public List<DeliveryCost> getDeliveryCosts(){
        List<DeliveryCost> deliveryCosts = new ArrayList<>();
        deliveryCosts.addAll(
                Arrays.asList( gson.fromJson(connection.getObjects(ConnectionApi.ObjectType.DELIVERY_COSTS), DeliveryCost[].class) )
        );
        return deliveryCosts;
    }

    public Integer createDeliveryCost(DeliveryCost deliveryCost){
        System.out.println(deliveryCost.serialize());
        return connection.createObject(deliveryCost.serialize(), ConnectionApi.ObjectType.DELIVERY_COSTS);
    }

    public void deleteDeliveryCost(DeliveryCost item) {
        connection.deleteObject(item.getId(), ConnectionApi.ObjectType.DELIVERY_COSTS);
    }

    public void updateDeliveryCost(Integer id, DeliveryCost deliveryCost) {
        connection.updateObject(id, gson.toJson(deliveryCost), ConnectionApi.ObjectType.DELIVERY_COSTS);
    }
}
