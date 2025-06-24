/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.proyectopoo3;

import Controlador.Controlador;
import Modelos.Clientes.Admin;
import Vista.Ingresar;
import javax.swing.JOptionPane;

/**
 *
 * @author karol
 */
public class ProyectoPOO3 {

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        
        Controlador co = Controlador.getInstance();
        co.guardarAdmin("Ian", "14");
        
        
        
        Ingresar ing = new Ingresar();
        ing.setVisible(true);
    }
}
