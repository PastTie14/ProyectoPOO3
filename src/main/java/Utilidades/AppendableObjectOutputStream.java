/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utilidades;

/**
 *
 * @author Ian
 */
import java.io.*;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

/**
 * Extensión de ObjectOutputStream que permite anexar objetos a un archivo
 * sin escribir un nuevo encabezado de flujo, evitando así corrupción de datos.
 */
public class AppendableObjectOutputStream extends ObjectOutputStream {

    /**
     * Constructor que inicializa el ObjectOutputStream con el OutputStream dado.
     * 
     * @param out el flujo de salida al que se escribirán los objetos
     * @throws IOException si ocurre un error de E/S al crear el flujo
     */
    public AppendableObjectOutputStream(OutputStream out) throws IOException {
        super(out);
    }

    /**
     * Sobrescribe el método para evitar escribir un nuevo encabezado
     * en el flujo, solo resetea el flujo para poder anexar objetos.
     * 
     * @throws IOException si ocurre un error de E/S al resetear el flujo
     */
    @Override
    protected void writeStreamHeader() throws IOException {
        reset();
    }
}



