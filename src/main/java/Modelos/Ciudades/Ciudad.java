/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelos.Ciudades;
import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;
import java.util.HashSet;

/**
 *
 * @author Sebas Masís
 */
import java.io.Serializable;
import java.util.*;

/**
 * Clase que representa una ciudad dentro del sistema.
 * Contiene estaciones, conexiones hacia otras ciudades, y métodos de validación y búsqueda de rutas.
 */
public class Ciudad implements Serializable {
    private String nombre;
    private ArrayList<Estacion> estaciones;
    private ArrayList<Conexion> conexiones;
    private double longitudX;
    private double latitudY;

    // Constructor
    public Ciudad(String nombre) {
        this.nombre = nombre;
        this.estaciones = new ArrayList<>();
        this.conexiones = new ArrayList<>();
    }

    public Ciudad(String nombre, double longitudX, double latitudY) {
        this.nombre = nombre;
        this.longitudX = longitudX;
        this.latitudY = latitudY;
        this.estaciones = new ArrayList<>();
        this.conexiones = new ArrayList<>();
    }

    public String getNombre() { return nombre; }

    public List<Estacion> getEstaciones() { return estaciones; }

    public void setNombre(String nombre) { this.nombre = nombre; }

    public void setEstaciones(ArrayList<Estacion> estaciones) { this.estaciones = estaciones; }

    public double getLongitudX() { return longitudX; }

    public void setLongitudX(double longitudX) { this.longitudX = longitudX; }

    public double getLatitudY() { return latitudY; }

    public void setLatitudY(double latitudY) { this.latitudY = latitudY; }

    public String getConexionesAsString() {
        String str = "";
        for (Conexion conexion : conexiones) {
            str += conexion + ", ";
        }
        return str;
    }

    public String getEstacionesAsString() {
        String str = "";
        for (Estacion estacion : estaciones) {
            str += estacion + ", ";
        }
        return str;
    }

    /**
     * Devuelve toda la información de la ciudad en texto plano.
     *
     * @return Información de la ciudad como String
     */
    public String getFullInfo() {
        return " | Ciudad: " + nombre + "\n"
             + " | Longitud: " + longitudX + "\n"
             + " | Latitud: " + latitudY + "\n"
             + " | Conexiones: " + getConexionesAsString() + "\n"
             + " | Estaciones: " + getConexionesAsString();
    }

    /**
     * Devuelve la información de la ciudad en formato HTML.
     *
     * @return String HTML con la información de la ciudad
     */
    public String getFullInfoHTML() {
        return "<html>"
             + " Ciudad: " + nombre + "<br>"
             + " Longitud: " + longitudX + "<br>"
             + " Latitud: " + latitudY + "<br>"
             + " Conexiones: " + getConexionesAsString() + "<br>"
             + " Estaciones: " + getConexionesAsString()
             + "</html>";
    }

    @Override
    public String toString() {
        return "<html>"
             + " | Ciudad: " + nombre + "<br>"
             + " | Longitud: " + longitudX + "<br>"
             + " | Latitud: " + latitudY
             + "</html>";
    }

    //--------- Conexiones ---------

    /**
     * Verifica si ya existe una conexión hacia la ciudad dada.
     *
     * @param ciudad Ciudad destino
     * @return true si la conexión no existe aún
     */
    public boolean validarConexion(Ciudad ciudad) {
        for (Conexion conexion : conexiones) {
            if (conexion.getCiudad().equals(ciudad)) {
                return false;
            }
        }
        return true;
    }

    public ArrayList<Conexion> getConexiones() {
        return conexiones;
    }

    /**
     * Agrega una nueva conexión si no existe previamente.
     *
     * @param ciudad Ciudad destino
     * @param distancia Distancia entre ciudades
     * @param minutos Tiempo estimado
     * @param consumo Consumo estimado
     * @return true si se agregó exitosamente
     */
    public boolean agregarConexion(Ciudad ciudad, double distancia, int minutos, int consumo) {
        if (validarConexion(ciudad)) {
            Conexion temp = new Conexion(ciudad, distancia, minutos, consumo);
            conexiones.add(temp);
            return true;
        }
        return false;
    }

    /**
     * Elimina la conexión especificada.
     *
     * @param conexion Conexión a eliminar
     * @return true si se eliminó correctamente
     */
    public boolean eliminarConexion(Conexion conexion) {
        return conexiones.remove(conexion);
    }

