package com.mycompany.alquiler_autosv2;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class Rental {
    private int id;
    private Vehicle vehicle;
    private Customer customer;
    private LocalDate startDate;
    private LocalDate dueDate;
    private LocalDate returnDate;
    private double dailyRate;
    private double luxurySurcharge;
    private String operatorName;
    private boolean returned;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public Rental(int id, Vehicle vehicle, Customer customer, LocalDate startDate, LocalDate dueDate, String operatorName) {
        this.id = id;
        this.vehicle = vehicle;
        this.customer = customer;
        this.startDate = startDate;
        this.dueDate = dueDate;
        this.operatorName = operatorName;
        this.dailyRate = vehicle.getPricePerDay();
        this.luxurySurcharge = (vehicle instanceof LuxuryVehicle lv) ? lv.getSurcharge() : 0.0;
        this.returned = false;
        this.returnDate = null;
        vehicle.setStatus("RENTED");
    }

    public int getId() { return id; }
    public Vehicle getVehicle() { return vehicle; }
    public Customer getCustomer() { return customer; }
    public LocalDate getStartDate() { return startDate; }
    public LocalDate getDueDate() { return dueDate; }
    public LocalDate getReturnDate() { return returnDate; }
    public double getDailyRate() { return dailyRate; }
    public double getLuxurySurcharge() { return luxurySurcharge; }
    public String getOperatorName() { return operatorName; }
    public boolean isReturned() { return returned; }

    public long calculateContractedDays() {
        long days = ChronoUnit.DAYS.between(startDate, dueDate);
        return days <= 0 ? 1 : days;
    }

    public double calculateSubtotal() {
        return calculateContractedDays() * dailyRate;
    }

    public long calculateOverdueDays(LocalDate actualReturn) {
        LocalDate checkDate = (actualReturn != null) ? actualReturn : (returnDate != null ? returnDate : LocalDate.now());
        if (checkDate.isAfter(dueDate)) {
            return ChronoUnit.DAYS.between(dueDate, checkDate);
        }
        return 0;
    }

    public double calculatePenalty(LocalDate actualReturn, double dailyPenaltyRate) {
        return calculateOverdueDays(actualReturn) * dailyPenaltyRate;
    }

    public double calculateGrandTotal(LocalDate actualReturn, double dailyPenaltyRate) {
        return calculateSubtotal() + luxurySurcharge + calculatePenalty(actualReturn, dailyPenaltyRate);
    }

    public void registerReturn(LocalDate actualReturnDate) {
        this.returnDate = (actualReturnDate != null) ? actualReturnDate : LocalDate.now();
        this.returned = true;
        this.vehicle.setStatus("AVAILABLE");
    }

    public void extendDueDate(int additionalDays) {
        if (additionalDays > 0) {
            this.dueDate = this.dueDate.plusDays(additionalDays);
        }
    }

    @Override
    public String toString() {
        String retStr = (returnDate != null) ? returnDate.format(FORMATTER) : "EN CURSO";
        long mora = calculateOverdueDays(null);
        String moraStr = mora > 0 ? " [MORA: +" + mora + "d]" : "";
        return "Alquiler #" + id + " | Auto: " + vehicle.getPlate() + " (" + vehicle.getBrand() + " " + vehicle.getModel() + ")" +
               " | Cliente: " + customer.getName() + " (" + customer.getId() + ")" +
               " | Plazo: " + startDate.format(FORMATTER) + " al " + dueDate.format(FORMATTER) +
               " | Retorno: " + retStr + moraStr +
               " | Estado: " + (returned ? "FINALIZADO" : "ACTIVO");
    }
}
