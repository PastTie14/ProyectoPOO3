/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelos.Ciudades;

import java.io.Serializable;

/**
 *
 * @author Ian
 */
import java.io.Serializable;

/**
 * Representa una conexión entre una ciudad y otra ciudad destino.
 * Contiene información como distancia, tiempo estimado y consumo asociado.
 */
public class Conexion implements Serializable {
    Ciudad ciudad;
    double distancia;
    int minutos;
    int consumo;

    /**
     * Constructor principal para crear una conexión.
     *
     * @param ciudad Ciudad destino de la conexión
     * @param distancia Distancia entre las ciudades
     * @param minutos Tiempo estimado de viaje en minutos
     * @param consumo Consumo estimado de recursos en el trayecto
     */
    public Conexion(Ciudad ciudad, double distancia, int minutos, int consumo) {
        this.ciudad = ciudad;
        this.distancia = distancia;
        this.minutos = minutos;
        this.consumo = consumo;
    }

    /**
     *
     * @return
     */
    public Ciudad getCiudad() {
        return ciudad;
    }

    /**
     *
     * @return
     */
    public double getDistancia() {
        return distancia;
    }

    /**
     *
     * @return
     */
    public int getMinutos() {
        return minutos;
    }

    /**
     *
     * @return
     */
    public int getConsumo() {
        return consumo;
    }

    /**
     *
     * @param ciudad
     */
    public void setCiudad(Ciudad ciudad) {
        this.ciudad = ciudad;
    }

    /**
     *
     * @param distancia
     */
    public void setDistancia(double distancia) {
        this.distancia = distancia;
    }

    /**
     *
     * @param minutos
     */
    public void setMinutos(int minutos) {
        this.minutos = minutos;
    }

    /**
     *
     * @param consumo
     */
    public void setConsumo(int consumo) {
        this.consumo = consumo;
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return "Conexion{" + "ciudad: " + ciudad + ", distancia: " + distancia + '}';
    }
}

