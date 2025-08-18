package com.pahanaedu.billing.dao;

import com.pahanaedu.billing.model.Admin;
import com.pahanaedu.billing.model.Cashier;
import com.pahanaedu.billing.model.Customer;
import com.pahanaedu.billing.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminDAO {

    // Authenticate admin
    public Admin authenticate(String username, String password) {
        if (username == null || username.trim().isEmpty() ||
                password == null || password.trim().isEmpty()) {
            return null;
        }

        String query = "SELECT * FROM admin WHERE username = ? AND password = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, username);
            stmt.setString(2, password);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Admin admin = new Admin();
                    admin.setAdminId(rs.getInt("admin_id"));
                    admin.setName(rs.getString("name"));
                    admin.setUsername(rs.getString("username"));
                    admin.setEmail(rs.getString("email"));
                    admin.setPassword(rs.getString("password"));
                    return admin;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    // Check if email already exists in admin or cashier tables
    public boolean emailExists(String email) {
        String[] tables = {"admin", "cashier"};
        try (Connection conn = DBConnection.getConnection()) {
            for (String table : tables) {
                String sql = "SELECT 1 FROM " + table + " WHERE email = ? LIMIT 1";
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setString(1, email);
                    try (ResultSet rs = stmt.executeQuery()) {
                        if (rs.next()) {
                            return true; // email exists
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Add a new customer
    public boolean addCustomer(Customer customer) {
        String sql = "INSERT INTO customer (account_no, cust_name, address, telephone, email, password, status) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, customer.getAccountNo());
            stmt.setString(2, customer.getCustName());
            stmt.setString(3, customer.getAddress());
            stmt.setString(4, customer.getTelephone());
            stmt.setString(5, customer.getEmail());
            stmt.setString(6, customer.getPassword());
            stmt.setString(7, customer.getStatus());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
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

    // Add a new admin
    public boolean addAdmin(Admin admin) {
        String sql = "INSERT INTO admin (username, password, name, email) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, admin.getUsername());
            stmt.setString(2, admin.getPassword());
            stmt.setString(3, admin.getName());
            stmt.setString(4, admin.getEmail());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
