/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projectjava;

/**
 * Represents a high-end luxury Suite accommodation, extending the parent Accommodation class.
 * Showcases advanced OOP structure by encapsulating premium attributes like multiple internal chambers,
 * guest ceilings, and integrated complimentary spa privileges.
 */
public class Suite extends Accommodation {
    private int numOfRoom;
    private int maxGuests ;

    public Suite( String desc, String size, double price ,int numOfRoom, int maxGuests) {
        super(desc, size, price);
        this.numOfRoom = numOfRoom;
        this.maxGuests = maxGuests;
    }

    public Suite() {
        this("","",0.0,0,0);
    }

    @Override
    public void showAmenities() {
        System.out.println("includes a swiming pool");
        System.out.println("includes a Free Wi-Fi");
        System.out.println("includes a Spa");
    }

    @Override
    public String serviceType() {
      return "includes a VIP services and and butler service";
    }

   public void spaServiceType(){
       
    System.out.println("Spa Services:");
    System.out.println("- Full Body Massage");
    System.out.println("- Facial Treatment");
    System.out.println("- Sauna Session");

   }

    public int getNumOfRoom() {
        return numOfRoom;
    }

    public void setNumOfRoom(int numOfRoom) {
        this.numOfRoom = numOfRoom;
    }

    public int getMaxGuests() {
        return maxGuests;
    }

    public void setMaxGuests(int maxGuests) {
        this.maxGuests = maxGuests;
    }

    @Override
public String toString() {
    return super.toString() + String.format(" | Rooms: %d | Max Guests: %d", numOfRoom, maxGuests);
}
    } 

    

  