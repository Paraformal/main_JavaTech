package com.example.main_s2024.DataPack;

import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import org.hyperic.sigar.CpuPerc;
import org.hyperic.sigar.SigarException;


public class CpuStats {
    private static CpuStats myInstance = new CpuStats();
    private StringBuilder genericStringBuilder = new StringBuilder();
    private CentralProcessor processor = new SystemInfo().getHardware().getProcessor();

    private CpuStats() {
    }

    public static CpuStats getInstance() {
        return myInstance;
    }

    public String getCpuLoad() throws InterruptedException {
        long[] oldTicks = processor.getSystemCpuLoadTicks();
        Thread.sleep(1000);
        double cpuLoad = processor.getSystemCpuLoadBetweenTicks(oldTicks) * 100;
        String roundedCpuLoad = GeneralStats.getInstance().round((float) cpuLoad, 2).toString();
        setCpuLoadInModel(Float.valueOf(roundedCpuLoad));
        return roundedCpuLoad;
    }

    public String getPercPerThreadStats() {
        SystemInfo si = new SystemInfo();
        CentralProcessor processor = si.getHardware().getProcessor();
        long[][] prevTicks = processor.getProcessorCpuLoadTicks();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
        double[] load = processor.getProcessorCpuLoadBetweenTicks(prevTicks);
        genericStringBuilder.setLength(0);
        for (double cpuLoad : load) {
            genericStringBuilder.append(GeneralStats.getInstance().round((float) (cpuLoad * 100), 2))
                    .append("\n");
        }
        setPercPerThreadInModel(genericStringBuilder.toString());
        return genericStringBuilder.toString();
    }

    private void setCpuLoadInModel(Float cpuLoad){
        DynamicGeneralStats.getInstance().setCpuLoad(cpuLoad);
        System.out.println("Iam fucked" + DynamicGeneralStats.getInstance().getCpuLoad());
    }

    private void setPercPerThreadInModel(String percPerThread){
        String[] tmpStr = percPerThread.split("\n");
        Float[] tmpFlo = new Float[tmpStr.length];
        for (int i = 0; i < tmpStr.length; i++) {
            tmpFlo[i] = Float.valueOf(tmpStr[i]);
        }
        DynamicGeneralStats.getInstance().setPercPerThread(tmpFlo);
    }


}
