package com.example.main_s2024.ViewsPack;

import com.example.main_s2024.Models.*;
import com.example.main_s2024.Services.get_from_db;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ReportDetailsView {
    @FXML
    private TabPane tabPane;

    @FXML
    private Label lblBatteryId, lblBatteryModel, lblBatteryCurrentLoad, lblBatteryTimeStamp;

    @FXML
    private Label lblCpuId,
            lblCpuModel,
            lblCpuVersion,
            lblCurrentCpuLoad,
            lblCpuTimestamp;


    @FXML
    private Label lblDiskId,
            lblDiskModel,
            lblDiskMaxCapacity,
            lblDiskCurrentFreeSpace,
            lblDiskTimestamp;

    @FXML
    private Label lblSystemId,
            lblWindowsInfo,
            lblPcName,
            lblPcBoardManufacturer,
            lblPcBoardVersion,
            lblPcBoardModel,
            lblSystemTimestamp;

    @FXML
    private Label lblLoadId,
            lblSystemLoad,
            lblMemoryLoad,
            lblBatteryPercentage,
            lblDownloadSpeed,
            lblUploadSpeed,
            lblSystemLoadTimestamp;


    private boolean isInitialized = false;
    private int report_id;
    private Report_db report_db;
    private get_from_db getFromDb;

    @FXML
    public void initialize() {
        isInitialized = true;
        updateUI();
    }

    public void setProjectId(int report_id) {
        System.out.println("Project ID FUCKING HERE: " + report_id);
        this.report_id = report_id;
        updateUI();
    }

    private void updateUI() {
        if (report_id > 0 && isInitialized) {
            report_db = getReportById(report_id);
            fillBatteryInfo(report_db.getBatteryInfoId());
            fillCpuInfo(report_db.getCpuInfoId());
            fillDiskInfo(report_db.getDiskInfoId());
            fillSystemInfo(report_db.getSystemInfoId());
            fillSystemLoad(report_db.getSystemLoadId());
        }
    }

    private BatteryInfo getBatteryInfoById(int batteryInfoId) {
        getFromDb = new get_from_db();
        BatteryInfo batteryInfo = new BatteryInfo();
        try {
            batteryInfo = getFromDb.getBatteryInfoById(batteryInfoId);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return batteryInfo;
    }

    private CpuInfo getCpuInfoById(int cpuInfoId) {
        getFromDb = new get_from_db();
        CpuInfo cpuInfo = new CpuInfo();

        try {
            cpuInfo = getFromDb.getCpuInfoById(cpuInfoId);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return cpuInfo;
    }

    private DiskInfo getDiskInfoById(int diskInfoId) {
        getFromDb = new get_from_db();
        DiskInfo diskInfo = new DiskInfo();

        try {
            diskInfo = getFromDb.getDiskInfoById(diskInfoId);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return diskInfo;
    }

    private SystemInfo getSystemInfoById(int systemInfoId) {
        getFromDb = new get_from_db();
        SystemInfo systemInfo = new SystemInfo();

        try {
            systemInfo = getFromDb.getSystemInfoById(systemInfoId);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return systemInfo;
    }

    private SystemLoad getSystemLoadById(int systemLoadId) {
        getFromDb = new get_from_db();
        SystemLoad systemLoad = new SystemLoad();

        try {
            systemLoad = getFromDb.getSystemLoadById(systemLoadId);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return systemLoad;
    }

    private Report_db getReportById(int report_id) {
        getFromDb = new get_from_db();
        Report_db report_db = new Report_db();

        try {
            report_db = getFromDb.getReportById(report_id);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return report_db;
    }

    private void fillBatteryInfo(int batteryInfoId) {
        BatteryInfo batteryInfo = getBatteryInfoById(batteryInfoId);

        lblBatteryId.setText(String.valueOf(batteryInfo.getId()));
        lblBatteryModel.setText(batteryInfo.getBatteryModel());
        lblBatteryCurrentLoad.setText(String.valueOf(batteryInfo.getCurrentLoad()));

        lblBatteryTimeStamp.setText(getDate(batteryInfo.getTimestamp()));
    }

    private void fillCpuInfo(int cpuInfoId) {
        CpuInfo cpuInfo = getCpuInfoById(cpuInfoId);

        lblCpuId.setText(String.valueOf(cpuInfo.getId()));
        lblCpuModel.setText(cpuInfo.getCpuModel());
        lblCpuVersion.setText(String.valueOf(cpuInfo.getCpuVersion()));
        lblCurrentCpuLoad.setText(String.valueOf(cpuInfo.getCurrentCpuLoad()));
        lblCpuTimestamp.setText(getDate(cpuInfo.getTimestamp()));
    }

    private void fillDiskInfo(int diskInfoId) {
        DiskInfo diskInfo = getDiskInfoById(diskInfoId);

        lblDiskId.setText(String.valueOf(diskInfo.getId()));
        lblDiskMaxCapacity.setText(String.valueOf(diskInfo.getDiskMaxCapacity()));
        lblDiskModel.setText(diskInfo.getDiskModel());
        lblDiskCurrentFreeSpace.setText(String.valueOf(diskInfo.getDiskCurrentFreeSpace()));
        lblDiskTimestamp.setText(getDate(diskInfo.getTimestamp()));
    }

    private void fillSystemInfo(int systemInfoId) {
        SystemInfo systemInfo = getSystemInfoById(systemInfoId);

        lblSystemId.setText(String.valueOf(systemInfo.getId()));
        lblWindowsInfo.setText(systemInfo.getWindowsInfo());
        lblPcName.setText(systemInfo.getPcName());
        lblPcBoardManufacturer.setText(systemInfo.getPcBoardManufacturer());
        lblPcBoardModel.setText(systemInfo.getPcBoardModel());
        lblPcBoardVersion.setText(String.valueOf(systemInfo.getPcBoardVersion()));
        lblSystemTimestamp.setText(getDate(systemInfo.getTimestamp()));
    }

    private void fillSystemLoad(int systemLoadId) {
        SystemLoad systemLoad = getSystemLoadById(systemLoadId);

        lblLoadId.setText(String.valueOf(systemLoad.getId()));
        lblSystemLoad.setText(String.valueOf(systemLoad.getSystemLoad()));
        lblMemoryLoad.setText(String.valueOf(systemLoad.getMemoryLoad()));
        lblBatteryPercentage.setText(String.valueOf(systemLoad.getBatteryPercentage()));
        lblDownloadSpeed.setText(String.valueOf(systemLoad.getDownloadSpeed()));
        lblUploadSpeed.setText(String.valueOf(systemLoad.getUploadSpeed()));
        lblSystemLoadTimestamp.setText(getDate(systemLoad.getTimestamp()));

    }

    private String getDate(Date date) {
        Date timestamp = date;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedTimestamp = formatter.format(timestamp);

        return formattedTimestamp;
    }
}
