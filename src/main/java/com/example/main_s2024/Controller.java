package com.example.main_s2024;

import com.example.main_s2024.DataController.DataBegin;
import com.example.main_s2024.Graphs.LineGraphs;
import com.example.main_s2024.Graphs.MultiLineGraphs;
import com.example.main_s2024.Graphs.PieGraphs;
import com.example.main_s2024.Graphs.StackedAreaGraphs;
import com.example.main_s2024.ViewsPack.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.StackedAreaChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.util.List;

public class Controller {

    @FXML
    private VBox settingVBox;
    @FXML
    private ImageView qrImageView;
    @FXML
    private VBox openLibs;
    /* @FXML
     private Label serverPortInformation;*/
    @FXML
    private Label bluetoothInformation;
    /*@FXML
    private Label ipAddressInformation;*/
    @FXML
    private StackedAreaChart stackedAreaChartBattery;
    @FXML
    private LineChart lineChartPercPerThread;
    @FXML
    private PieChart pieChartDisk;
    @FXML
    private Button buttonSettings;
    @FXML
    private Button buttonSystemInfo;
    @FXML
    private Button buttonHdd;
    @FXML
    private Button buttonProcessor;
    @FXML
    private Button buttonBattery;
    @FXML
    private VBox mainVbox;
    @FXML
    private LineChart lineChartSystemLoad;
    @FXML
    private Button buttonSystemLoad;
    @FXML
    private Label systemInfoText;
    @FXML
    private Label systemLoadText;
    @FXML
    private Label cpuText;
    @FXML
    private Label batteryText;
    @FXML
    private Label disksText;

    private Node systemLoadBox;
    private Node batteryBox;
    private Node cpuBox;
    private Node systemInfoBox;
    private Node disksBox;
    private Node settingsBox;

    @FXML
    private void initialize() {
        PieGraphs PieGraphs = new PieGraphs(pieChartDisk);
        LineGraphs lineChartClass = new LineGraphs(lineChartSystemLoad);
        MultiLineGraphs multiLineGraphs = new MultiLineGraphs(lineChartPercPerThread);
        StackedAreaGraphs stackedAreaChartClass = new StackedAreaGraphs(stackedAreaChartBattery);

        //getting all view
        systemLoadBox = mainVbox.getChildren().get(0);
        batteryBox = mainVbox.getChildren().get(1);
        cpuBox = mainVbox.getChildren().get(2);
        disksBox = mainVbox.getChildren().get(3);
        systemInfoBox = mainVbox.getChildren().get(4);
        settingsBox = mainVbox.getChildren().get(5);
        changeView(systemLoadBox); //set first view

        new SystemLoadView(systemLoadText, lineChartClass);
        new BatteryView(batteryText, stackedAreaChartClass);
        new CpuView(cpuText, multiLineGraphs);
        new DiskView(disksText, PieGraphs);
        new SystemInfoView(systemInfoText);
        new SettingsView(qrImageView, bluetoothInformation, openLibs, settingVBox);

        // button listener on bottom
        buttonSystemLoad.setOnAction(event -> changeView(systemLoadBox));
        buttonBattery.setOnAction(event -> changeView(batteryBox));
        buttonProcessor.setOnAction(event -> changeView(cpuBox));
        buttonHdd.setOnAction(event -> changeView(disksBox));
        buttonSystemInfo.setOnAction(event -> changeView(systemInfoBox));
        buttonSettings.setOnAction(event -> changeView(settingsBox));
    }

    private void changeView(Node view) {
        mainVbox.getChildren().removeAll(systemInfoBox, batteryBox, cpuBox, disksBox, systemLoadBox, settingsBox);
        mainVbox.getChildren().add(view);
    }

    public void updateUI(DataBegin data) {
        Platform.runLater(() -> {
            // Update Labels Directly from DataBegin
            cpuText.setText("CPU Load: " + data.getNumericCpuLoad() + "%");
            batteryText.setText("Battery: " + data.getNumericBatteryPerc() + "%");
            disksText.setText("Disks: " + data.getDisks());
            systemInfoText.setText("Info: " + data.getComputerInfo());
            // Assuming you want to display miscellaneous data somewhere
            // For example, in a label not shown in your initial Controller code
            // miscText.setText("Misc: " + data.getMiscellaneous());

            // Update Line Chart for CPU Load
            updateLineChartForCPULoad(data.getNumericCpuLoad());

        });
    }

    private void updateLineChartForCPULoad(String cpuLoad) {
        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        series.setName("CPU Load");

        try {
            float load = Float.parseFloat(cpuLoad);
            series.getData().add(new XYChart.Data<>(1, load)); // Using '1' as a placeholder for the x-axis value
        } catch (NumberFormatException e) {
            System.err.println("Error parsing CPU load: " + cpuLoad);
        }

        lineChartSystemLoad.getData().clear();
        lineChartSystemLoad.getData().add(series);
    }


}
