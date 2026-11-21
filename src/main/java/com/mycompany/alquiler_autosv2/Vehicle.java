package com.mycompany.alquiler_autosv2;

public class Vehicle {
    private String plate;
    private String brand;
    private String model;
    private double pricePerDay;
    private String status; // 'AVAILABLE', 'RENTED', 'INACTIVE'
    private String vehicleType;

    public Vehicle(String plate, String brand, String model, double pricePerDay, String vehicleType) {
        this.plate = plate;
        this.brand = brand;
        this.model = model;
        this.pricePerDay = pricePerDay;
        this.vehicleType = vehicleType;
        this.status = "AVAILABLE";
    }

    public Vehicle(String plate, String brand, String model, double pricePerDay) {
        this(plate, brand, model, pricePerDay, "Económico");
    }

    public String getPlate() { return plate; }
    public String getBrand() { return brand; }
    public String getModel() { return model; }
    public double getPricePerDay() { return pricePerDay; }
    public String getStatus() { return status; }
    public String getVehicleType() { return vehicleType; }

    public boolean isAvailable() {
        return "AVAILABLE".equalsIgnoreCase(status);
    }

    public void setStatus(String status) { this.status = status; }
    public void setPricePerDay(double pricePerDay) { this.pricePerDay = pricePerDay; }
    public void setBrand(String brand) { this.brand = brand; }
    public void setModel(String model) { this.model = model; }
    public void setVehicleType(String vehicleType) { this.vehicleType = vehicleType; }

    public void deactivate() {
        this.status = "INACTIVE";
    }

    public void activate() {
        this.status = "AVAILABLE";
    }

    public double calculateDailyRate() {
        return pricePerDay;
    }

    public String getType() {
        return vehicleType != null ? vehicleType : "Regular";
    }

    @Override
    public String toString() {
        return "[" + getType() + "] Placa: " + plate +
               " | " + brand + " " + model +
               " | Tarifa/día: $" + String.format("%,.0f", calculateDailyRate()) + " COP" +
               " | Estado: " + status;
    }
}
