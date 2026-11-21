package com.mycompany.alquiler_autosv2;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Company {
    private String name = "Neiva Rent a Car";
    private String taxId = "900123456-7";
    private String address = "Cra 5 # 10-20, Neiva";
    private String phone = "3201234567";

    private ArrayList<Vehicle> vehicles = new ArrayList<>();
    private ArrayList<Customer> customers = new ArrayList<>();
    private ArrayList<Rental> rentals = new ArrayList<>();
    private ArrayList<User> users = new ArrayList<>();
    private int nextRentalId = 1;
    private double defaultPenaltyRate = 50000.0; // Tarifa diaria de mora en COP (matching vehicle_rental.sql)

    public Company() {
        loadSeedData();
    }

    public void loadSeedData() {
        users.add(new User("lmendez", "admin123", "Laura Méndez", "ADMIN"));
        users.add(new User("crojas",  "emp123",   "Carlos Rojas", "EMPLOYEE"));
        users.add(new User("dtorres", "cash123",  "Diana Torres", "CASHIER"));

        customers.add(new Customer("1075634521", "Andrés Felipe Cuellar", "3124567890", "Cra 5 # 10-20, Neiva"));
        customers.add(new Customer("52741963",   "Marcela Rincón",        "3009876543", "Calle 8 # 3-14, Neiva"));

        Vehicle v1 = new Vehicle("ABC123", "Chevrolet", "Spark", 90000.0, "Económico");
        Vehicle v2 = new Vehicle("DEF456", "Mazda", "CX-30", 150000.0, "Gama media");
        LuxuryVehicle v3 = new LuxuryVehicle("GHI789", "BMW", "Serie 4", 350000.0, 100000.0);
        Vehicle v4 = new Vehicle("JKL012", "Renault", "Logan", 85000.0, "Económico");
        Vehicle v5 = new Vehicle("TOY789", "Toyota", "Hilux 4x4", 280000.0, "Camioneta / SUV");

        vehicles.add(v1);
        vehicles.add(v2);
        vehicles.add(v3);
        vehicles.add(v4);
        vehicles.add(v5);

        // Alquiler histórico completado (sin mora)
        Rental r1 = new Rental(nextRentalId++, v1, customers.get(0),
                LocalDate.of(2026, 9, 1), LocalDate.of(2026, 9, 4), "Carlos Rojas");
        r1.registerReturn(LocalDate.of(2026, 9, 4));
        rentals.add(r1);

        // Alquiler con mora de 2 días ya liquidado
        Rental r2 = new Rental(nextRentalId++, v3, customers.get(1),
                LocalDate.of(2026, 9, 5), LocalDate.of(2026, 9, 8), "Diana Torres");
        r2.registerReturn(LocalDate.of(2026, 9, 10)); // 2 días de retraso
        rentals.add(r2);

        // Alquiler activo en mora para demostración en vivo (placa JKL012)
        Rental r3 = new Rental(nextRentalId++, v4, customers.get(0),
                LocalDate.now().minusDays(5), LocalDate.now().minusDays(2), "Carlos Rojas");
        // Actualmente activo con 2 días de retraso
        rentals.add(r3);
    }

    public User authenticate(String username, String password) {
        for (User u : users) {
            if (u.getUsername().equalsIgnoreCase(username) && u.checkPassword(password)) {
                return u;
            }
        }
        return null;
    }

    // VEHICULOS
    public void addVehicle(Vehicle v) { vehicles.add(v); }

    public Vehicle findVehicle(String plate) {
        for (Vehicle v : vehicles)
            if (v.getPlate().equalsIgnoreCase(plate)) return v;
        return null;
    }

    public List<Vehicle> searchVehicles(String query) {
        String q = query.toLowerCase();
        List<Vehicle> result = new ArrayList<>();
        for (Vehicle v : vehicles) {
            if (v.getPlate().toLowerCase().contains(q) ||
                v.getBrand().toLowerCase().contains(q) ||
                v.getModel().toLowerCase().contains(q) ||
                v.getType().toLowerCase().contains(q)) {
                result.add(v);
            }
        }
        return result;
    }

    public boolean updateVehicle(String plate, String brand, String model, double price, String type) {
        Vehicle v = findVehicle(plate);
        if (v == null) return false;
        v.setBrand(brand);
        v.setModel(model);
        v.setPricePerDay(price);
        if (type != null) v.setVehicleType(type);
        return true;
    }

    public boolean deactivateVehicle(String plate) {
        Vehicle v = findVehicle(plate);
        if (v == null || !v.isAvailable()) return false;
        v.deactivate();
        return true;
    }

    public boolean activateVehicle(String plate) {
        Vehicle v = findVehicle(plate);
        if (v == null) return false;
        v.activate();
        return true;
    }

    public ArrayList<Vehicle> getVehicles() { return vehicles; }

    // CLIENTES
    public void addCustomer(Customer c) { customers.add(c); }

    public Customer findCustomer(String id) {
        for (Customer c : customers)
            if (c.getId().equalsIgnoreCase(id)) return c;
        return null;
    }

    public List<Customer> searchCustomers(String query) {
        String q = query.toLowerCase();
        List<Customer> result = new ArrayList<>();
        for (Customer c : customers) {
            if (c.getId().toLowerCase().contains(q) || c.getName().toLowerCase().contains(q)) {
                result.add(c);
            }
        }
        return result;
    }

    public boolean updateCustomer(String id, String name, String phone, String address) {
        Customer c = findCustomer(id);
        if (c == null) return false;
        c.setName(name);
        c.setPhone(phone);
        if (address != null) c.setAddress(address);
        return true;
    }

    public ArrayList<Customer> getCustomers() { return customers; }

    // ALQUILERES
    public Rental rentVehicle(String plate, String customerId, LocalDate start, LocalDate due, String operatorName) {
        Vehicle v = findVehicle(plate);
        Customer c = findCustomer(customerId);
        if (v == null || c == null || !v.isAvailable()) return null;
        Rental r = new Rental(nextRentalId++, v, c, start, due, operatorName);
        rentals.add(r);
        return r;
    }

    public Rental findActiveRental(String plate) {
        for (Rental r : rentals) {
            if (r.getVehicle().getPlate().equalsIgnoreCase(plate) && !r.isReturned()) {
                return r;
            }
        }
        return null;
    }

    public Rental findRentalById(int id) {
        for (Rental r : rentals) {
            if (r.getId() == id) return r;
        }
        return null;
    }

    public boolean extendRental(String plate, int additionalDays) {
        Rental r = findActiveRental(plate);
        if (r == null || additionalDays <= 0) return false;
        r.extendDueDate(additionalDays);
        return true;
    }

    public boolean registerReturn(String plate, LocalDate actualReturnDate) {
        Rental r = findActiveRental(plate);
        if (r == null) return false;
        r.registerReturn(actualReturnDate);
        return true;
    }

    public ArrayList<Rental> getRentals() { return rentals; }
    public double getDefaultPenaltyRate() { return defaultPenaltyRate; }
    public void setDefaultPenaltyRate(double rate) { this.defaultPenaltyRate = rate; }
    public String getName() { return name; }
    public String getTaxId() { return taxId; }
    public String getAddress() { return address; }
    public String getPhone() { return phone; }
}
