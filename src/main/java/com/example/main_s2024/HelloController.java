package com.example.main_s2024;

import com.example.main_s2024.DbHandler.DbHandler;

import com.example.main_s2024.StageHandler.StageHandler;
import com.example.main_s2024.Utils.requestHandler;
import javafx.fxml.FXML;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.hardware.CentralProcessor.ProcessorIdentifier;
import oshi.SystemInfo;
import org.hyperic.sigar.CpuPerc;
import org.hyperic.sigar.SigarException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class HelloController {

    private DbHandler connection;

    private StageHandler stageHandler;
    private StringBuilder genericStringBuilder = new StringBuilder();
    private CentralProcessor processor = new SystemInfo().getHardware().getProcessor();


    @FXML
    protected void onClick() {
    }

}