package com.pahanaedu.billing.dao;

import com.pahanaedu.billing.model.Cashier;
import com.pahanaedu.billing.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CashierDAO {

    public Cashier authenticate(String username, String password) {
        try (Connection conn = DBConnection.getConnection()) {
            if (conn == null) {
                System.out.println("Database connection failed.");
                return null;
            }

            String query = "SELECT * FROM cashier WHERE username = ? AND password = ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, username);
                stmt.setString(2, password); // plain password match

                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        return new Cashier(
                                rs.getInt("cashier_id"),
                                rs.getString("username"),
                                rs.getString("password"),
                                rs.getString("name"),
                                rs.getString("email")
                        );
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // login failed
    }

}
