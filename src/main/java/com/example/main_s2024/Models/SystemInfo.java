package com.example.main_s2024.Models;

import java.util.Date;

public class SystemInfo {
    private int id;
    private String windowsInfo;
    private String pcName;
    private String pcBoardManufacturer;
    private String pcBoardVersion;
    private String pcBoardModel;
    private Date timestamp;

    public SystemInfo(String windowsInfo, String pcName, String pcBoardManufacturer, String pcBoardVersion,
                      String pcBoardModel) {
        this.windowsInfo = windowsInfo;
        this.pcName = pcName;
        this.pcBoardManufacturer = pcBoardManufacturer;
        this.pcBoardVersion = pcBoardVersion;
        this.pcBoardModel = pcBoardModel;
        this.timestamp = new Date();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWindowsInfo() {
        return windowsInfo;
    }

    public void setWindowsInfo(String windowsInfo) {
        this.windowsInfo = windowsInfo;
    }

    public String getPcName() {
        return pcName;
    }

    public void setPcName(String pcName) {
        this.pcName = pcName;
    }

    public String getPcBoardManufacturer() {
        return pcBoardManufacturer;
    }

    public void setPcBoardManufacturer(String pcBoardManufacturer) {
        this.pcBoardManufacturer = pcBoardManufacturer;
    }

    public String getPcBoardVersion() {
        return pcBoardVersion;
    }

    public void setPcBoardVersion(String pcBoardVersion) {
        this.pcBoardVersion = pcBoardVersion;
    }

    public String getPcBoardModel() {
        return pcBoardModel;
    }

    public void setPcBoardModel(String pcBoardModel) {
        this.pcBoardModel = pcBoardModel;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
