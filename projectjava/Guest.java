/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projectjava;
/**
 * Model class encapsulating comprehensive guest demographics and contact profiles.
 * Implements strict Encapsulation principles to protect customer data records 
 * and support institutional validation routines.
 */
    
public class Guest {
    private int ID;
    private String name;
    private String email;
    private String phoneNo;

  
    public Guest( String name,int ID, String phoneNo, String email) {
        this.ID = ID;
        this.name = name;
        this.email = email;
        this.phoneNo = phoneNo;
    }

    
    public Guest() {
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNo( String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public int getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

   @Override
public String toString() {
    return String.format("Name: %s | ID: %d | Phone: %s | Email: %s", name, ID, phoneNo, email);
}
    

}
    

