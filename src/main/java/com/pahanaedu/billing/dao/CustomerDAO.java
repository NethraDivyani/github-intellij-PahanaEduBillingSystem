package com.pahanaedu.billing.dao;

import com.pahanaedu.billing.model.Customer;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Data Access Object (DAO) for Customer.
 * Handles database operations related to Customer entities.
 */
public class CustomerDAO {

    private final Connection connection;

    /**
     * Constructor accepts a JDBC connection.
     * Ideally, connection pooling should be used in production.
     *
     * @param connection JDBC database connection
     */
    public CustomerDAO(Connection connection) {
        this.connection = connection;
    }

    /**
     * Retrieve customer details by account number.
     *
     * @param accountNumber customer's account number (primary key)
     * @return Customer object if found, null otherwise
     * @throws SQLException if DB error occurs
     */
    public Customer getCustomerByAccountNumber(int accountNumber) throws SQLException {
        String sql = "SELECT accountNumber, name, address, telephone, unitsConsumed, dob, email FROM customer WHERE accountNumber = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, accountNumber);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToCustomer(rs);
                }
            }
        }
        return null;  // customer not found
    }

    /**
     * Save a new customer record to the database.
     * (For Cashier/Admin usage only)
     *
     * @param customer the customer to save
     * @throws SQLException in case of DB errors
     */
    public void addCustomer(Customer customer) throws SQLException {
        String sql = "INSERT INTO customer (accountNumber, name, address, telephone, unitsConsumed, dob, email) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, customer.getAccountNumber());
            ps.setString(2, customer.getName());
            ps.setString(3, customer.getAddress());
            ps.setString(4, customer.getTelephone());
            ps.setInt(5, customer.getUnitsConsumed());
            if (customer.getDob() != null) {
                ps.setDate(6, new java.sql.Date(customer.getDob().getTime()));  // convert java.util.Date to java.sql.Date
            } else {
                ps.setNull(6, Types.DATE);
            }
            ps.setString(7, customer.getEmail());
            ps.executeUpdate();
        }
    }

    /**
     * Update an existing customer record.
     * (For Cashier/Admin usage only)
     *
     * @param customer the customer with updated info
     * @throws SQLException in case of DB errors
     */
    public void updateCustomer(Customer customer) throws SQLException {
        String sql = "UPDATE customer SET name = ?, address = ?, telephone = ?, unitsConsumed = ?, dob = ?, email = ? WHERE accountNumber = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, customer.getName());
            ps.setString(2, customer.getAddress());
            ps.setString(3, customer.getTelephone());
            ps.setInt(4, customer.getUnitsConsumed());
            if (customer.getDob() != null) {
                ps.setDate(5, new java.sql.Date(customer.getDob().getTime()));
            } else {
                ps.setNull(5, Types.DATE);
            }
            ps.setString(6, customer.getEmail());
            ps.setInt(7, customer.getAccountNumber());
            ps.executeUpdate();
        }
    }

    /**
     * Delete a customer record by account number.
     * (Admin only operation)
     *
     * @param accountNumber customer account number
     * @throws SQLException in case of DB errors
     */
    public void deleteCustomer(int accountNumber) throws SQLException {
        String sql = "DELETE FROM customer WHERE accountNumber = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, accountNumber);
            ps.executeUpdate();
        }
    }

    /**
     * Get all customers.
     * (For Admin or Cashier to list all customers)
     *
     * @return List of customers
     * @throws SQLException in case of DB errors
     */
    public List<Customer> getAllCustomers() throws SQLException {
        List<Customer> customers = new ArrayList<>();
        String sql = "SELECT accountNumber, name, address, telephone, unitsConsumed, dob, email FROM customer";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Customer customer = mapResultSetToCustomer(rs);
                customers.add(customer);
            }
        }
        return customers;
    }

    /**
     * Map a ResultSet row to a Customer object.
     */
    private Customer mapResultSetToCustomer(ResultSet rs) throws SQLException {
        int accountNumber = rs.getInt("accountNumber");
        String name = rs.getString("name");
        String address = rs.getString("address");
        String telephone = rs.getString("telephone");
        int unitsConsumed = rs.getInt("unitsConsumed");
        Date dob = rs.getDate("dob");
        String email = rs.getString("email");
        return new Customer(accountNumber, name, address, telephone, unitsConsumed, dob, email);
    }
}
