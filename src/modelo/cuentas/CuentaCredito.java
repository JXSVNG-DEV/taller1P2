/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.cuentas;


import java.time.LocalDateTime;
import modelo.abstractas.Cuenta;
import modelo.excepciones.CuentaBloqueadaException;
import modelo.excepciones.DatoInvalidoException;
import modelo.interfaces.Auditable;
import modelo.interfaces.Consultable;
import modelo.interfaces.Transaccionable;

public class CuentaCredito extends Cuenta implements Consultable, Transaccionable, Auditable {

    private double limiteCredito;
    private double tasaInteres;
    private double deudaActual;
    private LocalDateTime fechaCreacion;
    private LocalDateTime ultimaModificacion;
    private String usuarioModificacion;

    public CuentaCredito(String numeroCuenta, double limiteCredito, double tasaInteres) {
        super(numeroCuenta, 0);
        setLimiteCredito(limiteCredito);
        setTasaInteres(tasaInteres);
        this.deudaActual = 0;
        this.fechaCreacion = LocalDateTime.now();
        this.ultimaModificacion = LocalDateTime.now();
        this.usuarioModificacion = "SISTEMA";
    }

    //  abstractos de Cuenta
    @Override
    public double calcularInteres() {
        return deudaActual * tasaInteres / 12;
    }

    @Override
    public double getLimiteRetiro() {
        return limiteCredito - deudaActual;
    }

    @Override
    public String getTipoCuenta() {
        return "Cuenta Crédito";
    }

    // Transaccionable
    @Override
    public void depositar(double monto) throws CuentaBloqueadaException {
        verificarBloqueada();
        if (monto <= 0) {
            throw new DatoInvalidoException("monto", monto);
        }
        deudaActual = Math.max(0, deudaActual - monto);
        setUltimaModificacion(LocalDateTime.now());
    }

    @Override
    public void retirar(double monto) throws CuentaBloqueadaException {
        verificarBloqueada();
        if (monto <= 0) {
            throw new DatoInvalidoException("monto", monto);
        }
        if (monto > getLimiteRetiro()) {
            throw new DatoInvalidoException("monto supera límite de crédito disponible", monto);
        }
        deudaActual += monto;
        setUltimaModificacion(LocalDateTime.now());
    }

    @Override
    public double calcularComision(double monto) {
        return monto * 0.03;
    }

    @Override
    public double consultarSaldo() {
        return getLimiteRetiro();
    }

    // Consultable
    @Override
    public String obtenerResumen() {
        return "CuentaCredito | N°: " + getNumeroCuenta() + " | Límite: $" + limiteCredito
                + " | Deuda: $" + deudaActual + " | Disponible: $" + getLimiteRetiro();
    }

    @Override
    public boolean estaActivo() {
        return !isBloqueada();
    }

    @Override
    public String obtenerTipo() {
        return getTipoCuenta();
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
    public double getLimiteCredito() { return limiteCredito; }
    public double getTasaInteres() { return tasaInteres; }
    public double getDeudaActual() { return deudaActual; }

    public void setLimiteCredito(double limiteCredito) {
        if (limiteCredito <= 0) {
            throw new DatoInvalidoException("limiteCredito", limiteCredito);
        }
        this.limiteCredito = limiteCredito;
    }

    public void setTasaInteres(double tasaInteres) {
        if (tasaInteres <= 0) {
            throw new DatoInvalidoException("tasaInteres", tasaInteres);
        }
        this.tasaInteres = tasaInteres;
    }

    public void setDeudaActual(double deudaActual) {
        if (deudaActual < 0) {
            throw new DatoInvalidoException("deudaActual", deudaActual);
        }
        this.deudaActual = deudaActual;
    }
}