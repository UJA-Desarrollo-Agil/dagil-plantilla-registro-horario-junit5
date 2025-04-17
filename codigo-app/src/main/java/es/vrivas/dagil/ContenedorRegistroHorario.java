package es.vrivas.dagil;

import es.vrivas.dagil.App.CONF;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Contenedor de objetos de tipo RegistroHorario.
 */
public class ContenedorRegistroHorario {

    /// ArrayList con el conjunto de objetos contenidos
    private ArrayList<RegistroHorario> objetosContenidos;

    /**
     * Constructor.
     */
    public ContenedorRegistroHorario() {
        objetosContenidos = new ArrayList<RegistroHorario>();
    }

    /**
     * Devuelve el número de objetos que hay en el contenedor.
     * @return Número de objetos contenidos en el contenedor.
     */
    public int tamanio() {
        return objetosContenidos.size();
    }

    /**
     * Devuelve el objeto que que hay en la posición indicada.
     * @param posicion Posición del objeto a devolver.
     * @return El objeto que hay en la posición indicada.
     * @exception IllegalArgumentException Si la posición no es válida.
     */
    public RegistroHorario getPorPosicion(final int posicion) {
        if (posicion < 0 || posicion >= objetosContenidos.size()) {
            throw new IllegalArgumentException(
                    "La posición debe estar entre 0 y " + (objetosContenidos.size() - 1));
        }
        return objetosContenidos.get(posicion);
    }

    /**
     * Devuelve un contenedor con los registros horarios que tienen el idPersona indicado.
     * @param idPersona Id de la persona cuyos registros hay que devolver.
     * @return El objeto que tiene el id indicado o null si no existe.
    */
    public ContenedorRegistroHorario getPorIdPersona(final int idPersona) {
        ContenedorRegistroHorario toReturn = new ContenedorRegistroHorario();
        for (int i = 0; i < objetosContenidos.size(); ++i) {
            if (objetosContenidos.get(i).getIdPersona() == idPersona) {
                try {
                    toReturn.add(objetosContenidos.get(i));
                } catch (IllegalArgumentException e) {
                    // No se añade el objeto al contenedor
                    throw new IllegalArgumentException("Error al añadir registro horario al conjunto de una persona: "
                            + e.getMessage());
                }
            }
        }
        return toReturn;
    }

    /**
     * Devuelve el conjunto de objetos que hay en el contenedor en forma de string.
     * @return Cadena con el conjunto de objetos que hay en el contenedor en formato JSON.
     */
    public final String toString() {
        String cadenaFinal = "{\nobjetosContenidos = [";
        for (int i = 0; i < objetosContenidos.size(); i++) {
            cadenaFinal += (i == 0 ? "\n" : "") + objetosContenidos.get(i).toString() + ",\n";
        }
        cadenaFinal += "]\n}";
        return cadenaFinal;
    }

    /**
     * Añade un objeto al contenedor.
     * @param objeto Objeto a añadir.
     * @exception IllegalArgumentException Si el objeto ya está en el contenedor.
     * @exception IllegalArgumentException Si el objeto es NULL.
     * @return La propia instancia de Contenedor.
     */
    public ContenedorRegistroHorario add(final RegistroHorario objeto) {
        if (objeto == null) {
            throw new IllegalArgumentException("El registro horario que se intenta añadir es NULL");
        }
        if (objetosContenidos.contains(objeto)) {
            throw new IllegalArgumentException("El registro horario ya está en el contenedor");
        }
        objetosContenidos.add(objeto);
        return this;
    }

    /**
     * Devuelve un contenedor con el conjunto de objetos que hay ordenados cronológicamente por fecha de entrada.
     * @return El conjunto de objetos ordenados cronológicamente por fecha de entrada.
     */
    public ContenedorRegistroHorario getOrdenadosEntrada() {
        ContenedorRegistroHorario toReturn = new ContenedorRegistroHorario();
        toReturn.objetosContenidos.addAll(objetosContenidos);
        toReturn.objetosContenidos.sort((r1, r2) -> r1.comparaEntrada(r2));
        return toReturn;
    }

    /**
     * Inserta en el contenedor los datos recogidos desde la base de datos.
     * @exception SQLException Si hay algún error al acceder a la base de datos.
     */
    public void leerDesdeBBDD() throws SQLException {
        // Vacio el contenedor
        objetosContenidos.clear();

        // Código necesario para establecer la conexión con la base de datos usando JDBC
        try {
            // Cargar el driver
            Class.forName(CONF.JDBC_DRIVER);

            // Conectar con la base de datos
            Connection conexion = DriverManager.getConnection(CONF.URL, CONF.DBUSER, CONF.PASSWORD);

            // Crear un objeto Statement (=sentencia) para realizar las consultas
            Statement statement = conexion.createStatement();

            // Ejecutar una consulta
            ResultSet resultado = statement.executeQuery("SELECT * FROM registrohorario");

            // Ir procesando los distintos registros que devuelve la consulta
            while (resultado.next()) {
                // Retrieve data from the result set
                int laPersona = resultado.getInt("idPersona");
                int laEmpresa = resultado.getInt("idEmpresa");
                // Para las fechas, tenemos que establecer la fecha y la hora por separado
                LocalDateTime laEntrada = resultado.getDate("entrada").toLocalDate()
                        .atTime(resultado.getTime("entrada").toLocalTime());
                LocalDateTime laSalida = resultado.getDate("salida").toLocalDate()
                        .atTime(resultado.getTime("salida").toLocalTime());
                objetosContenidos.add(new RegistroHorario(laPersona, laEmpresa, laEntrada, laSalida));
            }
            // Finalmente, cerrar la conexión junto con el resto de recursos utilizados
            resultado.close();
            statement.close();
            conexion.close();
        } catch (ClassNotFoundException | SQLException e) {
            throw new SQLException(
                    "ContenedorRegistriHorario:leerDesdeBBDD: Error al acceder a la base de datos: " + e.getMessage());
        }
    }
}
