/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projectjava;

/**
 * Abstract base class serving as the core blueprint for all hotel accommodations.
 * Utilizes Abstraction to enforce shared attributes and behaviors (like price calculation and amenities)
 * across various concrete room types, establishing a foundation for Polymorphism.
 */
public abstract  class Accommodation {
   
   
    private String desc;
    private String size;
    private double price;
    private boolean available;

    public Accommodation( String desc,String size ,double price){
        this.desc = desc;
        this.size = size;
        this.price = price;
       setAvailable(true); 
       
        
       }

    public Accommodation() {
    this("","",0.0);
    }

    
    public abstract void showAmenities();
    
    public abstract  String serviceType();

   

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

   @Override
public String toString() {
    return String.format("%s | Description: %s | Size: %s | Price: %.2f SAR | Available: %s",
            getClass().getSimpleName(), desc, size, price, available ? "Yes" : "No");
}
    
    
    
    
    
}
 
