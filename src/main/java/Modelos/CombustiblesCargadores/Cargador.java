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
 * Representa un tipo de cargador eléctrico con un nombre específico.
 */
public class Cargador extends Tipos {
    String nombre;

    /**
     * Constructor que inicializa el tipo como "Cargador" y asigna el nombre.
     * 
     * @param nombre Nombre del cargador
     */
    public Cargador(String nombre) {
        super("Cargador");
        this.nombre = nombre;
    }

    /**
     *
     * @return
     */
    public String getNombre() {
        return nombre;
    }

    /**
     *
     * @param nombre
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return nombre;
    }
}
