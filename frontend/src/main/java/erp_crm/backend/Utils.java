package main.java.erp_crm.backend;

import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;


public class Utils {
    private static HashMap<String, String> utf8 = new HashMap<>();
    public static String normalizeString(String s){
        utf8.put("ą", "a");
        utf8.put("ć", "c");
        utf8.put("ę", "e");
        utf8.put("ł", "l");
        utf8.put("ń", "n");
        utf8.put("ó", "o");
        utf8.put("ś", "s");
        utf8.put("ź", "z");
        utf8.put("ż", "z");
        utf8.put("Ą", "A");
        utf8.put("Ć", "C");
        utf8.put("Ę", "E");
        utf8.put("Ł", "L");
        utf8.put("Ń", "N");
        utf8.put("Ó", "O");
        utf8.put("Ś", "S");
        utf8.put("Ź", "Z");
        utf8.put("Ż", "Z");
        utf8.put("&", " and ");
        for (Object o : utf8.entrySet()) {
            Map.Entry pair = (Map.Entry) o;
            s = s.replace((String) pair.getKey(), (String) pair.getValue());
        }
        return s;
    }


    public static String formatPln(Float liczba){
        DecimalFormat format = new DecimalFormat("###,###,###.## zł");
        return format.format(liczba);
    }
    public static String formatKg(Float liczba){
        DecimalFormat format = new DecimalFormat("###,###,###.## kg");
        return format.format(liczba);
    }


    public static String color2rgb(Color color) {
        int red = (int)Math.round(color.getRed()*255);
        int green = (int) Math.round(color.getGreen()*255);
        int blue = (int) Math.round(color.getBlue()*255);
        return "rgb(" + red +","+green+","+blue+")";
    }

}
