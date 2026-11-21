# Fase 6: Programa Funcional — Neiva Rent a Car
## Sistema de Gestión de Alquiler de Vehículos (MVP)

**Programa de Formación:** Tecnólogo en Análisis y Desarrollo de Software (ADSO)  
**Servicio Nacional de Aprendizaje (SENA):** Regional Huila — Centro de la Industria, la Empresa y los Servicios (CIES)  
**Ficha:** 3239188 | **Grupo:** Grupo 06  
**Autores:** Gabriel Tijaro Jiménez, Juan Camilo Gaitán  
**Instructor Evaluador:** Carlos Julio Cadena  
**Rama de Evaluación:** `develop`  
**Tecnologías:** Java 17 (OpenJDK), Apache Maven, POO (Herencia, Polimorfismo, Encapsulamiento), API de fechas `java.time.LocalDate`.

---

## 1. Presentación y Alcance de la Fase 6

La **Fase 6 (Programa Funcional)** materializa el paso definitivo del diseño a la **implementación ejecutable**. Representa el software en funcionamiento que integra de forma coherente las fases previas del ciclo formativo:
* **Fase 1 y 2 (Requerimientos y Metodología):** Cobertura estricta de las 13 Historias de Usuario priorizadas con MoSCoW y los 8 Requisitos Funcionales del documento `REQUERIMIENTOS DEL SISTEMA.pdf` (SRS bajo estándar IEEE 830) y `docs/fase1_fase2_especificacion_autenticacion.md`.
* **Fase 3 (Diseño UML):** Implementación de clases POO correspondientes a `Vehicle`, `LuxuryVehicle`, `Customer`, `Rental`, `Company` y `User`.
* **Fase 4 (Base de Datos Relacional 3FN):** Correspondencia de entidades, tipos de datos y reglas de negocio con `Fase 4/vehicle_rental.sql` (baja lógica `INACTIVE`, recargo de lujo y penalización de mora a $50.000 COP/día).
* **Fase 5 (Mockup UX/UI):** Reflejo funcional en consola de los flujos interactivos validados en el prototipo responsivo web.

---

## 2. Instrucciones Rápidas de Ejecución

Para facilitar la evaluación al instructor, el sistema puede ejecutarse mediante cualquiera de los siguientes dos métodos:

### Método A: Ejecución en 1 Clic (Recomendado sin necesidad de IDE)
Desde la terminal en la raíz del repositorio, ejecuta:
```bash
./"Fase 6-Programa Funcional/ejecutar.sh"
```
O directamente con la máquina virtual de Java:
```bash
java -jar "Fase 6-Programa Funcional/NeivaRentACar.jar"
```

### Método B: Ejecución desde NetBeans / Maven (Código Fuente)
El proyecto cuenta con su archivo `pom.xml` en la raíz. Para compilar y correr desde consola:
```bash
# Compilar clases
javac -d target/classes src/main/java/com/mycompany/alquiler_autosv2/*.java

# Ejecutar clase principal
java -cp target/classes com.mycompany.alquiler_autosv2.Alquiler_autosv2
```
O simplemente abrir el repositorio en **Apache NetBeans** y pulsar la tecla **`F6`** (Run Project).

---

## 3. Matriz de Autenticación y Usuarios de Prueba (HU-00 / RF-00)

El sistema incorpora control de acceso basado en roles (**RBAC - Role-Based Access Control**). Al arrancar, solicita credenciales predeterminadas para cada actor del negocio:

| Usuario (`username`) | Contraseña | Nombre Completo | Rol del Sistema | Alcance y Permisos en el Menú |
| :--- | :---: | :--- | :---: | :--- |
| **`lmendez`** | `admin123` | Laura Méndez | **`ADMIN`** | **Acceso total:** Gestión de flota (crear, actualizar, dar de baja `HU-03`), clientes, contratos, liquidaciones y reportes consolidados. |
| **`crojas`** | `emp123` | Carlos Rojas | **`EMPLOYEE`** | **Operaciones de Asesor:** Consultar flota disponible, registrar clientes, generar contratos de alquiler y extender plazos (`HU-07`). |
| **`dtorres`** | `cash123` | Diana Torres | **`CASHIER`** | **Operaciones de Caja:** Registrar devolución con verificación automática de mora y emisión de la factura final desglosada en COP (`HU-10`). |

---

## 4. Matriz de Trazabilidad 1 a 1 (SRS vs. Código Java)

