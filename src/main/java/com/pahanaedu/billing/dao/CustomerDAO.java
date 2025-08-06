package com.pahanaedu.billing.dao;

import com.pahanaedu.billing.model.Customer;
import com.pahanaedu.billing.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CustomerDAO {

    public Customer getCustomerByAccountNo(String accountNo) {
        Customer customer = null;
        String sql = "SELECT account_no, cust_name, address, telephone, email, password, status FROM customer WHERE account_no = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, accountNo);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    customer = new Customer();
                    customer.setAccountNo(rs.getString("account_no"));
                    customer.setCustName(rs.getString("cust_name"));
                    customer.setAddress(rs.getString("address"));
                    customer.setTelephone(rs.getString("telephone"));
                    customer.setEmail(rs.getString("email"));
                    customer.setPassword(rs.getString("password")); // Usually, you won't display password
                    customer.setStatus(rs.getString("status"));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return customer;
    }
}
