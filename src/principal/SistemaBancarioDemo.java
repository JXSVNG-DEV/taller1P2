/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package principal;

import java.time.LocalDate;
import modelo.banco.Banco;
import modelo.banco.Transaccion;
import modelo.cuentas.CuentaAhorros;
import modelo.cuentas.CuentaCorriente;
import modelo.cuentas.CuentaCredito;
import modelo.empleados.AsesorFinanciero;
import modelo.empleados.Cajero;
import modelo.empleados.GerenteSucursal;
import modelo.enums.EstadoTransaccion;
import modelo.enums.TipoDocumento;
import modelo.enums.Turno;
import modelo.excepciones.CapacidadExcedidaException;
import modelo.excepciones.ClienteNoEncontradoException;
import modelo.excepciones.CuentaBloqueadaException;
import modelo.excepciones.EstadoTransaccionInvalidoException;
import modelo.excepciones.PermisoInsuficienteException;
import modelo.excepciones.SaldoInsuficienteException;
import modelo.personas.ClienteEmpresarial;
import modelo.personas.ClienteNatural;

public class SistemaBancarioDemo {

    public static void main(String[] args) {

        System.out.println("================================");
        System.out.println("   SISTEMA DE GESTION BANCARIA (SGB)  ");
        System.out.println("================================\n");

        Banco banco = new Banco("Banco Nacional");

        // ESCENARIO 1 — Registrar clientes
        System.out.println("== ESCENARIO 1: Registro de clientes ==");
        try {
            ClienteNatural cliente1 = new ClienteNatural(
                    "C001", "Juan", "Perez", LocalDate.of(1990, 5, 15),
                    "juan@email.com", TipoDocumento.CEDULA, "123456789", true
            );
            ClienteNatural cliente2 = new ClienteNatural(
                    "C002", "Maria", "Garcia", LocalDate.of(1985, 8, 22),
                    "maria@email.com", TipoDocumento.CEDULA, "987654321", false
            );
            ClienteEmpresarial cliente3 = new ClienteEmpresarial(
                    "C003", "Carlos", "Lopez", LocalDate.of(1975, 3, 10),
                    "carlos@empresa.com", "900123456-1", "Empresa XYZ S.A.S",
                    "Carlos Lopez", true
            );

            banco.registrarCliente(cliente1);
            banco.registrarCliente(cliente2);
            banco.registrarCliente(cliente3);

        } catch (CapacidadExcedidaException e) {
            System.out.println("Error: " + e.getMessage());
        }

        // ESCENARIO 2 — Abrir cuentas
        System.out.println("\n== ESCENARIO 2: Apertura de cuentas ==");
        CuentaAhorros cuentaAhorros = new CuentaAhorros("AH-001", 1000000, 0.05, 5);
        CuentaCorriente cuentaCorriente = new CuentaCorriente("CC-001", 2000000, 500000, 15000);
        CuentaCredito cuentaCredito = new CuentaCredito("CR-001", 5000000, 0.02);

        try {
            banco.abrirCuenta("C001", cuentaAhorros);
            banco.abrirCuenta("C002", cuentaCorriente);
            banco.abrirCuenta("C003", cuentaCredito);
        } catch (ClienteNoEncontradoException | CapacidadExcedidaException e) {
            System.out.println(" Error: " + e.getMessage());
        }

        // ESCENARIO 3 — Depósito exitoso y cuenta bloqueada
        System.out.println("\n══ ESCENARIO 3: Depositos ══");
        try {
            cuentaAhorros.depositar(500000);
            System.out.println("Deposito exitoso. Saldo actual: $" + cuentaAhorros.consultarSaldo());
        } catch (CuentaBloqueadaException e) {
            System.out.println("Error: " + e.getMessage());
        }

        // Intentar depositar en  unacuenta bloqueada
        CuentaAhorros cuentaBloqueada = new CuentaAhorros("AH-002", 500000, 0.04, 3);
        cuentaBloqueada.setBloqueada(true);
        try {
            cuentaBloqueada.depositar(100000);
        } catch (CuentaBloqueadaException e) {
            System.out.println("Cuenta bloqueada capturada: " + e.getMessage());
        }

        // ESCENARIO 4 — Retiro exitoso y saldo insuficiente
        System.out.println("\n==ESCENARIO 4: Retiros ==");
        try {
            cuentaAhorros.retirar(200000);
            System.out.println(" Retiro exitoso. Saldo actual: $" + cuentaAhorros.consultarSaldo());
        } catch (SaldoInsuficienteException | CuentaBloqueadaException e) {
            System.out.println(" Error: " + e.getMessage());
        }

        // Intentar retirar más del saldo
        try {
            cuentaAhorros.retirar(99999999);
        } catch (SaldoInsuficienteException e) {
            System.out.println(" Saldo insuficiente capturado:");
            System.out.println("   Saldo actual: $" + e.getSaldoActual());
            System.out.println("   Monto solicitado: $" + e.getMontoSolicitado());
        } catch (CuentaBloqueadaException e) {
            System.out.println("Error: " + e.getMessage());
        }

        // ESCENARIO 5 — Transferencia entre cuentas
        System.out.println("\n== ESCENARIO 5: Transferencia ==");
        try {
            double montoTransferencia = 100000;
            cuentaAhorros.retirar(montoTransferencia);
            cuentaCorriente.depositar(montoTransferencia);
            Transaccion transferencia = new Transaccion(
                    "TRX-001", cuentaAhorros, cuentaCorriente,
                    montoTransferencia, "Transferencia entre cuentas"
            );
            transferencia.cambiarEstado(EstadoTransaccion.PROCESANDO);
            transferencia.cambiarEstado(EstadoTransaccion.COMPLETADA);
            System.out.println(" Transferencia completada.");
            System.out.println(transferencia.generarComprobante());
        } catch (SaldoInsuficienteException | CuentaBloqueadaException e) {
            System.out.println("Error en transferencia: " + e.getMessage());
        }

        // ESCENARIO 6 — Polimorfismo en empleados
        System.out.println("\n=== ESCENARIO 6: Salarios de empleados ===");
        Cajero cajero = new Cajero(
                "E001", "Luis", "Martinez", LocalDate.of(1995, 2, 20),
                "luis@banco.com", "LEG-001", LocalDate.of(2020, 1, 15),
                2000000, Turno.MAÑANA, "Sucursal Centro"
        );
        cajero.setTransaccionesDia(30);

        AsesorFinanciero asesor = new AsesorFinanciero(
                "E002", "Ana", "Rodríguez", LocalDate.of(1988, 7, 11),
                "ana@banco.com", "LEG-002", LocalDate.of(2018, 3, 1),
                3000000, 4000000, 3500000
        );

        GerenteSucursal gerente = new GerenteSucursal(
                "E003", "Pedro", "Sánchez", LocalDate.of(1980, 11, 5),
                "pedro@banco.com", "LEG-003", LocalDate.of(2010, 6, 10),
                5000000, "Sucursal Norte", 100000000
        );

        try {
            banco.registrarEmpleado(cajero);
            banco.registrarEmpleado(asesor);
            banco.registrarEmpleado(gerente);
        } catch (CapacidadExcedidaException e) {
            System.out.println("Error: " + e.getMessage());
        }

        modelo.abstractas.Empleado[] empleados = {cajero, asesor, gerente};
        for (modelo.abstractas.Empleado emp : empleados) {
            System.out.println("E" + emp.getNombreCompleto()
                    + " (" + emp.obtenerTipo() + ") → Salario: $" + emp.calcularSalario());
        }

        // ESCENARIO 7 — Polimorfismo en cuentas
        System.out.println("\n== ESCENARIO 7: Intereses por tipo de cuenta ══ ");
        modelo.abstractas.Cuenta[] cuentas = {cuentaAhorros, cuentaCorriente, cuentaCredito};
        for (modelo.abstractas.Cuenta cuenta : cuentas) {
            System.out.println("C " + cuenta.getTipoCuenta()
                    + " (" + cuenta.getNumeroCuenta() + ") → Interés: $" + cuenta.calcularInteres());
        }

        // ESCENARIO 8 — Transición de estado inválida
        System.out.println("\n== ESCENARIO 8: Transicion de estado invalida ==");
        Transaccion transaccion = new Transaccion(
                "TRX-002", cuentaAhorros, null, 50000, "Retiro en cajero"
        );
        transaccion.cambiarEstado(EstadoTransaccion.RECHAZADA);
        try {
            transaccion.cambiarEstado(EstadoTransaccion.COMPLETADA);
        } catch (EstadoTransaccionInvalidoException e) {
            System.out.println("Transicion invalida capturada: " + e.getMessage());
        }

        // ESCENARIO 9 — Permiso insuficiente
        System.out.println("\n== ESCENARIO 9: Permiso insuficiente ==");
        try {
            gerente.aprobarCredito(cajero, 10000000);
        } catch (PermisoInsuficienteException e) {
            System.out.println(" Permiso insuficiente capturado: " + e.getMessage());
        }
        gerente.aprobarCredito(5000000);

        // ESCENARIO 10 — Notificaciones
        System.out.println("\n== ESCENARIO 10: Notificaciones ==");
        try {
            ClienteNatural clienteNotif = (ClienteNatural) banco.buscarCliente("C001");
            ClienteNatural clienteSinNotif = (ClienteNatural) banco.buscarCliente("C002");
            clienteNotif.notificar("Su deposito de $500.000 fue procesado exitosamente.");
            clienteSinNotif.notificar("Su deposito de $200.000 fue procesado exitosamente.");
        } catch (ClienteNoEncontradoException e) {
            System.out.println("Error: " + e.getMessage());
        }

        // ESCENARIO 11 — Auditable
        System.out.println("\n== ESCENARIO 11: Auditoría de cuenta ==");
        cuentaAhorros.registrarModificacion("cajero-LEG001");
        System.out.println("Última modificacion: " + cuentaAhorros.obtenerUltimaModificacion());
        System.out.println(" Usuario modificación: " + cuentaAhorros.obtenerUsuarioModificacion());

        // ESCENARIO 12 — Nómina total
        System.out.println("\n==ESCENARIO 12: Nómina total del banco ==");
        double nominaTotal = banco.calcularNominaTotal();
        System.out.println(" Nómina total del banco: $" + nominaTotal);
        banco.calcularInteresesMensuales();

        System.out.println("\n===============================");
        System.out.println("|        FIN DE LA DEMOSTRACIoN   |     ║");
        System.out.println("=================================");
    }
}
