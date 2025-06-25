/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo.Vehiculos;

import Modelo.Vehiculos.Vehiculo;
import Modelos.CombustiblesCargadores.Cargador;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jmari
 */

import java.util.ArrayList;
import java.util.List;

/**
 * Clase que representa un vehículo eléctrico.
 * Hereda de {@link Vehiculo} e incluye una lista de cargadores compatibles.
 */
public class VehiculoElectrico extends Vehiculo {
    private ArrayList<Cargador> cargadoresCompatibles;

    /**
     *
     */
    public VehiculoElectrico() {
        super();
        this.cargadoresCompatibles = new ArrayList<>();
    }

    /**
     * Constructor que inicializa la marca y modelo del vehículo eléctrico.
     * La lista de cargadores compatibles se inicializa vacía.
     *
     * @param marca Marca del vehículo
     * @param modelo Modelo del vehículo
     * @param cargadoresCompatibles Lista de cargadores compatibles (no se utiliza en este constructor)
     */
    public VehiculoElectrico(String marca, String modelo, List<Cargador> cargadoresCompatibles) {
        super(marca, modelo);
        this.cargadoresCompatibles = new ArrayList<>(cargadoresCompatibles);
    }

    /**
     *
     * @return
     */
    public List<Cargador> getCargadoresCompatibles() {
        return cargadoresCompatibles;
    }

    /**
     *
     * @param cargadoresCompatibles
     */
    public void setCargadoresCompatibles(ArrayList<Cargador> cargadoresCompatibles) {
        this.cargadoresCompatibles = cargadoresCompatibles;
    }

    /**
     * Agrega un cargador a la lista de compatibles si aún no está incluido.
     *
     * @param cargador Cargador a agregar
     */
    public void agregarCargador(Cargador cargador) {
        if (!cargadoresCompatibles.contains(cargador)) {
            cargadoresCompatibles.add(cargador);
        }
    }

    /**
     * Retorna una representación en cadena de los cargadores compatibles.
     *
     * @return Lista de cargadores como String, separados por coma
     */
    public String getTiposDeCargadoresAsString() {
        String str = "";
        for (Cargador cargador : cargadoresCompatibles) {
            str += cargador.toString() + ", ";
        }
        return str.isEmpty() ? "Ninguno" : str;
    }

    /**
     * Indica el tipo de vehículo.
     *
     * @return "Eléctrico"
     */
    @Override
    public String getTipo() {
        return "Eléctrico";
    }

    /**
     * Retorna la información detallada del vehículo eléctrico.
     *
     * @return Información del vehículo en formato legible
     */
    @Override
    public String getFullInfo() {
        return "<html> Marca: " + getMarca() + "\n"
             + "<br>Modelo: " + getModelo() + "\n"
             + "<br>Placa: " + getPlaca() + "\n"
             + "<br>Tipos de Cargadores: " + getTiposDeCargadoresAsString() + "</html>";
    }
}
