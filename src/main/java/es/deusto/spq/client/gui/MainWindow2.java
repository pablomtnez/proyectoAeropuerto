package es.deusto.spq.client.gui;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTable;

import es.deusto.spq.client.ResourceClient;
import es.deusto.spq.client.domain.Flight;

public class MainWindow2 {

    private static final long serialVersionUID = 1L;

    private ResourceClient resourceClient;
    private List<Flight> flights = new ArrayList<>();
    private JTable jTableFlights = new JTable();
    private JLabel jLabelInfo = new JLabel("Selecciona un aeropuerto origen");
    private JComboBox<String> jComboOrigin = new JComboBox<>();
    private JComboBox<String> jComboDestination = new JComboBox<>();
    private JButton jBtnRecursiveSearch = new JButton("Búsqueda Recursiva (max. 2 escalas)");
    
    
}
