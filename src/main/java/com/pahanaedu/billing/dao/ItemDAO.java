package com.pahanaedu.billing.dao;

import com.pahanaedu.billing.model.Item;
import com.pahanaedu.billing.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItemDAO {

    // Create a new item record
    public boolean addItem(Item item) {
        String sql = "INSERT INTO item (name, description, price, quantityAvailable, category) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, item.getName());
            stmt.setString(2, item.getDescription());
            stmt.setDouble(3, item.getPrice());
            stmt.setInt(4, item.getQuantityAvailable());
            stmt.setString(5, item.getCategory());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Item getItemById(int itemId) {
        String sql = "SELECT * FROM item WHERE itemId = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, itemId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) return extractItemFromResultSet(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean updateItem(Item item) {
        String sql = "UPDATE item SET name=?, description=?, price=?, quantityAvailable=?, category=? WHERE itemId=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, item.getName());
            stmt.setString(2, item.getDescription());
            stmt.setDouble(3, item.getPrice());
            stmt.setInt(4, item.getQuantityAvailable());
            stmt.setString(5, item.getCategory());
            stmt.setInt(6, item.getItemId());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteItem(int itemId) {
        String sql = "DELETE FROM item WHERE itemId=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, itemId);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Item> getAllItems() {
        List<Item> items = new ArrayList<>();
        String sql = "SELECT * FROM item";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                items.add(extractItemFromResultSet(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return items;
    }

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
