/**
 * Aplicación para usar las clases Contenedor y Contenido de forma genérica
 * @author Víctor Rivas <vrivas@ujaen.es>
 * @date 01-abr-2024
 * @date Modificado 9-abr-2025 para adaptarlo a JUnit5
 */

package es.vrivas.dagil;

import java.sql.SQLException;

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

        // Inicio datos de prueba de registros horarios leyendo desde la base de datos.
        try {
            registrosHorarios.leerDesdeBBDD();
        } catch (SQLException e) {
            System.out.println("Error al acceder a la base de datos: " + e.getMessage());
            return;
        }
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
