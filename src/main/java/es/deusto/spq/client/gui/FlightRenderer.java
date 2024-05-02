package es.deusto.spq.client.gui;

import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import es.deusto.spq.client.domain.Airline;
import es.deusto.spq.client.domain.Airport;

public class FlightRenderer implements TableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        JLabel label = new JLabel();
        // El color de fondo es el color por defecto de la tabla
        label.setBackground(table.getBackground());
        // Por defecto el label se centra
        label.setHorizontalAlignment(JLabel.CENTER);

        // AEROLÍNEA se renderiza como un label con el logo de la aerolínea
        if (value instanceof Airline) {
            // Descomenta la línea siguiente para mostrar el icono de la aerolínea
            // label.setIcon(new ImageIcon(String.format("resources/images/%s.png", ((Airline)value).getCode())));
            label.setText(((Airline) value).getName());
            label.setToolTipText(((Airline) value).getName());
        }

        // El VUELO, RESERVAS y ASIENTOS LIBRES se renderizan como texto centrado
        if (column == 1 || column == 6 || column == 7) {
            label.setText(value != null ? value.toString() : "");
        }

        // ORIGEN y DESTINO se renderizan con el código de aeropuerto como texto centrado
        if (value instanceof Airport) {
            label.setText(((Airport) value).getIataCode());
            label.setToolTipText(((Airport) value).getName());
        }

        // DURACIÓN se alinea a la derecha y se añade "m."
        if (column == 4 && value != null) {
            label.setText(String.format("%s m.", value.toString()));
            label.setHorizontalAlignment(JLabel.RIGHT);
        }

        // PRECIO se alinea a la derecha, redondea con 2 decimales, se añade "€"
        if (column == 5 && value != null) {
            label.setText(String.format("%.2f €", value));
            label.setHorizontalAlignment(JLabel.RIGHT);
        }

        // DISPONIBILIDAD se renderiza como una progress bar.
        if (column == 8 && value != null) {
            JProgressBar bar = new JProgressBar(0, 100);
            float percent = (float) value;
            bar.setValue(Math.round(100 * percent));
            bar.setString(String.format("%.1f %%", 100 * percent));
            bar.setStringPainted(true);
            bar.setBackground(table.getBackground());

            if (isSelected) {
                bar.setBackground(table.getSelectionBackground());
                bar.setForeground(table.getSelectionForeground());
            }

            bar.setOpaque(true);
            return bar;
        }

        // Si la celda está seleccionada se usa el color por defecto de selección de la tabla
        if (isSelected) {
            label.setBackground(table.getSelectionBackground());
            label.setForeground(table.getSelectionForeground());
        }

        label.setOpaque(true);
        return label;
    }
}
