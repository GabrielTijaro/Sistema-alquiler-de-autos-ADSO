-- =====================================================================
-- Vehicle Rental Management System — Phase 4: Relational Database
-- Target engine: MySQL 8.0.16+ (CHECK constraints are enforced from this version)
-- Design rules: 3NF, plural table names, English column names
-- Run with:  mysql -u root -p < vehicle_rental.sql
-- =====================================================================

DROP DATABASE IF EXISTS vehicle_rental;
CREATE DATABASE vehicle_rental CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE vehicle_rental;

-- ---------------------------------------------------------------------
-- 1. TABLES
-- ---------------------------------------------------------------------

-- Rental company (maps to the Java class Company): owns the fleet and employs the users
CREATE TABLE companies (
    id          INT AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(100) NOT NULL,
    tax_id      VARCHAR(20)  NOT NULL,            -- NIT
    address     VARCHAR(150),
    phone       VARCHAR(15),
    CONSTRAINT uq_companies_tax_id UNIQUE (tax_id)
);

-- System users with a role (RNF-05: access control)
CREATE TABLE users (
    id            INT AUTO_INCREMENT PRIMARY KEY,
    company_id    INT          NOT NULL,
    full_name     VARCHAR(100) NOT NULL,
    username      VARCHAR(50)  NOT NULL,
    password_hash VARCHAR(255) NOT NULL,          -- never plain text
    role          VARCHAR(20)  NOT NULL,
    CONSTRAINT uq_users_username UNIQUE (username),
    CONSTRAINT fk_users_company  FOREIGN KEY (company_id) REFERENCES companies (id),
    CONSTRAINT ck_users_role     CHECK (role IN ('ADMIN', 'EMPLOYEE', 'CASHIER'))
);

-- Customers: natural PK = national ID number (RN-03: unique and mandatory)
CREATE TABLE customers (
    document_number VARCHAR(15)  PRIMARY KEY,
    full_name       VARCHAR(100) NOT NULL,
    phone           VARCHAR(15)  NOT NULL,
    address         VARCHAR(150),
    CONSTRAINT ck_customers_document_numeric CHECK (document_number REGEXP '^[0-9]+$'),
    CONSTRAINT ck_customers_phone_min        CHECK (CHAR_LENGTH(phone) >= 7)   -- HU-05
);

-- Vehicles: natural PK = license plate. LUXURY category = Java class LuxuryVehicle
CREATE TABLE vehicles (
    plate       VARCHAR(10)   PRIMARY KEY,
    company_id  INT           NOT NULL,
    brand       VARCHAR(50)   NOT NULL,
    model       VARCHAR(50)   NOT NULL,
    category    VARCHAR(20)   NOT NULL,
    daily_rate  DECIMAL(10,2) NOT NULL,
    status      VARCHAR(20)   NOT NULL DEFAULT 'AVAILABLE',
    CONSTRAINT fk_vehicles_company FOREIGN KEY (company_id) REFERENCES companies (id),
    CONSTRAINT ck_vehicles_category CHECK (category IN ('ECONOMY', 'MID_RANGE', 'LUXURY')),
    CONSTRAINT ck_vehicles_status   CHECK (status IN ('AVAILABLE', 'RENTED')),
    CONSTRAINT ck_vehicles_rate_positive CHECK (daily_rate > 0)               -- RF-01, HU-01
);

