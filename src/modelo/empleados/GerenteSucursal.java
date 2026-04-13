/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.empleados;

import java.time.LocalDate;
import java.time.LocalDateTime;
import modelo.abstractas.Empleado;
import modelo.excepciones.CapacidadExcedidaException;
import modelo.excepciones.DatoInvalidoException;
import modelo.excepciones.PermisoInsuficienteException;
import modelo.interfaces.Auditable;
import modelo.interfaces.Consultable;

public class GerenteSucursal extends Empleado implements Consultable, Auditable {

    private String sucursal;
    private double presupuestoAnual;
    private Empleado[] empleadosACargo;
    private int totalEmpleados;
    private static final int MAX_EMPLEADOS = 30;
    private static final double BONO_GERENCIA = 500000.0;
    private LocalDateTime fechaCreacion;
    private LocalDateTime ultimaModificacion;
    private String usuarioModificacion;

    public GerenteSucursal(String id, String nombre, String apellido, LocalDate fechaNacimiento,
                            String email, String legajo, LocalDate fechaContratacion,
                            double salarioBase, String sucursal, double presupuestoAnual) {
        super(id, nombre, apellido, fechaNacimiento, email, legajo, fechaContratacion, salarioBase);
        setSucursal(sucursal);
        setPresupuestoAnual(presupuestoAnual);
        this.empleadosACargo = new Empleado[MAX_EMPLEADOS];
        this.totalEmpleados = 0;
        this.fechaCreacion = LocalDateTime.now();
        this.ultimaModificacion = LocalDateTime.now();
        this.usuarioModificacion = "SISTEMA";
    }

    //  abstractos de Empleado
    @Override
    public double calcularBono() {
        return (calcularAntigüedad() * 50000.0) + BONO_GERENCIA;
    }

    @Override
    public double calcularSalario() {
        return getSalarioBase() + calcularBono();
    }

    //  abstractos de Persona
    @Override
    public String obtenerTipo() {
        return "Gerente de Sucursal";
    }

    @Override
    public String obtenerDocumentoIdentidad() {
        return "Legajo: " + getLegajo();
    }

    // Consultable
    @Override
    public String obtenerResumen() {
        return "GerenteSucursal | " + getNombreCompleto() + " | Sucursal: " + sucursal
                + " | Antigüedad: " + calcularAntigüedad() + " años | Salario: $" + calcularSalario();
    }

    @Override
    public boolean estaActivo() {
        return isActivo();
    }

    // Auditable
    @Override
    public LocalDateTime obtenerFechaCreacion() {
        return fechaCreacion;
    }

    @Override
    public LocalDateTime obtenerUltimaModificacion() {
        return ultimaModificacion;
    }

    @Override
    public String obtenerUsuarioModificacion() {
        return usuarioModificacion;
    }

    @Override
    public void registrarModificacion(String usuario) {
        if (usuario == null || usuario.trim().isEmpty()) {
            throw new DatoInvalidoException("usuario", usuario);
        }
        this.ultimaModificacion = LocalDateTime.now();
        this.usuarioModificacion = usuario;
    }

    // Método de gerente
    public void aprobarCredito(Empleado solicitante, double monto) {
        throw new PermisoInsuficienteException("aprobar crédito — solo el Gerente de Sucursal puede realizar esta acción");
    }

    public void aprobarCredito(double monto) {
        System.out.println("✅ Crédito aprobado por " + getNombreCompleto() + " por valor de $" + monto);
    }

    // Gestión de empleados a cargo
    public void agregarEmpleado(Empleado empleado) throws CapacidadExcedidaException {
        if (totalEmpleados >= MAX_EMPLEADOS) {
            throw new CapacidadExcedidaException("empleados a cargo", MAX_EMPLEADOS);
        }
        empleadosACargo[totalEmpleados] = empleado;
        totalEmpleados++;
    }

    public Empleado[] getEmpleadosACargo() {
        Empleado[] copia = new Empleado[totalEmpleados];
        System.arraycopy(empleadosACargo, 0, copia, 0, totalEmpleados);
        return copia;
    }

    // Getters y Setters
    public String getSucursal() { return sucursal; }
    public double getPresupuestoAnual() { return presupuestoAnual; }

    public void setSucursal(String sucursal) {
        if (sucursal == null || sucursal.trim().isEmpty()) {
            throw new DatoInvalidoException("sucursal", sucursal);
        }
        this.sucursal = sucursal;
    }

    public void setPresupuestoAnual(double presupuestoAnual) {
        if (presupuestoAnual < 0) {
            throw new DatoInvalidoException("presupuestoAnual", presupuestoAnual);
        }
        this.presupuestoAnual = presupuestoAnual;
    }
}