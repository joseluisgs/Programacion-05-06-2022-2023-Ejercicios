package models

import enums.tipoPaciente

class Paciente(
    val DNI: String,
    val nombre: String,
    val apellido: String,
    val fechaNacimiento: String,
    val fechaIngreso: Triple<Int, Int, Int>,
    val tipoPaciente: tipoPaciente
) {
    lateinit var fechaAlta: Triple<Int, Int, Int>
    private val fechaIngresoString = "\"${fechaIngreso.first}-${fechaIngreso.second}-${fechaIngreso.third}\""

    override fun toString(): String {
        return "Paciente \"$nombre $apellido\" con DNI \"$DNI\" y fecha de nacimiento $fechaNacimiento, ingresó a $fechaIngresoString y es de tipo $tipoPaciente"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Paciente) return false
        return this.DNI == other.DNI && this.nombre == other.nombre && this.fechaNacimiento == other.fechaNacimiento && this.fechaIngreso == other.fechaIngreso && this.tipoPaciente == other.tipoPaciente
    }

    override fun hashCode(): Int {
        var result = DNI.hashCode()
        result = 31 * result + nombre.hashCode()
        result = 31 * result + fechaNacimiento.hashCode()
        result = 31 * result + fechaIngreso.hashCode()
        result = 31 * result + tipoPaciente.hashCode()
        return result
    }
}