-- Rentals: central table. A rental is ACTIVE while return_date IS NULL (no stored status
-- column, so there is no dependency between non-key attributes).
-- rental days are derived: DATEDIFF(due_date, start_date)
CREATE TABLE rentals (
    id                INT AUTO_INCREMENT PRIMARY KEY,
    start_date        DATE          NOT NULL,
    due_date          DATE          NOT NULL,      -- agreed return date (HU-07 extends it)
    return_date       DATE,                        -- NULL while the rental is active
    daily_rate        DECIMAL(10,2) NOT NULL,      -- rate agreed at signing (historical value)
    user_id           INT           NOT NULL,
    vehicle_plate     VARCHAR(10)   NOT NULL,
    customer_document VARCHAR(15)   NOT NULL,
    -- Helper column: holds the plate only while the rental is active; the UNIQUE index below
    -- then prevents two active rentals for the same vehicle (RN-01) at database level.
    active_plate      VARCHAR(10) GENERATED ALWAYS AS (IF(return_date IS NULL, vehicle_plate, NULL)) VIRTUAL,
    CONSTRAINT fk_rentals_user     FOREIGN KEY (user_id)           REFERENCES users (id),
    CONSTRAINT fk_rentals_vehicle  FOREIGN KEY (vehicle_plate)     REFERENCES vehicles (plate),
    CONSTRAINT fk_rentals_customer FOREIGN KEY (customer_document) REFERENCES customers (document_number),
    CONSTRAINT ck_rentals_min_days      CHECK (DATEDIFF(due_date, start_date) >= 1),          -- RN-02
    CONSTRAINT ck_rentals_rate_positive CHECK (daily_rate > 0),
    CONSTRAINT ck_rentals_return_date   CHECK (return_date IS NULL OR return_date >= start_date), -- RF-06 flow A1
    CONSTRAINT uq_rentals_one_active_per_vehicle UNIQUE (active_plate)
);

-- Penalties: composition with rentals (1 : 0..1). days_late and the total are NOT stored:
-- days_late = GREATEST(DATEDIFF(return_date, due_date), 0); total = days_late * daily_penalty_rate
CREATE TABLE penalties (
    id                 INT AUTO_INCREMENT PRIMARY KEY,
    rental_id          INT           NOT NULL,
    daily_penalty_rate DECIMAL(10,2) NOT NULL,     -- rate in force when the penalty was calculated
    CONSTRAINT fk_penalties_rental FOREIGN KEY (rental_id) REFERENCES rentals (id),
    CONSTRAINT uq_penalties_rental UNIQUE (rental_id),
    CONSTRAINT ck_penalties_rate_positive CHECK (daily_penalty_rate > 0)       -- RN-04
);

-- Invoices: composition with rentals (1 : 1). Only non-derivable facts are stored;
-- subtotal, penalty total and grand total are exposed by the view v_invoice_totals.
CREATE TABLE invoices (
    id               INT AUTO_INCREMENT PRIMARY KEY,
    rental_id        INT           NOT NULL,
    issue_date       DATE          NOT NULL,
    luxury_surcharge DECIMAL(10,2) NOT NULL DEFAULT 0,   -- amount charged for LUXURY vehicles
    CONSTRAINT fk_invoices_rental FOREIGN KEY (rental_id) REFERENCES rentals (id),
    CONSTRAINT uq_invoices_rental UNIQUE (rental_id),
    CONSTRAINT ck_invoices_surcharge_non_negative CHECK (luxury_surcharge >= 0)
);

-- ---------------------------------------------------------------------
-- 2. INDEXES
-- ---------------------------------------------------------------------
CREATE INDEX idx_vehicles_status    ON vehicles (status);               -- availability query (RF-02)
CREATE INDEX idx_rentals_customer   ON rentals (customer_document);     -- history per customer
CREATE INDEX idx_vehicles_company   ON vehicles (company_id);

-- ---------------------------------------------------------------------
-- 3. VIEW: derived values (keeps the tables in 3NF)
-- ---------------------------------------------------------------------
CREATE VIEW v_invoice_totals AS
SELECT  i.id                                                        AS invoice_id,
        i.issue_date,
        r.id                                                        AS rental_id,
        r.customer_document,
        r.vehicle_plate,
        DATEDIFF(r.due_date, r.start_date)                          AS rental_days,
        DATEDIFF(r.due_date, r.start_date) * r.daily_rate           AS subtotal,
        i.luxury_surcharge,
        COALESCE(GREATEST(DATEDIFF(r.return_date, r.due_date), 0) * p.daily_penalty_rate, 0)
                                                                    AS penalty_total,
        DATEDIFF(r.due_date, r.start_date) * r.daily_rate
          + i.luxury_surcharge
          + COALESCE(GREATEST(DATEDIFF(r.return_date, r.due_date), 0) * p.daily_penalty_rate, 0)
                                                                    AS grand_total
