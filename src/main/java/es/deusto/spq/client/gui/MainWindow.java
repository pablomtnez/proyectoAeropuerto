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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MainWindow extends JFrame {
    private static final long serialVersionUID = 1L;

    private ResourceClient resourceClient;
    private List<Flight> flights;
    private JTable jTableFlights;
    private JLabel jLabelInfo;
    private JComboBox<String> jComboOrigin;
    private JComboBox<String> jComboDestination;

    public static Usuario logged;

    public MainWindow(ResourceClient resourceClient) {
        this.resourceClient = resourceClient;
        this.flights = resourceClient.getFlights();
        
        initialize();
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

        // Load initial data
        loadOrigins();
        loadFlights();
    }

    private void loadOrigins() {
        Set<Airport> origins = new HashSet<>(resourceClient.getAirports());
        updateOrigins(new ArrayList<>(origins));
    }

    private void updateOrigins(List<Airport> airports) {
        this.jComboOrigin.removeAllItems();
        this.jComboOrigin.addItem("");
        Collections.sort(airports);
        airports.forEach(a -> jComboOrigin.addItem(String.format("%s - %s (%s)",
                a.getCode(), a.getName(), a.getCountry().getName())));
    }

    private void updateDestinations(List<Airport> airports) {
        this.jComboDestination.removeAllItems();
        this.jComboDestination.addItem("");
        Collections.sort(airports);
        airports.forEach(a -> jComboDestination.addItem(String.format("%s - %s (%s)",
                a.getCode(), a.getName(), a.getCountry().getName())));
    }

    private void loadFlights() {
        flights = resourceClient.getFlights();
        updateFlights();
    }

    public void updateFlights() {
        Comparator<Flight> priceComparator = Comparator.comparing(Flight::getPrice);
        Collections.sort(flights, priceComparator);

        jTableFlights.setModel(new FlightsTableModel(flights));

        FlightRenderer defaultRenderer = new FlightRenderer();

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
            Set<Airport> destinations = new HashSet<>(resourceClient.getAirports());
            updateDestinations(new ArrayList<>(destinations));
        } else {
            jComboDestination.removeAllItems();
        }

        updateFlights();
        jLabelInfo.setText("Selecciona un aeropuerto origen");
    }

    private void onDestinationSelected() {
        Object toItem = jComboDestination.getSelectedItem();
        flights.clear();

        if (toItem != null && !toItem.toString().isEmpty()) {
            String destination = toItem.toString().split(" - ")[0];

            if (!destination.isEmpty() && jComboOrigin.getSelectedIndex() > 0) {
                String origin = jComboOrigin.getSelectedItem().toString().split(" - ")[0];
                flights = resourceClient.getFlights(); // This should be filtered by origin and destination
            }
        }

        updateFlights();
    }
}
