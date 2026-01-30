package com.company.data;

import com.company.data.interfaces.IDB;

import java.sql.*;

public class PostgresDB implements IDB {

    private static PostgresDB instance;

    private String host;
    private String username;
    private String password;
    private String dbName;

    private Connection connection;

    private PostgresDB(String host, String username, String password, String dbName) {
        this.host = host;
        this.username = username;
        this.password = password;
        this.dbName = dbName;
    }

    public static PostgresDB getInstance(
            String host,
            String username,
            String password,
            String dbName
    ) {
        if (instance == null) {
            instance = new PostgresDB(host, username, password, dbName);
        }
        return instance;
    }

    @Override
    public Connection getConnection() {

        String url = host + "/" + dbName;

        try {
            if (connection != null && !connection.isClosed()) {
                return connection;
            }

            Class.forName("org.postgresql.Driver");

            connection = DriverManager.getConnection(
                    url,
                    username,
                    password
            );

            return connection;

        } catch (Exception e) {
            System.out.println("DB connection error: " + e.getMessage());
            return null;
        }
    }

    @Override
    public void close() {

        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                System.out.println("Close error: " + e.getMessage());
            }
        }
    }
}