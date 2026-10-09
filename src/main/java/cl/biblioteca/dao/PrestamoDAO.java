package cl.biblioteca.dao;

import cl.biblioteca.conexion.DatabaseConnection;
import cl.biblioteca.modelo.Categoria;
import cl.biblioteca.modelo.Estudiante;
import cl.biblioteca.modelo.Libro;
import cl.biblioteca.modelo.Prestamo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

/**
 * DAO encargado de gestionar las operaciones de los préstamos
 * en la base de datos.
 *
 * @author Consuelo Martinez
 */
public class PrestamoDAO {

    /**
     * Registra un nuevo préstamo en la base de datos.
     *
     * @param prestamo préstamo que se desea registrar
     * @return true si el préstamo fue registrado correctamente
     */
    public boolean crearPrestamo(Prestamo prestamo) {

        try {
            Connection connection = DatabaseConnection.getInstance();
            return crearPrestamo(connection, prestamo);

        } catch (SQLException e) {
            System.out.println("Error al crear préstamo: "
                    + e.getMessage());
            return false;
        }
    }

    /**
     * Registra un préstamo utilizando una conexión existente.
     *
     * @param connection conexión activa con la base de datos
     * @param prestamo préstamo que se desea registrar
     * @return true si el préstamo fue registrado correctamente
     * @throws SQLException si ocurre un error durante la operación
     */
    public boolean crearPrestamo(
            Connection connection,
            Prestamo prestamo) throws SQLException {

        String sql = "INSERT INTO prestamos " +
                "(id_estudiante, id_libro, fecha_prestamo, " +
                "fecha_devolucion, devuelto) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(
                    1,
                    prestamo.getEstudiante().getIdEstudiante()
            );

            statement.setInt(
                    2,
                    prestamo.getLibro().getIdLibro()
            );

            statement.setDate(
                    3,
                    java.sql.Date.valueOf(
                            prestamo.getFechaPrestamo()
                    )
            );

            if (prestamo.getFechaDevolucion() != null) {

                statement.setDate(
                        4,
                        java.sql.Date.valueOf(
                                prestamo.getFechaDevolucion()
                        )
                );

            } else {

                statement.setNull(
                        4,
                        java.sql.Types.DATE
                );
            }

            statement.setBoolean(
                    5,
                    prestamo.isDevuelto()
            );

