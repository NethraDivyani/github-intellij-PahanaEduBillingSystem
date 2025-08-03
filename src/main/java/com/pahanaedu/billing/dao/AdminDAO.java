package com.pahanaedu.billing.dao;

import com.pahanaedu.billing.model.Admin;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdminDAO {
    private final String jdbcURL = "jdbc:mysql://localhost:3306/pahan_edu";
    private final String jdbcUsername = "your_username"; // Replace with your DB username
    private final String jdbcPassword = "your_password"; // Replace with your DB password

    public AdminDAO() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Load JDBC driver
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
    }

    // Add new admin with hashed password
    public boolean addAdmin(Admin admin, String plainPassword) {
        String sql = "INSERT INTO admin (username, password, name, email) VALUES (?, ?, ?, ?)";
        String hashedPw = BCrypt.hashpw(plainPassword, BCrypt.gensalt());

        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, admin.getUsername());
            stmt.setString(2, hashedPw);
            stmt.setString(3, admin.getName());
            stmt.setString(4, admin.getEmail());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    // Authenticate admin by username and password
    public Admin authenticate(String username, String plainPassword) {
        if (username == null || plainPassword == null || username.isEmpty() || plainPassword.isEmpty()) {
            return null;
        }

        String sql = "SELECT * FROM admin WHERE username = ?";

        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String storedHash = rs.getString("password");
                    if (storedHash != null && BCrypt.checkpw(plainPassword, storedHash)) {
                        return extractAdminFromResultSet(rs);
                    } else {
                        System.out.println("Password does not match for user: " + username);
                    }
                } else {
                    System.out.println("No admin found with username: " + username);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    // Get admin by ID
    public Admin getAdminById(int adminId) {
        String sql = "SELECT * FROM admin WHERE admin_id = ?";

        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, adminId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return extractAdminFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    // Update an admin
    public boolean updateAdmin(Admin admin, String plainPassword) {
        String sql = "UPDATE admin SET username = ?, password = ?, name = ?, email = ? WHERE admin_id = ?";
        String hashedPw = BCrypt.hashpw(plainPassword, BCrypt.gensalt());

        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, admin.getUsername());
            stmt.setString(2, hashedPw);
            stmt.setString(3, admin.getName());
            stmt.setString(4, admin.getEmail());
            stmt.setInt(5, admin.getAdminId());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    // Delete admin
    public boolean deleteAdmin(int adminId) {
        String sql = "DELETE FROM admin WHERE admin_id = ?";

        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, adminId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    // Get all admins
    public List<Admin> getAllAdmins() {
        List<Admin> admins = new ArrayList<>();
        String sql = "SELECT * FROM admin";

        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                admins.add(extractAdminFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return admins;
    }

    // Helper: Build Admin object from ResultSet
    private Admin extractAdminFromResultSet(ResultSet rs) throws SQLException {
        Admin admin = new Admin();
        admin.setAdminId(rs.getInt("admin_id"));
        admin.setUsername(rs.getString("username"));
        admin.setPasswordHash(rs.getString("password")); // This should be used carefully
        admin.setName(rs.getString("name"));
        admin.setEmail(rs.getString("email"));
        return admin;
    }
}
