package com.example.main_s2024;

import com.example.main_s2024.DataController.DataBegin;
import com.example.main_s2024.DataPack.MemoryStats;
import com.example.main_s2024.Graphs.LineGraphs;
import com.example.main_s2024.Graphs.MultiLineGraphs;
import com.example.main_s2024.Graphs.PieGraphs;
import com.example.main_s2024.Graphs.StackedAreaGraphs;
import com.example.main_s2024.Models.*;
import com.example.main_s2024.Services.add_to_db;
import com.example.main_s2024.Services.get_from_db;
import com.example.main_s2024.StageHandler.StageHandler;
import com.example.main_s2024.ViewsPack.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.StackedAreaChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;


public class Controller {

    private static final AtomicReference<String> systemId = new AtomicReference<>(null);
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
    private Button buttonSave;
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
    @FXML
    private Slider opacitySlider;
    @FXML
    private Button ButtonViewReports;
    @FXML
    private Button ButtonViewAdmin;
    @FXML
    private Label systemIdLbl;
    private Node systemLoadBox;
    private Node batteryBox;
    private Node cpuBox;
    private Node systemInfoBox;
    private Node disksBox;
    private Node settingsBox;
    private LineGraphs lineChartClass;
    private StackedAreaGraphs stackedAreaChartClass;
    private MultiLineGraphs multiLineGraphs;
    private String systemIdString;

    private DataBegin tempDataBegin;


