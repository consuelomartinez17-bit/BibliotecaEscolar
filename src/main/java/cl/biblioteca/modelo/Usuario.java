package cl.biblioteca.modelo;

/**
 * Representa a un usuario del sistema de biblioteca.
 * Contiene los datos utilizados para la autenticación
 * y definición de su rol.
 *
 * @author Consuelo Martinez
 */
public class Usuario {

    private int idUsuario;
    private String nombreUsuario;
    private String rutUsuario;
    private String correoUsuario;
    private String contrasena;
    private String rolUsuario;

    /**
     * Constructor vacío.
     */
    public Usuario() {
    }

    /**
     * Constructor con todos los datos del usuario.
     *
     * @param idUsuario identificador del usuario
     * @param nombreUsuario nombre del usuario
     * @param rutUsuario RUT del usuario
     * @param correoUsuario correo electrónico
     * @param contrasena contraseña del usuario
     * @param rolUsuario rol del usuario en el sistema
     */
    public Usuario(int idUsuario, String nombreUsuario, String rutUsuario,
                   String correoUsuario, String contrasena, String rolUsuario) {
        this.idUsuario = idUsuario;
        this.nombreUsuario = nombreUsuario;
        this.rutUsuario = rutUsuario;
        this.correoUsuario = correoUsuario;
        this.contrasena = contrasena;
        this.rolUsuario = rolUsuario;
    }

    // Getters

    public int getIdUsuario() {
        return idUsuario;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public String getRutUsuario() {
        return rutUsuario;
    }

    public String getCorreoUsuario() {
        return correoUsuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public String getRolUsuario() {
        return rolUsuario;
    }

    // Setters

    /**
     * Permite actualizar el nombre del usuario.
     *
     * @param nombreUsuario nuevo nombre
     */
    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    /**
     * Permite actualizar el correo del usuario.
     *
     * @param correoUsuario nuevo correo
     */
    public void setCorreoUsuario(String correoUsuario) {
        this.correoUsuario = correoUsuario;
    }

    /**
     * Permite actualizar la contraseña del usuario.
     *
     * @param contrasena nueva contraseña
     */
    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    /**
     * Retorna una representación legible del usuario.
     *
     * @return datos principales del usuario
     */
    @Override
    public String toString() {
        return "Usuario{" +
                "idUsuario=" + idUsuario +
                ", nombreUsuario='" + nombreUsuario + '\'' +
                ", rutUsuario='" + rutUsuario + '\'' +
                ", correoUsuario='" + correoUsuario + '\'' +
                ", rolUsuario='" + rolUsuario + '\'' +
                '}';
    }
}