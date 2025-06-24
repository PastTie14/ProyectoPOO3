/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vista.Usuario;

import Controlador.Controlador;
import Modelo.Vehiculos.Vehiculo;
import Modelo.Vehiculos.VehiculoCombustible;
import Modelo.Vehiculos.VehiculoElectrico;
import Modelos.Clientes.Usuario;
import Modelos.CombustiblesCargadores.Cargador;
import Modelos.CombustiblesCargadores.Combustible;
import Vista.Componentes.ComboBoxPersonalizadoStr;
import Vista.Componentes.CreadorComponentesVista;
import Vista.Componentes.ListaCheckBoxPersonalizada;
import Vista.Componentes.PanelConFondo;
import Vista.Componentes.PanelTarjetas;
import Vista.Componentes.PasswordFieldPersonalizado;
import Vista.Componentes.TextFieldPersonalizado;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
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
public class GestionarVehiculos extends JFrame{
    
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
    TextFieldPersonalizado modelo;
    TextFieldPersonalizado marca;
    TextFieldPersonalizado placa;
    ComboBoxPersonalizadoStr tipos;
    ComboBoxPersonalizadoStr combustible;
    ListaCheckBoxPersonalizada cargadores;
    
    
    
    
    private Controlador controlador;
    
    public GestionarVehiculos() {
        
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
        configurarCombustibles();
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
            MenuUsuario temp = new MenuUsuario();
            temp.setVisible(true);
            dispose();
        });
        
        this.repaint();
    }
    
    public void generarPanelLista(){
        panelLista = new PanelTarjetas();
        ImageIcon icono = Utilidades.UtilidadesVisuales.obtenerImagenDeRecursos("seleccionado.png");
        Image tempImage = icono.getImage().getScaledInstance(18, 18, Image.SCALE_SMOOTH);
        icono = new ImageIcon(tempImage);
        panelLista.setIconoSelected(icono);
        panelLista.setOnSeleccionarTarjeta(obj -> {
            ((Usuario) controlador.getClienteSeleccionado()).setVehiculoSeleccionado((Vehiculo)obj);
        });
        panelLista.setBounds(30, 20, 
                panelPrincipal.getWidth()/3-20, panelPrincipal.getHeight()-40);
        panelLista.agregarTarjetas(((Usuario)controlador.getClienteSeleccionado()).getListaVehiculosFavoritos().toArray());
        panelPrincipal.add(panelLista);

    }
    
   
    
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
        
        marca = new TextFieldPersonalizado("Marca", "Marca del coche");
        modelo = new TextFieldPersonalizado("Modelo", "Modelo del coche");
        placa = new TextFieldPersonalizado("Placa", "Placa del coche");
        tipos = new ComboBoxPersonalizadoStr("Tipos", "");
        combustible = new ComboBoxPersonalizadoStr("Combustibles", "");
        String[] nombres = {"Opción A", "Opción B", "Opción C"};
        cargadores = new ListaCheckBoxPersonalizada(controlador.getCargadores().toArray());
        
        int anchoComponentes = panelInfo.getWidth()/2;
        int altoComponentes = 50;
        int xComponentes = panelInfo.getWidth()/4;
        
        marca.setBounds(xComponentes, subTitulo.getY() + subTitulo.getHeight() + 15, anchoComponentes, altoComponentes);
        modelo.setBounds(xComponentes, marca.getY() + marca.getHeight() + 15, anchoComponentes, altoComponentes);
        placa.setBounds(xComponentes, modelo.getY() + modelo.getHeight() + 15, anchoComponentes, altoComponentes);
        tipos.setBounds(xComponentes, placa.getY() + placa.getHeight() + 15, anchoComponentes, altoComponentes);
        combustible.setBounds(xComponentes, tipos.getY() + tipos.getHeight() + 15, anchoComponentes, altoComponentes);
        cargadores.setBounds(xComponentes, tipos.getY() + tipos.getHeight() + 15, anchoComponentes, altoComponentes*3);
        
        tipos.addItem("Combustion");
        tipos.addItem("Electrico");
        
        tipos.getComboBox().addActionListener(e -> {
            String seleccionado = (String) tipos.getComboBox().getSelectedItem();
            if ("Combustion".equalsIgnoreCase(seleccionado)) {
                combustible.setVisible(true);
                cargadores.setVisible(false);
            } else if ("Electrico".equalsIgnoreCase(seleccionado)) {
                combustible.setVisible(false);
                cargadores.setVisible(true);
            } else {
                combustible.setVisible(false);
                cargadores.setVisible(false);
            }
        });

        
        ImageIcon temp = Utilidades.UtilidadesVisuales.obtenerImagenDeRecursos("seleccionado.png");
        Image tempImage = temp.getImage().getScaledInstance(15, 15,  Image.SCALE_SMOOTH);
        temp = new ImageIcon(tempImage);
        cargadores.setCheckIcon(temp);

        
        combustible.setVisible(false);
        cargadores.setVisible(false);
        panelInfo.add(marca);
        panelInfo.add(modelo);
        panelInfo.add(placa);
        panelInfo.add(tipos);
        panelInfo.add(combustible);
        panelInfo.add(cargadores);
        panelInfo.add(titulo);
        panelInfo.add(subTitulo);
        panelPrincipal.add(panelInfo);
    }
    
    public void configurarCombustibles(){
        for (Combustible combustible1 : controlador.getCombustibles()) {
            combustible.addItem(combustible1.toString());
        }
    }
    
    public boolean validarEspacios(){
        return !marca.getText().isEmpty() || modelo.getText().isEmpty() || placa.getText().isEmpty() 
                || tipos.getSelectedItemText()!="Combustion" && combustible.getSelectedItemText().isBlank()
                || tipos.getSelectedItemText()!="Electrico" && cargadores.getSeleccionados().size()<1;
    }
    
    public Vehiculo validarVehiculo(){
        String seleccionado = (String) tipos.getComboBox().getSelectedItem();
        Vehiculo temp = null;
        if ("Combustion".equalsIgnoreCase(seleccionado)) {
                temp = validarCombustion();
            } else if ("Electrico".equalsIgnoreCase(seleccionado)) {
                temp = validarElectrico();
            }
        return temp;
    }
    
    public Vehiculo validarCombustion(){
        if (((Usuario)controlador.getClienteSeleccionado()).validarVehiculo(placa.getText())){
            VehiculoCombustible temp = new VehiculoCombustible(marca.getText(), modelo.getText(), controlador.getCombustibleDeString(combustible.getSelectedItemText()));
            return temp;
        }
        return null;
    }
    
    public Vehiculo validarElectrico(){
        if (((Usuario)controlador.getClienteSeleccionado()).validarVehiculo(placa.getText())){
            VehiculoElectrico temp = new VehiculoElectrico(marca.getText(), modelo.getText(), cargadores.getSeleccionados());
            return temp;
        }
        return null;
    }
    
    public void modificarVehiculo(){
        Vehiculo temp = controlador.getVehiculoSeleccionado();
        temp.setMarca(marca.getText());
        temp.setModelo(modelo.getText());
        temp.setPlaca(placa.getText());
        if(temp instanceof VehiculoCombustible){
            VehiculoCombustible tempMod = (VehiculoCombustible) temp;
            tempMod.setCombustible(controlador.getCombustibleDeString(combustible.getSelectedItemText()));
        }else{
            VehiculoElectrico tempMod = (VehiculoElectrico) temp;
            tempMod.setCargadoresCompatibles(cargadores.getSeleccionados());
        }
    }
    
    public void updatePanelLista(){
        panelLista.eliminarTodasLasTarjetas();
        panelLista.agregarTarjetas(((Usuario)controlador.getClienteSeleccionado()).getListaVehiculosFavoritos().toArray());
    }
    
    public void generarPanelOpciones(){
        panelOpciones = CreadorComponentesVista.generarPanelBlanco();
        int anchoPanelOpciones = (panelPrincipal.getWidth() / 3 - 40) * 2;
        int altoPanelOpciones = panelPrincipal.getHeight() / 4 - 20;
        int x = panelPrincipal.getWidth() - anchoPanelOpciones - 30;
        int y = panelInfo.getHeight() + panelInfo.getY() + 20;
        panelOpciones.setBounds(x, y, anchoPanelOpciones, altoPanelOpciones);
        
        JButton botonUnaVez = CreadorComponentesVista.generarBotonConIcono("UnaVez.png", "Usar una vez");
        JButton botonGuardar = CreadorComponentesVista.generarBotonConIcono("guardar.png", "Guardar");
        JButton botonModificar = CreadorComponentesVista.generarBotonConIcono("editar.png", "Modificar");
        JButton botonEliminar = CreadorComponentesVista.generarBotonConIcono("eliminar.png", "Eliminar");
        
        int anchoBotones = panelOpciones.getWidth()/8;
        int altoBotones = panelOpciones.getHeight()/2;
        botonUnaVez.setBounds(panelOpciones.getWidth()/9, panelOpciones.getHeight()/4, anchoBotones, altoBotones);
        botonGuardar.setBounds(panelOpciones.getWidth()/9*3, panelOpciones.getHeight()/4, anchoBotones, altoBotones);
        botonModificar.setBounds(panelOpciones.getWidth()/9*5, panelOpciones.getHeight()/4, anchoBotones, altoBotones);
        botonEliminar.setBounds(panelOpciones.getWidth()/9*7, panelOpciones.getHeight()/4, anchoBotones, altoBotones);
        
        botonUnaVez.addActionListener(e -> {
            if (validarEspacios()){
                Vehiculo temp = validarVehiculo();
               if(temp != null){
                   controlador.setVehiculoSeleccionado(temp);
               }else
                   JOptionPane.showMessageDialog(this, "Error El vehiculo ya esta almacenado");
            }else
                   JOptionPane.showMessageDialog(this, "Error Datos incompletos");
        });
        
        botonGuardar.addActionListener(e -> {
            if (validarEspacios()){
                Vehiculo temp = validarVehiculo();
               if(temp != null){
                   controlador.guardarVehiculo(temp);
                   updatePanelLista();
               }else
                   JOptionPane.showMessageDialog(this, "Error El vehiculo ya esta almacenado");
            }else
                   JOptionPane.showMessageDialog(this, "Error Datos incompletos");
            //dispose();
        });
        
        botonModificar.addActionListener(e -> {
            if (validarEspacios()){
                modificarVehiculo();
                controlador.modificarVehiculo();
            }else
                   JOptionPane.showMessageDialog(this, "Error Datos incompletos");
            //dispose();
        });
        
        botonEliminar.addActionListener(e -> {
            if (controlador.getVehiculoSeleccionado()!=null){
                if(JOptionPane.showConfirmDialog(this, "Seguro de que desea eliminar el Vehiculo" 
                        + controlador.getVehiculoSeleccionado().toString(),"Confirmación", JOptionPane.YES_NO_OPTION)==0){
                    controlador.eliminarVehiculo(controlador.getVehiculoSeleccionado());
                    updatePanelLista();
                }
                
            }else
                   JOptionPane.showMessageDialog(this, "Error Datos incompletos");
            //dispose();
        });
        
        panelOpciones.add(botonUnaVez);
        panelOpciones.add(botonGuardar);
        panelOpciones.add(botonModificar);
        panelOpciones.add(botonEliminar);
        
        
        panelPrincipal.add(panelOpciones);
    }

    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(GestionarVehiculos::new);
    }
}

