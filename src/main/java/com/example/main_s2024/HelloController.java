package com.example.main_s2024;

import com.example.main_s2024.DataController.DataBegin;
import com.example.main_s2024.DataController.DataBeginController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class HelloController extends Application {

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