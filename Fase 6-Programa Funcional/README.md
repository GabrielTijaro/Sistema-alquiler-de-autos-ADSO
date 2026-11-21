# Fase 6: Programa Funcional — Neiva Rent a Car
## Aplicación Web Operativa Integral (Fase 6)

**Programa de Formación:** Tecnólogo en Análisis y Desarrollo de Software (ADSO)  
**Servicio Nacional de Aprendizaje (SENA):** Regional Huila — Centro de la Industria, la Empresa y los Servicios (CIES)  
**Ficha:** 3239188 | **Grupo:** Grupo 06  
**Autores:** Gabriel Tijaro Jiménez, Juan Camilo Gaitán  
**Instructor Evaluador:** Carlos Julio Cadena  
**Rama de Evaluación:** `develop`  
**Tecnologías:** HTML5 Semántico, Tailwind CSS (RWD), Vanilla JavaScript Moderno (ES6+), Persistencia de Datos `localStorage`, Java 17 / Maven.

---

## 1. Presentación de la Aplicación Funcional

La **Fase 6 (Programa Funcional)** representa el sistema en pleno funcionamiento con una **interfaz gráfica interactiva y profesional** basada en el sistema de diseño corporativo de *Neiva Rent a Car*.

A diferencia de los prototipos estáticos de la Fase 5, esta versión es **100% interactiva y funcional**:
* **Gestión de Sesión con Roles (RBAC - `HU-00 / RF-00`):** Inicio de sesión con validación para `ADMIN` (Laura Méndez), `EMPLOYEE` (Carlos Rojas) y `CASHIER` (Diana Torres).
* **Panel de Control Dinámico:** Tarjetas KPI en tiempo real (Total Flota, Disponibles, Alquilados, En Mora) y alertas preventivas.
* **Flota con Bajas Lógicas (`HU-01`, `HU-02`, `HU-03`):** Registro de nuevos autos con cálculo de tarifas, búsqueda predictiva instantánea (`LIKE`), y acción funcional de **"Dar de baja"** que pasa el vehículo al estado `INACTIVO` e impide alquilarlo.
* **Gestión de Clientes (`HU-04`, `HU-05`):** Formulario para registrar arrendatarios con cédula, teléfono y dirección.
* **Contratos de Alquiler y Cálculo en COP (`HU-06`, `HU-07`):** Selección de vehículos disponibles, cliente, fechas con calendario dinámico y cálculo automático del subtotal. Incluye extensión de plazos pactados.
* **Devoluciones con Detección Automática de Mora (`HU-08`, `HU-09`):** Al recibir el auto, el sistema detecta los días de retraso y liquida la multa por mora ($50.000 COP/día, correspondiente al script SQL de Fase 4).
* **Facturación Formal (`HU-10 / RF-07`):** Recibo corporativo listo para imprimir o guardar en PDF con desglose de días contratados, recargo de lujo, penalización de mora, IVA discriminado (19%) y total a pagar.
* **Persistencia de Datos:** Todos los registros nuevos o cambios de estado se guardan en el navegador (`localStorage`) y vienen precargados con datos semilla de Neiva para probar de inmediato.

---

## 2. Cómo Probar el Programa (Sin Terminal)

Tienes dos formas sencillas de probar la aplicación en tu navegador web (Google Chrome, Firefox o Edge):

### Opción 1: Doble Clic Directo (Recomendada)
Simplemente entra a la carpeta **`Fase 6-Programa Funcional/`** y haz **doble clic sobre el archivo `index.html`**. Se abrirá de inmediato en tu navegador predeterminado.

### Opción 2: Mediante el Lanzador
Desde la terminal o haciendo clic derecho en el archivo:
```bash
./"Fase 6-Programa Funcional/abrir_en_navegador.sh"
```

---

## 3. Credenciales de Acceso para la Evaluación (HU-00)

En la pantalla de inicio puedes hacer clic en los botones de acceso rápido o ingresar manualmente:

