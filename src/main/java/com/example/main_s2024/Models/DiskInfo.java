package com.example.main_s2024.Models;

import java.util.Date;

public class DiskInfo {
    private int id;
    private String diskModel;
    private int diskMaxCapacity;
    private int diskCurrentFreeSpace;
    private Date timestamp;

    public DiskInfo(String diskModel, int diskMaxCapacity, int diskCurrentFreeSpace) {
        this.diskModel = diskModel;
        this.diskMaxCapacity = diskMaxCapacity;
        this.diskCurrentFreeSpace = diskCurrentFreeSpace;
        this.timestamp = new Date();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDiskModel() {
        return diskModel;
    }

    public void setDiskModel(String diskModel) {
        this.diskModel = diskModel;
    }

    public int getDiskMaxCapacity() {
        return diskMaxCapacity;
    }

    public void setDiskMaxCapacity(int diskMaxCapacity) {
        this.diskMaxCapacity = diskMaxCapacity;
    }

    public int getDiskCurrentFreeSpace() {
        return diskCurrentFreeSpace;
    }

    public void setDiskCurrentFreeSpace(int diskCurrentFreeSpace) {
        this.diskCurrentFreeSpace = diskCurrentFreeSpace;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
