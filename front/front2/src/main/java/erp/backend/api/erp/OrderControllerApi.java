package main.java.erp.backend.api.erp;

import com.google.gson.Gson;
import main.java.erp.backend.api.ConnectionApi;
import main.java.erp.backend.model.erp.Order;
import main.java.erp.frontend.login.Login;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OrderControllerApi {

    private ConnectionApi connection = Login.connection;
    private Gson gson = new Gson();


    public List<Order> getOrders(){
        List<Order> deliveryCosts = new ArrayList<>();
        deliveryCosts.addAll(
                Arrays.asList( gson.fromJson(connection.getObjects(ConnectionApi.ObjectType.ORDERS), Order[].class) )
        );
        return deliveryCosts;
    }

    public Integer createOrder(Order order){
        System.out.println(order.serialize());
        return connection.createObject(order.serialize(), ConnectionApi.ObjectType.ORDERS);
    }

    public void deleteOrder(Order item) {
        connection.deleteObject(item.getId(), ConnectionApi.ObjectType.DELIVERY_COSTS);
    }

    public void updateOrder(Integer id, Order order) {
        connection.updateObject(id, gson.toJson(order), ConnectionApi.ObjectType.ORDERS);
    }
}
