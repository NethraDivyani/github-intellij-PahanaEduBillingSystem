package com.pahanaedu.billing.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/pahan_edu";
    private static final String JDBC_USERNAME = "root";
    private static final String JDBC_PASSWORD = "root";

    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD);
        } catch (Exception e) {
            System.out.println("Database connection failed: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}
