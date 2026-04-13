/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.excepciones;


public class CapacidadExcedidaException extends SistemaBancarioException {
     private int capacidadMaxima;

    public CapacidadExcedidaException(String entidad, int capacidadMaxima) {
        super("Se alcanzó la capacidad máxima de " + capacidadMaxima + " para: " + entidad, "CAPACIDAD_EXCEDIDA");
        this.capacidadMaxima = capacidadMaxima;
    }

    public int getCapacidadMaxima() { return capacidadMaxima; }

}
