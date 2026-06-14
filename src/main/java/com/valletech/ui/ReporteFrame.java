package com.valletech.ui;

import com.valletech.dao.ProductoDAO;
import com.valletech.model.Producto;
import com.valletech.model.Usuario;
import com.valletech.util.ReporteUtil;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ReporteFrame extends JFrame {

    private final Usuario usuarioActual;
    private final ProductoDAO dao = new ProductoDAO();
    private List<Producto> listaActual;

    private JTable tabla;
    private DefaultTableModel modeloTabla;
    private JLabel lblTotal, lblValor, lblEstado;

    private static final Color C_AZUL   = new Color(26, 82, 118);
    private static final Color C_VERDE  = new Color(39, 174, 96);
    private static final Color C_BLANCO = Color.WHITE;
    private static final Color C_FONDO  = new Color(236, 240, 241);

    public ReporteFrame(Usuario usuario) {
        this.usuarioActual = usuario;
        initUI();
        cargarReporte();
    }

    private void initUI() {
        setTitle("ValleTech - Reportes");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setSize(860, 600);
        setLocationRelativeTo(null);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) { volver(); }
        });

        JPanel panelPrincipal = new JPanel(new BorderLayout(0, 0));
        panelPrincipal.setBackground(C_FONDO);

        // ── Header ─────────────────────────────────────────────────
        JPanel panelHeader = new JPanel(new BorderLayout());
        panelHeader.setBackground(C_AZUL);
        panelHeader.setBorder(new EmptyBorder(14, 20, 14, 20));

        JLabel lblTitulo = new JLabel("📊  Reporte de Productos");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitulo.setForeground(C_BLANCO);

        JButton btnVolver = new JButton("← Volver");
        btnVolver.setFont(new Font("Arial", Font.BOLD, 11));
        btnVolver.setBackground(new Color(52, 73, 94));
        btnVolver.setForeground(C_BLANCO);
        btnVolver.setFocusPainted(false);
        btnVolver.setBorderPainted(false);
        btnVolver.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnVolver.addActionListener(e -> volver());

        panelHeader.add(lblTitulo, BorderLayout.WEST);
        panelHeader.add(btnVolver, BorderLayout.EAST);

        // ── Barra de herramientas ───────────────────────────────────
        JPanel panelToolbar = new JPanel(new BorderLayout());
        panelToolbar.setBackground(C_BLANCO);
        panelToolbar.setBorder(new EmptyBorder(10, 16, 10, 16));

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        panelBotones.setBackground(C_BLANCO);

        JButton btnActualizar = boton("🔄 Actualizar", C_AZUL);
        JButton btnExportarPDF = boton("📥 Exportar PDF", C_VERDE);

        panelBotones.add(btnActualizar);
        panelBotones.add(btnExportarPDF);

        JPanel panelFecha = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelFecha.setBackground(C_BLANCO);
        String ahora = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
        JLabel lblFecha = new JLabel("Fecha: " + ahora);
        lblFecha.setFont(new Font("Arial", Font.PLAIN, 11));
        lblFecha.setForeground(Color.GRAY);
        panelFecha.add(lblFecha);

        panelToolbar.add(panelBotones, BorderLayout.WEST);
        panelToolbar.add(panelFecha, BorderLayout.EAST);

        // ── Tabla ───────────────────────────────────────────────────
        String[] columnas = {"ID", "Nombre del Producto", "Categoría", "Precio Unit. (S/)", "Stock", "Valor Total (S/)"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        tabla = new JTable(modeloTabla);
        tabla.setFont(new Font("Arial", Font.PLAIN, 12));
        tabla.setRowHeight(28);
        tabla.setSelectionBackground(new Color(174, 214, 241));
        tabla.setGridColor(new Color(189, 195, 199));
        tabla.setShowGrid(true);

        JTableHeader header = tabla.getTableHeader();
        header.setBackground(C_AZUL);
        header.setForeground(C_BLANCO);
        header.setFont(new Font("Arial", Font.BOLD, 12));
        header.setPreferredSize(new Dimension(0, 32));

        int[] anchos = {50, 220, 130, 120, 80, 130};
        for (int i = 0; i < anchos.length; i++)
            tabla.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(189, 195, 199)));

        // ── Panel resumen ───────────────────────────────────────────
        JPanel panelResumen = new JPanel(new GridLayout(1, 3, 10, 0));
        panelResumen.setBackground(C_FONDO);
        panelResumen.setBorder(new EmptyBorder(10, 16, 10, 16));

        lblTotal = new JLabel("Total productos: -");
        lblTotal.setFont(new Font("Arial", Font.BOLD, 13));
        lblTotal.setForeground(C_AZUL);

        lblValor = new JLabel("Valor inventario: -");
        lblValor.setFont(new Font("Arial", Font.BOLD, 13));
        lblValor.setForeground(new Color(39, 116, 174));

        lblEstado = new JLabel("");
        lblEstado.setFont(new Font("Arial", Font.PLAIN, 11));
        lblEstado.setForeground(C_VERDE);
        lblEstado.setHorizontalAlignment(SwingConstants.RIGHT);

        panelResumen.add(lblTotal);
        panelResumen.add(lblValor);
        panelResumen.add(lblEstado);

        // ── Ensamblado ──────────────────────────────────────────────
        JPanel panelCentro = new JPanel(new BorderLayout(0, 4));
        panelCentro.setBackground(C_FONDO);
        panelCentro.setBorder(new EmptyBorder(6, 12, 0, 12));
        panelCentro.add(scroll, BorderLayout.CENTER);

        panelPrincipal.add(panelHeader,  BorderLayout.NORTH);
        panelPrincipal.add(panelToolbar, BorderLayout.CENTER);
        panelPrincipal.add(panelCentro,  BorderLayout.CENTER);

        // Usamos un panel norte para toolbar y otro sur para resumen
        JPanel panelNorte = new JPanel(new BorderLayout());
        panelNorte.add(panelHeader, BorderLayout.NORTH);
        panelNorte.add(panelToolbar, BorderLayout.SOUTH);

        panelPrincipal.add(panelNorte, BorderLayout.NORTH);
        panelPrincipal.add(panelCentro, BorderLayout.CENTER);
        panelPrincipal.add(panelResumen, BorderLayout.SOUTH);

        setContentPane(panelPrincipal);

        // ── Eventos ─────────────────────────────────────────────────
        btnActualizar.addActionListener(e -> cargarReporte());

        btnExportarPDF.addActionListener(e -> exportarPDF());
    }

    private void cargarReporte() {
        listaActual = dao.listar();
        modeloTabla.setRowCount(0);

        double valorTotal = 0;
        for (Producto p : listaActual) {
            double vt = p.getPrecio() * p.getStock();
            valorTotal += vt;
            modeloTabla.addRow(new Object[]{
                p.getId(),
                p.getNombre(),
                p.getCategoria(),
                String.format("%.2f", p.getPrecio()),
                p.getStock(),
                String.format("%.2f", vt)
            });
        }

        lblTotal.setText("Total productos: " + listaActual.size());
        lblValor.setText(String.format("Valor inventario: S/ %.2f", valorTotal));
        lblEstado.setText("✓ Datos cargados desde Docker MySQL");
    }

    private void exportarPDF() {
        if (listaActual == null || listaActual.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay datos para exportar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Guardar Reporte PDF");
        String nombre = "Reporte_Productos_" +
            LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")) + ".pdf";
        chooser.setSelectedFile(new File(System.getProperty("user.home"), nombre));

        if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            String ruta = chooser.getSelectedFile().getAbsolutePath();
            if (!ruta.toLowerCase().endsWith(".pdf")) ruta += ".pdf";

            final String rutaFinal = ruta;
            lblEstado.setText("Generando PDF...");

            SwingWorker<String, Void> worker = new SwingWorker<>() {
                @Override
                protected String doInBackground() throws Exception {
                    return ReporteUtil.generarReporte(listaActual, rutaFinal);
                }

                @Override
                protected void done() {
                    try {
                        String path = get();
                        lblEstado.setText("✓ PDF exportado correctamente");
                        int resp = JOptionPane.showConfirmDialog(ReporteFrame.this,
                            "PDF generado en:\n" + path + "\n\n¿Deseas abrir el archivo?",
                            "Exportación Exitosa", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
                        if (resp == JOptionPane.YES_OPTION) {
                            Desktop.getDesktop().open(new File(path));
                        }
                    } catch (Exception ex) {
                        lblEstado.setText("Error al generar PDF");
                        JOptionPane.showMessageDialog(ReporteFrame.this,
                            "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            };
            worker.execute();
        }
    }

    private JButton boton(String texto, Color color) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Arial", Font.BOLD, 12));
        btn.setBackground(color);
        btn.setForeground(C_BLANCO);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setPreferredSize(new Dimension(160, 34));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private void volver() {
        dispose();
        new DashboardFrame(usuarioActual).setVisible(true);
    }
}
