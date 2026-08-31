/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.alquiler_autosv2;

public class LuxuryVehicle extends Vehicle {
    private double surcharge;

    public LuxuryVehicle(String plate, String brand, String model, double pricePerDay, double surcharge) {
        super(plate, brand, model, pricePerDay);
        this.surcharge = surcharge;
    }

    @Override
    public double calculateDailyRate() {
        return getPricePerDay() + surcharge;
    }

    @Override
    public String getType() {
        return "Luxury";
    }
}
