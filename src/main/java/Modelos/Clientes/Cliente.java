/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelos.Clientes;

import Vista.Admin.MenuAdmin;
import Vista.Usuario.MenuUsuario;
import java.io.Serializable;
import javax.swing.JFrame;

/**
 *
 * @author Ian
 */
import java.io.Serializable;
import javax.swing.JFrame;

/**
 * Clase que representa un cliente genérico en el sistema.
 * Implementa la interfaz {@code LogIn} para manejo de ingreso.
 */
public class Cliente implements LogIn, Serializable {
    protected String nombre;
    protected String contrasennia;

    /**
     * Constructor para crear un cliente.
     *
     * @param nombre Nombre del cliente
     * @param contrasennia Contraseña del cliente
     */
    public Cliente(String nombre, String contrasennia) {
        this.nombre = nombre;
        this.contrasennia = contrasennia;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getContrasennia() {
        return contrasennia;
    }

    public void setContrasennia(String contrasennia) {
        this.contrasennia = contrasennia;
    }

    /**
     * Implementación por defecto del método ingresar de la interfaz {@code LogIn}.
     * Retorna null, debe ser sobreescrito por subclases.
     *
     * @return null
     */
    @Override
    public JFrame ingresar() {
        return null;
    }
}
