/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projectjava;

/**
 * High-level structural entity representing the main hotel institution.
 * Acts as an orchestrator or container for managing system inventories, branch configurations,
 * and overarching corporate attributes.
 */
public interface Hotel {
    public static final double VAT = 0.15;
    
  public abstract  void book();
public abstract double calcTotal();

}
