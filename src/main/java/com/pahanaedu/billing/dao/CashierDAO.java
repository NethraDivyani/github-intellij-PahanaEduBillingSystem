package com.pahanaedu.billing.dao;

import com.pahanaedu.billing.model.Cashier;
import com.pahanaedu.billing.util.DBConnection;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CashierDAO {

    // Authenticate cashier by username and password
    public Cashier authenticate(String username, String password) {
        try (Connection conn = DBConnection.getConnection()) {
            if (conn == null) {
                System.out.println("Database connection failed.");
                return null;
            }

            String query = "SELECT * FROM cashier WHERE username = ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, username);

                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        String hashedPassword = rs.getString("password");
                        if (BCrypt.checkpw(password, hashedPassword)) {
                            Cashier cashier = new Cashier(
                                    rs.getInt("cashier_id"),
                                    rs.getString("username"),
                                    hashedPassword,
                                    rs.getString("name"),
                                    rs.getString("email")
                            );
                            return cashier;
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;  // Authentication failed
    }

    // Add cashier (similar to addCustomer in AdminDAO)
    public boolean addCashier(Cashier cashier) {
        boolean result = false;

        String sql = "INSERT INTO cashier (username, password, name, email) VALUES (?, ?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            String hashedPassword = BCrypt.hashpw(cashier.getPassword(), BCrypt.gensalt());

            stmt.setString(1, cashier.getUsername());
            stmt.setString(2, hashedPassword);
            stmt.setString(3, cashier.getName());
            stmt.setString(4, cashier.getEmail());

            result = stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }
}
