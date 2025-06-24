/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vista;

import Controlador.Controlador;
import Vista.Componentes.CreadorComponentesVista;
import Utilidades.UtilidadesVisuales;
import Vista.Componentes.PanelConFondo;
import Vista.Componentes.PasswordFieldPersonalizado;
import Vista.Componentes.TextFieldPersonalizado;
import javax.swing.*;
import java.awt.*;


/**
 *
 * @author Ian
 */

public class Ingresar extends JFrame{
    
    private final int screenWidth;
    private final int screenHeight;
    private JPanel panelPrincipal;
    private PanelConFondo panelFondo;
    private JButton abandonar;
    private JButton imgUser;
    TextFieldPersonalizado usuarioInput;
    PasswordFieldPersonalizado contrasennaInput;
    
    
    private Controlador controlador;
    
    public Ingresar() {
        
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
        generarContenido();
    }
    
    private void generarPanelPrincipal(){
        panelPrincipal = CreadorComponentesVista.generarPanelUnTercio(screenWidth, screenHeight);
        panelPrincipal.setOpaque(true);
        int x = screenWidth / 3; 
        int y = 50; 
        panelPrincipal.setBounds(x, y, screenWidth/3, screenHeight-100);
        panelFondo.add(panelPrincipal);
        this.repaint();
    }
    
    private void generarContenido(){
        abandonar = CreadorComponentesVista.generarBotonImagen("cancelar.png",20,20);
        panelPrincipal.add(abandonar);
        abandonar.setBounds(panelPrincipal.getWidth()-50, 25, 20, 20);
        abandonar.addActionListener(e -> {
            dispose();
        });
        imgUser = CreadorComponentesVista.generarBotonImagen("user.png", panelPrincipal.getWidth()/3+50, panelPrincipal.getWidth()/3+50);
        panelPrincipal.add(imgUser);
        
        imgUser.setBounds((panelPrincipal.getWidth()/3)-25,100,panelPrincipal.getWidth()/3+50,panelPrincipal.getWidth()/3+50);
        
        usuarioInput = new TextFieldPersonalizado("Digite su usuario","Usuario");
        usuarioInput.setBounds(panelPrincipal.getWidth()/3, imgUser.getY() + imgUser.getHeight() + 30, panelPrincipal.getWidth()/3, 50);
        
        contrasennaInput = new PasswordFieldPersonalizado("Digite su contraseña","Contraseña");
        contrasennaInput.setBounds(panelPrincipal.getWidth()/3, usuarioInput.getY() + usuarioInput.getHeight() + 30, panelPrincipal.getWidth()/3, 50);
        
        panelPrincipal.add(contrasennaInput);
        panelPrincipal.add(usuarioInput);
        
        
        JButton ingresar = CreadorComponentesVista.generarBotonConIcono("ingresar.png", "Ingresar");
        JButton crearUsuario = CreadorComponentesVista.generarBotonConIcono("crearUsuario.png", "Crear Usuario");
        
        ingresar.setBounds(panelPrincipal.getWidth()/3, contrasennaInput.getY() + contrasennaInput.getHeight() + 30, panelPrincipal.getWidth()/3, 30);
        crearUsuario.setBounds(panelPrincipal.getWidth()/3, ingresar.getY() + ingresar.getHeight() + 30, panelPrincipal.getWidth()/3, 30);
        
        ingresar.addActionListener(e -> {
            JFrame menu = controlador.ingresar(controlador.validarCredenciales(
                            usuarioInput.getText(), contrasennaInput.getText().toString()));
            if (menu!=null){
                menu.setVisible(true);
                dispose();
            }else {
                JOptionPane.showMessageDialog(null, "Credenciales Incorrectas.");
            }  
        });
        
        crearUsuario.addActionListener(e -> {
            Registrar reg = new Registrar();
            reg.setVisible(true);
            dispose();
        });
        
        panelPrincipal.add(ingresar);
        panelPrincipal.add(crearUsuario);
        repaint();
        
    }
    
    public void setUserText(String text){
        usuarioInput.setText(text);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(Ingresar::new);
    }
}