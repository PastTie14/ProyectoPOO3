/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


package Vista.Usuario;

import Controlador.Controlador;
import Modelo.Vehiculos.Vehiculo;
import Modelo.Vehiculos.VehiculoCombustible;
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
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import static javax.swing.SwingConstants.CENTER;
import javax.swing.SwingUtilities;

/**
 *
 * @author Ian
 */
public class VerRutas extends JFrame{
    
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
    private JScrollPane scrollPanelRutas;
    private JPanel panelContenedorRutas;
    
    private JLabel info;
    

    
    
    
    private Controlador controlador;
    
    public VerRutas() {
        
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
        generarPanelRutasScroll();
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
    private void generarPanelRutasScroll() {
        panelContenedorRutas = new JPanel();
        panelContenedorRutas.setLayout(new BoxLayout(panelContenedorRutas, BoxLayout.Y_AXIS));
        panelContenedorRutas.setBackground(Color.WHITE);

        scrollPanelRutas = new JScrollPane(panelContenedorRutas);
        scrollPanelRutas.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPanelRutas.setBounds(20, 80, panelPrincipal.getWidth() - 40, panelPrincipal.getHeight() - 120);
        scrollPanelRutas.getVerticalScrollBar().setUnitIncrement(16); // suavizar scroll

        panelPrincipal.add(scrollPanelRutas);
    }
    
    

    public void agregarTarjetaRuta(ArrayList<Conexion> ruta,Ciudad salida, Ciudad destino) {
        JPanel tarjeta = new JPanel();
        tarjeta.setLayout(null);
        tarjeta.setBackground(new Color(245, 245, 245));
        tarjeta.setPreferredSize(new Dimension(scrollPanelRutas.getWidth() - 40, 120));
        tarjeta.setMaximumSize(new Dimension(Integer.MAX_VALUE, 150));
        
        JLabel lblSalidaDestino  = new JLabel("Saliendo de: " + salida.getNombre() + " -----> Destino: " + destino.getNombre());
        lblSalidaDestino.setFont(new Font("Arial", Font.PLAIN, 14));
        lblSalidaDestino.setBounds(20, 10, 400, 20);
        tarjeta.add(lblSalidaDestino);

        JLabel lblTiempo = new JLabel("Tiempo esperado: " + controlador.getMinutosRuta(ruta) + " mins.");
        lblTiempo.setFont(new Font("Arial", Font.PLAIN, 14));
        lblTiempo.setBounds(20, lblSalidaDestino.getY() + lblSalidaDestino.getHeight() + 5 , 400, 20);
        tarjeta.add(lblTiempo);
        
        String tipoConsumo  = controlador.getVehiculoSeleccionado() instanceof VehiculoCombustible ? "litros" : "%";

        JLabel lblConsumo = new JLabel("Consumo esperado: " + controlador.getConsumoRuta(ruta) + " " + tipoConsumo);
        lblConsumo.setFont(new Font("Arial", Font.PLAIN, 14));
        lblConsumo.setBounds(20, lblTiempo.getY() + lblTiempo.getHeight() + 5, 500, 20);
        tarjeta.add(lblConsumo);

        JLabel lblDistancia = new JLabel("Distancia a recorrer: " + controlador.getDistanciaRuta(ruta) + " km");
        lblDistancia.setFont(new Font("Arial", Font.PLAIN, 14));
        lblDistancia.setBounds(20, lblConsumo.getY() + lblConsumo.getHeight() + 5, 300, 20);
        tarjeta.add(lblDistancia);

        JLabel lblCiudades = new JLabel("Ciudades a recorrer: " + controlador.getCiudadesVisitadas(ruta));
        lblCiudades.setFont(new Font("Arial", Font.PLAIN, 14));
        lblCiudades.setBounds(20, lblDistancia.getY() + lblDistancia.getHeight() + 5, 600, 20);
        tarjeta.add(lblCiudades);

        //JButton btnVerMapa = new JButton("🗺 Ver mapa");
        //btnVerMapa.setBounds(tarjeta.getPreferredSize().width - 130, 35, 100, 40);
        // btnVerMapa.addActionListener(e -> {
        //     // Acción personalizada
        // });

        //tarjeta.add(btnVerMapa);

        panelContenedorRutas.add(Box.createVerticalStrut(10));
        panelContenedorRutas.add(tarjeta);
        panelContenedorRutas.revalidate();
        panelContenedorRutas.repaint();
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(VerRutas::new);
    }
}