package main.java.erp.frontend.mainView;

import main.java.erp.frontend.statistics.StatisticsView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

/*Kontroler do głównego pojemnika*/
public class MainController implements Initializable{
    public static TabPane settingsBox;
    public static VBox clientsBox;
    public static VBox deliveryBox;
    public static VBox articlesBox;
    public static VBox ordersBox;
    public static VBox proformasBox;
    public VBox sidebar;
    public VBox rightSidebar;
    private GridPane zestawieniaBox;
    @FXML
    private BorderPane root;
    private enum ActiveScene {
        Clients, Orders, Proformas, Articles, Reports, DeliveryCosts, Settings
    }
    private ActiveScene active;
    @FXML
    private Button clientsBtn, ordersBtn, proformasBtn, articlesBtn, reportsBtn, deliveryCostsBtn, settingsBtn;
    private TextField metersField;
    private TextField yardsField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        StatisticsView statisticsView = new StatisticsView();
        zestawieniaBox= statisticsView.getBox();
        //ustawienie pierwszego widoku na klientów
        active = ActiveScene.Clients;
        updateView();
        setEvents();
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
            root.setCenter(zestawieniaBox);
            active = ActiveScene.Reports;
            updateView();
        });

    }

    //Ustawianie klas dla przycisków w menu (podświetlanie dla aktywnych widoków)

    private void updateView() {
        clientsBtn.getStyleClass().remove("active");
        ordersBtn.getStyleClass().remove("active");
        proformasBtn.getStyleClass().remove("active");
        articlesBtn.getStyleClass().remove("active");
        reportsBtn.getStyleClass().remove("active");
        deliveryCostsBtn.getStyleClass().remove("active");
        settingsBtn.getStyleClass().remove("active");
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
        }
    }
}
