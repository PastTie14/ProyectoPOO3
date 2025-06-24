/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vista.Usuario;

import Controlador.Controlador;
import Modelo.Vehiculos.Vehiculo;
import Modelos.Ciudades.Ciudad;
import Modelos.Ciudades.Conexion;
import Modelos.Clientes.Usuario;
import Vista.Componentes.CreadorComponentesVista;
import Vista.Componentes.PanelConFondo;
import Vista.Componentes.PanelTarjetas;
import Vista.Componentes.PasswordFieldPersonalizado;
import Vista.Componentes.TextFieldPersonalizado;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.ArrayList;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import static javax.swing.SwingConstants.CENTER;
import javax.swing.SwingUtilities;

/**
 *
 * @author Ian
 */
public class ConsultarRutas extends JFrame{
    
    private final int screenWidth;
    private final int screenHeight;
    private JPanel panelPrincipal;
    private PanelConFondo panelFondo;
    private JButton abandonar;
    private PanelTarjetas panelCiudadSalida;
    private PanelTarjetas panelCiudadDestino;
    private JPanel panelOpciones;
    private JLabel titulo;
    private JLabel subTitulo;
    
    private JLabel info;
    
    private Ciudad salida;
    private Ciudad destino;
    
    
    
    private Controlador controlador;
    
    public ConsultarRutas() {
        
        controlador = Controlador.getInstance();
        
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
        generarPanelCiudadSalida();
        generarPanelCiudadDestino(); 
        generarPanelOpciones();
        this.repaint();
    }
    
    private void generarPanelPrincipal(){
        panelPrincipal = CreadorComponentesVista.generarPanelMenu(screenWidth, screenHeight);
        panelPrincipal.setOpaque(true);
        int x = screenWidth/6; 
        int y = 50; 
        panelPrincipal.setBounds(x, y, screenWidth/3*2, screenHeight-100);
        panelFondo.add(panelPrincipal);
        abandonar = CreadorComponentesVista.generarBotonImagen("cancelar.png",20,20);
        panelPrincipal.add(abandonar);
        abandonar.setBounds(panelPrincipal.getWidth()-25, 10, 20, 20);
        abandonar.addActionListener(e -> {
            MenuUsuario temp = new MenuUsuario();
            temp.setVisible(true);
            dispose();
        });
        
        this.repaint();
    }
    
    public void generarPanelCiudadSalida(){
        panelCiudadSalida = new PanelTarjetas();
        ImageIcon icono = Utilidades.UtilidadesVisuales.obtenerImagenDeRecursos("seleccionado.png");
        Image tempImage = icono.getImage().getScaledInstance(18, 18, Image.SCALE_SMOOTH);
        icono = new ImageIcon(tempImage);
        panelCiudadSalida.setIconoSelected(icono);
        panelCiudadSalida.setOnSeleccionarTarjeta(obj -> {
            salida = (Ciudad) obj;
            setInfo();

        });
        panelCiudadSalida.setBounds(20, 20, 
                panelPrincipal.getWidth()/2-40, panelPrincipal.getHeight()/4*3-40);
        panelCiudadSalida.agregarTarjetas(controlador.getCiudades().toArray());
        panelPrincipal.add(panelCiudadSalida);
    }
    
    public void generarPanelCiudadDestino(){
        panelCiudadDestino = new PanelTarjetas();
        ImageIcon icono = Utilidades.UtilidadesVisuales.obtenerImagenDeRecursos("seleccionado.png");
        Image tempImage = icono.getImage().getScaledInstance(18, 18, Image.SCALE_SMOOTH);
        icono = new ImageIcon(tempImage);
        panelCiudadDestino.setIconoSelected(icono);
        panelCiudadDestino.setOnSeleccionarTarjeta(obj -> {
            destino = (Ciudad) obj;
            setInfo();
        });
        panelCiudadDestino.setBounds(panelCiudadSalida.getX() + panelCiudadSalida.getWidth() + 40, 20, 
                panelPrincipal.getWidth()/2-40, panelPrincipal.getHeight()/4*3-40);
        panelCiudadDestino.agregarTarjetas(controlador.getCiudades().toArray());
        panelPrincipal.add(panelCiudadDestino);
    }
    
    public void setInfo(){
        if (salida!=null){
            info.setText("<html>" +"Saliendo de: " + salida.getNombre() + "<br>" + "Destino: " +  ""  +  "</html>");
        }
        if (destino!=null){
            info.setText("<html>" +"Saliendo de: " + "" + "<br>" + "Destino: " + destino.getNombre() +  "</html>");
        }
        if (destino!=null && salida!=null){
            info.setText("<html>" +"Saliendo de: " + salida.getNombre() + "<br>" + "Destino: " + destino.getNombre() +  "</html>");
        }
    }
    
    public boolean validarSelecciones(){
        if (salida!=null && destino !=null){
            if(salida!=destino)
                return true;
        }
        return false;
    }
    
    
    public void generarPanelOpciones(){
        
        
        
        panelOpciones = CreadorComponentesVista.generarPanelBlanco();
        int anchoPanelOpciones = panelPrincipal.getWidth()-40;
        int altoPanelOpciones = panelPrincipal.getHeight() / 4 - 20;
        int x = 20;
        int y = panelCiudadDestino.getHeight() + panelCiudadDestino.getY() + 20;
        panelOpciones.setBounds(x, y, anchoPanelOpciones, altoPanelOpciones);
        
        JButton botonRutas = CreadorComponentesVista.generarBotonConIcono("mapa.png", "Rutas");
        int anchoBotones = panelOpciones.getWidth()/5;
        int altoBotones = panelOpciones.getHeight()/2;
        botonRutas.setBounds(panelOpciones.getWidth()/5*3, panelOpciones.getHeight()/4, anchoBotones, altoBotones);
        
        
        
        botonRutas.addActionListener(e -> {
            if (validarSelecciones()){
                if(controlador.getVehiculoSeleccionado()!=null){
                    VerRutas temp = new VerRutas();
                    for (ArrayList<Conexion> ruta : salida.obtenerTodasLasRutas(destino)) {
                        System.out.println(salida);
                        temp.agregarTarjetaRuta(ruta,salida,destino);
                    }
                    temp.setVisible(true);
                    dispose();
                }else
                    JOptionPane.showMessageDialog(this, "No se selecciono ningun vehiculo, regrese al menu principal e intentelo denuevo");
            }else
                JOptionPane.showMessageDialog(this, "Error Revise que las ciudad de Salida y la Ciudad destino sean distintas, y que esten seleccionadas.");
        });
        
        info = new JLabel("", SwingConstants.CENTER);
        info.setFont(new Font("Arial", Font.PLAIN, 24));
        info.setBounds(10, 20 , panelOpciones.getWidth()/2, panelOpciones.getHeight());
        info.setText("<html>" +"Saliendo de: " + "<br>" + "Destino: " +  "</html>");
        panelOpciones.add(botonRutas);
        panelOpciones.add(info);
        
        
        panelPrincipal.add(panelOpciones);
    }

    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(ConsultarRutas::new);
    }
}

