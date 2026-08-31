/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.alquiler_autosv2;

import java.util.ArrayList;

public class Company {
    private ArrayList<Vehicle> vehicles = new ArrayList<>();
    private ArrayList<Customer> customers = new ArrayList<>();
    private ArrayList<Rental> rentals = new ArrayList<>();

    
    public void addVehicle(Vehicle v) { vehicles.add(v); }

    public Vehicle findVehicle(String plate) {
        for (Vehicle v : vehicles)
            if (v.getPlate().equalsIgnoreCase(plate)) return v;
        return null;
    }

    public boolean updateVehicle(String plate, String brand, String model, double price) {
        Vehicle v = findVehicle(plate);
        if (v == null) return false;
        v.setBrand(brand);
        v.setModel(model);
        v.setPricePerDay(price);
        return true;
    }

    public boolean deleteVehicle(String plate) {
        Vehicle v = findVehicle(plate);
        if (v == null || !v.isAvailable()) return false;
        vehicles.remove(v);
        return true;
    }

    public ArrayList<Vehicle> getVehicles() { return vehicles; }

   
    public void addCustomer(Customer c) { customers.add(c); }

    public Customer findCustomer(String id) {
        for (Customer c : customers)
            if (c.getId().equalsIgnoreCase(id)) return c;
        return null;
    }

    public boolean updateCustomer(String id, String name, String phone) {
        Customer c = findCustomer(id);
        if (c == null) return false;
        c.setName(name);
        c.setPhone(phone);
        return true;
    }

    public boolean deleteCustomer(String id) {
        Customer c = findCustomer(id);
        if (c == null) return false;
        customers.remove(c);
        return true;
    }

    public ArrayList<Customer> getCustomers() { return customers; }

    
    public Rental rentVehicle(String plate, String customerId, int days) {
        Vehicle v = findVehicle(plate);
        Customer c = findCustomer(customerId);
        if (v == null || c == null || !v.isAvailable()) return null;
        Rental r = new Rental(v, c, days);
        rentals.add(r);
        return r;
    }

    public Rental findActiveRental(String plate) {
        for (Rental r : rentals)
            if (r.getVehicle().getPlate().equalsIgnoreCase(plate) && !r.isReturned())
                return r;
        return null;
    }

    public boolean registerReturn(String plate) {
        Rental r = findActiveRental(plate);
        if (r == null) return false;
        r.registerReturn();
        return true;
    }

    public ArrayList<Rental> getRentals() { return rentals; }

   
    }


