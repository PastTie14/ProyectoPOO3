/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelos.CombustiblesCargadores;

import java.io.Serializable;

/**
 *
 * @author Ian
 */
import java.io.Serializable;

/**
 * Clase base que representa un tipo genérico con una categoría definida.
 */
public class Tipos implements Serializable {
    String tipo;

    /**
     * Constructor que asigna el tipo del objeto.
     * 
     * @param tipo Categoría o tipo de este objeto
     */
    public Tipos(String tipo) {
        this.tipo = tipo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return "";
    }
}

