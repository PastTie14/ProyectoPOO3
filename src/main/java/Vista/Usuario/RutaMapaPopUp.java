/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vista.Usuario;
/**
 *
 * @author Ian
 */


import Modelos.Ciudades.Ciudad;
import Modelos.Ciudades.Conexion;
import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.OSMTileFactoryInfo;
import org.jxmapviewer.input.CenterMapListener;
import org.jxmapviewer.input.PanKeyListener;
import org.jxmapviewer.input.PanMouseInputListener;
import org.jxmapviewer.input.ZoomMouseWheelListenerCursor;
import org.jxmapviewer.painter.CompoundPainter;
import org.jxmapviewer.painter.Painter;
import org.jxmapviewer.viewer.DefaultTileFactory;
import org.jxmapviewer.viewer.DefaultWaypoint;
import org.jxmapviewer.viewer.GeoPosition;
import org.jxmapviewer.viewer.TileFactoryInfo;
import org.jxmapviewer.viewer.Waypoint;
import org.jxmapviewer.viewer.WaypointPainter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * PopUp que muestra una ruta en el mapa de Costa Rica usando JXMapViewer
 * @author Tu Nombre
 */
public class RutaMapaPopUp extends JDialog {
    
    private JXMapViewer mapViewer;
    private Ciudad ciudadSalida;
    private ArrayList<Conexion> ruta;
    private JPanel infoPanel;
    private JLabel infoLabel;
    
    /**
     * Constructor del PopUp de ruta en mapa
     * @param parent Ventana padre
     * @param ciudadSalida Ciudad de salida
     * @param ruta Lista de conexiones que conforman la ruta
     */
    public RutaMapaPopUp(JFrame parent, Ciudad ciudadSalida, ArrayList<Conexion> ruta) {
        super(parent, "Ruta en Mapa - Costa Rica", true);
        this.ciudadSalida = ciudadSalida;
        this.ruta = ruta;
        
        initComponents();
        setupMapa();
        mostrarRuta();
        
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(900, 700);
        setLocationRelativeTo(parent);
        setResizable(true);
    }
    
