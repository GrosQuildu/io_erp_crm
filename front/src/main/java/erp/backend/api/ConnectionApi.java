package main.java.erp.backend.api;

import com.google.gson.Gson;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ConnectionApi {


    public enum ObjectType {ARTICLES, DELIVERY_COSTS, ORDERS, CLIENT_TYPES, UNITS, CLIENTS}
    private String token = "";
    private String clientId = "javafx-client";
    private String clientSecret = "R3GMPX2DQHD1234VFG929ABW";
    private String username;
    private String password;


    private HashMap<ObjectType, String> urlMap = new HashMap<>();
    private Config config = new Config(true);
    public ConnectionApi(String username, String password){

        this.username = username;
        this.password = password;
        token = receiveToken();
        urlMap.put(ObjectType.ARTICLES, "/erp/articles");
        urlMap.put(ObjectType.CLIENTS, "/clients");
        urlMap.put(ObjectType.CLIENT_TYPES, "/clientTypes");
        urlMap.put(ObjectType.DELIVERY_COSTS, "/erp/deliveryCosts");
        urlMap.put(ObjectType.ORDERS, "/erp/orders");
        urlMap.put(ObjectType.UNITS, "/units");
    }

    private String receiveToken() {
        String authUrl = "http://"+clientId+":"+clientSecret+"@"+config.getAddress()+":"+config.getPort()+"/api/oauth/token";
        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
            HttpPost request = new HttpPost(authUrl);

            ArrayList<NameValuePair> postParameters = new ArrayList<>();
            postParameters.add(new BasicNameValuePair("grant_type", "password"));
            postParameters.add(new BasicNameValuePair("username", username));
            postParameters.add(new BasicNameValuePair("password", password));

            request.setEntity(new UrlEncodedFormEntity(postParameters, "UTF-8"));

            HttpResponse result = httpClient.execute(request);

            String json = EntityUtils.toString(result.getEntity(), "UTF-8");

            Gson gson = new Gson();
            Map<String,String> responseMap = new HashMap<>();
            responseMap = (Map<String,String>) gson.fromJson(json, responseMap.getClass());

            System.out.println("-----------------------------------------------------");
            System.out.println(responseMap.get("access_token"));
            System.out.println("-----------------------------------------------------");

            return responseMap.get("access_token");

        } catch (Exception e) {
            e.printStackTrace();;
        }
        return "";
    }
    public String getToken() {
        return token;
    }

    public String getUrl(ObjectType objectType){
        return "http://" + config.getAddress() + ":" + config.getPort() + "/api" + urlMap.get(objectType);
    }

    public int createObject(String object, ObjectType objectType){
        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
            HttpPost request = new HttpPost(getUrl(objectType));

            Gson gson = new Gson();
            StringEntity jsonObject = new StringEntity(object);
            request.setEntity(jsonObject);
            request.setHeader("Content-type", "application/json");
            request.setHeader("Authorization", "Bearer " + token);

            HttpResponse result = httpClient.execute(request);
            String body = EntityUtils.toString(result.getEntity(), "UTF-8");


            System.out.println("-----------------------------------------------------");
            System.out.println(body);
            System.out.println("-----------------------------------------------------");

            return Integer.parseInt(body);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    public String getObjects(ObjectType objectType) {
        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
            HttpGet request = new HttpGet(getUrl(objectType));

            request.setHeader("Content-type", "application/json");
            request.setHeader("Authorization", "Bearer " + token);

            HttpResponse result = httpClient.execute(request);
            String body = EntityUtils.toString(result.getEntity(), "UTF-8");

            System.out.println("-----------------------------------------------------");
            System.out.println(body);
            System.out.println("-----------------------------------------------------");
            return body;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "[]";
    }


    public void deleteObject(Integer id, ObjectType objectType) {
        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
            HttpDelete request = new HttpDelete(getUrl(objectType)+"/"+id.toString());

            request.setHeader("Content-type", "application/json");
            request.setHeader("Authorization", "Bearer " + token);

            HttpResponse result = httpClient.execute(request);
            String body = EntityUtils.toString(result.getEntity(), "UTF-8");


            System.out.println("-----------------------------------------------------");
            System.out.println(body);
            System.out.println("-----------------------------------------------------");


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void updateObject(Integer id, String object, ObjectType objectType) {
        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
            HttpPut request = new HttpPut(getUrl(objectType)+"/"+id);

            Gson gson = new Gson();
            StringEntity jsonObject = new StringEntity(object);
            request.setEntity(jsonObject);
            request.setHeader("Content-type", "application/json");
            request.setHeader("Authorization", "Bearer " + token);

            HttpResponse result = httpClient.execute(request);
            String body = EntityUtils.toString(result.getEntity(), "UTF-8");


            System.out.println("-----------------------------------------------------");
            System.out.println(body);
            System.out.println("-----------------------------------------------------");


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
