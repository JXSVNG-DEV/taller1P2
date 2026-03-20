/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.abstractas;

import java.time.LocalDate;
import java.time.Period;


public abstract class Empleado extends Persona {
    
     
    private int legajo;
    private LocalDate fechaContratacion;
    private double salarioBase;
    private boolean activo;

    
    public Empleado(int id, String nombre, String apellido, LocalDate fechaNacimiento, String email,
                    int legajo, LocalDate fechaContratacion, double salarioBase, boolean activo) {
        
        super(id, nombre, apellido, fechaNacimiento, email);
        this.legajo = legajo;
        this.fechaContratacion = fechaContratacion;
        this.salarioBase = salarioBase;
        this.activo = activo;
    }
    
    
    
    public abstract double calcularSalario();

    
    public int antiguedad() {
        return Period.between(fechaContratacion, LocalDate.now()).getYears();
    }

    
    @Override
    public int calcularEdad() {
        return Period.between(getFechaNacimiento(), LocalDate.now()).getYears();
    }

    @Override
    public String obtenerTipo() {
        return "Empleado";
    }

    
    public int getLegajo() {
        return legajo;
    }

    public void setLegajo(int legajo) {
        this.legajo = legajo;
    }

    public LocalDate getFechaContratacion() {
        return fechaContratacion;
    }

    public void setFechaContratacion(LocalDate fechaContratacion) {
        this.fechaContratacion = fechaContratacion;
    }

    public double getSalarioBase() {
        return salarioBase;
    }

    public void setSalarioBase(double salarioBase) {
        if (salarioBase > 0) {
            this.salarioBase = salarioBase;
        }
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    
    
    
    
    
}
