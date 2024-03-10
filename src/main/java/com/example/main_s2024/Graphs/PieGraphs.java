package com.example.main_s2024.Graphs;

import javafx.scene.chart.PieChart;

public class PieGraphs {

    private PieChart.Data slice1;

    private PieChart.Data slice2;

    public PieGraphs(PieChart pieChart) {

        slice1 = new PieChart.Data("Unused space", 0);
        slice2 = new PieChart.Data("Used space", 0);

        pieChart.getData().add(slice1);
        pieChart.getData().add(slice2);
        pieChart.setPrefSize(400, 300);
        pieChart.animatedProperty().setValue(false);

        pieChart.legendVisibleProperty().setValue(false);
    }

    public void addEntryPieGraphs(Float[] avaibleFileSystem) {
        slice1.setName(avaibleFileSystem[0] + "%\nUnused space");
        slice1.setPieValue(avaibleFileSystem[0]);
        slice2.setName((100f - avaibleFileSystem[0]) + "%\nUsed space");
        slice2.setPieValue(100f - avaibleFileSystem[0]);

        slice1.getNode().setStyle("-fx-pie-color: #bbdefb");
        slice2.getNode().setStyle("-fx-pie-color: #78909c");
    }
}
