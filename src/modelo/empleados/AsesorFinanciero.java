/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.empleados;


import java.time.LocalDate;
import java.time.LocalDateTime;
import modelo.abstractas.Empleado;
import modelo.abstractas.Persona;
import modelo.excepciones.CapacidadExcedidaException;
import modelo.excepciones.DatoInvalidoException;
import modelo.interfaces.Auditable;
import modelo.interfaces.Consultable;

public class AsesorFinanciero extends Empleado implements Consultable, Auditable {

    private double comisionBase;
    private double metasMensuales;
    private Persona[] clientesAsignados;
    private int totalClientes;
    private static final int MAX_CLIENTES = 20;
    private LocalDateTime fechaCreacion;
    private LocalDateTime ultimaModificacion;
    private String usuarioModificacion;

    public AsesorFinanciero(String id, String nombre, String apellido, LocalDate fechaNacimiento,
                             String email, String legajo, LocalDate fechaContratacion,
                             double salarioBase, double comisionBase, double metasMensuales) {
        super(id, nombre, apellido, fechaNacimiento, email, legajo, fechaContratacion, salarioBase);
        setComisionBase(comisionBase);
        setMetasMensuales(metasMensuales);
        this.clientesAsignados = new Persona[MAX_CLIENTES];
        this.totalClientes = 0;
        this.fechaCreacion = LocalDateTime.now();
        this.ultimaModificacion = LocalDateTime.now();
        this.usuarioModificacion = "SISTEMA";
    }

    // abstractos de Empleado
    @Override
    public double calcularBono() {
        return comisionBase > metasMensuales ? comisionBase * 0.10 : 0;
    }

    @Override
    public double calcularSalario() {
        return getSalarioBase() + calcularBono();
    }

    // abstractos de Persona
    @Override
    public String obtenerTipo() {
        return "Asesor Financiero";
    }

    @Override
    public String obtenerDocumentoIdentidad() {
        return "Legajo: " + getLegajo();
    }

    // Consultable
    @Override
    public String obtenerResumen() {
        return "AsesorFinanciero | " + getNombreCompleto() + " | Clientes: " + totalClientes
                + " | Meta: $" + metasMensuales + " | Salario: $" + calcularSalario();
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

    // Gestión de clientes asignados
    public void asignarCliente(Persona cliente) throws CapacidadExcedidaException {
        if (totalClientes >= MAX_CLIENTES) {
            throw new CapacidadExcedidaException("clientes por asesor", MAX_CLIENTES);
        }
        clientesAsignados[totalClientes] = cliente;
        totalClientes++;
    }

    public Persona[] getClientesAsignados() {
        Persona[] copia = new Persona[totalClientes];
        System.arraycopy(clientesAsignados, 0, copia, 0, totalClientes);
        return copia;
    }

    // Getters y Setters
    public double getComisionBase() { return comisionBase; }
    public double getMetasMensuales() { return metasMensuales; }

    public void setComisionBase(double comisionBase) {
        if (comisionBase < 0) {
            throw new DatoInvalidoException("comisionBase", comisionBase);
        }
        this.comisionBase = comisionBase;
    }

    public void setMetasMensuales(double metasMensuales) {
        if (metasMensuales < 0) {
            throw new DatoInvalidoException("metasMensuales", metasMensuales);
        }
        this.metasMensuales = metasMensuales;
    }
}