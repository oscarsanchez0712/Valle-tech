package com.valletech.ui;

import com.valletech.model.Usuario;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;

public class DashboardFrame extends JFrame {

    private final Usuario usuarioActual;

    private static final Color C_AZUL      = new Color(26, 82, 118);
    private static final Color C_HOVER     = new Color(52, 152, 219);
    private static final Color C_FONDO     = new Color(236, 240, 241);
    private static final Color C_BLANCO    = Color.WHITE;
    private static final Color C_ROJO      = new Color(192, 57, 43);
    private static final Color C_ROJO_H    = new Color(231, 76, 60);

    public DashboardFrame(Usuario usuario) {
        this.usuarioActual = usuario;
        initUI();
    }

    private void initUI() {
        setTitle("ValleTech - Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(520, 560);
        setResizable(false);
        setLocationRelativeTo(null);

        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBackground(C_FONDO);

        // ── Header ─────────────────────────────────────────────────
        JPanel panelHeader = new JPanel(new BorderLayout());
        panelHeader.setBackground(C_AZUL);
        panelHeader.setBorder(new EmptyBorder(20, 25, 20, 25));

        JLabel lblTitulo = new JLabel("Panel Principal");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 22));
        lblTitulo.setForeground(C_BLANCO);

        JLabel lblUsuario = new JLabel("Usuario: " + usuarioActual.getNombreCompleto());
        lblUsuario.setFont(new Font("Arial", Font.PLAIN, 12));
        lblUsuario.setForeground(new Color(174, 214, 241));

        JPanel panelInfo = new JPanel();
        panelInfo.setOpaque(false);
        panelInfo.setLayout(new BoxLayout(panelInfo, BoxLayout.Y_AXIS));
        panelInfo.add(lblTitulo);
        panelInfo.add(Box.createVerticalStrut(4));
        panelInfo.add(lblUsuario);
        panelHeader.add(panelInfo, BorderLayout.WEST);

        // ── Contenido ───────────────────────────────────────────────
        JPanel panelContenido = new JPanel();
        panelContenido.setBackground(C_FONDO);
        panelContenido.setLayout(new GridBagLayout());
        panelContenido.setBorder(new EmptyBorder(40, 60, 40, 60));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(12, 0, 12, 0);
        gbc.gridx = 0;
        gbc.weightx = 1.0;

        JLabel lblBienvenida = new JLabel("¿Qué deseas gestionar hoy?");
        lblBienvenida.setFont(new Font("Arial", Font.PLAIN, 14));
        lblBienvenida.setForeground(new Color(52, 73, 94));
        lblBienvenida.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridy = 0;
        panelContenido.add(lblBienvenida, gbc);

        gbc.gridy = 1;
        panelContenido.add(Box.createVerticalStrut(10), gbc);

        // Botón Gestión de Productos
        JButton btnProductos = crearBoton("📦  Gestión de Productos",
                "Registrar, editar, eliminar y buscar productos", C_AZUL, C_HOVER);
        gbc.gridy = 2;
        panelContenido.add(btnProductos, gbc);

        // Botón Reportes
        JButton btnReportes = crearBoton("📊  Reportes",
                "Ver e imprimir listado de productos", new Color(39, 116, 174), new Color(41, 128, 185));
        gbc.gridy = 3;
        panelContenido.add(btnReportes, gbc);

        // Botón Cerrar Sesión
        JButton btnSalir = crearBoton("🚪  Cerrar Sesión",
                "Salir del sistema", C_ROJO, C_ROJO_H);
        gbc.gridy = 4;
        panelContenido.add(btnSalir, gbc);

        // ── Acciones ────────────────────────────────────────────────
        btnProductos.addActionListener(e -> {
            new ProductoFrame(usuarioActual);
            setVisible(false);
        });

        btnReportes.addActionListener(e -> {
            new ReporteFrame(usuarioActual);
            setVisible(false);
        });

        btnSalir.addActionListener(e -> {
            int resp = JOptionPane.showConfirmDialog(this,
                "¿Estás seguro de que deseas cerrar sesión?",
                "Cerrar Sesión", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (resp == JOptionPane.YES_OPTION) {
                dispose();
                new LoginFrame();
            }
        });

        // ── Footer ──────────────────────────────────────────────────
        JPanel panelFooter = new JPanel();
        panelFooter.setBackground(new Color(189, 195, 199));
        panelFooter.setBorder(new EmptyBorder(6, 0, 6, 0));
        JLabel lblFooter = new JLabel("ValleTech © 2024");
        lblFooter.setFont(new Font("Arial", Font.PLAIN, 10));
        lblFooter.setForeground(new Color(85, 85, 85));
        panelFooter.add(lblFooter);

        panelPrincipal.add(panelHeader, BorderLayout.NORTH);
        panelPrincipal.add(panelContenido, BorderLayout.CENTER);
        panelPrincipal.add(panelFooter, BorderLayout.SOUTH);

        setContentPane(panelPrincipal);
        setVisible(true);
    }

    private JButton crearBoton(String texto, String tooltip, Color fondo, Color hover) {
        JButton btn = new JButton("<html><div style='text-align:left;padding:4px 6px'>" + texto + "</div></html>");
        btn.setFont(new Font("Arial", Font.BOLD, 14));
        btn.setBackground(fondo);
        btn.setForeground(C_BLANCO);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setPreferredSize(new Dimension(360, 62));
        btn.setToolTipText(tooltip);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e)  { btn.setBackground(hover); }
            public void mouseExited(MouseEvent e)   { btn.setBackground(fondo); }
        });
        return btn;
    }
}
