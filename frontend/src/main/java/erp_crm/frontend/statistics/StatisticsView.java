package main.java.erp_crm.frontend.statistics;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import main.java.erp_crm.backend.Utils;
import main.java.erp_crm.backend.model.DBData;
import main.java.erp_crm.backend.model.common.Employee;
import main.java.erp_crm.backend.model.erp.Order;
import main.java.erp_crm.backend.model.erp.OrderedArticle;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class StatisticsView {
    private GridPane main=new GridPane();
    private Label headerLabel = new Label("Reports");
    private ErpChart erpChart;
    private Label pracownikLabel=new Label("Employee: ");
    private Label planLabel = new Label("Month schedule: ");
    private Label kwotaLabel=new Label("Amount: ");
    private Label procentLabel=new Label("");
    private Label caloscLabel=new Label("");
    private Label odLabel=new Label("From");
    private Label doLabel=new Label("To");
    private Button generujBtn = new Button("Generate");
    private DatePicker fromDatePicker =new DatePicker();
    private DatePicker toDatePicker =new DatePicker();
    private Float calosc=0f;
    ArrayList<Employee> t = new ArrayList<>();

    public StatisticsView(){
        Font font = new Font("main/resources/calibri.ttf", 14);
        pracownikLabel.setFont(font);
        pracownikLabel.setStyle("-fx-text-fill: #284B63; -font-weight:bold;");
        planLabel.setFont(font);
        planLabel.setStyle("-fx-text-fill: #284B63; -font-weight:bold;");
        kwotaLabel.setFont(font);
        kwotaLabel.setStyle("-fx-text-fill: #284B63; -font-weight:bold;");
        procentLabel.setFont(font);
        procentLabel.setStyle("-fx-text-fill: #284B63; -font-weight:bold;");
        caloscLabel.setFont(font);
        caloscLabel.setPadding(new Insets(20,0,0,0));
        fromDatePicker.setValue(LocalDate.now());
        toDatePicker.setValue(LocalDate.now());
        ErpChart.statisticsView =this;
        erpChart =new ErpChart(pracownikLabel,kwotaLabel,procentLabel,calosc);

        main.setPadding(new Insets(10));
        main.setHgap(10);
        main.setVgap(10);
        HBox odBox = new HBox();
        odBox.setSpacing(10);
        HBox doBox = new HBox();
        doBox.setSpacing(10);
        VBox szczegolyBox = new VBox();
        szczegolyBox.setSpacing(10);
        odBox.getChildren().addAll(odLabel, fromDatePicker);
        doBox.getChildren().addAll(doLabel, toDatePicker,generujBtn);
        szczegolyBox.getChildren().addAll(pracownikLabel,planLabel,kwotaLabel,procentLabel,caloscLabel);
        main.add(headerLabel,0,0);
        main.add(odBox,1,1);
        main.add(doBox,2,1);
        main.add(erpChart.getWykres(),0,2,2,3);
        szczegolyBox.setPadding(new Insets(40,10,10,10));
        main.add(szczegolyBox,2,2);
        generujBtn.setOnAction(e->{
            boolean isAdmin = Employee.Role.ADMIN.equalsTo(DBData.getLoggedUser().getRole());
            ObservableList<PieChart.Data> da;
            if(isAdmin) {
                da = getDataForAdmin();
            } else {
                da = getDataForEmployee();
            }
            erpChart.setData(da, isAdmin);

        });
    }
    public ObservableList<PieChart.Data> getDataForEmployee(){
        ObservableList<PieChart.Data> d = FXCollections.observableArrayList();
        Float cal =calculateAllNet();
        caloscLabel.setText("");
        erpChart.calosc=cal;
        calosc=cal;
        d.addAll(new PieChart.Data("Net",Math.round(calculateNet(DBData.getLoggedUser()))), new PieChart.Data("Left",100-calculateNet(DBData.getLoggedUser())));
        pracownikLabel.setText(DBData.getLoggedUser().getName());
        kwotaLabel.setText("Orders' net: "+ Utils.formatPln(calosc));
        planLabel.setText("Month schedule: "+Utils.formatPln((DBData.getLoggedUser().getMonthSchedule() != null ? DBData.getLoggedUser().getMonthSchedule().floatValue() : 0f)));
        procentLabel.setText(Math.round(calosc/calculateNet(DBData.getLoggedUser()))+"% schedule");
        return d;
    }

    private Float calculateAllNet() {
        float res = 0f;
        long fromTime = Date.valueOf(fromDatePicker.getValue()).getTime();
        long toTime = Date.valueOf(toDatePicker.getValue()).getTime();
        for(Order i : DBData.getOrders()){
            long orderTime = i.getOrderDate().toDate().getTime();

            if(orderTime > fromTime && orderTime < toTime){
                res+= sumOrderedArticlesCost(i.getOrderedArticles());
            }
        }
        return res;
    }

    private float calculateNet(Employee employee) {
        return calosc/(employee.getMonthSchedule() != null ? employee.getMonthSchedule().floatValue() : 1)*100;
    }

    public ObservableList<PieChart.Data> getDataForAdmin() {
        ObservableList<PieChart.Data> d = FXCollections.observableArrayList();
        Float cal = calculateAllNet();
        caloscLabel.setText("Total orders' net: "+Utils.formatPln(cal));
        erpChart.calosc=cal;
        calosc=cal;
        for(Employee i : DBData.getEmployees()){ 
            d.add(new PieChart.Data(i.getName(), Math.abs(getEmployeeOrderSum(i, fromDatePicker.getValue(), toDatePicker.getValue()) * 100 / cal)));
        }
        return d;
    }
    
    private float getEmployeeOrderSum(Employee employee, LocalDate fromDate, LocalDate toDate){
        float res = 0;
        long fromLong = Date.valueOf(fromDate).getTime();
        long toLong = Date.valueOf(toDate).getTime();
        for(Order o : DBData.getOrders()){

            LocalDate orderLocalDate = LocalDate.of(o.getOrderDate().getYear(), o.getOrderDate().getMonthOfYear(), o.getOrderDate().getDayOfMonth());
            long orderDateLong = Date.valueOf(orderLocalDate).getTime();
            if(
            o.getEmployee().getId().equals(employee.getId()) &&
            fromLong <= orderDateLong &&
            toLong >= orderDateLong
            ) {
                res+= sumOrderedArticlesCost(o.getOrderedArticles());
            }
        }
        return res;
    }

    private float sumOrderedArticlesCost(List<OrderedArticle> orderedArticles) {
        float res = 0;
        for(OrderedArticle o : orderedArticles){
            res+=o.getNetPrice().floatValue();
        }
        return res;
    }

    public GridPane getBox(){
        return this.main;
    }

    public void update(String name, String kwota, String procent){
        pracownikLabel.setText("Employee: "+name);
        planLabel.setText("Month schedule: "+findPlan(t,name));
        kwotaLabel.setText("Amount: "+kwota);
        procentLabel.setText(procent);
    }
    private String findPlan(ArrayList<Employee> l, String name){
        for(Employee i : l){
            if(i.getName().equals(name)){
                return Utils.formatPln(i.getMonthSchedule().floatValue()+0f);
            }
        }
        return "";
    }
}
