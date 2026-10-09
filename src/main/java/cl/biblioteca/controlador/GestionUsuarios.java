package cl.biblioteca.controlador;

import cl.biblioteca.dao.UsuarioDAO;
import cl.biblioteca.modelo.Usuario;

/**
 * Clase encargada de gestionar las operaciones relacionadas
 * con los usuarios del sistema.
 *
 * @author Consuelo Martinez
 */
public class GestionUsuarios {

    private final UsuarioDAO usuarioDAO;

    /**
     * Constructor de la clase.
     */
    public GestionUsuarios() {
        this.usuarioDAO = new UsuarioDAO();
    }

    /**
     * Valida las credenciales de un usuario.
     *
     * @param correo correo utilizado para iniciar sesión
     * @param contrasena contraseña utilizada para iniciar sesión
     * @return usuario encontrado o null si las credenciales no son válidas
     */
    public Usuario iniciarSesion(String correo, String contrasena) {
        return usuarioDAO.buscarPorCredenciales(correo, contrasena);
    }
}
