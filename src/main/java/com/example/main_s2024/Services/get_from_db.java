package com.example.main_s2024.Services;

import com.example.main_s2024.DbHandler.DbHandler;
import com.example.main_s2024.Models.*;
import com.google.gson.reflect.TypeToken;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONObject;


import java.io.IOException;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class get_from_db {

    private final OkHttpClient client = new OkHttpClient();
    private final String baseUrl = "http://localhost:8080/api/cpu-status";


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
        SystemLoad systemLoad = null;
        Response response = null;

        try {
            String url = baseUrl + "/system-load/get-by-id/" + id;

            Request request = new Request.Builder()
                    .url(url)
                    .build();

            response = client.newCall(request).execute();

            if (response.isSuccessful()) {
                ResponseBody responseBody = response.body();
                if (responseBody != null) {
                    JSONObject jsonObject = new JSONObject(responseBody.string());

                    int apiId = jsonObject.getInt("id");
                    float systemLoadValue = (float) jsonObject.getDouble("systemLoad");
                    float memoryLoad = (float) jsonObject.getDouble("memoryLoad");
                    int batteryPercentage = jsonObject.getInt("batteryPercentage");
                    float downloadSpeed = (float) jsonObject.getDouble("downloadSpeed");
                    float uploadSpeed = (float) jsonObject.getDouble("uploadSpeed");
                    String timestampString = jsonObject.getString("timestamp");

                    java.util.Date timestamp = new java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").parse(timestampString);

                    systemLoad = new SystemLoad(systemLoadValue, memoryLoad, batteryPercentage, downloadSpeed, uploadSpeed);
                    systemLoad.setTimestamp(timestamp);
                    systemLoad.setId(apiId);
                }
            } else {
                System.out.println("Request failed with code: " + ((Response) response).code());
            }
        } catch (IOException | org.json.JSONException | java.text.ParseException e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                response.close();
            }
        }

        return systemLoad;
    }

    public CpuInfo getCpuInfoById(int id) {
        CpuInfo cpuInfo = null;
        Response response = null;

        try {
            String url = baseUrl + "/cpu-info/get-by-id/" + id;

            Request request = new Request.Builder()
                    .url(url)
                    .build();

            response = client.newCall(request).execute();

            if (response.isSuccessful()) {
                ResponseBody responseBody = response.body();
                if (responseBody != null) {
                    JSONObject jsonObject = new JSONObject(responseBody.string());

                    int apiId = jsonObject.getInt("id");
                    String cpuModel = jsonObject.getString("cpuModel");
                    String cpuVersion = jsonObject.getString("cpuVersion");
                    float currentCpuLoad = (float) jsonObject.getDouble("currentCpuLoad");
                    String timestampString = jsonObject.getString("timestamp");

                    java.util.Date timestamp = java.util.Date.from(java.time.ZonedDateTime.parse(timestampString).toInstant());

                    cpuInfo = new CpuInfo(cpuModel, cpuVersion, currentCpuLoad);
                    cpuInfo.setId(apiId);
                    cpuInfo.setTimestamp(timestamp);
                }
            } else {
                System.out.println("Request failed with code: " + response.code());
            }
        } catch (IOException | org.json.JSONException e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                response.close();
            }
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
        BatteryInfo batteryInfo = null;
        Response response = null;

        try {
            String url = baseUrl + "/battery-info/get-by-id/" + id;

            Request request = new Request.Builder()
                    .url(url)
                    .build();

            response = client.newCall(request).execute();

            if (response.isSuccessful()) {
                ResponseBody responseBody = response.body();
                if (responseBody != null) {
                    JSONObject jsonObject = new JSONObject(responseBody.string());

                    int apiId = jsonObject.getInt("id");
                    String batteryModel = jsonObject.getString("batteryModel");
                    int maxAc = jsonObject.getInt("maxAc");
                    int currentLoad = jsonObject.getInt("currentLoad");
                    String timestampString = jsonObject.getString("timestamp");

                    java.util.Date timestamp = java.util.Date.from(java.time.ZonedDateTime.parse(timestampString).toInstant());

                    batteryInfo = new BatteryInfo(batteryModel, maxAc, currentLoad);
                    batteryInfo.setId(apiId);
                    batteryInfo.setTimestamp(timestamp);
                }
            } else {
                System.out.println("Request failed with code: " + response.code());
            }
        } catch (IOException | org.json.JSONException e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                response.close();
            }
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
        DiskInfo diskInfo = null;
        Response response = null;

        try {
            String url = baseUrl + "/disk-info/get-by-id/" + id;

            Request request = new Request.Builder()
                    .url(url)
                    .build();

            response = client.newCall(request).execute();

            if (response.isSuccessful()) {
                ResponseBody responseBody = response.body();
                if (responseBody != null) {
                    JSONObject jsonObject = new JSONObject(responseBody.string());
                    System.out.println(jsonObject + "I AM DYING");

                    int apiId = jsonObject.getInt("id");
                    String diskModel = jsonObject.getString("diskModel");
                    int diskMaxCapacity = jsonObject.getInt("diskMaxCapacity");
                    int diskCurrentFreeSpace = jsonObject.getInt("diskCurrentFreespace");
                    String timestampString = jsonObject.getString("timestamp");

                    java.util.Date timestamp = java.util.Date.from(java.time.ZonedDateTime.parse(timestampString).toInstant());

                    diskInfo = new DiskInfo(diskModel, diskMaxCapacity, diskCurrentFreeSpace);
                    diskInfo.setId(apiId);
                    diskInfo.setTimestamp(timestamp);
                }
            } else {
                System.out.println("Request failed with code: " + response.code());
            }
        } catch (IOException | org.json.JSONException e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                response.close();
            }
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
        SystemInfo systemInfo = null;
        Response response = null;

        try {
            String url = baseUrl + "/system-info/get-by-id/" + id;

            Request request = new Request.Builder()
                    .url(url)
                    .build();

            response = client.newCall(request).execute();

            if (response.isSuccessful()) {
                ResponseBody responseBody = response.body();
                if (responseBody != null) {
                    JSONObject jsonObject = new JSONObject(responseBody.string());

                    int apiId = jsonObject.getInt("id");
                    String windowsInfo = jsonObject.getString("windowsInfo");
                    String pcName = jsonObject.getString("pcName");
                    String pcBoardManufacturer = jsonObject.getString("pcBoardManufacturer");
                    String pcBoardVersion = jsonObject.getString("pcBoardVersion");
                    String pcBoardModel = jsonObject.getString("pcBoardModel");
                    String timestampString = jsonObject.getString("timestamp");

                    java.util.Date timestamp = java.util.Date.from(java.time.ZonedDateTime.parse(timestampString).toInstant());

                    systemInfo = new SystemInfo(windowsInfo, pcName, pcBoardManufacturer, pcBoardVersion, pcBoardModel);
                    systemInfo.setId(apiId);
                    systemInfo.setTimestamp(timestamp);
                }
            } else {
                System.out.println("Request failed with code: " + response.code());
            }
        } catch (IOException | org.json.JSONException e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                response.close();
            }
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
                String systemId = resultSet.getString("system_id");

                report = new Report_db(systemLoadId, batteryInfoId, cpuInfoId, diskInfoId, systemInfoId, systemId);
                report.setReportId(resultSet.getInt("report_id"));
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
                String systemId = resultSet.getString("system_id");


                Report_db report = new Report_db(systemLoadId, batteryInfoId, cpuInfoId, diskInfoId, systemInfoId, systemId);
                report.setReportId(resultSet.getInt("report_id"));

                reports.add(report);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbHandler.closeConnection(connection);
        }

        return reports;
    }

    public List<Report_db> getAllReportsById(String systemId) throws IOException {
        List<Report_db> reports = new ArrayList<>();

        Request request = new Request.Builder().url(baseUrl + "/report/get-all-by-id/" + systemId).build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected response code: " + response);
            }

            String responseBody = response.body().string();
            JSONArray jsonArray = new JSONArray(responseBody);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                int id = jsonObject.getInt("id");
                int systemLoad = jsonObject.getInt("systemLoad");
                int batteryInfo = jsonObject.getInt("batteryInfo");
                int cpuInfo = jsonObject.getInt("cpuInfo");
                int diskInfo = jsonObject.getInt("diskInfo");
                int systemInfo = jsonObject.getInt("systemInfo");

                Report_db report = new Report_db(systemLoad, batteryInfo, cpuInfo, diskInfo, systemInfo, systemId);
                reports.add(report);
            }
        }
        return reports;

    }
}
