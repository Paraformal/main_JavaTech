package com.example.main_s2024.Models;

import java.util.Date;

public class BatteryInfo {
    private int id;
    private String batteryModel;
    private int maxAc;
    private int currentLoad;
    private Date timestamp;

    public BatteryInfo(String batteryModel, int maxAc, int currentLoad) {
        this.batteryModel = batteryModel;
        this.maxAc = maxAc;
        this.currentLoad = currentLoad;
        this.timestamp = new Date();
    }

    public int getId() {
        return id;
    }

    private void setId(int id) {
        this.id = id;
    }

    public String getBatteryModel() {
        return batteryModel;
    }

    public void setBatteryModel(String batteryModel) {
        this.batteryModel = batteryModel;
    }

    public int getMaxAc() {
        return maxAc;
    }

    public void setMaxAc(int maxAc) {
        this.maxAc = maxAc;
    }

    public int getCurrentLoad() {
        return currentLoad;
    }

    public void setCurrentLoad(int currentLoad) {
        this.currentLoad = currentLoad;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }


}
