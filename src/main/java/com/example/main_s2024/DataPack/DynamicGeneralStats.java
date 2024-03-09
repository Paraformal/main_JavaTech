package com.example.main_s2024.DataPack;

public class DynamicGeneralStats {
    private static DynamicGeneralStats myInstance = new DynamicGeneralStats();
    private Float cpuLoad;
    private Float[] percPerThread;
    private Float freeRam;
    private Float[] avaibleFileSystem;
    private Integer batteryPerc;
    private String[] disks;
    private String[] battery;

    private DynamicGeneralStats() {
    }

    public static DynamicGeneralStats getInstance() {
        return myInstance;
    }

    public Float getCpuLoad() {
        return cpuLoad;
    }

    void setCpuLoad(Float cpuLoad) {
        this.cpuLoad = cpuLoad;
    }

    public String getFreeRam() {
        if (freeRam != null)
            return freeRam.toString();
        else
            return "";
    }

    void setFreeRam(Float freeRam) {
        this.freeRam = freeRam;
    }

    void setBatteryPerc(String batteryPerc) {
        this.batteryPerc = Integer.parseInt(batteryPerc);
    }

    public Integer getBatteryPerc() {
        return batteryPerc;
    }

    public Float[] getAvaibleFileSystem() {
        return avaibleFileSystem;
    }

    void setAvaibleFileSystem(Float[] avaibleFileSystem) {
        this.avaibleFileSystem = avaibleFileSystem;
    }

    void setPercPerThread(Float[] percPerThread) {
        this.percPerThread = percPerThread;
    }

    public Float[] getPercPerThread() {
        return percPerThread;
    }

    public String[] getDisks() {
        return disks;
    }

    public void setDisks(String[] disks) {
        this.disks = disks;
    }

    void setBattery(String[] battery){
        this.battery = battery;
    }

    public String[] getBattery() {
        return battery;
    }
}
