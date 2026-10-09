package cl.biblioteca.controlador;

import cl.biblioteca.modelo.Prestamo;

import java.util.function.Consumer;

/**
 * Hilo encargado de registrar un préstamo sin bloquear la interfaz.
 *
 * @author Consuelo Martinez
 */
public class RegistrarPrestamoThread extends Thread {

    private final GestionPrestamos gestionPrestamos;
    private final Prestamo prestamo;
    private final Consumer<Boolean> accionFinalizacion;

    /**
     * Constructor del hilo de registro de préstamos.
     *
     * @param gestionPrestamos controlador encargado del préstamo
     * @param prestamo préstamo que se desea registrar
     * @param accionFinalizacion acción que se ejecutará al terminar
     */
    public RegistrarPrestamoThread(
            GestionPrestamos gestionPrestamos,
            Prestamo prestamo,
            Consumer<Boolean> accionFinalizacion) {

        this.gestionPrestamos = gestionPrestamos;
        this.prestamo = prestamo;
        this.accionFinalizacion = accionFinalizacion;
    }

    /**
     * Ejecuta el registro del préstamo.
     */
    @Override
    public void run() {

        boolean registrado =
                gestionPrestamos.registrarPrestamo(prestamo);

        if (registrado) {
            System.out.println(
                    "Préstamo registrado correctamente."
            );
        } else {
            System.out.println(
                    "No fue posible registrar el préstamo."
            );
        }

        if (accionFinalizacion != null) {
            javax.swing.SwingUtilities.invokeLater(
                    () -> accionFinalizacion.accept(registrado)
            );
        }
    }
}