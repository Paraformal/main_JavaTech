package com.example.main_s2024.ViewsPack;

import com.example.main_s2024.DataPack.DynamicGeneralStats;
import com.example.main_s2024.DataPack.StaticGeneralStats;
import com.example.main_s2024.Graphs.MultiLineGraphs;
import javafx.scene.control.Label;

import java.util.Observable;
import java.util.Observer;

public class CpuView implements Observer {

    private Label cpuText;
    private MultiLineGraphs multipleLineChartClass;

    public CpuView(Label cpuText, MultiLineGraphs multipleLineChartClass) {
        this.cpuText = cpuText;
        this.multipleLineChartClass = multipleLineChartClass;
        StaticGeneralStats.getInstance().addingObserver(CpuView.this);
    }


    @Override
    public void update(Observable o, Object arg) {
        cpuText.setText(String.join("\n", StaticGeneralStats.getInstance().getCpuInfo()));

        if (StaticGeneralStats.getInstance().isFirtShow()) {
            multipleLineChartClass.createSeries();
            multipleLineChartClass.addEntryLineChart(DynamicGeneralStats.getInstance().getPercPerThread());
        } else {
            multipleLineChartClass.addEntryLineChart(DynamicGeneralStats.getInstance().getPercPerThread());
        }
    }
}
