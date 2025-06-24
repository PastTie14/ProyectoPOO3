/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Modelos.Clientes;

import javax.swing.JFrame;

/**
 *
 * @author Ian
 */
/**
 * Interfaz que define el método para ingresar al sistema.
 */
public interface LogIn {

    /**
     * Método que se debe implementar para manejar el ingreso de un usuario o cliente.
     *
     * @return Un objeto {@code JFrame} que representa la ventana de ingreso o interfaz principal,
     *         o {@code null} si no aplica.
     */
    public JFrame ingresar();
}

