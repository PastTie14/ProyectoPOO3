/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelos.Ciudades;

import Modelos.CombustiblesCargadores.Cargador;
import Modelos.CombustiblesCargadores.Tipos;
import java.util.ArrayList;

/**
 *
 * @author Ian
 */
/**
 * Clase que representa una estación de carga eléctrica.
 * Extiende de {@code Estacion} y únicamente acepta suministros del tipo {@code Cargador}.
 */
public class EstacionCarga extends Estacion {

    /**
     * Constructor de la estación de carga.
     *
     * @param nombre Nombre de la estación de carga
     */
    public EstacionCarga(String nombre) {
        super(nombre, "Cargadores");
    }

    /**
     * Agrega un suministro a la estación si es del tipo {@code Cargador}.
     *
     * @param suministro Objeto del tipo {@code Tipos} a agregar
     * @return true si se agregó exitosamente, false si no es un cargador
     */
    @Override
    public boolean agregarSuministro(Tipos suministro) {
        if (suministro instanceof Cargador) {
            super.suministros.add(suministro);
            return true;
        }
        return false;
    }
}

