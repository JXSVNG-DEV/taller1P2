/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.personas;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import modelo.abstractas.Cuenta;
import modelo.abstractas.Persona;
import modelo.enums.TipoDocumento;
import modelo.excepciones.CapacidadExcedidaException;
import modelo.excepciones.DatoInvalidoException;
import modelo.interfaces.Auditable;
import modelo.interfaces.Consultable;
import modelo.interfaces.Notificable;

public class ClienteNatural extends Persona implements Consultable, Notificable, Auditable {

    private TipoDocumento tipoDocumento;
    private String numeroDocumento;
    private Cuenta[] cuentas;
    private int totalCuentas;
    private static final int MAX_CUENTAS = 5;
    private boolean aceptaNotificaciones;
    private LocalDateTime fechaCreacion;
    private LocalDateTime ultimaModificacion;
    private String usuarioModificacion;

    public ClienteNatural(String id, String nombre, String apellido, LocalDate fechaNacimiento,
                          String email, TipoDocumento tipoDocumento, String numeroDocumento,
                          boolean aceptaNotificaciones) {
        super(id, nombre, apellido, fechaNacimiento, email);
        setTipoDocumento(tipoDocumento);
        setNumeroDocumento(numeroDocumento);
        this.aceptaNotificaciones = aceptaNotificaciones;
        this.cuentas = new Cuenta[MAX_CUENTAS];
        this.totalCuentas = 0;
        this.fechaCreacion = LocalDateTime.now();
        this.ultimaModificacion = LocalDateTime.now();
        this.usuarioModificacion = "SISTEMA";
    }

    //  abstractos de clase Persona
    @Override
    public int calcularEdad() {
        return Period.between(getFechaNacimiento(), LocalDate.now()).getYears();
    }

    @Override
    public String obtenerTipo() {
        return "Cliente Natural";
    }

    @Override
    public String obtenerDocumentoIdentidad() {
        return tipoDocumento.name() + ": " + numeroDocumento;
    }

    // Consultable
    @Override
    public String obtenerResumen() {
        return "ClienteNatural | " + getNombreCompleto() + " | Doc: " + obtenerDocumentoIdentidad()
                + " | Cuentas: " + totalCuentas + " | Edad: " + calcularEdad();
    }

    @Override
    public boolean estaActivo() {
        return true;
    }

    // Notificable
    @Override
    public void notificar(String mensaje) {
        if (aceptaNotificaciones) {
            System.out.println("📩 Notificación para " + getNombreCompleto() + ": " + mensaje);
        } else {
            System.out.println("🔕 " + getNombreCompleto() + " no acepta notificaciones.");
        }
    }

    @Override
    public String obtenerContacto() {
        return getEmail();
    }

    @Override
    public boolean aceptaNotificaciones() {
        return aceptaNotificaciones;
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

    // Gestión de cuentas
    public void agregarCuenta(Cuenta cuenta) throws CapacidadExcedidaException {
        if (totalCuentas >= MAX_CUENTAS) {
            throw new CapacidadExcedidaException("cuentas por cliente", MAX_CUENTAS);
        }
        cuentas[totalCuentas] = cuenta;
        totalCuentas++;
    }

    public Cuenta[] getCuentas() {
        Cuenta[] copia = new Cuenta[totalCuentas];
        System.arraycopy(cuentas, 0, copia, 0, totalCuentas);
        return copia;
    }

    // Setters
 
    public void setTipoDocumento(TipoDocumento tipoDocumento) {
        if (tipoDocumento == null) {
            throw new DatoInvalidoException("tipoDocumento", null);
        }
        this.tipoDocumento = tipoDocumento;
    }

    public void setNumeroDocumento(String numeroDocumento) {
        if (numeroDocumento == null || numeroDocumento.trim().isEmpty()) {
            throw new DatoInvalidoException("numeroDocumento", numeroDocumento);
        }
        this.numeroDocumento = numeroDocumento;
    }

    public TipoDocumento getTipoDocumento() { return tipoDocumento; }
    public String getNumeroDocumento() { return numeroDocumento; }
}
