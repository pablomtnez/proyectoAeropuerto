package es.deusto.spq.client.gui;

import java.awt.Component;
import java.util.EventObject;
import java.util.List;

import javax.swing.AbstractCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import es.deusto.spq.client.domain.Flight;

public class BookRendererEditor extends AbstractCellEditor implements TableCellEditor, TableCellRenderer {
    private static final long serialVersionUID = 1L;

    private Flight flight;
    private MainWindow mainWindow;

    public BookRendererEditor(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
    }

    private JButton prepare(JTable table, Object value, boolean isSelected, int row, int column) {
        flight = (Flight) value;

        JButton button = new JButton("Reservar");
        button.setEnabled(true);
        button.setToolTipText(String.format("Reservar - %s", flight.getCode()));
        button.setBackground(table.getBackground());

        if (isSelected) {
            button.setBackground(table.getSelectionBackground());
        }

        button.addActionListener((e) -> {
            // Se crea el cuadro de diálogo para confirmar la reserva
            BookDialog dialog = new BookDialog(flight);

            // Mostrar el diálogo y procesar la reserva si se confirman los pasajeros
            if (dialog.getPassengers() != null && !dialog.getPassengers().isEmpty()) {
                // Obtener los pasajeros confirmados del diálogo
                List<String> passengers = dialog.getPassengers();
                boolean hasBaggage = dialog.hasAdditionalBaggage(); // Obtener el estado del equipaje adicional

                // Aquí debería llamar al servicio de reserva con los pasajeros y el estado del equipaje
                //String locator = mainWindow.getService(flight).book(flight.getCode(), passengers, hasBaggage);

                //JOptionPane.showMessageDialog(mainWindow, 
                        //String.format("El localizador de la reserva es: %s", locator),
                        //String.format("Confirmación de la reserva del vuelo %s", flight.getCode()),
                        //JOptionPane.INFORMATION_MESSAGE,
                        //new ImageIcon("resources/images/confirm.png"));

                // Actualizar la lista de vuelos en la ventana principal
                mainWindow.updateFlights();
            }

            dialog.dispose();
        });

        button.setOpaque(true);

        return button;
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        return prepare(table, value, isSelected, row, column);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        return prepare(table, value, isSelected, row, column);
    }

    @Override
    public Object getCellEditorValue() {
        return flight;
    }

    @Override
    public boolean shouldSelectCell(EventObject event) {
        return true;
    }
}
