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
        desc.setText("This program is licensed under the GNU AGPLv3 or later.\n\n" +
                "PC-status uses the following open source libraries:\n");
        Label bluecove = new Label("Bluecove");
        bluecove.setTextFill(Color.BLUE);
        bluecove.setOnMouseClicked(event -> Platform.runLater(() ->
                HelloController.serverBatteryMain.getHostServices().showDocument("http://www.bluecove.org")));

        Label sigar = new Label("Sigar-lib");
        sigar.setTextFill(Color.BLUE);
        sigar.setOnMouseClicked(event -> Platform.runLater(() ->
                HelloController.serverBatteryMain.getHostServices().
                        showDocument("https://mvnrepository.com/artifact/org.gridkit.lab/sigar-lib")));

        Label spring = new Label("SpringFramework");
        spring.setTextFill(Color.BLUE);
        spring.setOnMouseClicked(event -> Platform.runLater(() ->
                HelloController.serverBatteryMain.getHostServices().
                        showDocument("https://projects.spring.io/spring-framework/")));

        Label androidJson = new Label("Android-Json");
        androidJson.setTextFill(Color.BLUE);
        androidJson.setOnMouseClicked(event -> Platform.runLater(() ->
                HelloController.serverBatteryMain.getHostServices().
                        showDocument("https://mvnrepository.com/artifact/com.vaadin.external.google/" +
                                "android-json/0.0.20131108.vaadin1")));

        Label zxing = new Label("Zxing");
        zxing.setTextFill(Color.BLUE);
        zxing.setOnMouseClicked(event -> Platform.runLater(() ->
                HelloController.serverBatteryMain.getHostServices().
                        showDocument("https://github.com/zxing/zxing")));

        Label icons = new Label("Icons8");
        icons.setTextFill(Color.BLUE);
        icons.setOnMouseClicked(event -> Platform.runLater(() ->
                HelloController.serverBatteryMain.getHostServices().showDocument("https://icons8.com")));

        openLibs.getChildren().addAll(desc, bluecove, sigar, spring, androidJson, zxing, icons);
        StaticGeneralStats.getInstance().addingObserver(SettingsView.this);
    }

    @Override
    public void update(Observable o, Object arg) {
        if (StaticGeneralStats.getInstance().isServerCreated()) {
            String url = StaticGeneralStats.getInstance().getIpAddress() + ":" + StaticGeneralStats.getInstance().
                    getPort();
//            qrImageView.setImage(Controller.createQR(url));
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
