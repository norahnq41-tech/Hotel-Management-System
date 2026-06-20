/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projectjava;

/**
 * Concrete implementation of a standard Single-Occupancy Room, inheriting from Accommodation.
 * Overrides behavior to inject unique premium logic including living area configurations 
 * and base individual bedding options.
 */

public class SingleRoom extends Room {
private boolean hasLivingRoom;

    public SingleRoom(String desc, String size, double price, int numOfBeds,boolean hasLivingRoom  ) {
        super(desc, size, price, numOfBeds);
        this.hasLivingRoom=  hasLivingRoom;
    }

    public SingleRoom() {
        this( "","",0.0,0,true);
    }

    public boolean isHasLivingRoom() {
        return hasLivingRoom;
    }

    public void setHasLivingRoom(boolean hasLivingRoom) {
        this.hasLivingRoom = hasLivingRoom;
    }

   

    @Override
    public void showAmenities() {
        System.out.println("includes a wi-fi");
        System.out.println("includes a Bathroom with a Jacuzzi tub"); 
    }

    @Override
    public String serviceType() {
       return "includes a Housekeeping / GuestMealService";
    }

   @Override
public String toString() {
    return super.toString() + String.format(" | Beds: %d | Living Room: %s", getNumOfBeds(), hasLivingRoom ? "Yes" : "No");
}
  
           
           
}
