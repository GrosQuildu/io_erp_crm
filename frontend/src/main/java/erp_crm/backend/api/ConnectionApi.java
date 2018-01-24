package main.java.erp_crm.backend.api;

import com.google.gson.Gson;
import main.java.erp_crm.backend.SharedData;
import main.java.erp_crm.frontend.alert.AlertWindow;
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


    public enum ObjectType {ARTICLES, DELIVERY_COSTS, ORDERS, CLIENT_TYPES, UNITS, CONTACTS, TASKS, TASK_COMMENTS, TASK_COMMENTS_SECOND, TASK_NOTES, TASK_NOTES_SECOND, TASK_STATUSES, MEETINGS, MEETING_NOTES, MEETING_NOTES_SECOND, EMPLOYEES, PROFORMAS, CONTACT_GROUPS, CLIENTS}
    private String token = "";
    private String clientId = "javafx-client";
    private String clientSecret = "R3GMPX2DQHD1234VFG929ABW";
    private String username;
    private String password;


    private HashMap<ObjectType, String> urlMap = new HashMap<>();
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
        urlMap.put(ObjectType.CONTACTS, "/crm/contacts");
        urlMap.put(ObjectType.TASKS, "/crm/tasks");
        urlMap.put(ObjectType.TASK_COMMENTS, "/crm/tasks");
        urlMap.put(ObjectType.TASK_COMMENTS_SECOND, "/comments");
        urlMap.put(ObjectType.TASK_NOTES, "/crm/tasks");
        urlMap.put(ObjectType.TASK_NOTES_SECOND, "/notes");
        urlMap.put(ObjectType.TASK_STATUSES, "/crm/taskStatuses");
        urlMap.put(ObjectType.MEETINGS, "/crm/meetings");
        urlMap.put(ObjectType.MEETING_NOTES, "/crm/meetings");
        urlMap.put(ObjectType.MEETING_NOTES_SECOND, "/notes");
        urlMap.put(ObjectType.EMPLOYEES, "/employees");
        urlMap.put(ObjectType.PROFORMAS, "/erp/proformas");
        urlMap.put(ObjectType.CONTACT_GROUPS, "/crm/contactGroups");
    }

    //CREATE
    public int createObject(String object, ObjectType objectType){
        String url = getUrl(objectType);
        return createObject(object, url);
    }

    public int createObject(String object, ObjectType objectType, int parentId, ObjectType second){
        String url = getUrl(objectType) + "/" + parentId + getUrl(second);
        return createObject(object, url);
    }

    //READ
    public String getObjects(ObjectType objectType) {
        String url = getUrl(objectType);
        return getObjects(url);
    }

    public String getObjects(ObjectType objectType, int parentId, ObjectType second) {
        String url = getUrl(objectType) + getUrl(second) + "/" + parentId;
        return getObjects(url);
    }

    //UPDATE
    public void updateObject(Integer id, String object, ObjectType objectType) {
        String url = getUrl(objectType) + "/" + id;
        updateObject(object, url);
    }
    public void updateObject(Integer id, String object, ObjectType objectType, int objectId, ObjectType second) {
        String url = getUrl(objectType)+"/"+id + getUrl(second) + "/" + objectId;
        updateObject(object, url);
    }

    //DELETE
    public void deleteObject(Integer id, ObjectType objectType) {
        String url = getUrl(objectType)+"/"+id.toString();
        deleteObject(url);
    }
    public void deleteObject(Integer parentId, ObjectType objectType, int objectId, ObjectType second) {
        String url = getUrl(objectType)+"/"+parentId + getUrl(second) + "/" + objectId;
        deleteObject(url);
    }







    //PRIVATE METHODS

    private String receiveToken() {
        String authUrl = "http://"+clientId+":"+clientSecret+"@"+ SharedData.getConfig().getAddress()+":"+SharedData.getConfig().getPort()+"/api/oauth/token";
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

            return responseMap.get("access_token") == null ? "" : responseMap.get("access_token");

        } catch (Exception e) {
            AlertWindow.show(e.getMessage());
            e.printStackTrace();
        }
        return "";
    }
    public String getToken() {
        return token;
    }

    private int createObject(String object, String url) {
        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
            HttpPost request = new HttpPost(url);


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
            AlertWindow.show(e.getMessage());
            e.printStackTrace();
        }
        return -1;
    }


    private String getObjects(String url) {
        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
            HttpGet request = new HttpGet(url);

            request.setHeader("Content-type", "application/json");
            request.setHeader("Authorization", "Bearer " + token);

            HttpResponse result = httpClient.execute(request);
            String body = EntityUtils.toString(result.getEntity(), "UTF-8");

            System.out.println("-----------------------------------------------------");
            System.out.println(body);
            System.out.println("-----------------------------------------------------");
            return body;

        } catch (Exception e) {
            AlertWindow.show(e.getMessage());
            e.printStackTrace();
        }
        return "[]";
    }




    private void updateObject(String object, String url) {
        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
            HttpPut request = new HttpPut(url);


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
            AlertWindow.show(e.getMessage());
            e.printStackTrace();
        }
    }



    private void deleteObject(String url) {
        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
            HttpDelete request = new HttpDelete(url);

            request.setHeader("Content-type", "application/json");
            request.setHeader("Authorization", "Bearer " + token);

            HttpResponse result = httpClient.execute(request);
            String body = EntityUtils.toString(result.getEntity(), "UTF-8");


            System.out.println("-----------------------------------------------------");
            System.out.println(body);
            System.out.println("-----------------------------------------------------");


        } catch (Exception e) {
            AlertWindow.show(e.getMessage());
            e.printStackTrace();
        }
    }

    private String getUrl(ObjectType objectType){
        return "http://" + SharedData.getConfig().getAddress() + ":" + SharedData.getConfig().getPort() + "/api" + urlMap.get(objectType);
    }

}
