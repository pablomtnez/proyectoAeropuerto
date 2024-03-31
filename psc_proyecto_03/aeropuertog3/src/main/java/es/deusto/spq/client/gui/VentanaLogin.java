package es.deusto.spq.client.gui;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;


public class VentanaLogin extends JFrame{
    
    private JPanel contentPane;
    private JTextField textFieldUsuario, textFieldPassword;
    private JButton botonLogin, botonRegistro;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
        public void run() {
            try {
                VentanaLogin frame = new VentanaLogin();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        });
    }

    public VentanaLogin(){
        
        //Ventana

        setTitle("Login");
        setBounds(100, 100, 1100, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(0, 0));

        //Panel Norte
        JPanel panelNorte = new JPanel();
        contentPane.add(panelNorte, BorderLayout.NORTH);

        JLabel lblTitulo = new JLabel("Login");
        lblTitulo.setFont(new Font("Verdana Pro Cond Semibold", Font.PLAIN, 15));
        panelNorte.add(lblTitulo);

        //PanelCentro
        JPanel panelCentro = new JPanel();
        contentPane.add(panelCentro, BorderLayout.CENTER);
        panelCentro.setLayout(new GridLayout(1, 2, 10, 10));

        //PanelFormulario
        JPanel panelFormulario = new JPanel();
        panelFormulario.setLayout(new GridLayout(2,2,10,10));
        panelCentro.add(panelFormulario);

        JLabel lblUsuario = new JLabel("Usuario");
        panelFormulario.add(lblUsuario);

        textFieldUsuario = new JTextField();
        panelFormulario.add(textFieldUsuario);
        textFieldUsuario.setColumns(50);

        JLabel lblPassword = new JLabel("Contrasena");
        panelFormulario.add(lblPassword);

        textFieldPassword = new JTextField();
        panelFormulario.add(textFieldPassword);
        textFieldPassword.setColumns(50);

        //Panel Imagen
        JPanel panelImagen = new JPanel();
        panelCentro.add(panelImagen);

        JLabel lblImagen = new JLabel();
        ImageIcon icono = new ImageIcon("C:/Users/pablo/Documents/GitHub/proyectoAeropuerto/psc_proyecto_03/aeropuertog3/src/main/java/es/deusto/spq/client/utils/logo.jpg");
        lblImagen.setIcon(icono);
        panelImagen.add(lblImagen);

        //Panel Sur
        JPanel panelSur = new JPanel();
        contentPane.add(panelSur, BorderLayout.SOUTH);
        panelSur.setLayout(new GridLayout(1, 1, 0, 0));

        JPanel panelBoton= new JPanel();
        panelBoton.setLayout(new GridLayout(1,2,10,10));
        panelSur.add(panelBoton);
        botonLogin = new JButton("Login");
        panelBoton.add(botonLogin);

        botonLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
            }
        });

        botonRegistro = new JButton("Registro");
        panelBoton.add(botonRegistro);

        botonRegistro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
            }
        });

    }

    
}
