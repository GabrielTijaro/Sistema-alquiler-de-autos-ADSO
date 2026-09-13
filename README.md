## Trazabilidad: Código vs Historias de Usuario HUs
El diseño orientado a objetos de este proyecto  responde directamente a las necesidades del negocio documentadas en las Historias de Usuario:

* **Clase `Vehicle.java` y `LuxuryVehicle.java`**: 
  Resuelven la **Épica 1 (Gestión de Flota)**. Encapsulan la lógica para registrar vehículos (HU-01), validar tarifas > 0, y calcular el sobrecargo polimórfico si el auto es de lujo (HU-10).
* **Clase `Customer.java`**: 
  Resuelve la **Épica 2 (Gestión de Clientes)**. Gestiona la unicidad del documento y los datos de contacto para la HU-04 y HU-05.
* **Clase `Rental.java`**: 
  Resuelve la **Épica 3 (Operaciones de Alquiler)** y parte de la **Épica 4**. Controla la lógica de vinculación entre cliente y vehículo (HU-06), extensión de días (HU-07) y contiene el algoritmo para calcular días de mora al momento de la devolución (HU-09).
* **Clase `Company.java`**: 
  Actúa como el controlador principal que orquesta las colecciones en memoria, permitiendo las consultas de disponibilidad (HU-02) y generando la facturación final (HU-10).
