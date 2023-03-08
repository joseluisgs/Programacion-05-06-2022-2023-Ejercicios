package models

import enums.TipoDePaciente
import java.time.LocalDate

class Paciente(val dni: String, val nombre: String, val fechaNacimiento: LocalDate, val tipo: TipoDePaciente, val fechaIngreso: LocalDate, var fechaAlta: LocalDate? = null)
{
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Paciente

        if (dni != other.dni) return false
        if (nombre != other.nombre) return false
        if (fechaNacimiento != other.fechaNacimiento) return false

        return true
    }

    override fun hashCode(): Int {
        var result = dni.hashCode()
        result = 31 * result + nombre.hashCode()
        result = 31 * result + fechaNacimiento.hashCode()
        return result
    }

    override fun toString(): String {
        return "Paciente(dni='$dni', nombre='$nombre', fechaNacimiento=$fechaNacimiento, tipo=$tipo, fechaIngreso=$fechaIngreso, fechaAlta=$fechaAlta)"
    }


}