package com.example.main_s2024.ViewsPack;


import com.example.main_s2024.DataPack.DynamicGeneralStats;
import com.example.main_s2024.DataPack.StaticGeneralStats;
import com.example.main_s2024.Graphs.MultiLineGraphs;
import com.example.main_s2024.Graphs.PieGraphs;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;

import java.util.Observable;
import java.util.Observer;

public class DiskView implements Observer {

    private Label disksText;

    private PieGraphs pieChartClass;

    public DiskView(Label disksText, PieGraphs pieChartClass) {
        this.disksText = disksText;
        this.pieChartClass = pieChartClass;
        StaticGeneralStats.getInstance().addingObserver(DiskView.this);
    }


    @Override
    public void update(Observable o, Object arg) {
        disksText.setText(String.join("\n", DynamicGeneralStats.getInstance().getDisks()));
        pieChartClass.addEntryPieGraphs(DynamicGeneralStats.getInstance().getAvaibleFileSystem());
    }
}
