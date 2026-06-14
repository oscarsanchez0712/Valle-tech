package com.valletech.ui;

import com.valletech.dao.ProductoDAO;
import com.valletech.model.Producto;
import com.valletech.model.Usuario;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class ProductoFrame extends JFrame {

    private final Usuario usuarioActual;
    private final ProductoDAO dao = new ProductoDAO();

    private JTextField txtId, txtNombre, txtCategoria, txtPrecio, txtStock, txtBuscar;
    private JTable tabla;
    private DefaultTableModel modeloTabla;

    private static final Color C_AZUL    = new Color(26, 82, 118);
    private static final Color C_VERDE   = new Color(39, 174, 96);
    private static final Color C_ROJO    = new Color(192, 57, 43);
    private static final Color C_NARANJA = new Color(230, 126, 34);
    private static final Color C_GRIS    = new Color(127, 140, 141);
    private static final Color C_BLANCO  = Color.WHITE;
    private static final Color C_FONDO   = new Color(236, 240, 241);

    public ProductoFrame(Usuario usuario) {
        this.usuarioActual = usuario;
        initUI();
        cargarTabla(dao.listar());
    }

    private void initUI() {
        setTitle("ValleTech - Gestión de Productos");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setSize(950, 680);
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

        JLabel lblTitulo = new JLabel("Gestión de Productos");
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

        // ── Formulario con GridLayout limpio ────────────────────────
        JPanel panelForm = new JPanel(new GridBagLayout());
        panelForm.setBackground(C_BLANCO);
        panelForm.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(189, 195, 199)),
                new EmptyBorder(18, 24, 18, 24)
        ));

        GridBagConstraints g = new GridBagConstraints();
        g.fill = GridBagConstraints.HORIZONTAL;
        g.insets = new Insets(4, 8, 4, 8);

        // ── Fila 0: etiquetas ──────────────────────────────────────
        g.gridy = 0; g.ipady = 0;
        addLabel(panelForm, g, "ID",          0);
        addLabel(panelForm, g, "Nombre",      1);
        addLabel(panelForm, g, "Categoría",   2);
        addLabel(panelForm, g, "Precio (S/)", 3);
        addLabel(panelForm, g, "Stock",       4);

        // ── Fila 1: campos ─────────────────────────────────────────
        txtId        = campo(70);  txtId.setEditable(false); txtId.setBackground(new Color(245,245,245));
        txtNombre    = campo(200);
        txtCategoria = campo(150);
        txtPrecio    = campo(100);
        txtStock     = campo(80);

        g.gridy = 1; g.ipady = 6;
        addField(panelForm, g, txtId,        0, 70);
        addField(panelForm, g, txtNombre,    1, 200);
        addField(panelForm, g, txtCategoria, 2, 150);
        addField(panelForm, g, txtPrecio,    3, 100);
        addField(panelForm, g, txtStock,     4, 80);

        // ── Fila 2: botones ────────────────────────────────────────
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 0));
        panelBotones.setBackground(C_BLANCO);

        JButton btnNuevo     = boton("Nuevo",     C_AZUL);
        JButton btnGuardar   = boton("Guardar",   C_VERDE);
        JButton btnModificar = boton("Modificar", C_NARANJA);
        JButton btnEliminar  = boton("Eliminar",  C_ROJO);
        JButton btnLimpiar   = boton("Limpiar",   C_GRIS);

        panelBotones.add(btnNuevo);
        panelBotones.add(btnGuardar);
        panelBotones.add(btnModificar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnLimpiar);

        g.gridy = 2; g.gridx = 0; g.gridwidth = 5; g.ipady = 0;
        g.insets = new Insets(14, 8, 4, 8);
        panelForm.add(panelBotones, g);
        g.gridwidth = 1;

        // ── Buscador ────────────────────────────────────────────────
        JPanel panelBuscar = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 6));
        panelBuscar.setBackground(C_FONDO);
        panelBuscar.setBorder(new EmptyBorder(6, 12, 2, 12));

        JLabel lblB = new JLabel("Buscar:");
        lblB.setFont(new Font("Arial", Font.BOLD, 12));
        txtBuscar = new JTextField(22);
        txtBuscar.setFont(new Font("Arial", Font.PLAIN, 12));
        txtBuscar.setPreferredSize(new Dimension(220, 30));

        JButton btnBuscar = boton("Buscar",   C_AZUL);
        JButton btnTodos  = boton("Ver Todos", C_GRIS);

        panelBuscar.add(lblB);
        panelBuscar.add(txtBuscar);
        panelBuscar.add(btnBuscar);
        panelBuscar.add(btnTodos);

        // ── Tabla ───────────────────────────────────────────────────
        String[] cols = {"ID", "Nombre", "Categoría", "Precio (S/)", "Stock"};
        modeloTabla = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        tabla = new JTable(modeloTabla);
        tabla.setFont(new Font("Arial", Font.PLAIN, 12));
        tabla.setRowHeight(28);
        tabla.setSelectionBackground(new Color(174, 214, 241));
        tabla.setGridColor(new Color(189, 195, 199));

        JTableHeader header = tabla.getTableHeader();
        header.setBackground(C_AZUL);
        header.setForeground(C_BLANCO);
        header.setFont(new Font("Arial", Font.BOLD, 12));
        header.setPreferredSize(new Dimension(0, 32));

        int[] anchos = {55, 250, 160, 110, 80};
        for (int i = 0; i < anchos.length; i++)
            tabla.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(189, 195, 199)));

        JPanel panelTabla = new JPanel(new BorderLayout(0, 4));
        panelTabla.setBackground(C_FONDO);
        panelTabla.setBorder(new EmptyBorder(0, 12, 12, 12));
        panelTabla.add(panelBuscar, BorderLayout.NORTH);
        panelTabla.add(scroll, BorderLayout.CENTER);

        // ── Ensamblado ──────────────────────────────────────────────
        panelPrincipal.add(panelHeader, BorderLayout.NORTH);
        panelPrincipal.add(panelForm,   BorderLayout.CENTER);
        panelPrincipal.add(panelTabla,  BorderLayout.SOUTH);
        panelTabla.setPreferredSize(new Dimension(950, 340));

        setContentPane(panelPrincipal);

        // ── Eventos ─────────────────────────────────────────────────
        btnNuevo.addActionListener(e -> limpiar());
        btnGuardar.addActionListener(e -> guardar());
        btnModificar.addActionListener(e -> modificar());
        btnEliminar.addActionListener(e -> eliminar());
        btnLimpiar.addActionListener(e -> limpiar());
        btnBuscar.addActionListener(e -> {
            String t = txtBuscar.getText().trim();
            if (!t.isEmpty()) cargarTabla(dao.buscar(t));
        });
        txtBuscar.addActionListener(e -> btnBuscar.doClick());
        btnTodos.addActionListener(e -> { txtBuscar.setText(""); cargarTabla(dao.listar()); });
        tabla.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) { seleccionar(); }
        });

        setVisible(true);
    }

    // ── Helpers UI ───────────────────────────────────────────────
    private void addLabel(JPanel p, GridBagConstraints g, String texto, int col) {
        g.gridx = col;
        JLabel lbl = new JLabel(texto);
        lbl.setFont(new Font("Arial", Font.BOLD, 11));
        lbl.setForeground(new Color(52, 73, 94));
        p.add(lbl, g);
    }

    private void addField(JPanel p, GridBagConstraints g, JTextField tf, int col, int w) {
        g.gridx = col;
        tf.setPreferredSize(new Dimension(w, 32));
        p.add(tf, g);
    }

    private JTextField campo(int width) {
        JTextField tf = new JTextField();
        tf.setFont(new Font("Arial", Font.PLAIN, 12));
        tf.setPreferredSize(new Dimension(width, 32));
        tf.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(189, 195, 199)),
                new EmptyBorder(3, 7, 3, 7)
        ));
        return tf;
    }

    private JButton boton(String texto, Color color) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Arial", Font.BOLD, 11));
        btn.setBackground(color);
        btn.setForeground(C_BLANCO);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setPreferredSize(new Dimension(100, 32));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }

    // ── Lógica ──────────────────────────────────────────────────
    private void cargarTabla(List<Producto> lista) {
        modeloTabla.setRowCount(0);
        for (Producto p : lista)
            modeloTabla.addRow(new Object[]{
                    p.getId(), p.getNombre(), p.getCategoria(),
                    String.format("%.2f", p.getPrecio()), p.getStock()
            });
    }

    private void seleccionar() {
        int fila = tabla.getSelectedRow();
        if (fila < 0) return;
        txtId.setText(modeloTabla.getValueAt(fila, 0).toString());
        txtNombre.setText(modeloTabla.getValueAt(fila, 1).toString());
        txtCategoria.setText(modeloTabla.getValueAt(fila, 2).toString());
        txtPrecio.setText(modeloTabla.getValueAt(fila, 3).toString());
        txtStock.setText(modeloTabla.getValueAt(fila, 4).toString());
    }

    private void guardar() {
        try {
            Producto p = datosFormulario();
            if (dao.registrar(p)) {
                JOptionPane.showMessageDialog(this, "Producto registrado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                limpiar(); cargarTabla(dao.listar());
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo registrar.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Datos inválidos", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void modificar() {
        if (txtId.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Selecciona un producto de la tabla.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            Producto p = datosFormulario();
            p.setId(Integer.parseInt(txtId.getText().trim()));
            if (dao.modificar(p)) {
                JOptionPane.showMessageDialog(this, "Producto modificado.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                limpiar(); cargarTabla(dao.listar());
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo modificar.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Datos inválidos", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void eliminar() {
        if (txtId.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Selecciona un producto de la tabla.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int r = JOptionPane.showConfirmDialog(this,
                "¿Eliminar el producto ID " + txtId.getText() + "?",
                "Confirmar", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (r == JOptionPane.YES_OPTION) {
            if (dao.eliminar(Integer.parseInt(txtId.getText().trim()))) {
                JOptionPane.showMessageDialog(this, "Producto eliminado.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                limpiar(); cargarTabla(dao.listar());
            }
        }
    }

    private Producto datosFormulario() {
        String nombre    = txtNombre.getText().trim();
        String categoria = txtCategoria.getText().trim();
        String precioStr = txtPrecio.getText().trim();
        String stockStr  = txtStock.getText().trim();

        if (nombre.isEmpty() || categoria.isEmpty() || precioStr.isEmpty() || stockStr.isEmpty())
            throw new IllegalArgumentException("Todos los campos son obligatorios.");

        double precio;
        int stock;
        try { precio = Double.parseDouble(precioStr); }
        catch (NumberFormatException e) { throw new IllegalArgumentException("El precio debe ser un número válido."); }
        try { stock = Integer.parseInt(stockStr); }
        catch (NumberFormatException e) { throw new IllegalArgumentException("El stock debe ser un número entero."); }
        if (precio < 0) throw new IllegalArgumentException("El precio no puede ser negativo.");
        if (stock  < 0) throw new IllegalArgumentException("El stock no puede ser negativo.");

        return new Producto(nombre, categoria, precio, stock);
    }

    private void limpiar() {
        txtId.setText(""); txtNombre.setText(""); txtCategoria.setText("");
        txtPrecio.setText(""); txtStock.setText("");
        tabla.clearSelection(); txtNombre.requestFocus();
    }

    private void volver() {
        dispose();
        new DashboardFrame(usuarioActual).setVisible(true);
    }
}