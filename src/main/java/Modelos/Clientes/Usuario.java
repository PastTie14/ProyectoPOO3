/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelos.Clientes;

import Modelo.Vehiculos.Vehiculo;
import Vista.Usuario.MenuUsuario;
import java.util.ArrayList;
import javax.swing.JFrame;

/**
 *
 * @author Ian
 */
import javax.swing.JFrame;
import java.util.ArrayList;

/**
 * Clase que representa un usuario en el sistema.
 * Extiende de {@code Cliente} e implementa la interfaz {@code LogIn}.
 * Gestiona una lista de vehículos favoritos y un vehículo seleccionado.
 */
public class Usuario extends Cliente implements LogIn {

    ArrayList<Vehiculo> listaVehiculosFavoritos;
    Vehiculo vehiculoSeleccionado;

    /**
     * Constructor para crear un usuario con nombre y contraseña.
     *
     * @param nombre Nombre del usuario
     * @param contrasennia Contraseña del usuario
     */
    public Usuario(String nombre, String contrasennia) {
        super(nombre, contrasennia);
        listaVehiculosFavoritos = new ArrayList<>();
    }

    /**
     * Valida si un vehículo con la placa dada no existe en la lista de favoritos.
     *
     * @param placa Placa del vehículo a validar
     * @return true si no existe, false si ya está registrado
     */
    public boolean validarVehiculo(String placa) {
        for (Vehiculo vehiculo : listaVehiculosFavoritos) {
            if (vehiculo.getPlaca().equals(placa))
                return false;
        }
        return true;
    }

    /**
     * Guarda un vehículo en la lista de favoritos si no está previamente agregado.
     *
     * @param vehiculo Vehículo a agregar
     * @return true si se agregó, false si ya existía
     */
    public boolean guardarVehiculo(Vehiculo vehiculo) {
        if (validarVehiculo(vehiculo.getPlaca())) {
            listaVehiculosFavoritos.add(vehiculo);
            return true;
        }
        return false;
    }

    /**
     * Elimina un vehículo de la lista de favoritos.
     *
     * @param vehiculo Vehículo a eliminar
     * @return true si se eliminó, false si no se encontró
     */
    public boolean eliminarVehiculo(Vehiculo vehiculo) {
        return listaVehiculosFavoritos.remove(vehiculo);
    }

    public ArrayList<Vehiculo> getListaVehiculosFavoritos() {
        return listaVehiculosFavoritos;
    }

    public Vehiculo getVehiculoSeleccionado() {
        return vehiculoSeleccionado;
    }

    public void setVehiculoSeleccionado(Vehiculo vehiculoSeleccionado) {
        this.vehiculoSeleccionado = vehiculoSeleccionado;
    }

    /**
     * Implementación del método {@code ingresar} de la interfaz {@code LogIn}.
     * Retorna la ventana principal para un usuario.
     *
     * @return Una instancia de {@code MenuUsuario}
     */
    @Override
    public JFrame ingresar() {
        return new MenuUsuario();
    }
}

