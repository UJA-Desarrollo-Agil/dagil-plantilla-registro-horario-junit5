package es.vrivas.dagil;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.fail;

import java.time.LocalDateTime;

public class ContenedorRegistroHorarioTest {
    @Test
    public void testConstructor() {
        ContenedorRegistroHorario contenedor = new ContenedorRegistroHorario();
        assertEquals(0, contenedor.tamanio());
    }

    @Test
    public void testAdd() {
        // Compruebo la excepción si se intenta añadir un objeto nulo
        try {
            ContenedorRegistroHorario contenedor = new ContenedorRegistroHorario();
            contenedor.add(null);
            fail("Debería haber lanzado una excepción al intentar añadir un objeto nulo");
        } catch (IllegalArgumentException e) {
            System.err.println("testAdd: Excepción capturada: " + e.getMessage());
        }

        ContenedorRegistroHorario contenedor = new ContenedorRegistroHorario();
        String entrada = "2020-03-04T05:06:00";
        String salida = "2027-08-09T10:11:00";
        RegistroHorario registro = new RegistroHorario(1, 101,
                LocalDateTime.parse(entrada), LocalDateTime.parse(salida));
        contenedor.add(registro);
        assertEquals(1, contenedor.tamanio());
        assertSame(registro, contenedor.getPorPosicion(0));

        // Compruebo la excepción si se intenta añadir un objeto que ya está en el
        // contenedor
        try {
            contenedor.add(registro);
            fail("Debería haber lanzado una excepción al intentar añadir un objeto que ya está en el contenedor");
        } catch (IllegalArgumentException e) {
            System.err.println("testAdd: Excepción capturada: " + e.getMessage());
        }
    }

    @Test
    public void testGetPorPosicion() {
        ContenedorRegistroHorario contenedor = new ContenedorRegistroHorario();
        String entrada = "2020-03-04T05:06:00";
        String salida = "2027-08-09T10:11:00";
        RegistroHorario registro = new RegistroHorario(1, 101,
                LocalDateTime.parse(entrada), LocalDateTime.parse(salida));
        contenedor.add(registro);
        assertSame(registro, contenedor.getPorPosicion(0));

        // Compruebo la excepción si se intenta acceder a una posición no válida
        try {
            contenedor.getPorPosicion(1);
            fail("Debería haber lanzado una excepción al intentar acceder a una posición no válida");
        } catch (IllegalArgumentException e) {
            System.err.println("testGetPorPosicion: Excepción capturada: " + e.getMessage());
        }
        try {
            contenedor.getPorPosicion(-1);
            fail("Debería haber lanzado una excepción al intentar acceder a una posición no válida");
        } catch (IllegalArgumentException e) {
            System.err.println("testGetPorPosicion: Excepción capturada: " + e.getMessage());
        }
    }

    @Test
    public void testGetPorIdPersona() {
        ContenedorRegistroHorario contenedor = new ContenedorRegistroHorario();
        RegistroHorario registro1 = new RegistroHorario(1, 101,
                LocalDateTime.parse("2021-01-01T08:00"), LocalDateTime.parse("2021-01-01T16:00"));
        RegistroHorario registro2 = new RegistroHorario(2, 101,
                LocalDateTime.parse("2022-02-02T10:00"), LocalDateTime.parse("2022-02-02T17:00"));
        RegistroHorario registro3 = new RegistroHorario(1, 101,
                LocalDateTime.parse("2021-01-03T12:20"), LocalDateTime.parse("2024-11-10T21:00"));
        contenedor.add(registro1);
        contenedor.add(registro2);
        contenedor.add(registro3);

        // Comprobamos que se devuelven los registros correctos para la persona 1
        try { // uso try porque getIdPorPersona puede lanzar una excepción, pero no para
              // probar la excepción
            ContenedorRegistroHorario contenedor2 = contenedor.getPorIdPersona(1);
            assertEquals(2, contenedor2.tamanio());
            assertSame(registro1, contenedor2.getPorPosicion(0));
            assertSame(registro3, contenedor2.getPorPosicion(1));

        } catch (IllegalArgumentException e) {
            System.err.println("testGetPorIdPersona: Excepción capturada: " + e.getMessage());
        }

        // Comprobamos que se devuelve el registro correcto para la persona 2
        try { // uso try porque getIdPorPersona puede lanzar una excepción, pero no para
              // probar la excepción
            ContenedorRegistroHorario contenedor3 = contenedor.getPorIdPersona(2);
            assertEquals(1, contenedor3.tamanio());
            assertSame(registro2, contenedor3.getPorPosicion(0));
        } catch (IllegalArgumentException e) {
            System.err.println("testGetPorIdPersona: Excepción capturada: " + e.getMessage());
        }

    }

