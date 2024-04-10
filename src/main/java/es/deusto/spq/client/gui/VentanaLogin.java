package es.deusto.spq.client.gui;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.border.EmptyBorder;

import es.deusto.spq.client.ResourceClient;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.ProcessingException;

public class VentanaLogin extends JFrame {
    
    private JPanel contentPane;
    private JTextField textFieldUsuario, textFieldPassword;
    private JButton botonLogin, botonRegistro;

    public VentanaLogin() {
        
        // Configuración básica de la ventana
        setTitle("Login");
        setPreferredSize(new Dimension(600, 400)); // Tamaño preferido de la ventana
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // La aplicación se cierra al cerrar la ventana

        // Creación del panel principal
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10)); // Espacios en blanco alrededor del panel
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout()); // Diseño de la ventana

        // Panel Norte - Título
        JPanel panelNorte = new JPanel();
        contentPane.add(panelNorte, BorderLayout.NORTH);

        JLabel lblTitulo = new JLabel("Login");
        lblTitulo.setFont(new Font("Verdana", Font.BOLD, 24)); // Fuente más grande y en negrita
        panelNorte.add(lblTitulo);

        // Panel Centro - Formulario de Login
        JPanel panelCentro = new JPanel(new GridBagLayout());
        contentPane.add(panelCentro, BorderLayout.CENTER);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Espacios entre componentes
        gbc.anchor = GridBagConstraints.WEST; // Alineación de los componentes a la izquierda

        // Campo de usuario
        JLabel lblUsuario = new JLabel("Email:");
        gbc.gridx = 0; // Columna
        gbc.gridy = 0; // Fila
        panelCentro.add(lblUsuario, gbc);

        textFieldUsuario = new JTextField(20); // Ancho del campo de texto
        gbc.gridx = 1;
        gbc.gridy = 0;
        panelCentro.add(textFieldUsuario, gbc);

        // Campo de contraseña
        JLabel lblPassword = new JLabel("Contraseña:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        panelCentro.add(lblPassword, gbc);

        textFieldPassword = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 1;
        panelCentro.add(textFieldPassword, gbc);

        // Panel Este - Imagen
        JPanel panelImagen = new JPanel();
        contentPane.add(panelImagen, BorderLayout.EAST);

        JLabel lblImagen = new JLabel();
        ImageIcon icono = new ImageIcon("ruta/a/tu/imagen/logo.jpg"); // Ruta de la imagen
        lblImagen.setIcon(icono);
        panelImagen.add(lblImagen);

        // Panel Sur - Botones de Login y Registro
        JPanel panelSur = new JPanel();
        contentPane.add(panelSur, BorderLayout.SOUTH);

        botonLogin = new JButton("Login");
        panelSur.add(botonLogin);

        // Acción del botón de login
        botonLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean loggedIn = ResourceClient.login(textFieldUsuario.getText(), textFieldPassword.getText());
                if (loggedIn) {
                    // Si el login es exitoso, muestra un mensaje informativo
                    JOptionPane.showMessageDialog(null, "Inicio de sesión exitoso.", "Sesión Iniciada", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    // Si el login falla, muestra un mensaje de error
                    JOptionPane.showMessageDialog(null, "Error al iniciar sesión.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        botonRegistro = new JButton("Registro");
        panelSur.add(botonRegistro);

        // Acción del botón de registro
        botonRegistro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Oculta la ventana de login
                setVisible(false);
                // Muestra la ventana de registro
                VentanaRegistro ventanaRegistro = new VentanaRegistro();
                ventanaRegistro.setVisible(true);
            }
        });

        // Ajusta automáticamente el tamaño de la ventana según su contenido
        pack();
        // Centra la ventana en la pantalla
        setLocationRelativeTo(null);
    }
}


