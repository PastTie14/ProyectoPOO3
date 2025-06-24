/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelos.Ciudades.Estaciones;

/**
 *
 * @author Ian
 */
public class EstacionCargaFactory extends EstacionFactory {
    @Override
    public Estacion crearEstacion(String nombre) {
        return new EstacionCarga(nombre);
    }
}
