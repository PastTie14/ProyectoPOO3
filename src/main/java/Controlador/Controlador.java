/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.Vehiculos.Vehiculo;
import Modelo.Vehiculos.VehiculoCombustible;
import Modelos.Ciudades.AdmCiudad;
import Modelos.Ciudades.Ciudad;
import Modelos.Ciudades.Conexion;
import Modelos.Ciudades.Estacion;
import Modelos.Ciudades.EstacionCarga;
import Modelos.Ciudades.EstacionCombustible;
import Modelos.Clientes.*;
import Modelos.CombustiblesCargadores.AdmTipos;
import Modelos.CombustiblesCargadores.Cargador;
import Modelos.CombustiblesCargadores.Combustible;
import Modelos.CombustiblesCargadores.Tipos;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;

/**
 * Clase singleton que centraliza y controla la lógica del sistema,
 * manejando usuarios, ciudades, tipos de estaciones y más.
 */
public final class Controlador {
    private static Controlador instance;
    private AdministradorClientes adminClientes;
    private AdmCiudad adminCiudades;
    private AdmTipos adminTipos;
    private Cliente clienteSeleccionado;

    /**
     * Constructor privado del singleton Controlador.
     * Inicializa los administradores y carga datos desde archivos.
     */
    private Controlador() {
        adminClientes = new AdministradorClientes();
        adminCiudades = new AdmCiudad();
        adminTipos = new AdmTipos();

        adminClientes.cargarClientes();
        adminCiudades.cargarCiudades();
        adminTipos.cargarCargadores();
        adminTipos.cargarCombustibles();
    }

    /**
     * Obtiene la instancia única de Controlador.
     * 
     * @return instancia del controlador
     */
    public static Controlador getInstance() {
        if (instance == null)
            instance = new Controlador();
        return instance;
    }

    //------------------Funcionalidades de un Usuario------------------

    /**
     * Establece el vehículo seleccionado para el usuario autenticado.
     * 
     * @param vehiculo Vehículo a seleccionar
     */
    public void setVehiculoSeleccionado(Vehiculo vehiculo) {
        if (clienteSeleccionado instanceof Usuario)
            ((Usuario) clienteSeleccionado).setVehiculoSeleccionado(vehiculo);
    }

    /**
     *
     * @return
     */
    public Vehiculo getVehiculoSeleccionado() {
        if (clienteSeleccionado instanceof Usuario)
            return ((Usuario) clienteSeleccionado).getVehiculoSeleccionado();
        return null;
    }

    /**
     * Elimina un vehículo del usuario autenticado.
     * 
     * @param vehiculo Vehículo a eliminar
     */
    public void eliminarVehiculo(Vehiculo vehiculo) {
        if (clienteSeleccionado instanceof Usuario) {
            ((Usuario) clienteSeleccionado).eliminarVehiculo(vehiculo);
            adminClientes.guardarDatosModificados();
        }
    }

    /**
     * Guarda los cambios realizados en los vehículos del usuario.
     */
    public void modificarVehiculo() {
        adminClientes.guardarDatosModificados();
    }

    /**
     * Guarda un nuevo vehículo para el usuario autenticado.
     * 
     * @param vehiculo Vehículo a guardar
     */
    public void guardarVehiculo(Vehiculo vehiculo) {
        if (clienteSeleccionado instanceof Usuario) {
            ((Usuario) clienteSeleccionado).guardarVehiculo(vehiculo);
            adminClientes.guardarDatosModificados();
        }
    }

    /**
     * Calcula la distancia total de una ruta.
     * 
     * @param ruta Lista de conexiones de la ruta
     * @return distancia total en kilómetros
     */
    public double getDistanciaRuta(ArrayList<Conexion> ruta) {
        double sum = 0;
        for (Conexion conexion : ruta) {
            sum += conexion.getDistancia();
        }
        return sum;
    }

    /**
     * Calcula el tiempo total en minutos de una ruta.
     * 
     * @param ruta Lista de conexiones de la ruta
     * @return tiempo total en minutos
     */
    public int getMinutosRuta(ArrayList<Conexion> ruta) {
        int sum = 0;
        for (Conexion conexion : ruta) {
            sum += conexion.getMinutos();
        }
        return sum;
    }

    /**
     * Calcula el consumo total de una ruta.
     * 
     * @param ruta Lista de conexiones de la ruta
     * @return consumo total
     */
    public int getConsumoRuta(ArrayList<Conexion> ruta) {
        int sum = 0;
        for (Conexion conexion : ruta) {
            sum += conexion.getConsumo();
        }
        return sum;
    }

