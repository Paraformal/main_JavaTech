package com.example.main_s2024.Models;

public class Report_db {

    private int systemLoadId;
    private int batteryInfoId;
    private int cpuInfoId;
    private int diskInfoId;
    private int systemInfoId;

    public Report_db(int systemLoadId, int batteryInfoId, int cpuInfoId, int diskInfoId, int systemInfoId) {
        this.systemLoadId = systemLoadId;
        this.batteryInfoId = batteryInfoId;
        this.cpuInfoId = cpuInfoId;
        this.diskInfoId = diskInfoId;
        this.systemInfoId = systemInfoId;
    }

    public int getSystemLoadId() {
        return systemLoadId;
    }

    public void setSystemLoadId(int systemLoadId) {
        this.systemLoadId = systemLoadId;
    }

    public int getBatteryInfoId() {
        return batteryInfoId;
    }

    public void setBatteryInfoId(int batteryInfoId) {
        this.batteryInfoId = batteryInfoId;
    }

    public int getCpuInfoId() {
        return cpuInfoId;
    }

    public void setCpuInfoId(int cpuInfoId) {
        this.cpuInfoId = cpuInfoId;
    }

    public int getDiskInfoId() {
        return diskInfoId;
    }

    public void setDiskInfoId(int diskInfoId) {
        this.diskInfoId = diskInfoId;
    }

    public int getSystemInfoId() {
        return systemInfoId;
    }

    public void setSystemInfoId(int systemInfoId) {
        this.systemInfoId = systemInfoId;
    }
}
