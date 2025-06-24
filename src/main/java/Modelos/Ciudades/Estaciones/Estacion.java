/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelos.Ciudades.Estaciones;
import Modelos.CombustiblesCargadores.Tipos;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;

/**
 *
 * @author Sebas Masís
 */
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Clase abstracta que representa una estación dentro de una ciudad.
 * Puede ser una estación de combustible o de carga eléctrica.
 * Cada estación tiene un nombre, un tipo y una lista de suministros (tipos compatibles).
 */
public abstract class Estacion implements Serializable {
    private String nombre;
    private String tipoEstacion;

    /**
     *
     */
    protected ArrayList<Tipos> suministros;

    /**
     * Constructor de la estación.
     *
     * @param nombre Nombre de la estación
     * @param tipoEstacion Tipo de estación (e.g., "Combustibles", "Cargadores")
     */
    public Estacion(String nombre, String tipoEstacion) {
        this.nombre = nombre;
        this.tipoEstacion = tipoEstacion;
        suministros = new ArrayList<>();
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
     * @return
     */
    public String getTipoEstacion() {
        return tipoEstacion;
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
     * @param tipoEstacion
     */
    public void setTipoEstacion(String tipoEstacion) {
        this.tipoEstacion = tipoEstacion;
    }

    /**
     * Establece la lista de suministros compatibles desde un arreglo de objetos.
     *
     * @param array Arreglo de objetos del tipo {@code Tipos} que representan los suministros disponibles
     */
    public void setSuministros(Object[] array) {
        suministros.clear();
        for (Object object : array) {
            suministros.add((Tipos) object);
        }
    }

    /**
     * Método abstracto que debe implementar cada tipo de estación.
     * Se encarga de agregar un suministro si es válido.
     *
     * @param suministro Objeto del tipo {@code Tipos} a agregar
     * @return true si se agregó correctamente, false si no
     */
    public abstract boolean agregarSuministro(Tipos suministro);

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return nombre + ",Tipo: " + tipoEstacion;
    }
}
