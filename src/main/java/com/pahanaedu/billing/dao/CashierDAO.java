package com.pahanaedu.billing.dao;

import com.pahanaedu.billing.model.Cashier;
import com.pahanaedu.billing.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CashierDAO {

    //Cashier Authenticate
    public Cashier authenticate(String username, String password) {
        if (username == null || username.trim().isEmpty() ||
                password == null || password.trim().isEmpty()) {
            return null;
        }

        String query = "SELECT * FROM cashier WHERE username = ? AND password = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            if (conn == null) {
                System.out.println("Database connection failed.");
                return null;
            }

            stmt.setString(1, username);
            stmt.setString(2, password);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Cashier cashier = new Cashier(
                            rs.getInt("cashier_id"),
                            rs.getString("username"),
                            rs.getString("password"),
                            rs.getString("name"),
                            rs.getString("email")
                    );
                    cashier.setStatus(rs.getString("status"));
                    return cashier;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null; // login failed
    }

    // Add a new cashier
    public boolean addCashier(Cashier cashier) {
        String sql = "INSERT INTO cashier (username, password, name, email, status) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cashier.getUsername());
            stmt.setString(2, cashier.getPassword());
            stmt.setString(3, cashier.getName());
            stmt.setString(4, cashier.getEmail());
            stmt.setString(5, cashier.getStatus());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Check if email exists for cashier
    public boolean emailExists(String email) {
        String sql = "SELECT 1 FROM cashier WHERE email = ? LIMIT 1";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next(); // true if email exists
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
