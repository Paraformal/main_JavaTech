package com.example.main_s2024.DbHandler;

import java.sql.*;

import com.example.main_s2024.Utils.EnvLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DbHandler {
    private static final Logger logger = LoggerFactory.getLogger(DbHandler.class);
    private static final String connectionString = EnvLoader.dotenv.get("DB_CONNECTION_STRING");
    private static final String dbUsername = EnvLoader.dotenv.get("DB_USERNAME");
    private static final String dbPassword = EnvLoader.dotenv.get("DB_PASSWORD");

    private EnvLoader dotenv = new EnvLoader();

    public static Connection openConnection() throws SQLException {
        try {
            DriverManager.getConnection(connectionString, dbUsername, dbPassword);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return DriverManager.getConnection(connectionString, dbUsername, dbPassword);
    }

    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static String getInfo() {
        return "Connection String: " + connectionString + "\n" +
                "Username: " + dbUsername + "\n" +
                "Password: " + dbPassword;
    }
}
