package es.deusto.spq.client.gui;

import java.util.Arrays;
import java.util.List;

import javax.swing.table.DefaultTableModel;

import es.deusto.spq.pojo.Vuelo;

public class FlightsTableModel extends DefaultTableModel{

    private static final long serialVersionUID = 1L;
	
	private List<Vuelo> flights;
	private final List<String> headers = Arrays.asList(
			"AEROLÍNEA", 
			"VUELO", 
			"ORIGEN", 
			"DESTINO", 
			"DURACIÓN", 
			"PRECIO", 
			"RESERVAS",
			"ASIENTOS LIBRES",
			"DISPONIBILIDAD", //Columna para la disponibilidad
			"RESERVAR");

	public FlightsTableModel(List<Vuelo> flights) {
		this.flights = flights;
	}
	
	@Override
	public String getColumnName(int column) {
		return headers.get(column);
	}

	@Override
	public int getRowCount() {
		if (flights != null) {
			return flights.size();
		} else { 
			return 0;
		}
	}

	@Override
	public int getColumnCount() {
		return headers.size(); 
	}
	
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        //Hay que modificar este método para que la columna del botón sea editable.
    	return (columnIndex == headers.size()-1);  // O 9 (en vez de 8) si se hace literal
    }
    
    @Override
    public void setValueAt(Object aValue, int row, int column) {    	
    }
	
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Vuelo flight = flights.get(rowIndex);
		
		switch (columnIndex) {
			case 0: return flight.getAerolinea();
			case 1: return flight.getCode();
			case 2: return flight.getOrigen();
			case 3: return flight.getDestino();
			case 4: return Integer.valueOf(flight.getDuracion());
			case 5: return Float.valueOf(flight.getPrecio());
			case 6: return Integer.valueOf(flight.getReservas().size());
			case 7: return Integer.valueOf(flight.getRemainingSeats());
			//La disponibilidad se calcula como el cociente entre RemainingSeats y Seats
			case 8: return Float.valueOf( 1.0f * flight.getRemainingSeats() / flight.getSeats() );
			case 9: return flight;
			default: return null;
		}
	}
    
}