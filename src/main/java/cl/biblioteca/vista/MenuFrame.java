package cl.biblioteca.vista;

import cl.biblioteca.modelo.Usuario;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Menú principal del sistema.
 * Muestra las opciones correspondientes según
 * el rol del usuario que inició sesión.
 *
 * @author Consuelo Martinez
 */
public class MenuFrame extends JFrame {

    private final Usuario usuario;

    private static final Color AZUL_PRINCIPAL =
            new Color(36, 78, 121);

    private static final Color AZUL_BOTON =
            new Color(52, 101, 153);

    private static final Color FONDO =
            new Color(242, 245, 248);

    private static final Color TEXTO =
            new Color(45, 55, 65);

    public MenuFrame(Usuario usuario) {

        this.usuario = usuario;

        setTitle("Trabajo Sumativo - Biblioteca Escolar");
        setSize(850, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        inicializarComponentes();
    }

    /**
     * Inicializa los componentes del menú.
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
                new Dimension(850, 145)
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

        lblActividad.setForeground(Color.WHITE);

        lblActividad.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        JLabel lblTitulo =
                new JLabel("Biblioteca Escolar");

        lblTitulo.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        28
                )
        );

        lblTitulo.setForeground(Color.WHITE);

        lblTitulo.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        JLabel lblBienvenida =
                new JLabel(
                        "Bienvenido/a, "
                                + usuario.getNombreUsuario()
                );

        lblBienvenida.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        15
                )
        );

        lblBienvenida.setForeground(Color.WHITE);

        lblBienvenida.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        panelEncabezado.add(
                Box.createVerticalStrut(18)
        );

        panelEncabezado.add(lblActividad);

        panelEncabezado.add(
                Box.createVerticalStrut(5)
        );

        panelEncabezado.add(lblTitulo);

        panelEncabezado.add(
                Box.createVerticalStrut(8)
        );

        panelEncabezado.add(lblBienvenida);

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
                        25,
                        45,
                        25,
                        45
                )
        );

        JLabel lblRol =
                new JLabel(
                        "Rol: "
                                + usuario.getRolUsuario(),
                        SwingConstants.CENTER
                );

        lblRol.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        14
                )
        );

        lblRol.setForeground(TEXTO);

        lblRol.setBorder(
                new EmptyBorder(
                        0,
                        0,
                        15,
                        0
                )
        );

        panelCentro.add(
                lblRol,
                BorderLayout.NORTH
        );

        JPanel panelBotones;

        if (usuario.getRolUsuario()
                .equalsIgnoreCase("bibliotecario")) {

            // Menú del bibliotecario
            panelBotones =
                    new JPanel(
                            new GridLayout(
                                    3,
                                    2,
                                    18,
                                    18
                            )
                    );

            panelBotones.setBackground(FONDO);

            JButton btnGestionUsuarios =
                    crearBoton(
                            "Gestión de usuarios"
                    );

            btnGestionUsuarios.addActionListener(e -> {

                UsuariosFrame usuariosFrame =
                        new UsuariosFrame();

                usuariosFrame.setVisible(true);
            });

            panelBotones.add(
                    btnGestionUsuarios
            );

            JButton btnGestionEstudiantes =
                    crearBoton(
                            "Gestión de estudiantes"
                    );

            btnGestionEstudiantes.addActionListener(e -> {

                EstudiantesFrame estudiantesFrame =
                        new EstudiantesFrame();

                estudiantesFrame.setVisible(true);
            });

            panelBotones.add(
                    btnGestionEstudiantes
            );

            JButton btnGestionLibros =
                    crearBoton(
                            "Gestión de libros"
                    );

            btnGestionLibros.addActionListener(e -> {

                LibrosFrame librosFrame =
                        new LibrosFrame();

                librosFrame.setVisible(true);
            });

            panelBotones.add(
                    btnGestionLibros
            );

            JButton btnGestionCategorias =
                    crearBoton(
                            "Gestión de categorías"
                    );

            btnGestionCategorias.addActionListener(e -> {

                CategoriaFrame categoriaFrame =
                        new CategoriaFrame();

                categoriaFrame.setVisible(true);
            });

            panelBotones.add(
                    btnGestionCategorias
            );

            JButton btnPrestamosDevoluciones =
                    crearBoton(
                            "Préstamos y devoluciones"
                    );

            btnPrestamosDevoluciones.addActionListener(e -> {

                PrestamosFrame prestamosFrame =
                        new PrestamosFrame();

                prestamosFrame.setVisible(true);
            });

            panelBotones.add(
                    btnPrestamosDevoluciones
            );

            JButton btnReportes =
                    crearBoton("Reportes");

            btnReportes.addActionListener(e -> {

                ReportesFrame reportesFrame =
                        new ReportesFrame();

                reportesFrame.setVisible(true);
            });

            panelBotones.add(
                    btnReportes
            );

        } else if (usuario.getRolUsuario()
                .equalsIgnoreCase("estudiante")) {

            // Menú del estudiante
            panelBotones =
                    new JPanel(
                            new GridBagLayout()
                    );

            panelBotones.setBackground(FONDO);

            GridBagConstraints gbc =
                    new GridBagConstraints();

            gbc.insets =
                    new Insets(
                            8,
                            10,
                            8,
                            10
                    );

            gbc.fill =
                    GridBagConstraints.HORIZONTAL;

            gbc.weightx = 1.0;

            // Préstamos
            JButton btnPrestamos =
                    crearBotonEstudiante(
                            "Préstamos"
                    );

            btnPrestamos.addActionListener(e -> {

                PrestamosFrame prestamosFrame =
                        new PrestamosFrame(
                                usuario,
                                "PRESTAMO"
                        );

                prestamosFrame.setVisible(true);
            });

            gbc.gridx = 0;
            gbc.gridy = 0;

            panelBotones.add(
                    btnPrestamos,
                    gbc
            );

            // Devoluciones
            JButton btnDevoluciones =
                    crearBotonEstudiante(
                            "Devoluciones"
                    );

            btnDevoluciones.addActionListener(e -> {

                PrestamosFrame prestamosFrame =
                        new PrestamosFrame(
                                usuario,
                                "DEVOLUCION"
                        );

                prestamosFrame.setVisible(true);
            });

            gbc.gridx = 1;

            panelBotones.add(
                    btnDevoluciones,
                    gbc
            );

            // Mis préstamos
            JButton btnConsultas =
                    crearBotonEstudiante(
                            "Mis préstamos"
                    );

            btnConsultas.addActionListener(e -> {

                ConsultasFrame consultasFrame =
                        new ConsultasFrame(usuario);

                consultasFrame.setVisible(true);
            });

            gbc.gridx = 0;
            gbc.gridy = 1;
            gbc.gridwidth = 2;

            panelBotones.add(
                    btnConsultas,
                    gbc
            );

        } else {

            panelBotones =
                    new JPanel();

            panelBotones.setBackground(FONDO);

            JLabel lblSinPermisos =
                    new JLabel(
                            "El usuario no tiene un rol válido."
                    );

            lblSinPermisos.setForeground(
                    Color.RED
            );

            panelBotones.add(
                    lblSinPermisos
            );
        }

        panelCentro.add(
                panelBotones,
                BorderLayout.CENTER
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
                        12,
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
     * Crea un botón para el menú del bibliotecario.
     *
     * @param texto texto del botón
     * @return botón configurado
     */
    private JButton crearBoton(String texto) {

        JButton boton =
                new JButton(texto);

        boton.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        14
                )
        );

        boton.setForeground(Color.WHITE);

        boton.setBackground(
                AZUL_BOTON
        );

        boton.setFocusPainted(false);

        boton.setMargin(
                new Insets(
                        12,
                        20,
                        12,
                        20
                )
        );

        return boton;
    }

    /**
     * Crea un botón para el menú del estudiante.
     *
     * @param texto texto del botón
     * @return botón configurado
     */
    private JButton crearBotonEstudiante(String texto) {

        JButton boton =
                new JButton(texto);

        boton.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        15
                )
        );

        boton.setForeground(
                AZUL_PRINCIPAL
        );

        boton.setBackground(
                Color.WHITE
        );

        boton.setFocusPainted(false);

        boton.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(190, 205, 220),
                                1
                        ),
                        new EmptyBorder(
                                18,
                                35,
                                18,
                                35
                        )
                )
        );

        boton.setMargin(
                new Insets(
                        15,
                        25,
                        15,
                        25
                )
        );

        return boton;
    }
}