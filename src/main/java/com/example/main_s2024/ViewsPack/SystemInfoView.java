package com.example.main_s2024.ViewsPack;

import com.example.main_s2024.DataPack.StaticGeneralStats;
import javafx.scene.control.Label;

import java.util.Observable;
import java.util.Observer;

public class SystemInfoView implements Observer {
    private Label systemInfoText;

    public SystemInfoView(Label systemInfoText) {
        this.systemInfoText = systemInfoText;
        StaticGeneralStats.getInstance().addingObserver(SystemInfoView.this);
    }


    @Override
    public void update(Observable o, Object arg) {
        systemInfoText.setText(String.join("\n", StaticGeneralStats.getInstance().getComputerInfo()));

    }
}
