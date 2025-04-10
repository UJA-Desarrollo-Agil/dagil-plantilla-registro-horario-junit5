package es.vrivas.dagil;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Clase de test para la clase Contenido.
 */
public class ContenidoTest {

    /**
     * Mensaje indicando que se inician los tests de la clase Contenido.
     */
    @BeforeAll
    public static void setUp() {
        System.out.println("Iniciando test clase Contenido...");
    }

    //---------------------------------------------------------------
    // Tests para el método getId
    //---------------------------------------------------------------

    /**
     * Comprueba que el id inicial es 0.
     */
    @Test
    public void getId_inicia_a_0() {
        assertEquals(0, new Contenido().getId());
    }

    /**
     * Comprueba que el id se puede establecer a otro valor.
     */
    @Test
    public void getId_inicia_a_otro_valor() {
        // Comprueba otro id
        Contenido objeto = new Contenido()
                .setId(1);
        assertEquals(1, objeto.getId());
    }

    //---------------------------------------------------------------
    // Tests para el método getDescripcion
    //---------------------------------------------------------------

    /**
     * Comprueba que la descripción inicial es "".
     */
    @Test
    public void getDescripcion_inicia_vacia() {
        // Comprueba que la descripción inicial es ""
        assertEquals("", new Contenido().getDescripcion());
    }

    /**
     * Comprueba que la descripción se puede establecer a otro valor.
     */
    @Test
    public void getDescripcion_inicia_a_otro_valor() {
        // Comprueba otra descripción
        Contenido objeto = new Contenido();
        objeto.setDescripcion("Descripción");
    }

    //---------------------------------------------------------------
    // Tests para el método setId
    //---------------------------------------------------------------

    /**
     * Comprueba que salta una excepción si se intenta establecer un id negativo.
     */
    @Test
    public void setId_excepciones() {
        // Comprueba que no se puede establecer un id negativo
        try {
            new Contenido().setId(-1);
            fail();
        } catch (IllegalArgumentException e) {
            System.err.println("Excepción lanzada: " + e.getMessage() + " para id negativo");
        }
    }

    /**
     * Comprueba que se puede establecer un id y que devuelve el propio objeto.
     */
    @Test
    public void setId_asigna_id_y_devuelve_this() {
        // Comprueba que establece el id y que devuelve el propio objeto
        Contenido objeto = new Contenido();
        assertSame(objeto, objeto.setId(1));
        assertEquals(1, objeto.getId());
    }

    //---------------------------------------------------------------
    // Tests para el método setDescripcion
    //---------------------------------------------------------------

    /**
     * Comprueba que salta una excepción si se intenta establecer una descripción nula o vacía.
     */
    @Test
    public void setDescripcion_excepciones() {
        // Comprueba que no se puede establecer una descripción nula
        Contenido objeto = new Contenido();
        try {
            objeto.setDescripcion(null);
            fail();
        } catch (IllegalArgumentException e) {
            System.err.println("Excepción lanzada: " + e.getMessage() + " para descripción nula");
        }

        // Comprueba que no se puede establecer una descripción vacía
        try {
            objeto.setDescripcion("");
            fail();
        } catch (IllegalArgumentException e) {
            System.err.println("Excepción lanzada: " + e.getMessage() + " para descripción vacía");
        }
    }

    /**
     * Comprueba que la descripción inicial es "".
     */
    @Test
    public void setDescripcion_descripcion_inicial_es_nula() {
        Contenido objeto = new Contenido();
        assertEquals("", objeto.getDescripcion());
    }

    /**
     * Comprueba que establece una descripción y que devuelve el mismo objeto.
     */
    @Test
    public void setDescripcion_asigna_descripcion_y_devuelve_this() {
        // Comprueba que establece una descripción y que devuelve el mismo objeto
        Contenido objeto = new Contenido();
        assertSame(objeto, objeto.setDescripcion("Descripción"));
        assertEquals("Descripción", objeto.getDescripcion());
    }

    //---------------------------------------------------------------
    // Tests para el método toString
    //---------------------------------------------------------------
    /**
     * Comprueba que devuelve un string con el objeto en formato JSON para un objeto con id 0 y descripción "".
     */
    @Test
    public void toString_constructor_por_defecto() {
        Contenido objeto = new Contenido();
        assertEquals("{id: 0, descripcion: ''}", objeto.toString());
    }

    /**
    * Comprueba que devuelve un string con el objeto en formato JSON.
    */
    @Test
    public void toString_objeto_con_datos() {
        // Comprueba que devuelve un string con el objeto en formato JSON para un objeto con id y descripción
        Contenido objeto = new Contenido();
        objeto.setId(1);
        objeto.setDescripcion("Descripción para testToString");
        assertEquals("{id: 1, descripcion: 'Descripción para testToString'}", objeto.toString());
    }

    /**
     * Mensaje indicando que se finalizan los tests de la clase Contenido.
     */
    @AfterAll
    public static void tearDown() {
        System.out.println("Finalizando test clase Contenido...");
    }
}