| Módulo / Menú | Historia de Usuario | Requerimiento Funcional | Clase Java Responsable | Regla de Negocio / Implementación |
| :--- | :---: | :---: | :--- | :--- |
| **Inicio de Sesión** | `HU-00` | `RF-00` | `User.java`, `Alquiler_autosv2.java` | Autenticación RBAC para roles `ADMIN`, `EMPLOYEE`, `CASHIER`. |
| **Registro de Flota** | `HU-01` | `RF-01` | `Vehicle.java`, `Company.java` | Registro de autos con placa única, marca, modelo, gama y tarifa/día. |
| **Consulta de Flota** | `HU-02` | `RF-02` | `Company.java` (`searchVehicles`) | Búsqueda predictiva con filtros por placa, marca, modelo o tipo (`LIKE`). |
| **Baja Lógica** | `HU-03` | `RF-01` | `Vehicle.java` (`deactivate`) | Cambio de estado a `INACTIVE` sin borrar registros históricos; impide alquiler de autos inactivos. |
| **Gestión de Clientes**| `HU-04`, `HU-05`| `RF-03` | `Customer.java`, `Company.java` | Registro y actualización con validación de cédula/documento único y dirección. |
| **Contrato de Alquiler**| `HU-06` | `RF-04` | `Rental.java`, `Company.java` | Contrato con fechas reales (`LocalDate`), cálculo de días e indisponibilidad del auto (`RENTED`). |
| **Extensión de Alquiler**| `HU-07` | `RF-08` | `Rental.java` (`extendDueDate`) | Adición de días al plazo pactado recalculando la fecha de entrega. |
| **Devolución de Auto** | `HU-08` | `RF-05` | `Rental.java` (`registerReturn`) | Retorno del auto liberándolo a `AVAILABLE` y registro de fecha real de entrega. |
| **Cálculo de Mora** | `HU-09` | `RF-06` | `Rental.java` (`calculatePenalty`)| Detección de días de retraso (`ChronoUnit.DAYS`) y cobro de penalización ($50.000 COP/día). |
| **Facturación Final** | `HU-10` | `RF-07` | `Alquiler_autosv2.java` (`printInvoice`)| Recibo formal en COP: subtotal, recargo de lujo, mora, IVA discriminado y total liquidado. |
| **Vehículos de Lujo** | `HU-01` | `RF-01` | `LuxuryVehicle.java` | Herencia POO que sobreescribe `calculateDailyRate()` sumando tarifa base + recargo. |
| **Reportes Generales** | `HU-13` | — | `Company.java`, `Alquiler_autosv2.java` | Auditoría consolidada de inventario, clientes y estados de contratos. |

---

## 5. Flujo de Demostración Guiado para Evaluación

Para verificar en vivo el funcionamiento del programa ante el instructor:

1. **Paso 1: Demostración de Roles y Flota (Usuario `lmendez` / `admin123`)**
   * Inicia sesión como Administradora.
   * Selecciona **Opción 6 (Reportes Consolidados)**: Observa los 5 vehículos pre-cargados (Mazda CX-30, Renault Logan, BMW Serie 4, etc.) y los clientes de prueba con cédulas colombianas.
   * Selecciona **Opción 1 > Opción 3 (Dar de baja vehiculo)**: Digita la placa `ABC123`. Confirma con `S`. Verifica que su estado pasa inmediatamente a `INACTIVE`.
   * Cierra sesión (Opción 7).

2. **Paso 2: Demostración de Contrato de Alquiler (Usuario `crojas` / `emp123`)**
   * Inicia sesión como Asesor. Observa que el menú se adapta automáticamente mostrando solo sus funciones operativas.
   * Selecciona **Opción 3 (Registrar Nuevo Contrato)**:
     * Placa: `DEF456` (Mazda CX-30 disponible).
     * Cédula: `1075634521` (Andrés Felipe Cuellar).
     * Días: `4`.
   * El sistema genera el **Contrato #4** calculando automáticamente el subtotal en COP y marcando el vehículo como `RENTED`.
   * Cierra sesión (Opción 6).

3. **Paso 3: Demostración de Devolución con Mora y Facturación (Usuario `dtorres` / `cash123`)**
   * Inicia sesión como Cajera.
   * Selecciona **Opción 1 (Registrar Devolucion)** y digita la placa **`JKL012`** (alquiler de prueba en mora):
     * El sistema detecta inmediatamente:  
       `>>> ATENCION: EL VEHICULO PRESENTA 2 DIAS DE MORA <<<`  
       `Total multa por mora: $ 100.000 COP (2 días x $ 50.000 COP)`
     * Confirma la recepción con `S`. El vehículo regresa al estado `AVAILABLE`.
   * Selecciona **Opción 2 (Generar Factura Final)** para la placa **`JKL012`**:
     * El sistema imprime la factura formal desglosando los 3 días contratados ($255.000 COP), la penalización por mora ($100.000 COP) y el total liquidado de **$355.000 COP**.

---

## 6. Principios de Ingeniería de Software Aplicados

* **Paradigma Orientado a Objetos (POO):**
  * **Encapsulamiento:** Atributos privados con métodos de acceso controlados en todas las entidades.
  * **Herencia y Polimorfismo:** `LuxuryVehicle` extiende de `Vehicle`, redefiniendo el cálculo de tarifa diaria y agregando recargos específicos.
  * **Manejo de Fechas Inmutables:** Uso exclusivo de `java.time.LocalDate` y `ChronoUnit` para cálculos cronológicos precisos sin inconsistencias horarias.
* **Trazabilidad 3FN y Base de Datos:**
  * Nomenclatura, estados y tarifas sincronizados de forma matemática con el script `Fase 4/vehicle_rental.sql`.
