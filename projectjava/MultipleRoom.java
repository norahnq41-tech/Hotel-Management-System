/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projectjava;

/**
 * Represents a concrete Multiple-Guest Room configuration, extending the base Accommodation class.
 * Inherits core pricing behaviors while introducing subclass-specific variables 
 * like distinct bed types and capacity scales.
 */
public class MultipleRoom extends Room {
    private String bedType;

    public MultipleRoom( String desc, String size, double price, int numOfBeds,String bedType) {
        super(desc, size, price, numOfBeds);
        this.bedType = bedType;
    }

    public MultipleRoom() {
        this("","",0.0,0,"");
        
    }

    public String getBedType() {
        return bedType;
    }

    public void setBedType(String bedType) {
        this.bedType = bedType;
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
    return super.toString() + String.format(" | Beds: %d | Bed Type: %s", getNumOfBeds(), bedType);
}

        
    
    
    
    
}
