# Sistema de Gestión Bancaria (SGB)

**Asignatura:** Programación Orientada a Objetos  
**Tema:** Clases Abstractas, Interfaces, Excepciones, Herencia y Polimorfismo  
**Lenguaje:** Java 17

---

## Datos del Estudiante

| Campo | Estudiante 1 | Estudiante 2 |
|---|---|---|
| Nombre | *(José A. Peralta Aguilar)* | *(Sebastián Jiménez)* |
| Código | *(0000069078)* | *(0000068588)* |
| Grupo | *(2703)* | *(2703)* |

---

## Estructura del Proyecto

SistemaBancario/
├── src/
│   ├── modelo/
│   │   ├── abstractas/     → Persona.java, Empleado.java, Cuenta.java
│   │   ├── personas/       → ClienteNatural.java, ClienteEmpresarial.java
│   │   ├── empleados/      → Cajero.java, AsesorFinanciero.java, GerenteSucursal.java
│   │   ├── cuentas/        → CuentaAhorros.java, CuentaCorriente.java, CuentaCredito.java
│   │   ├── banco/          → Banco.java, Transaccion.java
│   │   ├── interfaces/     → Consultable.java, Transaccionable.java, Auditable.java, Notificable.java
│   │   ├── excepciones/    → SistemaBancarioException.java y subclases
│   │   └── enums/          → EstadoTransaccion.java, TipoCuenta.java, Turno.java, TipoDocumento.java
│   └── principal/
│       └── SistemaBancarioDemo.java
└── docs/
├── casos_de_uso.puml
├── diagrama_clases.puml
└── README.md

---

## Tabla de Historias de Usuario

| ID | Historia | Criterios de Aceptación | Prioridad | Story Points |
|---|---|---|---|---|
| HU-01 | Como cajero, quiero registrar un cliente natural | Valida documento único, lanza CapacidadExcedidaException si hay límite, lanza DatoInvalidoException si hay nulos | Alta | 3 |
| HU-02 | Como cajero, quiero registrar un cliente empresarial | Valida NIT único, lanza excepciones correspondientes | Alta | 3 |
| HU-03 | Como cajero, quiero abrir una cuenta de ahorros | Asocia cuenta al cliente, máximo 5 cuentas por cliente | Alta | 3 |
| HU-04 | Como cajero, quiero abrir una cuenta corriente | Configura sobregiro y comisión de mantenimiento | Alta | 2 |
| HU-05 | Como gerente, quiero abrir una cuenta de crédito | Solo el gerente puede aprobar créditos | Alta | 3 |
| HU-06 | Como cajero, quiero realizar un depósito | Valida cuenta no bloqueada, actualiza saldo | Alta | 2 |
| HU-07 | Como cajero, quiero realizar un retiro | Valida saldo suficiente y cuenta no bloqueada | Alta | 3 |
| HU-08 | Como asesor, quiero realizar una transferencia | Retira de origen, deposita en destino, registra transacción | Alta | 5 |
| HU-09 | Como asesor, quiero bloquear una cuenta | Cambia estado bloqueada=true, impide operaciones | Media | 2 |
| HU-10 | Como asesor, quiero cambiar el estado de una transacción | Valida transiciones válidas, lanza EstadoTransaccionInvalidoException | Media | 3 |
| HU-11 | Como gerente, quiero calcular la nómina total | Usa polimorfismo sobre array de empleados | Media | 2 |
| HU-12 | Como gerente, quiero calcular intereses mensuales | Usa polimorfismo sobre array de cuentas | Media | 2 |
| HU-13 | Como sistema, quiero notificar al cliente | Solo notifica si aceptaNotificaciones=true | Baja | 1 |
| HU-14 | Como auditor, quiero consultar historial de modificaciones | Retorna fecha y usuario de última modificación | Baja | 2 |

---

## Lista de Verificación Pre-Entrega

- [x] Las clases Persona, Empleado y Cuenta son abstractas con al menos un método abstracto
- [x] Todas las clases concretas implementan los métodos abstractos de sus padres
- [x] Todas las clases hijas usan super() en su constructor
- [x] Los getters de arrays retornan copia con System.arraycopy()
- [x] Las 4 interfaces tienen implementación real en todas las clases indicadas
- [x] Jerarquía de excepciones checked con base en SistemaBancarioException
- [x] Jerarquía de excepciones unchecked con base en BancoRuntimeException
- [x] Los setters lanzan DatoInvalidoException ante datos inválidos
- [x] cambiarEstado() lanza EstadoTransaccionInvalidoException en transiciones inválidas
- [x] La clase principal demuestra los 12 escenarios
- [x] El código compila y ejecuta sin errores

---

