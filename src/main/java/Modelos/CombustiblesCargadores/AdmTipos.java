/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelos.CombustiblesCargadores;

import Modelos.Clientes.Cliente;
import Modelos.Clientes.Usuario;
import java.util.ArrayList;

/**
 *
 * @author Ian
 */
import java.util.ArrayList;

/**
 * Clase que administra los tipos de combustibles y cargadores disponibles en el sistema.
 */
public class AdmTipos {
    ArrayList<Combustible> combustibles;
    ArrayList<Cargador> cargadores;

    /**
     * Constructor que inicializa las listas de combustibles y cargadores vacías.
     */
    public AdmTipos() {
        combustibles = new ArrayList<>();
        cargadores = new ArrayList<>();
    }

    /**
     * Elimina un combustible de la lista y guarda los cambios.
     *
     * @param combustible Combustible a eliminar
     * @return true si se eliminó correctamente, false si no se encontró
     */
    public boolean eliminarCombustible(Combustible combustible) {
        boolean res = combustibles.remove(combustible);
        if (res)
            guardarDatosModificadosCombustibles();
        return res;
    }

    /**
     * Elimina un cargador de la lista y guarda los cambios.
     *
     * @param cargador Cargador a eliminar
     * @return true si se eliminó correctamente, false si no se encontró
     */
    public boolean eliminarCargador(Cargador cargador) {
        boolean res = cargadores.remove(cargador);
        if (res)
            guardarDatosModificadosCargadores();
        return res;
    }

    /**
     * Valida que no exista un cargador con el mismo nombre.
     *
     * @param nombre Nombre a validar
     * @return true si es válido (no existe), false si ya existe
     */
    public boolean validarCargador(String nombre) {
        for (Cargador cargador : cargadores) {
            if (cargador.getNombre().equals(nombre))
                return false;
        }
        return true;
    }

    /**
     * Valida que no exista un combustible con el mismo nombre.
     *
     * @param nombre Nombre a validar
     * @return true si es válido (no existe), false si ya existe
     */
    public boolean validarCombustible(String nombre) {
        for (Combustible combustible : combustibles) {
            if (combustible.getNombre().equals(nombre))
                return false;
        }
        return true;
    }

    /**
     * Carga la lista de cargadores desde un archivo.
     *
     * @return true si se cargaron correctamente, false en caso contrario
     */
    public boolean cargarCargadores() {
        ArrayList<Object> temp = Utilidades.Utilidades.leerObjetos(Utilidades.Utilidades.obtenerRutaBase() + "\\datos\\Cargadores.bin");
        if (temp != null) {
            try {
                for (Object object : temp) {
                    cargadores.add((Cargador) object);
                }
                return true;
            } catch (ClassCastException e) {
                System.out.println("Los datos leidos no corresponden a un cargador");
            }
        }
        return false;
    }

    /**
     * Guarda los cambios realizados en la lista de cargadores.
     *
     * @return true si se guardaron correctamente, false en caso contrario
     */
    public boolean guardarDatosModificadosCargadores() {
        if (!Utilidades.Utilidades.modificarObjeto(cargadores.toArray(), Utilidades.Utilidades.obtenerRutaBase() + "\\datos\\Cargadores.bin")) {
            return false;
        }
        return true;
    }

    /**
     * Guarda un nuevo cargador si el nombre es válido.
     *
     * @param nombre Nombre del nuevo cargador
     * @return true si se guardó correctamente, false si el nombre ya existe
     */
    public boolean guardarNuevoCargador(String nombre) {
        if (validarCargador(nombre)) {
            Cargador temp = new Cargador(nombre);
            cargadores.add(temp);
            return Utilidades.Utilidades.guardarNuevoObjeto(temp, Utilidades.Utilidades.obtenerRutaBase() + "\\datos\\Cargadores.bin");
        }
        return false;
    }

    /**
     * Carga la lista de combustibles desde un archivo.
     *
     * @return true si se cargaron correctamente, false en caso contrario
     */
    public boolean cargarCombustibles() {
        ArrayList<Object> temp = Utilidades.Utilidades.leerObjetos(Utilidades.Utilidades.obtenerRutaBase() + "\\datos\\Combustibles.bin");
        if (temp != null) {
            try {
                for (Object object : temp) {
                    combustibles.add((Combustible) object);
                }
                return true;
            } catch (ClassCastException e) {
                System.out.println("Los datos leidos no corresponden a un cargador");
            }
        }
        return false;
    }

    /**
     * Guarda los cambios realizados en la lista de combustibles.
     *
     * @return true si se guardaron correctamente, false en caso contrario
     */
    public boolean guardarDatosModificadosCombustibles() {
        if (!Utilidades.Utilidades.modificarObjeto(combustibles.toArray(), Utilidades.Utilidades.obtenerRutaBase() + "\\datos\\Combustibles.bin")) {
            return false;
        }
        return true;
    }

    /**
     * Guarda un nuevo combustible si el nombre es válido.
     *
     * @param nombre Nombre del nuevo combustible
     * @return true si se guardó correctamente, false si el nombre ya existe
     */
    public boolean guardarNuevoCombustible(String nombre) {
        if (validarCargador(nombre)) {
            Combustible temp = new Combustible(nombre);
            combustibles.add(temp);
            return Utilidades.Utilidades.guardarNuevoObjeto(temp, Utilidades.Utilidades.obtenerRutaBase() + "\\datos\\Combustibles.bin");
        }
        return false;
    }

    /**
     *
     * @return
     */
    public ArrayList<Combustible> getCombustibles() {
        return combustibles;
    }

    /**
     *
     * @return
     */
    public ArrayList<Cargador> getCargadores() {
        return cargadores;
    }

    /**
     * Busca un combustible por su nombre.
     *
     * @param combustible Nombre del combustible
     * @return El objeto {@code Combustible} si se encuentra, o {@code null} si no existe
     */
    public Combustible getCombustibleDeString(String combustible) {
        for (Combustible combustible1 : combustibles) {
            if (combustible1.getNombre().equals(combustible)) {
                return combustible1;
            }
        }
        return null;
    }
}
