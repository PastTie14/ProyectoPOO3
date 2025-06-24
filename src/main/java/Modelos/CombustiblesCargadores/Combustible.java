/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelos.CombustiblesCargadores;

/**
 *
 * @author Ian
 */
/**
 * Representa un tipo de combustible con un nombre específico.
 */
public class Combustible extends Tipos {
    String nombre;

    /**
     * Constructor que inicializa el tipo como "Combustible" y asigna el nombre.
     * 
     * @param nombre Nombre del combustible
     */
    public Combustible(String nombre) {
        super("Combustible");
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return nombre;
    }
}

