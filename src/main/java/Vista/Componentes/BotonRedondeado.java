/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vista.Componentes;

/**
 *
 * @author Ian
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;

public class BotonRedondeado extends JButton {
    private Color colorBase = new Color(230, 230, 230);
    private Color colorHover = new Color(200, 200, 200);
    private boolean hovering = false;

    public BotonRedondeado(String texto, Icon icono) {
        super(texto, icono);
        setFont(new Font("Arial", Font.PLAIN, 16));
        setForeground(Color.BLACK);
        setContentAreaFilled(false); 
        setFocusPainted(false);
        setBorderPainted(false);
        setOpaque(false); 
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setHorizontalAlignment(SwingConstants.CENTER);
        setIconTextGap(10);
        setMargin(new Insets(10, 15, 10, 15));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                hovering = true;
                setFont(getFont().deriveFont(17f));
                setMargin(new Insets(12, 18, 12, 18));
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                hovering = false;
                setFont(getFont().deriveFont(16f));
                setMargin(new Insets(10, 15, 10, 15));
                repaint();
            }
        });
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Recorte redondeado
        Shape clip = new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 20, 20);
        g2.setClip(clip);

        // Fondo
        g2.setColor(hovering ? colorHover : colorBase);
        g2.fill(clip);

        // Ícono y texto centrados
        Icon icon = getIcon();
        String text = getText();

        FontMetrics fm = g2.getFontMetrics(getFont());
        int textWidth = fm.stringWidth(text);
        int textHeight = fm.getAscent();

        int iconWidth = (icon != null) ? icon.getIconWidth() : 0;
        int iconHeight = (icon != null) ? icon.getIconHeight() : 0;
        int gap = (icon != null && text != null && !text.isEmpty()) ? getIconTextGap() : 0;

        int totalWidth = iconWidth + gap + textWidth;
        int xStart = (getWidth() - totalWidth) / 2;
        int yIcon = (getHeight() - iconHeight) / 2;
        int yText = (getHeight() + textHeight) / 2 - 2;

        // Dibuja el ícono
        if (icon != null) {
            icon.paintIcon(this, g2, xStart, yIcon);
        }

        // Dibuja el texto
        g2.setColor(getForeground());
        g2.setFont(getFont());
        g2.drawString(text, xStart + iconWidth + gap, yText);

        g2.dispose();
    }
    
   @Override
    protected void paintBorder(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(Color.LIGHT_GRAY);
        g2.setStroke(new BasicStroke(1));
        g2.draw(new RoundRectangle2D.Float(0, 0, getWidth() - 1, getHeight() - 1, 20, 20));

        g2.dispose();
    }
}
