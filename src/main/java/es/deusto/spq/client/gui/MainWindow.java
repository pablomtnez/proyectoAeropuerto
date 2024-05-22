package es.deusto.spq.client.gui;

import es.deusto.spq.client.ResourceClient;
import es.deusto.spq.client.domain.Airport;
import es.deusto.spq.client.domain.Flight;
import es.deusto.spq.client.domain.Usuario;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MainWindow extends JFrame {
    private static final long serialVersionUID = 1L;
    protected static final Logger logger = LogManager.getLogger();

    private ResourceClient resourceClient;
    private List<Flight> flights;
    private JTable jTableFlights;
    private JLabel jLabelInfo;
    private JComboBox<String> jComboOrigin;
    private JComboBox<String> jComboDestination;

    public static Usuario logged;

    public MainWindow(ResourceClient resourceClient) {
        this.resourceClient = resourceClient;
        this.flights = new ArrayList<>(); // Initialize flights list

        initialize();
        loadOrigins();
        loadFlights();
    }

    private void initialize() {
        setTitle("Easy Booking");
        setSize(1200, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        jComboOrigin = new JComboBox<>();
        jComboDestination = new JComboBox<>();
        jLabelInfo = new JLabel("Selecciona un aeropuerto origen");
        jTableFlights = new JTable();

        jComboOrigin.setPrototypeDisplayValue("Seleccione el nombre del aeropuerto origen");
        jComboOrigin.addActionListener((e) -> onOriginSelected());

        jComboDestination.setPrototypeDisplayValue("Seleccione el nombre del aeropuerto destino");
        jComboDestination.addActionListener((e) -> onDestinationSelected());

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

        setLocationRelativeTo(null);
    }

    private void loadOrigins() {
        List<Airport> origins = resourceClient.getAirports();
        logger.debug("Loaded origins: {}", origins);
        updateOrigins(origins);
    }

    private void updateOrigins(List<Airport> airports) {
        jComboOrigin.removeAllItems();
        jComboOrigin.addItem("");
        Collections.sort(airports, Comparator.comparing(Airport::getName));
        for (Airport a : airports) {
            jComboOrigin.addItem(String.format("%s - %s (%s)", a.getCode(), a.getName(), a.getCountry().getName()));
        }
        logger.debug("Updated origin combo box with: {}", airports);
    }

    private void updateDestinations(List<Airport> airports) {
        jComboDestination.removeAllItems();
        jComboDestination.addItem("");
        Collections.sort(airports, Comparator.comparing(Airport::getName));
        for (Airport a : airports) {
            jComboDestination.addItem(String.format("%s - %s (%s)", a.getCode(), a.getName(), a.getCountry().getName()));
        }
        logger.debug("Updated destination combo box with: {}", airports);
    }

    private void loadFlights() {
        flights = resourceClient.getFlights();
        updateFlights();
    }

    public void updateFlights() {
        Comparator<Flight> priceComparator = Comparator.comparing(Flight::getPrice);
        Collections.sort(flights, priceComparator);

        jTableFlights.setModel(new FlightsTableModel(flights));

        FlightRenderer defaultRenderer = new FlightRenderer(flights);

        for (int i = 0; i < jTableFlights.getColumnModel().getColumnCount() - 1; i++) {
            jTableFlights.getColumnModel().getColumn(i).setCellRenderer(defaultRenderer);
        }

        int lastColumn = jTableFlights.getColumnModel().getColumnCount() - 1;
        jTableFlights.getColumnModel().getColumn(lastColumn).setCellRenderer(new BookRendererEditor(this));
        jTableFlights.getColumnModel().getColumn(lastColumn).setCellEditor(new BookRendererEditor(this));

        jLabelInfo.setText(String.format("%d vuelos", flights.size()));
    }

    private void onOriginSelected() {
        Object fromItem = jComboOrigin.getSelectedItem();
        flights.clear();

        if (fromItem != null && !fromItem.toString().isEmpty()) {
            String origin = fromItem.toString().split(" - ")[0];
            List<Airport> destinations = resourceClient.getAirports(); // Fetch destinations
            updateDestinations(destinations);

            // Only fetch flights if destination is also selected
            if (jComboDestination.getSelectedIndex() > 0) {
                String destination = jComboDestination.getSelectedItem().toString().split(" - ")[0];
                flights = resourceClient.getFlightsByOriginAndDestination(origin, destination);
                updateFlights();
            }
        } else {
            jComboDestination.removeAllItems();
        }
    }

    private void onDestinationSelected() {
        Object toItem = jComboDestination.getSelectedItem();
        flights.clear();

        if (toItem != null && !toItem.toString().isEmpty()) {
            String destination = toItem.toString().split(" - ")[0];

            if (!destination.isEmpty() && jComboOrigin.getSelectedIndex() > 0) {
                String origin = jComboOrigin.getSelectedItem().toString().split(" - ")[0];
                flights = resourceClient.getFlightsByOriginAndDestination(origin, destination);
                updateFlights();
            }
        }
    }
}