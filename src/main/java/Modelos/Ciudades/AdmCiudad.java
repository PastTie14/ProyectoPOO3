/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelos.Ciudades;

import Modelos.Clientes.Cliente;
import Modelos.Clientes.Usuario;
import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author Sebas Masís
 */
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que administra el conjunto de ciudades dentro del sistema.
 * Permite operaciones como agregar, eliminar, validar y gestionar conexiones entre ciudades.
 */
public class AdmCiudad {
    private List<Ciudad> ciudades;
    private Ciudad ciudadSeleccionada;

    /**
     * Constructor que inicializa la lista de ciudades vacía.
     */
    public AdmCiudad() {
        this.ciudades = new ArrayList<>();
    }

    /**
     * Elimina una ciudad del sistema y guarda los cambios.
     *
     * @param ciudad Ciudad a eliminar
     * @return true si la ciudad fue eliminada correctamente
     */
    public boolean eliminar(Ciudad ciudad) {
        boolean res = ciudades.remove(ciudad);
        guardarDatosModificados();
        return res;
    }

    /**
     * Elimina todas las conexiones que apuntan hacia la ciudad especificada.
     * (Método pendiente de implementación)
     *
     * @param ciudad Ciudad destino de las conexiones a eliminar
     */
    public void eliminarConexionesHacia(Ciudad ciudad) {
        // Por implementar
    }

    /**
     * Agrega una nueva conexión desde la ciudad seleccionada hacia otra ciudad.
     *
     * @param destino Ciudad destino
     * @param distancia Distancia de la conexión
     * @param minutos Tiempo en minutos de recorrido
     * @param consumo Consumo estimado
     * @return true si se agregó correctamente
     */
    public boolean guardarConexion(Ciudad destino, double distancia, int minutos, int consumo) {
        boolean res = ciudadSeleccionada.agregarConexion(destino, distancia, minutos, consumo);
        guardarDatosModificados();
        return res;
    }

    /**
     * Elimina una conexión desde la ciudad seleccionada.
     *
     * @param conexion Conexión a eliminar
     * @return true si fue eliminada correctamente
     */
    public boolean eliminarConexion(Conexion conexion) {
        boolean res = ciudadSeleccionada.eliminarConexion(conexion);
        guardarDatosModificados();
        return res;
    }

    /**
     * Verifica si una ciudad con el mismo nombre y coordenadas ya existe.
     *
     * @param nombre Nombre de la ciudad
     * @param longitudX Coordenada X
     * @param latitudY Coordenada Y
     * @return true si la ciudad es válida y no está repetida
     */
    public boolean validarNuevaCiudad(String nombre, double longitudX, double latitudY) {
        for (Ciudad ciudad : ciudades) {
            if (ciudad.getNombre().equals(nombre)
                    && ciudad.getLongitudX() == longitudX
                    && ciudad.getLatitudY() == latitudY)
                return false;
        }
        return true;
    }

    /**
     * Carga las ciudades desde un archivo binario.
     *
     * @return true si se cargaron correctamente
     */
    public boolean cargarCiudades() {
        ArrayList<Object> temp = Utilidades.Utilidades.leerObjetos(
                Utilidades.Utilidades.obtenerRutaBase() + "\\datos\\Ciudades.bin");
        if (temp != null) {
            try {
                for (Object object : temp) {
                    ciudades.add((Ciudad) object);
                }
                return true;
            } catch (ClassCastException e) {
                System.out.println("Los datos leídos no corresponden a una Ciudad: " + e);
            }
        }
        return false;
    }

    /**
     * Guarda en el archivo los cambios realizados sobre la lista de ciudades.
     *
     * @return true si los datos se guardaron exitosamente
     */
    public boolean guardarDatosModificados() {
        if (!Utilidades.Utilidades.modificarObjeto(
                ciudades.toArray(),
                Utilidades.Utilidades.obtenerRutaBase() + "\\datos\\Ciudades.bin")) {
            return false;
        }
        return true;
    }

    /**
     * Guarda una nueva ciudad, si no existe ya una con el mismo nombre y coordenadas.
     *
     * @param nombre Nombre de la ciudad
     * @param longitudX Coordenada X
     * @param latitudY Coordenada Y
     * @return true si la ciudad se guardó correctamente
     */
    public boolean guardarNuevaCiudad(String nombre, double longitudX, double latitudY) {
        if (validarNuevaCiudad(nombre, longitudX, latitudY)) {
            Ciudad city = new Ciudad(nombre, longitudX, latitudY);
            ciudades.add(city);
            return Utilidades.Utilidades.guardarNuevoObjeto(
                    city,
                    Utilidades.Utilidades.obtenerRutaBase() + "\\datos\\Ciudades.bin");
        }
        return false;
    }

    public List<Ciudad> getCiudades() {
        return ciudades;
    }

    public Ciudad getCiudadSeleccionada() {
        return ciudadSeleccionada;
    }

    public void setCiudadSeleccionada(Ciudad ciudadSeleccionada) {
        this.ciudadSeleccionada = ciudadSeleccionada;
    }
}

