package es.deusto.spq.client.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.tree.DefaultMutableTreeNode;

import es.deusto.spq.client.domain.AirAlliance;
import es.deusto.spq.client.domain.Airport;
import es.deusto.spq.client.domain.Flight;
import es.deusto.spq.client.domain.Usuario;
import es.deusto.spq.client.gui.FlightRenderer;

public class VentanaPrincipal extends JFrame{
    private static final long serialVersionUID = 1L;
	
	//Servicios de aerolíneas
	//private List<AirAlliance> airAllianceServices = new ArrayList<>();
	
	//Lista de vuelos que se está visualizando en la ventana
	private List<Flight> flights = new ArrayList<>();
	
	//JTable de vuelos
	private JTable jTableFlights = new JTable();	
	//JLabel para mensajes de información
	private JLabel jLabelInfo = new JLabel("Selecciona un aeropuerto origen");
	//JCombos de Origen y Destino
	private JComboBox<String> jComboOrigin = new JComboBox<>();
	private JComboBox<String> jComboDestination = new JComboBox<>();

	public static Usuario logged;

    public VentanaPrincipal(){
        //airAllianceServices.add(new ONE_WORLD());
        //airAllianceServices.add(new SKY_TIME());
		//airAllianceServices.add(new STAR_ALLIENCE());

		logged = null;

        jComboOrigin.setPrototypeDisplayValue("Seleccione el nombre del aeropuerto origen");		
		jComboOrigin.addActionListener((e) -> {
			Object fromItem = ((JComboBox<?>) e.getSource()).getSelectedItem();
			flights = new ArrayList<>();

            if (fromItem != null && !fromItem.toString().isEmpty()) {
				final String origin = fromItem.toString().substring(0, fromItem.toString().indexOf(" - "));

                if (!origin.isEmpty()) {
					Set<Airport> destinations = new HashSet<>();					
					//airAllianceServices.forEach(a -> destinations.addAll(a.getDestino(origin)));					
					updateDestinations(new ArrayList<Airport>(destinations));
				} else {
					jComboDestination.removeAllItems();
				}								
			}  
            updateFlights();
			jLabelInfo.setText("Selecciona un aeropuerto origen");
		});
        
        Set<Airport> origins = new HashSet<>();
		//airAllianceServices.forEach(a -> origins.addAll(a.getOrigin()));
		updateOrigins(new ArrayList<Airport>(origins));

        jComboDestination.setPrototypeDisplayValue("Seleccione el nombre del aeropuerto destino");
		jComboDestination.addActionListener((e) -> {
			Object toItem = ((JComboBox<?>) e.getSource()).getSelectedItem();
			flights = new ArrayList<>();
			
			if (toItem != null && !toItem.toString().isEmpty()) {
				final String destination = toItem.toString().substring(0, toItem.toString().indexOf(" - "));
				
				if (!destination.isEmpty() && jComboOrigin.getSelectedIndex() > 0) {
					Object fromItem = jComboOrigin.getSelectedItem();
					final String origin = fromItem.toString().substring(0, fromItem.toString().indexOf(" - "));
					//airAllianceServices.forEach(a -> flights.addAll(a.search(origin, destination)));				
				}
			}
			
			updateFlights();
		});
        
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

    public void updateFlights() {
		//Se crea un comparador para ordenar los itinerarios por número de vuelos				
		Comparator<Flight> priceComparator = (f1, f2) -> {
			return Float.compare(f1.getPrice(), f2.getPrice());
		};
		//Se ordenan los vuelos por precio
		Collections.sort(flights, priceComparator);
		
		jTableFlights.setModel(new FlightsTableModel(flights));	
		
		//Se define el render para todas las columnas de la tabla excepto la última
		FlightRenderer defaultRenderer = new FlightRenderer();
		
		for (int i=0; i<jTableFlights.getColumnModel().getColumnCount()-1; i++) {
			jTableFlights.getColumnModel().getColumn(i).setCellRenderer(defaultRenderer);
		}

		//Se define el render y editor para la última columna de la tabla
		int lastColumn = jTableFlights.getColumnModel().getColumnCount()-1;
		
		/*jTableFlights.getColumnModel().getColumn(lastColumn).setCellRenderer(new BookRendererEditor(this));
		jTableFlights.getColumnModel().getColumn(lastColumn).setCellEditor(new BookRendererEditor(this));		
		*/
		jLabelInfo.setText(String.format("%d vuelos", flights.size()));
	}
	/**
	 * Devuelve el servicio de la alianaza de aerolíneas que gestiona el vuelo.
	 * @param flight Flight con el vuelo.
	 * @return AirAllianceService con el servicio de la alianza de aerolíneas.
	 */
	//protected AirAllianceService getService(Vuelo flight) {
		//for (AirAllianceService service : airAllianceServices) {
			//if (service.getAirAlliance().equals(flight.getAerolinea().getAlliance())) {
				//return service;
			//}
		//}
		
		//return null;
	//}
    /**
	 * Método main
	 * @param args String[] con los argumentos.
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new VentanaPrincipal());
	}
}
