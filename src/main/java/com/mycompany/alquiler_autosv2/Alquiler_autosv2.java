package com.mycompany.alquiler_autosv2;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class Alquiler_autosv2 {
    static Scanner sc = new Scanner(System.in);
    static Company company = new Company();
    static User currentUser = null;
    static final DateTimeFormatter DATE_FMT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static void main(String[] args) {
        System.out.println("=============================================================");
        System.out.println("   SISTEMA DE GESTION DE ALQUILER DE AUTOS - NEIVA RENT");
        System.out.println("        SENA Regional Huila - Ficha 3239188 - Grupo 06");
        System.out.println("=============================================================");

        while (true) {
            if (currentUser == null) {
                loginMenu();
            } else {
                mainRoleMenu();
            }
        }
    }

    static void loginMenu() {
        System.out.println("\n>>> INICIO DE SESION (HU-00) <<<");
        System.out.println("Usuarios predeterminados para evaluacion:");
        System.out.println("  1. lmendez / admin123   -> Administradora (Laura Mendez)");
        System.out.println("  2. crojas  / emp123     -> Asesor / Empleado (Carlos Rojas)");
        System.out.println("  3. dtorres / cash123    -> Cajera (Diana Torres)");
        System.out.println("  0. Salir del sistema");
        System.out.println("-------------------------------------------------------------");
        
        System.out.print("Usuario: ");
        String username = sc.nextLine().trim();
        if ("0".equals(username)) {
            System.out.println("\nGracias por utilizar Neiva Rent a Car. Hasta pronto!");
            System.exit(0);
        }

        System.out.print("Contraseña: ");
        String password = sc.nextLine().trim();

        User u = company.authenticate(username, password);
        if (u != null) {
            currentUser = u;
            System.out.println("\n[OK] Bienvenido(a), " + u.getFullName() + " | Rol: " + u.getRole());
        } else {
            System.out.println("\n[ERROR] Credenciales invalidas. Intente nuevamente.");
        }
    }

    // MENÚ PRINCIPAL DINÁMICO SEGÚN ROL (RBAC)
    static void mainRoleMenu() {
        System.out.println("\n=============================================================");
        System.out.println("  NEIVA RENT A CAR | Sesion activa: " + currentUser.getFullName() + " (" + currentUser.getRole() + ")");
        System.out.println("=============================================================");

        switch (currentUser.getRole()) {
            case "ADMIN" -> adminMenu();
            case "EMPLOYEE" -> employeeMenu();
            case "CASHIER" -> cashierMenu();
            default -> {
                System.out.println("Rol no reconocido. Cerrando sesion.");
                currentUser = null;
            }
        }
    }

    // MENÚ ADMINISTRADOR
    static void adminMenu() {
        System.out.println("1. Gestion de Flota (Vehiculos, Bajas HU-03)");
        System.out.println("2. Gestion de Clientes");
        System.out.println("3. Operaciones de Alquiler (Contratos y Prorrogas)");
        System.out.println("4. Devoluciones y Calculo de Mora");
        System.out.println("5. Facturacion y Liquidacion");
        System.out.println("6. Reportes Consolidados del Sistema");
        System.out.println("7. Cerrar Sesion");
        System.out.println("8. Salir del programa");
        int opt = readInt("Seleccione una opcion: ");
        switch (opt) {
            case 1 -> vehicleMenu();
            case 2 -> customerMenu();
            case 3 -> rentalOperationsMenu();
            case 4 -> returnsMenu();
            case 5 -> billingMenu();
            case 6 -> reportsMenu();
            case 7 -> logout();
            case 8 -> exitProgram();
            default -> System.out.println("Opcion invalida.");
        }
    }

    // MENÚ ASESOR / EMPLEADO
    static void employeeMenu() {
        System.out.println("1. Consultar Flota Disponible");
        System.out.println("2. Gestion de Clientes (Registrar / Actualizar)");
        System.out.println("3. Registrar Nuevo Contrato de Alquiler");
        System.out.println("4. Extender Plazo de Alquiler Activo");
        System.out.println("5. Ver Alquileres Activos");
        System.out.println("6. Cerrar Sesion");
        System.out.println("7. Salir del programa");
        int opt = readInt("Seleccione una opcion: ");
        switch (opt) {
            case 1 -> listAvailableVehicles();
            case 2 -> customerMenu();
            case 3 -> registerRental();
            case 4 -> extendRental();
            case 5 -> listActiveRentals();
            case 6 -> logout();
            case 7 -> exitProgram();
            default -> System.out.println("Opcion invalida.");
        }
    }

    // MENÚ CAJERO
    static void cashierMenu() {
        System.out.println("1. Registrar Devolucion de Vehiculo (Verificar Mora)");
        System.out.println("2. Generar Factura Final de Liquidacion (HU-10)");
        System.out.println("3. Consultar Alquileres Pendientes por Retorno");
        System.out.println("4. Cerrar Sesion");
        System.out.println("5. Salir del programa");
        int opt = readInt("Seleccione una opcion: ");
        switch (opt) {
            case 1 -> returnsMenu();
            case 2 -> billingMenu();
            case 3 -> listActiveRentals();
            case 4 -> logout();
            case 5 -> exitProgram();
            default -> System.out.println("Opcion invalida.");
        }
    }

    static void logout() {
        System.out.println("\nCerrando sesion de " + currentUser.getFullName() + "...");
        currentUser = null;
    }

    static void exitProgram() {
        System.out.println("\nGracias por utilizar Neiva Rent a Car. Hasta pronto!");
        System.exit(0);
    }

    static void vehicleMenu() {
        int opt;
        do {
            System.out.println("\n--- GESTION DE FLOTA (E1. Flota) ---");
            System.out.println("1. Registrar vehiculo regular (Economico / Gama Media)");
            System.out.println("2. Registrar vehiculo de lujo (Con recargo)");
            System.out.println("3. Dar de baja vehiculo (HU-03 - Baja logica INACTIVE)");
            System.out.println("4. Reactivar vehiculo dado de baja");
            System.out.println("5. Actualizar vehiculo");
            System.out.println("6. Buscar vehiculos (por Placa, Marca o Modelo)");
            System.out.println("7. Listar todos los vehiculos");
            System.out.println("8. Volver al menu anterior");
            opt = readInt("Seleccione: ");
            switch (opt) {
                case 1 -> registerRegularVehicle();
                case 2 -> registerLuxuryVehicle();
                case 3 -> deactivateVehicle();
                case 4 -> reactivateVehicle();
                case 5 -> updateVehicle();
                case 6 -> searchVehicle();
                case 7 -> listAllVehicles();
                case 8 -> System.out.println("Regresando...");
                default -> System.out.println("Opcion invalida.");
            }
        } while (opt != 8);
    }

    static void registerRegularVehicle() {
        System.out.println("\n[Registrar Vehiculo Regular]");
        System.out.print("Placa (ej. ABC123): "); String plate = sc.nextLine().trim().toUpperCase();
        if (company.findVehicle(plate) != null) {
            System.out.println("[ERROR] Ya existe un vehiculo registrado con la placa " + plate);
            return;
        }
        System.out.print("Marca: "); String brand = sc.nextLine().trim();
        System.out.print("Modelo: "); String model = sc.nextLine().trim();
        System.out.print("Gama/Tipo (ej. Economico / Gama media / SUV): "); String type = sc.nextLine().trim();
        double price = readDouble("Tarifa diaria (COP): $");
        company.addVehicle(new Vehicle(plate, brand, model, price, type));
        System.out.println("[OK] Vehiculo " + plate + " registrado exitosamente.");
    }

    static void registerLuxuryVehicle() {
        System.out.println("\n[Registrar Vehiculo de Lujo]");
        System.out.print("Placa (ej. GHI789): "); String plate = sc.nextLine().trim().toUpperCase();
        if (company.findVehicle(plate) != null) {
            System.out.println("[ERROR] Ya existe un vehiculo con la placa " + plate);
            return;
        }
        System.out.print("Marca: "); String brand = sc.nextLine().trim();
        System.out.print("Modelo: "); String model = sc.nextLine().trim();
        double price = readDouble("Tarifa base diaria (COP): $");
        double surcharge = readDouble("Recargo diario de lujo (COP): $");
        company.addVehicle(new LuxuryVehicle(plate, brand, model, price, surcharge));
        System.out.println("[OK] Vehiculo de Lujo " + plate + " registrado exitosamente.");
    }

    static void deactivateVehicle() {
        System.out.println("\n[Dar de Baja Vehiculo - HU-03]");
        System.out.print("Placa del vehiculo a dar de baja: "); String plate = sc.nextLine().trim().toUpperCase();
        Vehicle v = company.findVehicle(plate);
        if (v == null) {
            System.out.println("[ERROR] Vehiculo no encontrado.");
            return;
        }
        if ("RENTED".equalsIgnoreCase(v.getStatus())) {
            System.out.println("[ERROR] No se puede dar de baja un vehiculo que esta actualmente ALQUILADO.");
            return;
        }
        System.out.print("Confirma dar de baja el vehiculo " + v.getBrand() + " " + v.getModel() + " (S/N)?: ");
        String confirm = sc.nextLine().trim();
        if (confirm.equalsIgnoreCase("S") || confirm.equalsIgnoreCase("SI")) {
            company.deactivateVehicle(plate);
            System.out.println("[OK] Vehiculo " + plate + " marcado como INACTIVO (Dado de baja con exito).");
        } else {
            System.out.println("Operacion cancelada.");
        }
    }

    static void reactivateVehicle() {
        System.out.print("Placa del vehiculo a reactivar: "); String plate = sc.nextLine().trim().toUpperCase();
        Vehicle v = company.findVehicle(plate);
        if (v != null && "INACTIVE".equalsIgnoreCase(v.getStatus())) {
            v.activate();
            System.out.println("[OK] Vehiculo " + plate + " reactivado. Estado actual: AVAILABLE.");
        } else {
            System.out.println("[INFO] El vehiculo no existe o no se encuentra en estado INACTIVO.");
        }
    }

    static void updateVehicle() {
        System.out.print("Placa a actualizar: "); String plate = sc.nextLine().trim().toUpperCase();
        Vehicle v = company.findVehicle(plate);
        if (v == null) {
            System.out.println("[ERROR] Vehiculo no encontrado.");
            return;
        }
        System.out.print("Nueva marca [" + v.getBrand() + "]: "); String brand = sc.nextLine().trim();
        if (brand.isEmpty()) brand = v.getBrand();
        System.out.print("Nuevo modelo [" + v.getModel() + "]: "); String model = sc.nextLine().trim();
        if (model.isEmpty()) model = v.getModel();
        System.out.print("Nuevo tipo [" + v.getType() + "]: "); String type = sc.nextLine().trim();
        if (type.isEmpty()) type = v.getType();
        double price = readDouble("Nueva tarifa diaria (COP) [" + v.getPricePerDay() + "]: $");
        company.updateVehicle(plate, brand, model, price, type);
        System.out.println("[OK] Vehiculo " + plate + " actualizado.");
    }

    static void searchVehicle() {
        System.out.print("Ingrese texto de busqueda (Placa, Marca o Modelo): ");
        String q = sc.nextLine().trim();
        List<Vehicle> list = company.searchVehicles(q);
        if (list.isEmpty()) {
            System.out.println("No se encontraron vehiculos con el criterio: " + q);
        } else {
            System.out.println("\nResultados encontrados (" + list.size() + "):");
            for (Vehicle v : list) System.out.println(" - " + v);
        }
    }

    static void listAllVehicles() {
        System.out.println("\n=== FLOTA COMPLETA DE VEHICULOS ===");
        for (Vehicle v : company.getVehicles()) {
            System.out.println(" - " + v);
        }
    }

    static void listAvailableVehicles() {
        System.out.println("\n=== VEHICULOS DISPONIBLES PARA ALQUILER ===");
        int count = 0;
        for (Vehicle v : company.getVehicles()) {
            if (v.isAvailable()) {
                System.out.println(" - " + v);
                count++;
            }
        }
        if (count == 0) System.out.println("No hay vehiculos disponibles en este momento.");
    }

    static void customerMenu() {
        int opt;
        do {
            System.out.println("\n--- GESTION DE CLIENTES (E2. Clientes) ---");
            System.out.println("1. Registrar nuevo cliente");
            System.out.println("2. Actualizar datos de cliente");
            System.out.println("3. Buscar cliente por Cedula/ID o Nombre");
            System.out.println("4. Listar todos los clientes");
            System.out.println("5. Volver al menu anterior");
            opt = readInt("Seleccione: ");
            switch (opt) {
                case 1 -> registerCustomer();
                case 2 -> updateCustomer();
                case 3 -> searchCustomer();
                case 4 -> listAllCustomers();
                case 5 -> System.out.println("Regresando...");
                default -> System.out.println("Opcion invalida.");
            }
        } while (opt != 5);
    }

    static void registerCustomer() {
        System.out.println("\n[Registrar Nuevo Cliente - HU-04]");
        System.out.print("Numero de Documento / Cedula: "); String id = sc.nextLine().trim();
        if (company.findCustomer(id) != null) {
            System.out.println("[ERROR] Ya existe un cliente registrado con ese documento.");
            return;
        }
        System.out.print("Nombre completo: "); String name = sc.nextLine().trim();
        System.out.print("Telefono celular: "); String phone = sc.nextLine().trim();
        System.out.print("Direccion de residencia: "); String address = sc.nextLine().trim();
        company.addCustomer(new Customer(id, name, phone, address));
        System.out.println("[OK] Cliente " + name + " registrado exitosamente.");
    }

    static void updateCustomer() {
        System.out.print("Cedula del cliente a actualizar: "); String id = sc.nextLine().trim();
        Customer c = company.findCustomer(id);
        if (c == null) {
            System.out.println("[ERROR] Cliente no encontrado.");
            return;
        }
        System.out.print("Nuevo nombre [" + c.getName() + "]: "); String name = sc.nextLine().trim();
        if (name.isEmpty()) name = c.getName();
        System.out.print("Nuevo telefono [" + c.getPhone() + "]: "); String phone = sc.nextLine().trim();
        if (phone.isEmpty()) phone = c.getPhone();
        System.out.print("Nueva direccion [" + c.getAddress() + "]: "); String address = sc.nextLine().trim();
        if (address.isEmpty()) address = c.getAddress();
        company.updateCustomer(id, name, phone, address);
        System.out.println("[OK] Datos del cliente actualizados.");
    }

    static void searchCustomer() {
        System.out.print("Ingrese texto de busqueda (Cedula o Nombre): ");
        String q = sc.nextLine().trim();
        List<Customer> list = company.searchCustomers(q);
        if (list.isEmpty()) {
            System.out.println("No se encontraron clientes con el criterio: " + q);
        } else {
            System.out.println("\nResultados encontrados (" + list.size() + "):");
            for (Customer c : list) System.out.println(" - " + c);
        }
    }

    static void listAllCustomers() {
        System.out.println("\n=== LISTADO GENERAL DE CLIENTES ===");
        for (Customer c : company.getCustomers()) {
            System.out.println(" - " + c);
        }
    }

    static void rentalOperationsMenu() {
        int opt;
        do {
            System.out.println("\n--- OPERACIONES DE ALQUILER (E3. Alquiler) ---");
            System.out.println("1. Registrar nuevo contrato de alquiler (HU-06)");
            System.out.println("2. Extender dias de alquiler activo (HU-07)");
            System.out.println("3. Listar alquileres activos");
            System.out.println("4. Historial completo de alquileres");
            System.out.println("5. Volver al menu anterior");
            opt = readInt("Seleccione: ");
            switch (opt) {
                case 1 -> registerRental();
                case 2 -> extendRental();
                case 3 -> listActiveRentals();
                case 4 -> listAllRentals();
                case 5 -> System.out.println("Regresando...");
                default -> System.out.println("Opcion invalida.");
            }
        } while (opt != 5);
    }

    static void registerRental() {
        System.out.println("\n[Registrar Nuevo Contrato de Alquiler - HU-06]");
        System.out.print("Placa del vehiculo a alquilar: "); String plate = sc.nextLine().trim().toUpperCase();
        Vehicle v = company.findVehicle(plate);
        if (v == null) {
            System.out.println("[ERROR] Vehiculo no encontrado.");
            return;
        }
        if (!v.isAvailable()) {
            System.out.println("[ERROR] El vehiculo " + plate + " no esta disponible (Estado: " + v.getStatus() + ").");
            return;
        }

        System.out.print("Cedula del cliente: "); String id = sc.nextLine().trim();
        Customer c = company.findCustomer(id);
        if (c == null) {
            System.out.println("[ERROR] Cliente no encontrado. Registre al cliente antes de crear el contrato.");
            return;
        }

        int days = readInt("Cantidad de dias de alquiler contratados (minimo 1): ");
        if (days <= 0) {
            System.out.println("[ERROR] La duracion debe ser de al menos 1 dia.");
            return;
        }

        LocalDate startDate = LocalDate.now();
        LocalDate dueDate = startDate.plusDays(days);

        Rental r = company.rentVehicle(plate, id, startDate, dueDate, currentUser.getFullName());
        if (r != null) {
            System.out.println("\n=======================================================");
            System.out.println("   CONTRATO DE ALQUILER #" + r.getId() + " GENERADO CON EXITO");
            System.out.println("=======================================================");
            System.out.println("Cliente:        " + c.getName() + " (CC: " + c.getId() + ")");
            System.out.println("Vehiculo:       " + v.getPlate() + " - " + v.getBrand() + " " + v.getModel());
            System.out.println("Fecha inicio:   " + startDate.format(DATE_FMT));
            System.out.println("Fecha entrega:  " + dueDate.format(DATE_FMT) + " (" + days + " dias)");
            System.out.println("Tarifa pactada: $" + String.format("%,.0f", r.getDailyRate()) + " COP/dia");
            System.out.println("Subtotal:       $" + String.format("%,.0f", r.calculateSubtotal()) + " COP");
            System.out.println("Operador:       " + currentUser.getFullName());
            System.out.println("=======================================================");
        } else {
            System.out.println("[ERROR] No se pudo generar el contrato. Verifique datos.");
        }
    }

    static void extendRental() {
        System.out.println("\n[Extender Plazo de Alquiler - HU-07]");
        System.out.print("Placa del vehiculo con alquiler activo: "); String plate = sc.nextLine().trim().toUpperCase();
        Rental r = company.findActiveRental(plate);
        if (r == null) {
            System.out.println("[ERROR] No hay alquiler activo para el vehiculo con placa " + plate);
            return;
        }
        System.out.println("Alquiler actual vence el: " + r.getDueDate().format(DATE_FMT));
        int addDays = readInt("Cuantos dias adicionales desea extender?: ");
        if (addDays <= 0) {
            System.out.println("[ERROR] La extension debe ser de al menos 1 dia.");
            return;
        }
        company.extendRental(plate, addDays);
        System.out.println("[OK] Contrato #" + r.getId() + " extendido exitosamente.");
        System.out.println("Nueva fecha pactada de entrega: " + r.getDueDate().format(DATE_FMT));
    }

    static void listActiveRentals() {
        System.out.println("\n=== ALQUILERES ACTIVOS (EN CURSO) ===");
        int count = 0;
        for (Rental r : company.getRentals()) {
            if (!r.isReturned()) {
                System.out.println(" - " + r);
                count++;
            }
        }
        if (count == 0) System.out.println("No hay alquileres activos actualmente.");
    }

    static void listAllRentals() {
        System.out.println("\n=== HISTORIAL COMPLETO DE ALQUILERES ===");
        for (Rental r : company.getRentals()) {
            System.out.println(" - " + r);
        }
    }

    static void returnsMenu() {
        System.out.println("\n[Registrar Devolucion de Vehiculo - HU-08 / HU-09]");
        System.out.print("Placa del vehiculo a devolver: "); String plate = sc.nextLine().trim().toUpperCase();
        Rental r = company.findActiveRental(plate);
        if (r == null) {
            System.out.println("[ERROR] No se encontro ningun alquiler activo para la placa " + plate);
            return;
        }

        LocalDate returnDate = LocalDate.now();
        long mora = r.calculateOverdueDays(returnDate);
        double penalty = r.calculatePenalty(returnDate, company.getDefaultPenaltyRate());

        System.out.println("\nResumen del Contrato #" + r.getId() + ":");
        System.out.println("Cliente:           " + r.getCustomer().getName());
        System.out.println("Vehiculo:          " + r.getVehicle().getPlate() + " (" + r.getVehicle().getBrand() + ")");
        System.out.println("Fecha pactada:     " + r.getDueDate().format(DATE_FMT));
        System.out.println("Fecha recepcion:   " + returnDate.format(DATE_FMT));

        if (mora > 0) {
            System.out.println("\n>>> ATENCION: EL VEHICULO PRESENTA " + mora + " DIAS DE MORA <<<");
            System.out.println("Tarifa de penalizacion diaria: $" + String.format("%,.0f", company.getDefaultPenaltyRate()) + " COP");
            System.out.println("Total multa por mora:          $" + String.format("%,.0f", penalty) + " COP");
        } else {
            System.out.println("\n[OK] Devolucion a tiempo. Sin penalizacion por mora.");
        }

        System.out.print("\nConfirmar recepcion del vehiculo (S/N)?: ");
        String confirm = sc.nextLine().trim();
        if (confirm.equalsIgnoreCase("S") || confirm.equalsIgnoreCase("SI")) {
            company.registerReturn(plate, returnDate);
            System.out.println("[OK] Devolucion registrada. El vehiculo " + plate + " ahora esta DISPONIBLE.");
            System.out.println("Puede pasar al modulo de FACTURACION para generar el recibo.");
        } else {
            System.out.println("Recepcion cancelada.");
        }
    }

    static void billingMenu() {
        System.out.println("\n[Liquidacion y Facturacion - HU-10]");
        System.out.print("Placa del vehiculo a facturar: "); String plate = sc.nextLine().trim().toUpperCase();

        // Buscar el alquiler más reciente de esa placa
        Rental target = null;
        for (int i = company.getRentals().size() - 1; i >= 0; i--) {
            Rental r = company.getRentals().get(i);
            if (r.getVehicle().getPlate().equalsIgnoreCase(plate)) {
                target = r;
                break;
            }
        }

        if (target == null) {
            System.out.println("[ERROR] No se encontraron registros de alquiler para la placa " + plate);
            return;
        }

        printInvoice(target);
    }

    static void printInvoice(Rental r) {
        LocalDate retDate = (r.getReturnDate() != null) ? r.getReturnDate() : LocalDate.now();
        long days = r.calculateContractedDays();
        double subtotal = r.calculateSubtotal();
        double surcharge = r.getLuxurySurcharge();
        long overdueDays = r.calculateOverdueDays(retDate);
        double penalty = r.calculatePenalty(retDate, company.getDefaultPenaltyRate());
        double grandTotal = r.calculateGrandTotal(retDate, company.getDefaultPenaltyRate());
        double iva = grandTotal * 0.19;

        System.out.println("\n=============================================================");
        System.out.println("                NEIVA RENT A CAR - FACTURA                   ");
        System.out.println("             NIT: " + company.getTaxId() + " | Sede Principal           ");
        System.out.println("         " + company.getAddress() + " | Tel: " + company.getPhone());
        System.out.println("=============================================================");
        System.out.println("Factura N°:         FAC-" + String.format("%04d", r.getId()) + "        Fecha emision: " + LocalDate.now().format(DATE_FMT));
        System.out.println("Cliente:            " + r.getCustomer().getName() + " (CC: " + r.getCustomer().getId() + ")");
        System.out.println("Telefono:           " + r.getCustomer().getPhone());
        System.out.println("Vehiculo:           " + r.getVehicle().getPlate() + " - " + r.getVehicle().getBrand() + " " + r.getVehicle().getModel());
        System.out.println("Gama / Tipo:        " + r.getVehicle().getType());
        System.out.println("Periodo:            " + r.getStartDate().format(DATE_FMT) + " al " + r.getDueDate().format(DATE_FMT) + " (" + days + " dias)");
        if (r.getReturnDate() != null) {
            System.out.println("Fecha de retorno:   " + r.getReturnDate().format(DATE_FMT));
        } else {
            System.out.println("Estado actual:      EN USO (Liquidacion preliminar)");
        }
        System.out.println("-------------------------------------------------------------");
        System.out.println("Subtotal alquiler (" + days + " dias x $" + String.format("%,.0f", r.getDailyRate()) + "):   $" + String.format("%,12.0f", subtotal) + " COP");
        if (surcharge > 0) {
            System.out.println("Recargo de gama alta / lujo:           $" + String.format("%,12.0f", surcharge) + " COP");
        }
        if (overdueDays > 0) {
            System.out.println("Dias de mora (retraso): " + overdueDays + " dias");
            System.out.println("Penalizacion por mora:                 $" + String.format("%,12.0f", penalty) + " COP");
        } else {
            System.out.println("Penalizacion por mora (0 dias):        $" + String.format("%,12.0f", 0.0) + " COP");
        }
        System.out.println("-------------------------------------------------------------");
        System.out.println("Base gravable:                         $" + String.format("%,12.0f", grandTotal) + " COP");
        System.out.println("IVA incluido (19% ref):                $" + String.format("%,12.0f", iva) + " COP");
        System.out.println("=============================================================");
        System.out.println("TOTAL A PAGAR:                         $" + String.format("%,12.0f", grandTotal) + " COP");
        System.out.println("=============================================================");
        System.out.println("Operador responsable: " + r.getOperatorName());
        System.out.println("Resolucion DIAN habilitada | Transaccion completada con exito\n");
    }

    // 6. REPORTES GENERALES
    static void reportsMenu() {
        System.out.println("\n=== REPORTE GENERAL CONSOLIDADO - NEIVA RENT A CAR ===");
        System.out.println("\n1. Flota de Vehiculos (" + company.getVehicles().size() + " registros):");
        for (Vehicle v : company.getVehicles()) {
            System.out.println("   * " + v);
        }

        System.out.println("\n2. Clientes Registrados (" + company.getCustomers().size() + " registros):");
        for (Customer c : company.getCustomers()) {
            System.out.println("   * " + c);
        }

        System.out.println("\n3. Contratos de Alquiler (" + company.getRentals().size() + " registros):");
        for (Rental r : company.getRentals()) {
            System.out.println("   * " + r);
        }
        System.out.println("=======================================================");
    }

    static int readInt(String msg) {
        System.out.print(msg);
        while (!sc.hasNextInt()) {
            System.out.println("[!] Ingrese un numero entero valido.");
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
            System.out.println("[!] Ingrese un numero decimal valido.");
            System.out.print(msg);
            sc.next();
        }
        double v = sc.nextDouble();
        sc.nextLine();
        return v;
    }
}
