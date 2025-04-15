package es.vrivas.dagil;

import java.util.ArrayList;

/**
 * Clase Contenedor.
 * Representa un contenedor de objetos de la clase Contenido.
 */
public class Contenedor {

    /// ArrayList con el conjunto de objetos contenidos.
    private ArrayList<Contenido> objetosContenidos;

    /**
     * Constructor.
     */
    public Contenedor() {
        objetosContenidos = new ArrayList<Contenido>();
    }

    /**
     * Devuelve el número de objetos que hay en el contenedor.
     * @return Número de objetos contenidos en el contenedor.
     */
    public int getNumObjetosContenidos() {
        return objetosContenidos.size();
    }

    /**
     * Devuelve el objeto que que hay en la posición indicada.
     * @param posicion Posición del objeto a devolver.
     * @return El objeto que hay en la posición indicada.
     * @exception IllegalArgumentException Si la posición no es válida.
     */
    public Contenido getPorPosicion(final int posicion) {
        if (posicion < 0 || posicion >= objetosContenidos.size()) {
            throw new IllegalArgumentException(
                    "La posición debe estar entre 0 y " + (objetosContenidos.size() - 1));
        }
        return objetosContenidos.get(posicion);
    }

    /**
     * Devuelve el objeto que tiene el id indicado.
     * @param id Id del objeto a devolver.
     * @return El objeto que tiene el id indicado o null si no existe.
    */
    public Contenido getPorId(final int id) {
        Contenido objeto = null;
        for (int i = 0; objeto == null && i < objetosContenidos.size(); ++i) {
            if (objetosContenidos.get(i).getId() == id) {
                objeto = objetosContenidos.get(i);
            }
        }
        return objeto;
    }

    /**
     * Devuelve el conjunto de objetos que hay en el contenedor en forma de string.
     * @return Cadena con el conjunto de objetos que hay en el contenedor en formato JSON.
     */
    public final String toString() {
        String cadenaFinal = "[";
        for (int i = 0; i < objetosContenidos.size(); i++) {
            cadenaFinal += (i == 0 ? "\n" : "") + objetosContenidos.get(i).toString() + ",\n";
        }
        cadenaFinal += "]";
        return cadenaFinal;
    }

    /**
     * Añade un objeto al contenedor.
     * @param objeto Objeto a añadir.
     * @exception IllegalArgumentException Si el objeto ya está en el contenedor.
     * @exception IllegalArgumentException Si ya hay un objeto con el mismo id en el contenedor.
     * @exception IllegalArgumentException Si el objeto es NULL.
     * @return La propia instancia de Contenedor.
     */
    public Contenedor add(final Contenido objeto) {
        if (objeto == null) {
            throw new IllegalArgumentException("El objeto que se intenta añadir es NULL");
        }
        if (objetosContenidos.contains(objeto)) {
            throw new IllegalArgumentException("El objeto ya está en el contenedor");
        }
        if (getPorId(objeto.getId()) != null) {
            throw new IllegalArgumentException("Ya hay un objeto con el mismo id en el contenedor");
        }
        objetosContenidos.add(objeto);
        return this;
    }
}
