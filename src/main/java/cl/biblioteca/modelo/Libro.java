package cl.biblioteca.modelo;

/**
 * Representa un libro registrado en el sistema de biblioteca.
 * Contiene la información del libro, su stock y la categoría a la que pertenece.
 *
 * @author Consuelo Martinez
 */
public class Libro extends ElementoBiblioteca implements Prestable {

    private int idLibro;
    private String tituloLibro;
    private String autorLibro;
    private String isbn;
    private String editorial;
    private int stock;
    private Categoria categoria;

    /**
     * Constructor vacío.
     */
    public Libro() {
        super(0);
    }

    /**
     * Constructor con todos los datos del libro.
     *
     * @param idLibro identificador del libro
     * @param tituloLibro título del libro
     * @param autorLibro autor del libro
     * @param isbn ISBN del libro
     * @param editorial editorial del libro
     * @param stock cantidad disponible
     * @param categoria categoría del libro
     */
    public Libro(int idLibro, String tituloLibro, String autorLibro,
                 String isbn, String editorial, int stock,
                 Categoria categoria) {

        super(idLibro);

        this.idLibro = idLibro;
        this.tituloLibro = tituloLibro;
        this.autorLibro = autorLibro;
        this.isbn = isbn;
        this.editorial = editorial;
        this.stock = stock;
        this.categoria = categoria;
    }

    // Getters

    public int getIdLibro() {
        return idLibro;
    }

    public String getTituloLibro() {
        return tituloLibro;
    }

    public String getAutorLibro() {
        return autorLibro;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getEditorial() {
        return editorial;
    }

    public int getStock() {
        return stock;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    /**
     * Disminuye en una unidad el stock disponible.
     * Se utiliza cuando se realiza un préstamo.
     */
    public void disminuirStock() {
        if (stock > 0) {
            stock--;
        }
    }

    /**
     * Aumenta en una unidad el stock disponible.
     * Se utiliza cuando se devuelve un libro.
     */
    public void aumentarStock() {
        stock++;
    }

    /**
     * Registra el préstamo del libro disminuyendo su stock.
     */
    @Override
    public void prestar() {
        disminuirStock();
    }

    /**
     * Registra la devolución del libro aumentando su stock.
     */
    @Override
    public void devolver() {
        aumentarStock();
    }

    /**
     * Obtiene una descripción del libro.
     *
     * @return título y autor del libro
     */
    @Override
    public String obtenerDescripcion() {
        return tituloLibro + " - " + autorLibro;
    }

    /**
     * Retorna una representación legible del libro.
     *
     * @return datos principales del libro
     */
    @Override
    public String toString() {
        return "Libro{" +
                "idLibro=" + idLibro +
                ", tituloLibro='" + tituloLibro + '\'' +
                ", autorLibro='" + autorLibro + '\'' +
                ", isbn='" + isbn + '\'' +
                ", editorial='" + editorial + '\'' +
                ", stock=" + stock +
                ", categoria=" + categoria +
                '}';
    }
}