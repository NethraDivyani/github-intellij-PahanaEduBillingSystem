package com.pahanaedu.billing.dao;
import com.pahanaedu.billing.model.Customer;

import com.pahanaedu.billing.model.Admin;
import com.pahanaedu.billing.util.DBConnection;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminDAO {

    public Admin authenticate(String username, String password) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DBConnection.getConnection(); // Use your DBConnection utility

            if (conn == null) {
                System.out.println("Database connection failed.");
                return null;
            }

            String query = "SELECT * FROM admin WHERE username = ?";
            stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            rs = stmt.executeQuery();

            if (rs.next()) {
                String hashedPassword = rs.getString("password");
                if (BCrypt.checkpw(password, hashedPassword)) {
                    Admin admin = new Admin();
                    admin.setAdminId(rs.getInt("admin_id"));
                    admin.setName(rs.getString("name"));
                    admin.setUsername(rs.getString("username"));
                    admin.setEmail(rs.getString("email"));
                    admin.setPasswordHash(hashedPassword); // Optional
                    return admin;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close resources safely
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return null; // Login failed
    }
    // Admin adds a customer
    public static boolean addCustomer(Customer customer) {
        boolean result = false;

        try (Connection connection = DBConnection.getConnection()) {
            String sql = "INSERT INTO customer (account_no, cust_name, address, telephone, email, password, status) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, customer.getAccountNo());
            statement.setString(2, customer.getCustName());
            statement.setString(3, customer.getAddress());
            statement.setString(4, customer.getTelephone());
            statement.setString(5, customer.getEmail());

            // Hashing the customer password before storing
            String hashedPassword = BCrypt.hashpw(customer.getPassword(), BCrypt.gensalt());
            statement.setString(6, hashedPassword);

            statement.setString(7, customer.getStatus());

            result = statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }
}






