/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelos.Ciudades.Estaciones;

import Modelos.CombustiblesCargadores.Combustible;
import Modelos.CombustiblesCargadores.Tipos;

/**
 *
 * @author Ian
 */
/**
 * Clase que representa una estación de combustible.
 * Extiende de {@code Estacion} y solo acepta suministros del tipo {@code Combustible}.
 */
public class EstacionCombustible extends Estacion {

    /**
     * Constructor de la estación de combustible.
     *
     * @param nombre Nombre de la estación de combustible
     */
    public EstacionCombustible(String nombre) {
        super(nombre, "Combustibles");
    }

    /**
     * Agrega un suministro a la estación si es del tipo {@code Combustible}.
     *
     * @param suministro Objeto del tipo {@code Tipos} a agregar
     * @return true si se agregó correctamente, false si no es un combustible
     */
    @Override
    public boolean agregarSuministro(Tipos suministro) {
        if (suministro instanceof Combustible) {
            super.suministros.add(suministro);
            return true;
        }
        return false;
    }
}

