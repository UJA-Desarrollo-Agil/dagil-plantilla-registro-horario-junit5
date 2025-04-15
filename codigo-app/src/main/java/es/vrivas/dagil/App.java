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

public class App {

    // Título de la aplicación
    private static final String TITULO = "Gestor de asistencia de trabajadores con acceso a BBDD";

    // Autor/a de la aplicación
    private static final String AUTOR = "Víctor Rivas <vrivas@ujaen.es>";

    /// Un objeto contenedor
    private static ContenedorRegistroHorario registrosHorarios = new ContenedorRegistroHorario();

    /**
     * Descargo datos de la BBDD
     */
    public static void descargar_RegistroHorarios() {
        { // Conexión a la base de datos
          // Variables para la conexión a JDBC
            String sgbd = "mysql";
            String puerto = "41006";
            String servidor = "localhost";
            String database = "gestorasistencias";
            String url = "jdbc:" + sgbd + "://" + servidor + ":" + puerto + "/" + database;
            // Es decir, URL = "jdbc:mysql://localhost:3306/asistencia";
            String usuario = "gestorasistencias";
            String password = "patatafrita";

            // Código necesario para establecer la conexión con la base de datos usando JDBC
            try {
                // Cargar el driver
                Class.forName("com.mysql.cj.jdbc.Driver");

                // Conectar con la base de datos
                Connection conexion = DriverManager.getConnection(url, usuario, password);

                // Crear un objeto Statement (=sentencia) para realizar las consultas
                Statement statement = conexion.createStatement();

                // Ejecutar una consulta
                ResultSet resultado = statement.executeQuery("SELECT * FROM registrohorario");

                // Ir procesando los distintos registros que devuelve la consulta
                while (resultado.next()) {
                    // Retrieve data from the result set
                    int la_persona = resultado.getInt("idPersona");
                    int la_empresa = resultado.getInt("idEmpresa");
                    // Para las fechas, tenemos que establecer la fecha y la hora por separado
                    LocalDateTime la_entrada = resultado.getDate("entrada").toLocalDate()
                            .atTime(resultado.getTime("entrada").toLocalTime());
                    LocalDateTime la_salida = resultado.getDate("salida").toLocalDate()
                            .atTime(resultado.getTime("salida").toLocalTime());
                    registrosHorarios.add(new RegistroHorario(la_persona, la_empresa, la_entrada, la_salida));
                }
                // Finalmente, cerrar la conexión junto con el resto de recursos utilizados
                resultado.close();
                statement.close();
                conexion.close();
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Método para responder a la opción Mostrar registros horarios ordenados
     * cronológicamente
     * 
     * @param opcion Número de opción que representa en el menú
     */
    private static void mostrar_registros_horarios_ordenados_cronologicamente(int opcion) {
        System.out.println(
                "Opción " + opcion + ": Mostrar registros horarios de una persona ordenados cronológicamente\n");
        System.out.print("Indique el id de la persona: ");
        String id_persona = System.console().readLine();
        ContenedorRegistroHorario listaOrdenada = registrosHorarios.getOrdenadosEntrada();
        boolean hayAlguno = false;
        for (int i = 0; i < listaOrdenada.tamanio(); ++i) {
            RegistroHorario registro = (RegistroHorario) listaOrdenada.getPorPosicion(i);
            if (registro.getIdPersona() == Integer.parseInt(id_persona)) {
                hayAlguno = true;
                System.out.println(listaOrdenada.getPorPosicion(i));
            }
        }
        if (!hayAlguno) {
            System.out.println("No hay registros horarios para la persona con id " + id_persona);
        }

        System.out.println(); // Añado una línea al final, para separar el mensaje de pausa
    }

    /**
     * Método para mostrar el menú principal y leer la opción elegida
     * 
     * @return Opción elegida por el usuario
     */
    private static int menu_principal() {
        for (int i = 0; i < 5; ++i) {
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
     * Método para pausar la ejecución hasta que el usuario pulse una tecla
     */
    private static void pausa() {
        System.out.println("(Pulse una tecla para continuar...)");
        System.console().readLine();
    }

    /**
     * Función principal
     * 
     * @param args Argumentos de la línea de comandos
     */
    public static void main(String[] args) {
        System.out.println("\n" + TITULO + "    (por " + AUTOR + ")");

        // Inicio datos de prueba de registros horarios
        descargar_RegistroHorarios();

        boolean salir = false;
        do {
            int opcion;
            switch (opcion = menu_principal()) {
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