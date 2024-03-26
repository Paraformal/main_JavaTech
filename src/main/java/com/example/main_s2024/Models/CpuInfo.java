package com.example.main_s2024.Models;

import java.util.Date;

public class CpuInfo {
    private int id;
    private String cpuModel;
    private String cpuVersion;
    private float currentCpuLoad;
    private Date timestamp;

    public CpuInfo(String cpuModel, String cpuVersion, float currentCpuLoad) {
        this.cpuModel = cpuModel;
        this.cpuVersion = cpuVersion;
        this.currentCpuLoad = currentCpuLoad;
        this.timestamp = new Date();
    }

    public CpuInfo() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCpuModel() {
        return cpuModel;
    }

    public void setCpuModel(String cpuModel) {
        this.cpuModel = cpuModel;
    }

    public String getCpuVersion() {
        return cpuVersion;
    }

    public void setCpuVersion(String cpuVersion) {
        this.cpuVersion = cpuVersion;
    }

    public float getCurrentCpuLoad() {
        return currentCpuLoad;
    }

    public void setCurrentCpuLoad(float currentCpuLoad) {
        this.currentCpuLoad = currentCpuLoad;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String toString() {
        return "CpuInfo{" +
                "id=" + id +
                ", cpuModel='" + cpuModel + '\'' +
                ", cpuVersion='" + cpuVersion + '\'' +
                ", currentCpuLoad=" + currentCpuLoad +
                ", timestamp=" + timestamp +
                '}';
    }
}
