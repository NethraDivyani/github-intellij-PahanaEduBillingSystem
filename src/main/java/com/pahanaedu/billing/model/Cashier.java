package com.pahanaedu.billing.model;

/**
 * Represents a Cashier user in the billing system.
 * Passwords should be securely hashed and managed in production.
 */
public class Cashier {
    private final int cashierId;  // id is immutable once set
    private String username;
    private String password; // hashed password
    private String name;
    private String email;
    private String status;

    public Cashier(int cashierId, String username, String password, String name, String email) {
        this.cashierId = cashierId;
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    public Cashier() {
        this.cashierId = 0;
    }

    // Getters
    public int getCashierId() {
        return cashierId;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    // Setters
    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
