package main.java.erp_crm.backend.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.java.erp_crm.backend.model.common.Client;
import main.java.erp_crm.backend.model.common.ClientType;
import main.java.erp_crm.backend.model.common.Employee;
import main.java.erp_crm.backend.model.common.Unit;
import main.java.erp_crm.backend.model.crm.*;
import main.java.erp_crm.backend.model.erp.*;

import java.util.List;

public class DBData {

    //COMMON
    private static ObservableList<Client> clients = FXCollections.observableArrayList();
    private static ObservableList<ClientType> clientTypes = FXCollections.observableArrayList();
    private static ObservableList<main.java.erp_crm.backend.model.crm.Contact> contacts = FXCollections.observableArrayList();
    private static ObservableList<ContactGroup> contactGroups = FXCollections.observableArrayList();
    private static ObservableList<Employee> employees = FXCollections.observableArrayList();
    private static ObservableList<Unit> units = FXCollections.observableArrayList();
    
    //ERP
    private static ObservableList<Article> articles = FXCollections.observableArrayList();
    private static ObservableList<DeliveryCost> deliveryCosts = FXCollections.observableArrayList();
    private static ObservableList<Order> orders = FXCollections.observableArrayList();
    private static ObservableList<OrderedArticle> orderedArticles = FXCollections.observableArrayList();
    private static ObservableList<Proforma> proformas = FXCollections.observableArrayList();
    
    
    //CRM
    private static ObservableList<Meeting> meetings = FXCollections.observableArrayList();
    private static ObservableList<MeetingContact> meetingContacts = FXCollections.observableArrayList();
    private static ObservableList<MeetingEmployee> meetingEmployees = FXCollections.observableArrayList();
    private static ObservableList<MeetingNote> meetingNotes = FXCollections.observableArrayList();
    private static ObservableList<Task> tasks = FXCollections.observableArrayList();
    private static ObservableList<TaskComment> taskComments = FXCollections.observableArrayList();
    private static ObservableList<TaskContact> taskContacts = FXCollections.observableArrayList();
    private static ObservableList<TaskNote> taskNotes = FXCollections.observableArrayList();
    private static ObservableList<TaskStatus> taskStatuses = FXCollections.observableArrayList();
    private static Employee loggedUser;


    //Getters & setters

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

    public static ObservableList<main.java.erp_crm.backend.model.crm.Contact> getContacts() {
        return contacts;
    }

    public static void setContacts(List<main.java.erp_crm.backend.model.crm.Contact> contacts) {
        DBData.contacts.setAll(contacts);
    }

    public static ObservableList<ContactGroup> getContactGroups() {
        return contactGroups;
    }

    public static void setContactGroups(List<ContactGroup> contactGroups) {
        DBData.contactGroups.setAll(contactGroups);
    }

    public static ObservableList<Meeting> getMeetings() {
        return meetings;
    }

    public static void setMeetings(List<Meeting> meetings) {
        DBData.meetings.setAll(meetings);
    }

    public static ObservableList<MeetingContact> getMeetingContacts() {
        return meetingContacts;
    }

    public static void setMeetingContacts(List<MeetingContact> meetingContacts) {
        DBData.meetingContacts.setAll(meetingContacts);
    }

    public static ObservableList<MeetingEmployee> getMeetingEmployees() {
        return meetingEmployees;
    }

    public static void setMeetingEmployees(List<MeetingEmployee> meetingEmployees) {
        DBData.meetingEmployees.setAll(meetingEmployees);
    }

    public static ObservableList<MeetingNote> getMeetingNotes() {
        return meetingNotes;
    }

    public static void setMeetingNotes(List<MeetingNote> meetingNotes) {
        DBData.meetingNotes.setAll(meetingNotes);
    }

    public static ObservableList<Task> getTasks() {
        return tasks;
    }

    public static void setTasks(List<Task> tasks) {
        DBData.tasks.setAll(tasks);
    }

    public static ObservableList<TaskComment> getTaskComments() {
        return taskComments;
    }

    public static void setTaskComments(List<TaskComment> taskComments) {
        DBData.taskComments.setAll(taskComments);
    }

    public static ObservableList<TaskContact> getTaskContacts() {
        return taskContacts;
    }

    public static void setTaskContacts(List<TaskContact> taskContacts) {
        DBData.taskContacts.setAll(taskContacts);
    }

    public static ObservableList<TaskNote> getTaskNotes() {
        return taskNotes;
    }

    public static void setTaskNotes(List<TaskNote> taskNotes) {
        DBData.taskNotes.setAll(taskNotes);
    }

    public static ObservableList<TaskStatus> getTaskStatuses() {
        return taskStatuses;
    }

    public static void setTaskStatuses(List<TaskStatus> taskStatuses) {
        DBData.taskStatuses.setAll(taskStatuses);
    }

    public static Employee getLoggedUser() {
        return loggedUser;
    }

    public static void setLoggedUser(Employee loggedUser) {
        DBData.loggedUser = loggedUser;
    }

    public static Proforma getProforma(String orderNumber) {
        for(Proforma p : proformas){
            if(p.getOrder().getOrderNumber().equals(orderNumber))
                return p;
        }
        return null;
    }

    public static TaskStatus getTaskStatus(String string) {
        for(TaskStatus status : taskStatuses){
            if(string.equals(status.getDescription())) return status;
        }
        return null;
    }

    public static Employee getEmployee(String string) {
        for(Employee employee : employees){
            if(string.equals(employee.getName())) return employee;
        }
        return null;
    }

    public static ContactGroup getContactGroup(String string) {
        for(ContactGroup contactGroup : contactGroups){
            if(string.equals(contactGroup.getDescription())) return contactGroup;
        }
        return null;
    }
}
