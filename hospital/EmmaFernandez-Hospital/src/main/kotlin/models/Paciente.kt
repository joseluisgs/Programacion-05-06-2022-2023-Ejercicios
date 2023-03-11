package models

import java.time.LocalDate

class Paciente(
    val dni: String,
    val nombre: String,
    val fechaNacimiento: LocalDate,
    var fechaIngreso: LocalDate? = null,
    var fechaAlta: LocalDate? = null,
    val tipoPaciente: TipoPaciente = TipoPaciente.NORMAL
) {
    enum class TipoPaciente {
        NORMAL, URGENCIA
    }
}