    /**
     * Encuentra una ruta simple (no necesariamente la mejor) hacia una ciudad destino.
     *
     * @param destino Ciudad destino
     * @return Lista de conexiones que conforman la ruta, o null si no se encuentra
     */
    public ArrayList<Conexion> encontrarRutaHacia(Ciudad destino) {
        HashSet<Ciudad> visitadas = new HashSet<>();
        return encontrarRutaRecursiva(destino, visitadas);
    }

    /**
     * Método auxiliar recursivo para encontrar ruta hacia el destino.
     *
     * @param destino Ciudad destino
     * @param visitadas Conjunto de ciudades visitadas
     * @return Lista de conexiones si se encuentra ruta; null si no
     */
    private ArrayList<Conexion> encontrarRutaRecursiva(Ciudad destino, HashSet<Ciudad> visitadas) {
        if (this.equals(destino)) return new ArrayList<>();
        visitadas.add(this);
        for (Conexion conexion : conexiones) {
            Ciudad ciudadSiguiente = conexion.getCiudad();
            if (!visitadas.contains(ciudadSiguiente)) {
                ArrayList<Conexion> subRuta = ciudadSiguiente.encontrarRutaRecursiva(destino, visitadas);
                if (subRuta != null) {
                    subRuta.add(0, conexion);
                    return subRuta;
                }
            }
        }
        return null;
    }

    /**
     * Obtiene todas las rutas posibles hacia la ciudad destino.
     *
     * @param destino Ciudad destino
     * @return Lista de rutas (cada ruta es una lista de conexiones)
     */
    public ArrayList<ArrayList<Conexion>> obtenerTodasLasRutas(Ciudad destino) {
        ArrayList<ArrayList<Conexion>> rutas = new ArrayList<>();
        HashSet<Ciudad> visitadas = new HashSet<>();
        ArrayList<Conexion> rutaActual = new ArrayList<>();
        buscarTodasLasRutas(destino, visitadas, rutaActual, rutas);
        return rutas;
    }

    /**
     * Método auxiliar recursivo para encontrar todas las rutas posibles.
     *
     * @param destino Ciudad destino
     * @param visitadas Conjunto de ciudades visitadas
     * @param rutaActual Ruta actual construida hasta este punto
     * @param rutas Lista final de rutas encontradas
     */
    private void buscarTodasLasRutas(Ciudad destino, HashSet<Ciudad> visitadas,
                                     ArrayList<Conexion> rutaActual, ArrayList<ArrayList<Conexion>> rutas) {
        if (this.equals(destino)) {
            rutas.add(new ArrayList<>(rutaActual));
            return;
        }

        visitadas.add(this);

        for (Conexion conexion : conexiones) {
            Ciudad siguiente = conexion.getCiudad();
            if (!visitadas.contains(siguiente)) {
                rutaActual.add(conexion);
                siguiente.buscarTodasLasRutas(destino, visitadas, rutaActual, rutas);
                rutaActual.remove(rutaActual.size() - 1); // Backtracking
            }
        }

        visitadas.remove(this);
    }

    //---------- Estaciones ----------

    /**
     * Valida si una estación con el mismo nombre y tipo ya existe.
     *
     * @param nombre Nombre de la estación
     * @param tipo Tipo de estación
     * @return true si es válida (no existe aún)
     */
    public boolean validarEstacion(String nombre, String tipo) {
        for (Estacion estacion : estaciones) {
            if (estacion.getNombre().equals(nombre)) {
                if (estacion.getTipoEstacion().equals(tipo))
                    return false;
            }
        }
        return true;
    }

    /**
     * Agrega una nueva estación a la ciudad.
     *
     * @param newSC Estación a agregar
     */
    public void agregarEstacion(Estacion newSC) {
        estaciones.add(newSC);
    }

    /**
     * Elimina una estación de la ciudad según su nombre y tipo.
     *
     * @param SC Nombre de la estación
     * @param tipo Tipo de estación
     * @return true si fue eliminada exitosamente
     */
    public boolean removerEstacion(String SC, String tipo) {
        return estaciones.removeIf(e -> e.getNombre().equalsIgnoreCase(SC)
                                     && e.getTipoEstacion().equalsIgnoreCase(tipo));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ciudad)) return false;
        Ciudad ciudad = (Ciudad) o;
        return nombre.equals(ciudad.nombre);
    }

    @Override
    public int hashCode() {
        return nombre.hashCode();
    }
}

