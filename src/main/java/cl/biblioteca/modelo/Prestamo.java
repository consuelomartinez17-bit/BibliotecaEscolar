package cl.biblioteca.modelo;

import java.time.LocalDate;

/**
 * Representa un préstamo realizado en el sistema de biblioteca.
 * Relaciona un estudiante con un libro y registra las fechas
 * y el estado del préstamo.
 *
 * @author Consuelo Martinez
 */
public class Prestamo {

    private int idPrestamo;
    private Estudiante estudiante;
    private Libro libro;
    private LocalDate fechaPrestamo;
    private LocalDate fechaDevolucion;
    private boolean devuelto;
    private static final int DIAS_PRESTAMO = 7;

    /**
     * Constructor vacío.
     */
    public Prestamo() {
    }

    /**
     * Constructor con todos los datos del préstamo.
     *
     * @param idPrestamo identificador del préstamo
     * @param estudiante estudiante que realiza el préstamo
     * @param libro libro prestado
     * @param fechaPrestamo fecha en que se realiza el préstamo
     * @param fechaDevolucion fecha de devolución
     * @param devuelto indica si el libro fue devuelto
     */
    public Prestamo(int idPrestamo, Estudiante estudiante, Libro libro,
                    LocalDate fechaPrestamo, LocalDate fechaDevolucion,
                    boolean devuelto) {
        this.idPrestamo = idPrestamo;
        this.estudiante = estudiante;
        this.libro = libro;
        this.fechaPrestamo = fechaPrestamo;
        this.fechaDevolucion = fechaDevolucion;
        this.devuelto = devuelto;
    }

    // Getters

    public int getIdPrestamo() {
        return idPrestamo;
    }

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public Libro getLibro() {
        return libro;
    }

    public LocalDate getFechaPrestamo() {
        return fechaPrestamo;
    }

    public LocalDate getFechaDevolucion() {
        return fechaDevolucion;
    }

    public boolean isDevuelto() {
        return devuelto;
    }

    /**
     * Calcula la fecha de vencimiento del préstamo.
     *
     * @return fecha de vencimiento
     */
    public LocalDate getFechaVencimiento() {

        return fechaPrestamo.plusDays(DIAS_PRESTAMO);
    }

    /**
     * Determina si el préstamo está atrasado.
     *
     * @return true si está atrasado
     */
    public boolean estaAtrasado() {

        if (devuelto && fechaDevolucion != null) {

            return fechaDevolucion.isAfter(
                    getFechaVencimiento()
            );
        }

        return LocalDate.now().isAfter(
                getFechaVencimiento()
        );
    }

    // Métodos de comportamiento

    /**
     * Marca el préstamo como devuelto.
     */
    public void marcarComoDevuelto() {
        this.devuelto = true;
    }

    /**
     * Retorna una representación legible del préstamo.
     *
     * @return datos principales del préstamo
     */
    @Override
    public String toString() {
        return "Prestamo{" +
                "idPrestamo=" + idPrestamo +
                ", estudiante=" + estudiante +
                ", libro=" + libro +
                ", fechaPrestamo=" + fechaPrestamo +
                ", fechaDevolucion=" + fechaDevolucion +
                ", devuelto=" + devuelto +
                '}';
    }
}