/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.excepciones;

public class ClienteNoEncontradoException extends SistemaBancarioException{
    private String idCliente;

    public ClienteNoEncontradoException(String idCliente) {
        super("No se encontró el cliente con ID: " + idCliente, "CLIENTE_NO_ENCONTRADO");
        this.idCliente = idCliente;
    }

    public String getIdCliente() { return idCliente; }
}
