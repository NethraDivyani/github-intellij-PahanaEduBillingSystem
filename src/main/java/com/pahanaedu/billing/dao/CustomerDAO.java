/*package com.pahanaedu.billing.dao;

import com.pahanaedu.billing.model.Customer;
import com.pahanaedu.billing.util.DBConnection;
import java.sql.*;

public class CustomerDAO {
    public boolean insertCustomer(Customer customer) {
        String sql = "INSERT INTO customer (account_no, cust_name, address, telephone, email, password, status) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, customer.getAccountNo());
            ps.setString(2, customer.getCustName());
            ps.setString(3, customer.getAddress());
            ps.setString(4, customer.getTelephone());
            ps.setString(5, customer.getEmail());
            ps.setString(6, customer.getPassword());
            ps.setString(7, customer.getStatus());

            int rows = ps.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
*/