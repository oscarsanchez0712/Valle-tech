package com.valletech.ui;

import com.valletech.dao.UsuarioDAO;
import com.valletech.model.Usuario;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;

public class    LoginFrame extends JFrame {

    private JTextField txtUsuario;
    private JPasswordField txtContrasena;
    private JLabel lblMensaje;
    private JButton btnIngresar;

    // Colores
    private static final Color C_AZUL       = new Color(26, 82, 118);
    private static final Color C_AZUL_CLARO = new Color(52, 152, 219);
    private static final Color C_FONDO      = new Color(236, 240, 241);
    private static final Color C_BLANCO     = Color.WHITE;
    private static final Color C_ROJO       = new Color(192, 57, 43);
    private static final Color C_VERDE      = new Color(39, 174, 96);

    public LoginFrame() {
        initUI();
    }

    private void initUI() {
        setTitle("ValleTech - Iniciar Sesión");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(420, 520);
        setResizable(false);
        setLocationRelativeTo(null);

        // Panel principal con fondo
        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBackground(C_FONDO);

        // Panel superior azul (header)
        JPanel panelHeader = new JPanel(new GridBagLayout());
        panelHeader.setBackground(C_AZUL);
        panelHeader.setPreferredSize(new Dimension(420, 160));

        JLabel lblLogo = new JLabel("VT");
        lblLogo.setFont(new Font("Arial", Font.BOLD, 48));
        lblLogo.setForeground(C_BLANCO);

        JLabel lblEmpresa = new JLabel("ValleTech");
        lblEmpresa.setFont(new Font("Arial", Font.BOLD, 22));
        lblEmpresa.setForeground(C_BLANCO);

        JLabel lblSlogan = new JLabel("Gestión de Productos");
        lblSlogan.setFont(new Font("Arial", Font.PLAIN, 13));
        lblSlogan.setForeground(new Color(174, 214, 241));

        JPanel panelTextoHeader = new JPanel();
        panelTextoHeader.setOpaque(false);
        panelTextoHeader.setLayout(new BoxLayout(panelTextoHeader, BoxLayout.Y_AXIS));
        lblLogo.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblEmpresa.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblSlogan.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelTextoHeader.add(lblLogo);
        panelTextoHeader.add(Box.createVerticalStrut(4));
        panelTextoHeader.add(lblEmpresa);
        panelTextoHeader.add(Box.createVerticalStrut(2));
        panelTextoHeader.add(lblSlogan);
        panelHeader.add(panelTextoHeader);

        // Panel del formulario
        JPanel panelForm = new JPanel();
        panelForm.setBackground(C_BLANCO);
        panelForm.setLayout(new GridBagLayout());
        panelForm.setBorder(new EmptyBorder(30, 40, 30, 40));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(6, 0, 6, 0);
        gbc.gridx = 0;
        gbc.weightx = 1.0;

        // Título
        JLabel lblTitulo = new JLabel("Iniciar Sesión");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitulo.setForeground(C_AZUL);
        gbc.gridy = 0;
        panelForm.add(lblTitulo, gbc);

        gbc.gridy = 1;
        panelForm.add(Box.createVerticalStrut(10), gbc);

        // Campo Usuario
        JLabel lblU = new JLabel("Usuario");
        lblU.setFont(new Font("Arial", Font.BOLD, 12));
        lblU.setForeground(new Color(52, 73, 94));
        gbc.gridy = 2;
        panelForm.add(lblU, gbc);

        txtUsuario = new JTextField();
        txtUsuario.setFont(new Font("Arial", Font.PLAIN, 13));
        txtUsuario.setPreferredSize(new Dimension(300, 38));
        txtUsuario.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189, 195, 199), 1),
            new EmptyBorder(5, 10, 5, 10)
        ));
        gbc.gridy = 3;
        panelForm.add(txtUsuario, gbc);

        // Campo Contraseña
        JLabel lblC = new JLabel("Contraseña");
        lblC.setFont(new Font("Arial", Font.BOLD, 12));
        lblC.setForeground(new Color(52, 73, 94));
        gbc.gridy = 4;
        panelForm.add(lblC, gbc);

        txtContrasena = new JPasswordField();
        txtContrasena.setFont(new Font("Arial", Font.PLAIN, 13));
        txtContrasena.setPreferredSize(new Dimension(300, 38));
        txtContrasena.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189, 195, 199), 1),
            new EmptyBorder(5, 10, 5, 10)
        ));
        gbc.gridy = 5;
        panelForm.add(txtContrasena, gbc);

        // Mensaje
        lblMensaje = new JLabel(" ");
        lblMensaje.setFont(new Font("Arial", Font.BOLD, 12));
        lblMensaje.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridy = 6;
        gbc.insets = new Insets(10, 0, 4, 0);
        panelForm.add(lblMensaje, gbc);

        // Botón Ingresar
        btnIngresar = new JButton("INGRESAR");
        btnIngresar.setFont(new Font("Arial", Font.BOLD, 14));
        btnIngresar.setBackground(C_AZUL_CLARO);
        btnIngresar.setForeground(C_BLANCO);
        btnIngresar.setFocusPainted(false);
        btnIngresar.setBorderPainted(false);
        btnIngresar.setPreferredSize(new Dimension(300, 42));
        btnIngresar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnIngresar.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e)  { btnIngresar.setBackground(C_AZUL); }
            public void mouseExited(MouseEvent e)   { btnIngresar.setBackground(C_AZUL_CLARO); }
        });
        gbc.gridy = 7;
        gbc.insets = new Insets(4, 0, 0, 0);
        panelForm.add(btnIngresar, gbc);

        // Panel inferior
        JPanel panelFooter = new JPanel();
        panelFooter.setBackground(C_FONDO);
        panelFooter.setBorder(new EmptyBorder(8, 0, 8, 0));
        JLabel lblFooter = new JLabel("© 2024 ValleTech - Todos los derechos reservados");
        lblFooter.setFont(new Font("Arial", Font.PLAIN, 10));
        lblFooter.setForeground(Color.GRAY);
        panelFooter.add(lblFooter);

        panelPrincipal.add(panelHeader, BorderLayout.NORTH);
        panelPrincipal.add(panelForm, BorderLayout.CENTER);
        panelPrincipal.add(panelFooter, BorderLayout.SOUTH);

        setContentPane(panelPrincipal);

        // Eventos
        btnIngresar.addActionListener(e -> login());
        txtContrasena.addActionListener(e -> login());

        setVisible(true);
    }

    private void login() {
        String usuario = txtUsuario.getText().trim();
        String contrasena = new String(txtContrasena.getPassword()).trim();

        if (usuario.isEmpty() || contrasena.isEmpty()) {
            mostrarMensaje("Por favor completa todos los campos.", C_ROJO);
            return;
        }

        btnIngresar.setEnabled(false);
        btnIngresar.setText("Verificando...");

        SwingWorker<Usuario, Void> worker = new SwingWorker<>() {
            @Override
            protected Usuario doInBackground() {
                return new UsuarioDAO().autenticar(usuario, contrasena);
            }

            @Override
            protected void done() {
                try {
                    Usuario u = get();
                    if (u != null) {
                        mostrarMensaje("✓ Acceso correcto. Bienvenido, " + u.getNombreCompleto(), C_VERDE);
                        Timer timer = new Timer(800, ev -> {
                            dispose();
                            new DashboardFrame(u);
                        });
                        timer.setRepeats(false);
                        timer.start();
                    } else {
                        mostrarMensaje("✗ Usuario o contraseña incorrectos.", C_ROJO);
                        txtContrasena.setText("");
                        txtContrasena.requestFocus();
                        btnIngresar.setEnabled(true);
                        btnIngresar.setText("INGRESAR");
                    }
                } catch (Exception ex) {
                    mostrarMensaje("Error de conexión con la base de datos.", C_ROJO);
                    btnIngresar.setEnabled(true);
                    btnIngresar.setText("INGRESAR");
                }
            }
        };
        worker.execute();
    }

    private void mostrarMensaje(String msg, Color color) {
        lblMensaje.setText(msg);
        lblMensaje.setForeground(color);
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}
        SwingUtilities.invokeLater(LoginFrame::new);
    }
}
