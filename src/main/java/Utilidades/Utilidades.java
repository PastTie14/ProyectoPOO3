/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utilidades;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.util.ArrayList;

/**
 *
 * @author Ian
 */
public class Utilidades {
    
    /**
     * Guarda un nuevo objeto en el archivo especificado.
     * Si el archivo ya contiene objetos, los lee, agrega el nuevo
     * y vuelve a escribir todos los objetos para mantener el archivo consistente.
     * 
     * @param objeto el objeto a guardar (no puede ser null)
     * @param rutaArchivo ruta completa del archivo donde se guardará el objeto
     * @return true si el objeto se guardó correctamente, false en caso contrario
     */
    public static boolean guardarNuevoObjeto(Object objeto, String rutaArchivo) {
        if (objeto == null) {
            System.err.println("Error: No se puede guardar un objeto null");
            return false;
        }
        
        try {
            File archivo = new File(rutaArchivo);
            File directorio = archivo.getParentFile();
            if (directorio != null && !directorio.exists()) {
                directorio.mkdirs();
            }
            
            ArrayList<Object> objetosExistentes = new ArrayList<>();
            if (archivo.exists() && archivo.length() > 0) {
                ArrayList<Object> objetosLeidos = leerObjetos(rutaArchivo);
                if (objetosLeidos != null) {
                    objetosExistentes = objetosLeidos;
                }
            }
            objetosExistentes.add(objeto);
            return escribirTodosLosObjetos(objetosExistentes.toArray(), rutaArchivo);
            
        } catch (Exception e) {
            System.err.println("Error al guardar el objeto: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Método auxiliar que escribe todos los objetos recibidos en el archivo especificado.
     * Sobrescribe el contenido previo del archivo.
     * 
     * @param objetos arreglo de objetos a escribir en el archivo
     * @param rutaArchivo ruta completa del archivo
     * @return true si la escritura fue exitosa, false si ocurrió algún error
     */
    private static boolean escribirTodosLosObjetos(Object[] objetos, String rutaArchivo) {
        if (objetos == null || objetos.length == 0) {
            return false;
        }
        
        try (FileOutputStream fos = new FileOutputStream(rutaArchivo);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            
            for (Object objeto : objetos) {
                if (objeto != null) {
                    oos.writeObject(objeto);
                }
            }
            
            oos.flush();
            System.out.println("Objetos guardados exitosamente en " + rutaArchivo);
            return true;
            
        } catch (IOException e) {
            System.err.println("Error al escribir objetos: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Reemplaza todos los objetos almacenados en el archivo con el arreglo proporcionado.
     * Crea el directorio si no existe.
     * 
     * @param objetos arreglo de objetos a escribir (no puede ser null)
     * @param rutaArchivo ruta completa del archivo
     * @return true si la operación fue exitosa, false en caso contrario
     */
    public static boolean modificarObjeto(Object[] objetos, String rutaArchivo) {
        if (objetos == null) {
            System.err.println("Error: Array de objetos es null");
            return false;
        }
        
        // Crear directorio si no existe
        File archivo = new File(rutaArchivo);
        File directorio = archivo.getParentFile();
        if (directorio != null && !directorio.exists()) {
            directorio.mkdirs();
        }
        
        return escribirTodosLosObjetos(objetos, rutaArchivo);
    }
    
    /**
     * Lee todos los objetos almacenados en el archivo.
     * Realiza comprobaciones para evitar errores por archivos vacíos o corruptos.
     * 
     * @param rutaArchivo ruta completa del archivo de donde se leerán los objetos
     * @return una lista con los objetos leídos; si no hay objetos o ocurre error, devuelve lista vacía
     */
    public static ArrayList<Object> leerObjetos(String rutaArchivo) {
        ArrayList<Object> objetos = new ArrayList<>();
        
        File archivo = new File(rutaArchivo);
        
        // Verificaciones de seguridad
        if (!archivo.exists()) {
            System.out.println("El archivo no existe: " + rutaArchivo);
            return objetos; // Retorna lista vacía en lugar de null
        }
        
        if (archivo.length() == 0) {
            System.out.println("El archivo está vacío: " + rutaArchivo);
            return objetos; // Retorna lista vacía en lugar de null
        }
        
        // Verificar si el archivo tiene un header válido de ObjectInputStream
        if (!verificarHeaderValido(archivo)) {
            System.err.println("El archivo no tiene un formato válido de ObjectInputStream: " + rutaArchivo);
            return objetos;
        }
        
        try (FileInputStream fis = new FileInputStream(archivo);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            
            while (true) {
                try {
                    Object obj = ois.readObject();
                    if (obj != null) {
                        objetos.add(obj);
                    }
                } catch (EOFException eof) {
                    // Fin del archivo alcanzado normalmente
                    break;
                } catch (ClassNotFoundException e) {
                    System.err.println("Clase no encontrada al deserializar: " + e.getMessage());
                    // Continúa intentando leer otros objetos
                }
            }
            
            System.out.println("Se leyeron " + objetos.size() + " objetos del archivo");
            
        } catch (StreamCorruptedException e) {
            System.err.println("Archivo corrupto - Header inválido: " + e.getMessage());
            return new ArrayList<>(); // Retorna lista vacía
        } catch (IOException e) {
            System.err.println("Error de E/O al leer objetos: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>(); // Retorna lista vacía
        }
        
        return objetos;
    }
    
    /**
     * Verifica que el archivo tenga el encabezado válido esperado de un ObjectInputStream.
     * Este método evita intentar leer archivos corruptos o con formato incorrecto.
     * 
     * @param archivo archivo a verificar
     * @return true si el encabezado es válido, false en caso contrario
     */
    private static boolean verificarHeaderValido(File archivo) {
        try (FileInputStream fis = new FileInputStream(archivo)) {
            byte[] header = new byte[4];
            int bytesLeidos = fis.read(header);
            
            if (bytesLeidos < 4) {
                return false;
            }
            
            // Header válido de ObjectInputStream: AC ED 00 05
            return header[0] == (byte) 0xAC && 
                   header[1] == (byte) 0xED && 
                   header[2] == (byte) 0x00 && 
                   header[3] == (byte) 0x05;
                   
        } catch (IOException e) {
            return false;
        }
    }
    
    /**
     * Obtiene la ruta base (directorio actual) del proyecto en ejecución.
     * 
     * @return la ruta base como String
     */
    public static String obtenerRutaBase() {
        return System.getProperty("user.dir");
    }
    
    /**
     * Limpia un archivo específico, eliminándolo y creando uno nuevo vacío.
     * Útil para corregir archivos corruptos.
     * 
     * @param rutaArchivo ruta completa del archivo a limpiar
     * @return true si el archivo fue limpiado o creado correctamente, false en caso de error
     */
    public static boolean limpiarArchivo(String rutaArchivo) {
        try {
            File archivo = new File(rutaArchivo);
            if (archivo.exists()) {
                archivo.delete();
            }
            return archivo.createNewFile();
        } catch (IOException e) {
            System.err.println("Error al limpiar archivo: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Crea una copia de seguridad del archivo especificado con extensión ".backup".
     * 
     * @param rutaArchivo ruta completa del archivo original
     * @return true si el backup se creó con éxito, false si ocurrió algún error o el archivo original no existe
     */
    public static boolean hacerBackup(String rutaArchivo) {
        try {
            File original = new File(rutaArchivo);
            if (!original.exists()) {
                return false;
            }
            
            File backup = new File(rutaArchivo + ".backup");
            
            try (FileInputStream fis = new FileInputStream(original);
                 FileOutputStream fos = new FileOutputStream(backup)) {
                
                byte[] buffer = new byte[1024];
                int bytesLeidos;
                while ((bytesLeidos = fis.read(buffer)) != -1) {
                    fos.write(buffer, 0, bytesLeidos);
                }
            }
            
            System.out.println("Backup creado: " + backup.getAbsolutePath());
            return true;
            
        } catch (IOException e) {
            System.err.println("Error al crear backup: " + e.getMessage());
            return false;
        }
    }
}
