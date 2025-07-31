package com.pahanaedu.billing.model;

public class Item {
    private int itemId;
    private String name;
    private String description;
    private double price;
    private int quantityAvailable;
    private String category;

    // Default constructor
    public Item() {}

    // Parameterized constructor
    public Item(int itemId, String name, String description, double price, int quantityAvailable, String category) {
        this.itemId = itemId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantityAvailable = quantityAvailable;
        this.category = category;
    }

    // Getters and Setters
    public int getItemId() {
        return itemId;
    }
    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantityAvailable() {
        return quantityAvailable;
    }
    public void setQuantityAvailable(int quantityAvailable) {
        this.quantityAvailable = quantityAvailable;
    }

    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }

    // Example method: Update stock quantity
    public void updateStock(int quantity) {
        this.quantityAvailable += quantity;
        System.out.println("Stock updated. New quantity available: " + this.quantityAvailable);
    }

    // Example method: Display item info
    public void displayItemInfo() {
        System.out.println("Item ID: " + itemId + ", Name: " + name + ", Category: " + category +
                ", Price: " + price + ", Available Quantity: " + quantityAvailable);
    }

    @Override
    public String toString() {
        return "Item{" +
                "itemId=" + itemId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", quantityAvailable=" + quantityAvailable +
                ", category='" + category + '\'' +
                '}';
    }
}
