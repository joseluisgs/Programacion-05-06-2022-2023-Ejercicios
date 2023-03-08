package models

import java.sql.Date
import java.text.DateFormat

class Paciente(
    override val dni: String,
    override val nombre: String,
    override val fechaNacimiento: String,
    override val fechaIngreso: String,
    override var tipoPaciente:TipoPaciente
) :IPaciente {
    override var fechaAlta: String? = null
    override fun toString(): String {
        return "Nombre:$nombre Dni:$dni tipoPaciente:$tipoPaciente fechaIngreso:$fechaIngreso"
    }
}

