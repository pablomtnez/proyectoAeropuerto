package es.deusto.spq.client.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.*;
import javax.swing.*;

import es.deusto.spq.client.ResourceClient;
import es.deusto.spq.client.domain.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MainWindow extends JFrame {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = LogManager.getLogger(MainWindow.class);

    // Lista de vuelos que se muestra en la ventana
    private List<Flight> flights = new ArrayList<>();
    // JTable de vuelos
    JTable jTableFlights = new JTable();
    // JLabel para mensajes de información
    private JLabel jLabelInfo = new JLabel("Selecciona un aeropuerto origen");
    // JComboBox para Origen y Destino
    JComboBox<String> jComboOrigin = new JComboBox<>();
    JComboBox<String> jComboDestination = new JComboBox<>();

    public MainWindow() {
        // Cargar todos los datos usando ResourceClient
        Map<String, Object> allData = null;
        try {
            allData = ResourceClient.getAllData();
        } catch (Exception e) {
            logger.error("Error al obtener datos del servidor", e);
            JOptionPane.showMessageDialog(this, "Error al obtener datos del servidor", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (allData == null || allData.isEmpty()) {
            logger.warn("No se recibieron datos del servidor");
            JOptionPane.showMessageDialog(this, "No se pudieron cargar los datos del servidor", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        List<Airport> airports = (List<Airport>) allData.get("airports");
        flights = (List<Flight>) allData.get("flights");

        logger.info("Datos recibidos: {} aeropuertos, {} vuelos", airports.size(), flights.size());

        // Configurar JComboBox de origen
        jComboOrigin.setPrototypeDisplayValue("Seleccione el aeropuerto origen");
        jComboOrigin.addActionListener((e) -> {
            Object fromItem = ((JComboBox<?>) e.getSource()).getSelectedItem();
            flights = new ArrayList<>();

            if (fromItem != null && !fromItem.toString().isEmpty()) {
                String origin = fromItem.toString().substring(0, fromItem.toString().indexOf(" - "));
                if (!origin.isEmpty()) {
                    Set<Airport> destinations = new HashSet<>();
                    for (Flight flight : flights) {
                        if (flight.getFrom().getIataCode().equals(origin)) {
                            destinations.add(flight.getTo());
                        }
                    }
                    updateDestinations(new ArrayList<>(destinations));
                } else {
                    jComboDestination.removeAllItems();
                }
            }
            updateFlights();
            jLabelInfo.setText("Selecciona un aeropuerto origen");
        });
        updateOrigins(airports);

        // Configurar JComboBox de destino
        jComboDestination.setPrototypeDisplayValue("Seleccione el aeropuerto destino");
        jComboDestination.addActionListener((e) -> {
            Object toItem = ((JComboBox<?>) e.getSource()).getSelectedItem();
            flights = new ArrayList<>();

            if (toItem != null && !toItem.toString().isEmpty()) {
                String destination = toItem.toString().substring(0, toItem.toString().indexOf(" - "));
                if (!destination.isEmpty() && jComboOrigin.getSelectedIndex() > 0) {
                    String origin = jComboOrigin.getSelectedItem().toString().substring(0, jComboOrigin.getSelectedItem().toString().indexOf(" - "));
                    for (Flight flight : flights) {
                        if (flight.getFrom().getIataCode().equals(origin) && flight.getTo().getIataCode().equals(destination)) {
                            flights.add(flight);
                        }
                    }
                }
            }
            updateFlights();
        });

        // Configuración de la ventana principal
        jTableFlights.setRowHeight(30);
        jTableFlights.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        ((DefaultTableCellRenderer) jTableFlights.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);

        jLabelInfo.setHorizontalAlignment(JLabel.RIGHT);

        JPanel pOrigin = new JPanel();
        pOrigin.add(new JLabel("Origen: "));
        pOrigin.add(this.jComboOrigin);

        JPanel pDestination = new JPanel();
        pDestination.add(new JLabel("Destino: "));
        pDestination.add(jComboDestination);

        JPanel pSearch = new JPanel();
        pSearch.setBorder(new TitledBorder("Búsqueda de vuelos"));
        pSearch.setLayout(new GridLayout(2, 1));
        pSearch.add(pOrigin);
        pSearch.add(pDestination);

        add(pSearch, BorderLayout.NORTH);
        add(new JScrollPane(jTableFlights), BorderLayout.CENTER);
        add(jLabelInfo, BorderLayout.SOUTH);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Easy Booking");
        setSize(1200, 600);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void updateOrigins(List<Airport> airports) {
        if (airports == null || airports.isEmpty()) {
            logger.warn("No se encontraron aeropuertos");
            return;
        }

        this.jComboOrigin.removeAllItems();
        this.jComboOrigin.addItem("");
        Collections.sort(airports);
        for (Airport airport : airports) {
            jComboOrigin.addItem(String.format("%s - %s (%s)", airport.getIataCode(), airport.getName(), airport.getCountry().getName()));
        }
    }

    private void updateDestinations(List<Airport> airports) {
        if (airports == null || airports.isEmpty()) {
            logger.warn("No se encontraron destinos");
            return;
        }

        this.jComboDestination.removeAllItems();
        this.jComboDestination.addItem("");
        Collections.sort(airports);
        for (Airport airport : airports) {
            jComboDestination.addItem(String.format("%s - %s (%s)", airport.getIataCode(), airport.getName(), airport.getCountry().getName()));
        }
    }

    public void updateFlights() {
        if (flights == null || flights.isEmpty()) {
            logger.warn("No se encontraron vuelos");
            jLabelInfo.setText("No se encontraron vuelos");
            return;
        }

        Comparator<Flight> priceComparator = Comparator.comparingDouble(Flight::getPrice);
        Collections.sort(flights, priceComparator);

        jTableFlights.setModel(new FlightsTableModel(flights));

        FlightRenderer defaultRenderer = new FlightRenderer();

        for (int i = 0; i < jTableFlights.getColumnModel().getColumnCount() - 1; i++) {
            jTableFlights.getColumnModel().getColumn(i).setCellRenderer(defaultRenderer);
        }

        int lastColumn = jTableFlights.getColumnModel().getColumnCount() - 1;

        // Define el render y el editor para la última columna de la tabla
        jTableFlights.getColumnModel().getColumn(lastColumn).setCellRenderer(new BookRendererEditor(this));
        jTableFlights.getColumnModel().getColumn(lastColumn).setCellEditor(new BookRendererEditor(this));

        jLabelInfo.setText(String.format("%d vuelos", flights.size()));
    }
}
