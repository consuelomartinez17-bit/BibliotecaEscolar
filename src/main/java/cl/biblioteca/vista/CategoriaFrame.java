package cl.biblioteca.vista;

import cl.biblioteca.dao.CategoriaDAO;
import cl.biblioteca.modelo.Categoria;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Ventana para gestionar las categorías de la biblioteca.
 *
 * @author Consuelo Martinez
 */
public class CategoriaFrame extends JFrame {

    private JTextField txtNombre;

    private JTable tablaCategorias;
    private DefaultTableModel modeloTabla;

    private final CategoriaDAO categoriaDAO;

    /**
     * Constructor de la ventana de gestión de categorías.
     */
    public CategoriaFrame() {

        categoriaDAO = new CategoriaDAO();

        setTitle("Gestión de Categorías");
        setSize(600, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        inicializarComponentes();
        cargarCategorias();
    }

    /**
     * Inicializa los componentes gráficos de la ventana.
     */
    private void inicializarComponentes() {

        JPanel panelFormulario = new JPanel(
                new GridLayout(1, 2, 10, 10)
        );

        panelFormulario.setBorder(
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        );

        txtNombre = new JTextField();

        panelFormulario.add(new JLabel("Nombre:"));
        panelFormulario.add(txtNombre);

        modeloTabla = new DefaultTableModel(
                new Object[]{"ID", "Nombre"},
                0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaCategorias = new JTable(modeloTabla);

        JScrollPane scrollTabla =
                new JScrollPane(tablaCategorias);

        JButton btnAgregar = new JButton("Agregar");
        JButton btnModificar = new JButton("Modificar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnLimpiar = new JButton("Limpiar");

        JPanel panelBotones = new JPanel();

        panelBotones.add(btnAgregar);
        panelBotones.add(btnModificar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnLimpiar);

        setLayout(new BorderLayout());

        add(panelFormulario, BorderLayout.NORTH);
        add(scrollTabla, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);

        btnAgregar.addActionListener(e -> agregarCategoria());
        btnModificar.addActionListener(e -> modificarCategoria());
        btnEliminar.addActionListener(e -> eliminarCategoria());
        btnLimpiar.addActionListener(e -> limpiarFormulario());

        tablaCategorias.getSelectionModel()
                .addListSelectionListener(e -> {

                    if (!e.getValueIsAdjusting()) {
                        cargarCategoriaSeleccionada();
                    }
                });
    }

    /**
     * Carga las categorías registradas en la tabla.
     */
    private void cargarCategorias() {

        modeloTabla.setRowCount(0);

        List<Categoria> categorias =
                categoriaDAO.listarCategorias();

        for (Categoria categoria : categorias) {

            modeloTabla.addRow(new Object[]{
                    categoria.getIdCategoria(),
                    categoria.getNombreCategoria()
            });
        }
    }

    /**
     * Registra una nueva categoría en la base de datos.
     */
    private void agregarCategoria() {

        String nombre = txtNombre.getText().trim();

        if (nombre.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Ingrese el nombre de la categoría."
            );
            return;
        }

        Categoria categoria = new Categoria(
                0,
                nombre
        );

        if (categoriaDAO.crearCategoria(categoria)) {

            JOptionPane.showMessageDialog(
                    this,
                    "Categoría agregada correctamente."
            );

            cargarCategorias();
            limpiarFormulario();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "No fue posible agregar la categoría."
            );
        }
    }

    /**
     * Modifica la categoría seleccionada.
     */
    private void modificarCategoria() {

        int fila = tablaCategorias.getSelectedRow();

        if (fila < 0) {

            JOptionPane.showMessageDialog(
                    this,
                    "Seleccione una categoría de la tabla."
            );
            return;
        }

        String nombre = txtNombre.getText().trim();

        if (nombre.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Ingrese el nombre de la categoría."
            );
            return;
        }

        int idCategoria = Integer.parseInt(
                modeloTabla.getValueAt(fila, 0).toString()
        );

        Categoria categoria = new Categoria(
                idCategoria,
                nombre
        );

        if (categoriaDAO.actualizarCategoria(categoria)) {

            JOptionPane.showMessageDialog(
                    this,
                    "Categoría modificada correctamente."
            );

            cargarCategorias();
            limpiarFormulario();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "No fue posible modificar la categoría."
            );
        }
    }

    /**
     * Elimina la categoría seleccionada después de solicitar confirmación.
     */
    private void eliminarCategoria() {

        int fila = tablaCategorias.getSelectedRow();

        if (fila < 0) {

            JOptionPane.showMessageDialog(
                    this,
                    "Seleccione una categoría de la tabla."
            );
            return;
        }

        int idCategoria = Integer.parseInt(
                modeloTabla.getValueAt(fila, 0).toString()
        );

        String nombre =
                modeloTabla.getValueAt(fila, 1).toString();

        int confirmacion = JOptionPane.showConfirmDialog(
                this,
                "¿Está seguro de eliminar la categoría \""
                        + nombre + "\"?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION
        );

        if (confirmacion == JOptionPane.YES_OPTION) {

            if (categoriaDAO.eliminarCategoria(idCategoria)) {

                JOptionPane.showMessageDialog(
                        this,
                        "Categoría eliminada correctamente."
                );

                cargarCategorias();
                limpiarFormulario();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "No fue posible eliminar la categoría."
                );
            }
        }
    }

    /**
     * Carga en el formulario los datos de la categoría seleccionada.
     */
    private void cargarCategoriaSeleccionada() {

        int fila = tablaCategorias.getSelectedRow();

        if (fila >= 0) {

            txtNombre.setText(
                    modeloTabla.getValueAt(fila, 1).toString()
            );
        }
    }

    /**
     * Limpia el campo del formulario y la selección de la tabla.
     */
    private void limpiarFormulario() {

        txtNombre.setText("");

        tablaCategorias.clearSelection();
    }
}