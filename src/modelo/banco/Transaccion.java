/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.banco;


import java.time.LocalDateTime;
import modelo.abstractas.Cuenta;
import modelo.enums.EstadoTransaccion;
import modelo.excepciones.DatoInvalidoException;
import modelo.excepciones.EstadoTransaccionInvalidoException;

public class Transaccion {

    private String id;
    private Cuenta cuentaOrigen;
    private Cuenta cuentaDestino;
    private double monto;
    private EstadoTransaccion estado;
    private LocalDateTime fecha;
    private String descripcion;

    public Transaccion(String id, Cuenta cuentaOrigen, Cuenta cuentaDestino,
                       double monto, String descripcion) {
        setId(id);
        this.cuentaOrigen = cuentaOrigen;
        this.cuentaDestino = cuentaDestino;
        setMonto(monto);
        this.estado = EstadoTransaccion.PENDIENTE;
        this.fecha = LocalDateTime.now();
        setDescripcion(descripcion);
    }

    public void cambiarEstado(EstadoTransaccion nuevo) {
        boolean transicionValida = false;

        switch (estado) {
            case PENDIENTE:
                transicionValida = nuevo == EstadoTransaccion.PROCESANDO
                        || nuevo == EstadoTransaccion.RECHAZADA;
                break;
            case PROCESANDO:
                transicionValida = nuevo == EstadoTransaccion.COMPLETADA
                        || nuevo == EstadoTransaccion.RECHAZADA;
                break;
            case COMPLETADA:
                transicionValida = nuevo == EstadoTransaccion.REVERTIDA;
                break;
            case RECHAZADA:
            case REVERTIDA:
                transicionValida = false;
                break;
        }

        if (!transicionValida) {
            throw new EstadoTransaccionInvalidoException(estado.name(), nuevo.name());
        }

        this.estado = nuevo;
    }

    public String generarComprobante() {
        return "==== COMPROBANTE DE TRANSACCIÓN ====" +
               "\nID: " + id +
               "\nFecha: " + fecha +
               "\nCuenta Origen: " + (cuentaOrigen != null ? cuentaOrigen.getNumeroCuenta() : "N/A") +
               "\nCuenta Destino: " + (cuentaDestino != null ? cuentaDestino.getNumeroCuenta() : "N/A") +
               "\nMonto: $" + monto +
               "\nEstado: " + estado +
               "\nDescripción: " + descripcion +
               "\n====================================";
    }

    // Getters
    public String getId() { return id; }
    public Cuenta getCuentaOrigen() { return cuentaOrigen; }
    public Cuenta getCuentaDestino() { return cuentaDestino; }
    public double getMonto() { return monto; }
    public EstadoTransaccion getEstado() { return estado; }
    public LocalDateTime getFecha() { return fecha; }
    public String getDescripcion() { return descripcion; }

    // Setters
    public void setId(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new DatoInvalidoException("id", id);
        }
        this.id = id;
    }

    public void setMonto(double monto) {
        if (monto <= 0) {
            throw new DatoInvalidoException("monto", monto);
        }
        this.monto = monto;
    }

    public void setDescripcion(String descripcion) {
        if (descripcion == null || descripcion.trim().isEmpty()) {
            throw new DatoInvalidoException("descripcion", descripcion);
        }
        this.descripcion = descripcion;
    }
}