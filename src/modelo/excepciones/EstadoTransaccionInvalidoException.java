/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.excepciones;

public class EstadoTransaccionInvalidoException extends BancoRuntimeException {
    public EstadoTransaccionInvalidoException(String estadoOrigen, String estadoDestino) {
        super("Transición de estado inválida: " + estadoOrigen + " → " + estadoDestino);
    }
}
