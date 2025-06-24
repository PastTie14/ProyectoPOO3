/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vista.Componentes;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Componente personalizado que muestra una lista de objetos con checkboxes.Usa ícono de check personalizado si se proporciona.
 * 
 * @author Ian
 * @param <T>
 */
public class ListaCheckBoxPersonalizada<T> extends JPanel {

    private final List<JCheckBox> checkBoxes = new ArrayList<>();
    private final List<T> objetos = new ArrayList<>();
    private Icon checkIcon = null;

    /**
     *
     * @param elementos
     */
    public ListaCheckBoxPersonalizada(T[] elementos) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Color.WHITE); // Fondo blanco para mantener estética uniforme
        construirLista(elementos);
    }

    private void construirLista(T[] elementos) {
        for (T elemento : elementos) {
            JCheckBox checkBox = new JCheckBox(elemento.toString());
            checkBox.setFont(new Font("Arial", Font.PLAIN, 14));
            checkBox.setBackground(Color.WHITE);
            checkBox.setAlignmentX(Component.LEFT_ALIGNMENT);

            if (checkIcon != null) {
                checkBox.setSelectedIcon(checkIcon); // Aplica el ícono personalizado al estar marcado
            }

            objetos.add(elemento);
            checkBoxes.add(checkBox);
            add(checkBox);
        }
    }

    /**
     *
     * @param icono
     */
    public void setCheckIcon(Icon icono) {
        this.checkIcon = icono;
        for (JCheckBox cb : checkBoxes) {
            cb.setSelectedIcon(icono);
        }
        revalidate();
        repaint();
    }

    /**
     *
     * @param habilitados
     */
    public void setTodosHabilitados(boolean habilitados) {
        for (JCheckBox cb : checkBoxes) {
            cb.setEnabled(habilitados);
        }
    }

    /**
     *
     * @return
     */
    public ArrayList<T> getSeleccionados() {
        ArrayList<T> seleccionados = new ArrayList<>();
        for (int i = 0; i < checkBoxes.size(); i++) {
            if (checkBoxes.get(i).isSelected()) {
                seleccionados.add(objetos.get(i));
            }
        }
        return seleccionados;
    }
}
