

package com.mycompany.alquiler_autosv2;

import java.util.Scanner;

public class Alquiler_autosv2{
    static Scanner sc = new Scanner(System.in);
    static Company company = new Company();

    public static void main(String[] args) {
        int option;
        do {
            System.out.println("\n===== VEHICLE RENTAL SYSTEM =====");
            System.out.println("1. Vehicle management");
            System.out.println("2. Customer management");
            System.out.println("3. Rental management");
            System.out.println("4. Returns");
            System.out.println("5. Billing");
            System.out.println("6. Reports");
            System.out.println("7. Exit");
            option = readInt("Select option: ");
            switch (option) {
                case 1 -> vehicleMenu();
                case 2 -> customerMenu();
                case 3 -> rentalMenu();
                case 4 -> returnsMenu();
                case 5 -> billingMenu();
                case 6 -> reportsMenu();
                case 7 -> System.out.println("Goodbye!");
                default -> System.out.println("Invalid option.");
            }
        } while (option != 7);
    }

    
    static void vehicleMenu() {
        int opt;
        do {
            System.out.println("\n--- Vehicle Management ---");
            System.out.println("1. Register regular vehicle");
            System.out.println("2. Register luxury vehicle");
            System.out.println("3. Update vehicle");
            System.out.println("4. Delete vehicle");
            System.out.println("5. Search vehicle by plate");
            System.out.println("6. Back");
            opt = readInt("Select: ");
            switch (opt) {
                case 1 -> registerRegularVehicle();
                case 2 -> registerLuxuryVehicle();
                case 3 -> updateVehicle();
                case 4 -> deleteVehicle();
                case 5 -> searchVehicle();
                case 6 -> System.out.println("Going back...");
                default -> System.out.println("Invalid option.");
            }
        } while (opt != 6);
    }

    static void registerRegularVehicle() {
        System.out.print("Plate: "); String plate = sc.nextLine();
        System.out.print("Brand: "); String brand = sc.nextLine();
        System.out.print("Model: "); String model = sc.nextLine();
        double price = readDouble("Price per day: $");
        company.addVehicle(new Vehicle(plate, brand, model, price));
        System.out.println("Regular vehicle registered.");
    }

    static void registerLuxuryVehicle() {
        System.out.print("Plate: "); String plate = sc.nextLine();
        System.out.print("Brand: "); String brand = sc.nextLine();
        System.out.print("Model: "); String model = sc.nextLine();
        double price = readDouble("Base price per day: $");
        double surcharge = readDouble("Luxury surcharge: $");
        company.addVehicle(new LuxuryVehicle(plate, brand, model, price, surcharge));
        System.out.println("Luxury vehicle registered.");
    }

    static void updateVehicle() {
        System.out.print("Plate to update: "); String plate = sc.nextLine();
        System.out.print("New brand: "); String brand = sc.nextLine();
        System.out.print("New model: "); String model = sc.nextLine();
        double price = readDouble("New price per day: $");
        if (company.updateVehicle(plate, brand, model, price))
            System.out.println("Vehicle updated.");
        else
            System.out.println("Vehicle not found.");
    }

    static void deleteVehicle() {
        System.out.print("Plate to delete: "); String plate = sc.nextLine();
        if (company.deleteVehicle(plate))
            System.out.println("Vehicle deleted.");
        else
            System.out.println("Vehicle not found or currently rented.");
    }

    static void searchVehicle() {
        System.out.print("Plate: "); String plate = sc.nextLine();
        Vehicle v = company.findVehicle(plate);
        if (v != null) System.out.println(v);
        else System.out.println("Vehicle not found.");
    }

    
    static void customerMenu() {
        int opt;
        do {
            System.out.println("\n--- Customer Management ---");
            System.out.println("1. Register customer");
            System.out.println("2. Update customer");
            System.out.println("3. Delete customer");
            System.out.println("4. Search customer by ID");
            System.out.println("5. Back");
            opt = readInt("Select: ");
            switch (opt) {
                case 1 -> registerCustomer();
                case 2 -> updateCustomer();
                case 3 -> deleteCustomer();
                case 4 -> searchCustomer();
                case 5 -> System.out.println("Going back...");
                default -> System.out.println("Invalid option.");
            }
        } while (opt != 5);
    }

