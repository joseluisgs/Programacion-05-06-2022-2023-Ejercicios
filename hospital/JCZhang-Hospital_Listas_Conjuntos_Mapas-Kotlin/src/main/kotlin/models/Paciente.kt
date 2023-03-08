package models

import enums.TipoPaciente
import java.time.LocalDate

class Paciente(
    val DNI: String,
    val nombre: String,
    var fechaIngreso: LocalDate,
    var fechaAlta: LocalDate? = null,
    val fechaNacimiento: LocalDate,
    val tipo:TipoPaciente
){

    override fun toString(): String {
        return "Paciente(DNI = $DNI, " +
                "nombre = $nombre, " +
                "fechaIngreso = $fechaIngreso, " +
                "fechaAlta = $fechaAlta, f" +
                "echaNacimiento = $fechaNacimiento)"
    }
}