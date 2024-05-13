package com.example.main_s2024.Services;

import com.example.main_s2024.DbHandler.DbHandler;
import com.example.main_s2024.Models.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class add_to_db {
    private Report report;

    public add_to_db(Report report) {
        this.report = report;
    }

    public int add_to_systemLoad() {
        int generatedId = -1;

        try (Connection connection = DbHandler.openConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "INSERT INTO system_load (system_load, memory_load, battery_percentage, download_speed, upload_speed) VALUES (?, ?, ?, ?, ?)",
                     Statement.RETURN_GENERATED_KEYS)) {

            SystemLoad systemLoad = report.getSystemLoad();

            preparedStatement.setFloat(1, systemLoad.getSystemLoad());
            preparedStatement.setFloat(2, systemLoad.getMemoryLoad());
            preparedStatement.setInt(3, systemLoad.getBatteryPercentage());
            preparedStatement.setFloat(4, systemLoad.getDownloadSpeed());
            preparedStatement.setFloat(5, systemLoad.getUploadSpeed());

            preparedStatement.executeUpdate();

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    generatedId = generatedKeys.getInt(1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return generatedId;
    }


    public int add_to_batteryInfo() {
        int generatedId = -1;

        try (Connection connection = DbHandler.openConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "INSERT INTO battery_info (battery_model, max_ac, current_load) VALUES (?, ?, ?)",
                     Statement.RETURN_GENERATED_KEYS)) {

            BatteryInfo batteryInfo = report.getBatteryInfo();

            preparedStatement.setString(1, batteryInfo.getBatteryModel());
            preparedStatement.setInt(2, batteryInfo.getMaxAc());
            preparedStatement.setInt(3, batteryInfo.getCurrentLoad());

            preparedStatement.executeUpdate();

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    generatedId = generatedKeys.getInt(1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return generatedId;
    }


    public int add_to_cpuInfo() {
        int generatedId = -1;

        try (Connection connection = DbHandler.openConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "INSERT INTO cpu_info (cpu_model, cpu_version, current_cpu_load) VALUES (?, ?, ?)",
                     Statement.RETURN_GENERATED_KEYS)) {  // Indicate the intention to retrieve generated keys

            CpuInfo cpuInfo = report.getCpuInfo();

            preparedStatement.setString(1, cpuInfo.getCpuModel());
            preparedStatement.setString(2, cpuInfo.getCpuVersion());
            preparedStatement.setFloat(3, cpuInfo.getCurrentCpuLoad());

            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println(rowsAffected + " row(s) inserted into cpu_info table.");

            // Retrieve the generated keys
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    generatedId = generatedKeys.getInt(1);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return generatedId;
    }


    public int add_to_diskInfo() {
        int generatedId = -1;  // Default to -1 to indicate failure

        try (Connection connection = DbHandler.openConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "INSERT INTO disk_info (disk_model, disk_max_capacity, disk_current_freeSpace) VALUES (?, ?, ?)",
                     Statement.RETURN_GENERATED_KEYS)) {

            DiskInfo diskInfo = report.getDiskInfo();

            preparedStatement.setString(1, diskInfo.getDiskModel());
            preparedStatement.setInt(2, diskInfo.getDiskMaxCapacity());
            preparedStatement.setInt(3, diskInfo.getDiskCurrentFreeSpace());

            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println(rowsAffected + " row(s) inserted into disk_info table.");

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    generatedId = generatedKeys.getInt(1);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return generatedId;
    }


    public int add_to_systemInfo() {
        int generatedId = -1;

        try (Connection connection = DbHandler.openConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "INSERT INTO system_info (windows_info, pc_name, pc_board_manufacturer, pc_board_version, pc_board_model) VALUES (?, ?, ?, ?, ?)",
                     Statement.RETURN_GENERATED_KEYS)) {  // Specify you want to return the generated keys

            SystemInfo systemInfo = report.getSystemInfo();

            preparedStatement.setString(1, systemInfo.getWindowsInfo());
            preparedStatement.setString(2, systemInfo.getPcName());
            preparedStatement.setString(3, systemInfo.getPcBoardManufacturer());
            preparedStatement.setString(4, systemInfo.getPcBoardVersion());
            preparedStatement.setString(5, systemInfo.getPcBoardModel());

            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println(rowsAffected + " row(s) inserted into system_info table.");

            // Retrieve the generated key
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    generatedId = generatedKeys.getInt(1);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return generatedId;
    }


    public int add_report(Report_db report_db) {
        int generatedId = -1;

        try (Connection connection = DbHandler.openConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "INSERT INTO report (system_load_id, battery_info_id, cpu_info_id, disk_info_id, system_info_id, system_id) VALUES (?, ?, ?, ?, ?, ?)",
                     Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setInt(1, report_db.getSystemLoadId());
            preparedStatement.setInt(2, report_db.getBatteryInfoId());
            preparedStatement.setInt(3, report_db.getCpuInfoId());
            preparedStatement.setInt(4, report_db.getDiskInfoId());
            preparedStatement.setInt(5, report_db.getSystemInfoId());
            preparedStatement.setString(6, report_db.getSystemId());

            preparedStatement.executeUpdate();

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    generatedId = generatedKeys.getInt(1);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return generatedId;
    }


}
