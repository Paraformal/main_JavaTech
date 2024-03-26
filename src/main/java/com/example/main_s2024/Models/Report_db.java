package com.example.main_s2024.Models;

import java.util.Date;

public class Report_db {

    private int reportId;
    private int systemLoadId;
    private int batteryInfoId;
    private int cpuInfoId;
    private int diskInfoId;
    private int systemInfoId;
    private Date timeStamp;

    public Report_db(int systemLoadId, int batteryInfoId, int cpuInfoId, int diskInfoId, int systemInfoId) {
        this.systemLoadId = systemLoadId;
        this.batteryInfoId = batteryInfoId;
        this.cpuInfoId = cpuInfoId;
        this.diskInfoId = diskInfoId;
        this.systemInfoId = systemInfoId;
        this.timeStamp = new Date();
    }

    public Report_db() {
    }

    public int getReportId() {
        return reportId;
    }

    public void setReportId(int reportId) {
        this.reportId = reportId;
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

    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }
}
