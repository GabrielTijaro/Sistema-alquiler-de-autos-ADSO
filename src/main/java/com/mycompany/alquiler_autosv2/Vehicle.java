/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.alquiler_autosv2;

public class Vehicle {
    private String plate;
    private String brand;
    private String model;
    private double pricePerDay;
    private boolean available;

    public Vehicle(String plate, String brand, String model, double pricePerDay) {
        this.plate = plate;
        this.brand = brand;
        this.model = model;
        this.pricePerDay = pricePerDay;
        this.available = true;
    }

    public String getPlate() { return plate; }
    public String getBrand() { return brand; }
    public String getModel() { return model; }
    public double getPricePerDay() { return pricePerDay; }
    public boolean isAvailable() { return available; }
    public void setAvailable(boolean available) { this.available = available; }
    public void setPricePerDay(double pricePerDay) { this.pricePerDay = pricePerDay; }
    public void setBrand(String brand) { this.brand = brand; }
    public void setModel(String model) { this.model = model; }

    public double calculateDailyRate() {
        return pricePerDay;
    }

    @Override
    public String toString() {
        return "[" + getType() + "] Plate: " + plate +
               " | Brand: " + brand +
               " | Model: " + model +
               " | Price/day: $" + calculateDailyRate() +
               " | Available: " + (available ? "Yes" : "No");
    }

    public String getType() {
        return "Regular";
    }
}