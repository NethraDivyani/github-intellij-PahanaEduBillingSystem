package com.pahanaedu.billing.dao;

import com.pahanaedu.billing.model.Item;
import com.pahanaedu.billing.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItemDAO {
    private final String jdbcURL = "jdbc:mysql://localhost:3306/pahan_edu";
    private final String jdbcUsername = "root";
    private final String jdbcPassword = "root";

    public ItemDAO() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Load MySQL JDBC driver
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
    }

    // Create a new item record
    public boolean addItem(Item item) {
        String sql = "INSERT INTO item (name, description, price, quantityAvailable, category) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, item.getName());
            stmt.setString(2, item.getDescription());
            stmt.setDouble(3, item.getPrice());
            stmt.setInt(4, item.getQuantityAvailable());
            stmt.setString(5, item.getCategory());

            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Retrieve item by its ID
    public Item getItemById(int itemId) {
        String sql = "SELECT * FROM item WHERE itemId = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, itemId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return extractItemFromResultSet(rs);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Update existing item details (changed from static to instance method)
    public boolean updateItem(Item item) {
        String sql = "UPDATE item SET name = ?, description = ?, price = ?, quantityAvailable = ?, category = ? WHERE itemId = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, item.getName());
            stmt.setString(2, item.getDescription());
            stmt.setDouble(3, item.getPrice());
            stmt.setInt(4, item.getQuantityAvailable());
            stmt.setString(5, item.getCategory());
            stmt.setInt(6, item.getItemId());

            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Delete an item by ID
    public boolean deleteItem(int itemId) {
        try {
            Connection con = DBConnection.getConnection();
            String sql = "DELETE FROM item WHERE itemId=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, itemId);
            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    // Retrieve all items
    public List<Item> getAllItems() {
        List<Item> items = new ArrayList<>();
        String sql = "SELECT * FROM item";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Item item = extractItemFromResultSet(rs);
                items.add(item);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }

    // Helper method to convert ResultSet row into Item object
    private Item extractItemFromResultSet(ResultSet rs) throws SQLException {
        Item item = new Item();
        item.setItemId(rs.getInt("itemId"));
        item.setName(rs.getString("name"));
        item.setDescription(rs.getString("description"));
        item.setPrice(rs.getDouble("price"));
        item.setQuantityAvailable(rs.getInt("quantityAvailable"));
        item.setCategory(rs.getString("category"));
        return item;
    }
}
