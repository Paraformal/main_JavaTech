package com.example.main_s2024.DataController;

import com.example.main_s2024.DataPack.*;


import java.util.Arrays;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

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

    public DataBegin getData() {
        final CountDownLatch latch = new CountDownLatch(2);

        final String[] numericPercPerThread = new String[1];
        String[][] cpuInfo = {null};
        getCpuInfo.execute(() -> {
            cpuInfo[0] = sSgs.getCpuInfo();
            try {
                cpuInfo[0][5] = "CPU load: " + CpuStats.getInstance().getCpuLoad() + "%";
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            numericPercPerThread[0] = CpuStats.getInstance().getPercPerThreadStats();
            latch.countDown();
        });

        String[] networkSpeed = {null};
        getNetworkSpeed.execute(() -> {
            networkSpeed[0] = "\n" + NetworkStats.getInstance().getNetworkSpeed();
            latch.countDown();
        });

        String[] batteryParts = BatteryStats.getInstance().getBatteryStats();

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String ramMemory = MemoryStats.getInstance().getRamMemory() + "\n";
        String sb = cpuInfo[0][5] + "\n" + ramMemory + batteryParts[1] + "\n" + networkSpeed[0];
        String[] miscellaneous = sb.split("\n");

        return new DataBegin(counter.incrementAndGet(),
                Arrays.toString(batteryParts),
                batteryParts,
                cpuInfo[0],
                MemoryStats.getInstance().getFileSystems(),
                sSgs.getSystemInformation(),
                String.join("\n", miscellaneous),
                sDgs.getAvaibleFileSystem(),
                sDgs.getCpuLoad().toString(),
                sDgs.getFreeRam(),
                numericPercPerThread[0],
                sDgs.getBatteryPerc().toString()
        );
    }

    // Include other necessary methods...
}
