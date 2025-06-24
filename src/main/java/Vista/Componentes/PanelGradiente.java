/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vista.Componentes;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import static java.awt.RenderingHints.VALUE_ANTIALIAS_ON;
import java.awt.geom.Point2D;
import javax.swing.JPanel;

/**
 *
 * @author Ian
 */
    public class PanelGradiente extends JPanel {

    /**
     *
     * @param g
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Point2D center = new Point2D.Double(getWidth() / 2.0, getHeight() / 2.0);
        Color colorA = new Color(156, 207, 255); // #9CCFFF
        Color colorB = new Color(251, 232, 204); // #FBE8CC

        double startAngle = 0.10 * 2 * Math.PI;
        double endAngle = 0.60 * 2 * Math.PI;

        for (int y = 0; y < getHeight(); y++) {
            for (int x = 0; x < getWidth(); x++) {
                double dx = x - center.getX();
                double dy = y - center.getY();
                double angle = Math.atan2(dy, dx);
                if (angle < 0) angle += 2 * Math.PI;

                // Interpolación circular continua (sin cortes)
                float t;
                if (startAngle < endAngle) {
                    if (angle < startAngle) angle += 2 * Math.PI; // wrap
                    t = (float) ((angle - startAngle) / (endAngle - startAngle));
                } else {
                    // si el rango cruza 0°
                    if (angle < startAngle && angle > endAngle) angle += 2 * Math.PI;
                    t = (float) ((angle - startAngle) / ((endAngle + 2 * Math.PI) - startAngle));
                }

                t = clamp(t, 0f, 1f);
                t = smoothstep(t); // suaviza visualmente la transición

                Color blended = blend(colorA, colorB, t);
                g2.setColor(blended);
                g2.drawLine(x, y, x, y);
            }
        }
    }

    private float clamp(float v, float min, float max) {
        return Math.max(min, Math.min(max, v));
    }

    private float smoothstep(float t) {
        return t * t * (3 - 2 * t); // opcional pero ayuda a que se vea más natural
    }

    private Color blend(Color a, Color b, float t) {
        int r = (int) (a.getRed() * (1 - t) + b.getRed() * t);
        int g = (int) (a.getGreen() * (1 - t) + b.getGreen() * t);
        int bVal = (int) (a.getBlue() * (1 - t) + b.getBlue() * t);
        return new Color(r, g, bVal);
    }
    }