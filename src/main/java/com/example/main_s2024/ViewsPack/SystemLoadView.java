package com.example.main_s2024.ViewsPack;

import com.example.main_s2024.DataPack.DynamicGeneralStats;
import com.example.main_s2024.DataPack.StaticGeneralStats;
import com.example.main_s2024.Graphs.LineGraphs;
import javafx.scene.control.Label;

import java.util.Observable;
import java.util.Observer;

public class SystemLoadView implements Observer {

    private Label systemLoadText;
    private LineGraphs lineChartClass;

    public SystemLoadView(Label systemLoadText, LineGraphs lineChartClass) {
        this.systemLoadText = systemLoadText;
        this.lineChartClass = lineChartClass;
        StaticGeneralStats.getInstance().addingObserver(SystemLoadView.this);
    }


    @Override
    public void update(Observable o, Object arg) {
        systemLoadText.setText(String.join("\n", StaticGeneralStats.getInstance().getMiscellaneous()));
        lineChartClass.addEntryLineChart(DynamicGeneralStats.getInstance().getCpuLoad());
    }
}