            return statement.executeUpdate() > 0;
        }
    }

    /**
     * Busca un préstamo por su identificador.
     *
     * @param idPrestamo identificador del préstamo
     * @return préstamo encontrado o null si no existe
     */
    public Prestamo obtenerPrestamoPorId(int idPrestamo) {

        String sql = "SELECT p.id, " +
                "p.fecha_prestamo, p.fecha_devolucion, p.devuelto, " +
                "e.id AS id_estudiante, e.nombre AS nombre_estudiante, " +
                "e.rut AS rut_estudiante, e.curso, " +
                "e.correo AS correo_estudiante, " +
                "l.id AS id_libro, l.titulo, l.autor, l.isbn, " +
                "l.editorial, l.stock, " +
                "c.id AS id_categoria, c.nombre AS nombre_categoria " +
                "FROM prestamos p " +
                "INNER JOIN estudiantes e ON p.id_estudiante = e.id " +
                "INNER JOIN libros l ON p.id_libro = l.id " +
                "INNER JOIN categorias c ON l.id_categoria = c.id " +
                "WHERE p.id = ?";

        try {
            Connection connection = DatabaseConnection.getInstance();

            try (PreparedStatement statement =
                         connection.prepareStatement(sql)) {

                statement.setInt(1, idPrestamo);

                try (ResultSet resultSet = statement.executeQuery()) {

                    if (resultSet.next()) {

                        Categoria categoria = new Categoria(
                                resultSet.getInt("id_categoria"),
                                resultSet.getString("nombre_categoria")
                        );

                        Libro libro = new Libro(
                                resultSet.getInt("id_libro"),
                                resultSet.getString("titulo"),
                                resultSet.getString("autor"),
                                resultSet.getString("isbn"),
                                resultSet.getString("editorial"),
                                resultSet.getInt("stock"),
                                categoria
                        );

                        Estudiante estudiante = new Estudiante(
                                resultSet.getInt("id_estudiante"),
                                resultSet.getString("nombre_estudiante"),
                                resultSet.getString("rut_estudiante"),
                                resultSet.getString("curso"),
                                resultSet.getString("correo_estudiante")
                        );

                        java.sql.Date fechaDevolucion =
                                resultSet.getDate("fecha_devolucion");

                        return new Prestamo(
                                resultSet.getInt("id"),
                                estudiante,
                                libro,
                                resultSet.getDate("fecha_prestamo")
                                        .toLocalDate(),
                                fechaDevolucion != null
                                        ? fechaDevolucion.toLocalDate()
                                        : null,
                                resultSet.getBoolean("devuelto")
                        );
                    }
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener préstamo: "
                    + e.getMessage());
        }

        return null;
    }

    /**
     * Obtiene todos los préstamos registrados en la base de datos.
     *
     * @return lista de préstamos registrados
     */
    public List<Prestamo> listarPrestamos() {

        List<Prestamo> prestamos = new ArrayList<>();

        String sql = "SELECT p.id, " +
                "p.fecha_prestamo, p.fecha_devolucion, p.devuelto, " +
                "e.id AS id_estudiante, " +
                "e.nombre AS nombre_estudiante, " +
                "e.rut AS rut_estudiante, " +
                "e.curso AS curso_estudiante, " +
                "e.correo AS correo_estudiante, " +
                "l.id AS id_libro, " +
                "l.titulo AS titulo_libro, " +
                "l.autor AS autor_libro, " +
                "l.isbn AS isbn_libro, " +
                "l.editorial AS editorial_libro, " +
                "l.stock AS stock_libro, " +
                "c.id AS id_categoria, " +
                "c.nombre AS nombre_categoria " +
                "FROM prestamos p " +
                "INNER JOIN estudiantes e ON p.id_estudiante = e.id " +
                "INNER JOIN libros l ON p.id_libro = l.id " +
                "INNER JOIN categorias c ON l.id_categoria = c.id " +
                "ORDER BY p.id";

        try {
            Connection connection = DatabaseConnection.getInstance();

            try (PreparedStatement statement =
                         connection.prepareStatement(sql);
                 ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {

                    Estudiante estudiante = new Estudiante(
                            resultSet.getInt("id_estudiante"),
                            resultSet.getString("nombre_estudiante"),
                            resultSet.getString("rut_estudiante"),
                            resultSet.getString("curso_estudiante"),
                            resultSet.getString("correo_estudiante")
                    );

                    Categoria categoria = new Categoria(
                            resultSet.getInt("id_categoria"),
                            resultSet.getString("nombre_categoria")
                    );

                    Libro libro = new Libro(
                            resultSet.getInt("id_libro"),
                            resultSet.getString("titulo_libro"),
                            resultSet.getString("autor_libro"),
                            resultSet.getString("isbn_libro"),
                            resultSet.getString("editorial_libro"),
                            resultSet.getInt("stock_libro"),
                            categoria
                    );

                    Prestamo prestamo = new Prestamo(
                            resultSet.getInt("id"),
                            estudiante,
                            libro,
                            resultSet.getDate("fecha_prestamo")
                                    .toLocalDate(),
                            resultSet.getDate("fecha_devolucion") != null
                                    ? resultSet.getDate("fecha_devolucion")
                                    .toLocalDate()
                                    : null,
                            resultSet.getBoolean("devuelto")
                    );

                    prestamos.add(prestamo);
                }
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error al listar préstamos: " + e.getMessage()
            );
        }

        return prestamos;
    }

    /**
     * Cuenta los préstamos que todavía no han sido devueltos.
     *
     * @return cantidad de préstamos activos
     */
    public int contarPrestamosActivos() {

        String sql =
                "SELECT COUNT(*) FROM prestamos WHERE devuelto = false";

        try {
            Connection connection = DatabaseConnection.getInstance();

            try (PreparedStatement statement =
                         connection.prepareStatement(sql);
                 ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {
                    return resultSet.getInt(1);
                }
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error al contar préstamos activos: "
                            + e.getMessage()
            );
        }

        return 0;
    }

    /**
     * Cuenta los préstamos que ya fueron devueltos.
     *
     * @return cantidad de préstamos devueltos
     */
    public int contarPrestamosDevueltos() {

        String sql =
                "SELECT COUNT(*) FROM prestamos WHERE devuelto = true";

        try {
            Connection connection = DatabaseConnection.getInstance();

            try (PreparedStatement statement =
                         connection.prepareStatement(sql);
                 ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {
                    return resultSet.getInt(1);
                }
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error al contar préstamos devueltos: "
                            + e.getMessage()
            );
        }

        return 0;
    }

    /**
     * Marca un préstamo como devuelto.
     *
     * @param idPrestamo identificador del préstamo
     * @return true si la devolución fue registrada correctamente
     */
    public boolean registrarDevolucion(int idPrestamo) {

        try {
            Connection connection = DatabaseConnection.getInstance();
            return registrarDevolucion(connection, idPrestamo);

        } catch (SQLException e) {
            System.out.println("Error al registrar devolución: "
                    + e.getMessage());
            return false;
        }
    }

    /**
     * Marca un préstamo como devuelto utilizando una conexión existente
     * y registra la fecha actual de devolución.
     *
     * @param connection conexión utilizada para la transacción
     * @param idPrestamo identificador del préstamo
     * @return true si la devolución fue registrada correctamente
     * @throws SQLException si ocurre un error en la operación
     */
    public boolean registrarDevolucion(
            Connection connection, int idPrestamo) throws SQLException {

        String sql = "UPDATE prestamos " +
                "SET devuelto = ?, fecha_devolucion = CURDATE() " +
                "WHERE id = ?";

        try (PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setBoolean(1, true);
            statement.setInt(2, idPrestamo);

            return statement.executeUpdate() > 0;
        }
    }

    /**
     * Obtiene los préstamos de un estudiante específico.
     *
     * @param idEstudiante identificador del estudiante
     * @return lista de préstamos del estudiante
     */
    public List<Prestamo> listarPrestamosPorEstudiante(int idEstudiante) {

        List<Prestamo> prestamos = new ArrayList<>();

        String sql = """
                SELECT p.id,
                       p.fecha_prestamo,
                       p.fecha_devolucion,
                       p.devuelto,
                       e.id AS estudiante_id,
                       e.nombre AS estudiante_nombre,
                       e.rut AS estudiante_rut,
                       e.curso AS estudiante_curso,
                       e.correo AS estudiante_correo,
                       l.id AS libro_id,
                       l.titulo,
                       l.autor,
                       l.isbn,
                       l.editorial,
                       l.stock,
                       c.id AS categoria_id,
                       c.nombre AS categoria_nombre
                FROM prestamos p
                INNER JOIN estudiantes e
                    ON p.id_estudiante = e.id
                INNER JOIN libros l
                    ON p.id_libro = l.id
                INNER JOIN categorias c
                    ON l.id_categoria = c.id
                WHERE e.id = ?
                ORDER BY p.id DESC
                """;

        try {
            Connection connection = DatabaseConnection.getInstance();

            try (PreparedStatement ps =
                         connection.prepareStatement(sql)) {

                ps.setInt(1, idEstudiante);

                try (ResultSet rs = ps.executeQuery()) {

                    while (rs.next()) {

                        Estudiante estudiante = new Estudiante(
                                rs.getInt("estudiante_id"),
                                rs.getString("estudiante_nombre"),
                                rs.getString("estudiante_rut"),
                                rs.getString("estudiante_curso"),
                                rs.getString("estudiante_correo")
                        );

                        Categoria categoria = new Categoria(
                                rs.getInt("categoria_id"),
                                rs.getString("categoria_nombre")
                        );

                        Libro libro = new Libro(
                                rs.getInt("libro_id"),
                                rs.getString("titulo"),
                                rs.getString("autor"),
                                rs.getString("isbn"),
                                rs.getString("editorial"),
                                rs.getInt("stock"),
                                categoria
                        );

                        Prestamo prestamo = new Prestamo(
                                rs.getInt("id"),
                                estudiante,
                                libro,
                                rs.getDate("fecha_prestamo")
                                        .toLocalDate(),
                                rs.getDate("fecha_devolucion") != null
                                        ? rs.getDate("fecha_devolucion")
                                        .toLocalDate()
                                        : null,
                                rs.getBoolean("devuelto")
                        );

                        prestamos.add(prestamo);
                    }
                }
            }

        } catch (SQLException e) {
            System.out.println(
                    "Error al listar préstamos del estudiante: "
                            + e.getMessage()
            );
        }

        return prestamos;
    }

    /**
     * Obtiene los libros ordenados desde el más prestado
     * hasta el menos prestado.
     *
     * @return lista con título y cantidad de préstamos
     */
    public List<Object[]> listarLibrosMasPrestados() {

        List<Object[]> resultados = new ArrayList<>();

        String sql = """
            SELECT l.titulo, COUNT(p.id) AS cantidad
            FROM prestamos p
            INNER JOIN libros l
                ON p.id_libro = l.id
            GROUP BY l.id, l.titulo
            ORDER BY cantidad DESC
            """;

        try {
            Connection connection = DatabaseConnection.getInstance();

            try (PreparedStatement statement =
                         connection.prepareStatement(sql);
                 ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {

                    resultados.add(
                            new Object[]{
                                    resultSet.getString("titulo"),
                                    resultSet.getInt("cantidad")
                            }
                    );
                }
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error al obtener libros más prestados: "
                            + e.getMessage()
            );
        }

        return resultados;
    }

    /**
     * Obtiene los préstamos que todavía están activos.
     *
     * @return lista de préstamos activos
     */
    public List<Prestamo> listarPrestamosActivos() {

        List<Prestamo> prestamos = new ArrayList<>();

        String sql = """
            SELECT p.id,
                   p.fecha_prestamo,
                   p.fecha_devolucion,
                   p.devuelto,
                   e.id AS id_estudiante,
                   e.nombre AS nombre_estudiante,
                   e.rut AS rut_estudiante,
                   e.curso AS curso_estudiante,
                   e.correo AS correo_estudiante,
                   l.id AS id_libro,
                   l.titulo AS titulo_libro,
                   l.autor AS autor_libro,
                   l.isbn AS isbn_libro,
                   l.editorial AS editorial_libro,
                   l.stock AS stock_libro,
                   c.id AS id_categoria,
                   c.nombre AS nombre_categoria
            FROM prestamos p
            INNER JOIN estudiantes e
                ON p.id_estudiante = e.id
            INNER JOIN libros l
                ON p.id_libro = l.id
            INNER JOIN categorias c
                ON l.id_categoria = c.id
            WHERE p.devuelto = false
            ORDER BY p.fecha_prestamo DESC
            """;

        try {
            Connection connection = DatabaseConnection.getInstance();

            try (PreparedStatement statement =
                         connection.prepareStatement(sql);
                 ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {

                    Categoria categoria = new Categoria(
                            resultSet.getInt("id_categoria"),
                            resultSet.getString("nombre_categoria")
                    );

                    Libro libro = new Libro(
                            resultSet.getInt("id_libro"),
                            resultSet.getString("titulo_libro"),
                            resultSet.getString("autor_libro"),
                            resultSet.getString("isbn_libro"),
                            resultSet.getString("editorial_libro"),
                            resultSet.getInt("stock_libro"),
                            categoria
                    );

                    Estudiante estudiante = new Estudiante(
                            resultSet.getInt("id_estudiante"),
                            resultSet.getString("nombre_estudiante"),
                            resultSet.getString("rut_estudiante"),
                            resultSet.getString("curso_estudiante"),
                            resultSet.getString("correo_estudiante")
                    );

                    Prestamo prestamo = new Prestamo(
                            resultSet.getInt("id"),
                            estudiante,
                            libro,
                            resultSet.getDate("fecha_prestamo")
                                    .toLocalDate(),
                            resultSet.getDate("fecha_devolucion") != null
                                    ? resultSet.getDate("fecha_devolucion")
                                    .toLocalDate()
                                    : null,
                            resultSet.getBoolean("devuelto")
                    );

                    prestamos.add(prestamo);
                }
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error al obtener préstamos activos: "
                            + e.getMessage()
            );
        }

        return prestamos;
    }
}