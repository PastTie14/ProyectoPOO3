/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vista.Componentes;

import Modelos.Ciudades.Conexion;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 *
 * @author Ian
 */
public class ComboBoxPersonalizadoObj extends JPanel {
    private JLabel titleLabel;
    private JComboBox<Object> comboBox;
    private String placeholder;
    private boolean isPlaceholderVisible = true;
    private Color placeholderColor = Color.GRAY;
    private Color textColor = Color.BLACK;

    /**
     *
     * @param title
     * @param placeholder
     */
    public ComboBoxPersonalizadoObj(String title, String placeholder) {
        this.placeholder = placeholder;
        initComponents(title);
        setupLayout();
        setupPlaceholder();
    }

    private void initComponents(String title) {
        titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        titleLabel.setForeground(Color.BLACK);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(1, 10, 1, 10));

        comboBox = new JComboBox<>();
        comboBox.setFont(new Font("Arial", Font.PLAIN, 14));
        comboBox.setBorder(BorderFactory.createEmptyBorder(6, 10, 6, 10));
        comboBox.setEditable(false); // Para que se pueda ver el placeholder

        // Establecer placeholder como primer valor
        comboBox.addItem(placeholder);
        comboBox.setSelectedIndex(0);
        comboBox.setForeground(placeholderColor);

        // Evita que se dispare acción al cargar el placeholder
        comboBox.addActionListener(e -> {
            if (isPlaceholderVisible && comboBox.getSelectedIndex() != 0) {
                comboBox.setForeground(textColor);
                isPlaceholderVisible = false;
            }
        });
    }

    private void setupLayout() {
        setLayout(new BorderLayout(0, 5));
        add(titleLabel, BorderLayout.NORTH);
        add(comboBox, BorderLayout.CENTER);

        setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.BLACK));
        setOpaque(false);
        setBackground(Color.WHITE);
    }

    private void setupPlaceholder() {
        comboBox.getEditor().getEditorComponent().addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (isPlaceholderVisible) {
                    comboBox.setSelectedItem("");
                    comboBox.setForeground(textColor);
                    isPlaceholderVisible = false;
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (comboBox.getSelectedItem() == null || comboBox.getSelectedItem().toString().isEmpty()) {
                    comboBox.setSelectedItem(placeholder);
                    comboBox.setForeground(placeholderColor);
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
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
        g2.dispose();
        super.paintComponent(g);
    }

    /**
     *
     * @param item
     */
    public void addItem(Object item) {
        if (isPlaceholderVisible && comboBox.getItemCount() == 1) {
            Object firstItem = comboBox.getItemAt(0);
            if (firstItem != null && firstItem.equals(placeholder)) {
                comboBox.removeAllItems();
            }
        }
        comboBox.addItem(item);
    }

    /**
     *
     * @return
     */
    public Object getSelectedItemText() {
        Object selected = comboBox.getSelectedItem();
        return isPlaceholderVisible ? "" : selected;
    }
    
    /**
     *
     * @return
     */
    public Object getSelectedItem() {
    Object selected = comboBox.getSelectedItem();
    if (selected == null || isPlaceholderVisible || placeholder.equals(selected)) {
        return null;
    }
    return selected;
}

    /**
     *
     * @param text
     */
    public void setSelectedItemText(String text) {
        comboBox.setSelectedItem(text);
        comboBox.setForeground(textColor);
        isPlaceholderVisible = false;
    }

    /**
     *
     */
    public void eliminarTodosLosItems() {
        comboBox.removeAllItems();
        comboBox.addItem(null);
        comboBox.setSelectedIndex(0);
        comboBox.setForeground(placeholderColor);
        isPlaceholderVisible = true;
    }
    
    /**
     *
     * @param item
     */
    public void seleccionarItem(Object item) {
        ComboBoxModel<Object> model = comboBox.getModel();
        for (int i = 0; i < model.getSize(); i++) {
            Object element = model.getElementAt(i);
            if (element != null && element.equals(item)) {
                comboBox.setSelectedItem(item);
                comboBox.setForeground(textColor);
                isPlaceholderVisible = false;
                return;
            }
        }
    }

    /**
     *
     * @param enabled
     */
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        comboBox.setEnabled(enabled);
        titleLabel.setEnabled(enabled);
    }

    /**
     *
     * @return
     */
    public JComboBox<Object> getComboBox() {
        return comboBox;
    }
    
}
