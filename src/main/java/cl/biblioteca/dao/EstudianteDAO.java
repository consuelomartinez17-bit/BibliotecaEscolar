package cl.biblioteca.dao;

import cl.biblioteca.conexion.DatabaseConnection;
import cl.biblioteca.modelo.Estudiante;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * DAO encargado de gestionar las operaciones de la entidad Estudiante
 * en la base de datos.
 *
 * @author Consuelo Martinez
 */
public class EstudianteDAO {

    /**
     * Crea un nuevo estudiante en la base de datos.
     *
     * @param estudiante estudiante que se desea registrar
     * @return true si el estudiante fue creado correctamente
     */
    public boolean crearEstudiante(Estudiante estudiante) {

        String sql = "INSERT INTO estudiantes " +
                "(nombre, rut, curso, correo) " +
                "VALUES (?, ?, ?, ?)";

        try {
            Connection connection = DatabaseConnection.getInstance();

            try (PreparedStatement statement = connection.prepareStatement(sql)) {

                statement.setString(1, estudiante.getNombreEstudiante());
                statement.setString(2, estudiante.getRutEstudiante());
                statement.setString(3, estudiante.getCurso());
                statement.setString(4, estudiante.getCorreoEstudiante());

                return statement.executeUpdate() > 0;
            }

        } catch (SQLException e) {
            System.out.println("Error al crear estudiante: " + e.getMessage());
            return false;
        }
    }

    /**
     * Busca un estudiante por su identificador.
     *
     * @param idEstudiante identificador del estudiante
     * @return estudiante encontrado o null si no existe
     */
    public Estudiante obtenerEstudiantePorId(int idEstudiante) {

        String sql = "SELECT id, nombre, rut, curso, correo " +
                "FROM estudiantes WHERE id = ?";

        try {
            Connection connection = DatabaseConnection.getInstance();

            try (PreparedStatement statement = connection.prepareStatement(sql)) {

                statement.setInt(1, idEstudiante);

                try (ResultSet resultSet = statement.executeQuery()) {

                    if (resultSet.next()) {

                        return new Estudiante(
                                resultSet.getInt("id"),
                                resultSet.getString("nombre"),
                                resultSet.getString("rut"),
                                resultSet.getString("curso"),
                                resultSet.getString("correo")
                        );
                    }
                }

            }

        } catch (SQLException e) {
            System.out.println("Error al obtener estudiante: " + e.getMessage());
        }

        return null;
    }

    /**
     * Obtiene todos los estudiantes registrados en la base de datos.
     *
     * @return lista de estudiantes registrados
     */
    public java.util.List<Estudiante> listarEstudiantes() {

        java.util.List<Estudiante> estudiantes =
                new java.util.ArrayList<>();

        String sql = "SELECT id, nombre, rut, curso, correo " +
                "FROM estudiantes";

        try {
            Connection connection = DatabaseConnection.getInstance();

            try (PreparedStatement statement =
                         connection.prepareStatement(sql);
                 ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {

                    Estudiante estudiante = new Estudiante(
                            resultSet.getInt("id"),
                            resultSet.getString("nombre"),
                            resultSet.getString("rut"),
                            resultSet.getString("curso"),
                            resultSet.getString("correo")
                    );

                    estudiantes.add(estudiante);
                }
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error al listar estudiantes: " + e.getMessage()
            );
        }

        return estudiantes;
    }

    /**
     * Cuenta la cantidad total de estudiantes registrados.
     *
     * @return cantidad de estudiantes registrados
     */
    public int contarEstudiantes() {

        String sql = "SELECT COUNT(*) FROM estudiantes";

        try {
            Connection connection = DatabaseConnection.getInstance();

            try (PreparedStatement statement =
                         connection.prepareStatement(sql);
                 ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {
                    return resultSet.getInt(1);
                }
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error al contar estudiantes: " + e.getMessage()
            );
        }

        return 0;
    }

    /**
     * Actualiza los datos permitidos de un estudiante.
     *
     * @param estudiante estudiante con los datos actualizados
     * @return true si la actualización fue realizada
     */
    public boolean actualizarEstudiante(Estudiante estudiante) {

        String sql = "UPDATE estudiantes " +
                "SET nombre = ?, curso = ?, correo = ? " +
                "WHERE id = ?";

        try {
            Connection connection = DatabaseConnection.getInstance();

            try (PreparedStatement statement = connection.prepareStatement(sql)) {

                statement.setString(1, estudiante.getNombreEstudiante());
                statement.setString(2, estudiante.getCurso());
                statement.setString(3, estudiante.getCorreoEstudiante());
                statement.setInt(4, estudiante.getIdEstudiante());

                return statement.executeUpdate() > 0;
            }

        } catch (SQLException e) {
            System.out.println("Error al actualizar estudiante: "
                    + e.getMessage());
            return false;
        }
    }

    /**
     * Elimina un estudiante de la base de datos.
     *
     * @param idEstudiante identificador del estudiante
     * @return true si el estudiante fue eliminado correctamente
     */
    public boolean eliminarEstudiante(int idEstudiante) {

        String sql = "DELETE FROM estudiantes WHERE id = ?";

        try {
            Connection connection = DatabaseConnection.getInstance();

            try (PreparedStatement statement = connection.prepareStatement(sql)) {

                statement.setInt(1, idEstudiante);

                return statement.executeUpdate() > 0;
            }

        } catch (SQLException e) {
            System.out.println("Error al eliminar estudiante: "
                    + e.getMessage());
            return false;
        }
    }
    /**
     * Busca un estudiante por su RUT.
     *
     * @param rut RUT del estudiante
     * @return estudiante encontrado o null
     */
    public Estudiante obtenerEstudiantePorRut(String rut) {

        String sql = """
            SELECT id, nombre, rut, curso, correo
            FROM estudiantes
            WHERE rut = ?
            """;

        try {
            Connection connection = DatabaseConnection.getInstance();

            try (PreparedStatement ps = connection.prepareStatement(sql)) {

                ps.setString(1, rut);

                try (ResultSet rs = ps.executeQuery()) {

                    if (rs.next()) {

                        return new Estudiante(
                                rs.getInt("id"),
                                rs.getString("nombre"),
                                rs.getString("rut"),
                                rs.getString("curso"),
                                rs.getString("correo")
                        );
                    }
                }
            }

        } catch (SQLException e) {
            System.out.println(
                    "Error al buscar estudiante por RUT: "
                            + e.getMessage()
            );
        }

        return null;
    }
}