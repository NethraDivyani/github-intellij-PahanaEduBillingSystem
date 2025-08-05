package com.pahanaedu.billing.model;

import java.util.Date;
import java.util.List;

public class Bill {
    private int billId;
    private Customer customer;
    private List<Item> items;
    private Date billDate;
    private double totalAmount;

    // Default constructor
    public Bill() {}

    // Parameterized constructor
    public Bill(int billId, Customer customer, List<Item> items, Date billDate) {
        this.billId = billId;
        this.customer = customer;
        this.items = items;
        this.billDate = billDate;
        calculateTotalAmount();
    }

    // Getters and Setters
    public int getBillId() {
        return billId;
    }
    public void setBillId(int billId) {
        this.billId = billId;
    }

    public Customer getCustomer() {
        return customer;
    }
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<Item> getItems() {
        return items;
    }
    public void setItems(List<Item> items) {
        this.items = items;
        calculateTotalAmount();
    }

    public Date getBillDate() {
        return billDate;
    }
    public void setBillDate(Date billDate) {
        this.billDate = billDate;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    // Calculate total amount based on items and their prices
    public void calculateTotalAmount() {
        if (items == null || items.isEmpty()) {
            totalAmount = 0;
            return;
        }
        double sum = 0;
        for (Item item : items) {
            sum += item.getPrice();
        }
        this.totalAmount = sum;
    }

    // Example method: print bill details
    public void printBillDetails() {
        System.out.println("Bill ID: " + billId);
        System.out.println("Date: " + billDate);
        System.out.println("Customer: " + (customer != null ? customer.getCustName(): "Unknown"));
        System.out.println("Items bought:");
        if (items != null) {
            for (Item item : items) {
                System.out.println(" - " + item.getName() + ": $" + item.getPrice());
            }
        }
        System.out.println("Total Amount: $" + totalAmount);
    }

    @Override
    public String toString() {
        return "Bill{" +
                "billId=" + billId +
                ", customer=" + (customer != null ? customer.getCustName() : "null") +
                ", billDate=" + billDate +
                ", totalAmount=" + totalAmount +
                '}';
    }
}
