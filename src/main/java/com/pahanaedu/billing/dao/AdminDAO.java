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

    // Authenticate admin (plain text check)
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
    public static boolean emailExists(String email) {
        boolean exists = false;
        String[] tables = {"admin", "cashier"};

        try (Connection conn = DBConnection.getConnection()) {
            for (String table : tables) {
                String sql = "SELECT 1 FROM " + table + " WHERE email = ? LIMIT 1";
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setString(1, email);
                    try (ResultSet rs = stmt.executeQuery()) {
                        if (rs.next()) {
                            exists = true;
                            break;
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return exists;
    }

    // Add a new customer
    public boolean addCustomer(Customer customer) {
        String sql = "INSERT INTO customer (account_no, cust_name, address, telephone, email, password, status) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, customer.getAccountNo());
            statement.setString(2, customer.getCustName());
            statement.setString(3, customer.getAddress());
            statement.setString(4, customer.getTelephone());
            statement.setString(5, customer.getEmail());
            statement.setString(6, customer.getPassword()); // no hashing for customers
            statement.setString(7, customer.getStatus());

            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Add a new cashier (plain password)
    public static boolean addCashier(Cashier cashier) {
        boolean result = false;
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "INSERT INTO cashier (username, password, name, email, status) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, cashier.getUsername());
            stmt.setString(2, cashier.getPassword()); // store plain password
            stmt.setString(3, cashier.getName());
            stmt.setString(4, cashier.getEmail());
            stmt.setString(5, cashier.getStatus());

            result = stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }


    // Add new admin
    public boolean addAdmin(Admin admin) {
        String sql = "INSERT INTO admin (username, password, name, email) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, admin.getUsername());
            stmt.setString(2, admin.getPassword()); // no hashing for admin
            stmt.setString(3, admin.getName());
            stmt.setString(4, admin.getEmail());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