FROM invoices i
JOIN rentals r        ON r.id = i.rental_id
LEFT JOIN penalties p ON p.rental_id = r.id;

-- ---------------------------------------------------------------------
-- 4. TEST DATA (each rental covers a different acceptance scenario)
-- ---------------------------------------------------------------------
INSERT INTO companies (name, tax_id, address, phone) VALUES
('Neiva Rent a Car', '900123456-7', 'Cra 5 # 10-20, Neiva', '3201234567');

INSERT INTO users (company_id, full_name, username, password_hash, role) VALUES
(1, 'Laura Méndez', 'lmendez', '$2a$10$examplehash1', 'ADMIN'),
(1, 'Carlos Rojas', 'crojas',  '$2a$10$examplehash2', 'EMPLOYEE'),
(1, 'Diana Torres', 'dtorres', '$2a$10$examplehash3', 'CASHIER');

INSERT INTO vehicles (plate, company_id, brand, model, category, daily_rate, status) VALUES
('ABC123', 1, 'Chevrolet', 'Spark',   'ECONOMY',   90000.00,  'AVAILABLE'),
('DEF456', 1, 'Mazda',     'CX-30',   'MID_RANGE', 150000.00, 'AVAILABLE'),
('GHI789', 1, 'BMW',       'Serie 4', 'LUXURY',    350000.00, 'AVAILABLE'),
('JKL012', 1, 'Renault',   'Logan',   'ECONOMY',   85000.00,  'RENTED');

INSERT INTO customers (document_number, full_name, phone, address) VALUES
('1075634521', 'Andrés Felipe Cuellar', '3124567890', 'Cra 5 # 10-20, Neiva'),
('52741963',   'Marcela Rincón',        '3009876543', 'Calle 8 # 3-14, Neiva');

-- Rental 1: returned on time, no penalty, economy vehicle
INSERT INTO rentals (start_date, due_date, return_date, daily_rate, user_id, vehicle_plate, customer_document) VALUES
('2026-09-01', '2026-09-04', '2026-09-04', 90000.00, 2, 'ABC123', '1075634521');
-- Rental 2: returned 2 days late, luxury vehicle (penalty + surcharge)
INSERT INTO rentals (start_date, due_date, return_date, daily_rate, user_id, vehicle_plate, customer_document) VALUES
('2026-09-05', '2026-09-08', '2026-09-10', 350000.00, 3, 'GHI789', '52741963');
-- Rental 3: still active (vehicle JKL012 is RENTED)
INSERT INTO rentals (start_date, due_date, return_date, daily_rate, user_id, vehicle_plate, customer_document) VALUES
('2026-09-12', '2026-09-15', NULL, 85000.00, 2, 'JKL012', '1075634521');

INSERT INTO penalties (rental_id, daily_penalty_rate) VALUES (2, 50000.00);

INSERT INTO invoices (rental_id, issue_date, luxury_surcharge) VALUES
(1, '2026-09-04', 0.00),
(2, '2026-09-10', 100000.00);

-- ---------------------------------------------------------------------
-- 5. VERIFICATION QUERIES
-- ---------------------------------------------------------------------
-- Available vehicles (RF-02):
-- SELECT plate, brand, daily_rate FROM vehicles WHERE status = 'AVAILABLE' ORDER BY plate;
-- Invoice breakdown (RF-07): expected grand totals 270000.00 and 1250000.00
-- SELECT * FROM v_invoice_totals ORDER BY invoice_id;
-- Active rental of a plate (RF-05):
-- SELECT * FROM rentals WHERE vehicle_plate = 'JKL012' AND return_date IS NULL;
