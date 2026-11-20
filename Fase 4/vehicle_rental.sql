DROP DATABASE IF EXISTS vehicle_rental;
CREATE DATABASE vehicle_rental CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE vehicle_rental;
 
CREATE TABLE companies (
    id          INT AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(100) NOT NULL,
    tax_id      VARCHAR(20)  NOT NULL,            
    address     VARCHAR(150),
    phone       VARCHAR(15),
    CONSTRAINT uq_companies_tax_id UNIQUE (tax_id)
);


CREATE TABLE users (
    id            INT AUTO_INCREMENT PRIMARY KEY,
    company_id    INT          NOT NULL,
    full_name     VARCHAR(100) NOT NULL,
    username      VARCHAR(50)  NOT NULL,
    password_hash VARCHAR(255) NOT NULL,          
    role          VARCHAR(20)  NOT NULL,
    CONSTRAINT uq_users_username UNIQUE (username),
    CONSTRAINT fk_users_company  FOREIGN KEY (company_id) REFERENCES companies (id),
    CONSTRAINT ck_users_role     CHECK (role IN ('ADMIN', 'EMPLOYEE', 'CASHIER'))
);


CREATE TABLE customers (
    document_number VARCHAR(15)  PRIMARY KEY,
    full_name       VARCHAR(100) NOT NULL,
    phone           VARCHAR(15)  NOT NULL,
    address         VARCHAR(150),
    CONSTRAINT ck_customers_document_numeric CHECK (document_number REGEXP '^[0-9]+$'),
    CONSTRAINT ck_customers_phone_min        CHECK (CHAR_LENGTH(phone) >= 7)   
);


CREATE TABLE vehicle_types (
    id          INT AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(50) NOT NULL,
    CONSTRAINT uq_vehicle_types_name UNIQUE (name)
);


CREATE TABLE vehicles (
    plate            VARCHAR(10)   PRIMARY KEY,
    company_id       INT           NOT NULL,
    brand            VARCHAR(50)   NOT NULL,
    model            VARCHAR(50)   NOT NULL,
    vehicle_type_id  INT           NOT NULL,
    daily_rate       DECIMAL(10,2) NOT NULL,
    status           VARCHAR(20)   NOT NULL DEFAULT 'AVAILABLE',
    CONSTRAINT fk_vehicles_company      FOREIGN KEY (company_id)      REFERENCES companies (id),
    CONSTRAINT fk_vehicles_vehicle_type FOREIGN KEY (vehicle_type_id) REFERENCES vehicle_types (id),
    CONSTRAINT ck_vehicles_status   CHECK (status IN ('AVAILABLE', 'RENTED', 'INACTIVE')),
    CONSTRAINT ck_vehicles_rate_positive CHECK (daily_rate > 0)               -- RF-01, HU-01, HU-03
);


CREATE TABLE rentals (
    id                INT AUTO_INCREMENT PRIMARY KEY,
    start_date        DATE          NOT NULL,
    due_date          DATE          NOT NULL,     
    return_date       DATE,                        
    daily_rate        DECIMAL(10,2) NOT NULL,     
    user_id           INT           NOT NULL,
    vehicle_plate     VARCHAR(10)   NOT NULL,
    customer_document VARCHAR(15)   NOT NULL,
    

    active_plate      VARCHAR(10) GENERATED ALWAYS AS (IF(return_date IS NULL, vehicle_plate, NULL)) VIRTUAL,
    CONSTRAINT fk_rentals_user     FOREIGN KEY (user_id)           REFERENCES users (id),
    CONSTRAINT fk_rentals_vehicle  FOREIGN KEY (vehicle_plate)     REFERENCES vehicles (plate),
    CONSTRAINT fk_rentals_customer FOREIGN KEY (customer_document) REFERENCES customers (document_number),
    CONSTRAINT ck_rentals_min_days      CHECK (DATEDIFF(due_date, start_date) >= 1),          
    CONSTRAINT ck_rentals_rate_positive CHECK (daily_rate > 0),
    CONSTRAINT ck_rentals_return_date   CHECK (return_date IS NULL OR return_date >= start_date), 
    CONSTRAINT uq_rentals_one_active_per_vehicle UNIQUE (active_plate)
);


CREATE TABLE penalties (
    id                 INT AUTO_INCREMENT PRIMARY KEY,
    rental_id          INT           NOT NULL,
    daily_penalty_rate DECIMAL(10,2) NOT NULL,     
    CONSTRAINT fk_penalties_rental FOREIGN KEY (rental_id) REFERENCES rentals (id),
    CONSTRAINT uq_penalties_rental UNIQUE (rental_id),
    CONSTRAINT ck_penalties_rate_positive CHECK (daily_penalty_rate > 0)       
);


CREATE TABLE invoices (
    id               INT AUTO_INCREMENT PRIMARY KEY,
    rental_id        INT           NOT NULL,
    issue_date       DATE          NOT NULL,
    luxury_surcharge DECIMAL(10,2) NOT NULL DEFAULT 0,   
    CONSTRAINT fk_invoices_rental FOREIGN KEY (rental_id) REFERENCES rentals (id),
    CONSTRAINT uq_invoices_rental UNIQUE (rental_id),
    CONSTRAINT ck_invoices_surcharge_non_negative CHECK (luxury_surcharge >= 0)
);


