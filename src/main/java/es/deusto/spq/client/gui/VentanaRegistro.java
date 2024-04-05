package es.deusto.spq.client.gui;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VentanaRegistro extends JFrame {

    private JPanel contentPane;
    private JTextField textFieldUsuario, textFieldApellido, textFieldCorreo, textFieldPassword, textFieldConfirm;
    private JButton botonEntrar, botonVolver; // Declaramos los botones aquí

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    VentanaRegistro frame = new VentanaRegistro();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public VentanaRegistro() {

        setTitle("Registro");
        setBounds(100, 100, 600, 400); // Cambié las dimensiones para que sea más manejable
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(0, 0));

        // Panel de titulo
        JPanel panelTitulo = new JPanel();
        contentPane.add(panelTitulo, BorderLayout.NORTH);
        JLabel lblTitulo = new JLabel("Registro de Usuario"); // Cambié el nombre del título para hacerlo más descriptivo
        lblTitulo.setFont(new Font("Verdana", Font.BOLD, 18)); // Cambié el estilo de la fuente para hacerla más llamativa
        panelTitulo.add(lblTitulo);

        // Panel principal
        JPanel panelPrincipal = new JPanel();
        contentPane.add(panelPrincipal, BorderLayout.CENTER);
        panelPrincipal.setLayout(new GridLayout(7, 2, 10, 10)); // Ajusté el diseño para acomodar todos los campos de entrada

        // Campos de formulario
        JLabel lblUsuario = new JLabel("Nombre:");
        panelPrincipal.add(lblUsuario);

        textFieldUsuario = new JTextField();
        panelPrincipal.add(textFieldUsuario);
        textFieldUsuario.setColumns(20);

        JLabel lblApellido = new JLabel("Apellido:");
        panelPrincipal.add(lblApellido);

        textFieldApellido = new JTextField();
        panelPrincipal.add(textFieldApellido);
        textFieldApellido.setColumns(20);

        JLabel lblCorreo = new JLabel("Correo electrónico:");
        panelPrincipal.add(lblCorreo);

        textFieldCorreo = new JTextField();
        panelPrincipal.add(textFieldCorreo);
        textFieldCorreo.setColumns(20);

        JLabel lblPassword = new JLabel("Contraseña:");
        panelPrincipal.add(lblPassword);

        textFieldPassword = new JTextField();
        panelPrincipal.add(textFieldPassword);
        textFieldPassword.setColumns(20);

        JLabel lblConfirmar = new JLabel("Confirmar Contraseña:");
        panelPrincipal.add(lblConfirmar);

        textFieldConfirm = new JTextField();
        panelPrincipal.add(textFieldConfirm);
        textFieldConfirm.setColumns(20);

        // Panel de botones
        JPanel panelBoton = new JPanel();
        contentPane.add(panelBoton, BorderLayout.SOUTH);

        botonEntrar = new JButton("Registrar Usuario");
        panelBoton.add(botonEntrar);
        botonEntrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO: Agregar la lógica para registrar al usuario
            }
        });

        botonVolver = new JButton("Atrás");
        panelBoton.add(botonVolver);
        botonVolver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                VentanaLogin ventanaLogin = new VentanaLogin();
                ventanaLogin.setVisible(true); // Agregué la visibilidad de la ventana de inicio de sesión al volver atrás
            }
        });
    }
}
