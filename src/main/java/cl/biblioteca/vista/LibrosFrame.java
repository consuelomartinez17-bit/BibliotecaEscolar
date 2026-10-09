package cl.biblioteca.vista;

import cl.biblioteca.dao.CategoriaDAO;
import cl.biblioteca.dao.LibroDAO;
import cl.biblioteca.modelo.Categoria;
import cl.biblioteca.modelo.Libro;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Ventana para gestionar los libros de la biblioteca.
 *
 * @author Consuelo Martinez
 */
public class LibrosFrame extends JFrame {

    private JTextField txtTitulo;
    private JTextField txtAutor;
    private JTextField txtIsbn;
    private JTextField txtEditorial;
    private JTextField txtStock;

    private JComboBox<Categoria> cmbCategoria;

    private JTable tablaLibros;
    private DefaultTableModel modeloTabla;

    private JButton btnAgregar;
    private JButton btnModificar;
    private JButton btnEliminar;
    private JButton btnLimpiar;

    private final LibroDAO libroDAO;
    private final CategoriaDAO categoriaDAO;

    /**
     * Constructor de la ventana de gestión de libros.
     */
    public LibrosFrame() {

        libroDAO = new LibroDAO();
        categoriaDAO = new CategoriaDAO();

        setTitle("Gestión de Libros");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        inicializarComponentes();
        cargarCategorias();
        cargarLibros();
    }

