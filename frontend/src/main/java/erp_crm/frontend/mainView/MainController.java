package main.java.erp_crm.frontend.mainView;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import main.java.erp_crm.backend.model.DBData;
import main.java.erp_crm.backend.model.common.Employee;
import main.java.erp_crm.frontend.statistics.StatisticsView;

import java.net.URL;
import java.util.ResourceBundle;


public class MainController implements Initializable{
    public static TabPane settingsBox;
    public static VBox clientsBox;
    public static VBox deliveryBox;
    public static VBox articlesBox;
    public static VBox ordersBox;
    public static VBox proformasBox;
    public static VBox meetingsBox;
    public static VBox tasksBox;
    public static VBox contactsBox;


    public BorderPane root;
    public VBox sidebar;
    public VBox rightSidebar;
    public Button clientsBtn;
    public Button ordersBtn;
    public Button proformasBtn;
    public Button articlesBtn;
    public Button reportsBtn;
    public Button deliveryCostsBtn;
    public Button settingsBtn;
    public Button meetingsBtn;
    public Button contactsBtn;
    public Button tasksBtn;
    private GridPane statisticsBox;
    private ActiveScene active;
    private enum ActiveScene {
        Clients, Orders, Proformas, Articles, Reports, DeliveryCosts, Meetings, Settings, Tasks, Contacts
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setViewAccordingToRole();
        StatisticsView statisticsView = new StatisticsView();
        statisticsBox = statisticsView.getBox();
        updateView();
        setEvents();
    }

    private void setViewAccordingToRole() {
        Employee user = DBData.getLoggedUser();
        if(Employee.Role.ERP.equalsTo(user.getRole())){
            sidebar.getChildren().removeAll(meetingsBtn, tasksBtn);
            active = ActiveScene.Clients;
        } else if (Employee.Role.CRM.equalsTo(user.getRole())){
            sidebar.getChildren().removeAll(ordersBtn, proformasBtn, articlesBtn, reportsBtn, deliveryCostsBtn);
            active = ActiveScene.Tasks;
        } else {
            active = ActiveScene.Clients;
        }
    }

    private void setEvents() {
        ordersBtn.setOnAction(e -> {
            root.setCenter(ordersBox);
            active = ActiveScene.Orders;
            updateView();
        });
        clientsBtn.setOnAction(e -> {
                    root.setCenter(clientsBox);
                    active = ActiveScene.Clients;
                    updateView();
                }
        );
        contactsBtn.setOnAction(e -> {
                    root.setCenter(contactsBox);
                    active = ActiveScene.Contacts;
                    updateView();
                }
        );
        articlesBtn.setOnAction(e -> {
            root.setCenter(articlesBox);
            active = ActiveScene.Articles;
            updateView();
        });
        settingsBtn.setOnAction(e -> {
            root.setCenter(settingsBox);
            active = ActiveScene.Settings;
            updateView();
        });
        deliveryCostsBtn.setOnAction(e -> {
            root.setCenter(deliveryBox);
            active = ActiveScene.DeliveryCosts;
            updateView();
        });
        proformasBtn.setOnAction(e -> {
            root.setCenter(proformasBox);
            active = ActiveScene.Proformas;
            updateView();
        });
        reportsBtn.setOnAction(e -> {
            root.setCenter(statisticsBox);
            active = ActiveScene.Reports;
            updateView();
        });
        meetingsBtn.setOnAction(e -> {
            root.setCenter(meetingsBox);
            active = ActiveScene.Meetings;
            updateView();
        });
        tasksBtn.setOnAction(e -> {
            root.setCenter(tasksBox);
            active = ActiveScene.Tasks;
            updateView();
        });

    }

    private void updateView() {
        removeActiveClass();
        setActiveClass();
    }

    private void setActiveClass() {
        switch(active){
            case Clients:
                clientsBtn.getStyleClass().add("active");
                break;
            case Orders:
                ordersBtn.getStyleClass().add("active");
                break;
            case Proformas:
                proformasBtn.getStyleClass().add("active");
                break;
            case Articles:
                articlesBtn.getStyleClass().add("active");
                break;
            case Reports:
                reportsBtn.getStyleClass().add("active");
                break;
            case DeliveryCosts:
                deliveryCostsBtn.getStyleClass().add("active");
                break;
            case Settings:
                settingsBtn.getStyleClass().add("active");
                break;
            case Meetings:
                meetingsBtn.getStyleClass().add("active");
                break;
            case Tasks:
                tasksBtn.getStyleClass().add("active");
                break;
            case Contacts:
                contactsBtn.getStyleClass().add("active");
                break;
        }
    }

    private void removeActiveClass() {
        clientsBtn.getStyleClass().remove("active");
        ordersBtn.getStyleClass().remove("active");
        proformasBtn.getStyleClass().remove("active");
        articlesBtn.getStyleClass().remove("active");
        reportsBtn.getStyleClass().remove("active");
        deliveryCostsBtn.getStyleClass().remove("active");
        settingsBtn.getStyleClass().remove("active");
        meetingsBtn.getStyleClass().remove("active");
        tasksBtn.getStyleClass().remove("active");
        contactsBtn.getStyleClass().remove("active");
    }
}
