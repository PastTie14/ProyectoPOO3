/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelos.Clientes;

import java.util.ArrayList;
import javax.swing.JFrame;

/**
 *
 * @author Ian
 */
import java.util.ArrayList;

/**
 * Clase que administra la lista de clientes (usuarios y administradores).
 * Permite cargar, guardar, validar y obtener clientes del sistema.
 */
public class AdministradorClientes {

    ArrayList<Cliente> listaClientes;

    /**
     * Constructor que inicializa la lista de clientes vacía.
     */
    public AdministradorClientes() {
        this.listaClientes = new ArrayList<>();
    }

    /**
     * Carga la lista de clientes desde un archivo binario.
     *
     * @return true si se cargaron correctamente, false en caso contrario
     */
    public boolean cargarClientes() {
        ArrayList<Object> temp = Utilidades.Utilidades.leerObjetos(Utilidades.Utilidades.obtenerRutaBase() + "\\datos\\Clientes.bin");
        if (temp != null) {
            try {
                for (Object object : temp) {
                    listaClientes.add((Cliente) object);
                }
                return true;
            } catch (ClassCastException e) {
                System.out.println("Los datos leidos no corresponden a un cliente");
            }
        }
        return false;
    }

    /**
     * Guarda los datos modificados de la lista de clientes en el archivo.
     *
     * @return true si se guardaron correctamente, false en caso contrario
     */
    public boolean guardarDatosModificados() {
        if (!Utilidades.Utilidades.modificarObjeto(listaClientes.toArray(), Utilidades.Utilidades.obtenerRutaBase() + "\\datos\\Clientes.bin")) {
            return false;
        }
        return true;
    }

    /**
     * Guarda un nuevo usuario en la lista y archivo si el nombre es válido.
     *
     * @param nombre Nombre del nuevo usuario
     * @param contrasenna Contraseña del nuevo usuario
     * @return true si se guardó correctamente, false si el usuario ya existe
     */
    public boolean guardarNuevoUsuario(String nombre, String contrasenna) {
        if (validarNuevoUsuario(nombre)) {
            Usuario user = new Usuario(nombre, contrasenna);
            listaClientes.add(user);
            return Utilidades.Utilidades.guardarNuevoObjeto(user, Utilidades.Utilidades.obtenerRutaBase() + "\\datos\\Clientes.bin");
        }
        return false;
    }

    /**
     * Guarda un nuevo administrador en la lista y archivo si el nombre es válido.
     *
     * @param nombre Nombre del nuevo administrador
     * @param contrasenna Contraseña del nuevo administrador
     * @return true si se guardó correctamente, false si el administrador ya existe
     */
    public boolean guardarNuevoAdmin(String nombre, String contrasenna) {
        if (validarNuevoUsuario(nombre)) {
            Admin user = new Admin(nombre, contrasenna);
            listaClientes.add(user);
            return Utilidades.Utilidades.guardarNuevoObjeto(user, Utilidades.Utilidades.obtenerRutaBase() + "\\datos\\Clientes.bin");
        }
        return false;
    }

    /**
     * Valida que el nombre de usuario no exista ya en la lista.
     *
     * @param nombre Nombre a validar
     * @return true si el nombre es válido (no existe), false si ya está en uso
     */
    public boolean validarNuevoUsuario(String nombre) {
        for (Cliente cliente : listaClientes) {
            if (cliente.getNombre().equals(nombre)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Obtiene un cliente que coincida con el nombre de usuario y contraseña.
     *
     * @param user Nombre de usuario
     * @param contrasenna Contraseña
     * @return El cliente si se encuentra y las credenciales coinciden, o null en caso contrario
     */
    public Cliente obtenerCliente(String user, String contrasenna) {
        for (Cliente cliente : listaClientes) {
            if (cliente.getNombre().equals(user)) {
                if (cliente.getContrasennia().equals(contrasenna))
                    return cliente;
            }
        }
        return null;
    }
}
