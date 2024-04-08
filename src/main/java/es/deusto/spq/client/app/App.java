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
        res.register("Pablo", "Martinez", "pablo@gmail.com", "pablo1234");
        VentanaLogin login = new VentanaLogin();
        login.setVisible(true);
    }
}
