package com.pahanaedu.billing.model;

import java.util.ArrayList;
import java.util.List;

public class ItemCategory {
    private int categoryId;
    private String categoryName;
    private List<Item> items;  // Items belonging to this category

    // Default constructor
    public ItemCategory() {
        this.items = new ArrayList<>();
    }

    // Parameterized constructor
    public ItemCategory(int categoryId, String categoryName) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.items = new ArrayList<>();
    }

    // Getters and Setters
    public int getCategoryId() {
        return categoryId;
    }
    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public List<Item> getItems() {
        return items;
    }
    public void setItems(List<Item> items) {
        this.items = items;
    }

    // Add an item to this category
    public void addItem(Item item) {
        if (item != null && !items.contains(item)) {
            items.add(item);
            item.setCategory(categoryName);  // Optional: keep category updated in Item
        }
    }

    // Remove an item from this category
    public void removeItem(Item item) {
        if (item != null && items.contains(item)) {
            items.remove(item);
            item.setCategory(null);  // Optional: clear category in Item
        }
    }

    @Override
    public String toString() {
        return "ItemCategory{" +
                "categoryId=" + categoryId +
                ", categoryName='" + categoryName + '\'' +
                ", itemsCount=" + (items != null ? items.size() : 0) +
                '}';
    }
}
