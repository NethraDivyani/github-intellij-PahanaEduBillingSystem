package com.pahanaedu.billing.dao;

import com.pahanaedu.billing.model.Cashier;
import com.pahanaedu.billing.util.DBConnection;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CashierDAO {

    /**
     * Authenticate cashier by username and password.
     * @param username the cashier username
     * @param password the raw password input
     * @return Cashier object if authenticated; null otherwise
     */
    public Cashier authenticate(String username, String password) {
        String sql = "SELECT * FROM cashier WHERE username = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String storedHash = rs.getString("password");
                    if (BCrypt.checkpw(password, storedHash)) {
                        return new Cashier(
                                rs.getInt("cashier_id"),
                                rs.getString("username"),
                                storedHash,
                                rs.getString("name"),
                                rs.getString("email")
                        );
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // replace with proper logging
        }

        return null;  // authentication failed
    }
}
