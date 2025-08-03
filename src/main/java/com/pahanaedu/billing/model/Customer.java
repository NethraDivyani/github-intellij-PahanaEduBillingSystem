package com.pahanaedu.billing.model;

import java.util.Date;

<<<<<<< HEAD
public class Customer {
    private int accountNumber;  // changed from String to int to match DAO
    private String name;
    private String address;
    private String telephone;
    private int unitsConsumed;
    private Date dob;
    private String email;

    // Constructor
=======
/**
 * Customer class with read-only access for customers.
 * Only getters provided, no public setters to prevent unauthorized modifications.
 * Data modification should be performed by cashier/admin through service/DAO layers.
 */
public class Customer {

    private final int accountNumber;  // immutable once set
    private final String name;
    private final String address;
    private final String telephone;
    private final int unitsConsumed;
    private final Date dob;
    private final String email;

    /**
     * Full constructor - all fields set during object creation.
     *
     * @param accountNumber unique account number
     * @param name customer name
     * @param address customer address
     * @param telephone customer phone number
     * @param unitsConsumed number of units consumed
     * @param dob date of birth
     * @param email customer email
     */
>>>>>>> bf992412cd3a6a01e28f6e8aa90f3d557278dde1
    public Customer(int accountNumber, String name, String address, String telephone, int unitsConsumed, Date dob, String email) {
        this.accountNumber = accountNumber;
        this.name = name;
        this.address = address;
        this.telephone = telephone;
        this.unitsConsumed = unitsConsumed;
<<<<<<< HEAD
        this.dob = dob;
        this.email = email;
    }

    // Default constructor
    public Customer() {}

    // Getters and Setters
=======
        this.dob = dob != null ? new Date(dob.getTime()) : null; // Defensive copy
        this.email = email;
    }

    // No default constructor to enforce setting all fields at creation

    // Getters only - no setters to ensure immutability for customer profiles

>>>>>>> bf992412cd3a6a01e28f6e8aa90f3d557278dde1
    public int getAccountNumber() {
        return accountNumber;
    }

<<<<<<< HEAD
    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }

=======
>>>>>>> bf992412cd3a6a01e28f6e8aa90f3d557278dde1
    public String getName() {
        return name;
    }

<<<<<<< HEAD
    public void setName(String name) {
        this.name = name;
    }

=======
>>>>>>> bf992412cd3a6a01e28f6e8aa90f3d557278dde1
    public String getAddress() {
        return address;
    }

<<<<<<< HEAD
    public void setAddress(String address) {
        this.address = address;
    }

=======
>>>>>>> bf992412cd3a6a01e28f6e8aa90f3d557278dde1
    public String getTelephone() {
        return telephone;
    }

<<<<<<< HEAD
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

=======
>>>>>>> bf992412cd3a6a01e28f6e8aa90f3d557278dde1
    public int getUnitsConsumed() {
        return unitsConsumed;
    }

<<<<<<< HEAD
    public void setUnitsConsumed(int unitsConsumed) {
        this.unitsConsumed = unitsConsumed;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
=======
    public Date getDob() {
        return dob != null ? new Date(dob.getTime()) : null; // Defensive copy
>>>>>>> bf992412cd3a6a01e28f6e8aa90f3d557278dde1
    }

    public String getEmail() {
        return email;
    }

<<<<<<< HEAD
    public void setEmail(String email) {
        this.email = email;
    }

    // toString method
=======
>>>>>>> bf992412cd3a6a01e28f6e8aa90f3d557278dde1
    @Override
    public String toString() {
        return "Customer{" +
                "accountNumber=" + accountNumber +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", telephone='" + telephone + '\'' +
                ", unitsConsumed=" + unitsConsumed +
                ", dob=" + dob +
                ", email='" + email + '\'' +
                '}';
    }
}
