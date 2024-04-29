package es.deusto.spq.client.app;

import javax.swing.UIManager;

import es.deusto.spq.client.ResourceClient;
import es.deusto.spq.client.gui.VentanaLogin;

public class App {
    public static ResourceClient res = new ResourceClient("127.0.0.1", "8080");

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {
            // TODO: handle exception
        }

        // Agregar registro de usuario
        boolean registered = ResourceClient.register("Pablo", "Martinez", "pablo@gmail.com", "pablo1234");
        if (registered) {
            System.out.println("User registered successfully");
        } else {
            System.out.println("Failed to register user");
        }

        // boolean flight1 = ResourceClient.createFlights(0001, null, null, null, null,
        // 0, 0)

        // Mostrar ventana de inicio de sesión
        ResourceClient.loadFlights();
        VentanaLogin login = new VentanaLogin();
        login.setVisible(true);
    }
}
