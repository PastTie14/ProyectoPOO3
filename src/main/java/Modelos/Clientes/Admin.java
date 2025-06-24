/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelos.Clientes;

import Vista.Admin.MenuAdmin;
import javax.swing.JFrame;

/**
 *
 * @author Ian
 */
import javax.swing.JFrame;

/**
 * Clase que representa un administrador del sistema.
 * Extiende de {@code Cliente} e implementa la interfaz {@code LogIn}.
 */
public class Admin extends Cliente implements LogIn {

    /**
     * Constructor para crear un administrador con nombre y contraseña.
     *
     * @param nombre Nombre del administrador
     * @param contrasennia Contraseña del administrador
     */
    public Admin(String nombre, String contrasennia) {
        super(nombre, contrasennia);
    }

    /**
     * Implementación del método {@code ingresar} de la interfaz {@code LogIn}.
     * Retorna la ventana principal para un administrador.
     *
     * @return Una instancia de {@code MenuAdmin}
     */
    @Override
    public JFrame ingresar() {
        return new MenuAdmin();
    }
}

