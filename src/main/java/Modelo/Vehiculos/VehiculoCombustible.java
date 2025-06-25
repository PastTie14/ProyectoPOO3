/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo.Vehiculos;

import Modelo.Vehiculos.Vehiculo;
import Modelos.CombustiblesCargadores.Combustible;

/**
 *
 * @author jmari
 */

/**
 * Clase que representa un vehículo que funciona con combustible.
 * Hereda de {@link Vehiculo} e incluye el tipo de combustible que utiliza.
 */
public class VehiculoCombustible extends Vehiculo {
    private Combustible tipoCombustible;

    /**
     *
     */
    public VehiculoCombustible() {
        super();
    }

    /**
     * Constructor que inicializa los atributos del vehículo a combustible.
     *
     * @param marca Marca del vehículo
     * @param modelo Modelo del vehículo
     * @param tipoCombustible Tipo de combustible utilizado
     */
    public VehiculoCombustible(String marca, String modelo, Combustible tipoCombustible) {
        super(marca, modelo);
        this.tipoCombustible = tipoCombustible;
    }

    /**
     *
     * @return
     */
    public Combustible getCombustible() {
        return tipoCombustible;
    }

    /**
     *
     * @param tipoCombustible
     */
    public void setCombustible(Combustible tipoCombustible) {
        this.tipoCombustible = tipoCombustible;
    }

    /**
     * Devuelve el tipo de vehículo, incluyendo el tipo de combustible.
     *
     * @return cadena representando el tipo del vehículo
     */
    @Override
    public String getTipo() {
        return "Combustible (" + tipoCombustible + ")";
    }

    /**
     * Devuelve la información detallada del vehículo.
     *
     * @return información completa del vehículo como cadena
     */
    @Override
    public String getFullInfo() {
        return "<html> Marca: " + getMarca() + "<br>" 
             + "Modelo: " + getModelo() + "<br>" 
             + "Placa: " + getPlaca() + "<br>" 
             + "Tipo de Combustible: " + tipoCombustible + "</hmtl>";
    }
}