    /**
     * Obtiene una cadena con los nombres de las ciudades visitadas en la ruta.
     * 
     * @param ruta Lista de conexiones
     * @return cadena con nombres de ciudades
     */
    public String getCiudadesVisitadas(ArrayList<Conexion> ruta) {
        String sum = "";
        for (Conexion conexion : ruta) {
            sum += conexion.getCiudad().getNombre() + ", ";
        }
        return sum;
    }

    //----------------Funcionalidades de un Admin----------------------------

    /**
     *
     * @return
     */

    public List<Ciudad> getCiudades() {
        return adminCiudades.getCiudades();
    }

    /**
     *
     * @return
     */
    public Ciudad getCiudadSeleccionada() {
        return adminCiudades.getCiudadSeleccionada();
    }

    /**
     *
     * @param ciudadSeleccionada
     */
    public void setCiudadSeleccionada(Ciudad ciudadSeleccionada) {
        adminCiudades.setCiudadSeleccionada(ciudadSeleccionada);
    }

    /**
     * Guarda una nueva ciudad con sus coordenadas.
     * 
     * @param nombre Nombre de la ciudad
     * @param longitudX Coordenada X
     * @param latitudY Coordenada Y
     * @return true si se guardó exitosamente
     */
    public boolean guardarCiudad(String nombre, double longitudX, double latitudY) {
        return adminCiudades.guardarNuevaCiudad(nombre, longitudX, latitudY);
    }

    /**
     * Modifica los datos de la ciudad actualmente seleccionada.
     * 
     * @param nombre Nuevo nombre
     * @param longitudX Nueva coordenada X
     * @param latitudY Nueva coordenada Y
     * @return true si se guardaron los cambios
     */
    public boolean modificarCiudadSeleccionada(String nombre, double longitudX, double latitudY) {
        Ciudad city = adminCiudades.getCiudadSeleccionada();
        city.setNombre(nombre);
        city.setLongitudX(longitudX);
        city.setLatitudY(latitudY);
        return adminCiudades.guardarDatosModificados();
    }

    /**
     * Elimina la ciudad actualmente seleccionada.
     * 
     * @return false siempre (¿incompleto?)
     */
    public boolean eliminarCiudad() {
        if (getCiudadSeleccionada() != null) {
            adminCiudades.eliminar(getCiudadSeleccionada());
        }
        return false;
    }

    /**
     * Guarda una estación en la ciudad seleccionada.
     * 
     * @param nombre Nombre de la estación
     * @param tipo Tipo (Combustibles o Carga)
     * @param tipos Tipos de suministros
     * @return true si se agregó exitosamente
     */
    public boolean guardarEstacion(String nombre, String tipo, Object[] tipos) {
        Ciudad city = getCiudadSeleccionada();
        Estacion temp;
        if (city.validarEstacion(nombre, tipo)) {
            if ("Combustibles".equalsIgnoreCase(tipo)) {
                temp = new EstacionCombustible(nombre);
            } else {
                temp = new EstacionCarga(nombre);
            }
            city.agregarEstacion(temp);
            adminCiudades.guardarDatosModificados();
            return true;
        }
        return false;
    }

    /**
     * Modifica una estación existente.
     * 
     * @param estacionSeleccionada Estación a modificar
     * @param nombre Nuevo nombre
     * @param tipos Nuevos tipos de suministros
     * @return true si se modificó
     */
    public boolean modificarEstacion(Estacion estacionSeleccionada, String nombre, Object[] tipos) {
        Ciudad city = getCiudadSeleccionada();
        if (estacionSeleccionada instanceof EstacionCombustible) {
            EstacionCombustible temp = (EstacionCombustible) estacionSeleccionada;
            temp.setNombre(nombre);
            temp.setSuministros(tipos);
        } else {
            EstacionCarga temp = (EstacionCarga) estacionSeleccionada;
            temp.setNombre(nombre);
            temp.setSuministros(tipos);
        }
        adminCiudades.guardarDatosModificados();
        return true;
    }

    /**
     * Elimina una estación.
     * 
     * @param estacionSeleccionada Estación a eliminar
     * @return true si se eliminó correctamente
     */
    public boolean eliminarEstacion(Estacion estacionSeleccionada) {
        Ciudad city = getCiudadSeleccionada();
        return city.removerEstacion(estacionSeleccionada.getNombre(), estacionSeleccionada.getTipoEstacion());
    }

    /**
     * Agrega un nuevo tipo (Combustible o Cargador).
     * 
     * @param nombre Nombre del tipo
     * @param tipo Tipo (Combustible/Cargador)
     * @return true si se agregó
     */
    public boolean agregarTipo(String nombre, String tipo) {
        if (tipo.equalsIgnoreCase("Combustible"))
            return agregarCombustible(nombre);
        return agregarCargador(nombre);
    }

    private boolean agregarCombustible(String nombre) {
        return adminTipos.guardarNuevoCombustible(nombre);
    }

