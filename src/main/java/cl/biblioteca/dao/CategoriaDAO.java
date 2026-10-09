package cl.biblioteca.dao;

import cl.biblioteca.conexion.DatabaseConnection;
import cl.biblioteca.modelo.Categoria;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * DAO encargado de gestionar las operaciones de la entidad Categoria
 * en la base de datos.
 *
 * @author Consuelo Martinez
 */
public class CategoriaDAO {

    /**
     * Crea una nueva categoría en la base de datos.
     *
     * @param categoria categoría que se desea registrar
     * @return true si la categoría fue creada correctamente
     */
    public boolean crearCategoria(Categoria categoria) {

        String sql = "INSERT INTO categorias (nombre) VALUES (?)";

        try {
            Connection connection = DatabaseConnection.getInstance();

            try (PreparedStatement statement = connection.prepareStatement(sql)) {

                statement.setString(1, categoria.getNombreCategoria());

                return statement.executeUpdate() > 0;
            }

        } catch (SQLException e) {
            System.out.println("Error al crear categoría: "
                    + e.getMessage());
            return false;
        }
    }

    /**
     * Busca una categoría por su identificador.
     *
     * @param idCategoria identificador de la categoría
     * @return categoría encontrada o null si no existe
     */
    public Categoria obtenerCategoriaPorId(int idCategoria) {

        String sql = "SELECT id, nombre " +
                "FROM categorias WHERE id = ?";

        try {
            Connection connection = DatabaseConnection.getInstance();

            try (PreparedStatement statement = connection.prepareStatement(sql)) {

                statement.setInt(1, idCategoria);

                try (ResultSet resultSet = statement.executeQuery()) {

                    if (resultSet.next()) {

                        return new Categoria(
                                resultSet.getInt("id"),
                                resultSet.getString("nombre")
                        );
                    }
                }

            }

        } catch (SQLException e) {
            System.out.println("Error al obtener categoría: "
                    + e.getMessage());
        }

        return null;
    }

    public java.util.List<Categoria> listarCategorias() {

        java.util.List<Categoria> categorias = new java.util.ArrayList<>();

        String sql = "SELECT id, nombre FROM categorias";

        try {
            Connection connection = DatabaseConnection.getInstance();

            try (PreparedStatement statement = connection.prepareStatement(sql);
                 ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {

                    Categoria categoria = new Categoria(
                            resultSet.getInt("id"),
                            resultSet.getString("nombre")
                    );

                    categorias.add(categoria);
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al listar categorías: " + e.getMessage());
        }

        return categorias;
    }

    /**
     * Actualiza el nombre de una categoría.
     *
     * @param categoria categoría con los datos actualizados
     * @return true si la actualización fue realizada
     */
    public boolean actualizarCategoria(Categoria categoria) {

        String sql = "UPDATE categorias " +
                "SET nombre = ? " +
                "WHERE id = ?";

        try {
            Connection connection = DatabaseConnection.getInstance();

            try (PreparedStatement statement = connection.prepareStatement(sql)) {

                statement.setString(1, categoria.getNombreCategoria());
                statement.setInt(2, categoria.getIdCategoria());

                return statement.executeUpdate() > 0;
            }

        } catch (SQLException e) {
            System.out.println("Error al actualizar categoría: "
                    + e.getMessage());
            return false;
        }
    }

    /**
     * Elimina una categoría de la base de datos.
     *
     * @param idCategoria identificador de la categoría
     * @return true si la categoría fue eliminada correctamente
     */
    public boolean eliminarCategoria(int idCategoria) {

        String sql = "DELETE FROM categorias WHERE id = ?";

        try {
            Connection connection = DatabaseConnection.getInstance();

            try (PreparedStatement statement = connection.prepareStatement(sql)) {

                statement.setInt(1, idCategoria);

                return statement.executeUpdate() > 0;
            }

        } catch (SQLException e) {
            System.out.println("Error al eliminar categoría: "
                    + e.getMessage());
            return false;
        }
    }
}
