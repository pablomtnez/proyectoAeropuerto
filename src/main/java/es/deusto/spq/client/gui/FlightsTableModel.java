package es.deusto.spq.client.gui;

import java.util.Arrays;
import java.util.List;

import javax.swing.table.DefaultTableModel;

import es.deusto.spq.client.domain.Flight;

public class FlightsTableModel extends DefaultTableModel {
    private static final long serialVersionUID = 1L;
    
    private List<Flight> flights;
    private final List<String> headers = Arrays.asList(
        "AEROLÍNEA",
        "VUELO",
        "ORIGEN",
        "DESTINO",
        "DURACIÓN",
        "PRECIO",
        "RESERVAS",
        "ASIENTOS LIBRES",
        "DISPONIBILIDAD", // Columna para la disponibilidad
        "RESERVAR"
    );

    public FlightsTableModel(List<Flight> flights) {
        this.flights = flights;
    }

    @Override
    public String getColumnName(int column) {
        return headers.get(column);
    }

    @Override
    public int getRowCount() {
        return flights != null ? flights.size() : 0;
    }

    @Override
    public int getColumnCount() {
        return headers.size();
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        // Solo la columna de "RESERVAR" es editable
        return columnIndex == headers.size() - 1;
    }

    @Override
    public void setValueAt(Object aValue, int row, int column) {
        if (column == headers.size() - 1 && aValue instanceof Flight) {
            flights.set(row, (Flight) aValue);
            fireTableCellUpdated(row, column);
        }
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Flight flight = flights.get(rowIndex);

        switch (columnIndex) {
            case 0: return flight.getAirline();
            case 1: return flight.getCode();
            case 2: return flight.getFrom().getIataCode();
            case 3: return flight.getTo().getIataCode();
            case 4: return flight.getDuration() + " min";
            case 5: return String.format("%.2f €", flight.getPrice());
            case 6: return flight.getReservations().size();
            case 7: return flight.getRemainingSeats();
            case 8: return String.format("%.1f %%", (1.0f * flight.getRemainingSeats() / flight.getSeats()) * 100);
            case 9: return flight;
            default: return null;
        }
    }
}
