/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.cuentas;


import java.time.LocalDateTime;
import modelo.abstractas.Cuenta;
import modelo.excepciones.CuentaBloqueadaException;
import modelo.excepciones.DatoInvalidoException;
import modelo.excepciones.SaldoInsuficienteException;
import modelo.interfaces.Auditable;
import modelo.interfaces.Consultable;
import modelo.interfaces.Transaccionable;

public class CuentaCorriente extends Cuenta implements Consultable, Transaccionable, Auditable {

    private double montoSobregiro;
    private double comisionMantenimiento;
    private LocalDateTime fechaCreacion;
    private LocalDateTime ultimaModificacion;
    private String usuarioModificacion;

    public CuentaCorriente(String numeroCuenta, double saldoInicial,
                            double montoSobregiro, double comisionMantenimiento) {
        super(numeroCuenta, saldoInicial);
        setMontoSobregiro(montoSobregiro);
        setComisionMantenimiento(comisionMantenimiento);
        this.fechaCreacion = LocalDateTime.now();
        this.ultimaModificacion = LocalDateTime.now();
        this.usuarioModificacion = "SISTEMA";
    }

    //  abstractos de Cuenta
    @Override
    public double calcularInteres() {
        return -comisionMantenimiento;
    }

    @Override
    public double getLimiteRetiro() {
        return getSaldo() + montoSobregiro;
    }

    @Override
    public String getTipoCuenta() {
        return "Cuenta Corriente";
    }

    // Transaccionable
    @Override
    public void depositar(double monto) throws CuentaBloqueadaException {
        verificarBloqueada();
        if (monto <= 0) {
            throw new DatoInvalidoException("monto", monto);
        }
        setSaldo(getSaldo() + monto);
        setUltimaModificacion(LocalDateTime.now());
    }

    @Override
    public void retirar(double monto) throws SaldoInsuficienteException, CuentaBloqueadaException {
        verificarBloqueada();
        if (monto <= 0) {
            throw new DatoInvalidoException("monto", monto);
        }
        if (monto > getSaldo() + montoSobregiro) {
            throw new SaldoInsuficienteException(getSaldo(), monto);
        }
        setSaldo(getSaldo() - monto);
        setUltimaModificacion(LocalDateTime.now());
    }

    @Override
    public double calcularComision(double monto) {
        return comisionMantenimiento;
    }

    @Override
    public double consultarSaldo() {
        return getSaldo();
    }

    // Consultable
    @Override
    public String obtenerResumen() {
        return "CuentaCorriente | N°: " + getNumeroCuenta() + " | Saldo: $" + getSaldo()
                + " | Sobregiro: $" + montoSobregiro + " | Comisión: $" + comisionMantenimiento;
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
    public double getMontoSobregiro() { return montoSobregiro; }
    public double getComisionMantenimiento() { return comisionMantenimiento; }

    public void setMontoSobregiro(double montoSobregiro) {
        if (montoSobregiro < 0) {
            throw new DatoInvalidoException("montoSobregiro", montoSobregiro);
        }
        this.montoSobregiro = montoSobregiro;
    }

    public void setComisionMantenimiento(double comisionMantenimiento) {
        if (comisionMantenimiento < 0) {
            throw new DatoInvalidoException("comisionMantenimiento", comisionMantenimiento);
        }
        this.comisionMantenimiento = comisionMantenimiento;
    }
}
