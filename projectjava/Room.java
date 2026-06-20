 /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projectjava;
/**
 * Structural domain entity representing physical space allocations within the property.
 * Maps spatial definitions and baseline statuses for integration into broader accommodation hierarchies.
 */
public abstract class Room extends Accommodation {
  private int numOfBeds;

    public Room(String desc, String size, double price,int numOfBeds) {
        super(desc, size, price);
        this.numOfBeds = numOfBeds;
    }

    public Room() {
        
        this("","",0.0,0);
    }

    public int getNumOfBeds() {
        return numOfBeds;
    }

    public void setNumOfBeds(int numOfBeds) {
        this.numOfBeds = numOfBeds;
    }

    @Override
    public String toString() {
        return super.toString()+ "| Number Of Beds : " + numOfBeds ;
    }

   

    



  


}
