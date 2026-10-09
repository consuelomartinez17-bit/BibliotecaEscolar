package cl.biblioteca.modelo;

/**
 * Representa a un estudiante registrado en el sistema de biblioteca.
 *
 * @author Consuelo Martinez
 */
public class Estudiante {

    private int idEstudiante;
    private String nombreEstudiante;
    private String rutEstudiante;
    private String curso;
    private String correoEstudiante;

    /**
     * Constructor vacío.
     */
    public Estudiante() {
    }

    /**
     * Constructor con todos los datos del estudiante.
     *
     * @param idEstudiante identificador del estudiante
     * @param nombreEstudiante nombre del estudiante
     * @param rutEstudiante RUT del estudiante
     * @param curso curso del estudiante
     * @param correoEstudiante correo electrónico
     */
    public Estudiante(int idEstudiante, String nombreEstudiante,
                      String rutEstudiante, String curso,
                      String correoEstudiante) {
        this.idEstudiante = idEstudiante;
        this.nombreEstudiante = nombreEstudiante;
        this.rutEstudiante = rutEstudiante;
        this.curso = curso;
        this.correoEstudiante = correoEstudiante;
    }

    // Getters

    public int getIdEstudiante() {
        return idEstudiante;
    }

    public String getNombreEstudiante() {
        return nombreEstudiante;
    }

    public String getRutEstudiante() {
        return rutEstudiante;
    }

    public String getCurso() {
        return curso;
    }

    public String getCorreoEstudiante() {
        return correoEstudiante;
    }

    // Setters

    /**
     * Permite actualizar el nombre del estudiante.
     *
     * @param nombreEstudiante nuevo nombre
     */
    public void setNombreEstudiante(String nombreEstudiante) {
        this.nombreEstudiante = nombreEstudiante;
    }

    /**
     * Permite actualizar el curso del estudiante.
     *
     * @param curso nuevo curso
     */
    public void setCurso(String curso) {
        this.curso = curso;
    }

    /**
     * Permite actualizar el correo del estudiante.
     *
     * @param correoEstudiante nuevo correo
     */
    public void setCorreoEstudiante(String correoEstudiante) {
        this.correoEstudiante = correoEstudiante;
    }

    /**
     * Retorna una representación legible del estudiante.
     *
     * @return datos del estudiante
     */
    @Override
    public String toString() {
        return "Estudiante{" +
                "idEstudiante=" + idEstudiante +
                ", nombreEstudiante='" + nombreEstudiante + '\'' +
                ", rutEstudiante='" + rutEstudiante + '\'' +
                ", curso='" + curso + '\'' +
                ", correoEstudiante='" + correoEstudiante + '\'' +
                '}';
    }
}