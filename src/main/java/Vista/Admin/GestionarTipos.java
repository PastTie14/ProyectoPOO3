/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vista.Admin;

import Controlador.Controlador;
import Modelo.Vehiculos.Vehiculo;
import Modelos.Ciudades.Ciudad;
import Modelos.Clientes.Usuario;
import Modelos.CombustiblesCargadores.Tipos;
import Vista.Componentes.CreadorComponentesVista;
import Vista.Componentes.InterruptorPersonalizado;
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
public class GestionarTipos extends JFrame{
    
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
    private TextFieldPersonalizado nombre;
    private InterruptorPersonalizado tipos;
    private Tipos selectectedTipo;

    
    
    
    
    private Controlador controlador;
    
    /**
     *
     */
    public GestionarTipos() {
        
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
            Tipos temp = (Tipos) obj;
            selectectedTipo = temp;
            nombre.setText(temp.toString());
            nombre.setTitle("Nombre del " + temp.getTipo());
        });
        
        panelLista.setBounds(30, 20, 
                panelPrincipal.getWidth()/3-20, panelPrincipal.getHeight()-40);
        panelLista.agregarTarjetas(controlador.getCargadores().toArray());
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
        
        nombre = new TextFieldPersonalizado("Nombre", "Nombre de la ciudad");
        tipos = new InterruptorPersonalizado("Cargadores", "Combustibles");
        
        tipos.addCambioListener(e -> {
            changeSwitch();
        });
        
        int anchoComponentes = panelInfo.getWidth()/2;
        int altoComponentes = 50;
        int xComponentes = panelInfo.getWidth()/4;
        
        tipos.setBounds(xComponentes, subTitulo.getY() + subTitulo.getHeight() + 25, anchoComponentes, altoComponentes);
        nombre.setBounds(xComponentes, tipos.getY() + tipos.getHeight() + 25, anchoComponentes, altoComponentes);
        
        
        
        ImageIcon temp = Utilidades.UtilidadesVisuales.obtenerImagenDeRecursos("seleccionado.png");
        Image tempImage = temp.getImage().getScaledInstance(15, 15,  Image.SCALE_SMOOTH);
        temp = new ImageIcon(tempImage);
        
        panelInfo.add(nombre);
        panelInfo.add(tipos);
        panelInfo.add(titulo);
        panelInfo.add(subTitulo);
        panelPrincipal.add(panelInfo);
    }
    
    /**
     *
     * @return
     */
    public boolean validarEspacios(){
        boolean vacios = !nombre.getText().isBlank();
        return vacios;
    }
    
    /**
     *
     */
    public void changeSwitch(){
        if (tipos.isEncendido()) {
            updatePanelLista(controlador.getCargadores().toArray());
        } else {
            updatePanelLista(controlador.getCombustibles().toArray());
        }
    }

    /**
     *
     * @param array
     */
    public void updatePanelLista(Object[] array){
        panelLista.eliminarTodasLasTarjetas();
        panelLista.agregarTarjetas(array);
        
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
                boolean res = controlador.agregarTipo(nombre.getText(), tipos.isEncendido() ? "Cargador" : "Combustible");
                if (!res){
                    JOptionPane.showMessageDialog(this, "Ya existe un tipo con los datos dados");
                }else
                    changeSwitch();
            }else
                JOptionPane.showMessageDialog(this, "Error Datos incompletos");
            //dispose();
        });
        
        botonModificar.addActionListener(e -> {
            if (validarEspacios()){
               if (controlador.modificarTipo(selectectedTipo, nombre.getText()))
                   changeSwitch();
               else
                   JOptionPane.showMessageDialog(this, "Error Ya existe un (Cargador/Combustible) con el nombre dado.");        
            }else
                JOptionPane.showMessageDialog(this, "Error Datos incompletos");
            //dispose();
        });
        
        botonEliminar.addActionListener(e -> {
            if (selectectedTipo!=null){
                if(JOptionPane.showConfirmDialog(this, "Seguro de que desea eliminar el  " + selectectedTipo.getTipo() + ": " + selectectedTipo
                        ,"Confirmación", JOptionPane.YES_NO_OPTION)==0){
                    controlador.eliminarTipos(selectectedTipo);
                    changeSwitch();
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
        SwingUtilities.invokeLater(GestionarTipos::new);
    }
}

