package cl.biblioteca.vista;

import cl.biblioteca.dao.EstudianteDAO;
import cl.biblioteca.modelo.Estudiante;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Ventana para gestionar los estudiantes de la biblioteca.
 *
 * @author Consuelo Martinez
 */
public class EstudiantesFrame extends JFrame {

    private JTextField txtNombre;
    private JTextField txtRut;
    private JTextField txtCurso;
    private JTextField txtCorreo;

    private JTable tablaEstudiantes;
    private DefaultTableModel modeloTabla;

    private final EstudianteDAO estudianteDAO;

    /**
     * Constructor de la ventana de gestión de estudiantes.
     */
    public EstudiantesFrame() {

        estudianteDAO = new EstudianteDAO();

        setTitle("Gestión de Estudiantes");
        setSize(800, 550);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        inicializarComponentes();
        cargarEstudiantes();
    }

    /**
     * Inicializa los componentes gráficos de la ventana.
     */
    private void inicializarComponentes() {

        JPanel panelFormulario = new JPanel(new GridLayout(4, 2, 10, 10));
        panelFormulario.setBorder(
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        );

        txtNombre = new JTextField();
        txtRut = new JTextField();
        txtCurso = new JTextField();
        txtCorreo = new JTextField();

        panelFormulario.add(new JLabel("Nombre:"));
        panelFormulario.add(txtNombre);

        panelFormulario.add(new JLabel("RUT:"));
        panelFormulario.add(txtRut);

        panelFormulario.add(new JLabel("Curso:"));
        panelFormulario.add(txtCurso);

        panelFormulario.add(new JLabel("Correo:"));
        panelFormulario.add(txtCorreo);

        modeloTabla = new DefaultTableModel(
                new Object[]{"ID", "Nombre", "RUT", "Curso", "Correo"},
                0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaEstudiantes = new JTable(modeloTabla);

        JScrollPane scrollTabla = new JScrollPane(tablaEstudiantes);

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

        btnAgregar.addActionListener(e -> agregarEstudiante());
        btnModificar.addActionListener(e -> modificarEstudiante());
        btnEliminar.addActionListener(e -> eliminarEstudiante());
        btnLimpiar.addActionListener(e -> limpiarFormulario());

        tablaEstudiantes.getSelectionModel().addListSelectionListener(e -> {

            if (!e.getValueIsAdjusting()) {
                cargarEstudianteSeleccionado();
            }
        });
    }

    /**
     * Carga los estudiantes registrados desde la base de datos.
     */
    private void cargarEstudiantes() {

        modeloTabla.setRowCount(0);

        List<Estudiante> estudiantes =
                estudianteDAO.listarEstudiantes();

        for (Estudiante estudiante : estudiantes) {

            modeloTabla.addRow(new Object[]{
                    estudiante.getIdEstudiante(),
                    estudiante.getNombreEstudiante(),
                    estudiante.getRutEstudiante(),
                    estudiante.getCurso(),
                    estudiante.getCorreoEstudiante()
            });
        }
    }

    /**
     * Registra un nuevo estudiante en la base de datos.
     */
    private void agregarEstudiante() {

        String nombre = txtNombre.getText().trim();
        String rut = txtRut.getText().trim();
        String curso = txtCurso.getText().trim();
        String correo = txtCorreo.getText().trim();

        Estudiante estudiante = new Estudiante(
                0,
                nombre,
                rut,
                curso,
                correo
        );

        if (estudianteDAO.crearEstudiante(estudiante)) {

            JOptionPane.showMessageDialog(
                    this,
                    "Estudiante agregado correctamente."
            );

            cargarEstudiantes();
            limpiarFormulario();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "No fue posible agregar el estudiante."
            );
        }
    }

    /**
     * Modifica los datos del estudiante seleccionado.
     */
    private void modificarEstudiante() {

        int fila = tablaEstudiantes.getSelectedRow();

        if (fila < 0) {
            JOptionPane.showMessageDialog(
                    this,
                    "Seleccione un estudiante de la tabla."
            );
            return;
        }

        int idEstudiante = Integer.parseInt(
                modeloTabla.getValueAt(fila, 0).toString()
        );

        Estudiante estudiante = new Estudiante(
                idEstudiante,
                txtNombre.getText().trim(),
                txtRut.getText().trim(),
                txtCurso.getText().trim(),
                txtCorreo.getText().trim()
        );

        if (estudianteDAO.actualizarEstudiante(estudiante)) {

            JOptionPane.showMessageDialog(
                    this,
                    "Estudiante modificado correctamente."
            );

            cargarEstudiantes();
            limpiarFormulario();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "No fue posible modificar el estudiante."
            );
        }
    }

    /**
     * Elimina el estudiante seleccionado después de solicitar confirmación.
     */
    private void eliminarEstudiante() {

        int fila = tablaEstudiantes.getSelectedRow();

        if (fila < 0) {
            JOptionPane.showMessageDialog(
                    this,
                    "Seleccione un estudiante de la tabla."
            );
            return;
        }

        int idEstudiante = Integer.parseInt(
                modeloTabla.getValueAt(fila, 0).toString()
        );

        String nombre = modeloTabla.getValueAt(fila, 1).toString();

        int confirmacion = JOptionPane.showConfirmDialog(
                this,
                "¿Está seguro de eliminar al estudiante \""
                        + nombre + "\"?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION
        );

        if (confirmacion == JOptionPane.YES_OPTION) {

            if (estudianteDAO.eliminarEstudiante(idEstudiante)) {

                JOptionPane.showMessageDialog(
                        this,
                        "Estudiante eliminado correctamente."
                );

                cargarEstudiantes();
                limpiarFormulario();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "No fue posible eliminar el estudiante."
                );
            }
        }
    }

    /**
     * Carga en el formulario los datos del estudiante seleccionado.
     */
    private void cargarEstudianteSeleccionado() {

        int fila = tablaEstudiantes.getSelectedRow();

        if (fila >= 0) {

            txtNombre.setText(
                    modeloTabla.getValueAt(fila, 1).toString()
            );

            txtRut.setText(
                    modeloTabla.getValueAt(fila, 2).toString()
            );

            txtCurso.setText(
                    modeloTabla.getValueAt(fila, 3).toString()
            );

            txtCorreo.setText(
                    modeloTabla.getValueAt(fila, 4).toString()
            );
        }
    }

    /**
     * Limpia los campos del formulario y la selección de la tabla.
     */
    private void limpiarFormulario() {

        txtNombre.setText("");
        txtRut.setText("");
        txtCurso.setText("");
        txtCorreo.setText("");

        tablaEstudiantes.clearSelection();
    }
}