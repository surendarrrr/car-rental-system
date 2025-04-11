package entity;

import java.util.Date;

public class Payment {
    private int paymentID;
    private int leaseID;
    private Date paymentDate;
    private double amount;

    // Default constructor
    public Payment() {
    }

    // Parameterized constructor
    public Payment(int paymentID, int leaseID, Date paymentDate, double amount) {
        this.paymentID = paymentID;
        this.leaseID = leaseID;
        this.paymentDate = paymentDate;
        this.amount = amount;
    }

    // Constructor without ID (for new payment creation)
    public Payment(int leaseID, Date paymentDate, double amount) {
        this.leaseID = leaseID;
        this.paymentDate = paymentDate;
        this.amount = amount;
    }

    // Getters and Setters
    public int getPaymentID() {
        return paymentID;
    }

    public void setPaymentID(int paymentID) {
        this.paymentID = paymentID;
    }

    public int getLeaseID() {
        return leaseID;
    }

    public void setLeaseID(int leaseID) {
        this.leaseID = leaseID;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "paymentID=" + paymentID +
                ", leaseID=" + leaseID +
                ", paymentDate=" + paymentDate +
                ", amount=" + amount +
                '}';
    }
}