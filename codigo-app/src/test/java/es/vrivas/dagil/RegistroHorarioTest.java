package es.vrivas.dagil;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.fail;

import java.time.LocalDateTime;

public class RegistroHorarioTest {
    @Test
    public void testRegistroHorario() {
        String entrada = "2020-03-04T05:06:00";
        String salida = "2027-08-09T10:11:00";
        RegistroHorario registroHorario = new RegistroHorario(1, 2,
                LocalDateTime.parse(entrada),
                LocalDateTime.parse(salida));

        // Otra forma de establecer la hora
        LocalDateTime entradaEsperada = LocalDateTime.of(2020, 3, 4, 5, 6, 0);
        LocalDateTime salidaEsperada = LocalDateTime.of(2027, 8, 9, 10, 11, 0);
        assertEquals(entradaEsperada, registroHorario.getEntrada());
        assertEquals(salidaEsperada, registroHorario.getSalida());
    }

    @Test
    public void testGetEntrada() {
        String entrada = "2020-03-04T05:06:00";
        String salida = "2027-08-09T10:11:00";
        RegistroHorario registroHorario = new RegistroHorario(1, 2,
                LocalDateTime.parse(entrada),
                LocalDateTime.parse(salida));

        LocalDateTime entradaEsperada = LocalDateTime.parse(entrada);

        assertEquals(entradaEsperada, registroHorario.getEntrada());
    }

    @Test
    public void testGetSalida() {
        String entrada = "2020-03-04T05:06:00";
        String salida = "2027-08-09T10:11:00";
        RegistroHorario registroHorario = new RegistroHorario(1, 2,
                LocalDateTime.parse(entrada),
                LocalDateTime.parse(salida));

        LocalDateTime salidaEsperada = LocalDateTime.parse(salida);

        assertEquals(salidaEsperada, registroHorario.getSalida());

    }

    @Test
    public void testGetIdEmpresa() {
        int Idempresa = 2;
        String entrada = "2020-03-04T05:06:00";
        String salida = "2027-08-09T10:11:00";
        RegistroHorario registroHorario = new RegistroHorario(1, Idempresa,
                LocalDateTime.parse(entrada),
                LocalDateTime.parse(salida));

        assertEquals(Idempresa, registroHorario.getIdEmpresa());
    }

    @Test
    public void testGetIdPersona() {
        int Idpersona = 1;
        String entrada = "2020-03-04T05:06:00";
        String salida = "2027-08-09T10:11:00";
        RegistroHorario registroHorario = new RegistroHorario(Idpersona, 2,
                LocalDateTime.parse(entrada),
                LocalDateTime.parse(salida));

        assertEquals(Idpersona, registroHorario.getIdPersona());
    }

    @Test
    public void testSetIdEmpresa() {
        String entrada = "2020-03-04T05:06:00";
        String salida = "2027-08-09T10:11:00";

        // pruebo excepción
        try {
            new RegistroHorario(1, 0,
                    LocalDateTime.parse(entrada),
                    LocalDateTime.parse(salida));
            fail();
        } catch (IllegalArgumentException e) {
            System.err.println("testSetIdEmpresa: Excepción capturada: " + e.getMessage());
        }
        // Continúo con la prueba del cambio de id empresa
        int Idempresa = 1;

        RegistroHorario registroHorario = new RegistroHorario(1, Idempresa,
                LocalDateTime.parse(entrada),
                LocalDateTime.parse(salida));

        assertSame(registroHorario, registroHorario.setIdEmpresa(Idempresa + 10));
        assertEquals(Idempresa + 10, registroHorario.getIdEmpresa());
    }

    @Test
    public void testSetIdpersona() {
        String entrada = "2020-03-04T05:06:00";
        String salida = "2027-08-09T10:11:00";
        // pruebo excepción
        try {
            new RegistroHorario(0, 1,
                    LocalDateTime.parse(entrada),
                    LocalDateTime.parse(salida));
            fail();
        } catch (IllegalArgumentException e) {
            System.err.println("testSetIdPersona: Excepción capturada: " + e.getMessage());
        }

        // Continúo con la prueba del cambio de id persona
        int Idpersona = 1;

        RegistroHorario registroHorario = new RegistroHorario(Idpersona, 1,
                LocalDateTime.parse(entrada),
                LocalDateTime.parse(salida));

        assertSame(registroHorario, registroHorario.setIdPersona(Idpersona + 10));
        assertEquals(Idpersona + 10, registroHorario.getIdPersona());
    }

