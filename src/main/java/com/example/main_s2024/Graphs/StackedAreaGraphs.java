package com.example.main_s2024.Graphs;

import javafx.scene.Node;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.StackedAreaChart;
import javafx.scene.chart.XYChart;

public class StackedAreaGraphs {

    private XYChart.Series series;

    private int numberOfVisibleEntries = 0;

    private NumberAxis xAxis;

    public StackedAreaGraphs(StackedAreaChart stackedAreaChart) {
        NumberAxis yaxis = (NumberAxis) stackedAreaChart.getYAxis();

        //getter and setting proprieties for y and x axes
        xAxis = (NumberAxis) stackedAreaChart.getXAxis();
        xAxis.setLowerBound(0);
        xAxis.setUpperBound(10);
        xAxis.setAutoRanging(false);
        xAxis.setTickLabelsVisible(false);
        stackedAreaChart.setVerticalGridLinesVisible(false);
        stackedAreaChart.animatedProperty().setValue(false);
        yaxis.setAutoRanging(false);
        yaxis.setLowerBound(0);
        yaxis.setUpperBound(100);

        series = new XYChart.Series();
        series.setName("Battery level");
        stackedAreaChart.getData().add(series);

        //setting colors of charts
        Node fill = series.getNode().lookup(".chart-series-area-fill");
        fill.setStyle("-fx-fill: #fff7ad;");
        Node line = series.getNode().lookup(".chart-series-area-line");
        line.setStyle("-fx-stroke: #8bc34a;" +
                "-fx-stroke-width: 3px;");
        stackedAreaChart.setStyle("CHART_COLOR_1: #8bc34a;");
    }

    public void addEntryStackedAreaChart(Integer value) {
        series.getData().add(new XYChart.Data(numberOfVisibleEntries, value));
        int maxRange = 10;
        if (numberOfVisibleEntries > maxRange - 1) {
            xAxis.setUpperBound(xAxis.getUpperBound() + 1);
        }
        numberOfVisibleEntries++;
    }

}
