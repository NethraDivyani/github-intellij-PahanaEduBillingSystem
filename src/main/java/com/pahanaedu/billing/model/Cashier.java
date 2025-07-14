package com.pahanaedu.billing.model;

public class Cashier {
    private int cashierId;
    private String username;
    private String password; // In production, store hashed passwords!
    private String name;
    private String email;

    // Constructor with all fields
    public Cashier(int cashierId, String username, String password, String name, String email) {
        this.cashierId = cashierId;
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    // Default constructor (useful for frameworks or if you set properties later)
    public Cashier() {}

    // Getters and Setters for all fields
    public int getCashierId() {
        return cashierId;
    }

    public void setCashierId(int cashierId) {
        this.cashierId = cashierId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String string) { // Renamed parameter from 'password' to 'string' to avoid conflict if needed, or simply 'password' is fine
        this.password = string;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Example: Cashier-specific actions (you will implement these later with business logic)
    public void generateBill() {
        System.out.println("Cashier is generating a bill...");
        // This method would eventually call a BillingService to create a bill
    }

    public void processPayment() {
        System.out.println("Cashier is processing payment...");
        // This method would interact with payment handling logic
    }

    public void viewCustomerDetails() {
        System.out.println("Cashier is viewing customer details...");
        // This method would access CustomerDAO to retrieve customer info
    }

    @Override
    public String toString() {
        return "Cashier{" +
                "cashierId=" + cashierId +
                ", username='" + username + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