    @Test
    public void testSetEntrada() {
        String entrada = "2020-03-04T05:06:00";
        String salida = "2027-08-09T10:11:00";
        RegistroHorario registroHorario = new RegistroHorario(1, 2,
                LocalDateTime.parse(entrada),
                LocalDateTime.parse(salida));

        // Compruebo excepción cuando la entrada es nula
        try {
            registroHorario.setEntrada(null);
            fail();
        } catch (IllegalArgumentException e) {
            System.err.println("testSetEntrada: Excepción capturada: " + e.getMessage());
        }

        // Compruebo excepción cuando la entrada es posterior a la salida
        try {
            registroHorario.setEntrada(LocalDateTime.parse("2027-08-09T10:12:00"));
            fail();
        } catch (IllegalArgumentException e) {
            System.err.println("testSetEntrada: Excepción capturada: " + e.getMessage());
        }
        LocalDateTime nuevaEntradaEsperada = LocalDateTime.parse("2021-03-04T05:06:00");

        assertSame(registroHorario, registroHorario.setEntrada(nuevaEntradaEsperada));
        assertEquals(nuevaEntradaEsperada, registroHorario.getEntrada());
    }

    @Test
    public void testSetSalida() {
        String entrada = "2020-03-04T05:06:00";
        String salida = "2027-08-09T10:11:00";
        RegistroHorario registroHorario = new RegistroHorario(1, 2,
                LocalDateTime.parse(entrada),
                LocalDateTime.parse(salida));

        // Compruebo excepción cuando la salida es nula
        try {
            registroHorario.setSalida(null);
            fail();
        } catch (IllegalArgumentException e) {
            System.err.println("testSetSalida: Excepción capturada: " + e.getMessage());
        }

        // Compruebo excepción cuando la salida es anterior a la entrada
        try {
            registroHorario.setSalida(LocalDateTime.parse("2020-03-04T05:05:00"));
            fail();
        } catch (IllegalArgumentException e) {
            System.err.println("testSetSalida: Excepción capturada: " + e.getMessage());
        }
        LocalDateTime nuevaSalidaEsperada = LocalDateTime.parse("2027-08-09T10:12:00");

        assertSame(registroHorario, registroHorario.setSalida(nuevaSalidaEsperada));
        assertEquals(nuevaSalidaEsperada, registroHorario.getSalida());
    }

    @Test
    public void testToString() {

        String entrada = "2020-03-04T05:06:00";
        String salida = "2027-08-09T10:11:00";
        RegistroHorario registroHorario = new RegistroHorario(1, 2,
                LocalDateTime.parse(entrada),
                LocalDateTime.parse(salida));
        assertEquals(
                "{idPersona: 1, idEmpresa: 2, entrada: \"2020-03-04T05:06\", salida: \"2027-08-09T10:11\"}",
                registroHorario.toString());
    }

    @Test
    public void testComparaEntrada() {
        int original = 5;
        // Sumo +10 al año para que al incrementar el año no dé error por ser la fecha
        // de salida anterior a la de entrada
        RegistroHorario r1 = new RegistroHorario(1, 2,
                LocalDateTime.of(original, original, original, original, original),
                LocalDateTime.of(original + 10, original, original, original, original));

        RegistroHorario r2 = new RegistroHorario(1, 2,
                LocalDateTime.of(original, original, original, original, original),
                LocalDateTime.of(original + 10, original, original, original, original));

        // Compruebo año
        r1.setEntrada(LocalDateTime.of(original - 2, original, original, original, original));
        assert (r1.comparaEntrada(r2) < 0);

        r1.setEntrada(LocalDateTime.of(original + 2, original, original, original, original));
        assert (r1.comparaEntrada(r2) > 0);

        // Compruebo mes
        r1.setEntrada(LocalDateTime.of(original, original - 2, original, original, original));
        assert (r1.comparaEntrada(r2) < 0);

        r1.setEntrada(LocalDateTime.of(original, original + 2, original, original, original));
        assert (r1.comparaEntrada(r2) > 0);

        // Compruebo día
        r1.setEntrada(LocalDateTime.of(original, original, original - 2, original, original));
        assert (r1.comparaEntrada(r2) < 0);

        r1.setEntrada(LocalDateTime.of(original, original, original + 2, original, original));
        assert (r1.comparaEntrada(r2) > 0);

        // Compruebo hora
        r1.setEntrada(LocalDateTime.of(original, original, original, original - 2, original));
        assert (r1.comparaEntrada(r2) < 0);

        r1.setEntrada(LocalDateTime.of(original, original, original, original + 2, original));
        assert (r1.comparaEntrada(r2) > 0);

        // Compruebo minuto
        r1.setEntrada(LocalDateTime.of(original, original, original, original, original - 2));
        assert (r1.comparaEntrada(r2) < 0);

        r1.setEntrada(LocalDateTime.of(original, original, original, original, original + 2));
        assert (r1.comparaEntrada(r2) > 0);
    }

}
