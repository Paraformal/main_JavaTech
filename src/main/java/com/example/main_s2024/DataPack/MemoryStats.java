package com.example.main_s2024.DataPack;

import oshi.SystemInfo;
import oshi.software.os.OSFileStore;
import oshi.util.FormatUtil;

public class MemoryStats {
    private static MemoryStats ourInstance = new MemoryStats();

    private SystemInfo systemInfo = new SystemInfo();

    private MemoryStats() {
    }

    public static MemoryStats getInstance() {
        return ourInstance;
    }

    public String getRamMemory() {
        setRamMemoryInModel(
                GeneralStats.getInstance().round(
                        ((float) systemInfo.getHardware().getMemory().getAvailable() / (float) systemInfo.
                                getHardware().getMemory().getTotal()) * 100,
                        2)
        );
        return "Memory: " + FormatUtil.formatBytes(systemInfo.getHardware().getMemory().getAvailable()) + " free of "
                + FormatUtil.formatBytes(systemInfo.getHardware().getMemory().getTotal());
    }

    private void setRamMemoryInModel(Float ramMemoryInModel) {
        DynamicGeneralStats.getInstance().setFreeRam(ramMemoryInModel);
    }

    public String getFileSystems() {
        OSFileStore[] fsArray = systemInfo.getOperatingSystem().getFileSystem().getFileStores().toArray(new OSFileStore[0]);
        String[] numericSpace = new String[fsArray.length];
        String[] volume = new String[fsArray.length];
        String[] stringBuilder = new String[fsArray.length];
        for (int i = 0; i < fsArray.length; i++) {
            OSFileStore fs = fsArray[i];
            long usable = fs.getUsableSpace();
            long total = fs.getTotalSpace();
            stringBuilder[i] = (String.format(" %s (%s) [%s] %s free of %s (%.1f%%) " +
                            (fs.getLogicalVolume() != null && fs.getLogicalVolume().length() > 0 ? "[%s]" : "%s") +
                            "%n", fs.getName(),
                    fs.getDescription().isEmpty() ? "file system" : fs.getDescription(), fs.getType(),
                    FormatUtil.formatBytes(usable), FormatUtil.formatBytes(fs.getTotalSpace()), 100d * usable / total, fs.getLogicalVolume()));
            try {
                if (total == 0) {
                    volume[i] = fs.getMount();
                    numericSpace[i] = "0";
                } else {
                    volume[i] = fs.getMount();
                    numericSpace[i] = String.format("%.1f", (100d * usable / total)).replace(",", ".");
                }
            } catch (NumberFormatException e) {
                numericSpace[i] = "error";
            }
        }

        for (int i = 0; i < numericSpace.length; i++) {
            if (volume[i].equals("C:\\")) {
                String genericString = numericSpace[0];
                numericSpace[0] = numericSpace[i];
                numericSpace[i] = genericString;

                genericString = stringBuilder[0];
                stringBuilder[0] = stringBuilder[i];
                stringBuilder[i] = genericString;
            }
        }
        setAvailabeFileSystemInModel(numericSpace);
        return String.join("", stringBuilder);
    }

    private void setAvailabeFileSystemInModel(String[] availabeFileSystemInModel) {
        Float[] floats = new Float[availabeFileSystemInModel.length];
        for (int i = 0; i < availabeFileSystemInModel.length; i++) {
            floats[i] = Float.valueOf(availabeFileSystemInModel[i]);
        }
        DynamicGeneralStats.getInstance().setAvaibleFileSystem(floats);
    }
}
