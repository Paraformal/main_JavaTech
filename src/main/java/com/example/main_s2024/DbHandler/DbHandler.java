package com.example.main_s2024.DbHandler;

import java.sql.*;

import com.example.main_s2024.EnvLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DbHandler {
    private static final Logger logger = LoggerFactory.getLogger(DbHandler.class);
    private static final String connectionString = EnvLoader.dotenv.get("DB_CONNECTION_STRING");
    private static final String dbUsername = EnvLoader.dotenv.get("DB_USERNAME");
    private static final String dbPassword = EnvLoader.dotenv.get("DB_PASSWORD");

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            logger.info("Driver loaded");
        } catch (ClassNotFoundException e) {
            logger.error("Driver not found", e);
            System.exit(-1);
        }
    }

    private EnvLoader dotenv = new EnvLoader();

    public static Connection openConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(connectionString, dbUsername, dbPassword);
            logger.info("Database connection established");
        } catch (SQLException e) {
            logger.error("Could not establish database connection", e);
        }
        return connection;
    }

    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
                logger.info("Connection closed");
            } catch (SQLException e) {
                logger.error("Failed to close connection", e);
            }
        }
    }

    public static String getInfo() {
        return "Connection String: " + connectionString + "\n" +
                "Username: " + dbUsername + "\n" +
                "Password: " + dbPassword;
    }

}
