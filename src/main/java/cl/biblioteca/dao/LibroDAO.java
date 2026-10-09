package cl.biblioteca.dao;

import cl.biblioteca.conexion.DatabaseConnection;
import cl.biblioteca.modelo.Categoria;
import cl.biblioteca.modelo.Libro;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * DAO encargado de gestionar las operaciones de la entidad Libro
 * en la base de datos.
 *
 * @author Consuelo Martinez
 */
public class LibroDAO {

    /**
     * Crea un nuevo libro en la base de datos.
     *
     * @param libro libro que se desea registrar
     * @return true si el libro fue creado correctamente
     */
    public boolean crearLibro(Libro libro) {

        if (libro.getStock() < 0) {
            return false;
        }

        String sql = "INSERT INTO libros " +
                "(titulo, autor, isbn, editorial, stock, id_categoria) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try {
            Connection connection = DatabaseConnection.getInstance();

            try (PreparedStatement statement =
                         connection.prepareStatement(sql)) {

                statement.setString(1, libro.getTituloLibro());
                statement.setString(2, libro.getAutorLibro());
                statement.setString(3, libro.getIsbn());
                statement.setString(4, libro.getEditorial());
                statement.setInt(5, libro.getStock());
                statement.setInt(6, libro.getCategoria().getIdCategoria());

                return statement.executeUpdate() > 0;
            }

        } catch (SQLException e) {
            System.out.println("Error al crear libro: "
                    + e.getMessage());
            return false;
        }
    }

    /**
     * Busca un libro por su identificador.
     *
     * @param idLibro identificador del libro
     * @return libro encontrado o null si no existe
     */
    public Libro obtenerLibroPorId(int idLibro) {

        String sql = "SELECT l.id, l.titulo, l.autor, l.isbn, " +
                "l.editorial, l.stock, " +
                "c.id AS id_categoria, c.nombre AS nombre_categoria " +
                "FROM libros l " +
                "INNER JOIN categorias c ON l.id_categoria = c.id " +
                "WHERE l.id = ?";

        try {
            Connection connection = DatabaseConnection.getInstance();

            try (PreparedStatement statement =
                         connection.prepareStatement(sql)) {

                statement.setInt(1, idLibro);

                try (ResultSet resultSet = statement.executeQuery()) {

                    if (resultSet.next()) {

                        Categoria categoria = new Categoria(
                                resultSet.getInt("id_categoria"),
                                resultSet.getString("nombre_categoria")
                        );

                        return new Libro(
                                resultSet.getInt("id"),
                                resultSet.getString("titulo"),
                                resultSet.getString("autor"),
                                resultSet.getString("isbn"),
                                resultSet.getString("editorial"),
                                resultSet.getInt("stock"),
                                categoria
                        );
                    }
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener libro: "
                    + e.getMessage());
        }

        return null;
    }

    /**
     * Obtiene todos los libros registrados en la base de datos.
     *
     * @return lista de libros registrados
     */
    public java.util.List<Libro> listarLibros() {

        java.util.List<Libro> libros =
                new java.util.ArrayList<>();

        String sql = "SELECT l.id, l.titulo, l.autor, l.isbn, " +
                "l.editorial, l.stock, " +
                "c.id AS id_categoria, c.nombre AS nombre_categoria " +
                "FROM libros l " +
                "INNER JOIN categorias c ON l.id_categoria = c.id";

        try {
            Connection connection = DatabaseConnection.getInstance();

            try (PreparedStatement statement =
                         connection.prepareStatement(sql);
                 ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {

                    Categoria categoria = new Categoria(
                            resultSet.getInt("id_categoria"),
                            resultSet.getString("nombre_categoria")
                    );

                    Libro libro = new Libro(
                            resultSet.getInt("id"),
                            resultSet.getString("titulo"),
                            resultSet.getString("autor"),
                            resultSet.getString("isbn"),
                            resultSet.getString("editorial"),
                            resultSet.getInt("stock"),
                            categoria
                    );

                    libros.add(libro);
                }
            }

        } catch (SQLException e) {
            System.out.println(
                    "Error al listar libros: " + e.getMessage()
            );
        }

        return libros;
    }

    /**
     * Cuenta la cantidad total de libros registrados.
     *
     * @return cantidad de libros registrados
     */
    public int contarLibros() {

        String sql = "SELECT COUNT(*) FROM libros";

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
                    "Error al contar libros: " + e.getMessage()
            );
        }

        return 0;
    }

    /**
     * Actualiza los datos de un libro existente en la base de datos.
     *
     * @param libro libro con los datos actualizados
     * @return true si el libro fue actualizado correctamente
     */
    public boolean actualizarLibro(Libro libro) {

        if (libro.getStock() < 0) {
            return false;
        }

        String sql = "UPDATE libros SET titulo=?, autor=?, isbn=?, " +
                "editorial=?, stock=?, id_categoria=? WHERE id=?";

        try {
            Connection connection = DatabaseConnection.getInstance();

            try (PreparedStatement statement =
                         connection.prepareStatement(sql)) {

                statement.setString(1, libro.getTituloLibro());
                statement.setString(2, libro.getAutorLibro());
                statement.setString(3, libro.getIsbn());
                statement.setString(4, libro.getEditorial());
                statement.setInt(5, libro.getStock());
                statement.setInt(
                        6,
                        libro.getCategoria().getIdCategoria()
                );
                statement.setInt(7, libro.getIdLibro());

                return statement.executeUpdate() > 0;
            }

        } catch (SQLException e) {
            System.out.println(
                    "Error al actualizar libro: " + e.getMessage()
            );
            return false;
        }
    }

    /**
     * Actualiza el stock de un libro en la base de datos.
     *
     * @param libro libro con el stock actualizado
     * @return true si la actualización fue realizada
     */
    public boolean actualizarStock(Libro libro) {

        if (libro.getStock() < 0) {
            return false;
        }

        try {
            Connection connection = DatabaseConnection.getInstance();

            return actualizarStock(connection, libro);

        } catch (SQLException e) {
            System.out.println(
                    "Error al actualizar stock: "
                            + e.getMessage()
            );
            return false;
        }
    }

    /**
     * Actualiza el stock utilizando una conexión existente.
     *
     * @param connection conexión utilizada para la transacción
     * @param libro libro con el stock actualizado
     * @return true si la actualización fue realizada
     * @throws SQLException si ocurre un error en la operación
     */
    public boolean actualizarStock(
            Connection connection,
            Libro libro) throws SQLException {

        if (libro.getStock() < 0) {
            return false;
        }

        String sql = "UPDATE libros " +
                "SET stock = ? " +
                "WHERE id = ?";

        try (PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, libro.getStock());
            statement.setInt(2, libro.getIdLibro());

            return statement.executeUpdate() > 0;
        }
    }

    /**
     * Elimina un libro de la base de datos.
     *
     * @param idLibro identificador del libro
     * @return true si el libro fue eliminado correctamente
     */
    public boolean eliminarLibro(int idLibro) {

        String sql = "DELETE FROM libros WHERE id = ?";

        try {
            Connection connection = DatabaseConnection.getInstance();

            try (PreparedStatement statement =
                         connection.prepareStatement(sql)) {

                statement.setInt(1, idLibro);

                return statement.executeUpdate() > 0;
            }

        } catch (SQLException e) {

            if (e.getMessage().contains(
                    "Cannot delete or update a parent row")) {

                System.out.println(
                        "No se puede eliminar el libro porque "
                                + "tiene préstamos asociados."
                );

            } else {

                System.out.println(
                        "Error al eliminar libro: "
                                + e.getMessage()
                );
            }

            return false;
        }
    }
}