package com.pahanaedu.billing.dao;

import com.pahanaedu.billing.model.Customer;

import java.sql.*;
import java.util.ArrayList;
<<<<<<< HEAD
import java.util.List;

public class CustomerDAO {
    private final String jdbcURL = "jdbc:mysql://localhost:3306/pahan_edu";
    private final String jdbcUsername = "your_username";
    private final String jdbcPassword = "your_password";

    public CustomerDAO() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Load MySQL driver
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
    }

    public boolean addCustomer(Customer customer) {
        String sql = "INSERT INTO customer (name, dob, address, telephone, units_consumed, email) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, customer.getName());
            if (customer.getDob() != null) {
                stmt.setDate(2, new java.sql.Date(customer.getDob().getTime()));
            } else {
                stmt.setNull(2, Types.DATE);
            }
            stmt.setString(3, customer.getAddress());
            stmt.setString(4, customer.getTelephone());
            stmt.setInt(5, customer.getUnitsConsumed());
            stmt.setString(6, customer.getEmail());

            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Customer getCustomerById(int id) {
        String sql = "SELECT * FROM customer WHERE account_number = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return extractCustomerFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean updateCustomer(Customer customer) {
        String sql = "UPDATE customer SET name = ?, dob = ?, address = ?, telephone = ?, units_consumed = ?, email = ? WHERE account_number = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, customer.getName());
            if (customer.getDob() != null) {
                stmt.setDate(2, new java.sql.Date(customer.getDob().getTime()));
            } else {
                stmt.setNull(2, Types.DATE);
            }
            stmt.setString(3, customer.getAddress());
            stmt.setString(4, customer.getTelephone());
            stmt.setInt(5, customer.getUnitsConsumed());
            stmt.setString(6, customer.getEmail());
            stmt.setInt(7, customer.getAccountNumber());

            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteCustomer(int id) {
        String sql = "DELETE FROM customer WHERE account_number = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int rowsDeleted = stmt.executeUpdate();
            return rowsDeleted > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Customer> getAllCustomers() {
        List<Customer> customers = new ArrayList<>();
        String sql = "SELECT * FROM customer";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Customer customer = extractCustomerFromResultSet(rs);
                customers.add(customer);
            }

        } catch (SQLException e) {
            e.printStackTrace();
=======
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
>>>>>>> bf992412cd3a6a01e28f6e8aa90f3d557278dde1
        }
        return customers;
    }

<<<<<<< HEAD
    private Customer extractCustomerFromResultSet(ResultSet rs) throws SQLException {
        Customer customer = new Customer();
        customer.setAccountNumber(rs.getInt("account_number"));
        customer.setName(rs.getString("name"));
        Date dob = rs.getDate("dob");
        if (dob != null) {
            customer.setDob(new java.util.Date(dob.getTime()));
        }
        customer.setAddress(rs.getString("address"));
        customer.setTelephone(rs.getString("telephone"));
        customer.setUnitsConsumed(rs.getInt("units_consumed"));
        customer.setEmail(rs.getString("email"));
        return customer;
=======
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
>>>>>>> bf992412cd3a6a01e28f6e8aa90f3d557278dde1
    }
}
