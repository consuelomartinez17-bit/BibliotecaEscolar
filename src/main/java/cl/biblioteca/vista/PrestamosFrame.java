package cl.biblioteca.vista;

import cl.biblioteca.controlador.GestionPrestamos;
import cl.biblioteca.controlador.RegistrarDevolucionThread;
import cl.biblioteca.controlador.RegistrarPrestamoThread;
import cl.biblioteca.dao.EstudianteDAO;
import cl.biblioteca.dao.LibroDAO;
import cl.biblioteca.dao.PrestamoDAO;
import cl.biblioteca.modelo.Estudiante;
import cl.biblioteca.modelo.Libro;
import cl.biblioteca.modelo.Prestamo;
import cl.biblioteca.modelo.Usuario;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

/**
 * Ventana para gestionar préstamos y devoluciones.
 *
 * Los bibliotecarios pueden gestionar todos los préstamos.
 * Los estudiantes solamente pueden gestionar sus propios préstamos.
 */
public class PrestamosFrame extends JFrame {

    private final GestionPrestamos gestionPrestamos;
    private final EstudianteDAO estudianteDAO;
    private final LibroDAO libroDAO;
    private final PrestamoDAO prestamoDAO;

    private final Usuario usuarioActual;
    private final String modo;

    private Estudiante estudianteActual;

    private JComboBox<Estudiante> cmbEstudiante;
    private JComboBox<Libro> cmbLibro;

    private JTable tablaPrestamos;
    private DefaultTableModel modeloTabla;

    private JButton btnRegistrarPrestamo;
    private JButton btnRegistrarDevolucion;
    private JButton btnActualizar;

    /**
     * Constructor utilizado por el bibliotecario.
     */
    public PrestamosFrame() {

        usuarioActual = null;
        modo = "BIBLIOTECARIO";

        gestionPrestamos = new GestionPrestamos();
        estudianteDAO = new EstudianteDAO();
        libroDAO = new LibroDAO();
        prestamoDAO = new PrestamoDAO();

        configurarVentana();
        cargarDatos();
    }

    /**
     * Constructor utilizado por el estudiante.
     *
     * @param usuario usuario que inició sesión
     * @param modo operación que desea realizar
     */
    public PrestamosFrame(Usuario usuario, String modo) {

        this.usuarioActual = usuario;
        this.modo = modo;

        gestionPrestamos = new GestionPrestamos();
        estudianteDAO = new EstudianteDAO();
        libroDAO = new LibroDAO();
        prestamoDAO = new PrestamoDAO();

        estudianteActual =
                estudianteDAO.obtenerEstudiantePorRut(
                        usuario.getRutUsuario()
                );

        configurarVentana();
        cargarDatos();
    }

