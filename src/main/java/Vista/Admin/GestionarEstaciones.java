/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vista.Admin;

import Controlador.Controlador;
import Modelo.Vehiculos.Vehiculo;
import Modelos.Ciudades.Ciudad;
import Modelos.Ciudades.Estaciones.Estacion;
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
public class GestionarEstaciones extends JFrame{
    
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
    ComboBoxPersonalizadoObj estaciones;
    TextFieldPersonalizado nombre;
    ComboBoxPersonalizadoStr tiposEstaciones;
    ListaCheckBoxPersonalizada tiposCombustibles;
    ListaCheckBoxPersonalizada tiposCargadores;
    
    private Controlador controlador;
    
    /**
     *
     */
    public GestionarEstaciones() {
        
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
            controlador.setCiudadSeleccionada((Ciudad) obj);
            estaciones.eliminarTodosLosItems();
            for (Estacion estacion : controlador.getCiudadSeleccionada().getEstaciones()) {
                estaciones.addItem(estacion);
            }
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
        
        estaciones = new ComboBoxPersonalizadoObj("Estaciones de la ciudad", "");
        nombre = new TextFieldPersonalizado("Nombre", "Nombre de la Estacion");
        tiposEstaciones = new ComboBoxPersonalizadoStr("Tipos de Estaciones", "");
        tiposCombustibles = new ListaCheckBoxPersonalizada(controlador.getCombustibles().toArray());
        tiposCargadores = new ListaCheckBoxPersonalizada(controlador.getCargadores().toArray());
        
        
        int anchoComponentes = panelInfo.getWidth()/2;
        int altoComponentes = 50;
        int xComponentes = panelInfo.getWidth()/4;
        
        estaciones.setBounds(xComponentes, subTitulo.getY() + subTitulo.getHeight() + 25, anchoComponentes, altoComponentes);
        nombre.setBounds(xComponentes, estaciones.getY() + estaciones.getHeight() + 25, anchoComponentes, altoComponentes);
        tiposEstaciones.setBounds(xComponentes, nombre.getY() + nombre.getHeight() + 25, anchoComponentes, altoComponentes);
        tiposCombustibles.setBounds(xComponentes, tiposEstaciones.getY() + tiposEstaciones.getHeight() + 25, anchoComponentes, panelInfo.getHeight());
        tiposCargadores.setBounds(xComponentes, tiposEstaciones.getY() + tiposEstaciones.getHeight() + 25, anchoComponentes, panelInfo.getHeight());
        
        ImageIcon temp = Utilidades.UtilidadesVisuales.obtenerImagenDeRecursos("seleccionado.png");
        Image tempImage = temp.getImage().getScaledInstance(15, 15,  Image.SCALE_SMOOTH);
        temp = new ImageIcon(tempImage);
        
        tiposCargadores.setVisible(false);
        tiposCombustibles.setVisible(false);
        
        tiposEstaciones.addItem("Combustibles");
        tiposEstaciones.addItem("Cargadores");
        
        tiposEstaciones.getComboBox().addActionListener(e -> {
            String seleccionado = (String) tiposEstaciones.getComboBox().getSelectedItem();
            if ("Combustibles".equalsIgnoreCase(seleccionado)) {
                tiposCombustibles.setVisible(true);
                tiposCargadores.setVisible(false);
            } else if ("Cargadores".equalsIgnoreCase(seleccionado)) {
                tiposCombustibles.setVisible(false);
                tiposCargadores.setVisible(true);
            } else {
                tiposCombustibles.setVisible(false);
                tiposCargadores.setVisible(false);
            }
        });
        
        estaciones.getComboBox().addActionListener(e -> {
            if (estaciones.getComboBox().getSelectedItem() instanceof Estacion){
                Estacion seleccionado = (Estacion) estaciones.getComboBox().getSelectedItem();
                nombre.setText(seleccionado.getNombre());
                tiposEstaciones.setSelectedItem(seleccionado.getTipoEstacion().equals("Combustibles") ? 0 : 1);
                
            }
        });
        
        panelInfo.add(estaciones);
        panelInfo.add(nombre);
        panelInfo.add(tiposCombustibles);
        panelInfo.add(tiposCargadores);
        panelInfo.add(tiposEstaciones);
        panelInfo.add(titulo);
        panelInfo.add(subTitulo);
        panelPrincipal.add(panelInfo);
    }
    
    /**
     *
     * @return
     */
    public boolean validarEspacios(){
        boolean txtFieldsVacios = !nombre.getText().isBlank() ;
        String estacion = tiposEstaciones.getSelectedItemText();
        boolean checkBoxListVacios;
        if (tiposCargadores.isVisible()){
            checkBoxListVacios = tiposCargadores.getSeleccionados().size()<1;
            return txtFieldsVacios && !checkBoxListVacios;
        }
        checkBoxListVacios = tiposCombustibles.getSeleccionados().size()<1;
        return txtFieldsVacios &&  !checkBoxListVacios;
        
       
        
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
            if(controlador.getCiudadSeleccionada()!=null) {
            if (validarEspacios()){
                if (controlador.getCiudadSeleccionada()!=null){
                    boolean res = controlador.guardarEstacion(nombre.getText(), tiposEstaciones.getSelectedItemText(),
                             tiposEstaciones.getSelectedItemText().equals("Combustibles") ? tiposCombustibles.getSeleccionados().toArray() : tiposCargadores.getSeleccionados().toArray()
                            );
                    if (res)
                        updatePanelLista();
                    else
                        JOptionPane.showMessageDialog(this, "Ya existe una ciudad con los datos dados");
                }
            }else
                JOptionPane.showMessageDialog(this, "Error Datos incompletos");
            //dispose();
            }else
                JOptionPane.showMessageDialog(this, "Error Debe seleccionar una ciudad");
        }   );
        
        botonModificar.addActionListener(e -> {
            if (validarEspacios()){
                controlador.modificarEstacion((Estacion) estaciones.getSelectedItem(), nombre.getText(),
                        tiposEstaciones.getSelectedItemText().equals("Combustibles") ? tiposCombustibles.getSeleccionados().toArray() : tiposCargadores.getSeleccionados().toArray() );
               updatePanelLista();
            }else
                JOptionPane.showMessageDialog(this, "Error Datos incompletos");
            //dispose();
        });
        
        botonEliminar.addActionListener(e -> {
            if (estaciones.getSelectedItem()!=null){
                if(JOptionPane.showConfirmDialog(this, "Seguro de que desea eliminar la Estacion" 
                        + estaciones.getSelectedItemText().toString(),"Confirmación", JOptionPane.YES_NO_OPTION)==0){
                    controlador.eliminarEstacion((Estacion) estaciones.getSelectedItem());
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
        SwingUtilities.invokeLater(GestionarEstaciones::new);
    }
}

