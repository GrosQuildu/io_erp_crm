package main.java.erp.backend.api.erp;

import com.google.gson.*;
import main.java.erp.backend.api.ConnectionApi;
import main.java.erp.backend.model.DBData;
import main.java.erp.backend.model.erp.Order;
import main.java.erp.frontend.login.Login;

import java.lang.reflect.Type;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OrderControllerApi {

    private ConnectionApi connection = Login.connection;
    private Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new DateDeserializer()).create();

    class DateDeserializer implements JsonDeserializer<LocalDate> {

        @Override
        public LocalDate deserialize(JsonElement element, Type arg1, JsonDeserializationContext arg2) throws JsonParseException {
            String date = element.getAsString();

            SimpleDateFormat formatter = new SimpleDateFormat("YYYY-MM-dd");
            DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE;

            return FORMATTER.parse(date, LocalDate::from);
        }
    }


    public void getOrders(){
        List<Order> orders = new ArrayList<>();
        orders.addAll(
                Arrays.asList( gson.fromJson(connection.getObjects(ConnectionApi.ObjectType.ORDERS), Order[].class) )
        );
        DBData.setOrders(orders);
    }

    public Integer createOrder(Order order){
        return connection.createObject(order.serialize(), ConnectionApi.ObjectType.ORDERS);
    }

    public void deleteOrder(Order item) {
        connection.deleteObject(item.getId(), ConnectionApi.ObjectType.DELIVERY_COSTS);
    }

    public void updateOrder(Integer id, Order order) {
        connection.updateObject(id, gson.toJson(order), ConnectionApi.ObjectType.ORDERS);
    }
}
