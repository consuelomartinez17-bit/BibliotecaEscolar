package cl.biblioteca.vista;

import cl.biblioteca.controlador.GestionUsuarios;
import cl.biblioteca.modelo.Usuario;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import com.formdev.flatlaf.FlatLightLaf;

/**
 * Ventana principal de inicio de sesión.
 *
 * @author Consuelo Martinez
 */
public class LoginFrame extends JFrame {

    private JTextField txtCorreo;
    private JPasswordField txtContrasena;
    private JButton btnIngresar;
    private JButton btnSalir;
    private GestionUsuarios gestionUsuarios;

    // Colores de la interfaz
    private static final Color AZUL_PRINCIPAL =
            new Color(36, 78, 121);

    private static final Color AZUL_BOTON =
            new Color(52, 101, 153);

    private static final Color FONDO =
            new Color(242, 245, 248);

    private static final Color TEXTO =
            new Color(45, 55, 65);

    public LoginFrame() {

        setTitle("Trabajo Sumativo - Biblioteca Escolar");
        setSize(500, 460);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        gestionUsuarios = new GestionUsuarios();

        inicializarComponentes();
        configurarEventos();
    }

    /**
     * Inicializa los componentes de la ventana.
     */
    private void inicializarComponentes() {

        JPanel panelPrincipal =
                new JPanel(new BorderLayout());

        panelPrincipal.setBackground(FONDO);

        // Encabezado
        JPanel panelEncabezado =
                new JPanel();

        panelEncabezado.setBackground(AZUL_PRINCIPAL);
        panelEncabezado.setPreferredSize(
                new Dimension(500, 115)
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
                        14
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

        JLabel lblSubtitulo =
                new JLabel(
                        "Sistema de gestión de préstamos"
                );

        lblSubtitulo.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        14
                )
        );

        lblSubtitulo.setForeground(Color.WHITE);
        lblSubtitulo.setAlignmentX(
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
                Box.createVerticalStrut(5)
        );

        panelEncabezado.add(lblSubtitulo);

        // Panel central
        JPanel panelCentro =
                new JPanel(new GridBagLayout());

        panelCentro.setBackground(FONDO);

        panelCentro.setBorder(
                new EmptyBorder(
                        25,
                        70,
                        20,
                        70
                )
        );

        JPanel panelFormulario =
                new JPanel(new GridBagLayout());

        panelFormulario.setBackground(Color.WHITE);

        panelFormulario.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(215, 222, 230)
                        ),
                        new EmptyBorder(
                                25,
                                30,
                                25,
                                30
                        )
                )
        );

        GridBagConstraints gbc =
                new GridBagConstraints();

        gbc.fill =
                GridBagConstraints.HORIZONTAL;

        gbc.weightx = 1.0;

        gbc.insets =
                new Insets(5, 0, 5, 0);

        // Correo
        JLabel lblCorreo =
                new JLabel("Correo electrónico");

        lblCorreo.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        13
                )
        );

        lblCorreo.setForeground(TEXTO);

        gbc.gridx = 0;
        gbc.gridy = 0;

        panelFormulario.add(
                lblCorreo,
                gbc
        );

        txtCorreo =
                new JTextField();

        txtCorreo.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        14
                )
        );

        txtCorreo.setPreferredSize(
                new Dimension(300, 38)
        );

        gbc.gridy = 1;

        gbc.insets =
                new Insets(
                        0,
                        0,
                        12,
                        0
                );

        panelFormulario.add(
                txtCorreo,
                gbc
        );

        // Contraseña
        JLabel lblContrasena =
                new JLabel("Contraseña");

        lblContrasena.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        13
                )
        );

        lblContrasena.setForeground(TEXTO);

        gbc.gridy = 2;

        gbc.insets =
                new Insets(
                        5,
                        0,
                        5,
                        0
                );

        panelFormulario.add(
                lblContrasena,
                gbc
        );

        txtContrasena =
                new JPasswordField();

        txtContrasena.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        14
                )
        );

        txtContrasena.setPreferredSize(
                new Dimension(300, 38)
        );

        gbc.gridy = 3;

        gbc.insets =
                new Insets(
                        0,
                        0,
                        18,
                        0
                );

        panelFormulario.add(
                txtContrasena,
                gbc
        );

        // Botones
        JPanel panelBotones =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.CENTER,
                                10,
                                0
                        )
                );

        panelBotones.setBackground(Color.WHITE);

        btnIngresar =
                new JButton("Iniciar sesión");

        btnIngresar.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        13
                )
        );

        btnIngresar.setForeground(Color.WHITE);
        btnIngresar.setBackground(AZUL_BOTON);
        btnIngresar.setFocusPainted(false);

        btnIngresar.setMargin(
                new Insets(8, 20, 8, 20)
        );

        btnSalir =
                new JButton("Salir");

        btnSalir.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        13
                )
        );

        btnSalir.setFocusPainted(false);

        btnSalir.setMargin(
                new Insets(8, 20, 8, 20)
        );

        panelBotones.add(btnIngresar);
        panelBotones.add(btnSalir);

        gbc.gridy = 4;

        gbc.insets =
                new Insets(
                        0,
                        0,
                        0,
                        0
                );

        panelFormulario.add(
                panelBotones,
                gbc
        );

        panelCentro.add(panelFormulario);

        // Pie de ventana
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
                panelEncabezado,
                BorderLayout.NORTH
        );

        panelPrincipal.add(
                panelCentro,
                BorderLayout.CENTER
        );

        panelPrincipal.add(
                lblPie,
                BorderLayout.SOUTH
        );

        add(panelPrincipal);
    }

    /**
     * Configura las acciones de los botones.
     */
    private void configurarEventos() {

        btnSalir.addActionListener(
                e -> System.exit(0)
        );

        btnIngresar.addActionListener(e -> {

            String correo =
                    txtCorreo.getText().trim();

            String contrasena =
                    new String(
                            txtContrasena.getPassword()
                    );

            if (correo.isEmpty()
                    || contrasena.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Debe ingresar correo y contraseña.",
                        "Datos incompletos",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }

            Usuario usuario =
                    gestionUsuarios.iniciarSesion(
                            correo,
                            contrasena
                    );

            if (usuario != null) {

                MenuFrame menu =
                        new MenuFrame(usuario);

                menu.setVisible(true);

                dispose();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Correo o contraseña incorrectos.",
                        "Error de inicio de sesión",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        });
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            try {
                UIManager.setLookAndFeel(
                        new FlatLightLaf()
                );
            } catch (Exception e) {
                System.out.println(
                        "No se pudo cargar FlatLaf."
                );
            }

            LoginFrame ventana =
                    new LoginFrame();

            ventana.setVisible(true);
        });
    }
}