/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projectjava;
/**
 * Transactional sub-module processing financial settlements and invoicing.
 * Validates transactional states and translates core booking totals into formalized currency formats.
 */
public class Payment {
    private double amount;
    private boolean isPaid;

    public Payment(double amount) {
        this.amount = amount;
        this.isPaid = false;
    }

    public Payment() {
    }
    
    

    public void processPayment() {
        if (!isPaid) {
            this.isPaid = true;
            System.out.println("Payment of " + amount + " has been processed.");
        } else {
            System.out.println("Payment was already processed.");
        }
    }

    public double getAmount() {
        return amount;
    }

    public boolean isPaid() {
        return isPaid;
    }
    
    @Override
public String toString() {
    return String.format("Amount: %.2f SAR", amount);
}
}
