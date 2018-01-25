package main.java.erp_crm.frontend.statistics;

import javafx.collections.ObservableList;
import javafx.geometry.Side;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class ErpChart {
    public PieChart getWykres() {
        return wykres;
    }

    private PieChart wykres;
    private Label pracownikLabel;
    private Label kwotaLabel;
    private Label procentLabel;
    public Float calosc;
    public static StatisticsView statisticsView;
    public ErpChart(Label pracownikLabel, Label kwotaLabel, Label procentLabel, Float calosc){
        this.pracownikLabel = pracownikLabel;
        this.kwotaLabel = kwotaLabel;
        this.procentLabel = procentLabel;
        this.calosc = calosc;
        wykres = new PieChart();
        wykres.setTitle(null);
        wykres.setLegendSide(Side.BOTTOM);
        final Label caption = new Label("");
        caption.setTextFill(Color.WHITE);

    }


    public void setData(ObservableList<Data> da,Boolean admin) {

        wykres.getData().clear();
        wykres.getData().addAll(da);
        for (final PieChart.Data data : wykres.getData()) {
            if(admin) {
                data.getNode().addEventHandler(MouseEvent.MOUSE_ENTERED,
                        e -> statisticsView.update(data.getName(), Float.toString(Math.round(calosc * (float) data.getPieValue()) / 100f) + " zł", String.valueOf(Math.round(data.getPieValue())) + "% of total"));
                data.getNode().addEventHandler(MouseEvent.MOUSE_EXITED,
                        e -> statisticsView.update("", "", ""));
            }
        }
    }
}
