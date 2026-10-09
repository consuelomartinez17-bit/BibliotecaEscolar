package cl.biblioteca.controlador;

import java.util.function.Consumer;

/**
 * Hilo encargado de registrar una devolución sin bloquear la interfaz.
 *
 * @author Consuelo Martinez
 */
public class RegistrarDevolucionThread extends Thread {

    private final GestionPrestamos gestionPrestamos;
    private final int idPrestamo;
    private final Consumer<Boolean> accionFinalizacion;

    /**
     * Constructor del hilo de registro de devoluciones.
     *
     * @param gestionPrestamos controlador encargado de la devolución
     * @param idPrestamo identificador del préstamo
     * @param accionFinalizacion acción que se ejecutará al terminar
     */
    public RegistrarDevolucionThread(
            GestionPrestamos gestionPrestamos,
            int idPrestamo,
            Consumer<Boolean> accionFinalizacion) {

        this.gestionPrestamos = gestionPrestamos;
        this.idPrestamo = idPrestamo;
        this.accionFinalizacion = accionFinalizacion;
    }

    /**
     * Ejecuta el registro de la devolución.
     */
    @Override
    public void run() {

        boolean devuelto =
                gestionPrestamos.registrarDevolucion(idPrestamo);

        if (devuelto) {
            System.out.println(
                    "Devolución registrada correctamente."
            );
        } else {
            System.out.println(
                    "No fue posible registrar la devolución."
            );
        }

        if (accionFinalizacion != null) {
            javax.swing.SwingUtilities.invokeLater(
                    () -> accionFinalizacion.accept(devuelto)
            );
        }
    }
}