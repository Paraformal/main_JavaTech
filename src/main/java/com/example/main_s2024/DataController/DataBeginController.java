package com.example.main_s2024.DataController;

import com.example.main_s2024.DataPack.*;
import org.hyperic.sigar.Cpu;


import java.util.Arrays;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataBeginController {
    private static DataBeginController instance = new DataBeginController();
    private final AtomicLong counter = new AtomicLong();
    private ExecutorService getCpuInfo = Executors.newSingleThreadExecutor();
    private ExecutorService getNetworkSpeed = Executors.newSingleThreadExecutor();
    private DynamicGeneralStats sDgs = DynamicGeneralStats.getInstance();
    private StaticGeneralStats sSgs = StaticGeneralStats.getInstance();

    private DataBeginController() {
    }

    public static DataBeginController getInstance() {
        return instance;
    }

    public DataBegin getData() throws InterruptedException {
        final CountDownLatch latch = new CountDownLatch(3);

        String[][] cpuInfo = new String[1][6];
        System.out.println("CPU INFO: " + GeneralStats.getInstance().getCpuDetails());
        getCpuInfo.execute(() -> {
            try {
                cpuInfo[0] = GeneralStats.getInstance().getCpuDetails();
                if (cpuInfo[0] != null) {
                    cpuInfo[0][5] = "CPU load: " + CpuStats.getInstance().getCpuLoad() + "%";
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            latch.countDown();
        });

        String[] networkSpeed = {null};
        getNetworkSpeed.execute(() -> {
            networkSpeed[0] = "\n" + NetworkStats.getInstance().getNetworkSpeed();
            latch.countDown();
        });

        String[] batteryParts = BatteryStats.getInstance().getBatteryStats();
        latch.countDown();

        StringBuilder miscellaneousString = new StringBuilder();
        miscellaneousString.append(CpuStats.getInstance().getCpuLoad()).append("\n");
        miscellaneousString.append(MemoryStats.getInstance().getRamMemory()).append("\n");
        miscellaneousString.append(batteryParts[1]).append("\n");
        miscellaneousString.append(NetworkStats.getInstance().getNetworkSpeed());

        System.out.println("TESTI: " + miscellaneousString.toString());

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String ramMemory = MemoryStats.getInstance().getRamMemory() + "\n";
        String sb = (cpuInfo[0] != null && cpuInfo[0][5] != null ? cpuInfo[0][5] : "") + "\n" + ramMemory + (batteryParts != null && batteryParts.length > 1 ? batteryParts[1] : "") + "\n" + (networkSpeed[0] != null ? networkSpeed[0] : "");
        String[] miscellaneous = sb.split("\n");

        System.out.println("I AM NOT HERE:" + MemoryStats.getInstance().getFileSystems());

        String input = MemoryStats.getInstance().getFileSystems();

        Pattern pattern = Pattern.compile("(\\d+(\\.\\d+)?)");
        Matcher matcher = pattern.matcher(input);

        float space = 0f;
        if (matcher.find()) {
            try {
                space = Float.parseFloat(matcher.group(1));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }


        System.out.println("PERC THREAD: " + CpuStats.getInstance().getPercPerThreadStats());

        return new DataBegin(
                counter.incrementAndGet(),
                Arrays.toString(batteryParts),
                batteryParts,
                cpuInfo[0],
                MemoryStats.getInstance().getFileSystems(),
                GeneralStats.getInstance().getComputerSystemInfo(),
                miscellaneousString.toString(),
                space,
                CpuStats.getInstance().getCpuLoad().toString(),
                MemoryStats.getInstance().getRamMemory(),
                sDgs.getPercPerThread().toString(),
                sDgs.getBatteryPerc().toString()
        );

    }

}
