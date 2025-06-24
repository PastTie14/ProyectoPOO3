/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vista;

import Controlador.Controlador;
import Vista.Componentes.CreadorComponentesVista;
import Vista.Componentes.PanelConFondo;
import Vista.Componentes.PasswordFieldPersonalizado;
import Vista.Componentes.TextFieldPersonalizado;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 *
 * @author Ian
 */
public class Registrar extends JFrame{
    
    private final int screenWidth;
    private final int screenHeight;
    private JPanel panelPrincipal;
    private PanelConFondo panelFondo;
    private JButton abandonar;
    private JButton imgUser;
    TextFieldPersonalizado usuarioInput;
    PasswordFieldPersonalizado contrasennaInput;
    PasswordFieldPersonalizado contrasennaInputConfirmacion;
    
    Controlador controlador;
    
    /**
     *
     */
    public Registrar() {
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
        imgUser = CreadorComponentesVista.generarBotonImagen("nuevoUsuario.png", panelPrincipal.getWidth()/3+50, panelPrincipal.getWidth()/3+50);
        panelPrincipal.add(imgUser);
        
        imgUser.setBounds((panelPrincipal.getWidth()/3)-25,100,panelPrincipal.getWidth()/3+50,panelPrincipal.getWidth()/3+50);
        
        usuarioInput = new TextFieldPersonalizado("Digite su usuario","Usuario");
        usuarioInput.setBounds(panelPrincipal.getWidth()/3, imgUser.getY() + imgUser.getHeight() + 30, panelPrincipal.getWidth()/3, 50);
        
        contrasennaInput = new PasswordFieldPersonalizado("Digite su contraseña","Contraseña");
        contrasennaInput.setBounds(panelPrincipal.getWidth()/3, usuarioInput.getY() + usuarioInput.getHeight() + 30, panelPrincipal.getWidth()/3, 50);
        
        contrasennaInputConfirmacion = new PasswordFieldPersonalizado("Confirme su contraseña","Contraseña");
        contrasennaInputConfirmacion.setBounds(panelPrincipal.getWidth()/3, contrasennaInput.getY() + contrasennaInput.getHeight() + 30, panelPrincipal.getWidth()/3, 50);
        
        panelPrincipal.add(contrasennaInput);
        panelPrincipal.add(contrasennaInputConfirmacion);
        panelPrincipal.add(usuarioInput);
        
        
        JButton crearUsuario = CreadorComponentesVista.generarBotonConIcono("crearUsuario.png", "Crear Usuario");
        
        crearUsuario.setBounds(panelPrincipal.getWidth()/3, contrasennaInputConfirmacion.getY() + contrasennaInputConfirmacion.getHeight() + 30, panelPrincipal.getWidth()/3, 30);
        
        crearUsuario.addActionListener(e -> {
            System.out.println(contrasennaInput.getText());
            if (contrasennaInput.getText().equals(contrasennaInputConfirmacion.getText())){
                if(controlador.guardarUsuario(usuarioInput.getText(),contrasennaInput.getText())){
                    Ingresar ing = new Ingresar();
                    ing.setVisible(true);
                    ing.setUserText(usuarioInput.getText());
                    dispose();
                }else
                    JOptionPane.showMessageDialog(this, "Ya existe un usuario con el nombre:" + usuarioInput.getText());
            }else
                JOptionPane.showMessageDialog(this,"Las contraseñas no coinciden");
            
        });
        
        panelPrincipal.add(crearUsuario);
        repaint();
    }
    
    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(Ingresar::new);
    }
}
