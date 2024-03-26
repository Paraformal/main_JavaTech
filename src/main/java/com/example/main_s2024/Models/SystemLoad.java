package com.example.main_s2024.Models;

import java.util.Date;

public class SystemLoad {
    private int id;
    private float systemLoad;
    private float memoryLoad;
    private int batteryPercentage;
    private float downloadSpeed;
    private float uploadSpeed;
    private Date timestamp;

    public SystemLoad(float systemLoad, float memoryLoad, int batteryPercentage, float downloadSpeed,
                      float uploadSpeed) {
        this.systemLoad = systemLoad;
        this.memoryLoad = memoryLoad;
        this.batteryPercentage = batteryPercentage;
        this.downloadSpeed = downloadSpeed;
        this.uploadSpeed = uploadSpeed;
        this.timestamp = new Date();
    }

    public SystemLoad() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getSystemLoad() {
        return systemLoad;
    }

    public void setSystemLoad(float systemLoad) {
        this.systemLoad = systemLoad;
    }

    public float getMemoryLoad() {
        return memoryLoad;
    }

    public void setMemoryLoad(float memoryLoad) {
        this.memoryLoad = memoryLoad;
    }

    public int getBatteryPercentage() {
        return batteryPercentage;
    }

    public void setBatteryPercentage(int batteryPercentage) {
        this.batteryPercentage = batteryPercentage;
    }

    public float getUploadSpeed() {
        return uploadSpeed;
    }

    public void setUploadSpeed(float uploadSpeed) {
        this.uploadSpeed = uploadSpeed;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public float getDownloadSpeed() {
        return downloadSpeed;
    }

    public void setDownloadSpeed(float downloadSpeed) {
        this.downloadSpeed = downloadSpeed;
    }

    public String toString() {
        return "SystemLoad{" +
                "id=" + id +
                ", systemLoad=" + systemLoad +
                ", memoryLoad=" + memoryLoad +
                ", batteryPercentage=" + batteryPercentage +
                ", downloadSpeed=" + downloadSpeed +
                ", uploadSpeed=" + uploadSpeed +
                ", timestamp=" + timestamp +
                '}';
    }
}
