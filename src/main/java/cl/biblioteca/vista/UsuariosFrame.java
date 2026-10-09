package cl.biblioteca.vista;

import cl.biblioteca.dao.UsuarioDAO;
import cl.biblioteca.modelo.Usuario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * Ventana encargada de gestionar los usuarios del sistema.
 * Permite registrar, modificar, eliminar y consultar usuarios.
 *
 * @author Consuelo Martinez
 */
public class UsuariosFrame extends JFrame {

    private JTextField txtNombre;
    private JTextField txtRut;
    private JTextField txtCorreo;
    private JPasswordField txtContrasena;
    private JComboBox<String> cmbRol;

    private JTable tablaUsuarios;
    private DefaultTableModel modeloTabla;

    private final UsuarioDAO usuarioDAO;

    /**
     * Constructor de la ventana de gestión de usuarios.
     */
    public UsuariosFrame() {

        usuarioDAO = new UsuarioDAO();

        setTitle("Gestión de Usuarios");
        setSize(850, 550);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        crearInterfaz();
        cargarUsuarios();
    }

    /**
     * Construye la interfaz gráfica de la ventana.
     */
    private void crearInterfaz() {

        JPanel panelPrincipal =
                new JPanel(new BorderLayout(10, 10));

        panelPrincipal.setBorder(
                BorderFactory.createEmptyBorder(
                        15, 15, 15, 15
                )
        );

        JPanel panelFormulario =
                new JPanel(new GridLayout(5, 2, 10, 10));

        panelFormulario.add(new JLabel("Nombre:"));
        txtNombre = new JTextField();
        panelFormulario.add(txtNombre);

        panelFormulario.add(new JLabel("RUT:"));
        txtRut = new JTextField();
        panelFormulario.add(txtRut);

        panelFormulario.add(new JLabel("Correo:"));
        txtCorreo = new JTextField();
        panelFormulario.add(txtCorreo);

        panelFormulario.add(new JLabel("Contraseña:"));
        txtContrasena = new JPasswordField();
        panelFormulario.add(txtContrasena);

        panelFormulario.add(new JLabel("Rol:"));
        cmbRol = new JComboBox<>(
                new String[]{"bibliotecario", "estudiante"}
        );
        panelFormulario.add(cmbRol);

        panelPrincipal.add(
                panelFormulario,
                BorderLayout.NORTH
        );

        modeloTabla = new DefaultTableModel(
                new Object[]{
                        "ID",
                        "Nombre",
                        "RUT",
                        "Correo",
                        "Rol"
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

        tablaUsuarios = new JTable(modeloTabla);

        tablaUsuarios.getSelectionModel()
                .addListSelectionListener(e -> {

                    if (!e.getValueIsAdjusting()) {
                        cargarUsuarioSeleccionado();
                    }
                });

        JScrollPane scrollPane =
                new JScrollPane(tablaUsuarios);

        panelPrincipal.add(
                scrollPane,
                BorderLayout.CENTER
        );

        JButton btnAgregar =
                new JButton("Agregar");

        JButton btnModificar =
                new JButton("Modificar");

        JButton btnEliminar =
                new JButton("Eliminar");

        JButton btnLimpiar =
                new JButton("Limpiar");

        btnAgregar.addActionListener(
                e -> agregarUsuario()
        );

        btnModificar.addActionListener(
                e -> modificarUsuario()
        );

        btnEliminar.addActionListener(
                e -> eliminarUsuario()
        );

        btnLimpiar.addActionListener(
                e -> limpiarFormulario()
        );

        JPanel panelBotones =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.CENTER,
                                10,
                                10
                        )
                );

        panelBotones.add(btnAgregar);
        panelBotones.add(btnModificar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnLimpiar);

        panelPrincipal.add(
                panelBotones,
                BorderLayout.SOUTH
        );

        add(panelPrincipal);
    }

    /**
     * Carga todos los usuarios registrados en la tabla.
     */
    private void cargarUsuarios() {

        modeloTabla.setRowCount(0);

        for (Usuario usuario : usuarioDAO.listarUsuarios()) {

            modeloTabla.addRow(new Object[]{
                    usuario.getIdUsuario(),
                    usuario.getNombreUsuario(),
                    usuario.getRutUsuario(),
                    usuario.getCorreoUsuario(),
                    usuario.getRolUsuario()
            });
        }
    }

