package com.pahanaedu.billing.dao;

import com.pahanaedu.billing.model.Admin;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdminDAO {
    private final String jdbcURL = "jdbc:mysql://localhost:3306/pahan_edu";
    private final String jdbcUsername = "your_username"; // Set your DB username here
    private final String jdbcPassword = "your_password"; // Set your DB password here

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

    /**
     * Create a new Admin with hashed password.
     * Password is hashed before storing in DB.
     */
    public boolean addAdmin(Admin admin, String plainPassword) {
        String sql = "INSERT INTO admin (username, password, name, email, telephone) VALUES (?, ?, ?, ?, ?)";

        String hashedPw = BCrypt.hashpw(plainPassword, BCrypt.gensalt()); // hash pw

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, admin.getUsername());
            stmt.setString(2, hashedPw);
            stmt.setString(3, admin.getName());
            stmt.setString(4, admin.getEmail());
            stmt.setString(5, admin.getTelephone());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Authenticate admin by verifying stored hashed password
     */
    public Admin authenticate(String username, String plainPassword) {
        String sql = "SELECT * FROM admin WHERE username = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String storedHash = rs.getString("password");
                    if (storedHash != null && BCrypt.checkpw(plainPassword, storedHash)) {
                        return extractAdminFromResultSet(rs);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // authentication failed
    }

    public Admin getAdminById(int adminId) {
        String sql = "SELECT * FROM admin WHERE admin_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

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

    public boolean updateAdmin(Admin admin, String plainPassword) {
        // Update password only if plainPassword != null else keep old hash
        try (Connection conn = getConnection()) {
            if (plainPassword != null && !plainPassword.isEmpty()) {
                String sql = "UPDATE admin SET username = ?, password = ?, name = ?, email = ?, telephone = ? WHERE admin_id = ?";
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    String hashedPw = BCrypt.hashpw(plainPassword, BCrypt.gensalt());
                    stmt.setString(1, admin.getUsername());
                    stmt.setString(2, hashedPw);
                    stmt.setString(3, admin.getName());
                    stmt.setString(4, admin.getEmail());
                    stmt.setString(5, admin.getTelephone());
                    stmt.setInt(6, admin.getAdminId());
                    return stmt.executeUpdate() > 0;
                }
            } else {
                // No password change
                String sql = "UPDATE admin SET username = ?, name = ?, email = ?, telephone = ? WHERE admin_id = ?";
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setString(1, admin.getUsername());
                    stmt.setString(2, admin.getName());
                    stmt.setString(3, admin.getEmail());
                    stmt.setString(4, admin.getTelephone());
                    stmt.setInt(5, admin.getAdminId());
                    return stmt.executeUpdate() > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteAdmin(int adminId) {
        String sql = "DELETE FROM admin WHERE admin_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, adminId);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

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

    private Admin extractAdminFromResultSet(ResultSet rs) throws SQLException {
        Admin admin = new Admin();
        admin.setAdminId(rs.getInt("admin_id"));
        admin.setUsername(rs.getString("username"));
        admin.setPasswordHash(rs.getString("password"));
        admin.setName(rs.getString("name"));
        admin.setEmail(rs.getString("email"));
        admin.setTelephone(rs.getString("telephone"));
        admin.setCreatedAt(rs.getTimestamp("created_at"));
        return admin;
    }
}
