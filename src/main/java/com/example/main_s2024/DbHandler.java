package com.example.main_s2024;

import java.sql.*;
import io.github.cdimascio.dotenv.Dotenv;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DbHandler {
    private EnvLoader dotenv = new EnvLoader();
    private final String connectionString = EnvLoader.dotenv.get("DB_CONNECTION_STRING");
    private final String dbUsername = EnvLoader.dotenv.get("DB_USERNAME");
    private final String dbPassword = EnvLoader.dotenv.get("DB_PASSWORD");

    public String getInfo() {
        return "Connection String: " + connectionString + "\n" +
                "Username: " + dbUsername + "\n" +
                "Password: " + dbPassword;
    }

}
