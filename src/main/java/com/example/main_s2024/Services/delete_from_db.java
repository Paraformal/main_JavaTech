package com.example.main_s2024.Services;

import com.example.main_s2024.DbHandler.DbHandler;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class delete_from_db {

    private final OkHttpClient client = new OkHttpClient();
    private final String baseUrl = "http://localhost:8080/api/cpu-status";

    public void deleteReportById(int reportId) {
        Response response = null;

        try {
            String url = baseUrl + "/report/delete/report/" + reportId;

            Request request = new Request.Builder()
                    .url(url)
                    .delete()
                    .build();

            response = client.newCall(request).execute();

            if (response.isSuccessful()) {
                System.out.println("Report deleted successfully.");
            } else {
                System.out.println("Failed to delete report. HTTP status code: " + response.code());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                response.close();
            }
        }
    }
}
