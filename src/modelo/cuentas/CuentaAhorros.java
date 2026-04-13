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

public class CuentaAhorros extends Cuenta implements Consultable, Transaccionable, Auditable {

    private double tasaInteres;
    private int retirosMesActual;
    private int maxRetirosMes;
    private LocalDateTime fechaCreacion;
    private LocalDateTime ultimaModificacion;
    private String usuarioModificacion;

    public CuentaAhorros(String numeroCuenta, double saldoInicial, double tasaInteres, int maxRetirosMes) {
        super(numeroCuenta, saldoInicial);
        setTasaInteres(tasaInteres);
        setMaxRetirosMes(maxRetirosMes);
        this.retirosMesActual = 0;
        this.fechaCreacion = LocalDateTime.now();
        this.ultimaModificacion = LocalDateTime.now();
        this.usuarioModificacion = "SISTEMA";
    }

    // abstractos de Cuenta
    @Override
    public double calcularInteres() {
        return getSaldo() * tasaInteres / 12;
    }

    @Override
    public double getLimiteRetiro() {
        return getSaldo();
    }

    @Override
    public String getTipoCuenta() {
        return "Cuenta de Ahorros";
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
        if (monto > getSaldo()) {
            throw new SaldoInsuficienteException(getSaldo(), monto);
        }
        setSaldo(getSaldo() - monto);
        retirosMesActual++;
        setUltimaModificacion(LocalDateTime.now());
    }

    @Override
    public double calcularComision(double monto) {
        return retirosMesActual > maxRetirosMes ? monto * 0.02 : 0;
    }

    @Override
    public double consultarSaldo() {
        return getSaldo();
    }

    // Consultable
    @Override
    public String obtenerResumen() {
        return "CuentaAhorros | N°: " + getNumeroCuenta() + " | Saldo: $" + getSaldo()
                + " | Tasa: " + (tasaInteres * 100) + "% | Retiros mes: " + retirosMesActual;
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
    public double getTasaInteres() { return tasaInteres; }
    public int getRetirosMesActual() { return retirosMesActual; }
    public int getMaxRetirosMes() { return maxRetirosMes; }

    public void setTasaInteres(double tasaInteres) {
        if (tasaInteres <= 0) {
            throw new DatoInvalidoException("tasaInteres", tasaInteres);
        }
        this.tasaInteres = tasaInteres;
    }

    public void setMaxRetirosMes(int maxRetirosMes) {
        if (maxRetirosMes <= 0) {
            throw new DatoInvalidoException("maxRetirosMes", maxRetirosMes);
        }
        this.maxRetirosMes = maxRetirosMes;
    }
}