    /**
     * Inicializa los componentes de la interfaz
     */
    private void initComponents() {
        setLayout(new BorderLayout());
        
        // Panel superior con información
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBorder(BorderFactory.createTitledBorder("Información de la Ruta"));
        topPanel.setPreferredSize(new Dimension(900, 100));
        
        infoLabel = new JLabel();
        infoLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        infoLabel.setVerticalAlignment(SwingConstants.TOP);
        
        JScrollPane scrollInfo = new JScrollPane(infoLabel);
        scrollInfo.setPreferredSize(new Dimension(880, 80));
        topPanel.add(scrollInfo, BorderLayout.CENTER);
        
        // Crear el mapa
        mapViewer = new JXMapViewer();
        
        // Panel inferior con botones
        JPanel bottomPanel = new JPanel(new FlowLayout());
        JButton btnCerrar = new JButton("Cerrar");
        JButton btnZoomRuta = new JButton("Zoom a Ruta");
        JButton btnZoomCostaRica = new JButton("Zoom Costa Rica");
        
        btnCerrar.addActionListener(e -> dispose());
        btnZoomRuta.addActionListener(e -> zoomARuta());
        btnZoomCostaRica.addActionListener(e -> zoomACostaRica());
        
        bottomPanel.add(btnZoomCostaRica);
        bottomPanel.add(btnZoomRuta);
        bottomPanel.add(btnCerrar);
        
        add(topPanel, BorderLayout.NORTH);
        add(mapViewer, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
        
        actualizarInfoRuta();
    }
    
    /**
     * Configura el mapa con las opciones básicas
     */
    private void setupMapa() {
        // Configurar el tile factory para OpenStreetMap
        TileFactoryInfo info = new OSMTileFactoryInfo();
        DefaultTileFactory tileFactory = new DefaultTileFactory(info);
        mapViewer.setTileFactory(tileFactory);
        
        // Configurar controles del mapa
        MouseAdapter mouseAdapter = new PanMouseInputListener(mapViewer);
        mapViewer.addMouseListener(mouseAdapter);
        mapViewer.addMouseMotionListener(mouseAdapter);
        mapViewer.addMouseWheelListener(new ZoomMouseWheelListenerCursor(mapViewer));
        mapViewer.addKeyListener(new PanKeyListener(mapViewer));
        
        // Centrar en Costa Rica inicialmente
        zoomACostaRica();
    }
    
    /**
     * Muestra la ruta en el mapa con waypoints y líneas
     */
    private void mostrarRuta() {
        if (ruta == null || ruta.isEmpty()) {
            return;
        }
        
        // Crear waypoints para todas las ciudades en la ruta
        Set<Waypoint> waypoints = new HashSet<>();
        
        // Agregar ciudad de salida
        GeoPosition posicionSalida = new GeoPosition(ciudadSalida.getLatitudY(), ciudadSalida.getLongitudX());
        waypoints.add(new DefaultWaypoint(posicionSalida));
        
        // Agregar ciudades destino de cada conexión
        for (Conexion conexion : ruta) {
            Ciudad ciudadDestino = conexion.getCiudad();
            GeoPosition posicion = new GeoPosition(ciudadDestino.getLatitudY(), ciudadDestino.getLongitudX());
            waypoints.add(new DefaultWaypoint(posicion));
        }
        
        // Crear painter para los waypoints
        WaypointPainter<Waypoint> waypointPainter = new WaypointPainter<Waypoint>();
        waypointPainter.setWaypoints(waypoints);
        waypointPainter.setRenderer(new WaypointRenderer());
        
        // Crear painter para las líneas de la ruta
        RoutePainter routePainter = new RoutePainter();
        
        // Combinar painters
        List<Painter<JXMapViewer>> painters = new ArrayList<>();
        painters.add(routePainter);
        painters.add(waypointPainter);
        
        CompoundPainter<JXMapViewer> painter = new CompoundPainter<>(painters);
        mapViewer.setOverlayPainter(painter);
    }
    
    /**
     * Actualiza la información de la ruta en el panel superior
     */
    private void actualizarInfoRuta() {
        if (ruta == null || ruta.isEmpty()) {
            infoLabel.setText("<html><b>No hay ruta disponible</b></html>");
            return;
        }
        
        StringBuilder info = new StringBuilder();
        info.append("<html>");
        info.append("<b>Ruta desde: </b>").append(ciudadSalida.getNombre()).append("<br>");
        info.append("<b>Ciudades en la ruta: </b>");
        
        double distanciaTotal = 0;
        int tiempoTotal = 0;
        int consumoTotal = 0;
        
        for (int i = 0; i < ruta.size(); i++) {
            Conexion conexion = ruta.get(i);
            if (i > 0) info.append(" → ");
            info.append(conexion.getCiudad().getNombre());
            
            distanciaTotal += conexion.getDistancia();
            tiempoTotal += conexion.getMinutos();
            consumoTotal += conexion.getConsumo();
        }
        
        info.append("<br><b>Distancia total: </b>").append(String.format("%.2f km", distanciaTotal));
        info.append("<br><b>Tiempo total: </b>").append(tiempoTotal).append(" minutos");
        info.append("<br><b>Consumo total: </b>").append(consumoTotal).append(" unidades");
        info.append("</html>");
        
        infoLabel.setText(info.toString());
    }
    
    /**
     * Hace zoom para mostrar toda la ruta
     */
    private void zoomARuta() {
        if (ruta == null || ruta.isEmpty()) {
            return;
        }
        
        // Calcular el área que abarca toda la ruta
        double minLat = ciudadSalida.getLatitudY();
        double maxLat = ciudadSalida.getLatitudY();
        double minLon = ciudadSalida.getLongitudX();
        double maxLon = ciudadSalida.getLongitudX();
        
        for (Conexion conexion : ruta) {
            double lat = conexion.getCiudad().getLatitudY();
            double lon = conexion.getCiudad().getLongitudX();
            
            minLat = Math.min(minLat, lat);
            maxLat = Math.max(maxLat, lat);
            minLon = Math.min(minLon, lon);
            maxLon = Math.max(maxLon, lon);
        }
        
        // Agregar un pequeño margen
        double marginLat = (maxLat - minLat) * 0.1;
        double marginLon = (maxLon - minLon) * 0.1;
        
        Set<GeoPosition> positions = new HashSet<>();
        positions.add(new GeoPosition(minLat - marginLat, minLon - marginLon));
        positions.add(new GeoPosition(maxLat + marginLat, maxLon + marginLon));
        
        mapViewer.zoomToBestFit(positions, 0.7);
    }
    
    /**
     * Centra el mapa en Costa Rica
     */
    private void zoomACostaRica() {
        // Coordenadas aproximadas de Costa Rica
        GeoPosition centroCostaRica = new GeoPosition(9.7489, -83.7534);
        mapViewer.setAddressLocation(centroCostaRica);
        mapViewer.setZoom(7);
    }
    
    /**
     * Renderer personalizado para los waypoints
     */
    private class WaypointRenderer implements org.jxmapviewer.viewer.WaypointRenderer<Waypoint> {
        @Override
        public void paintWaypoint(Graphics2D g, JXMapViewer map, Waypoint waypoint) {
            g = (Graphics2D) g.create();
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            Point2D point = map.getTileFactory().geoToPixel(waypoint.getPosition(), map.getZoom());
            int x = (int) point.getX();
            int y = (int) point.getY();
            
            // Determinar si es la ciudad de salida
            boolean esCiudadSalida = waypoint.getPosition().getLatitude() == ciudadSalida.getLatitudY() 
                                   && waypoint.getPosition().getLongitude() == ciudadSalida.getLongitudX();
            
            if (esCiudadSalida) {
                // Ciudad de salida - círculo verde más grande
                g.setColor(Color.GREEN);
                g.fillOval(x - 12, y - 12, 24, 24);
                g.setColor(Color.BLACK);
                g.setStroke(new BasicStroke(2));
                g.drawOval(x - 12, y - 12, 24, 24);
                
                // Texto "INICIO"
                g.setColor(Color.WHITE);
                g.setFont(new Font("Arial", Font.BOLD, 10));
                FontMetrics fm = g.getFontMetrics();
                String texto = "S";
                int textoX = x - fm.stringWidth(texto) / 2;
                int textoY = y + fm.getAscent() / 2 - 1;
                g.drawString(texto, textoX, textoY);
            } else {
                // Ciudades de destino - círculos rojos
                g.setColor(Color.RED);
                g.fillOval(x - 10, y - 10, 20, 20);
                g.setColor(Color.WHITE);
                g.setStroke(new BasicStroke(2));
                g.drawOval(x - 10, y - 10, 20, 20);
                
                // Número de orden en la ruta
                g.setColor(Color.WHITE);
                g.setFont(new Font("Arial", Font.BOLD, 12));
                FontMetrics fm = g.getFontMetrics();
                
                // Encontrar el número de orden de esta ciudad
                int orden = -1;
                for (int i = 0; i < ruta.size(); i++) {
                    Ciudad ciudadRuta = ruta.get(i).getCiudad();
                    if (ciudadRuta.getLatitudY() == waypoint.getPosition().getLatitude() 
                        && ciudadRuta.getLongitudX() == waypoint.getPosition().getLongitude()) {
                        orden = i + 1;
                        break;
                    }
                }
                
                if (orden > 0) {
                    String numero = String.valueOf(orden);
                    int textoX = x - fm.stringWidth(numero) / 2;
                    int textoY = y + fm.getAscent() / 2 - 1;
                    g.drawString(numero, textoX, textoY);
                }
            }
            
            g.dispose();
        }
    }
    
    /**
     * Painter para dibujar las líneas de la ruta
     */
    private class RoutePainter implements Painter<JXMapViewer> {
        @Override
        public void paint(Graphics2D g, JXMapViewer map, int w, int h) {
            if (ruta == null || ruta.isEmpty()) {
                return;
            }
            
            g = (Graphics2D) g.create();
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            // Configurar estilo de línea
            g.setColor(Color.BLUE);
            g.setStroke(new BasicStroke(4, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            
            // Punto de inicio (ciudad de salida)
            GeoPosition posicionActual = new GeoPosition(ciudadSalida.getLatitudY(), ciudadSalida.getLongitudX());
            Point2D puntoActual = map.getTileFactory().geoToPixel(posicionActual, map.getZoom());
            
            // Dibujar línea para cada conexión en secuencia
            for (Conexion conexion : ruta) {
                Ciudad ciudadDestino = conexion.getCiudad();
                GeoPosition posicionDestino = new GeoPosition(ciudadDestino.getLatitudY(), ciudadDestino.getLongitudX());
                Point2D puntoDestino = map.getTileFactory().geoToPixel(posicionDestino, map.getZoom());
                
                // Dibujar línea desde posición actual hasta destino
                g.drawLine(
                    (int) puntoActual.getX(), (int) puntoActual.getY(),
                    (int) puntoDestino.getX(), (int) puntoDestino.getY()
                );
                
                // Dibujar flecha direccional
                dibujarFlecha(g, puntoActual, puntoDestino);
                
                // Actualizar posición actual para la siguiente iteración
                puntoActual = puntoDestino;
                posicionActual = posicionDestino;
            }
            
            g.dispose();
        }
        
        /**
         * Dibuja una pequeña flecha para indicar la dirección de la ruta
         */
        private void dibujarFlecha(Graphics2D g, Point2D desde, Point2D hacia) {
            double dx = hacia.getX() - desde.getX();
            double dy = hacia.getY() - desde.getY();
            double distancia = Math.sqrt(dx * dx + dy * dy);
            
            if (distancia < 20) return; // No dibujar flecha si la distancia es muy pequeña
            
            // Normalizar el vector dirección
            dx /= distancia;
            dy /= distancia;
            
            // Punto donde colocar la flecha (75% del camino)
            double puntoFlechaX = desde.getX() + (hacia.getX() - desde.getX()) * 0.75;
            double puntoFlechaY = desde.getY() + (hacia.getY() - desde.getY()) * 0.75;
            
            // Tamaño de la flecha
            double tamañoFlecha = 12;
            
            // Calcular puntos de la flecha (triángulo)
            double x1 = puntoFlechaX - tamañoFlecha * dx - tamañoFlecha * 0.4 * dy;
            double y1 = puntoFlechaY - tamañoFlecha * dy + tamañoFlecha * 0.4 * dx;
            
            double x2 = puntoFlechaX - tamañoFlecha * dx + tamañoFlecha * 0.4 * dy;
            double y2 = puntoFlechaY - tamañoFlecha * dy - tamañoFlecha * 0.4 * dx;
            
            // Dibujar la flecha como triángulo relleno
            int[] xPoints = {(int) puntoFlechaX, (int) x1, (int) x2};
            int[] yPoints = {(int) puntoFlechaY, (int) y1, (int) y2};
            
            g.setColor(Color.RED);
            g.fillPolygon(xPoints, yPoints, 3);
            g.setColor(Color.BLACK);
            g.drawPolygon(xPoints, yPoints, 3);
        }
    }
}
