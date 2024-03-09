package com.example.main_s2024.DataPack;

import java.util.Observable;

import org.json.JSONException;
import org.json.JSONObject;
import javafx.application.Platform;

import java.util.Observer;


public class StaticGeneralStats extends Observable {
    private static StaticGeneralStats myInstance = new StaticGeneralStats();
    private boolean firtShow;
    private boolean isServerCreated;
    private String jsonStr;
    private String[] computerInfo;
    private String[] miscellaneous;
    private int port;
    private String bluetoothName;
    private String ipAddress;
    private boolean bluetoothServerCreated = false;
    private String systemInformation;
    private String[] cpuInfo;

    private StaticGeneralStats() {
    }

    public static StaticGeneralStats getInstance() {
        return myInstance;
    }

    public String[] getCpuInfo() {
        return cpuInfo;
    }

    void setCpuInfo(String[] cpuInfo) {
        this.cpuInfo = cpuInfo;
    }

    public String getSystemInformation() {
        return systemInformation;
    }

    void setSystemInformation(String systemInformation) {
        this.systemInformation = systemInformation;
    }

    public boolean isServerCreated() {
        return isServerCreated;
    }

    public void setServerCreated(boolean serverCreated) {
        isServerCreated = serverCreated;
    }

    public String getJsonStr() {
        return jsonStr;
    }

    public void setJsonStr(String jsonStr) throws JSONException {
        this.jsonStr = jsonStr;
        new JsonParser(jsonStr);
    }

    public String[] getComputerInfo() {
        return computerInfo;
    }

    public void setComputerInfo(String[] strings) {
        this.computerInfo = strings;
    }

    public String[] getMiscellaneous() {
        return miscellaneous;
    }

    public void setMiscellaneous(String[] strings) {
        miscellaneous = strings;
    }

    public void setFirstShow(boolean firstShow) {
        this.firtShow = firstShow;
    }

    public boolean isFirtShow() {
        return firtShow;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getBluetoothName() {
        return bluetoothName;
    }

    public void setBluetoothName(String bluetoothName) {
        this.bluetoothName = bluetoothName;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public boolean isBluetoothServerCreated() {
        return bluetoothServerCreated;
    }

    public void setBluetoothServerCreated(boolean bluetoothServerCreated) {
        this.bluetoothServerCreated = bluetoothServerCreated;
    }

    /**
     * add an observer to list
     *
     * @param observer view to add
     * @see Observer
     */
    public void addingObserver(Observer observer) {
        addObserver(observer);
    }

    /**
     * send notify to all observers
     *
     * @see Observable#notifyObservers()
     */
    public void notifyMyObservers() {

        Platform.runLater(() -> {
            setChanged();
            notifyObservers();
        });
    }
}
