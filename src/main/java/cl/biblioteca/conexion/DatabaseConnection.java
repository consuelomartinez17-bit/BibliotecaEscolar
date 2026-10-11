package cl.biblioteca.conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase encargada de realizar la conexión con la base de datos MySQL.
 * Utiliza el patrón Singleton para reutilizar una misma conexión.
 *
 * @author Consuelo Martinez
 */
public class DatabaseConnection {

    private static final String URL =
            "jdbc:mysql://localhost:3306/biblioteca";
    private static final String USER = "root";
    private static final String PASSWORD =
            System.getenv("BIBLIOTECA_DB_PASSWORD");

    private static Connection connection;

    /**
     * Constructor privado para evitar crear objetos de esta clase.
     */
    private DatabaseConnection() {
    }

    /**
     * Obtiene la conexión con la base de datos.
     * Si no existe una conexión o esta se encuentra cerrada,
     * se crea una nueva.
     *
     * @return conexión a la base de datos
     * @throws SQLException si ocurre un error al conectar
     */
    public static synchronized Connection getInstance() throws SQLException {

        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(
                    URL,
                    USER,
                    PASSWORD
            );
        }

        return connection;
    }
}