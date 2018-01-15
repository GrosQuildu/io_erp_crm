package main.java.erp.frontend.orders;

import main.java.erp.backend.SharedData;
import main.java.erp.backend.model.DBData;
import main.java.erp.backend.model.erp.Order;
import main.java.erp.backend.model.erp.Proforma;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Locale;

public class OrderUtils {

    public enum DefaultText {CONDITIONS,COMMENTS,MAIL_CONTENT}
    public static String generateOrderNumber(){
        String pattern = "yyyyMMdd";
        StringBuilder sb = new StringBuilder();
        SimpleDateFormat formatter = new SimpleDateFormat(pattern, Locale.getDefault());
        String numer = formatter.format(Date.valueOf(LocalDate.now()));
        sb.append(numer);
        sb.append("/");
        String prev = sb.toString();
        sb.append("1");
        String test = sb.toString();
        int count=1;
        while(isOrderNumber(test)){ //dopóki jest takie zamowienie w bazie
            sb.delete(0,sb.length());
            sb.append(prev);
            sb.append(count);
            count++;
            test=sb.toString();
        }
        return sb.toString();
    }

    public static boolean isOrderNumber(String orderNumber){
        for(Order i : DBData.getOrders()){
            if(i.getOrderNumber().equals(orderNumber)) return true;
        }
        return false;
    }
    public static String generateProformaNumber(String order){
        Proforma p = new Proforma(); //pobranie proformy wg numeru zamówienia
        if(p!=null && p.getProformaNumber()!=null) {
            if (!p.getProformaNumber().isEmpty()) {
                return p.getProformaNumber();
            }
        }
        String pattern = "yy";
        StringBuilder sb = new StringBuilder();
        SimpleDateFormat formatter = new SimpleDateFormat(pattern, Locale.getDefault());
        String number = formatter.format(Date.valueOf(LocalDate.now()));
        number=number+" PF";
        int count=1;
        sb.append(count).append("/").append(number);
        String test = sb.toString();
        int dupa=3;
        while(dupa-->0){ //to, co wyzej - mozna oddelegowac do jednej funkcji
            sb.delete(0,sb.length());
            sb.append(count);
            sb.append("/");
            sb.append(number);
            count++;
            test=sb.toString();
        }
        return sb.toString();
    }
}