    static void registerCustomer() {
        System.out.print("ID: "); String id = sc.nextLine();
        System.out.print("Name: "); String name = sc.nextLine();
        System.out.print("Phone: "); String phone = sc.nextLine();
        company.addCustomer(new Customer(id, name, phone));
        System.out.println("Customer registered.");
    }

    static void updateCustomer() {
        System.out.print("Customer ID to update: "); String id = sc.nextLine();
        System.out.print("New name: "); String name = sc.nextLine();
        System.out.print("New phone: "); String phone = sc.nextLine();
        if (company.updateCustomer(id, name, phone))
            System.out.println("Customer updated.");
        else
            System.out.println("Customer not found.");
    }

    static void deleteCustomer() {
        System.out.print("Customer ID to delete: "); String id = sc.nextLine();
        if (company.deleteCustomer(id))
            System.out.println("Customer deleted.");
        else
            System.out.println("Customer not found.");
    }

    static void searchCustomer() {
        System.out.print("Customer ID: "); String id = sc.nextLine();
        Customer c = company.findCustomer(id);
        if (c != null) System.out.println(c);
        else System.out.println("Customer not found.");
    }

  
    static void rentalMenu() {
        System.out.println("\n--- Register Rental ---");
        System.out.print("Vehicle plate: "); String plate = sc.nextLine();
        System.out.print("Customer ID: "); String id = sc.nextLine();
        int days = readInt("Number of days: ");
        Rental r = company.rentVehicle(plate, id, days);
        if (r != null) {
            System.out.println("Rental registered successfully.");
            System.out.println(r);
        } else {
            System.out.println("Could not register rental. Check plate, customer ID or availability.");
        }
    }

  
    static void returnsMenu() {
        System.out.println("\n--- Register Return ---");
        System.out.print("Vehicle plate: "); String plate = sc.nextLine();
        if (company.registerReturn(plate))
            System.out.println("Return registered. Vehicle is now available.");
        else
            System.out.println("No active rental found for that plate.");
    }

    
    static void billingMenu() {
        System.out.println("\n--- Billing ---");
        System.out.print("Vehicle plate: "); String plate = sc.nextLine();
        Rental r = company.findActiveRental(plate);
        if (r == null) {
            for (Rental rental : company.getRentals()) {
                if (rental.getVehicle().getPlate().equalsIgnoreCase(plate)) {
                    System.out.println(rental);
                    return;
                }
            }
            System.out.println("No rental found for that plate.");
        } else {
            System.out.println(r);
        }
    }

   
    static void reportsMenu() {
        System.out.println("\n=== VEHICLES ===");
        if (company.getVehicles().isEmpty())
            System.out.println("No vehicles registered.");
        for (Vehicle v : company.getVehicles())
            System.out.println(v);

        System.out.println("\n=== CUSTOMERS ===");
        if (company.getCustomers().isEmpty())
            System.out.println("No customers registered.");
        for (Customer c : company.getCustomers())
            System.out.println(c);

        System.out.println("\n=== RENTALS ===");
        if (company.getRentals().isEmpty())
            System.out.println("No rentals registered.");
        for (Rental r : company.getRentals())
            System.out.println(r);
    }

    
    static int readInt(String msg) {
        System.out.print(msg);
        while (!sc.hasNextInt()) {
            System.out.println("Enter a valid number.");
            System.out.print(msg);
            sc.next();
        }
        int v = sc.nextInt();
        sc.nextLine();
        return v;
    }

    static double readDouble(String msg) {
        System.out.print(msg);
        while (!sc.hasNextDouble()) {
            System.out.println("Enter a valid number.");
            System.out.print(msg);
            sc.next();
        }
        double v = sc.nextDouble();
        sc.nextLine();
        return v;
    }
}
