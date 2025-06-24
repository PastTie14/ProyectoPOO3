/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vista.Componentes;

import javax.swing.JTextField;
import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

/**
 *
 * @author Ian
 */



public class TextFieldPersonalizado extends JPanel {
    private JLabel titleLabel;
    private JTextField textField;
    private String placeholderText;
    private Color placeholderColor = Color.GRAY;
    private Color textColor = Color.BLACK;
    private boolean isPlaceholderVisible = true;
    
    /**
     *
     * @param title
     * @param placeholder
     */
    public TextFieldPersonalizado(String title, String placeholder) {
        this.placeholderText = placeholder;
        initComponents(title, placeholder);
        setupLayout();
        setupPlaceholder();
    }
    
    private void initComponents(String title, String placeholder) {
        titleLabel = new JLabel(title);
        textField = new JTextField();
        
        // Configurar el estilo del título
        titleLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        titleLabel.setForeground(Color.GRAY);
        titleLabel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.LIGHT_GRAY,0),
            BorderFactory.createEmptyBorder(1, 10, 1, 10)
        ));
        
        // Configurar el estilo del textField
        textField.setFont(new Font("Arial", Font.PLAIN, 14));
        textField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.LIGHT_GRAY,0),
            BorderFactory.createEmptyBorder(6, 10, 6, 10)
        ));
        // Mostrar placeholder inicialmente
        textField.setText(placeholder);
        textField.setForeground(placeholderColor);
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout(0, 5));
        add(titleLabel, BorderLayout.NORTH);
        add(textField, BorderLayout.CENTER);
        
        // Configurar el panel principal
        setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.BLACK));
        setOpaque(false); // Importante para que el fondo redondeado se vea
        setBackground(Color.WHITE); // O el color que quieras para el fondo redondeado
    }
    
    private void setupPlaceholder() {
        textField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (isPlaceholderVisible) {
                    textField.setText("");
                    textField.setForeground(textColor);
                    isPlaceholderVisible = false;
                }
            }
            
            @Override
            public void focusLost(FocusEvent e) {
                if (textField.getText().isEmpty()) {
                    textField.setText(placeholderText);
                    textField.setForeground(placeholderColor);
                    isPlaceholderVisible = true;
                }
            }
        });
    }
    
    /**
     *
     * @param g
     */
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Color de fondo
        g2.setColor(getBackground());

        // Dibuja el fondo redondeado
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10); // 20 = radio de esquina

        g2.dispose();
        super.paintComponent(g);
    }
    
    // Métodos públicos para interactuar con el componente

    /**
     *
     * @return
     */
    public String getText() {
        return isPlaceholderVisible ? "" : textField.getText();
    }
    
    /**
     *
     * @param text
     */
    public void setText(String text) {
        if (text == null || text.isEmpty()) {
            textField.setText(placeholderText);
            textField.setForeground(placeholderColor);
            isPlaceholderVisible = true;
        } else {
            textField.setText(text);
            textField.setForeground(textColor);
            isPlaceholderVisible = false;
        }
    }
    
    /**
     *
     * @param title
     */
    public void setTitle(String title) {
        titleLabel.setText(title);
    }
    
    /**
     *
     * @param placeholder
     */
    public void setPlaceholder(String placeholder) {
        this.placeholderText = placeholder;
        if (isPlaceholderVisible) {
            textField.setText(placeholder);
        }
    }
    
    /**
     *
     * @return
     */
    public JTextField getTextField() {
        return textField;
    }
    
    /**
     *
     * @param enabled
     */
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        textField.setEnabled(enabled);
        titleLabel.setEnabled(enabled);
    }
    
}