    /**
     * Carga en el formulario los datos del usuario seleccionado.
     */
    private void cargarUsuarioSeleccionado() {

        int filaSeleccionada =
                tablaUsuarios.getSelectedRow();

        if (filaSeleccionada == -1) {
            return;
        }

        txtNombre.setText(
                modeloTabla.getValueAt(
                        filaSeleccionada, 1
                ).toString()
        );

        txtRut.setText(
                modeloTabla.getValueAt(
                        filaSeleccionada, 2
                ).toString()
        );

        txtCorreo.setText(
                modeloTabla.getValueAt(
                        filaSeleccionada, 3
                ).toString()
        );

        cmbRol.setSelectedItem(
                modeloTabla.getValueAt(
                        filaSeleccionada, 4
                ).toString()
        );

        txtContrasena.setText("");
    }

    /**
     * Registra un nuevo usuario en la base de datos.
     */
    private void agregarUsuario() {

        if (!validarFormulario()) {
            return;
        }

        String nombre = txtNombre.getText().trim();
        String rut = txtRut.getText().trim();
        String correo = txtCorreo.getText().trim();
        String contrasena =
                new String(
                        txtContrasena.getPassword()
                );
        String rol =
                cmbRol.getSelectedItem().toString();

        Usuario usuario = new Usuario(
                0,
                nombre,
                rut,
                correo,
                contrasena,
                rol
        );

        if (usuarioDAO.crearUsuario(usuario)) {

            JOptionPane.showMessageDialog(
                    this,
                    "Usuario registrado correctamente."
            );

            cargarUsuarios();
            limpiarFormulario();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "No fue posible registrar el usuario.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    /**
     * Modifica los datos del usuario seleccionado.
     */
    private void modificarUsuario() {

        int filaSeleccionada =
                tablaUsuarios.getSelectedRow();

        if (filaSeleccionada == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Seleccione un usuario para modificar."
            );

            return;
        }

        if (!validarFormulario()) {
            return;
        }

        int idUsuario =
                Integer.parseInt(
                        modeloTabla.getValueAt(
                                filaSeleccionada, 0
                        ).toString()
                );

        String nombre = txtNombre.getText().trim();
        String correo = txtCorreo.getText().trim();
        String contrasena =
                new String(
                        txtContrasena.getPassword()
                );

        if (contrasena.isBlank()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Ingrese la contraseña para modificar el usuario."
            );

            return;
        }

        Usuario usuario =
                new Usuario(
                        idUsuario,
                        nombre,
                        txtRut.getText().trim(),
                        correo,
                        contrasena,
                        cmbRol.getSelectedItem().toString()
                );

        if (usuarioDAO.actualizarUsuario(usuario)) {

            JOptionPane.showMessageDialog(
                    this,
                    "Usuario modificado correctamente."
            );

            cargarUsuarios();
            limpiarFormulario();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "No fue posible modificar el usuario.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    /**
     * Elimina el usuario seleccionado.
     */
    private void eliminarUsuario() {

        int filaSeleccionada =
                tablaUsuarios.getSelectedRow();

        if (filaSeleccionada == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Seleccione un usuario para eliminar."
            );

            return;
        }

        int confirmacion =
                JOptionPane.showConfirmDialog(
                        this,
                        "¿Está seguro de eliminar este usuario?",
                        "Confirmar eliminación",
                        JOptionPane.YES_NO_OPTION
                );

        if (confirmacion != JOptionPane.YES_OPTION) {
            return;
        }

        int idUsuario =
                Integer.parseInt(
                        modeloTabla.getValueAt(
                                filaSeleccionada, 0
                        ).toString()
                );

        if (usuarioDAO.eliminarUsuario(idUsuario)) {

            JOptionPane.showMessageDialog(
                    this,
                    "Usuario eliminado correctamente."
            );

            cargarUsuarios();
            limpiarFormulario();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "No fue posible eliminar el usuario.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    /**
     * Valida los datos básicos del formulario.
     *
     * @return true si los campos obligatorios están completos
     */
    private boolean validarFormulario() {

        if (txtNombre.getText().trim().isEmpty()
                || txtRut.getText().trim().isEmpty()
                || txtCorreo.getText().trim().isEmpty()
                || txtContrasena.getPassword().length == 0) {

            JOptionPane.showMessageDialog(
                    this,
                    "Complete todos los campos."
            );

            return false;
        }

        return true;
    }

    /**
     * Limpia los campos del formulario.
     */
    private void limpiarFormulario() {

        txtNombre.setText("");
        txtRut.setText("");
        txtCorreo.setText("");
        txtContrasena.setText("");

        cmbRol.setSelectedIndex(0);

        tablaUsuarios.clearSelection();
    }
}