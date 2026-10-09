package cl.biblioteca.modelo;

/**
 * Clase base para los elementos principales de la biblioteca.
 *
 * @author Consuelo Martinez
 */
public abstract class ElementoBiblioteca {

    private final int id;

    /**
     * Constructor de la clase base.
     *
     * @param id identificador del elemento
     */
    protected ElementoBiblioteca(int id) {
        this.id = id;
    }

    /**
     * Obtiene el identificador del elemento.
     *
     * @return identificador del elemento
     */
    public int getId() {
        return id;
    }

    /**
     * Obtiene una descripción del elemento.
     *
     * @return descripción del elemento
     */
    public abstract String obtenerDescripcion();
}