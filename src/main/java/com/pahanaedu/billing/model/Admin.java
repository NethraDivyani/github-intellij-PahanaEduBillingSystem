package com.pahanaedu.billing.model;

<<<<<<< HEAD
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
=======
import java.io.Serializable;
import java.sql.Timestamp;

public class Admin implements Serializable {
    private static final long serialVersionUID = 1L;

    private int adminId;
    private String username;
    private String passwordHash; // Store only hashed password here
    private String name;
    private String email;

    public Admin() { }

    public Admin(int adminId, String username, String passwordHash, String name, String email, String telephone, Timestamp createdAt) {
        this.adminId = adminId;
        this.username = username;
        this.passwordHash = passwordHash;
>>>>>>> bf992412cd3a6a01e28f6e8aa90f3d557278dde1
        this.name = name;
        this.email = email;
    }

<<<<<<< HEAD
    // Default constructor
    public Admin() {}

    // Getters and Setters
=======
>>>>>>> bf992412cd3a6a01e28f6e8aa90f3d557278dde1
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

<<<<<<< HEAD
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
=======
    // Note: No getter for passwordHash to avoid exposing
    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getPasswordHash() {
        return passwordHash;
>>>>>>> bf992412cd3a6a01e28f6e8aa90f3d557278dde1
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

<<<<<<< HEAD
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

=======
>>>>>>> bf992412cd3a6a01e28f6e8aa90f3d557278dde1
    @Override
    public String toString() {
        return "Admin{" +
                "adminId=" + adminId +
                ", username='" + username + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
<<<<<<< HEAD
=======

    // Optionally, implement equals() and hashCode() here based on adminId or username

>>>>>>> bf992412cd3a6a01e28f6e8aa90f3d557278dde1
}
