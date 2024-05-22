package es.deusto.spq.client.gui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

import java.awt.Component;

import javax.swing.JButton;
import javax.swing.JTable;

import org.junit.Before;
import org.junit.Test;

import es.deusto.spq.client.domain.Flight;
import es.deusto.spq.client.gui.BookRendererEditor;
import es.deusto.spq.client.gui.MainWindow;

public class BookRendererEditorTest {

    private BookRendererEditor rendererEditor;
    private JTable tableMock;
    private Flight flightMock;
    private MainWindow mainWindowMock;

    @Before
    public void setUp() {
        mainWindowMock = mock(MainWindow.class);
        rendererEditor = new BookRendererEditor(mainWindowMock);
        tableMock = mock(JTable.class);
        flightMock = mock(Flight.class);
    }

    @Test
    public void testGetTableCellEditorComponent() {
        Component component = rendererEditor.getTableCellEditorComponent(tableMock, flightMock, true, 0, 0);
        assertTrue(component instanceof JButton);
        JButton button = (JButton) component;
        assertEquals("Reservar", button.getText());
    }

    @Test
    public void testGetTableCellRendererComponent() {
        Component component = rendererEditor.getTableCellRendererComponent(tableMock, flightMock, true, false, 0, 0);
        assertTrue(component instanceof JButton);
        JButton button = (JButton) component;
        assertEquals("Reservar", button.getText());
    }

    @Test
    public void testGetCellEditorValue() {
        rendererEditor.getTableCellEditorComponent(tableMock, flightMock, true, 0, 0);
        assertEquals(flightMock, rendererEditor.getCellEditorValue());
    }

    @Test
    public void testShouldSelectCell() {
        assertTrue(rendererEditor.shouldSelectCell(null));
    }
}
