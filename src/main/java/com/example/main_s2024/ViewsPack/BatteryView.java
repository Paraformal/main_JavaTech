package com.example.main_s2024.ViewsPack;

import com.example.main_s2024.DataPack.DynamicGeneralStats;
import com.example.main_s2024.DataPack.StaticGeneralStats;
import com.example.main_s2024.Graphs.StackedAreaGraphs;
import javafx.beans.Observable;
import javafx.scene.control.Label;

import java.util.Observer;

public class BatteryView implements Observer {

    private Label batteryText;

    private StackedAreaGraphs stackedAreaChartClass;

    public BatteryView(Label batteryText, StackedAreaGraphs stackedAreaChartClass) {
        this.batteryText = batteryText;
        this.stackedAreaChartClass = stackedAreaChartClass;
        StaticGeneralStats.getInstance().addingObserver(BatteryView.this);
    }

    @Override
    public void update(java.util.Observable o, Object arg) {
        batteryText.setText(String.join("\n" + DynamicGeneralStats.getInstance().getBattery()));

        if (DynamicGeneralStats.getInstance().getBatteryPerc() != -1) {
            stackedAreaChartClass.addEntryStackedAreaChart(DynamicGeneralStats.getInstance().getBatteryPerc());
        }
    }
}