    private boolean agregarCargador(String nombre) {
        return adminTipos.guardarNuevoCargador(nombre);
    }

    /**
     * Modifica un tipo existente.
     * 
     * @param obj Tipo a modificar
     * @param nombre Nuevo nombre
     * @return true si se modificó
     */
    public boolean modificarTipo(Tipos obj, String nombre) {
        if (obj instanceof Combustible)
            return modificarCombustible((Combustible) obj, nombre);
        return modificarCargador((Cargador) obj, nombre);
    }

    private boolean modificarCombustible(Combustible obj, String nombre) {
        if (adminTipos.validarCombustible(nombre)) {
            obj.setNombre(nombre);
            return adminTipos.guardarDatosModificadosCombustibles();
        }
        return false;
    }

    private boolean modificarCargador(Cargador obj, String nombre) {
        if (adminTipos.validarCargador(nombre)) {
            obj.setNombre(nombre);
            return adminTipos.guardarDatosModificadosCargadores();
        }
        return false;
    }

    /**
     * Elimina un tipo existente.
     * 
     * @param obj Tipo a eliminar
     * @return true si se eliminó correctamente
     */
    public boolean eliminarTipos(Tipos obj) {
        if (obj instanceof Combustible)
            return adminTipos.eliminarCombustible((Combustible) obj);
        return adminTipos.eliminarCargador((Cargador) obj);
    }

    /**
     * Guarda una nueva conexión para la ciudad seleccionada.
     * 
     * @param destino Ciudad destino
     * @param distancia Distancia
     * @param minutos Tiempo en minutos
     * @param consumo Consumo estimado
     * @return true si se guardó
     */
    public boolean guardarConexion(Ciudad destino, double distancia, int minutos, int consumo) {
        return adminCiudades.guardarConexion(destino, distancia, minutos, consumo);
    }

    /**
     * Modifica una conexión existente.
     * 
     * @param conexion Conexión a modificar
     * @param distancia Nueva distancia
     * @param minutos Nuevos minutos
     * @param consumo Nuevo consumo
     * @return true si se modificó
     */
    public boolean modificarConexion(Conexion conexion, double distancia, int minutos, int consumo) {
        conexion.setDistancia(distancia);
        conexion.setMinutos(minutos);
        conexion.setConsumo(consumo);
        adminCiudades.guardarDatosModificados();
        return true;
    }

    /**
     * Elimina una conexión de la ciudad seleccionada.
     * 
     * @param conexion Conexión a eliminar
     * @return true si se eliminó
     */
    public boolean eliminarConexion(Conexion conexion) {
        boolean res = getCiudadSeleccionada().eliminarConexion(conexion);
        adminCiudades.guardarDatosModificados();
        return res;
    }

    //------------------Generales-------------------------------------

    /**
     * Valida credenciales de acceso.
     * 
     * @param user Nombre de usuario
     * @param contrasenna Contraseña
     * @return Cliente si las credenciales son válidas, null en caso contrario
     */
    public Cliente validarCredenciales(String user, String contrasenna) {
        return adminClientes.obtenerCliente(user, contrasenna);
    }

    /**
     * Ingresa al sistema con el cliente especificado.
     * 
     * @param user Cliente autenticado
     * @return JFrame de la interfaz correspondiente
     */
    public JFrame ingresar(Cliente user) {
        if (user != null) {
            clienteSeleccionado = user;
            return user.ingresar();
        }
        return null;
    }

    /**
     * Guarda un nuevo usuario.
     * 
     * @param nombre Nombre de usuario
     * @param contrasenna Contraseña
     * @return true si se guardó exitosamente
     */
    public boolean guardarUsuario(String nombre, String contrasenna) {
        return adminClientes.guardarNuevoUsuario(nombre, contrasenna);
    }

    /**
     * Guarda un nuevo administrador.
     * 
     * @param nombre Nombre del admin
     * @param contrasenna Contraseña
     * @return true si se guardó exitosamente
     */
    public boolean guardarAdmin(String nombre, String contrasenna) {
        return adminClientes.guardarNuevoAdmin(nombre, contrasenna);
    }

    /**
     *
     * @return
     */
    public Cliente getClienteSeleccionado() {
        return clienteSeleccionado;
    }

    /**
     *
     * @return
     */
    public ArrayList<Cargador> getCargadores() {
        return adminTipos.getCargadores();
    }

    /**
     *
     * @return
     */
    public ArrayList<Combustible> getCombustibles() {
        return adminTipos.getCombustibles();
    }

    /**
     *
     * @param combustible
     * @return
     */
    public Combustible getCombustibleDeString(String combustible) {
        return adminTipos.getCombustibleDeString(combustible);
    }
}