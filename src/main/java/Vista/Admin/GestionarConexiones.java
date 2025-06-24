/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vista.Admin;

import Controlador.Controlador;
import Modelo.Vehiculos.Vehiculo;
import Modelos.Ciudades.Ciudad;
import Modelos.Ciudades.Conexion;
import Modelos.Ciudades.Estacion;
import Modelos.Clientes.Usuario;
import Modelos.CombustiblesCargadores.Combustible;
import Modelos.CombustiblesCargadores.Tipos;
import Vista.Componentes.ComboBoxPersonalizadoObj;
import Vista.Componentes.ComboBoxPersonalizadoStr;
import Vista.Componentes.CreadorComponentesVista;
import Vista.Componentes.ListaCheckBoxPersonalizada;
import Vista.Componentes.PanelConFondo;
import Vista.Componentes.PanelTarjetas;
import Vista.Componentes.TextFieldPersonalizado;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

/**
 *
 * @author Ian
 */
public class GestionarConexiones extends JFrame{
    
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
    private ComboBoxPersonalizadoObj conexion;
    private ComboBoxPersonalizadoObj ciudades;
    private TextFieldPersonalizado distancia;
    private TextFieldPersonalizado minutos;
    private TextFieldPersonalizado costo;
    private Conexion conexionSeleccionada;
    private Ciudad ciudadConectada;

    
    private Controlador controlador;
    
    /**
     *
     */
    public GestionarConexiones() {
        
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
            MenuAdmin temp = new MenuAdmin();
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
            controlador.setCiudadSeleccionada((Ciudad) obj);
            updateConexiones();

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

        int labelWidth = 200;
        int tituloHeight = 30;
        int subTituloHeight = 20;
        int xCentrado = (anchoPanelInfo - labelWidth) / 2;

        titulo = new JLabel("Menu Usuario", SwingConstants.CENTER); // Centro el texto directamente aquí
        titulo.setFont(new Font("Arial", Font.BOLD, 25));
        titulo.setBounds(xCentrado, 10, labelWidth, tituloHeight);

        subTitulo = new JLabel(controlador.getClienteSeleccionado().getNombre(), SwingConstants.CENTER);
        subTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        subTitulo.setBounds(xCentrado, 50, labelWidth, subTituloHeight);
        
        conexion = new ComboBoxPersonalizadoObj("Conexion", "");
        ciudades = new ComboBoxPersonalizadoObj("Conectada con", "");
        distancia = new TextFieldPersonalizado("Distancia", "Distancia a recorrer entre las Ciudades en KM");
        minutos = new TextFieldPersonalizado("Minutos", "Tiempo gastado en recorrer la ruta en mins");
        costo = new TextFieldPersonalizado("Consumo", "Consumo de energia/gasolina entero entre 1 y 100");

        
        
        int anchoComponentes = panelInfo.getWidth()/2;
        int altoComponentes = 50;
        int xComponentes = panelInfo.getWidth()/4;
        
        conexion.setBounds(xComponentes, subTitulo.getY() + subTitulo.getHeight() + 25, anchoComponentes, altoComponentes);
        ciudades.setBounds(xComponentes, conexion.getY() + conexion.getHeight() + 25, anchoComponentes, altoComponentes);
        distancia.setBounds(xComponentes, ciudades.getY() + ciudades.getHeight() + 25, anchoComponentes, altoComponentes);
        minutos.setBounds(xComponentes, distancia.getY() + distancia.getHeight() + 25, anchoComponentes, altoComponentes);
        costo.setBounds(xComponentes, minutos.getY() + minutos.getHeight() + 25, anchoComponentes, altoComponentes);
        
        ImageIcon temp = Utilidades.UtilidadesVisuales.obtenerImagenDeRecursos("seleccionado.png");
        Image tempImage = temp.getImage().getScaledInstance(15, 15,  Image.SCALE_SMOOTH);
        temp = new ImageIcon(tempImage);
        
        
        conexion.getComboBox().addActionListener(e -> {
            conexionSeleccionada = (Conexion) conexion.getSelectedItem();
            settearComponentes();
            
        });
        
        ciudades.getComboBox().addActionListener(e -> {
            ciudadConectada = (Ciudad) ciudades.getSelectedItem();
            settearComponentes();
            
        });
        
        updateCiudades();
        
        panelInfo.add(conexion);
        panelInfo.add(ciudades);
        panelInfo.add(distancia);
        panelInfo.add(minutos);
        panelInfo.add(costo);
        panelInfo.add(titulo);
        panelInfo.add(subTitulo);
        panelPrincipal.add(panelInfo);
    }
    
