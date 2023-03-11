package models

import java.time.LocalDate

class Paciente(
    val dni: String,
    val nombre: String,
    val fechaIngreso: LocalDate,
    var fechaAlta: LocalDate,
    public val fechaNacimiento: LocalDate,
    val tipoPaciente: TipoPaciente
) {
    enum class TipoPaciente {
        NORMAL, URGENCIA
    }

    override fun toString(): String {
        return "dni: ${this.dni} " +
                "nombre: ${this.nombre} " +
                "fechaIngreso: ${this.fechaIngreso} " +
                "fechaAlta: ${this.fechaAlta} " +
                "fechaNacimiento: ${this.fechaNacimiento}" +
                "tipoPaciente: ${this.tipoPaciente}"
    }

}

