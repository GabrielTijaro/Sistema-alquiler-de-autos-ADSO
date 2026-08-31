/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.alquiler_autosv2;

public class Rental {
    private Vehicle vehicle;
    private Customer customer;
    private int days;
    private boolean returned;

    public Rental(Vehicle vehicle, Customer customer, int days) {
        this.vehicle = vehicle;
        this.customer = customer;
        this.days = days;
        this.returned = false;
        vehicle.setAvailable(false);
    }

    public double calculateTotal() {
        return vehicle.calculateDailyRate() * days;
    }

    public void registerReturn() {
        this.returned = true;
        vehicle.setAvailable(true);
    }

    public Vehicle getVehicle() { return vehicle; }
    public Customer getCustomer() { return customer; }
    public int getDays() { return days; }
    public boolean isReturned() { return returned; }

    @Override
    public String toString() {
        return "Vehicle: " + vehicle.getPlate() +
               " | Customer: " + customer.getName() +
               " | Days: " + days +
               " | Total: $" + calculateTotal() +
               " | Returned: " + (returned ? "Yes" : "No");
    }
}