| Usuario | Contraseña | Nombre del Operador | Rol Asignado |
| :--- | :---: | :--- | :---: |
| **`lmendez`** | `admin123` | Laura Méndez | **Administradora** |
| **`crojas`** | `emp123` | Carlos Rojas | **Asesor / Empleado** |
| **`dtorres`** | `cash123` | Diana Torres | **Cajera** |

---

## 4. Guía Rápida de Pruebas para la Sustentación

1. **Paso 1: Ingreso y Revisión de Flota**
   * Haz clic en el botón rápido **Admin (Laura M.)** y pulsa **"Ingresar al Sistema"**.
   * En el Panel de Control verás la alerta roja que indica que hay un vehículo con entrega retrasada.
   * Ve al menú **"Gestión de Flota"**: Verás la tabla con los vehículos iniciales (Spark `ABC123`, Mazda CX-30 `DEF456`, BMW `GHI789`, etc.).
   * En el buscador escribe `Mazda` o `Spark` y observa el filtrado predictivo en tiempo real.
   * En el vehículo `ABC123`, haz clic en **"Dar de baja"** y confirma en el modal: verás que su estado cambia inmediatamente a **`INACTIVO (BAJA)`**.
2. **Paso 2: Registrar un Nuevo Alquiler (`HU-06`)**
   * Ve a **"Alquileres y Contratos"** y haz clic en **"+ Crear Nuevo Contrato"**.
   * Selecciona el vehículo disponible (ej. Mazda CX-30), el cliente y define las fechas: verás cómo se calcula automáticamente el subtotal en COP.
   * Pulsa **"Emitir Contrato"**: El vehículo pasa a estado `ALQUILADO` y se suma a la tabla.
3. **Paso 3: Registrar Devolución con Mora y Factura (`HU-08`, `HU-09`, `HU-10`)**
   * En la tabla de alquileres, ubica el contrato **#3** (Placa `JKL012` - Renault Logan), el cual tiene entrega vencida.
   * Haz clic en el botón azul **"Recibir / Retorno"**:
     * El sistema muestra el modal detectando automáticamente:  
       `Penalización por mora detectada: 2 días ($ 100.000 COP)`.
     * Haz clic en **"Completar Recepción"**.
   * Inmediatamente se abre la **Factura Electrónica Corporativa**:
     * Desglose de los 3 días de alquiler: $255.000 COP.
     * Penalización por mora (2 días): $100.000 COP.
     * Total Liquidado: **$355.000 COP**.
     * Botón para **Imprimir o Guardar en PDF**.

---

## 5. Matriz de Trazabilidad Técnica (Fase 1 a Fase 6)

| Módulo Visual | Historia de Usuario | Requisito Funcional | Tabla BD (Fase 4) | Funcionalidad Implementada |
| :--- | :---: | :---: | :--- | :--- |
| **Login** | `HU-00` | `RF-00` | `users` | Autenticación con roles `ADMIN`, `EMPLOYEE`, `CASHIER`. |
| **Flota** | `HU-01`, `HU-02` | `RF-01`, `RF-02` | `vehicles`, `vehicle_types` | CRUD de vehículos y búsqueda predictiva con coincidencia parcial. |
| **Baja Lógica** | `HU-03` | `RF-01` | `vehicles.status` | Desactivación lógica a estado `INACTIVE` con modal protector. |
| **Clientes** | `HU-04`, `HU-05` | `RF-03` | `customers` | Registro y consulta con cédula, teléfono y dirección. |
| **Contratos** | `HU-06`, `HU-07` | `RF-04`, `RF-08` | `rentals` | Creación de contrato con fechas y extensión de días pactados. |
| **Devoluciones** | `HU-08` | `RF-05` | `rentals.return_date` | Cierre de contrato y retorno del vehículo a `AVAILABLE`. |
| **Penalizaciones**| `HU-09` | `RF-06` | `penalties` | Detección automática de mora y cobro de penalización ($50.000 COP/d). |
| **Facturación** | `HU-10` | `RF-07` | `invoices`, `v_invoice_totals` | Liquidación con desglose de IVA 19% y formato de recibo formal. |
