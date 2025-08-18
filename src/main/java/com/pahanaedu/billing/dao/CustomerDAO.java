package com.pahanaedu.billing.dao;

import com.pahanaedu.billing.model.Customer;
import com.pahanaedu.billing.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO {

    // Add a new customer (called by cashier)
    public boolean addCustomer(Customer customer) {
        String sql = "INSERT INTO customer (account_no, cust_name, address, telephone, email, password, status) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, customer.getAccountNo());
            stmt.setString(2, customer.getCustName());
            stmt.setString(3, customer.getAddress());
            stmt.setString(4, customer.getTelephone());
            stmt.setString(5, customer.getEmail());
            stmt.setString(6, customer.getPassword());
            stmt.setString(7, customer.getStatus());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Fetch customer by account number
    public Customer getCustomerByAccountNo(String accountNo) {
        Customer customer = null;
        String sql = "SELECT * FROM customer WHERE account_no = ?";

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
                    customer.setPassword(rs.getString("password"));
                    customer.setStatus(rs.getString("status"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return customer;
    }

    // Fetch all customers
    public List<Customer> getAllCustomers() {
        List<Customer> customers = new ArrayList<>();
        String sql = "SELECT * FROM customer";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Customer customer = new Customer();
                customer.setAccountNo(rs.getString("account_no"));
                customer.setCustName(rs.getString("cust_name"));
                customer.setAddress(rs.getString("address"));
                customer.setTelephone(rs.getString("telephone"));
                customer.setEmail(rs.getString("email"));
                customer.setPassword(rs.getString("password"));
                customer.setStatus(rs.getString("status"));
                customers.add(customer);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return customers;
    }

}
