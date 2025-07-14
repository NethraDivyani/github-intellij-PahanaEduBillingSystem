package com.pahanaedu.billing.model;

public class Admin {
    private int adminId;
    private String username;
    private String password; // In production, store hashed passwords!
    private String name;
    private String email;

    // Constructor
    public Admin(int adminId, String username, String password, String name, String email) {
        this.adminId = adminId;
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    // Default constructor
    public Admin() {}

    // Getters and Setters
    public int getAdminId() {
        return adminId;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
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

    public void setPassword(String password) {
        this.password = password;
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

    // Example: Admin actions (you can expand these)
    public void manageCustomers() {
        System.out.println("Managing customers...");
    }

    public void manageItems() {
        System.out.println("Managing items...");
    }

    public void generateReport() {
        System.out.println("Generating report...");
    }

    @Override
    public String toString() {
        return "Admin{" +
                "adminId=" + adminId +
                ", username='" + username + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
