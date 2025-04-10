package es.vrivas.dagil;

import java.time.LocalDateTime;

/**
 * Clase para almacenar los datos de cada uno de los registros horarios de entrada y salida de una persona.
 */
public class RegistroHorario {
    /// idPersona.
    private int idPersona;

    /// idEmpresa.
    private int idEmpresa;

    /// fecha y hora de entrada como tipo LocalDateTime.
    private LocalDateTime entrada;

    /// fecha y hora de salida como tipo LocalDateTime.
    private LocalDateTime salida;

    /**
    * Constructor parametrizado con fechas.
    */
    public RegistroHorario(int idPersona, int idEmpresa, LocalDateTime entrada, LocalDateTime salida) {
        try {
            this.setIdPersona(idPersona);
            this.setIdEmpresa(idEmpresa);
            this.setEntrada(entrada);
            this.setSalida(salida);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Error en parámetos de llamada a constructor Registro Horario: "
                + e.getMessage());
        }
    }

    /**
     * Devuelve el valor de idPersona.
     * @return Atributo idPersona
     */
    public int getIdPersona() {
        return idPersona;
    }

    /**
     * Establece el valor de idPersona.
     * @param nuevoIdPersona
     * @exception IllegalArgumentException Si el id de la persona no es mayor que 0
     * @return La propia instancia de RegistroHorario
     */
    public RegistroHorario setIdPersona(int nuevoIdPersona) {
        if (nuevoIdPersona <= 0) {
            throw new IllegalArgumentException("El id de la persona debe ser mayor que 0");
        }
        this.idPersona = nuevoIdPersona;
        return this;
    }

    /**
     * Devuelve el valor de idEmpresa.
     * @return Atributo idEmpresa
     */
    public int getIdEmpresa() {
        return idEmpresa;
    }

    /**
     * Establece el valor de idEmpresa.
     * @param nuevoIdEmpresa
     * @exception IllegalArgumentException Si el id de la empresa no es mayor que 0
     * @return La propia instancia de RegistroHorario
     */
    public RegistroHorario setIdEmpresa(int nuevoIdEmpresa) {
        if (nuevoIdEmpresa <= 0) {
            throw new IllegalArgumentException("El id de la empresa debe ser mayor que 0");
        }
        this.idEmpresa = nuevoIdEmpresa;
        return this;
    }

    /**
     * Devuelve el valor de fechaHora_entrada_date.
     */
    public LocalDateTime getEntrada() {
        return entrada;
    }

    /**
     * Devuelve el valor de fechaHora_salida_date.
     */
    public LocalDateTime getSalida() {
        return salida;
    }

    /**
     * Establece el valor de la fecha y hora de entrada.
     * @param fechaHora Valor de la fecha y hora de entrada
     * @exception IllegalArgumentException Si la fecha y hora de entrada es null
     * @exception IllegalArgumentException Si la fecha es posterior a la fecha de salida
     * @return La propia instancia de RegistroHorario
     */
    public RegistroHorario setEntrada(final LocalDateTime fechaHora) {
        if (fechaHora == null) {
            throw new IllegalArgumentException("La fecha y hora de entrada no puede ser null");
        }
        if (this.salida != null && fechaHora.isAfter(this.salida)) {
            throw new IllegalArgumentException("La entrada no puede ser posterior a la salida");
        }
        this.entrada = fechaHora;
        return this;
    }

    /**
     * Establece el valor de la fecha y hora de salida.
     * @param fechaHora Valor de la fecha y hora de salida
     * @exception IllegalArgumentException Si la fecha y hora de salida es null
     * @exception IllegalArgumentException Si la fecha es anterior a la fecha de entrada
     * @return La propia instancia de RegistroHorario
     */
    public RegistroHorario setSalida(final LocalDateTime fechaHora) {
        if (fechaHora == null) {
            throw new IllegalArgumentException("La fecha y hora de salida no puede ser null");
        }
        if (this.entrada != null && fechaHora.isBefore(this.entrada)) {
            throw new IllegalArgumentException("La salida no puede ser anterior a la entrada");
        }
        this.salida = fechaHora;
        return this;
    }

    /**
     * Devuelve los atributos del Contenido en una cadena.
     * @return Cadena con los atributos del contenido en formato JSON
     */
    public final String toString() {
        return "{idPersona: " + idPersona
            + ", idEmpresa: " + idEmpresa
            + ", entrada: \"" + entrada + "\""
            + ", salida: \"" + salida + "\""
            + "}";
    }

    /**
     * Compara dos registros horarios por la fecha y hora de entrada como LocalDateTime.
     * @param r2 RegistroHorario con el que comparar
     * @return -1, si este registro es anterior; 0 si son iguales; y 1, si es posterior.
     */
    public int comparaEntrada(final RegistroHorario r2) {
        return this.entrada.compareTo(r2.entrada);
    }
}
