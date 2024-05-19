package es.deusto.spq.client.gui;

import java.awt.Component;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import es.deusto.spq.server.jdo.Airline;
import es.deusto.spq.server.jdo.Airport;

public class FlightRenderer implements TableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        JLabel label = new JLabel();
        // El color de fondo es el color por defecto de la tabla
        label.setBackground(table.getBackground());
        // Por defecto el label se centra
        label.setHorizontalAlignment(JLabel.CENTER);

        if (value == null) {
            value = getDefaultValue(column);
        }

        // AEROLÍNEA se renderiza como un label con el logo de la aerolínea
        if (value instanceof Airline) {
            // label.setIcon(new ImageIcon(String.format("resources/images/%s.png", ((Airline)value).getCode())));
            label.setToolTipText(((Airline) value).getName());
        }

        // El VUELO, RESERVAS y ASIENTOS LIBRES se renderizan como texto centrado
        if (column == 1 || column == 6 || column == 7) {
            label.setText(value.toString());
        }

        // ORIGEN y DESTINO se renderizan con el código de aeropuerto como texto centrado
        if (value instanceof Airport) {
            label.setText(((Airport) value).getIataCode());
            label.setToolTipText(((Airport) value).getName());
        }

        // DURACIÓN se alinea a la derecha y se añade "m."
        if (column == 4) {
            label.setText(String.format("%s m.", value.toString()));
            label.setHorizontalAlignment(JLabel.RIGHT);
        }

        // PRECIO se alinea a la derecha, redondea con 2 decimales, se añade "€"
        if (column == 5) {
            label.setText(String.format("%.2f €", value));
            label.setHorizontalAlignment(JLabel.RIGHT);
        }

        // DISPONIBILIDAD se renderiza como una progress bar.
        if (column == 8) {
            JProgressBar bar = new JProgressBar(0, 100);
            bar.setValue(Math.round(100 * (Float) value));
            bar.setString(String.format("%.1f %%", 100 * (Float) value));
            bar.setStringPainted(true);
            bar.setBackground(table.getBackground());

            if (isSelected) {
                bar.setBackground(table.getSelectionBackground());
                bar.setForeground(table.getSelectionForeground());
            }

            bar.setOpaque(true);

            return bar;
        }

        // RESERVAR se renderiza como un botón
        if (column == 9) {
            JButton button = new JButton("Reservar");
            button.setBackground(table.getBackground());
            button.setOpaque(true);
            button.setEnabled(true);

            if (isSelected) {
                button.setBackground(table.getSelectionBackground());
                button.setForeground(table.getSelectionForeground());
            }

            return button;
        }

        // Si la celda está seleccionada se usa el color por defecto de selección de la tabla
        if (isSelected) {
            label.setBackground(table.getSelectionBackground());
            label.setForeground(table.getSelectionForeground());
        }

        label.setOpaque(true);

        return label;
    }

    private Object getDefaultValue(int column) {
        switch (column) {
            case 1: // VUELO
            case 6: // RESERVAS
            case 7: // ASIENTOS LIBRES
                return "";
            case 4: // DURACIÓN
                return "0";
            case 5: // PRECIO
                return "0.00";
            case 8: // DISPONIBILIDAD
                return 0.0f;
            case 9: // RESERVAR
                return "Reservar";
            default:
                return "";
        }
    }
}
