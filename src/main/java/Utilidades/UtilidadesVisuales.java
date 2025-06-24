/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utilidades;

import java.io.File;
import javax.swing.ImageIcon;

/**
 *
 * @author Ian
 */
public class UtilidadesVisuales {
    
    
    public static String obtenerRutaImagenDeRecursos(String nombreImagen){
        String carpetaBase = System.getProperty("user.dir");
        return carpetaBase + "\\Recursos\\" + nombreImagen;
    }
    
    
    public static ImageIcon obtenerImagenDeRecursos(String nombreImagen){
        ImageIcon imagen = new ImageIcon("Recursos\\" + nombreImagen);
        return imagen;
    }
}
