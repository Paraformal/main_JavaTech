package com.example.main_s2024.Services;

import com.example.main_s2024.DbHandler.DbHandler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class delete_from_db {

    public void deleteReportById(int reportId) {
        Connection connection = null;

        try {
            connection = DbHandler.openConnection();
            String query = "DELETE FROM report WHERE report_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, reportId);
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Report deleted successfully.");
            } else {
                System.out.println("No report found with the given ID.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbHandler.closeConnection(connection);
        }
    }
}
