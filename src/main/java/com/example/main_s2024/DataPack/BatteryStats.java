package com.example.main_s2024.DataPack;

import com.sun.jna.Native;
import com.sun.jna.Structure;
import com.sun.jna.win32.StdCallLibrary;

import java.util.ArrayList;
import java.util.List;


public class BatteryStats {

    private static BatteryStats ourInstance = new BatteryStats();

    private BatteryStats() {
    }

    public static BatteryStats getInstance() {
        return ourInstance;
    }

    public String[] getBatteryStats() {
        Kernel32.SYSTEM_POWER_STATUS batteryStatus = new Kernel32.SYSTEM_POWER_STATUS();
        Kernel32.INSTANCE.GetSystemPowerStatus(batteryStatus);
        setBatteryInModel(batteryStatus.toString().split("\n"));
        return batteryStatus.toString().split("\n");
    }

    private void setBatteryInModel(String[] battery) {
        if (!battery[1].replaceAll("[^0-9]", "").equals("")) {
            DynamicGeneralStats.getInstance().setBatteryPerc(battery[1].replaceAll("[^0-9]", ""));
        } else {
            DynamicGeneralStats.getInstance().setBatteryPerc("-1");
            DynamicGeneralStats.getInstance().setBattery(battery);
        }
    }

    public interface Kernel32 extends StdCallLibrary {

        Kernel32 INSTANCE = (Kernel32) Native.loadLibrary("Kernel32", Kernel32.class);

        int GetSystemPowerStatus(SYSTEM_POWER_STATUS result);

        class SYSTEM_POWER_STATUS extends Structure {
            public byte ACLineStatus;
            public byte BatteryFlag;
            public byte BatteryLifePercent;
            public byte Reserved1;
            public int BatteryLifeTime;
            public int BatteryFullLifeTime;

            @Override
            protected List<String> getFieldOrder() {
                ArrayList<String> fields = new ArrayList<String>();
                fields.add("ACLineStatus");
                fields.add("BatteryFlag");
                fields.add("BatteryLifePercent");
                fields.add("Reserved1");
                fields.add("BatteryLifeTime");
                fields.add("BatteryFullLifeTime");
                return fields;
            }


            public String getACLineStatusString() {
                switch (ACLineStatus) {
                    case (0):
                        return "Offline";
                    case (1):
                        return "Online";
                    default:
                        return "Unknown";
                }
            }


            public String getBatteryFlagString() {
                switch (BatteryFlag) {
                    case (1):
                        return "High, more than 66 percent";
                    case (2):
                        return "Low, less than 33 percent";
                    case (4):
                        return "Critical, less than five percent";
                    case (8):
                        return "Charging";
                    case ((byte) 128):
                        return "No system battery";
                    default:
                        return "Unknown";
                }
            }


            public String getBatteryLifePercent() {
                return (BatteryLifePercent == (byte) 255) ? "Unknown" : BatteryLifePercent + "%";
            }


            public String getBatteryLifeTime() {
                return (BatteryLifeTime == -1) ? "Unknown" : BatteryLifeTime + "";
            }


            public String getBatteryFullLifeTime() {
                return (BatteryFullLifeTime == -1) ? "Unknown" : BatteryFullLifeTime + "seconds";
            }

            @Override
            public String toString() {
                StringBuilder sb = new StringBuilder();
                sb.append("AC status: ").append(getACLineStatusString()).append("\n");
                sb.append("Battery percentage: ").append(getBatteryLifePercent()).append("\n");
                sb.append("Remaining battery: ").append(getHours(getBatteryLifeTime())).append("\n");
                return sb.toString();
            }

            private String getHours(String seconds) {
                if (seconds.equals("Unknown")) {
                    return seconds;
                } else {
                    Integer totalSecs = Integer.parseInt(seconds);
                    Integer hours = totalSecs / 3600;
                    Integer minutes = (totalSecs % 3600) / 60;

                    return String.format("%02d h %02d min", hours, minutes);
                }
            }
        }
    }

}