/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vista.Componentes;

import Utilidades.UtilidadesVisuales;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.JPanel;

/**
 *
 * @author Ian
 */
public class PanelConFondo extends JPanel {
        private final Image fondo;
        private final int screenWidth;
        private final int screenHeight;
        
    /**
     *
     * @param pathImagen
     */
    public PanelConFondo(String pathImagen) {
            fondo = UtilidadesVisuales.obtenerImagenDeRecursos(pathImagen).getImage();
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            screenWidth = screenSize.width;
            screenHeight = screenSize.height;
            setLayout(null);
        }
        
    /**
     *
     * @param g
     */
    @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(fondo, 0, 0, screenWidth, screenHeight, this);
        }
    }