    /**
     *
     * @return
     */
    public boolean validarEspacios(){
        boolean numeros = distancia.getText().matches("(\\-?\\d*\\.?\\d+)") && minutos.getText().matches("^\\d+$") && costo.getText().matches("^\\d+$");
        boolean comboBoxVacios = ciudades.getSelectedItem()!=null;
        return numeros && comboBoxVacios && controlador.getCiudadSeleccionada() != null;
    }
    
    /**
     *
     */
    public void settearComponentes(){
        if (conexionSeleccionada!=null){
            ciudades.seleccionarItem(conexionSeleccionada.getCiudad());
            distancia.setText(String.valueOf(conexionSeleccionada.getDistancia()));
            minutos.setText(String.valueOf(conexionSeleccionada.getMinutos()));
            costo.setText(String.valueOf(conexionSeleccionada.getConsumo()));
        }
    }
    
    /**
     *
     */
    public void updateConexiones(){
        conexion.eliminarTodosLosItems();
        for (Object conexion1 : controlador.getCiudadSeleccionada().getConexiones()) {
            conexion.addItem(conexion1);
        }
    }
    
    /**
     *
     */
    public void updateCiudades(){
        ciudades.eliminarTodosLosItems();
        for (Ciudad ciudad : controlador.getCiudades()) {
            ciudades.addItem(ciudad);
        }
    }
    
    /**
     *
     */
    public void updatePanelLista(){
        panelLista.eliminarTodasLasTarjetas();
        panelLista.agregarTarjetas(controlador.getCiudades().toArray());
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
        
        JButton botonGuardar = CreadorComponentesVista.generarBotonConIcono("guardar.png", "Guardar");
        JButton botonModificar = CreadorComponentesVista.generarBotonConIcono("editar.png", "Modificar");
        JButton botonEliminar = CreadorComponentesVista.generarBotonConIcono("eliminar.png", "Eliminar");
        
        int anchoBotones = panelOpciones.getWidth()/6;
        int altoBotones = panelOpciones.getHeight()/2;
        botonGuardar.setBounds(panelOpciones.getWidth()/7, panelOpciones.getHeight()/4, anchoBotones, altoBotones);
        botonModificar.setBounds(panelOpciones.getWidth()/7*3, panelOpciones.getHeight()/4, anchoBotones, altoBotones);
        botonEliminar.setBounds(panelOpciones.getWidth()/7*5, panelOpciones.getHeight()/4, anchoBotones, altoBotones);
        
        
        
        botonGuardar.addActionListener(e -> {
            if (validarEspacios()){
                if (controlador.getCiudadSeleccionada()!=null){
                    boolean res = controlador.guardarConexion(ciudadConectada, Double.parseDouble(distancia.getText()), 
                            Integer.parseInt(minutos.getText()), Integer.parseInt(costo.getText()));
                    if (res)
                        updatePanelLista();
                    else
                        JOptionPane.showMessageDialog(this, "Ya existe una conexion hacia la ciudad proporcionada");
                }
            }else
                JOptionPane.showMessageDialog(this, "Error Datos incompletos");
            //dispose();
        }   );
        
        botonModificar.addActionListener(e -> {
            if (validarEspacios()){
                controlador.modificarConexion(conexionSeleccionada, Double.parseDouble(distancia.getText()), 
                            Integer.parseInt(minutos.getText()), Integer.parseInt(costo.getText()));
               updatePanelLista();
            }else
                JOptionPane.showMessageDialog(this, "Error Datos incompletos");
            //dispose();
        });
        
        botonEliminar.addActionListener(e -> {
            if (conexionSeleccionada!=null){
                if(JOptionPane.showConfirmDialog(this, "Seguro de que desea eliminar la Conexion hacia" 
                        + conexionSeleccionada.getCiudad(),"Confirmación", JOptionPane.YES_NO_OPTION)==0){
                    controlador.eliminarConexion(conexionSeleccionada);
                    updatePanelLista();
                }
            }else
                   JOptionPane.showMessageDialog(this, "Error Datos incompletos");
            //dispose();
        });
        
        panelOpciones.add(botonGuardar);
        panelOpciones.add(botonModificar);
        panelOpciones.add(botonEliminar);
        
        
        panelPrincipal.add(panelOpciones);
    }

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(GestionarConexiones::new);
    }
}

