/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projectjava;

/**
 * Core transactional model responsible for managing the lifecycle of a hotel reservation.
 * It encapsulates guest data alongside an array of polymorphic Accommodation components,
 * aggregating financial sums and managing persistent booking states (confirmed/cancelled).
 */
public class Booking implements Hotel {

    private final int bookingID;
    private Guest guest;
    private Accommodation[] accommodation;
    private Payment payment;
    private String status;
    private static int numOfBooking;
    private int bookingId = generateId();

    public Booking(Guest guest, Accommodation[] accommodation) {
        this.bookingID = generateId();
        this.guest = guest;
        this.accommodation = accommodation;
        this.payment = new Payment(calcTotal()); 
        setStatus("New");
        numOfBooking++;
    }

    public Booking() {
        this(null, null);
    }

    private final int generateId() {
        int min = 1;
        int max = 1000;
        return (int) (Math.random() * ((max - min) + 1)) + min;
    }

    public Guest getGuest() {
        return guest;
    }

    public void setGuest(Guest guest) {
        this.guest = guest;
    }

    public Accommodation[] getAccommodation() {
        return accommodation;
    }

    public void setAccommodation(Accommodation[] accommodation) {
        this.accommodation = accommodation;
        this.payment = new Payment(calcTotal()); 
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public static int getNumOfBooking() {
        return numOfBooking;
    }

    public static void setNumOfBooking(int numOfBooking) {
        Booking.numOfBooking = numOfBooking;
    }

    public final void cancel() {
        setStatus("Cancelled");
        System.out.println("Booking has been cancelled.");
    }

    @Override
    public final void book() {
        setStatus("Confirmed");
        payment.processPayment(); 
        System.out.println("Booking has been confirmed and payment processed.");
    }

    @Override
    public double calcTotal() {
        double total = 0;
        for (Accommodation ac : accommodation) {
            total += ac.getPrice();
        }
        total += total * Hotel.VAT;
        return total;
    }

    public String accommodations() {
        String s = "";
        for (Accommodation ser : accommodation) {
            s += ser + "\n";
        }
        return s;
    }
    
   @Override
public String toString() {
    return String.format(
        "Booking ID: %d | Guest: %s | Rooms booked: %d | Total with VAT: %.2f SAR",
        bookingId, guest.getName(), accommodation.length, calcTotal()
    );
}

}
   