    @Test
    public void testGetObjetosOrdenadosFecha_Hora_Entrada() {
        ContenedorRegistroHorario contenedor = new ContenedorRegistroHorario();
        RegistroHorario registro1 = new RegistroHorario(1, 101,
                LocalDateTime.parse("2021-01-01T08:00"), LocalDateTime.parse("2021-01-01T16:00"));
        RegistroHorario registro2 = new RegistroHorario(2, 101,
                LocalDateTime.parse("2022-02-02T10:00"), LocalDateTime.parse("2022-02-02T17:00"));
        RegistroHorario registro3 = new RegistroHorario(1, 101,
                LocalDateTime.parse("2021-01-03T12:20"), LocalDateTime.parse("2024-11-10T21:00"));
        RegistroHorario registro4 = new RegistroHorario(1, 101,
                LocalDateTime.parse("2020-01-02T12:20"), LocalDateTime.parse("2024-11-10T21:00"));
        contenedor.add(registro1);
        contenedor.add(registro2);
        contenedor.add(registro3);
        contenedor.add(registro4);

        ContenedorRegistroHorario contenedorOrdenado = contenedor.getOrdenadosEntrada();
        assertEquals(4, contenedorOrdenado.tamanio());
        assertSame(registro4, contenedorOrdenado.getPorPosicion(0));
        assertSame(registro1, contenedorOrdenado.getPorPosicion(1));
        assertSame(registro3, contenedorOrdenado.getPorPosicion(2));
        assertSame(registro2, contenedorOrdenado.getPorPosicion(3));
    }

    @Test
    public void testToString() {
        ContenedorRegistroHorario contenedor = new ContenedorRegistroHorario();
        RegistroHorario registro1 = new RegistroHorario(1, 101,
                LocalDateTime.parse("2021-01-01T08:00"), LocalDateTime.parse("2021-01-01T16:00"));
        RegistroHorario registro2 = new RegistroHorario(2, 101,
                LocalDateTime.parse("2022-02-02T10:00"), LocalDateTime.parse("2022-02-02T17:00"));
        RegistroHorario registro3 = new RegistroHorario(1, 101,
                LocalDateTime.of(2021, 1, 3, 12, 20),
                LocalDateTime.of(2024, 11, 10, 21, 0));
        RegistroHorario registro4 = new RegistroHorario(3, 102,
                LocalDateTime.of(2020, 5, 7, 11, 12),
                LocalDateTime.of(2022, 6, 7, 18, 0));
        contenedor.add(registro1);
        contenedor.add(registro2);
        contenedor.add(registro3);
        contenedor.add(registro4);

        String esperado = "{\n" +
                "objetosContenidos = [\n" +
                "{idPersona: 1, idEmpresa: 101, entrada: \"2021-01-01T08:00\", salida: \"2021-01-01T16:00\"},\n" +
                "{idPersona: 2, idEmpresa: 101, entrada: \"2022-02-02T10:00\", salida: \"2022-02-02T17:00\"},\n" +
                "{idPersona: 1, idEmpresa: 101, entrada: \"2021-01-03T12:20\", salida: \"2024-11-10T21:00\"},\n" +
                "{idPersona: 3, idEmpresa: 102, entrada: \"2020-05-07T11:12\", salida: \"2022-06-07T18:00\"},\n" +
                "]\n" +
                "}";
        assertEquals(esperado, contenedor.toString());
    }

}
