/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vista.Componentes;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

/**
 *
 * @author Ian
 */

public class PanelTarjetas extends JScrollPane {
    private JPanel contenedor;
    private List<Object> objetos;
    private Consumer<Object> listener;
    private Icon iconoSelected;
    private JButton tarjetaSeleccionada = null;
    private Object objetoSeleccionado = null;
    

    public PanelTarjetas() {
        objetos = new ArrayList<>();
        contenedor = new JPanel();
        contenedor.setLayout(new BoxLayout(contenedor, BoxLayout.Y_AXIS));
        contenedor.setBackground(Color.WHITE);
        contenedor.setBorder(BorderFactory.createEmptyBorder());
        setBorder(BorderFactory.createEmptyBorder());
        setViewportView(contenedor);
        getVerticalScrollBar().setUnitIncrement(16); // para hacer scroll más fluido
    }

    public void setOnSeleccionarTarjeta(Consumer<Object> listener) {
        this.listener = listener;
    }
    
    public void agregarTarjetas(Object[] objetos){
        for (Object objeto : objetos) {
            agregarTarjeta(objeto);
        }
    }
    public void setIconoSelected(Icon icono) {
        this.iconoSelected = icono;
    }
    
    public void eliminarTodasLasTarjetas() {
        objetos.clear();
        contenedor.removeAll();              
        tarjetaSeleccionada = null;           
        objetoSeleccionado = null;            
        contenedor.revalidate();              
        contenedor.repaint();                 
    }
    
    
    

    public void agregarTarjeta(Object objeto) {
        objetos.add(objeto);

        JButton tarjeta = new JButton(objeto.toString());
        tarjeta.setMaximumSize(new Dimension(Integer.MAX_VALUE, 70));
        tarjeta.setAlignmentX(Component.CENTER_ALIGNMENT);
        tarjeta.setFocusPainted(false);
        tarjeta.setFont(new Font("Arial", Font.PLAIN, 16));
        tarjeta.setBackground(Color.WHITE);
        tarjeta.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.BLACK));
        tarjeta.setHorizontalAlignment(SwingConstants.LEFT);

        tarjeta.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (tarjeta != tarjetaSeleccionada) {
                    tarjeta.setBackground(tarjeta.getBackground().darker());
                }
                tarjeta.setFont(new Font("Arial", Font.BOLD, 16));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (tarjeta != tarjetaSeleccionada) {
                    tarjeta.setBackground(Color.WHITE);
                }
                tarjeta.setFont(new Font("Arial", Font.PLAIN, 16));
            }
        });

        tarjeta.addActionListener(e -> {
            // Desmarcar la anterior tarjeta seleccionada
            if (tarjetaSeleccionada != null && tarjetaSeleccionada != tarjeta) {
                tarjetaSeleccionada.setIcon(null);
                tarjetaSeleccionada.setBackground(Color.WHITE);
            }

            // Marcar esta como seleccionada
            tarjetaSeleccionada = tarjeta;
            objetoSeleccionado = objeto;
            tarjeta.setIcon(iconoSelected);
            tarjeta.setBackground(new Color(200, 200, 255)); // color de selección

            if (listener != null) {
                listener.accept(objeto);
            }
        });

        contenedor.add(tarjeta);
        contenedor.revalidate();
        contenedor.repaint();
    }
}