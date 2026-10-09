package cl.biblioteca.vista;

import cl.biblioteca.dao.EstudianteDAO;
import cl.biblioteca.dao.PrestamoDAO;
import cl.biblioteca.modelo.Estudiante;
import cl.biblioteca.modelo.Prestamo;
import cl.biblioteca.modelo.Usuario;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Ventana de consultas para el estudiante.
 * Permite visualizar el historial de préstamos
 * del estudiante que inició sesión.
 *
 * @author Consuelo Martinez
 */
public class ConsultasFrame extends JFrame {

    private final Usuario usuarioActual;
    private final EstudianteDAO estudianteDAO;
    private final PrestamoDAO prestamoDAO;

    private Estudiante estudianteActual;

    private JTable tablaPrestamos;
    private DefaultTableModel modeloTabla;

    private static final Color AZUL_PRINCIPAL =
            new Color(36, 78, 121);

    private static final Color AZUL_BOTON =
            new Color(52, 101, 153);

    private static final Color FONDO =
            new Color(242, 245, 248);

    private static final Color TEXTO =
            new Color(45, 55, 65);

    /**
     * Constructor de la ventana de consultas.
     *
     * @param usuario usuario que inició sesión
     */
    public ConsultasFrame(Usuario usuario) {

        this.usuarioActual = usuario;

        estudianteDAO = new EstudianteDAO();
        prestamoDAO = new PrestamoDAO();

        estudianteActual =
                estudianteDAO.obtenerEstudiantePorRut(
                        usuarioActual.getRutUsuario()
                );

        configurarVentana();
        cargarPrestamos();
    }

    /**
     * Configura la ventana.
     */
    private void configurarVentana() {

        setTitle(
                "Trabajo Sumativo - Mis préstamos"
        );

        setSize(900, 560);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE
        );
        setResizable(false);

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
                new Dimension(900, 125)
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
                new JLabel("Mis préstamos");

        lblTitulo.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        27
                )
        );

        lblTitulo.setForeground(
                Color.WHITE
        );

        lblTitulo.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        JLabel lblEstudiante =
                new JLabel(
                        estudianteActual != null
                                ? estudianteActual.getNombreEstudiante()
                                : usuarioActual.getNombreUsuario()
                );

        lblEstudiante.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        15
                )
        );

        lblEstudiante.setForeground(
                Color.WHITE
        );

        lblEstudiante.setAlignmentX(
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
                lblEstudiante
        );

        panelPrincipal.add(
                panelEncabezado,
                BorderLayout.NORTH
        );

        // Panel central
        JPanel panelCentro =
                new JPanel(new BorderLayout());

        panelCentro.setBackground(FONDO);

        panelCentro.setBorder(
                new EmptyBorder(
                        20,
                        25,
                        10,
                        25
                )
        );

        JLabel lblDescripcion =
                new JLabel(
                        "Historial de préstamos",
                        SwingConstants.LEFT
                );

        lblDescripcion.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        16
                )
        );

        lblDescripcion.setForeground(TEXTO);

        lblDescripcion.setBorder(
                new EmptyBorder(
                        0,
                        0,
                        12,
                        0
                )
        );

        panelCentro.add(
                lblDescripcion,
                BorderLayout.NORTH
        );

        String[] columnas = {
                "ID",
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
                        AZUL_PRINCIPAL
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

        // Botón actualizar
        JButton btnActualizar =
                new JButton("Actualizar");

        btnActualizar.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        13
                )
        );

        btnActualizar.setForeground(
                Color.WHITE
        );

        btnActualizar.setBackground(
                AZUL_BOTON
        );

        btnActualizar.setFocusPainted(false);

        btnActualizar.setMargin(
                new Insets(
                        8,
                        25,
                        8,
                        25
                )
        );

        btnActualizar.addActionListener(
                e -> cargarPrestamos()
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
     * Carga el historial de préstamos del estudiante.
     */
    private void cargarPrestamos() {

        modeloTabla.setRowCount(0);

        if (estudianteActual == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "No se encontró un estudiante asociado al usuario.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }

        List<Prestamo> prestamos =
                prestamoDAO.listarPrestamosPorEstudiante(
                        estudianteActual.getIdEstudiante()
                );

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

                estado =
                        prestamo.estaAtrasado()
                                ? "Devuelto con atraso"
                                : "Devuelto a tiempo";

            } else {

                estado =
                        prestamo.estaAtrasado()
                                ? "Atrasado"
                                : "En plazo";
            }

            modeloTabla.addRow(
                    new Object[]{
                            prestamo.getIdPrestamo(),
                            prestamo.getLibro()
                                    .getTituloLibro(),
                            prestamo.getFechaPrestamo(),
                            fechaVencimiento,
                            fechaDevolucion,
                            estado
                    }
            );
        }
    }
}