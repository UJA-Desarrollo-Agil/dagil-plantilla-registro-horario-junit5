/**
 * Aplicación para usar las clases Contenedor y Contenido de forma genérica
 * @author Víctor Rivas <vrivas@ujaen.es>
 * @date 01-abr-2024
 * @date Modificado 9-abr-2025 para adaptarlo a JUnit5
 */

package es.vrivas.dagil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;

/**
 * Clase principal de la aplicación.
 * Esta clase contiene el método main y el menú de la aplicación.
 */
public final class App {
    /**
     * Clase interna con las constantes con los datos de configuración de la aplicación.
     */
    public static final class CONF {
        /** Título de la aplicación. */
        public static final String TITULO = "Gestor de asistencia de trabajadores con acceso a BBDD";

        /** Autor/a de la aplicación. */
        public static final String AUTOR = "Víctor Rivas <vrivas@ujaen.es>";

        /** Motor SGDB.*/
        public static final String SGBD = "mysql";

        /** Ruta para JDBC driver. */
        public static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";

        /** Puerto del servidor SGBD. */
        public static final String PORT = "41006";

        /** Servidor SGBD. */
        public static final String SERVER = "localhost";

        /** Nombre de la base de datos. */
        public static final String DATABASE = "gestorasistencias";

        /** URL de conexión a la base de datos. */
        public static final String URL = "jdbc:" + SGBD + "://" + SERVER + ":" + PORT + "/" + DATABASE;

        /** Usuario de la base de datos. */
        public static final String DBUSER = "gestorasistencias";

        /** Contraseña de la base de datos. */
        public static final String PASSWORD = "patatafrita";
    } // Class CONF

    /** Un objeto contenedor de registros horarios. */
    private static ContenedorRegistroHorario registrosHorarios = new ContenedorRegistroHorario();

    /**
     * Constructor privado para que no pueda ser invocado.
     */
    private App() {
        // No se puede instanciar
    }

    /**
     * Descargo datos de la BBDD.
     */
    public static void descargar_RegistroHorarios() {

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
                registrosHorarios.add(new RegistroHorario(laPersona, laEmpresa, laEntrada, laSalida));
            }
            // Finalmente, cerrar la conexión junto con el resto de recursos utilizados
            resultado.close();
            statement.close();
            conexion.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Método para responder a la opción Mostrar registros horarios ordenados cronológicamente.
     * @param opcion Número de opción que representa en el menú
     */
    private static void mostrar_registros_horarios_ordenados_cronologicamente(int opcion) {
        System.out.println(
                "Opción " + opcion + ": Mostrar registros horarios de una persona ordenados cronológicamente\n");
        System.out.print("Indique el id de la persona: ");
        String idPersona = System.console().readLine();
        ContenedorRegistroHorario listaOrdenada = registrosHorarios.getOrdenadosEntrada();
        boolean hayAlguno = false;
        for (int i = 0; i < listaOrdenada.tamanio(); ++i) {
            RegistroHorario registro = (RegistroHorario) listaOrdenada.getPorPosicion(i);
            if (registro.getIdPersona() == Integer.parseInt(idPersona)) {
                hayAlguno = true;
                System.out.println(listaOrdenada.getPorPosicion(i));
            }
        }
        if (!hayAlguno) {
            System.out.println("No hay registros horarios para la persona con id " + idPersona);
        }

        System.out.println(); // Añado una línea al final, para separar el mensaje de pausa
    }

    /**
     * Método para mostrar el menú principal y leer la opción elegida.
     * @return Opción elegida por el usuario
     */
    private static int menu_principal() {
        final int numLineas = 5;
        for (int i = 0; i < numLineas; ++i) {
            System.out.println();
        }
        System.out.println("**** MENU ****");
        System.out.println("     ----");
        System.out.println("1. Mostrar registros horarios de una persona ordenados cronológicamente.");
        System.out.println("     ----");
        System.out.println("0. Terminar");
        System.out.println("--------------------");
        System.out.print("Introduzca una opción: ");
        String entrada = System.console().readLine();
        System.out.println();

        return Integer.parseInt(entrada);
    }

    /**
     * Método para pausar la ejecución hasta que el usuario pulse una tecla.
     */
    private static void pausa() {
        System.out.println("(Pulse una tecla para continuar...)");
        System.console().readLine();
    }

    /**
     * Función principal.
     * @param args Argumentos de la línea de comandos
     */
    public static void main(String[] args) {
        System.out.println("\n" + CONF.TITULO + "    (por " + CONF.AUTOR + ")");

        // Inicio datos de prueba de registros horarios
        descargar_RegistroHorarios();

        boolean salir = false;
        do {
            int opcion = menu_principal();
            switch (opcion) {
                case 0:
                    salir = true;
                    break;
                case 1:
                    mostrar_registros_horarios_ordenados_cronologicamente(opcion);
                    pausa();
                    break;
                default:
                    System.out.println("Opción no válida");
            }

        } while (!salir);
        System.out.println("Fin de la aplicación.");
    }
}
