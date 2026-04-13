/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.banco;


import java.time.LocalDateTime;
import modelo.abstractas.Cuenta;
import modelo.abstractas.Empleado;
import modelo.abstractas.Persona;
import modelo.excepciones.CapacidadExcedidaException;
import modelo.excepciones.ClienteNoEncontradoException;
import modelo.excepciones.DatoInvalidoException;
import modelo.interfaces.Auditable;
import modelo.personas.ClienteEmpresarial;
import modelo.personas.ClienteNatural;

public class Banco implements Auditable {

    private String nombre;
    private Empleado[] empleados;
    private Persona[] clientes;
    private Cuenta[] cuentas;
    private int totalEmpleados;
    private int totalClientes;
    private int totalCuentas;
    private static final int MAX_EMPLEADOS = 50;
    private static final int MAX_CLIENTES = 200;
    private static final int MAX_CUENTAS = 500;
    private LocalDateTime fechaCreacion;
    private LocalDateTime ultimaModificacion;
    private String usuarioModificacion;

    public Banco(String nombre) {
        setNombre(nombre);
        this.empleados = new Empleado[MAX_EMPLEADOS];
        this.clientes = new Persona[MAX_CLIENTES];
        this.cuentas = new Cuenta[MAX_CUENTAS];
        this.totalEmpleados = 0;
        this.totalClientes = 0;
        this.totalCuentas = 0;
        this.fechaCreacion = LocalDateTime.now();
        this.ultimaModificacion = LocalDateTime.now();
        this.usuarioModificacion = "SISTEMA";
    }

    // Gestión de clientes
    public void registrarCliente(Persona cliente) throws CapacidadExcedidaException {
        if (totalClientes >= MAX_CLIENTES) {
            throw new CapacidadExcedidaException("clientes del banco", MAX_CLIENTES);
        }
        clientes[totalClientes] = cliente;
        totalClientes++;
        System.out.println("✅ Cliente registrado: " + cliente.getNombreCompleto());
    }

    public Persona buscarCliente(String id) throws ClienteNoEncontradoException {
        for (int i = 0; i < totalClientes; i++) {
            if (clientes[i].getId().equals(id)) {
                return clientes[i];
            }
        }
        throw new ClienteNoEncontradoException(id);
    }

    // Gestión de empleados
    public void registrarEmpleado(Empleado empleado) throws CapacidadExcedidaException {
        if (totalEmpleados >= MAX_EMPLEADOS) {
            throw new CapacidadExcedidaException("empleados del banco", MAX_EMPLEADOS);
        }
        empleados[totalEmpleados] = empleado;
        totalEmpleados++;
        System.out.println("✅ Empleado registrado: " + empleado.getNombreCompleto());
    }

    // Gestión de cuentas
    public void abrirCuenta(String idCliente, Cuenta cuenta)
            throws ClienteNoEncontradoException, CapacidadExcedidaException {
        Persona persona = buscarCliente(idCliente);

        if (totalCuentas >= MAX_CUENTAS) {
            throw new CapacidadExcedidaException("cuentas del banco", MAX_CUENTAS);
        }

        if (persona instanceof ClienteNatural) {
            ((ClienteNatural) persona).agregarCuenta(cuenta);
        } else if (persona instanceof ClienteEmpresarial) {
            ((ClienteEmpresarial) persona).agregarCuenta(cuenta);
        }

        cuentas[totalCuentas] = cuenta;
        totalCuentas++;
        System.out.println("✅ Cuenta abierta: " + cuenta.getNumeroCuenta()
                + " para cliente: " + persona.getNombreCompleto());
    }

    // Polimorfismo 
    public double calcularNominaTotal() {
        double total = 0;
        for (int i = 0; i < totalEmpleados; i++) {
            total += empleados[i].calcularSalario();
        }
        return total;
    }

    // Polimorfismo
    public void calcularInteresesMensuales() {
        System.out.println("\n📊 Cálculo de intereses mensuales:");
        for (int i = 0; i < totalCuentas; i++) {
            double interes = cuentas[i].calcularInteres();
            System.out.println("  Cuenta " + cuentas[i].getNumeroCuenta()
                    + " (" + cuentas[i].getTipoCuenta() + "): $" + interes);
        }
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

    // Getters y Setters
    public String getNombre() { return nombre; }
    public int getTotalEmpleados() { return totalEmpleados; }
    public int getTotalClientes() { return totalClientes; }
    public int getTotalCuentas() { return totalCuentas; }

    public Empleado[] getEmpleados() {
        Empleado[] copia = new Empleado[totalEmpleados];
        System.arraycopy(empleados, 0, copia, 0, totalEmpleados);
        return copia;
    }

    public Persona[] getClientes() {
        Persona[] copia = new Persona[totalClientes];
        System.arraycopy(clientes, 0, copia, 0, totalClientes);
        return copia;
    }

    public Cuenta[] getCuentas() {
        Cuenta[] copia = new Cuenta[totalCuentas];
        System.arraycopy(cuentas, 0, copia, 0, totalCuentas);
        return copia;
    }

    public void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new DatoInvalidoException("nombre", nombre);
        }
        this.nombre = nombre;
    }
}