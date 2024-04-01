package es.deusto.spq.client.gui;

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

public class VentanaRegistro extends JFrame{

    private JPanel contentPane;
    private JButton botonEntrar, botonVolver;
    private JTextField textFieldUsuario, textFieldApellido, textFieldDNI, textFieldFecha, textFieldCorreo, textFieldPassword, textFieldConfirm;

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
    
    public VentanaRegistro(){

        setTitle("Registro");
        setBounds(100, 100, 1100, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(0, 0));

        //Panel de titulo
        JPanel panelTitulo = new JPanel();
        contentPane.add(panelTitulo, BorderLayout.NORTH);
        JLabel Titulo = new JLabel("Registro");
        Titulo.setFont(new Font("Verdana Pro Cond Semibold", Font.PLAIN, 15));
        panelTitulo.add(Titulo);

        //Panel principal
        JPanel panelPrincipal = new JPanel();
        contentPane.add(panelPrincipal, BorderLayout.CENTER);
        panelPrincipal.setLayout(new GridLayout(1, 2, 10, 10));

        //PanelFormulario
        JPanel panelFormulario = new JPanel();
        panelFormulario.setLayout(new GridLayout(2,2,10,10));
        panelPrincipal.add(panelFormulario);

        JLabel lblUsuario = new JLabel("Nombre: ");
        panelFormulario.add(lblUsuario);

        textFieldUsuario = new JTextField();
        panelFormulario.add(textFieldUsuario);
        textFieldUsuario.setColumns(50);

        JLabel lblApellido = new JLabel("Apellido: ");
        panelFormulario.add(lblApellido);

        textFieldApellido = new JTextField();
        panelFormulario.add(textFieldApellido);
        textFieldApellido.setColumns(50);

        JLabel lblDNI = new JLabel("DNI: ");
        panelFormulario.add(lblDNI);

        textFieldDNI = new JTextField();
        panelFormulario.add(textFieldDNI);
        textFieldDNI.setColumns(50);

        JLabel lblFecha = new JLabel("Fecha de nacimiento: ");
        panelFormulario.add(lblUsuario);

        textFieldFecha = new JTextField();
        panelFormulario.add(textFieldFecha);
        textFieldFecha.setColumns(50);

        JLabel lblcorreo = new JLabel("Correo electronico: ");
        panelFormulario.add(lblcorreo);

        textFieldCorreo = new JTextField();
        panelFormulario.add(textFieldCorreo);
        textFieldCorreo.setColumns(50);

        JLabel lblPassword = new JLabel("Contrasena: ");
        panelFormulario.add(lblPassword);

        textFieldPassword = new JTextField();
        panelFormulario.add(textFieldPassword);
        textFieldPassword.setColumns(50);

        JLabel lblConfirmar = new JLabel("Confirmar Contrasena: ");
        panelFormulario.add(lblConfirmar);

        textFieldConfirm = new JTextField();
        panelFormulario.add(textFieldConfirm);
        textFieldConfirm.setColumns(50);

        //Panel de botones
        JPanel panelBoton= new JPanel();
        contentPane.add(panelBoton, BorderLayout.SOUTH);
        panelBoton.setLayout(new GridLayout(1,2,10,10));
        
        botonEntrar = new JButton("registrar usuario");
        panelBoton.add(botonEntrar);

        botonEntrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
            }
        });

        botonVolver = new JButton("atras");
        panelBoton.add(botonVolver);

        botonEntrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                VentanaLogin ventanalogin = new VentanaLogin();
            }
        });

    }



}


