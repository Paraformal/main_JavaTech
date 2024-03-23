package com.example.main_s2024;

import com.example.main_s2024.DataController.DataBegin;
import com.example.main_s2024.DataController.DataBeginController;
import com.example.main_s2024.DataPack.StaticGeneralStats;
import com.example.main_s2024.DbHandler.DbHandler;

import com.example.main_s2024.StageHandler.StageHandler;
import com.example.main_s2024.Utils.requestHandler;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.hardware.CentralProcessor.ProcessorIdentifier;
import oshi.SystemInfo;
import org.hyperic.sigar.CpuPerc;
import org.hyperic.sigar.SigarException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class HelloController extends Application {

//    private DbHandler connection;
//
//    private StageHandler stageHandler;
//    private StringBuilder genericStringBuilder = new StringBuilder();
//    private CentralProcessor processor = new SystemInfo().getHardware().getProcessor();
//
//
//    @FXML
//    protected void onClick() {
//    }


    public static HelloController serverBatteryMain;
    private Stage primaryStage;
    private ScheduledExecutorService scheduledExecutorService;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        serverBatteryMain = this;
        this.primaryStage = primaryStage;

        // Setup the primary stage
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/com/example/main_s2024/icon.png")));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/main_s2024/sample.fxml"));

        try {
            Parent root = loader.load();
            Controller controller = loader.getController();

            // Initial data fetch and UI update
            fetchDataAndUpdateUI(controller);

            // Setup for periodic data fetch and UI update
            setupPeriodicDataFetch(controller);

            this.primaryStage.setScene(new Scene(root));
            this.primaryStage.setResizable(false);
            this.primaryStage.show();
            this.primaryStage.centerOnScreen();
            this.primaryStage.setOnCloseRequest(event -> shutdownApplication());

            // Optionally set title with IP address
            setTitleWithIpAddress();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void fetchDataAndUpdateUI(Controller controller) {
        try {
            DataBegin data = DataBeginController.getInstance().getData();
            Platform.runLater(() -> controller.updateUI(data));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private void setupPeriodicDataFetch(Controller controller) {
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleAtFixedRate(() -> fetchDataAndUpdateUI(controller), 0, 5,
                TimeUnit.SECONDS);
    }

    private void setTitleWithIpAddress() {
        try {
            String ip = getMyIp();
            String title = "PCstatus - " + (ip.isEmpty() ? "No IP" : ip);
            Platform.runLater(() -> primaryStage.setTitle(title));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    private String getMyIp() throws UnknownHostException {
        InetAddress addr = InetAddress.getLocalHost();
        return !"127.0.0.1".equals(addr.getHostAddress()) ? addr.getHostAddress() : "";
    }

    private void shutdownApplication() {
        if (scheduledExecutorService != null) {
            scheduledExecutorService.shutdownNow();
        }
        Platform.exit();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        shutdownApplication();
    }
}