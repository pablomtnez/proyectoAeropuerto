package es.deusto.spq.client.gui;

import javax.swing.JFrame;
import es.deusto.spq.client.domain.Usuario;


public class VentanaVacia extends JFrame {
    public static Usuario logged = null;

    public VentanaVacia() {
        // Establece el título de la ventana
        setTitle("Ventana Vacía");
        
        // Establece el comportamiento del botón de cerrar
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Establece el tamaño inicial de la ventana (ancho, alto)
        setSize(600, 400);
        
        // Hace que la ventana sea visible
        setVisible(true);
    }
}
