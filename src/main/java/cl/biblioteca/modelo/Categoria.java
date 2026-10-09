package cl.biblioteca.modelo;

/**
 * Representa una categoría de libros dentro del sistema de biblioteca.
 *
 * @author Consuelo Martinez
 */
public class Categoria {

    private int idCategoria;
    private String nombreCategoria;

    /**
     * Constructor vacío.
     */
    public Categoria() {
    }

    /**
     * Constructor con los datos de la categoría.
     *
     * @param idCategoria identificador de la categoría
     * @param nombreCategoria nombre de la categoría
     */
    public Categoria(int idCategoria, String nombreCategoria) {
        this.idCategoria = idCategoria;
        this.nombreCategoria = nombreCategoria;
    }

    // Getters

    public int getIdCategoria() {
        return idCategoria;
    }

    public String getNombreCategoria() {
        return nombreCategoria;
    }

    // Setters

    /**
     * Permite actualizar el nombre de la categoría.
     *
     * @param nombreCategoria nuevo nombre de la categoría
     */
    public void setNombreCategoria(String nombreCategoria) {
        this.nombreCategoria = nombreCategoria;
    }

    /**
     * Retorna una representación legible de la categoría.
     *
     * @return datos de la categoría
     */
    @Override
    public String toString() {
        return "Categoria{" +
                "idCategoria=" + idCategoria +
                ", nombreCategoria='" + nombreCategoria + '\'' +
                '}';
    }
}