package projectjava;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.InputMismatchException;

public class MainWithScanner {
    public static void main(String[] args) {
        // Initialize Scanner for user input and an ArrayList to maintain booking history
        Scanner input = new Scanner(System.in);
        ArrayList<Booking> allBookings = new ArrayList<>();

        System.out.println("===== Hotel Booking System =====");

        // Capture standard text input for guest registration
        System.out.print("Enter guest name: ");
        String name = input.nextLine();

        int ID = 0;
        while (true) {
            try {
                System.out.print("Enter guest ID (exactly 10 digits): ");
                String idInput = input.nextLine().trim();
                
                // Data Validation: Ensure the ID strictly consists of exactly 10 digits using Regular Expressions (Regex)
                if (!idInput.matches("\\d{10}")) {
                    throw new IllegalArgumentException("ID must be exactly 10 digits and numbers only.");
                }
                
                ID = Integer.parseInt(idInput);
                break; 
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage() + " Try again.");
            }
        }

        System.out.print("Enter guest email: ");
        String email = input.nextLine();

        String phoneNo = "";
        while (true) {
            try {
                System.out.print("Enter guest phone number (exactly 10 digits): ");
                phoneNo = input.nextLine().trim();
                
                // Flexible Validation: Accepts any 10-digit numeric format regardless of country code prefix
                if (!phoneNo.matches("\\d{10}")) {
                    throw new IllegalArgumentException("Phone number must be exactly 10 digits and numbers only.");
                }
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage() + " Try again.");
            }
        }

        // Instantiate the Guest object with validated information
        Guest guest = new Guest(name, ID, phoneNo, email);

        int count = 0;
        while (true) {
            try {
                System.out.print("How many accommodations do you want to book? ");
                count = input.nextInt();
                input.nextLine(); // Clear the buffer after reading an integer
                
                // Logical Validation: Prevent zero or negative accommodation counts
                if (count <= 0) {
                    throw new IllegalArgumentException("You must book at least 1 accommodation.");
                }
                break;
            } catch (InputMismatchException e) {
                System.out.println(" Error: Please enter a valid number.");
                input.nextLine(); // Flush invalid token out of the console buffer
            } catch (IllegalArgumentException e) {
                System.out.println("Error " + e.getMessage());
            }
        }

        // Dynamic array allocation based on user-defined accommodation count
        Accommodation[] accommodations = new Accommodation[count];

        // Loop to dynamically collect info and populate the polymorphic array
        for (int i = 0; i < count; i++) {
            System.out.println("\nAccommodation: " + (i + 1));
            int type = 0;
            
            while (true) {
                try {
                    System.out.print("Type (1 = SingleRoom, 2 = MultipleRoom, 3 = Suite): ");
                    type = input.nextInt();
                    input.nextLine();
                    
                    if (type < 1 || type > 3) {
                        throw new IllegalArgumentException("Invalid selection. Type must be 1, 2, or 3.");
                    }
                    break;
                } catch (InputMismatchException e) {
                    System.out.println(" Error: Enter a valid number for the type.");
                    input.nextLine();
                } catch (IllegalArgumentException e) {
                    System.out.println("Error " + e.getMessage());
                }
            }

            System.out.print("Description: ");
            String desc = input.nextLine();

            String size = "";
            while (true) {
                try {
                    System.out.print("Size (1 = Big, 2 = Small): ");
                    int sizeChoice = input.nextInt();
                    input.nextLine();
                    if (sizeChoice == 1) {
                        size = "Big";
                        break;
                    } else if (sizeChoice == 2) {
                        size = "Small";
                        break;
                    } else {
                        throw new IllegalArgumentException("Invalid selection. Size must be 1 for Big or 2 for Small.");
                    }
                } catch (InputMismatchException e) {
                    System.out.println(" Error: Enter a valid number (1 or 2).");
                    input.nextLine();
                } catch (IllegalArgumentException e) {
                    System.out.println("Error " + e.getMessage());
                }
            }

            try {
                // Polymorphic initialization: instantiating sub-classes under the Accommodation reference
                switch (type) {
                    case 1 -> {
                        System.out.print("Number of beds: ");
                        int beds = input.nextInt();
                        if (beds <= 0) throw new IllegalArgumentException("Beds count must be positive.");
                        
                        System.out.print("Do you want a Living Room? (true/false): ");
                        boolean hasLivingRoom = input.nextBoolean();
                        accommodations[i] = new SingleRoom(desc, size, 400.0, beds, hasLivingRoom);
                    }
                    case 2 -> {
                        input.nextLine();
                        System.out.print("Bed type (Large double bed / Single bed): ");
                        String bedType = input.nextLine();
                        System.out.print("Number of beds: ");
                        int beds = input.nextInt();
                        if (beds <= 0) throw new IllegalArgumentException("Beds count must be positive.");
                        
                        accommodations[i] = new MultipleRoom(desc, size, 700.0, beds, bedType);
                    }
                    case 3 -> {
                        System.out.print("Number of rooms in suite: ");
                        int rooms = input.nextInt();
                        if (rooms <= 0) throw new IllegalArgumentException("Rooms count must be positive.");
                        
                        int maxGuests;
                        do {
                            System.out.print("Max guests (max 6): ");
                            maxGuests = input.nextInt();
                            if (maxGuests > 6 || maxGuests <= 0) {
                                System.out.println("Guests number must be between 1 and 6, try again.");
                            }
                        } while (maxGuests > 6 || maxGuests <= 0);
                        
                        accommodations[i] = new Suite(desc, size, 900, rooms, maxGuests);

                        System.out.println("Complimentary Spa Services:");
                        // Downcasting reference to call a subclass-specific method
                        ((Suite) accommodations[i]).spaServiceType();
                    }
                }
                input.nextLine(); 
            } catch (InputMismatchException e) {
                System.out.println(" Error in room details input. Restarting current room entry...");
                input.nextLine();
                i--; // Decrement index loop counter to re-enter details for this specific room
            } catch (IllegalArgumentException e) {
                System.out.println(" Logical Error: " + e.getMessage() + " Restarting room entry...");
                i--;
            }
        }

        try {
            // Group the guest and their chosen accommodations into a core Booking entity
            Booking booking = new Booking(guest, accommodations);
            allBookings.add(booking);

            RestaurantBooking restaurant = null;
            Gym gym = null;
            double extrasTotal = 0;

            // Optional add-on: Restaurant Reservation Handler
            System.out.print("\nDo you want to book the restaurant? (yes/no): ");
            String restAns = input.nextLine().toLowerCase();
            if (restAns.equals("yes")) {
                System.out.print("Number of people: ");
                int people = input.nextInt();
                input.nextLine();
                System.out.print("Cuisine type (Italian, Chinese, Lebanese, Indian): ");
                String cuisine = input.nextLine();

                if (people > 40 || people <= 0) {
                    System.out.println("Cannot book for more than 40 people or less than 1. Restaurant booking skipped.");
                } else {
                    restaurant = new RestaurantBooking(guest, people, cuisine);
                    extrasTotal += restaurant.calcTotal();
                }
            }

            // Optional add-on: Gym Access Handler
            System.out.print("Do you want to book the gym? (yes/no): ");
            String gymAns = input.nextLine().toLowerCase();
            if (gymAns.equals("yes")) {
                System.out.print("Hours used: ");
                int hours = input.nextInt();
                if(hours <= 0) throw new IllegalArgumentException("Gym hours must be greater than 0.");
                
                System.out.print("With trainer? (true/false): ");
                boolean trainer = input.nextBoolean();
                input.nextLine();
                gym = new Gym(guest, hours, trainer);
                extrasTotal += gym.calcTotal();
            }

            System.out.print("\nDo you want to confirm the booking? (yes/no): ");
            String confirm = input.nextLine().toLowerCase();

            if (confirm.equals("yes")) {
                booking.book();
                double total = booking.calcTotal() + extrasTotal;

                // File I/O: Persistent storage handling to write booking invoice to an external text file
                try {
                    MyFileWriter writer = new MyFileWriter();
                    writer.openTextFile("bookings.txt");
                    writer.writeToFile("=== Booking Details ===");
                    writer.writeToFile(booking.toString());
                    for (Accommodation acc : accommodations) {
                        writer.writeToFile(acc.toString());
                    }
                    if (restaurant != null) writer.writeToFile(restaurant.toString());
                    if (gym != null) writer.writeToFile(gym.toString());
                    writer.writeToFile("Total: " + total + " SAR");
                    writer.closeFile(); // Ensure resources are released
                } catch (Exception fileEx) {
                    System.err.println("️ Warning: Could not save invoice to file.");
                }

                // UI Display: Generating comprehensive terminal-based text invoice
                System.out.println("\n===== Final Invoice =====");
                System.out.println(booking);

                System.out.println("\nRooms Booked:");
                for (Accommodation ac : accommodations) {
                    System.out.println(ac);
                    ac.showAmenities(); // Utilizing polymorphism to showcase specific amenities
                    System.out.println();
                }

                if (restaurant != null) {
                    System.out.println("\n--- Restaurant Summary ---");
                    System.out.println(restaurant);
                    restaurant.book();
                }

                if (gym != null) {
                    System.out.println("\n--- Gym Summary ---");
                    System.out.println(gym);
                    gym.book();
                }

                System.out.printf("\nTOTAL: %.2f SAR%n", total);
                System.out.println("============================");

                // Admin View: Display full records of all compiled orders inside the system
                System.out.println("\n===== All Bookings in System =====");
                for (Booking b : allBookings) {
                    System.out.println(b);
                    for (Accommodation a : b.getAccommodation()) {
                        System.out.println(a);
                        a.showAmenities();
                        System.out.println();
                    }
                    System.out.println("-------------------------");
                }
            } else {
                booking.cancel();
            }

        } catch (InputMismatchException e) {
            System.err.println(" Input Error: Invalid data type entered in extras booking.");
        } catch (IllegalArgumentException e) {
            System.err.println(" Logical Restriction: " + e.getMessage());
        } catch (NullPointerException e) {
            System.err.println(" Critical Object Error: Attempted to reference a null object in calculation.");
        } catch (Exception e) {
            System.err.println(" An unexpected error occurred: " + e.getMessage());
        } finally {
            input.close(); // Safeguard memory leaks by closing the Scanner instance safely
        }
    }
}