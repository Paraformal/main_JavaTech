package com.example.main_s2024.Services;

import com.example.main_s2024.DbHandler.DbHandler;
import com.example.main_s2024.Models.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class get_from_db {

    public List<SystemLoad> getAllSystemLoads() {
        Connection connection = null;
        List<SystemLoad> systemLoads = new ArrayList<>();

        try {
            connection = DbHandler.openConnection();
            String query = "SELECT * FROM system_load WHERE id IS NOT NULL ORDER BY timestamp DESC";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                float systemLoadValue = resultSet.getFloat("system_load");
                float memoryLoad = resultSet.getFloat("memory_load");
                int batteryPercentage = resultSet.getInt("battery_percentage");
                float downloadSpeed = resultSet.getFloat("download_speed");
                float uploadSpeed = resultSet.getFloat("upload_speed");
                java.util.Date timestamp = resultSet.getTimestamp("timestamp") != null ?
                        new java.util.Date(resultSet.getTimestamp("timestamp").getTime()) : null;

                if (timestamp != null) {
                    SystemLoad systemLoad = new SystemLoad(systemLoadValue, memoryLoad, batteryPercentage, downloadSpeed, uploadSpeed);
                    systemLoad.setTimestamp(timestamp);
                    systemLoad.setId(resultSet.getInt("id"));
                    systemLoads.add(systemLoad);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbHandler.closeConnection(connection);
        }

        return systemLoads;
    }

    public SystemLoad getSystemLoadById(int id) {
        Connection connection = null;
        SystemLoad systemLoad = null;

        try {
            connection = DbHandler.openConnection();
            String query = "SELECT * FROM system_load WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id); // Set the id parameter in the SQL query
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                float systemLoadValue = resultSet.getFloat("system_load");
                float memoryLoad = resultSet.getFloat("memory_load");
                int batteryPercentage = resultSet.getInt("battery_percentage");
                float downloadSpeed = resultSet.getFloat("download_speed");
                float uploadSpeed = resultSet.getFloat("upload_speed");
                // Handle potential null timestamp
                java.util.Date timestamp = resultSet.getTimestamp("timestamp") != null ?
                        new java.util.Date(resultSet.getTimestamp("timestamp").getTime()) : null;

                // Check if timestamp is not null before creating a SystemLoad object
                if (timestamp != null) {
                    systemLoad = new SystemLoad(systemLoadValue, memoryLoad, batteryPercentage, downloadSpeed, uploadSpeed);
                    systemLoad.setTimestamp(timestamp);
                    systemLoad.setId(resultSet.getInt("id"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbHandler.closeConnection(connection);
        }

        return systemLoad;
    }


    public CpuInfo getCpuInfoById(int id) {
        Connection connection = null;
        CpuInfo cpuInfo = null;

        try {
            connection = DbHandler.openConnection();
            String query = "SELECT * FROM cpu_info WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String cpuModel = resultSet.getString("cpu_model");
                String cpuVersion = resultSet.getString("cpu_version");
                float currentCpuLoad = resultSet.getFloat("current_cpu_load");
                java.util.Date timestamp = resultSet.getTimestamp("timestamp") != null ?
                        new java.util.Date(resultSet.getTimestamp("timestamp").getTime()) : null;

                cpuInfo = new CpuInfo(cpuModel, cpuVersion, currentCpuLoad);
                cpuInfo.setId(resultSet.getInt("id"));
                if (timestamp != null) {
                    cpuInfo.setTimestamp(timestamp);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbHandler.closeConnection(connection);
        }

        return cpuInfo;
    }

    public List<CpuInfo> getAllCpuInfo() {
        Connection connection = null;
        List<CpuInfo> cpuInfoList = new ArrayList<>();

        try {
            connection = DbHandler.openConnection();
            String query = "SELECT * FROM cpu_info WHERE id IS NOT NULL ORDER BY timestamp DESC";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String cpuModel = resultSet.getString("cpu_model");
                String cpuVersion = resultSet.getString("cpu_version");
                float currentCpuLoad = resultSet.getFloat("current_cpu_load");
                java.util.Date timestamp = resultSet.getTimestamp("timestamp") != null ?
                        new java.util.Date(resultSet.getTimestamp("timestamp").getTime()) : null;

                CpuInfo cpuInfo = new CpuInfo(cpuModel, cpuVersion, currentCpuLoad);
                cpuInfo.setId(resultSet.getInt("id"));
                if (timestamp != null) {
                    cpuInfo.setTimestamp(timestamp);
                }
                cpuInfoList.add(cpuInfo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbHandler.closeConnection(connection);
        }

        return cpuInfoList;
    }

    public BatteryInfo getBatteryInfoById(int id) {
        Connection connection = null;
        BatteryInfo batteryInfo = null;

        try {
            connection = DbHandler.openConnection();
            String query = "SELECT * FROM battery_info WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String batteryModel = resultSet.getString("battery_model");
                int maxAc = resultSet.getInt("max_ac");
                int currentLoad = resultSet.getInt("current_load");
                java.util.Date timestamp = resultSet.getTimestamp("timestamp") != null ?
                        new java.util.Date(resultSet.getTimestamp("timestamp").getTime()) : null;

                batteryInfo = new BatteryInfo(batteryModel, maxAc, currentLoad);
                batteryInfo.setId(resultSet.getInt("id"));
                if (timestamp != null) {
                    batteryInfo.setTimestamp(timestamp);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbHandler.closeConnection(connection);
        }

        return batteryInfo;
    }

    public List<BatteryInfo> getAllBatteryInfo() {
        Connection connection = null;
        List<BatteryInfo> batteryInfoList = new ArrayList<>();

        try {
            connection = DbHandler.openConnection();
            String query = "SELECT * FROM battery_info WHERE id IS NOT NULL ORDER BY timestamp DESC";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String batteryModel = resultSet.getString("battery_model");
                int maxAc = resultSet.getInt("max_ac");
                int currentLoad = resultSet.getInt("current_load");
                java.util.Date timestamp = resultSet.getTimestamp("timestamp") != null ?
                        new java.util.Date(resultSet.getTimestamp("timestamp").getTime()) : null;

                BatteryInfo batteryInfo = new BatteryInfo(batteryModel, maxAc, currentLoad);
                batteryInfo.setId(resultSet.getInt("id"));
                if (timestamp != null) {
                    batteryInfo.setTimestamp(timestamp);
                }
                batteryInfoList.add(batteryInfo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbHandler.closeConnection(connection);
        }

        return batteryInfoList;
    }

    public DiskInfo getDiskInfoById(int id) {
        Connection connection = null;
        DiskInfo diskInfo = null;

        try {
            connection = DbHandler.openConnection();
            String query = "SELECT * FROM disk_info WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String diskModel = resultSet.getString("disk_model");
                int diskMaxCapacity = resultSet.getInt("disk_max_capacity");
                int diskCurrentFreeSpace = resultSet.getInt("disk_current_freeSpace");
                java.util.Date timestamp = resultSet.getTimestamp("timestamp") != null ?
                        new java.util.Date(resultSet.getTimestamp("timestamp").getTime()) : null;

                diskInfo = new DiskInfo(diskModel, diskMaxCapacity, diskCurrentFreeSpace);
                diskInfo.setId(resultSet.getInt("id"));
                if (timestamp != null) {
                    diskInfo.setTimestamp(timestamp);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbHandler.closeConnection(connection);
        }

        return diskInfo;
    }

    public List<DiskInfo> getAllDiskInfo() {
        Connection connection = null;
        List<DiskInfo> diskInfoList = new ArrayList<>();

        try {
            connection = DbHandler.openConnection();
            String query = "SELECT * FROM disk_info WHERE id IS NOT NULL ORDER BY timestamp DESC";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String diskModel = resultSet.getString("disk_model");
                int diskMaxCapacity = resultSet.getInt("disk_max_capacity");
                int diskCurrentFreeSpace = resultSet.getInt("disk_current_freeSpace");
                java.util.Date timestamp = resultSet.getTimestamp("timestamp") != null ?
                        new java.util.Date(resultSet.getTimestamp("timestamp").getTime()) : null;

                DiskInfo diskInfo = new DiskInfo(diskModel, diskMaxCapacity, diskCurrentFreeSpace);
                diskInfo.setId(resultSet.getInt("id"));
                if (timestamp != null) {
                    diskInfo.setTimestamp(timestamp);
                }
                diskInfoList.add(diskInfo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbHandler.closeConnection(connection);
        }

        return diskInfoList;
    }

    public SystemInfo getSystemInfoById(int id) {
        Connection connection = null;
        SystemInfo systemInfo = null;

        try {
            connection = DbHandler.openConnection();
            String query = "SELECT * FROM system_info WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String windowsInfo = resultSet.getString("windows_info");
                String pcName = resultSet.getString("pc_name");
                String pcBoardManufacturer = resultSet.getString("pc_board_manufacturer");
                String pcBoardVersion = resultSet.getString("pc_board_version");
                String pcBoardModel = resultSet.getString("pc_board_model");
                java.util.Date timestamp = resultSet.getTimestamp("timestamp") != null ?
                        new java.util.Date(resultSet.getTimestamp("timestamp").getTime()) : null;

                systemInfo = new SystemInfo(windowsInfo, pcName, pcBoardManufacturer, pcBoardVersion, pcBoardModel);
                systemInfo.setId(resultSet.getInt("id"));
                if (timestamp != null) {
                    systemInfo.setTimestamp(timestamp);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbHandler.closeConnection(connection);
        }

        return systemInfo;
    }

    public List<SystemInfo> getAllSystemInfo() {
        Connection connection = null;
        List<SystemInfo> systemInfoList = new ArrayList<>();

        try {
            connection = DbHandler.openConnection();
            String query = "SELECT * FROM system_info WHERE id IS NOT NULL ORDER BY timestamp DESC";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String windowsInfo = resultSet.getString("windows_info");
                String pcName = resultSet.getString("pc_name");
                String pcBoardManufacturer = resultSet.getString("pc_board_manufacturer");
                String pcBoardVersion = resultSet.getString("pc_board_version");
                String pcBoardModel = resultSet.getString("pc_board_model");
                java.util.Date timestamp = resultSet.getTimestamp("timestamp") != null ?
                        new java.util.Date(resultSet.getTimestamp("timestamp").getTime()) : null;

                SystemInfo systemInfo = new SystemInfo(windowsInfo, pcName, pcBoardManufacturer, pcBoardVersion, pcBoardModel);
                systemInfo.setId(resultSet.getInt("id"));
                if (timestamp != null) {
                    systemInfo.setTimestamp(timestamp);
                }
                systemInfoList.add(systemInfo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbHandler.closeConnection(connection);
        }

        return systemInfoList;
    }

    public Report_db getReportById(int reportId) {
        Report_db report = null;
        Connection connection = null;

        try {
            connection = DbHandler.openConnection();
            String query = "SELECT * FROM report WHERE report_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, reportId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int systemLoadId = resultSet.getInt("system_load_id");
                int batteryInfoId = resultSet.getInt("battery_info_id");
                int cpuInfoId = resultSet.getInt("cpu_info_id");
                int diskInfoId = resultSet.getInt("disk_info_id");
                int systemInfoId = resultSet.getInt("system_info_id");
                Date timeStamp = new Date(resultSet.getTimestamp("timestamp").getTime());

                report = new Report_db(systemLoadId, batteryInfoId, cpuInfoId, diskInfoId, systemInfoId);
                report.setReportId(resultSet.getInt("report_id"));
                report.setTimeStamp(timeStamp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbHandler.closeConnection(connection);
        }

        return report;
    }

    public List<Report_db> getAllReports() {
        List<Report_db> reports = new ArrayList<>();
        Connection connection = null;

        try {
            connection = DbHandler.openConnection();
            String query = "SELECT * FROM report";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int systemLoadId = resultSet.getInt("system_load_id");
                int batteryInfoId = resultSet.getInt("battery_info_id");
                int cpuInfoId = resultSet.getInt("cpu_info_id");
                int diskInfoId = resultSet.getInt("disk_info_id");
                int systemInfoId = resultSet.getInt("system_info_id");
                Date timeStamp = resultSet.getTimestamp("timestamp") != null
                        ? new Date(resultSet.getTimestamp("timestamp").getTime())
                        : null;

                Report_db report = new Report_db(systemLoadId, batteryInfoId, cpuInfoId, diskInfoId, systemInfoId);
                report.setReportId(resultSet.getInt("report_id"));
                if (timeStamp != null) {
                    report.setTimeStamp(timeStamp);
                }
                reports.add(report);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbHandler.closeConnection(connection);
        }

        return reports;
    }



}
