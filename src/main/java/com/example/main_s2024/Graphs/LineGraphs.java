package com.example.main_s2024.Graphs;

import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

public class LineGraphs {

    private XYChart.Series series;

    private int numberOfVisibleEntries = 0;

    private NumberAxis xAxis;

    public LineGraphs(LineChart lineChart) {
        NumberAxis yAxis; //Y axis variable

        //getter and setting proprieties for y and x axes
        yAxis = (NumberAxis) lineChart.getYAxis();
        xAxis = (NumberAxis) lineChart.getXAxis();
        xAxis.setLowerBound(0);
        xAxis.setUpperBound(15);
        xAxis.setAutoRanging(false);
        yAxis.setAutoRanging(false);
        yAxis.setLowerBound(0);
        yAxis.setUpperBound(100);

        //set series of content to draw chart
        series = new XYChart.Series();
        series.setName("CPU load");

        //set color and name chart
        lineChart.setStyle("CHART_COLOR_1: #ff3d00;");
        lineChart.animatedProperty().setValue(false);
        lineChart.getData().add(series);
    }

    public void addEntryLineChart(Float value) {
        series.getData().add(new XYChart.Data(numberOfVisibleEntries, value));
        int maxRange = 15; //range of visible chart

        if (numberOfVisibleEntries > maxRange) {
            series.getData().remove(0);
        }
        if (numberOfVisibleEntries > maxRange - 1) {
            xAxis.setLowerBound(xAxis.getLowerBound() + 1);
            xAxis.setUpperBound(xAxis.getUpperBound() + 1);
        }
        numberOfVisibleEntries++;
    }
}
