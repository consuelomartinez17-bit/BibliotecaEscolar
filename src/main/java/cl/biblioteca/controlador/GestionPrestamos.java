package cl.biblioteca.controlador;

import java.sql.Connection;
import java.sql.SQLException;
import cl.biblioteca.conexion.DatabaseConnection;

import cl.biblioteca.dao.LibroDAO;
import cl.biblioteca.dao.PrestamoDAO;
import cl.biblioteca.modelo.Libro;
import cl.biblioteca.modelo.Prestamo;

import cl.biblioteca.modelo.Prestable;

/**
 * Clase encargada de gestionar las operaciones relacionadas
 * con los préstamos de la biblioteca.
 *
 * Mantiene una única instancia compartida por todas las ventanas,
 * de modo que los métodos synchronized usen siempre el mismo candado
 * y el stock se modifique de forma segura.
 *
 * @author Consuelo Martinez
 */
public class GestionPrestamos {

    /**
     * Única instancia compartida de la clase.
     */
    private static final GestionPrestamos INSTANCIA =
            new GestionPrestamos();

    private final LibroDAO libroDAO;
    private final PrestamoDAO prestamoDAO;

    /**
     * Constructor privado para evitar crear más de una instancia.
     */
    private GestionPrestamos() {
        this.libroDAO = new LibroDAO();
        this.prestamoDAO = new PrestamoDAO();
    }

    /**
     * Obtiene la instancia compartida del controlador de préstamos.
     *
     * @return instancia única de GestionPrestamos
     */
    public static GestionPrestamos getInstance() {
        return INSTANCIA;
    }

    /**
     * Registra un préstamo verificando disponibilidad y
     * evitando que un estudiante tenga duplicado el mismo libro.
     *
     * @param prestamo préstamo que se desea registrar
     * @return true si el préstamo fue registrado correctamente
     */
    public synchronized boolean registrarPrestamo(Prestamo prestamo) {

        Connection connection = null;

        try {
            connection = DatabaseConnection.getInstance();

            connection.setAutoCommit(false);

            Libro libro = libroDAO.obtenerLibroPorId(
                    prestamo.getLibro().getIdLibro());

            if (libro == null) {
                System.out.println("El libro no existe.");
                connection.rollback();
                return false;
            }

            /*
             * Verifica si el estudiante ya tiene este mismo libro
             * en un préstamo activo.
             */
            for (Prestamo prestamoDelEstudiante
                    : prestamoDAO.listarPrestamosPorEstudiante(
                    prestamo.getEstudiante().getIdEstudiante())) {

                if (!prestamoDelEstudiante.isDevuelto()
                        && prestamoDelEstudiante.getLibro().getIdLibro()
                        == libro.getIdLibro()) {

                    System.out.println(
                            "Ya tienes un ejemplar de este libro en tu poder. "
                                    + "Debes devolverlo antes de solicitarlo nuevamente.");

                    connection.rollback();
                    return false;
                }
            }

            if (libro.getStock() <= 0) {
                System.out.println("No hay stock disponible.");
                connection.rollback();
                return false;
            }

            Prestable elementoPrestable = libro;
            elementoPrestable.prestar();

            if (!libroDAO.actualizarStock(connection, libro)) {
                connection.rollback();
                return false;
            }

            Prestamo nuevoPrestamo = new Prestamo(
                    prestamo.getIdPrestamo(),
                    prestamo.getEstudiante(),
                    libro,
                    prestamo.getFechaPrestamo(),
                    prestamo.getFechaDevolucion(),
                    prestamo.isDevuelto()
            );

            if (!prestamoDAO.crearPrestamo(
                    connection, nuevoPrestamo)) {

                connection.rollback();
                return false;
            }

            connection.commit();
            return true;

        } catch (SQLException e) {

            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException rollbackException) {
                    System.out.println(
                            "Error al realizar rollback: "
                                    + rollbackException.getMessage());
                }
            }

            System.out.println(
                    "Error al registrar préstamo: "
                            + e.getMessage());

            return false;

        } finally {

            if (connection != null) {
                try {
                    connection.setAutoCommit(true);
                } catch (SQLException e) {
                    System.out.println(
                            "Error al restaurar autoCommit: "
                                    + e.getMessage());
                }
            }
        }
    }

    /**
     * Registra la devolución de un préstamo
     * y actualiza el stock del libro.
     *
     * @param idPrestamo identificador del préstamo
     * @return true si la devolución fue registrada correctamente
     */
    public synchronized boolean registrarDevolucion(int idPrestamo) {

        Connection connection = null;

        try {
            connection = DatabaseConnection.getInstance();

            connection.setAutoCommit(false);

            Prestamo prestamo = prestamoDAO.obtenerPrestamoPorId(idPrestamo);

            if (prestamo == null) {
                System.out.println("El préstamo no existe.");
                connection.rollback();
                return false;
            }

            if (prestamo.isDevuelto()) {
                System.out.println("El préstamo ya fue devuelto.");
                connection.rollback();
                return false;
            }

            Libro libro = prestamo.getLibro();

            Prestable elementoPrestable = libro;
            elementoPrestable.devolver();

            if (!libroDAO.actualizarStock(connection, libro)) {
                connection.rollback();
                return false;
            }

            if (!prestamoDAO.registrarDevolucion(
                    connection, idPrestamo)) {

                connection.rollback();
                return false;
            }

            connection.commit();
            return true;

        } catch (SQLException e) {

            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException rollbackException) {
                    System.out.println(
                            "Error al realizar rollback: "
                                    + rollbackException.getMessage());
                }
            }

            System.out.println("Error al registrar devolución: "
                    + e.getMessage());

            return false;

        } finally {

            if (connection != null) {
                try {
                    connection.setAutoCommit(true);
                } catch (SQLException e) {
                    System.out.println(
                            "Error al restaurar autoCommit: "
                                    + e.getMessage());
                }
            }
        }
    }
}