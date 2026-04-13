/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.excepciones;


public class CuentaBloqueadaException extends SistemaBancarioException{
    public CuentaBloqueadaException(String numeroCuenta) {
        super("La cuenta " + numeroCuenta + " está bloqueada.", "CUENTA_BLOQUEADA");
    }
}
