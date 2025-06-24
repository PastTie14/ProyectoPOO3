/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo.Vehiculos;

import java.io.Serializable;

/**
 *
 * @author jmari
 */
/**
 * Clase abstracta que representa un vehículo.
 * Define propiedades comunes como marca, modelo y placa.
 * Las subclases deben proporcionar información completa y su tipo específico.
 */
public abstract class Vehiculo implements Serializable {
    private String marca;
    private String modelo;
    private String placa;

    /**
     *
     */
    public Vehiculo() {}

    /**
     * Constructor que inicializa la marca y modelo del vehículo.
     *
     * @param marca Marca del vehículo
     * @param modelo Modelo del vehículo
     */
    public Vehiculo(String marca, String modelo) {
        this.marca = marca;
        this.modelo = modelo;
    }

    /**
     *
     * @return
     */
    public String getMarca() {
        return marca;
    }

    /**
     *
     * @param marca
     */
    public void setMarca(String marca) {
        this.marca = marca;
    }

    /**
     * Obtiene una descripción detallada del vehículo.
     *
     * @return información completa en formato String
     */
    public abstract String getFullInfo();

    /**
     *
     * @return
     */
    public String getModelo() {
        return modelo;
    }

    /**
     *
     * @param modelo
     */
    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    /**
     *
     * @return
     */
    public String getPlaca() {
        return placa;
    }

    /**
     *
     * @param placa
     */
    public void setPlaca(String placa) {
        this.placa = placa;
    }

    /**
     * Obtiene el tipo de vehículo (por ejemplo: eléctrico, combustible).
     *
     * @return tipo de vehículo en formato String
     */
    public abstract String getTipo();

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return marca + "\n" + modelo;
    }
}