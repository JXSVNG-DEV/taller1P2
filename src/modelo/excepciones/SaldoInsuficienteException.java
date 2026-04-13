/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.excepciones;


public class SaldoInsuficienteException extends SistemaBancarioException {
     private double saldoActual;
    private double montoSolicitado;

    public SaldoInsuficienteException(double saldoActual, double montoSolicitado) {
        super("Saldo insuficiente. Saldo actual: " + saldoActual + ", Monto solicitado: " + montoSolicitado, "SALDO_INSUFICIENTE");
        this.saldoActual = saldoActual;
        this.montoSolicitado = montoSolicitado;
    }

    public double getSaldoActual() { return saldoActual; }
    public double getMontoSolicitado() { return montoSolicitado; }

    @Override
    public String toString() {
        return "SaldoInsuficienteException - Saldo: " + saldoActual + ", Solicitado: " + montoSolicitado;
    }
}
