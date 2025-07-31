package com.pahanaedu.billing.model;

import java.util.Date;

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
    public Customer(int accountNumber, String name, String address, String telephone, int unitsConsumed, Date dob, String email) {
        this.accountNumber = accountNumber;
        this.name = name;
        this.address = address;
        this.telephone = telephone;
        this.unitsConsumed = unitsConsumed;
        this.dob = dob != null ? new Date(dob.getTime()) : null; // Defensive copy
        this.email = email;
    }

    // No default constructor to enforce setting all fields at creation

    // Getters only - no setters to ensure immutability for customer profiles

    public int getAccountNumber() {
        return accountNumber;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getTelephone() {
        return telephone;
    }

    public int getUnitsConsumed() {
        return unitsConsumed;
    }

    public Date getDob() {
        return dob != null ? new Date(dob.getTime()) : null; // Defensive copy
    }

    public String getEmail() {
        return email;
    }

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
