package com.example.main_s2024.Graphs;

import com.example.main_s2024.DataPack.DynamicGeneralStats;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

import java.util.stream.Stream;


public class MultiLineGraphs {
    private XYChart.Series[] series;

    private LineChart multiLineChart;

    private int numberOfVisibleEntries = 0;

    private NumberAxis xAxis;

    public MultiLineGraphs(LineChart multiLineChart) {
        this.multiLineChart = multiLineChart;
        multiLineChart.animatedProperty().setValue(false);
        NumberAxis yAxis;

        yAxis = (NumberAxis) multiLineChart.getYAxis();
        xAxis = (NumberAxis) multiLineChart.getXAxis();
        xAxis.setLowerBound(0);
        xAxis.setUpperBound(10);
        xAxis.setAutoRanging(false);
        yAxis.setAutoRanging(false);
        yAxis.setLowerBound(0);
        yAxis.setUpperBound(100);
    }

    public void createSeries() {
        Float[] seriesValues = DynamicGeneralStats.getInstance().getPercPerThread();
        series = Stream.<XYChart.Series<String, Number>>generate(XYChart.Series::new).limit(seriesValues.length).
                toArray(XYChart.Series[]::new);

        for (int i = 0; i < series.length; i++) {
            series[i] = new XYChart.Series();
            series[i].setName("Thread " + i);
            multiLineChart.getData().add(series[i]);
        }
    }

    public void addEntryLineChart(Float[] value) {
        int maxRange = 10;

        for (int i = 0; i < value.length; i++) {
            series[i].getData().add(new XYChart.Data(numberOfVisibleEntries, value[i]));
            if (numberOfVisibleEntries > maxRange) {
                series[i].getData().remove(0);
            }
        }
        if (numberOfVisibleEntries > maxRange - 1) {
            xAxis.setLowerBound(xAxis.getLowerBound() + 1);
            xAxis.setUpperBound(xAxis.getUpperBound() + 1);
        }
        numberOfVisibleEntries++;
    }
}
