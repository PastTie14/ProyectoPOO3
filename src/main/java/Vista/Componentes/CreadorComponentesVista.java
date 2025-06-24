/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vista.Componentes;

import Utilidades.UtilidadesVisuales;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Image;
import java.awt.Insets;
import javax.swing.*;

/**
 *
 * @author Ian
 */
public class CreadorComponentesVista {
    
    
    
    public static JPanel generarPanelUnTercio(int fatherWidth, int fatherHeight){
        JPanel temp = new JPanel();
        Color fondoPanel = new Color(201, 201, 201, 178);
        temp.setOpaque(false);
        temp.setBackground(fondoPanel);
        temp.setSize(fatherWidth/3, fatherHeight-100);
        temp.setLayout(null);
        return temp;
    }
    
    public static JPanel generarPanelMenu(int fatherWidth, int fatherHeight){
        JPanel temp = new JPanel();
        Color fondoPanel = new Color(201, 201, 201, 178);
        temp.setOpaque(false);
        temp.setBackground(fondoPanel);
        temp.setSize(fatherWidth/10*9, fatherHeight-100);
        temp.setLayout(null);
        return temp;
    }
    
    public static JPanel generarPanelBlanco(){
        JPanel temp = new JPanel();
        temp.setOpaque(true);
        temp.setBackground(Color.WHITE);
        temp.setLayout(null);
        return temp;
    }
    
    public static JButton generarBotonImagen(String nombreImagen, int anchoImagen, int altoImagen){
        ImageIcon tempIcon= UtilidadesVisuales.obtenerImagenDeRecursos(nombreImagen);
        Image tempImage = tempIcon.getImage().getScaledInstance(anchoImagen, altoImagen,  Image.SCALE_SMOOTH);
        tempIcon = new ImageIcon(tempImage);
        JButton temp = new JButton(tempIcon);
        temp.setBorderPainted(false);
        temp.setContentAreaFilled(false);
        temp.setFocusPainted(false);
        temp.setRolloverEnabled(false);
        temp.setLayout(null);
        return temp;
    }
    
    public static JButton generarBotonConIcono(String nombreIcono, String texto) {
        ImageIcon tempIcon = UtilidadesVisuales.obtenerImagenDeRecursos(nombreIcono);
        Image tempImage = tempIcon.getImage().getScaledInstance(18, 18, Image.SCALE_SMOOTH);
        tempIcon = new ImageIcon(tempImage);
        return new BotonRedondeado(texto, tempIcon);
    }

    
    public static JTextField generarTextField(){
        return null;
    }
}
