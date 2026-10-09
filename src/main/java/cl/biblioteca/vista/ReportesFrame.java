package cl.biblioteca.vista;

import cl.biblioteca.dao.PrestamoDAO;
import cl.biblioteca.modelo.Prestamo;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Ventana que muestra los reportes de la biblioteca.
 *
 * @author Consuelo Martinez
 */
public class ReportesFrame extends JFrame {

    private final PrestamoDAO prestamoDAO;

    private JTable tablaLibrosMasPrestados;
    private JTable tablaPrestamosActivos;

    private DefaultTableModel modeloLibrosMasPrestados;
    private DefaultTableModel modeloPrestamosActivos;

    private static final Color AZUL_PRINCIPAL =
            new Color(36, 78, 121);

    private static final Color AZUL_BOTON =
            new Color(52, 101, 153);

    private static final Color FONDO =
            new Color(242, 245, 248);

    private static final Color TEXTO =
            new Color(45, 55, 65);

    /**
     * Constructor de la ventana de reportes.
     */
    public ReportesFrame() {

        prestamoDAO = new PrestamoDAO();

        setTitle(
                "Trabajo Sumativo - Reportes"
        );

        setSize(1050, 650);
        setLocationRelativeTo(null);

        setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE
        );

        setResizable(false);

        inicializarComponentes();
        cargarReportes();
    }

    /**
     * Inicializa la interfaz.
     */
    private void inicializarComponentes() {

        JPanel panelPrincipal =
                new JPanel(new BorderLayout());

        panelPrincipal.setBackground(FONDO);

        // Encabezado
        JPanel panelEncabezado =
                new JPanel();

        panelEncabezado.setBackground(
                AZUL_PRINCIPAL
        );

        panelEncabezado.setPreferredSize(
                new Dimension(1050, 125)
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
                new JLabel("Reportes");

        lblTitulo.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        28
                )
        );

        lblTitulo.setForeground(
                Color.WHITE
        );

        lblTitulo.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        JLabel lblSubtitulo =
                new JLabel(
                        "Información de préstamos de la biblioteca"
                );

        lblSubtitulo.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        15
                )
        );

        lblSubtitulo.setForeground(
                Color.WHITE
        );

        lblSubtitulo.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        panelEncabezado.add(
                Box.createVerticalStrut(15)
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
                lblSubtitulo
        );

        panelPrincipal.add(
                panelEncabezado,
                BorderLayout.NORTH
        );

        // Panel central
        JPanel panelCentro =
                new JPanel(
                        new BorderLayout(15, 15)
                );

        panelCentro.setBackground(FONDO);

        panelCentro.setBorder(
                new EmptyBorder(
                        20,
                        20,
                        10,
                        20
                )
        );

        // Panel de reportes
        JPanel panelReportes =
                new JPanel(
                        new GridLayout(
                                1,
                                2,
                                15,
                                0
                        )
                );

        panelReportes.setBackground(FONDO);

        // -----------------------------
        // Libros más prestados
        // -----------------------------
        JPanel panelMasPrestados =
                crearPanelReporte(
                        "Libros más prestados"
                );

        modeloLibrosMasPrestados =
                new DefaultTableModel(
                        new Object[]{
                                "Libro",
                                "Cantidad de préstamos"
                        },
                        0
                ) {

                    @Override
                    public boolean isCellEditable(
                            int row,
                            int column) {

                        return false;
                    }
                };

        tablaLibrosMasPrestados =
                crearTabla(
                        modeloLibrosMasPrestados
                );

        panelMasPrestados.add(
                new JScrollPane(
                        tablaLibrosMasPrestados
                ),
                BorderLayout.CENTER
        );

        panelReportes.add(
                panelMasPrestados
        );

        // -----------------------------
        // Libros actualmente en préstamo
        // -----------------------------
        JPanel panelActivos =
                crearPanelReporte(
                        "Libros actualmente en préstamo"
                );

        modeloPrestamosActivos =
                new DefaultTableModel(
                        new Object[]{
                                "Estudiante",
                                "Libro",
                                "Fecha préstamo"
                        },
                        0
                ) {

                    @Override
                    public boolean isCellEditable(
                            int row,
                            int column) {

                        return false;
                    }
                };

        tablaPrestamosActivos =
                crearTabla(
                        modeloPrestamosActivos
                );

        panelActivos.add(
                new JScrollPane(
                        tablaPrestamosActivos
                ),
                BorderLayout.CENTER
        );

        panelReportes.add(
                panelActivos
        );

        panelCentro.add(
                panelReportes,
                BorderLayout.CENTER
        );

        // Botón actualizar
        JButton btnActualizar =
                crearBoton("Actualizar");

        btnActualizar.addActionListener(
                e -> cargarReportes()
        );

        JPanel panelBoton =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.CENTER
                        )
                );

        panelBoton.setBackground(FONDO);

        panelBoton.add(
                btnActualizar
        );

        panelCentro.add(
                panelBoton,
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

    /**
     * Crea un panel para cada reporte.
     */
    private JPanel crearPanelReporte(
            String titulo) {

        JPanel panel =
                new JPanel(
                        new BorderLayout(0, 8)
                );

        panel.setBackground(
                Color.WHITE
        );

        panel.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(205, 215, 225)
                        ),
                        new EmptyBorder(
                                10,
                                10,
                                10,
                                10
                        )
                )
        );

        JLabel lblTitulo =
                new JLabel(titulo);

        lblTitulo.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        15
                )
        );

        lblTitulo.setForeground(TEXTO);

        panel.add(
                lblTitulo,
                BorderLayout.NORTH
        );

        return panel;
    }

    /**
     * Configura el estilo de una tabla.
     */
    private JTable crearTabla(
            DefaultTableModel modelo) {

        JTable tabla =
                new JTable(modelo);

        tabla.setRowHeight(27);

        tabla.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        13
                )
        );

        tabla.getTableHeader()
                .setFont(
                        new Font(
                                "SansSerif",
                                Font.BOLD,
                                13
                        )
                );

        tabla.getTableHeader()
                .setBackground(
                        AZUL_PRINCIPAL
                );

        tabla.getTableHeader()
                .setForeground(
                        Color.WHITE
                );

        tabla.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        tabla.setFillsViewportHeight(
                true
        );

        return tabla;
    }

    /**
     * Crea un botón con el estilo del sistema.
     */
    private JButton crearBoton(
            String texto) {

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
                AZUL_BOTON
        );

        boton.setFocusPainted(false);

        boton.setMargin(
                new Insets(
                        9,
                        25,
                        9,
                        25
                )
        );

        return boton;
    }

    /**
     * Carga todos los reportes.
     */
    private void cargarReportes() {

        cargarLibrosMasPrestados();
        cargarPrestamosActivos();
    }

    /**
     * Carga el reporte de libros más prestados.
     */
    private void cargarLibrosMasPrestados() {

        modeloLibrosMasPrestados.setRowCount(0);

        List<Object[]> datos =
                prestamoDAO.listarLibrosMasPrestados();

        for (Object[] fila : datos) {

            modeloLibrosMasPrestados.addRow(
                    fila
            );
        }
    }

    /**
     * Carga el reporte de libros actualmente en préstamo.
     */
    private void cargarPrestamosActivos() {

        modeloPrestamosActivos.setRowCount(0);

        List<Prestamo> prestamos =
                prestamoDAO.listarPrestamosActivos();

        for (Prestamo prestamo : prestamos) {

            modeloPrestamosActivos.addRow(
                    new Object[]{
                            prestamo.getEstudiante()
                                    .getNombreEstudiante(),

                            prestamo.getLibro()
                                    .getTituloLibro(),

                            prestamo.getFechaPrestamo()
                    }
            );
        }
    }
}