    /**
     * Configura la ventana según el tipo de usuario y operación.
     */
    private void configurarVentana() {

        String titulo;

        if (modo.equals("PRESTAMO")) {
            titulo = "Solicitar préstamo";

        } else if (modo.equals("DEVOLUCION")) {
            titulo = "Registrar devolución";

        } else {
            titulo = "Gestión de préstamos y devoluciones";
        }

        setTitle(
                "Trabajo Sumativo - " + titulo
        );

        setSize(950, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE
        );
        setResizable(false);

        JPanel panelPrincipal =
                new JPanel(new BorderLayout());

        panelPrincipal.setBackground(
                new Color(242, 245, 248)
        );

        // Encabezado
        JPanel panelEncabezado =
                new JPanel();

        panelEncabezado.setBackground(
                new Color(36, 78, 121)
        );

        panelEncabezado.setPreferredSize(
                new Dimension(950, 115)
        );

        panelEncabezado.setLayout(
                new BoxLayout(
                        panelEncabezado,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel lblActividad =
                new JLabel("TRABAJO SUMATIVO");

        lblActividad.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        13
                )
        );

        lblActividad.setForeground(
                Color.WHITE
        );

        lblActividad.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        JLabel lblTitulo =
                new JLabel(titulo);

        lblTitulo.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        26
                )
        );

        lblTitulo.setForeground(
                Color.WHITE
        );

        lblTitulo.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        String textoUsuario;

        if (usuarioActual != null) {
            textoUsuario =
                    usuarioActual.getNombreUsuario();
        } else {
            textoUsuario =
                    "Administración de biblioteca";
        }

        JLabel lblUsuario =
                new JLabel(textoUsuario);

        lblUsuario.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        15
                )
        );

        lblUsuario.setForeground(
                Color.WHITE
        );

        lblUsuario.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        panelEncabezado.add(
                Box.createVerticalStrut(14)
        );

        panelEncabezado.add(
                lblActividad
        );

        panelEncabezado.add(
                Box.createVerticalStrut(5)
        );

        panelEncabezado.add(
                lblTitulo
        );

        panelEncabezado.add(
                Box.createVerticalStrut(7)
        );

        panelEncabezado.add(
                lblUsuario
        );

        panelPrincipal.add(
                panelEncabezado,
                BorderLayout.NORTH
        );

        // Panel central
        JPanel panelCentro =
                new JPanel(new BorderLayout());

        panelCentro.setBackground(
                new Color(242, 245, 248)
        );

        panelCentro.setBorder(
                BorderFactory.createEmptyBorder(
                        20,
                        25,
                        10,
                        25
                )
        );

        // Datos del préstamo
        JPanel panelSuperior =
                new JPanel(new GridBagLayout());

        panelSuperior.setBackground(
                Color.WHITE
        );

        panelSuperior.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(210, 220, 230)
                        ),
                        BorderFactory.createEmptyBorder(
                                15,
                                20,
                                15,
                                20
                        )
                )
        );

        GridBagConstraints gbc =
                new GridBagConstraints();

        gbc.fill =
                GridBagConstraints.HORIZONTAL;

        gbc.insets =
                new Insets(
                        5,
                        8,
                        5,
                        8
                );

        gbc.weightx = 0;

        JLabel lblEstudiante =
                new JLabel("Estudiante:");

        lblEstudiante.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        13
                )
        );

        gbc.gridx = 0;
        gbc.gridy = 0;

        panelSuperior.add(
                lblEstudiante,
                gbc
        );

        cmbEstudiante =
                new JComboBox<>();

        cmbEstudiante.setPreferredSize(
                new Dimension(300, 35)
        );

        if (usuarioActual != null) {

            if (estudianteActual != null) {

                cmbEstudiante.addItem(
                        estudianteActual
                );

                cmbEstudiante.setSelectedItem(
                        estudianteActual
                );
            }

            cmbEstudiante.setEnabled(false);

        } else {

            cargarEstudiantes();
        }

        cmbEstudiante.setRenderer(
                new DefaultListCellRenderer() {

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

                        if (value instanceof Estudiante estudiante) {
                            setText(
                                    estudiante.getNombreEstudiante()
                            );
                        }

                        return this;
                    }
                }
        );

        gbc.gridx = 1;
        gbc.weightx = 1.0;

        panelSuperior.add(
                cmbEstudiante,
                gbc
        );

        if (modo.equals("PRESTAMO")
                || modo.equals("BIBLIOTECARIO")) {

            JLabel lblLibro =
                    new JLabel("Libro:");

            lblLibro.setFont(
                    new Font(
                            "SansSerif",
                            Font.BOLD,
                            13
                    )
            );

            gbc.gridx = 0;
            gbc.gridy = 1;
            gbc.weightx = 0;

            panelSuperior.add(
                    lblLibro,
                    gbc
            );

            cmbLibro =
                    new JComboBox<>();

            cmbLibro.setPreferredSize(
                    new Dimension(300, 35)
            );

            cmbLibro.setRenderer(
                    new DefaultListCellRenderer() {

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

                            if (value instanceof Libro libro) {
                                setText(
                                        libro.getTituloLibro()
                                                + " — Stock: "
                                                + libro.getStock()
                                );
                            }

                            return this;
                        }
                    }
            );

            cargarLibros();

            gbc.gridx = 1;
            gbc.weightx = 1.0;

            panelSuperior.add(
                    cmbLibro,
                    gbc
            );
        }

        panelCentro.add(
                panelSuperior,
                BorderLayout.NORTH
        );

        // Tabla
        String[] columnas = {
                "ID",
                "Estudiante",
                "Libro",
                "Fecha préstamo",
                "Fecha vencimiento",
                "Fecha devolución",
                "Estado"
        };

        modeloTabla =
                new DefaultTableModel(
                        columnas,
                        0
                ) {

                    @Override
                    public boolean isCellEditable(
                            int row,
                            int column) {

                        return false;
                    }
                };

        tablaPrestamos =
                new JTable(modeloTabla);

        tablaPrestamos.setRowHeight(28);

        tablaPrestamos.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        13
                )
        );

        tablaPrestamos.getTableHeader()
                .setFont(
                        new Font(
                                "SansSerif",
                                Font.BOLD,
                                13
                        )
                );

        tablaPrestamos.getTableHeader()
                .setBackground(
                        new Color(36, 78, 121)
                );

        tablaPrestamos.getTableHeader()
                .setForeground(
                        Color.WHITE
                );

        tablaPrestamos.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        tablaPrestamos.setFillsViewportHeight(
                true
        );

        JScrollPane scrollPane =
                new JScrollPane(
                        tablaPrestamos
                );

        scrollPane.setBorder(
                BorderFactory.createLineBorder(
                        new Color(205, 215, 225)
                )
        );

        panelCentro.add(
                scrollPane,
                BorderLayout.CENTER
        );

        // Botones
        JPanel panelBotones =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.CENTER,
                                12,
                                10
                        )
                );

        panelBotones.setBackground(
                new Color(242, 245, 248)
        );

        btnRegistrarPrestamo =
                crearBoton("Registrar préstamo");

        btnRegistrarDevolucion =
                crearBoton("Registrar devolución");

        btnActualizar =
                crearBoton("Actualizar");

        btnRegistrarPrestamo.addActionListener(
                e -> registrarPrestamo()
        );

        btnRegistrarDevolucion.addActionListener(
                e -> registrarDevolucion()
        );

        btnActualizar.addActionListener(
                e -> cargarDatos()
        );

        if (modo.equals("PRESTAMO")) {

            panelBotones.add(
                    btnRegistrarPrestamo
            );

        } else if (modo.equals("DEVOLUCION")) {

            panelBotones.add(
                    btnRegistrarDevolucion
            );

        } else {

            panelBotones.add(
                    btnRegistrarPrestamo
            );

            panelBotones.add(
                    btnRegistrarDevolucion
            );
        }

        panelBotones.add(
                btnActualizar
        );

        panelCentro.add(
                panelBotones,
                BorderLayout.SOUTH
        );

        panelPrincipal.add(
                panelCentro,
                BorderLayout.CENTER
        );

        // Pie
        JLabel lblPie =
                new JLabel(
                        "Desarrollo Orientado a Objetos II — 2026",
                        SwingConstants.CENTER
                );

        lblPie.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        11
                )
        );

        lblPie.setForeground(
                new Color(110, 120, 130)
        );

        lblPie.setBorder(
                new EmptyBorder(
                        0,
                        0,
                        10,
                        0
                )
        );

        panelPrincipal.add(
                lblPie,
                BorderLayout.SOUTH
        );

        setContentPane(panelPrincipal);
    }

    private JButton crearBoton(String texto) {

        JButton boton =
                new JButton(texto);

        boton.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        13
                )
        );

        boton.setForeground(
                Color.WHITE
        );

        boton.setBackground(
                new Color(52, 101, 153)
        );

        boton.setFocusPainted(false);

        boton.setMargin(
                new Insets(
                        9,
                        22,
                        9,
                        22
                )
        );

        return boton;
    }

    /**
     * Carga los estudiantes.
     */
    private void cargarEstudiantes() {

        cmbEstudiante.removeAllItems();

        List<Estudiante> estudiantes =
                estudianteDAO.listarEstudiantes();

        for (Estudiante estudiante : estudiantes) {
            cmbEstudiante.addItem(estudiante);
        }
    }

    /**
     * Carga todos los libros mostrando su stock disponible.
     */
    private void cargarLibros() {

        cmbLibro.removeAllItems();

        List<Libro> libros =
                libroDAO.listarLibros();

        for (Libro libro : libros) {
            cmbLibro.addItem(libro);
        }
    }

    /**
     * Carga los préstamos según el modo de la ventana.
     */
    private void cargarDatos() {

        modeloTabla.setRowCount(0);

        List<Prestamo> prestamos;

        /*
         * Estudiante.
         */
        if (usuarioActual != null) {

            if (estudianteActual == null) {

                JOptionPane.showMessageDialog(
                        this,
                        "No se encontró un estudiante asociado al usuario.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );

                return;
            }

            prestamos =
                    prestamoDAO.listarPrestamosPorEstudiante(
                            estudianteActual.getIdEstudiante()
                    );

            /*
             * En préstamos y devoluciones solamente
             * se muestran los préstamos activos.
             */
            if (modo.equals("PRESTAMO")
                    || modo.equals("DEVOLUCION")) {

                prestamos.removeIf(
                        Prestamo::isDevuelto
                );
            }

            /*
             * Bibliotecario.
             */
        } else {

            prestamos =
                    prestamoDAO.listarPrestamosActivos();
        }

        for (Prestamo prestamo : prestamos) {

            String fechaVencimiento =
                    prestamo.getFechaVencimiento()
                            .toString();

            String fechaDevolucion =
                    prestamo.getFechaDevolucion() != null
                            ? prestamo.getFechaDevolucion()
                            .toString()
                            : "-";

            String estado;

            if (prestamo.isDevuelto()) {

                estado = prestamo.estaAtrasado()
                        ? "Devuelto con atraso"
                        : "Devuelto a tiempo";

            } else {

                estado = prestamo.estaAtrasado()
                        ? "Atrasado"
                        : "En plazo";
            }

            modeloTabla.addRow(
                    new Object[]{
                            prestamo.getIdPrestamo(),
                            prestamo.getEstudiante()
                                    .getNombreEstudiante(),
                            prestamo.getLibro()
                                    .getTituloLibro(),
                            prestamo.getFechaPrestamo(),
                            fechaVencimiento,
                            fechaDevolucion,
                            estado
                    }
            );
        }

        if (modo.equals("PRESTAMO")
                || modo.equals("BIBLIOTECARIO")) {

            cargarLibros();
        }
    }

    /**
     * Registra un nuevo préstamo.
     */
    private void registrarPrestamo() {

        Estudiante estudiante;

        if (usuarioActual != null) {

            estudiante = estudianteActual;

        } else {

            estudiante =
                    (Estudiante)
                            cmbEstudiante.getSelectedItem();
        }

        Libro libro =
                (Libro)
                        cmbLibro.getSelectedItem();

        if (estudiante == null || libro == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Debe seleccionar un estudiante y un libro.",
                    "Datos incompletos",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        List<Prestamo> prestamosEstudiante =
                prestamoDAO.listarPrestamosPorEstudiante(
                        estudiante.getIdEstudiante());

        for (Prestamo prestamoActivo : prestamosEstudiante) {

            if (!prestamoActivo.isDevuelto()
                    && prestamoActivo.getLibro().getIdLibro()
                    == libro.getIdLibro()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Este estudiante ya tiene este libro en su poder. "
                                + "Debe devolverlo antes de solicitarlo nuevamente.",
                        "Préstamo no permitido",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }
        }

        Prestamo prestamo =
                new Prestamo(
                        0,
                        estudiante,
                        libro,
                        LocalDate.now(),
                        null,
                        false
                );

        btnRegistrarPrestamo.setEnabled(false);

        new RegistrarPrestamoThread(
                gestionPrestamos,
                prestamo,
                resultado -> {

                    btnRegistrarPrestamo.setEnabled(true);

                    if (resultado) {

                        JOptionPane.showMessageDialog(
                                this,
                                "Préstamo registrado correctamente.",
                                "Préstamo",
                                JOptionPane.INFORMATION_MESSAGE
                        );

                        cargarDatos();

                    } else {

                        JOptionPane.showMessageDialog(
                                this,
                                "No hay stock disponible para este libro.",
                                "Préstamo",
                                JOptionPane.WARNING_MESSAGE
                        );
                    }
                }
        ).start();
    }

    /**
     * Registra una devolución.
     */
    private void registrarDevolucion() {

        int filaSeleccionada =
                tablaPrestamos.getSelectedRow();

        if (filaSeleccionada == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Seleccione un préstamo de la tabla.",
                    "Devolución",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        int idPrestamo =
                (int)
                        modeloTabla.getValueAt(
                                filaSeleccionada,
                                0
                        );

        btnRegistrarDevolucion.setEnabled(false);

        new RegistrarDevolucionThread(
                gestionPrestamos,
                idPrestamo,
                resultado -> {

                    btnRegistrarDevolucion.setEnabled(true);

                    if (resultado) {

                        JOptionPane.showMessageDialog(
                                this,
                                "Devolución registrada correctamente.",
                                "Devolución",
                                JOptionPane.INFORMATION_MESSAGE
                        );

                        cargarDatos();

                    } else {

                        JOptionPane.showMessageDialog(
                                this,
                                "El préstamo ya fue devuelto o no existe.",
                                "Devolución",
                                JOptionPane.WARNING_MESSAGE
                        );
                    }
                }
        ).start();
    }
}