    /**
     * Inicializa los componentes de la ventana.
     */
    private void inicializarComponentes() {

        JPanel panelFormulario = new JPanel(
                new GridLayout(6, 2, 10, 10)
        );

        panelFormulario.setBorder(
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        );

        txtTitulo = new JTextField();
        txtAutor = new JTextField();
        txtIsbn = new JTextField();
        txtEditorial = new JTextField();
        txtStock = new JTextField();

        cmbCategoria = new JComboBox<>();
        cmbCategoria.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(
                    JList<?> list,
                    Object value,
                    int index,
                    boolean isSelected,
                    boolean cellHasFocus) {

                super.getListCellRendererComponent(
                        list,
                        value,
                        index,
                        isSelected,
                        cellHasFocus
                );

                if (value instanceof Categoria categoria) {
                    setText(categoria.getNombreCategoria());
                }

                return this;
            }
        });

        panelFormulario.add(new JLabel("Título:"));
        panelFormulario.add(txtTitulo);

        panelFormulario.add(new JLabel("Autor:"));
        panelFormulario.add(txtAutor);

        panelFormulario.add(new JLabel("ISBN:"));
        panelFormulario.add(txtIsbn);

        panelFormulario.add(new JLabel("Editorial:"));
        panelFormulario.add(txtEditorial);

        panelFormulario.add(new JLabel("Stock:"));
        panelFormulario.add(txtStock);

        panelFormulario.add(new JLabel("Categoría:"));
        panelFormulario.add(cmbCategoria);

        JPanel panelBotones = new JPanel();

        btnAgregar = new JButton("Agregar");
        btnModificar = new JButton("Modificar");
        btnEliminar = new JButton("Eliminar");
        btnLimpiar = new JButton("Limpiar");

        panelBotones.add(btnAgregar);
        panelBotones.add(btnModificar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnLimpiar);

        modeloTabla = new DefaultTableModel(
                new Object[]{
                        "ID", "Título", "Autor", "ISBN",
                        "Editorial", "Stock", "Categoría"
                },
                0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaLibros = new JTable(modeloTabla);

        JScrollPane scrollTabla = new JScrollPane(tablaLibros);

        setLayout(new BorderLayout());

        add(panelFormulario, BorderLayout.NORTH);
        add(scrollTabla, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);

        btnAgregar.addActionListener(e -> agregarLibro());
        btnModificar.addActionListener(e -> modificarLibro());
        btnEliminar.addActionListener(e -> eliminarLibro());
        btnLimpiar.addActionListener(e -> limpiarFormulario());

        tablaLibros.getSelectionModel().addListSelectionListener(e -> {

            if (!e.getValueIsAdjusting()) {
                cargarLibroSeleccionado();
            }
        });
    }

    /**
     * Carga las categorías disponibles en el combo.
     */
    private void cargarCategorias() {

        cmbCategoria.removeAllItems();

        List<Categoria> categorias =
                categoriaDAO.listarCategorias();

        for (Categoria categoria : categorias) {
            cmbCategoria.addItem(categoria);
        }
    }

    /**
     * Carga los libros registrados en la tabla.
     */
    private void cargarLibros() {

        modeloTabla.setRowCount(0);

        List<Libro> libros =
                libroDAO.listarLibros();

        for (Libro libro : libros) {

            modeloTabla.addRow(new Object[]{
                    libro.getIdLibro(),
                    libro.getTituloLibro(),
                    libro.getAutorLibro(),
                    libro.getIsbn(),
                    libro.getEditorial(),
                    libro.getStock(),
                    libro.getCategoria().getNombreCategoria()
            });
        }
    }

    /**
     * Agrega un nuevo libro.
     */
    private void agregarLibro() {

        try {

            String titulo =
                    txtTitulo.getText().trim();

            String autor =
                    txtAutor.getText().trim();

            String isbn =
                    txtIsbn.getText().trim();

            String editorial =
                    txtEditorial.getText().trim();

            int stock =
                    Integer.parseInt(
                            txtStock.getText().trim()
                    );

            if (!validarStock(stock)) {
                return;
            }

            Categoria categoria =
                    (Categoria) cmbCategoria.getSelectedItem();

            Libro libro = new Libro(
                    0,
                    titulo,
                    autor,
                    isbn,
                    editorial,
                    stock,
                    categoria
            );

            if (libroDAO.crearLibro(libro)) {

                JOptionPane.showMessageDialog(
                        this,
                        "Libro agregado correctamente."
                );

                cargarLibros();
                limpiarFormulario();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "No fue posible agregar el libro."
                );
            }

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "El stock debe ser un número válido."
            );
        }
    }

    /**
     * Modifica los datos de un libro existente.
     */
    private void modificarLibro() {

        int fila = tablaLibros.getSelectedRow();

        if (fila < 0) {

            JOptionPane.showMessageDialog(
                    this,
                    "Seleccione un libro de la tabla."
            );

            return;
        }

        try {

            int idLibro = Integer.parseInt(
                    modeloTabla.getValueAt(fila, 0).toString()
            );

            String titulo =
                    txtTitulo.getText().trim();

            String autor =
                    txtAutor.getText().trim();

            String isbn =
                    txtIsbn.getText().trim();

            String editorial =
                    txtEditorial.getText().trim();

            int stock =
                    Integer.parseInt(
                            txtStock.getText().trim()
                    );

            if (!validarStock(stock)) {
                return;
            }

            Categoria categoria =
                    (Categoria) cmbCategoria.getSelectedItem();

            Libro libro = new Libro(
                    idLibro,
                    titulo,
                    autor,
                    isbn,
                    editorial,
                    stock,
                    categoria
            );

            if (libroDAO.actualizarLibro(libro)) {

                JOptionPane.showMessageDialog(
                        this,
                        "Libro modificado correctamente."
                );

                cargarLibros();
                limpiarFormulario();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "No fue posible modificar el libro."
                );
            }

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "El stock debe ser un número válido."
            );
        }
    }

    /**
     * Elimina un libro seleccionado de la tabla.
     */
    private void eliminarLibro() {

        int fila = tablaLibros.getSelectedRow();

        if (fila < 0) {

            JOptionPane.showMessageDialog(
                    this,
                    "Seleccione un libro de la tabla."
            );

            return;
        }

        int idLibro = Integer.parseInt(
                modeloTabla.getValueAt(fila, 0).toString()
        );

        String tituloLibro =
                modeloTabla.getValueAt(fila, 1).toString();

        int confirmacion = JOptionPane.showConfirmDialog(
                this,
                "¿Está seguro de eliminar el libro \"" +
                        tituloLibro + "\"?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION
        );

        if (confirmacion == JOptionPane.YES_OPTION) {

            if (libroDAO.eliminarLibro(idLibro)) {

                JOptionPane.showMessageDialog(
                        this,
                        "Libro eliminado correctamente."
                );

                cargarLibros();
                limpiarFormulario();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "No fue posible eliminar el libro."
                );
            }
        }
    }

    /**
     * Carga los datos del libro seleccionado.
     */
    private void cargarLibroSeleccionado() {

        int fila = tablaLibros.getSelectedRow();

        if (fila >= 0) {

            txtTitulo.setText(
                    modeloTabla.getValueAt(fila, 1).toString()
            );

            txtAutor.setText(
                    modeloTabla.getValueAt(fila, 2).toString()
            );

            txtIsbn.setText(
                    modeloTabla.getValueAt(fila, 3).toString()
            );

            txtEditorial.setText(
                    modeloTabla.getValueAt(fila, 4).toString()
            );

            txtStock.setText(
                    modeloTabla.getValueAt(fila, 5).toString()
            );

            String nombreCategoria =
                    modeloTabla.getValueAt(fila, 6).toString();

            for (int i = 0;
                 i < cmbCategoria.getItemCount();
                 i++) {

                Categoria categoria =
                        cmbCategoria.getItemAt(i);

                if (categoria.getNombreCategoria()
                        .equals(nombreCategoria)) {

                    cmbCategoria.setSelectedIndex(i);
                    break;
                }
            }
        }
    }

    /**
     * Valida que el stock ingresado sea un valor válido.
     *
     * @param stock stock que se desea validar
     * @return true si el stock es válido
     */
    private boolean validarStock(int stock) {

        if (stock < 0) {

            JOptionPane.showMessageDialog(
                    this,
                    "El stock no puede ser negativo."
            );

            return false;
        }

        return true;
    }

    /**
     * Limpia los campos del formulario.
     */
    private void limpiarFormulario() {

        txtTitulo.setText("");
        txtAutor.setText("");
        txtIsbn.setText("");
        txtEditorial.setText("");
        txtStock.setText("");

        if (cmbCategoria.getItemCount() > 0) {
            cmbCategoria.setSelectedIndex(0);
        }

        tablaLibros.clearSelection();
    }
}