/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.excepciones;

public class DatoInvalidoException extends BancoRuntimeException {
    private String campo;
    private Object valorRecibido;

    public DatoInvalidoException(String campo, Object valorRecibido) {
        super("Dato inválido en campo '" + campo + "': " + valorRecibido);
        this.campo = campo;
        this.valorRecibido = valorRecibido;
    }

    public String getCampo() { return campo; }
    public Object getValorRecibido() { return valorRecibido; }

    @Override
    public String toString() {
        return "DatoInvalidoException - Campo: " + campo + ", Valor: " + valorRecibido;
    }
}