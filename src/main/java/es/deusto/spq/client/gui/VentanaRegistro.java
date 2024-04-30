package es.deusto.spq.client.gui;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.border.EmptyBorder;

import es.deusto.spq.client.ResourceClient;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VentanaRegistro extends JFrame {

    private JPanel contentPane;
    private JTextField textFieldUsuario, textFieldApellido, textFieldCorreo, textFieldPassword, textFieldConfirm;
    private JButton botonEntrar, botonVolver;
    private ResourceClient resourceClient;

    public VentanaRegistro() {
        setTitle("Registro");
        setBounds(100, 100, 600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(0, 0));

        JPanel panelTitulo = new JPanel();
        contentPane.add(panelTitulo, BorderLayout.NORTH);
        JLabel lblTitulo = new JLabel("Registro de Usuario");
        lblTitulo.setFont(new Font("Verdana", Font.BOLD, 18));
        panelTitulo.add(lblTitulo);

        JPanel panelPrincipal = new JPanel();
        contentPane.add(panelPrincipal, BorderLayout.CENTER);
        panelPrincipal.setLayout(new GridLayout(7, 2, 10, 10));

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

        JPanel panelBoton = new JPanel();
        contentPane.add(panelBoton, BorderLayout.SOUTH);

        botonEntrar = new JButton("Registrar Usuario");
        panelBoton.add(botonEntrar);
        botonEntrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(textFieldUsuario.getText().isEmpty() || textFieldApellido.getText().isEmpty() || textFieldCorreo.getText().isEmpty() || textFieldPassword.getText().isEmpty() || textFieldConfirm.getText().isEmpty()){
                    JOptionPane.showMessageDialog(null, "Todos los campos deben estar llenos", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                String emailPattern = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
                if(!textFieldCorreo.getText().matches(emailPattern)){
                    JOptionPane.showMessageDialog(null, "Introduce un correo electrónico válido.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if(!textFieldPassword.getText().equals(textFieldConfirm.getText())){
                    JOptionPane.showMessageDialog(null, "Las contraseñas no coinciden", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if(ResourceClient.register(textFieldUsuario.getText(), textFieldApellido.getText(), textFieldCorreo.getText(), textFieldPassword.getText())){
                    JOptionPane.showMessageDialog(null, "Usuario registrado correctamente", "Registro exitoso", JOptionPane.INFORMATION_MESSAGE);
                    VentanaLogin VentanaLogin = new VentanaLogin();
                    VentanaLogin.setVisible(true);
                    setVisible(false);
                } else {
                    JOptionPane.showMessageDialog(null, "No se pudo registrar al usuario", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        botonVolver = new JButton("Atrás");
        panelBoton.add(botonVolver);
        botonVolver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                VentanaLogin ventanaLogin = new VentanaLogin();
                ventanaLogin.setVisible(true);
            }
        });
    }

    public VentanaRegistro(ResourceClient resourceClient) {
        this.resourceClient = resourceClient;
    }
}
