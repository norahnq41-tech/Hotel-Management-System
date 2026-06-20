/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projectjava;

/**
 * Represents a specialized auxiliary hotel service for fitness facility access.
 * Manages custom logic to calculate dynamic hourly fees, trainer premium rates,
 * and tracks individual guest utilization metrics.
 */
 public class Gym implements Hotel {
    private Guest guest;
    private int hoursUsed;
    private boolean withTrainer;
    private double hourlyRate = 100;
    private Payment payment;

    public Gym(Guest guest, int hoursUsed, boolean withTrainer) {
        this.guest = guest;
        this.hoursUsed = hoursUsed;
        this.withTrainer = withTrainer;
        this.payment = new Payment(calcTotal());
    }

    @Override
    public void book() {
        System.out.println("Gym booked for: " + guest.getName());
        System.out.println("Hours used: " + hoursUsed);
        System.out.println("With trainer: " + (withTrainer ? "Yes" : "No"));
        System.out.printf("Total price (with VAT): %.2f SAR%n", calcTotal());
        payment.processPayment();
    }

    @Override
    public double calcTotal() {
        double total = hoursUsed * hourlyRate;
        if (withTrainer) {
            total += 50 * hoursUsed;
        }
        return total + (total * Hotel.VAT);
    }

    @Override
public String toString() {
    return String.format("Gym for %s | Hours: %d | Trainer: %s | Total: %.2f SAR",
            guest.getName(), hoursUsed, withTrainer ? "Yes" : "No", calcTotal());
}
}