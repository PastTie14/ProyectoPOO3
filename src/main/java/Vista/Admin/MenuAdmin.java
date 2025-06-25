/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vista.Admin;

import Controlador.Controlador;
import Modelos.Ciudades.Ciudad;
import Vista.Componentes.CreadorComponentesVista;
import Vista.Componentes.PanelConFondo;
import Vista.Componentes.PanelTarjetas;
import Vista.Ingresar;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

/**
 *
 * @author Ian
 */

public class MenuAdmin extends JFrame{
    
    private final int screenWidth;
    private final int screenHeight;
    private JPanel panelPrincipal;
    private PanelConFondo panelFondo;
    private JButton abandonar;
    private PanelTarjetas panelLista;
    private JPanel panelInfo;
    private JPanel panelOpciones;
    private JLabel titulo;
    private JLabel subTitulo;
    private JLabel info;
    
    
    
    private Controlador controlador;
    
    /**
     *
     */
    public MenuAdmin() {
        
        controlador = Controlador.getInstance();
        controlador.setCiudadSeleccionada(null);
        
        // Obtener resolución de pantalla
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        screenWidth = screenSize.width;
        screenHeight = screenSize.height;
        setUndecorated(true);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Crear el panel de fondo como atributo
        String rutaFondo = "Fondo.png";
        panelFondo = new PanelConFondo(rutaFondo);
        
        // Establecer el panel de fondo como contentPane
        setContentPane(panelFondo);
        
        setVisible(true);
        generarPanelPrincipal();
        generarPanelLista();
        generarPanelInfo();
        generarPanelOpciones();
        this.repaint();
    }
    
    private void generarPanelPrincipal(){
        panelPrincipal = CreadorComponentesVista.generarPanelMenu(screenWidth, screenHeight);
        panelPrincipal.setOpaque(true);
        int x = screenWidth/20; 
        int y = 50; 
        panelPrincipal.setBounds(x, y, screenWidth/10*9, screenHeight-100);
        panelFondo.add(panelPrincipal);
        abandonar = CreadorComponentesVista.generarBotonImagen("cancelar.png",20,20);
        panelPrincipal.add(abandonar);
        abandonar.setBounds(panelPrincipal.getWidth()-25, 10, 20, 20);
        abandonar.addActionListener(e -> {
            Ingresar temp = new Ingresar();
            temp.setVisible(true);
            dispose();
        });
        
        this.repaint();
    }
    
    /**
     *
     */
    public void generarPanelLista(){
        panelLista = new PanelTarjetas();
        ImageIcon icono = Utilidades.UtilidadesVisuales.obtenerImagenDeRecursos("seleccionado.png");
        Image tempImage = icono.getImage().getScaledInstance(18, 18, Image.SCALE_SMOOTH);
        icono = new ImageIcon(tempImage);
        panelLista.setIconoSelected(icono);
        panelLista.setOnSeleccionarTarjeta(obj -> {
            info.setText(((Ciudad)obj).getFullInfoHTML());
        });
        panelLista.setBounds(30, 20, 
                panelPrincipal.getWidth()/3-20, panelPrincipal.getHeight()-40);
        panelLista.agregarTarjetas(controlador.getCiudades().toArray());
        panelPrincipal.add(panelLista);
        
    }
    
    /**
     *
     */
    public void generarPanelInfo() {
        panelInfo = CreadorComponentesVista.generarPanelBlanco();

        int anchoPanelInfo = (panelPrincipal.getWidth() / 3 - 40) * 2;
        int altoPanelInfo = panelPrincipal.getHeight() / 4 * 3 - 40;
        int x = panelPrincipal.getWidth() - anchoPanelInfo - 30;
        int y = 20;

        panelInfo.setBounds(x, y, anchoPanelInfo, altoPanelInfo);

        int labelWidth = panelInfo.getWidth()/3;
        int tituloHeight = 30;
        int subTituloHeight = 20;
        int xCentrado = (anchoPanelInfo - labelWidth) / 2;

        titulo = new JLabel("Menu Administrador", SwingConstants.CENTER); // Centro el texto directamente aquí
        titulo.setFont(new Font("Arial", Font.BOLD, 25));
        titulo.setBounds(xCentrado, 10, labelWidth, tituloHeight);

        subTitulo = new JLabel(controlador.getClienteSeleccionado().getNombre(), SwingConstants.CENTER);
        subTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        subTitulo.setBounds(xCentrado, 50, labelWidth, subTituloHeight);
        
        info = new JLabel("", SwingConstants.CENTER);
        info.setFont(new Font("Arial", Font.PLAIN, 20));
        info.setBounds(xCentrado, 80, labelWidth, panelInfo.getHeight()/3);
        
        panelInfo.add(titulo);
        panelInfo.add(subTitulo);
        panelInfo.add(info);
        panelPrincipal.add(panelInfo);
    }
    
    /**
     *
     */
    public void generarPanelOpciones(){
        panelOpciones = CreadorComponentesVista.generarPanelBlanco();
        int anchoPanelOpciones = (panelPrincipal.getWidth() / 3 - 40) * 2;
        int altoPanelOpciones = panelPrincipal.getHeight() / 4 - 20;
        int x = panelPrincipal.getWidth() - anchoPanelOpciones - 30;
        int y = panelInfo.getHeight() + panelInfo.getY() + 20;
        panelOpciones.setBounds(x, y, anchoPanelOpciones, altoPanelOpciones);
        
        JButton botonCiudades = CreadorComponentesVista.generarBotonConIcono("ciudad.png", "Ciudades");
        JButton botonConexiones = CreadorComponentesVista.generarBotonConIcono("conexion.png", "Conexiones");
        JButton botonEstaciones = CreadorComponentesVista.generarBotonConIcono("estacion.png", "Estaciones");
        JButton botonTipos = CreadorComponentesVista.generarBotonConIcono("tipos.png", "Tipos");
        
        int anchoBotones = panelOpciones.getWidth()/8;
        int altoBotones = panelOpciones.getHeight()/2;
        botonCiudades.setBounds(panelOpciones.getWidth()/9, panelOpciones.getHeight()/4, anchoBotones, altoBotones);
        botonConexiones.setBounds(panelOpciones.getWidth()/9*3, panelOpciones.getHeight()/4, anchoBotones, altoBotones);
        botonEstaciones.setBounds(panelOpciones.getWidth()/9*5, panelOpciones.getHeight()/4, anchoBotones, altoBotones);
        botonTipos.setBounds(panelOpciones.getWidth()/9*7, panelOpciones.getHeight()/4, anchoBotones, altoBotones);
        
        botonCiudades.addActionListener(e -> {
            GestionarCiudades temp = new GestionarCiudades();
            temp.setVisible(true);
            dispose();
        });
        
        botonConexiones.addActionListener(e -> {
            GestionarConexiones temp = new GestionarConexiones();
            temp.setVisible(true);
            dispose();
        });
        
        botonEstaciones.addActionListener(e -> {
            GestionarEstaciones temp = new GestionarEstaciones();
            temp.setVisible(true);
            dispose();
        });
        
        botonTipos.addActionListener(e -> {
            GestionarTipos temp = new GestionarTipos();
            temp.setVisible(true);
            dispose();
        });
        
        panelOpciones.add(botonCiudades);
        panelOpciones.add(botonConexiones);
        panelOpciones.add(botonEstaciones);
        panelOpciones.add(botonTipos);
        
        
        panelPrincipal.add(panelOpciones);
    }

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(MenuAdmin::new);
    }
}
