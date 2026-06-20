 /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projectjava;

/**
 * Specialized class managing distinct auxiliary dining reservations for hotel guests.
 * Houses business logic restricting catering limits (e.g., maximum guest capacities) 
 * and automates cuisine-specific service charges.
 */
public class RestaurantBooking implements Hotel {
    private Guest guest;
    private int numberOfPeople;
    private String cuisineType;
    private Payment payment;

    private static final int MAX_CAPACITY = 40;

    public RestaurantBooking(Guest guest, int numberOfPeople, String cuisineType) {
        if (numberOfPeople > MAX_CAPACITY) {
            throw new IllegalArgumentException("Cannot book for more than " + MAX_CAPACITY + " people.");
        }

        this.guest = guest;
        this.numberOfPeople = numberOfPeople;
        this.cuisineType = cuisineType;
        this.payment = new Payment(150 * numberOfPeople); // 150 لكل شخص
    }

    @Override
    public void book() {
        System.out.println("Booking confirmed for " + guest.getName());
        System.out.println("Number of people: " + numberOfPeople);

        switch (cuisineType.trim().toLowerCase()) {
            case "italian":
                System.out.println("Cuisine selected: Italian");
                break;
            case "chinese":
                System.out.println("Cuisine selected: Chinese");
                break;
            case "lebanese":
                System.out.println("Cuisine selected: Lebanese");
                break;
            case "indian":
                System.out.println("Cuisine selected: Indian");
                break;
            default:
                System.out.println("Unknown cuisine type selected.");
                break;
        }

        System.out.printf("Total price (with VAT): %.2f SAR%n", calcTotal());
        payment.processPayment();
    }

    @Override
    public double calcTotal() {
        double total = payment.getAmount();
        total += total * Hotel.VAT;
        return total;
    }

    // Getters
    public Guest getGuest() {
        return guest;
    }

    public int getNumberOfPeople() {
        return numberOfPeople;
    }

    public String getCuisineType() {
        return cuisineType;
    }

    public Payment getPayment() {
        return payment;
    }

   @Override
public String toString() {
    return String.format("Restaurant for %s | People: %d | Cuisine: %s | Total: %.2f SAR",
            guest.getName(), numberOfPeople, cuisineType, calcTotal());
}
}
