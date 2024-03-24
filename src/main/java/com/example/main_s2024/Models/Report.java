package com.example.main_s2024.Models;

public class Report {
    private SystemLoad systemLoad;
    private BatteryInfo batteryInfo;
    private CpuInfo cpuInfo;
    private DiskInfo diskInfo;
    private SystemInfo systemInfo;

    public Report(SystemLoad systemLoad, BatteryInfo batteryInfo, CpuInfo cpuInfo, DiskInfo diskInfo,
                  SystemInfo systemInfo) {
        this.systemLoad = systemLoad;
        this.batteryInfo = batteryInfo;
        this.cpuInfo = cpuInfo;
        this.diskInfo = diskInfo;
        this.systemInfo = systemInfo;
    }

    public SystemLoad getSystemLoad() {
        return systemLoad;
    }

    public void setSystemLoad(SystemLoad systemLoad) {
        this.systemLoad = systemLoad;
    }

    public BatteryInfo getBatteryInfo() {
        return batteryInfo;
    }

    public void setBatteryInfo(BatteryInfo batteryInfo) {
        this.batteryInfo = batteryInfo;
    }

    public CpuInfo getCpuInfo() {
        return cpuInfo;
    }

    public void setCpuInfo(CpuInfo cpuInfo) {
        this.cpuInfo = cpuInfo;
    }

    public DiskInfo getDiskInfo() {
        return diskInfo;
    }

    public void setDiskInfo(DiskInfo diskInfo) {
        this.diskInfo = diskInfo;
    }

    public SystemInfo getSystemInfo() {
        return systemInfo;
    }

    public void setSystemInfo(SystemInfo systemInfo) {
        this.systemInfo = systemInfo;
    }
}
