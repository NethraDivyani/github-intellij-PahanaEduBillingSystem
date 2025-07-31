package com.pahanaedu.billing.model;

import java.util.Date;

public class Payment {
    private int paymentId;
    private Bill bill;
    private double amount;
    private Date paymentDate;
    private String paymentMethod; // e.g., Cash, Credit Card, Online
    private String status;        // e.g., Completed, Pending, Failed

    // Default constructor
    public Payment() {}

    // Parameterized constructor
    public Payment(int paymentId, Bill bill, double amount, Date paymentDate, String paymentMethod, String status) {
        this.paymentId = paymentId;
        this.bill = bill;
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.paymentMethod = paymentMethod;
        this.status = status;
    }

    // Getters and setters
    public int getPaymentId() {
        return paymentId;
    }
    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }

    public Bill getBill() {
        return bill;
    }
    public void setBill(Bill bill) {
        this.bill = bill;
    }

    public double getAmount() {
        return amount;
    }
    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }
    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }
    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    // Example method to print payment details
    public void printPaymentDetails() {
        System.out.println("Payment ID: " + paymentId);
        System.out.println("Bill ID: " + (bill != null ? bill.getBillId() : "N/A"));
        System.out.println("Amount: $" + amount);
        System.out.println("Date: " + paymentDate);
        System.out.println("Payment Method: " + paymentMethod);
        System.out.println("Status: " + status);
    }

    @Override
    public String toString() {
        return "Payment{" +
                "paymentId=" + paymentId +
                ", billId=" + (bill != null ? bill.getBillId() : "null") +
                ", amount=" + amount +
                ", paymentDate=" + paymentDate +
                ", paymentMethod='" + paymentMethod + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