CREATE INDEX idx_vehicles_status    ON vehicles (status);              
CREATE INDEX idx_rentals_customer   ON rentals (customer_document);     
CREATE INDEX idx_vehicles_company   ON vehicles (company_id);


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


INSERT INTO companies (name, tax_id, address, phone) VALUES
('Neiva Rent a Car', '900123456-7', 'Cra 5 # 10-20, Neiva', '3201234567');

INSERT INTO users (company_id, full_name, username, password_hash, role) VALUES
(1, 'Laura Méndez', 'lmendez', '$2a$10$examplehash1', 'ADMIN'),
(1, 'Carlos Rojas', 'crojas',  '$2a$10$examplehash2', 'EMPLOYEE'),
(1, 'Diana Torres', 'dtorres', '$2a$10$examplehash3', 'CASHIER');

INSERT INTO vehicle_types (name) VALUES
('ECONOMY'),
('MID_RANGE'),
('LUXURY');

INSERT INTO vehicles (plate, company_id, brand, model, vehicle_type_id, daily_rate, status) VALUES
('ABC123', 1, 'Chevrolet', 'Spark',   1, 90000.00,  'AVAILABLE'),
('DEF456', 1, 'Mazda',     'CX-30',   2, 150000.00, 'AVAILABLE'),
('GHI789', 1, 'BMW',       'Serie 4', 3, 350000.00, 'AVAILABLE'),
('JKL012', 1, 'Renault',   'Logan',   1, 85000.00,  'RENTED');

INSERT INTO customers (document_number, full_name, phone, address) VALUES
('1075634521', 'Andrés Felipe Cuellar', '3124567890', 'Cra 5 # 10-20, Neiva'),
('52741963',   'Marcela Rincón',        '3009876543', 'Calle 8 # 3-14, Neiva');


INSERT INTO rentals (start_date, due_date, return_date, daily_rate, user_id, vehicle_plate, customer_document) VALUES
('2026-09-01', '2026-09-04', '2026-09-04', 90000.00, 2, 'ABC123', '1075634521');

INSERT INTO rentals (start_date, due_date, return_date, daily_rate, user_id, vehicle_plate, customer_document) VALUES
('2026-09-05', '2026-09-08', '2026-09-10', 350000.00, 3, 'GHI789', '52741963');

INSERT INTO rentals (start_date, due_date, return_date, daily_rate, user_id, vehicle_plate, customer_document) VALUES
('2026-09-12', '2026-09-15', NULL, 85000.00, 2, 'JKL012', '1075634521');

INSERT INTO penalties (rental_id, daily_penalty_rate) VALUES (2, 50000.00);

INSERT INTO invoices (rental_id, issue_date, luxury_surcharge) VALUES
(1, '2026-09-04', 0.00),
(2, '2026-09-10', 100000.00);


-- ============================================================================
-- CONSULTAS DE VERIFICACIÓN Y AUDITORÍA (CONSISTENCIA CON RFs Y HUs)
-- ============================================================================

-- Consulta 1: Búsqueda parcial de vehículos disponibles por marca, modelo o placa (RF-02, HU-02)
-- Permite al operador consultar la flota disponible filtrando por coincidencias parciales de texto.
SELECT v.plate,
       v.brand,
       v.model,
       vt.name AS vehicle_type,
       v.daily_rate,
       v.status
FROM vehicles v
JOIN vehicle_types vt ON v.vehicle_type_id = vt.id
WHERE v.status = 'AVAILABLE'
  AND (v.brand LIKE '%Mazda%' OR v.model LIKE '%Spark%' OR v.plate LIKE '%ABC%');

-- Consulta 2: Búsqueda predictiva de clientes por coincidencia en nombre o documento (RF-03, HU-04, HU-05)
-- Soporta la búsqueda asistida en el formulario de nuevos contratos sin exigir digitación manual exacta.
SELECT document_number,
       full_name,
       phone,
       address
FROM customers
WHERE full_name LIKE '%Andrés%'
   OR document_number LIKE '%1075%';

-- Consulta 3: Verificación de alquileres activos con datos del vehículo, cliente y operador (RF-04, HU-06)
SELECT r.id AS rental_id,
       r.start_date,
       r.due_date,
       r.return_date,
       v.plate,
       CONCAT(v.brand, ' ', v.model) AS vehicle,
       c.full_name AS customer_name,
       c.phone AS customer_phone,
       u.full_name AS operator_name
FROM rentals r
JOIN vehicles v    ON r.vehicle_plate = v.plate
JOIN customers c   ON r.customer_document = c.document_number
JOIN users u       ON r.user_id = u.id
WHERE r.return_date IS NULL;

-- Consulta 4: Auditoría de liquidación, recargo de lujo y mora calculada (RF-06, RF-07, HU-09, HU-10)
-- Valida el cálculo automático de días de mora y recargo mediante la vista consolidada.
SELECT invoice_id,
       issue_date,
       rental_id,
       customer_document,
       vehicle_plate,
       rental_days,
       subtotal,
       luxury_surcharge,
       penalty_total,
       grand_total
FROM v_invoice_totals;

