package main.java.erp.frontend.orders;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import main.java.erp.Main;
import main.java.erp.backend.Utils;
import main.java.erp.backend.model.common.Client;
import main.java.erp.backend.model.erp.Order;
import main.java.erp.backend.model.erp.OrderedArticle;

import java.net.URL;
import java.util.ResourceBundle;

public class OrderPreviewController implements Initializable {
    private Stage stage = new Stage();
    public VBox mainBox;
    public WebView previewView;
    public Button closeBtn;
    private OrdersController ordersController;


    private void setEvents() {
        closeBtn.setOnAction(e -> stage.close());
    }

    public WebView getPreviewView() {
        return previewView;
    }


    public void show(Order order){

        previewView.getEngine().loadContent(
                createOrderPreviewString(order)
        );
        stage.show();
    }



    private String createOrderPreviewString(Order order) {
        if(order!=null){
            ObservableList<OrderedArticle> dane = FXCollections.observableArrayList();
            dane.addAll(); //addAll(Towary z zamówienia pobrane z bazy)
            Client client = order.getClient(); //Dane clienta z zamówienia
            String head="<!DOCTYPE html><html><head><meta charset='UTF-8'>" +
                    "<title>Order preview</title></head><body><style>@font-face " +
                    "{font-family: 'Calibri Bold'; src: url('calibrib.ttf') " +
                    "format('truetype');} body{font-family: 'Calibri Bold', sans-serif;} " +
                    "h2{font-size:14px; color:#565656;} p{font-size: 12px; color: #454545;} " +
                    "table{border-collapse: collapse; font-size:12px; width: 780px; margin: 20px auto;} " +
                    "thead td{color: #ffffff; background: #284B63; border: 0; padding: 5px;} " +
                    "tbody td{color: #454545; background: #fefefe; border: 1px solid #ccc; padding: 5px;} " +
                    "tbody td:hover{background: #eeeeee;}</style>" +
                    "<h2>Client</h2>" +
                    "<table>" +
                    "<thead>" +
                    "<tr><td><b>NIP</b></td><td><b>Contact</b></td><td><b>Phone</b></td><td><b>Mail</b></td></tr>" +
                    "</thead><tbody>";
            String clientDetails = "<tr><td>" + client.getNip() + "</td>"+
                    "<td>" + client.getTelephone() + "</td>"+
                    "<td>" + client.getMail() + "</td></tr></tbody></table>"+
                    "<h2>Invoice data</h2>" +
                    "<table><thead>" +
                    "<tr><td><b>Name</b></td><td><b>Street</b></td><td><b>Post code</b></td><td><b>City</b></td></tr></thead><tbody>" +
                    "<tr><td>" + client.getName() + "</td>"+
                    "<td>" + client.getStreet() + "</td>"+
                    "<td>" + client.getPostCode() + "</td>"+
                    "<td>" + client.getCity() + "</td></tr></tbody></table>";

            clientDetails+="<h2>Delivery details</h2>" +
                    "<table><thead><tr><td><b>Name</b></td><td><b>Street</b></td><td><b>Post code</b></td><td><b>City</b></td></tr></thead><tbody>" +
                    "<tr><td>" + client.getNameDelivery() + "</td>"+
                    "<td>" + client.getStreetDelivery() + "</td>"+
                    "<td>" + client.getPostCodeDelivery() + "</td>"+
                    "<td>" + client.getCityDelivery() + "</td></tr></tbody></table>";

            clientDetails += "<p><b>Client type:</b> " + client.getClientType()+"</p>";
            String details = "<h2>Order no. " + order.getOrderNumber()+"</h2>" +
                    head+clientDetails+
                    "<h2>Articles:</h2>" +
                    "<table><thead><tr><td><b>No.</b></td>" +
                    "<td><b>Description</b></td><td><b>Weight</b></td>" +
                    "<td><b>Unit price</b></td><td><b>Quantity</b></td><td><b>Net price</b></td></tr></thead><tbody>";
            for (OrderedArticle ti : dane) {
                details = details + printArticle(ti);
            }
            details = details + "</tbody></table>";

            details = details + "<h2>Details</h2><table><thead><tr>" +
                    "<td><b>Realization date</b></td><td><b>Status</b></td><td><b>Employee</b></td><td><b>Net price</b></td>" +
                    "<td><b>VAT</b></td><td><b>Gross price</b></td><td><b>Signed</b></td><td><b>Paid</b></td></tr></thead>" +
                    "<tbody><tr><td>" + order.getRealizationDate() + "</td>"+
                    "<td>" + order.getState() + "</td>"+
                    "<td>" + order.getEmployee().getName() + "</td>"+
                    "<td>" + Utils.formatPln(1f) + "</td>"+ //toDo: cena netto
                    "<td>" + order.getVat()  + " %</td>"+
                    "<td>" + Utils.formatPln(1f)  + "</td>"+ //toDo: cena brutto
                    "<td>" + (order.getIsSigned() ? "Yes" : "No") + "</td>"+
                    "<td>" + order.getIsPaid() + "</td></tr></tbody></table>";
            details = details + "<p><b>Payment conditions:</b> " + order.getConditions() + "</p>"+
                    "<p><b>Comments: </b>" + order.getComments()+"</p>"+"<p><b>Delivery cost: "+
                    Utils.formatPln(order.getDeliveryCost().floatValue())+"</b></p>"+"<p><b>Advance: "+Utils.formatPln(order.getAdvance().floatValue())+"</b></p>";
            details = details + "</body></html>";
            return details;
        }
        return "";
    }


    private String printArticle(OrderedArticle article){
        return "<tr><td>"+article.getDescription()+"</td><td>"+Utils.formatKg(article.getWeight())+"</td><td>"+
                Utils.formatPln(article.getUnitPrice().floatValue())+"</td><td>"+article.getAmount()+"</td><td>"+
                Utils.formatPln(article.getNetPrice().floatValue())+"</td></tr>";
    }
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Scene scene = new Scene(mainBox);
        scene.getStylesheets().add(Main.css);
        stage.setWidth(840);
        stage.setHeight(600);
        stage.setScene(scene);
        setEvents();

    }

    public void setOrdersController(OrdersController ordersController) {
        this.ordersController = ordersController;
    }
}
