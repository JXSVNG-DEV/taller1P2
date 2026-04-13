/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package modelo.interfaces;

import modelo.excepciones.CuentaBloqueadaException;
import modelo.excepciones.SaldoInsuficienteException;

public interface Transaccionable {
    void depositar(double monto) throws CuentaBloqueadaException;
    void retirar(double monto) throws SaldoInsuficienteException, CuentaBloqueadaException;
    double calcularComision(double monto);
    double consultarSaldo();
}