    private static String getSystemId() {
        if (systemId.get() == null) {
            try {
                InetAddress localHost = InetAddress.getLocalHost();
                NetworkInterface network = NetworkInterface.getByInetAddress(localHost);
                byte[] mac = network.getHardwareAddress();

                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < mac.length; i++) {
                    sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
                }

                systemId.compareAndSet(null, sb.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return systemId.get();
    }

    @FXML
    private void initialize() {
        PieGraphs PieGraphs = new PieGraphs(pieChartDisk);
        lineChartClass = new LineGraphs(lineChartSystemLoad);
        multiLineGraphs = new MultiLineGraphs(lineChartPercPerThread);
        stackedAreaChartClass = new StackedAreaGraphs(stackedAreaChartBattery);

        systemLoadBox = mainVbox.getChildren().get(0);
        batteryBox = mainVbox.getChildren().get(1);
        cpuBox = mainVbox.getChildren().get(2);
        disksBox = mainVbox.getChildren().get(3);
        systemInfoBox = mainVbox.getChildren().get(4);
        settingsBox = mainVbox.getChildren().get(5);
        changeView(systemLoadBox);

        new SystemLoadView(systemLoadText, lineChartClass);
        new BatteryView(batteryText, stackedAreaChartClass);
        new CpuView(cpuText, multiLineGraphs);
        new DiskView(disksText, PieGraphs);
        new SystemInfoView(systemInfoText);
        new SettingsView(qrImageView, bluetoothInformation, openLibs, settingVBox);

        buttonSystemLoad.setOnAction(event -> changeView(systemLoadBox));
        buttonBattery.setOnAction(event -> changeView(batteryBox));
        buttonProcessor.setOnAction(event -> changeView(cpuBox));
        buttonHdd.setOnAction(event -> changeView(disksBox));
        buttonSystemInfo.setOnAction(event -> changeView(systemInfoBox));
        buttonSettings.setOnAction(event -> changeView(settingsBox));
        buttonSave.setOnAction(event -> {
            Stage loadingStage = showLoadingPopup();

            new Thread(() -> {
                try {
                    Report report = onExportToDbClicked(this.tempDataBegin);
                    add_to_db add_to_db = new add_to_db(report);
                    int systemLoadId = add_to_db.add_to_systemLoad();
                    int batteryInfoId = add_to_db.add_to_batteryInfo();
                    int cpuInfoId = add_to_db.add_to_cpuInfo();
                    int diskInfoId = add_to_db.add_to_diskInfo();
                    int sysetmInfoId = add_to_db.add_to_systemInfo();
                    String systemId = getSystemId();

                    Platform.runLater(() -> systemIdLbl.setText("System ID: " + systemId));
                    systemIdString = systemId;

                    Report_db report_db = new Report_db(systemLoadId, batteryInfoId, cpuInfoId, diskInfoId, sysetmInfoId, systemId);
                    add_to_db.add_report(report_db);

                    Thread.sleep(2000);

                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {

                    Platform.runLater(loadingStage::close);
                }
            }).start();
        });

        ButtonViewReports.setOnAction(event -> {
            try {
                Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                StageHandler stageHandler = new StageHandler(currentStage);

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/main_s2024/report_view.fxml"));
                Parent root = loader.load();

                ReportView reportController = loader.getController();

                reportController.setIsAdmin(false);

                stageHandler.openNewStage(root, "Reports", false,
                        false, reportController);


//                ReportView reportViewController = stageHandler.getLoader("/com/example/main_s2024/report_view.fxml").getController();
//
//                Platform.runLater(() -> reportViewController.setSystemId(this.systemIdString));


            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        ButtonViewAdmin.setOnAction(event -> {
            try {
                Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                StageHandler stageHandler = new StageHandler(currentStage);

                stageHandler.openNewStage("/com/example/main_s2024/access_view.fxml", "Admin", false,
                        false);


                AccessView accessView = stageHandler.getLoader("/com/example/main_s2024/access_view.fxml")
                        .getController();

                accessView.setOnLoginSuccessCallback((dummy) -> {
                    try {
                        stageHandler.openNewStage("/com/example/main_s2024/admin_view.fxml", "Admin", false,
                                false);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }

                });


                stageHandler.openNewStage("/com/example/main_s2024/report_view.fxml", "Reports", false,
                        false);

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void bindStageOpacity(Stage stage) {
        if (opacitySlider != null) {
            stage.opacityProperty().bind(opacitySlider.valueProperty());
        }
    }


    private void changeView(Node view) {
        mainVbox.getChildren().removeAll(systemInfoBox, batteryBox, cpuBox, disksBox, systemLoadBox, settingsBox);
        mainVbox.getChildren().add(view);
    }

    public void updateUI(DataBegin data) {
        Platform.runLater(() -> {
            this.tempDataBegin = data;
            systemLoadText.setText("System Load: " + data.getMiscellaneous());
            cpuText.setText("CPU Load: " + data.getNumericCpuLoad() + "%");
            batteryText.setText("Battery: " + data.getNumericBatteryPerc() + "%");
            disksText.setText("Disks: " + data.getDisks());
            systemInfoText.setText("Info: " + data.getComputerInfo());
            System.out.println("BROZIII: " + data.getComputerInfo());

            updateLineChartForCPULoad(data.getNumericCpuLoad());
            updateBatteryChart(data.getNumericBatteryPerc());
            updateCpuChart(data.getNumericPercPerThread());
            updateDiskChart(data.getDisks());

        });
    }

    private void updateLineChartForCPULoad(String cpuLoad) {
        try {
            float load = Float.parseFloat(cpuLoad);
            lineChartClass.addEntryLineChart(load);
        } catch (NumberFormatException e) {
            System.err.println("Error parsing CPU load: " + cpuLoad);
        }
    }

    private void updateBatteryChart(String batteryPerc) {
        try {
            int perc = Integer.parseInt(batteryPerc);
            stackedAreaChartClass.addEntryStackedAreaChart(perc);
        } catch (NumberFormatException e) {
            System.err.println("Error parsing battery percentage: " + batteryPerc);
        }
    }

    private void updateCpuChart(String cpuInfo) {
        try {
            String[] cpuInfoParts = cpuInfo.split(",");
            Float[] load = new Float[cpuInfoParts.length];
            for (int i = 0; i < cpuInfoParts.length; i++) {
                load[i] = Float.parseFloat(cpuInfoParts[i].trim());
            }
            multiLineGraphs.addEntryLineChart(load);
        } catch (NumberFormatException e) {
            System.err.println("Error parsing CPU load: " + cpuInfo);
        }
    }

    private void updateDiskChart(String diskInfo) {
        String[] parts = diskInfo.split(" ");

        if (parts.length >= 13) {
            try {
                float freeSpaceGiB = findFloat(parts, 7);
                float totalSpaceGiB = findFloat(parts, 11);

                float freeSpacePercentage = (freeSpaceGiB / totalSpaceGiB) * 100f;
                PieGraphs pieGraphs = new PieGraphs(pieChartDisk);

                pieGraphs.addEntryPieGraphs(new Float[]{freeSpacePercentage});


            } catch (NumberFormatException e) {
                System.err.println("Error parsing disk space information: " + e.getMessage());
                System.err.println("Original diskInfo: " + diskInfo);
                e.printStackTrace();
            }
        } else {
            System.err.println("Invalid diskInfo format. Expected at least 13 elements, got: " + parts.length);
        }
    }

    private float findFloat(String[] parts, int startIndex) throws NumberFormatException {
        for (int i = startIndex; i < parts.length; i++) {
            try {
                return Float.parseFloat(parts[i]);
            } catch (NumberFormatException e) {
            }
        }
        throw new NumberFormatException("Float value not found in diskInfo");
    }

    private Report onExportToDbClicked(DataBegin dataBegin) {
        String systemLoadData = "System Load: " + dataBegin.getMiscellaneous();

        float systemLoad = Float.parseFloat(systemLoadData.substring(systemLoadData.indexOf("System Load: ") + 13,
                systemLoadData.indexOf("\n")));
        String memoryLine = systemLoadData.substring(systemLoadData.indexOf("Memory: ") + 8,
                systemLoadData.indexOf(" GiB free"));
        float freeMemory = Float.parseFloat(memoryLine);
        float totalMemory = Float.parseFloat(systemLoadData.substring(systemLoadData.indexOf("free of ") + 8,
                systemLoadData.indexOf(" GiB\n")));
        float memoryLoad = totalMemory - freeMemory;
        int batteryPercentage = Integer.parseInt(systemLoadData.substring(systemLoadData.indexOf("Battery percentage: ")
                + 20, systemLoadData.indexOf("%")));
        float downloadSpeed = Float.parseFloat(systemLoadData.substring(systemLoadData.indexOf("download speed: ")
                + 16, systemLoadData.indexOf(" KB/s", systemLoadData.indexOf("download speed:"))));
        String uploadSpeedStr = systemLoadData.substring(systemLoadData.indexOf("upload speed: ") + "upload speed: ".length(),
                systemLoadData.indexOf(" KB/s", systemLoadData.indexOf("upload speed: ")));

        uploadSpeedStr = uploadSpeedStr.replaceAll("[^\\d.]", "");

        float uploadSpeed = 0;
        try {
            uploadSpeed = Float.parseFloat(uploadSpeedStr);
        } catch (NumberFormatException e) {
            System.out.println("Invalid number format for upload speed: " + uploadSpeedStr);
        }


        SystemLoad systemLoadObject = new SystemLoad(systemLoad, memoryLoad, batteryPercentage, downloadSpeed, uploadSpeed);

        String cpuData = dataBegin.getNumericCpuLoad();
        CpuInfo cpuInfo = new CpuInfo("Intel", "i7", Float.parseFloat(cpuData));

        String diskData = dataBegin.getDisks();
        String diskModel = diskData.substring(0, diskData.indexOf(" ("));
        String capacityString = diskData.substring(diskData.indexOf("free of ") + 8, diskData.indexOf(" GiB ("));
        int diskMaxCapacity = (int) Math.round(Double.parseDouble(capacityString));
        String freeSpaceString = diskData.substring(diskData.indexOf("[NTFS] ") + 7, diskData.indexOf(" GiB free"));
        int diskCurrentFreeSpace = (int) Math.round(Double.parseDouble(freeSpaceString));

        DiskInfo diskInfo = new DiskInfo(diskModel, diskMaxCapacity, diskCurrentFreeSpace);

        String batteryData = dataBegin.getNumericBatteryPerc();
        int batteryPercentage2 = Integer.parseInt(batteryData);
        BatteryInfo batteryInfo = new BatteryInfo("Dell 0MGJN9", 65, batteryPercentage2);


        String systemInfoData = dataBegin.getComputerInfo();
        String windowsInfo = systemInfoData.substring(0, systemInfoData.indexOf("\n"));
        String pcName = systemInfoData.substring(systemInfoData.indexOf("\n") + 1,
                systemInfoData.indexOf("\n", systemInfoData.indexOf("\n") + 1));

        String baseboardData = systemInfoData.substring(systemInfoData.indexOf("Baseboard:"));
        String pcBoardManufacturer = baseboardData.substring(baseboardData.indexOf("manufacturer:") + 14,
                baseboardData.indexOf("\n", baseboardData.indexOf("manufacturer:")));
        String pcBoardModel = baseboardData.substring(baseboardData.indexOf("model:") + 7,
                baseboardData.indexOf("\n", baseboardData.indexOf("model:")));
        String pcBoardVersion = baseboardData.substring(baseboardData.indexOf("version:") + 9,
                baseboardData.indexOf("\n", baseboardData.indexOf("version:")));

        SystemInfo systemInfo = new SystemInfo(windowsInfo, pcName, pcBoardManufacturer, pcBoardVersion, pcBoardModel);

        Report report = new Report(systemLoadObject, batteryInfo, cpuInfo, diskInfo, systemInfo);
        return report;
    }

    private Stage showLoadingPopup() {
        Stage loadingStage = new Stage();
        loadingStage.initModality(Modality.APPLICATION_MODAL);
        loadingStage.setTitle("Loading");

        Label loadingLabel = new Label("Saving data, please wait...");
        ProgressBar progressBar = new ProgressBar();
        progressBar.setPrefWidth(300);

        VBox layout = new VBox(10);
        layout.getChildren().addAll(loadingLabel, progressBar);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout, 300, 100);
        scene.getStylesheets().add("/com/example/main_s2024/styles/loading-style.css");
        loadingStage.setScene(scene);

        loadingStage.show();

        new Thread(() -> {
            for (int i = 0; i <= 100; i++) {
                int finalI = i;
                Platform.runLater(() -> progressBar.setProgress(finalI / 100.0));
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }).start();

        return loadingStage;
    }

}
