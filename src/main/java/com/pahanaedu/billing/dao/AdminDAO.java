package com.pahanaedu.billing.dao;

import com.pahanaedu.billing.model.Admin;
<<<<<<< HEAD
=======
import org.mindrot.jbcrypt.BCrypt;
>>>>>>> bf992412cd3a6a01e28f6e8aa90f3d557278dde1

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdminDAO {
    private final String jdbcURL = "jdbc:mysql://localhost:3306/pahan_edu";
<<<<<<< HEAD
    private final String jdbcUsername = "your_username";
    private final String jdbcPassword = "your_password";

    public AdminDAO() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Load JDBC driver
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // Get DB connection
=======
    private final String jdbcUsername = "your_username"; // <-- REPLACE with your DB username
    private final String jdbcPassword = "your_password"; // <-- REPLACE with your DB password

    public AdminDAO() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace(); // Consider logging instead
        }
    }

>>>>>>> bf992412cd3a6a01e28f6e8aa90f3d557278dde1
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
    }

<<<<<<< HEAD
    // Add a new Admin
    public boolean addAdmin(Admin admin) {
        String sql = "INSERT INTO admin (username, password, name, email) VALUES (?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, admin.getUsername());
            stmt.setString(2, admin.getPassword());
            stmt.setString(3, admin.getName());
            stmt.setString(4, admin.getEmail());

            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Get Admin by ID
    public Admin getAdminById(int adminId) {
        String sql = "SELECT * FROM admin WHERE adminId = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, adminId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return extractAdminFromResultSet(rs);
=======
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
            e.printStackTrace(); // Consider logging instead
        }

        return false;
    }

    // Authenticate admin by username and password
    public Admin authenticate(String username, String plainPassword) {
        if (username == null || plainPassword == null || username.isEmpty() || plainPassword.isEmpty()) {
            return null; // early return if input invalid
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
>>>>>>> bf992412cd3a6a01e28f6e8aa90f3d557278dde1
                }
            }

        } catch (SQLException e) {
<<<<<<< HEAD
            e.printStackTrace();
        }
        return null;
    }

    // Update an existing Admin
    public boolean updateAdmin(Admin admin) {
        String sql = "UPDATE admin SET username = ?, password = ?, name = ?, email = ? WHERE adminId = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, admin.getUsername());
            stmt.setString(2, admin.getPassword());
            stmt.setString(3, admin.getName());
            stmt.setString(4, admin.getEmail());
            stmt.setInt(5, admin.getAdminId());

            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Delete an Admin by ID
    public boolean deleteAdmin(int adminId) {
        String sql = "DELETE FROM admin WHERE adminId = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, adminId);
            int rowsDeleted = stmt.executeUpdate();
            return rowsDeleted > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Get all Admins
    public List<Admin> getAllAdmins() {
        List<Admin> admins = new ArrayList<>();
        String sql = "SELECT * FROM admin";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Admin admin = extractAdminFromResultSet(rs);
                admins.add(admin);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return admins;
    }

    // Helper method to build Admin object from ResultSet
    private Admin extractAdminFromResultSet(ResultSet rs) throws SQLException {
        Admin admin = new Admin();
        admin.setAdminId(rs.getInt("adminId"));
        admin.setUsername(rs.getString("username"));
        admin.setPassword(rs.getString("password"));  // Note: passwords should be hashed in production
=======
            e.printStackTrace(); // Consider logging instead
        }

        return null;
    }

    private Admin extractAdminFromResultSet(ResultSet rs) throws SQLException {
        Admin admin = new Admin();
        admin.setAdminId(rs.getInt("admin_id"));
        admin.setUsername(rs.getString("username"));
        admin.setPasswordHash(rs.getString("password")); // Store hashed password in model, if needed
>>>>>>> bf992412cd3a6a01e28f6e8aa90f3d557278dde1
        admin.setName(rs.getString("name"));
        admin.setEmail(rs.getString("email"));
        return admin;
    }
<<<<<<< HEAD
=======

    public List<Admin> getAllAdmins() {
        List<Admin> admins = new ArrayList<>();
        String sql = "SELECT * FROM admin";

        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                admins.add(extractAdminFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Consider logging instead
        }

        return admins;
    }
>>>>>>> bf992412cd3a6a01e28f6e8aa90f3d557278dde1
}
