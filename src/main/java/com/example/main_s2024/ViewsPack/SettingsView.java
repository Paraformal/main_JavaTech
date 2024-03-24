package com.example.main_s2024.ViewsPack;

import com.example.main_s2024.Controller;
import com.example.main_s2024.DataPack.DynamicGeneralStats;
import com.example.main_s2024.DataPack.StaticGeneralStats;
import com.example.main_s2024.Graphs.MultiLineGraphs;
import com.example.main_s2024.Graphs.PieGraphs;
import com.example.main_s2024.HelloController;
import javafx.application.Platform;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.util.Observable;
import java.util.Observer;

public class SettingsView implements Observer {

    private Label bluetoothInformation;
    private ImageView qrImageView;
    private VBox settingVBox;

    public SettingsView(ImageView qrImageView, Label bluetoothInformation, VBox openLibs, VBox settingVBox) {
        this.qrImageView = qrImageView;
        this.settingVBox = settingVBox;
        this.bluetoothInformation = bluetoothInformation;
        Label desc = new Label();
        desc.setText("Made By Peter El Khoury.\n");

        openLibs.getChildren().addAll(desc);
        StaticGeneralStats.getInstance().addingObserver(SettingsView.this);
    }

    @Override
    public void update(Observable o, Object arg) {
        if (StaticGeneralStats.getInstance().isServerCreated()) {
            String url = StaticGeneralStats.getInstance().getIpAddress() + ":" + StaticGeneralStats.getInstance().
                    getPort();
            qrImageView.setVisible(true);

            if (settingVBox.getChildren().get(1) instanceof Label) {
                settingVBox.getChildren().remove(1);
                settingVBox.getChildren().add(qrImageView);
            }

            bluetoothInformation.setText(StaticGeneralStats.getInstance().getBluetoothName());
        } else {
            if (settingVBox.getChildren().get(1) instanceof ImageView) {
                settingVBox.getChildren().remove(qrImageView);
                settingVBox.getChildren().add(new Label("Server not created"));
            }
        }
    }
}
