package com.mycompany.alquiler_autosv2;

public class LuxuryVehicle extends Vehicle {
    private double surcharge;

    public LuxuryVehicle(String plate, String brand, String model, double pricePerDay, double surcharge) {
        super(plate, brand, model, pricePerDay, "Gama alta / Lujo");
        this.surcharge = surcharge;
    }

    public double getSurcharge() { return surcharge; }
    public void setSurcharge(double surcharge) { this.surcharge = surcharge; }

    @Override
    public double calculateDailyRate() {
        return getPricePerDay() + surcharge;
    }

    @Override
    public String getType() {
        return "Gama alta / Lujo";
    }

    @Override
    public String toString() {
        return "[" + getType() + "] Placa: " + getPlate() +
               " | " + getBrand() + " " + getModel() +
               " | Base: $" + String.format("%,.0f", getPricePerDay()) +
               " + Recargo Lujo: $" + String.format("%,.0f", surcharge) +
               " (Total/día: $" + String.format("%,.0f", calculateDailyRate()) + " COP)" +
               " | Estado: " + getStatus();
    }
}
