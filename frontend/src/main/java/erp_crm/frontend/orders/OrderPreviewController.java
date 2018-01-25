package main.java.erp_crm.frontend.orders;

import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import main.java.erp_crm.Main;
import main.java.erp_crm.backend.Utils;
import main.java.erp_crm.backend.model.common.Client;
import main.java.erp_crm.backend.model.erp.Order;
import main.java.erp_crm.backend.model.erp.OrderedArticle;

import java.net.URL;
import java.util.ResourceBundle;

public class OrderPreviewController implements Initializable {
    public VBox mainBox;
    public WebView previewView;
    public Button closeBtn;



    private Stage stage = new Stage();


    private void setEvents() {
        closeBtn.setOnAction(e -> stage.close());
    }



    public void show(Order order){
        if(order!=null){
            previewView.getEngine().loadContent(
                    createOrderPreviewString(order)
            );
            stage.show();
        }
    }



    private String createOrderPreviewString(Order order) {
        if(order!=null){
            Client client = order.getClient(); //Dane klienta
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
                    "<tr><td><b>NIP</b></td><td><b>Phone</b></td><td><b>Mail</b></td></tr>" +
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
            StringBuilder details = new StringBuilder("<h2>Order no. " + order.getOrderNumber() + "</h2>" +
                    head + clientDetails +
                    "<h2>Articles:</h2>" +
                    "<table><thead><tr><td><b>No.</b></td>" +
                    "<td><b>Article name</b></td><td><b>Weight</b></td>" +
                    "<td><b>Unit price</b></td><td><b>Quantity</b></td><td><b>Net price</b></td></tr></thead><tbody>");
            int i = 1;
            for (OrderedArticle ti : order.getOrderedArticles()) {
                details.append(printArticle(ti, i));
            }
            details.append("</tbody></table>");

            details.append("<h2>Details</h2><table><thead><tr>" + "<td><b>Realization date</b></td><td><b>Status</b></td><td><b>Employee</b></td><td><b>Net price</b></td>" + "<td><b>VAT</b></td><td><b>Gross price</b></td><td><b>Signed</b></td><td><b>Paid</b></td></tr></thead>" + "<tbody><tr><td>").append(order.getRealizationDate()).append("</td>").append("<td>").append(order.getState()).append("</td>").append("<td>").append(order.getEmployee().getName()).append("</td>").append("<td>").append(Utils.formatPln(1f)).append("</td>").append( //toDo: cena netto
                    "<td>").append(order.getVat()).append(" %</td>").append("<td>").append(Utils.formatPln(1f)).append("</td>").append( //toDo: cena brutto
                    "<td>").append(order.getIsSigned() ? "Yes" : "No").append("</td>").append("<td>").append(order.getIsPaid()).append("</td></tr></tbody></table>");
            details.append("<p><b>Payment conditions:</b> ").append(order.getConditions()).append("</p>").append("<p><b>Comments: </b>").append(order.getComments()).append("</p>").append("<p><b>Delivery cost: ").append(Utils.formatPln(order.getDeliveryCost().floatValue())).append("</b></p>").append("<p><b>Advance: ").append(Utils.formatPln(order.getAdvance().floatValue())).append("</b></p>");
            details.append("</body></html>");
            return details.toString();
        }
        return "";
    }


    private String printArticle(OrderedArticle article, int i){
        if(article!=null){
            return "<tr><td>"+i+".</td><td>"+
                    article.getArticle().getName()+"</td><td>"+
                    Utils.formatKg(article.getWeight())+"</td><td>"+
                    Utils.formatPln(
                            article.getArticle().getUnitPrice() != null ? article.getArticle().getUnitPrice().floatValue() : 0f)
                    +"</td><td>"+
                    (article.getAmount() != null ? article.getAmount() :"")+"</td><td>"+
                    Utils.formatPln(article.getNetPrice().floatValue())+"</td></tr>";
        } else

            return "<tr><td></td><td></td><td></td><td></td><td></td></tr>";
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

}
