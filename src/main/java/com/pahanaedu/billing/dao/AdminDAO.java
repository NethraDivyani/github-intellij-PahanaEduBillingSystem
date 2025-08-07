package com.pahanaedu.billing.dao;

import com.pahanaedu.billing.model.Admin;
import com.pahanaedu.billing.model.Cashier;
import com.pahanaedu.billing.model.Customer;
import com.pahanaedu.billing.util.DBConnection;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * DAO class for Admin-related database operations,
 * including admin authentication, adding customers, and adding cashiers.
 */
public class AdminDAO {

    /**
     * Authenticate admin user by username and password.
     *
     * @param username the admin username to authenticate
     * @param password the raw password entered
     * @return Admin object if authentication succeeds, else null
     */
    public Admin authenticate(String username, String password) {
        if (username == null || username.trim().isEmpty() ||
                password == null || password.trim().isEmpty()) {
            return null;
        }

        String query = "SELECT * FROM admin WHERE username = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            if (conn == null) {
                System.err.println("Database connection failed.");
                return null;
            }

            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String hashedPassword = rs.getString("password");
                    if (BCrypt.checkpw(password, hashedPassword)) {
                        Admin admin = new Admin();
                        admin.setAdminId(rs.getInt("admin_id"));
                        admin.setName(rs.getString("name"));
                        admin.setUsername(rs.getString("username"));
                        admin.setEmail(rs.getString("email"));
                        // Do not expose password hash externally
                        return admin;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();  // Replace with logging in production
        }

        return null;
    }

    /**
     * Add a new customer to the database. Password is hashed before storing.
     *
     * @param customer the Customer object to add
     * @return true if inserted successfully, false otherwise
     */
    public boolean addCustomer(Customer customer) {
        if (customer == null ||
                customer.getPassword() == null || customer.getPassword().trim().isEmpty() ||
                customer.getAccountNo() == null || customer.getAccountNo().trim().isEmpty()) {
            return false;
        }

        String sql = "INSERT INTO customer (account_no, cust_name, address, telephone, email, password, status) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, customer.getAccountNo());
            statement.setString(2, customer.getCustName());
            statement.setString(3, customer.getAddress());
            statement.setString(4, customer.getTelephone());
            statement.setString(5, customer.getEmail());

            // Hash password before storing
            String hashedPassword = BCrypt.hashpw(customer.getPassword(), BCrypt.gensalt());
            statement.setString(6, hashedPassword);

            statement.setString(7, customer.getStatus());

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();  // Replace with logging as needed
        }
        return false;
    }

    /**
     * Add a new cashier user to the cashier table. Password is hashed before storing.
     *
     * @param cashier the Cashier object to add
     * @return true if inserted successfully, false otherwise
     */
    public static boolean addCashier(Cashier cashier) {
        boolean result = false;
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "INSERT INTO cashier (username, password, name, email, status) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, cashier.getUsername());

            // Hash password before storing
            String hashedPassword = BCrypt.hashpw(cashier.getPassword(), BCrypt.gensalt());
            stmt.setString(2, hashedPassword);

            stmt.setString(3, cashier.getName());
            stmt.setString(4, cashier.getEmail());
            stmt.setString(5, cashier.getStatus());

            result = stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace(); // Replace with your logging framework
        }
        return result;
    }
}
