package cl.biblioteca.dao;

import cl.biblioteca.conexion.DatabaseConnection;
import cl.biblioteca.modelo.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO encargado de gestionar las operaciones de la entidad Usuario
 * en la base de datos.
 *
 * @author Consuelo Martinez
 */
public class UsuarioDAO {

    /**
     * Crea un nuevo usuario en la base de datos.
     *
     * @param usuario usuario que se desea registrar
     * @return true si el usuario fue creado correctamente
     */
    public boolean crearUsuario(Usuario usuario) {

        String sql = "INSERT INTO usuarios " +
                "(nombre, rut, correo, contraseña, rol) " +
                "VALUES (?, ?, ?, ?, ?)";

        try {
            Connection connection =
                    DatabaseConnection.getInstance();

            try (PreparedStatement statement =
                         connection.prepareStatement(sql)) {

                statement.setString(
                        1, usuario.getNombreUsuario()
                );
                statement.setString(
                        2, usuario.getRutUsuario()
                );
                statement.setString(
                        3, usuario.getCorreoUsuario()
                );
                statement.setString(
                        4, usuario.getContrasena()
                );
                statement.setString(
                        5, usuario.getRolUsuario()
                );

                return statement.executeUpdate() > 0;
            }

        } catch (SQLException e) {
            System.out.println(
                    "Error al crear usuario: "
                            + e.getMessage()
            );
            return false;
        }
    }

    /**
     * Busca un usuario por su identificador.
     *
     * @param idUsuario identificador del usuario
     * @return usuario encontrado o null si no existe
     */
    public Usuario obtenerUsuarioPorId(int idUsuario) {

        String sql = "SELECT id, nombre, rut, correo, " +
                "contraseña, rol " +
                "FROM usuarios WHERE id = ?";

        try {
            Connection connection =
                    DatabaseConnection.getInstance();

            try (PreparedStatement statement =
                         connection.prepareStatement(sql)) {

                statement.setInt(1, idUsuario);

                try (ResultSet resultSet =
                             statement.executeQuery()) {

                    if (resultSet.next()) {

                        return new Usuario(
                                resultSet.getInt("id"),
                                resultSet.getString("nombre"),
                                resultSet.getString("rut"),
                                resultSet.getString("correo"),
                                resultSet.getString("contraseña"),
                                resultSet.getString("rol")
                        );
                    }
                }
            }

        } catch (SQLException e) {
            System.out.println(
                    "Error al obtener usuario: "
                            + e.getMessage()
            );
        }

        return null;
    }

    /**
     * Obtiene todos los usuarios registrados.
     *
     * @return lista de usuarios
     */
    public List<Usuario> listarUsuarios() {

        List<Usuario> usuarios = new ArrayList<>();

        String sql = "SELECT id, nombre, rut, correo, " +
                "contraseña, rol " +
                "FROM usuarios";

        try {
            Connection connection =
                    DatabaseConnection.getInstance();

            try (PreparedStatement statement =
                         connection.prepareStatement(sql);
                 ResultSet resultSet =
                         statement.executeQuery()) {

                while (resultSet.next()) {

                    Usuario usuario = new Usuario(
                            resultSet.getInt("id"),
                            resultSet.getString("nombre"),
                            resultSet.getString("rut"),
                            resultSet.getString("correo"),
                            resultSet.getString("contraseña"),
                            resultSet.getString("rol")
                    );

                    usuarios.add(usuario);
                }
            }

        } catch (SQLException e) {
            System.out.println(
                    "Error al listar usuarios: "
                            + e.getMessage()
            );
        }

        return usuarios;
    }

    /**
     * Actualiza los datos modificables de un usuario.
     *
     * @param usuario usuario con los datos actualizados
     * @return true si el usuario fue actualizado correctamente
     */
    public boolean actualizarUsuario(Usuario usuario) {

        String sql = "UPDATE usuarios SET " +
                "nombre = ?, correo = ?, contraseña = ? " +
                "WHERE id = ?";

        try {
            Connection connection =
                    DatabaseConnection.getInstance();

            try (PreparedStatement statement =
                         connection.prepareStatement(sql)) {

                statement.setString(
                        1, usuario.getNombreUsuario()
                );
                statement.setString(
                        2, usuario.getCorreoUsuario()
                );
                statement.setString(
                        3, usuario.getContrasena()
                );
                statement.setInt(
                        4, usuario.getIdUsuario()
                );

                return statement.executeUpdate() > 0;
            }

        } catch (SQLException e) {
            System.out.println(
                    "Error al actualizar usuario: "
                            + e.getMessage()
            );
            return false;
        }
    }

    /**
     * Elimina un usuario de la base de datos.
     *
     * @param idUsuario identificador del usuario
     * @return true si el usuario fue eliminado correctamente
     */
    public boolean eliminarUsuario(int idUsuario) {

        String sql = "DELETE FROM usuarios WHERE id = ?";

        try {
            Connection connection =
                    DatabaseConnection.getInstance();

            try (PreparedStatement statement =
                         connection.prepareStatement(sql)) {

                statement.setInt(1, idUsuario);

                return statement.executeUpdate() > 0;
            }

        } catch (SQLException e) {
            System.out.println(
                    "Error al eliminar usuario: "
                            + e.getMessage()
            );
            return false;
        }
    }

    /**
     * Busca un usuario utilizando su correo y contraseña.
     *
     * @param correo correo del usuario
     * @param contrasena contraseña del usuario
     * @return usuario encontrado o null si las credenciales no son válidas
     */
    public Usuario buscarPorCredenciales(
            String correo,
            String contrasena) {

        String sql = "SELECT id, nombre, rut, correo, " +
                "contraseña, rol " +
                "FROM usuarios " +
                "WHERE correo = ? AND contraseña = ?";

        try {
            Connection connection =
                    DatabaseConnection.getInstance();

            try (PreparedStatement statement =
                         connection.prepareStatement(sql)) {

                statement.setString(1, correo);
                statement.setString(2, contrasena);

                try (ResultSet resultSet =
                             statement.executeQuery()) {

                    if (resultSet.next()) {

                        return new Usuario(
                                resultSet.getInt("id"),
                                resultSet.getString("nombre"),
                                resultSet.getString("rut"),
                                resultSet.getString("correo"),
                                resultSet.getString("contraseña"),
                                resultSet.getString("rol")
                        );
                    }
                }
            }

        } catch (SQLException e) {
            System.out.println(
                    "Error al buscar usuario: "
                            + e.getMessage()
            );
        }

        return null;
    }
}