package es.vrivas.dagil;

/**
 * Clase Contenido.
 * Representa un contenido con un identificador y una descripción.
 */
public class Contenido {
    /// Identificador.
    private int id;

    /// Un campo de texto, como ejemplo de contenido.
    private String descripcion;

    /**
     * Constructor.
     */
    public Contenido() {
        this.id = 0;
        this.descripcion = "";
    }

    /**
     * Devuelve el valor de id.
     * @return Atributo id.
     */
    public final int getId() {
        return id;
    }

    /**
     * Devuelve la descripcion.
     * @return Atributo descripcion.
     */
    public final String getDescripcion() {
        return descripcion;
    }

    /**
     * Establece el valor de id.
     * @param valor Valor para id.
     * @exception IllegalArgumentException Si el valor es negativo.
     * @return La propia instancia de Contenido.
     */
    public Contenido setId(final int valor) {
        if (valor < 0) {
            throw new IllegalArgumentException("El id del contenido no puede ser negativo");
        }
        this.id = valor;
        return this;
    }

    /**
     * Establece la descripción.
     * @param valor Valor de descripción.
     * @exception IllegalArgumentException Si la descripción es nula o vacía.
     * @return La propia instancia de Contenido.
     */
    public Contenido setDescripcion(final String valor) {
        if (valor == null || valor.isEmpty()) {
            throw new IllegalArgumentException("la descripción no puede ser vacía");
        }
        this.descripcion = valor;
        return this;
    }

    /**
     * Devuelve los atributos del Contenido en una cadena.
     * @return Cadena con los atributos del contenido en formato JSON.
     */
    public final String toString() {
        return "{id: " + id + ", descripcion: '" + descripcion + "'}";
    }
}
