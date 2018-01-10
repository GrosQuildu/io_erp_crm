package main.java.erp.frontend.statistics;

import main.java.erp.backend.SharedData;
import main.java.erp.backend.Utils;
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
import main.java.erp.backend.model.common.Employee;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;


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
    private DatePicker odPicker=new DatePicker();
    private DatePicker doPicker=new DatePicker();
    private Float calosc=0f;
    private Employee p = null;
    ArrayList<Employee> t = new ArrayList<>();

    public StatisticsView(){
        Font font = new Font("calibri.ttf", 14);
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
        odPicker.setValue(LocalDate.now());
        doPicker.setValue(LocalDate.now());
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
        odBox.getChildren().addAll(odLabel,odPicker);
        doBox.getChildren().addAll(doLabel,doPicker,generujBtn);
        szczegolyBox.getChildren().addAll(pracownikLabel,planLabel,kwotaLabel,procentLabel,caloscLabel);
        main.add(headerLabel,0,0);
        main.add(odBox,1,1);
        main.add(doBox,2,1);
        main.add(erpChart.getWykres(),0,2,2,3);
        szczegolyBox.setPadding(new Insets(40,10,10,10));
        main.add(szczegolyBox,2,2);
        generujBtn.setOnAction(e->{
            if(SharedData.getAdmin()) {
                ObservableList<PieChart.Data> da = getDataForAdmin();
                erpChart.setData(da, SharedData.getAdmin());
            } else {
                ObservableList<PieChart.Data> da = getDataForWorker();
                erpChart.setData(da, SharedData.getAdmin());
            }

        });
    }
    public ObservableList<PieChart.Data> getDataForWorker(){
        ObservableList<PieChart.Data> d = FXCollections.observableArrayList();
        Float cal =0f; //pobranie kwot z bazy w zakresie od daty do daty
        caloscLabel.setText("");
        erpChart.calosc=cal;
        calosc=cal;
        d.addAll(new PieChart.Data("Net",Math.round(calosc/p.getMonthSchedule().floatValue()*100)), new PieChart.Data("Pozostało",100-Math.round(calosc/p.getMonthSchedule().floatValue()*100)));
        pracownikLabel.setText(p.getName());
        kwotaLabel.setText("Orders' net: "+ Utils.formatPln(calosc));
        planLabel.setText("Month schedule: "+Utils.formatPln(p.getMonthSchedule().floatValue()+0f));
        procentLabel.setText(Math.round(calosc/p.getMonthSchedule().floatValue()*100)+"% schedule");
        return d;
    }
    public ObservableList<PieChart.Data> getDataForAdmin() {
        ObservableList<PieChart.Data> d = FXCollections.observableArrayList();
        Float cal = 0f; //jak wyżej
        caloscLabel.setText("Total orders' net: "+Utils.formatPln(cal));
        erpChart.calosc=cal;
        calosc=cal;
        /*for(Employee i : Arrays.asList(new Employee(), new Employee())){ //iterowanie po liście pracowników (pobranej z bazy)
            //tak to wyglądało:
            // d.add(new PieChart.Data(i.getImie()+" "+i.getNazwisko(), Math.abs(SharedData.getSumaZamowienPracownika(i.getMail(), Date.valueOf(odPicker.getValue()), Date.valueOf(doPicker.getValue())) * 100 / cal)));
        }*/
        return d;
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
