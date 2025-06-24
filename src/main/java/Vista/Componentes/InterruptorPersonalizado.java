/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vista.Componentes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Interruptor estético con etiquetas personalizables y eventos de cambio de estado.
 * @author Ian
 */
public class InterruptorPersonalizado extends JComponent {

    private boolean encendido = false;
    private String textoOn;
    private String textoOff;
    private Color colorOn = new Color(76, 175, 80); // Verde
    private Color colorOff = new Color(189, 189, 189); // Gris
    private final List<ActionListener> listeners = new ArrayList<>();

    /**
     *
     * @param textoOn
     * @param textoOff
     */
    public InterruptorPersonalizado(String textoOn, String textoOff) {
        this.textoOn = textoOn;
        this.textoOff = textoOff;

        setPreferredSize(new Dimension(70, 30));
        setCursor(new Cursor(Cursor.HAND_CURSOR));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                encendido = !encendido;
                repaint();
                notificarCambio();
            }
        });
    }

    private void notificarCambio() {
        ActionEvent evento = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "cambio");
        for (ActionListener listener : listeners) {
            listener.actionPerformed(evento);
        }
    }

    /**
     *
     * @param listener
     */
    public void addCambioListener(ActionListener listener) {
        listeners.add(listener);
    }

    /**
     *
     * @return
     */
    public boolean isEncendido() {
        return encendido;
    }

    /**
     *
     * @param encendido
     */
    public void setEncendido(boolean encendido) {
        this.encendido = encendido;
        repaint();
    }

    /**
     *
     * @param on
     * @param off
     */
    public void setTextos(String on, String off) {
        this.textoOn = on;
        this.textoOff = off;
        repaint();
    }

    /**
     *
     * @param g
     */
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();

        // Antialiasing
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int width = getWidth();
        int height = getHeight();
        int padding = 4;
        int diameter = height - padding * 2;

        // Fondo del interruptor
        g2.setColor(encendido ? colorOn : colorOff);
        g2.fillRoundRect(0, 0, width, height, height, height);

        // Círculo que se mueve
        int circleX = encendido ? width - diameter - padding : padding;
        g2.setColor(Color.WHITE);
        g2.fillOval(circleX, padding, diameter, diameter);

        // Texto
        g2.setFont(new Font("Arial", Font.BOLD, 12));
        g2.setColor(Color.WHITE);
        String texto = encendido ? textoOn : textoOff;
        FontMetrics fm = g2.getFontMetrics();
        int textWidth = fm.stringWidth(texto);
        int textX = (width - textWidth) / 2;
        int textY = (height + fm.getAscent() - fm.getDescent()) / 2 - 1;
        g2.drawString(texto, textX, textY);

        g2.dispose();
    }
}
