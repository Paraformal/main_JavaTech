package com.example.main_s2024.DataPack;

import oshi.SystemInfo;
import oshi.software.os.OSProcess;
import oshi.software.os.OperatingSystem;
import oshi.util.FormatUtil;

import java.math.BigDecimal;

public class GeneralStats {
    private static GeneralStats myInstance = new GeneralStats();
    private SystemInfo systemInfo = new SystemInfo();

    private StringBuilder genericStringBuilder = new StringBuilder();

    private GeneralStats() {
    }

    public static GeneralStats getInstance() {
        return myInstance;
    }

    public static String getRamPerProcess() {
        OperatingSystem operatingSystem = new SystemInfo().getOperatingSystem();
        StringBuilder stringBuilder = new StringBuilder();
        for (OSProcess process : operatingSystem.getProcesses(5, OperatingSystem.ProcessSort.MEMORY)) {
            long rssBytes = process.getResidentSetSize();

            stringBuilder.append(process.getName()).append("\t").append(rssBytes).append("\n");
        }
        return stringBuilder.toString();
    }

    private void setCpuInfoInModel(String[] cpuInfo){
        StaticGeneralStats.getInstance().setCpuInfo(cpuInfo);
    }

    public String[] getCpuDetails() {
        String[] cpuDetails = new String[6];

        cpuDetails[0] = "Vendor: " + systemInfo.getHardware().getProcessor().getProcessorIdentifier().getName();
        cpuDetails[1] = systemInfo.getHardware().getProcessor().getProcessorIdentifier().getFamily();
        cpuDetails[2] = "Clock: " + FormatUtil.formatHertz(systemInfo.getHardware().getProcessor().getMaxFreq());
        cpuDetails[3] = "Physical CPU(s): " + systemInfo.getHardware().getProcessor().getPhysicalProcessorCount();
        cpuDetails[4] = "Logical CPU(s): " + systemInfo.getHardware().getProcessor().getLogicalProcessorCount();

         setCpuInfoInModel(cpuDetails);
        return cpuDetails;
    }

    Float round(float value, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Float.toString(value));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd.floatValue();
    }

    public String getComputerSystemInfo() {
        genericStringBuilder.setLength(0);
        genericStringBuilder.append(systemInfo.getOperatingSystem()).append("\n");
        genericStringBuilder.append(systemInfo.getHardware().getComputerSystem().getManufacturer()).append(" ").
                append(systemInfo.getHardware().getComputerSystem().getModel()).append("\n");
        genericStringBuilder.append("RAM: ").append(FormatUtil.formatBytes(systemInfo.getHardware().
                getMemory().getTotal())).append("\n\n");

        genericStringBuilder.append("Baseboard:").append("\n");
        genericStringBuilder.append("manufacturer: ").append(systemInfo.getHardware().getComputerSystem().getBaseboard()
                .getManufacturer()).append("\n");
        genericStringBuilder.append("     ").append("model: ").append(systemInfo.getHardware().getComputerSystem().
                getBaseboard().getModel()).append("\n");
        genericStringBuilder.append("     ").append("version: ").append(systemInfo.getHardware().getComputerSystem().
                getBaseboard().getVersion()).append("\n");

//        SingletonStaticGeneralStats.getInstance().setSystemInformation(genericStringBuilder.toString());

        return genericStringBuilder.toString();
    }

}
