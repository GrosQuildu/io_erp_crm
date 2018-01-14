package main.java.erp.backend.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.java.erp.backend.model.common.Client;
import main.java.erp.backend.model.common.ClientType;
import main.java.erp.backend.model.common.Employee;
import main.java.erp.backend.model.common.Unit;
import main.java.erp.backend.model.erp.*;

import java.util.List;

public class DBData {

    private static ObservableList<Client> clients = FXCollections.observableArrayList();
    private static ObservableList<ClientType> clientTypes = FXCollections.observableArrayList();
    private static ObservableList<Employee> employees = FXCollections.observableArrayList();
    private static ObservableList<Unit> units = FXCollections.observableArrayList();
    private static ObservableList<Article> articles = FXCollections.observableArrayList();
    private static ObservableList<DeliveryCost> deliveryCosts = FXCollections.observableArrayList();
    private static ObservableList<Order> orders = FXCollections.observableArrayList();
    private static ObservableList<OrderedArticle> orderedArticles = FXCollections.observableArrayList();
    private static ObservableList<Proforma> proformas = FXCollections.observableArrayList();

    public static ObservableList<Client> getClients() {
        return clients;
    }

    public static void setClients(List<Client> clients) {
        DBData.clients.setAll(clients);
    }

    public static ObservableList<ClientType> getClientTypes() {
        return clientTypes;
    }

    public static void setClientTypes(List<ClientType> clientTypes) {
        DBData.clientTypes.setAll(clientTypes);
    }

    public static ObservableList<Employee> getEmployees() {
        return employees;
    }

    public static void setEmployees(List<Employee> employees) {
        DBData.employees.setAll(employees);
    }

    public static ObservableList<Unit> getUnits() {
        return units;
    }

    public static void setUnits(List<Unit> units) {
        DBData.units.setAll(units);
    }

    public static ObservableList<Article> getArticles() {
        return articles;
    }

    public static void setArticles(List<Article> articles) {
        DBData.articles.setAll(articles);
    }

    public static ObservableList<DeliveryCost> getDeliveryCosts() {
        return deliveryCosts;
    }

    public static void setDeliveryCosts(List<DeliveryCost> deliveryCosts) {
        DBData.deliveryCosts.setAll(deliveryCosts);
    }

    public static ObservableList<Order> getOrders() {
        return orders;
    }

    public static void setOrders(List<Order> orders) {
        DBData.orders.setAll(orders);
    }

    public static ObservableList<OrderedArticle> getOrderedArticles() {
        return orderedArticles;
    }

    public static void setOrderedArticles(List<OrderedArticle> orderedArticles) {
        DBData.orderedArticles.setAll(orderedArticles);
    }

    public static ObservableList<Proforma> getProformas() {
        return proformas;
    }

    public static void setProformas(List<Proforma> proformas) {
        DBData.proformas.setAll(proformas);
    }
}
