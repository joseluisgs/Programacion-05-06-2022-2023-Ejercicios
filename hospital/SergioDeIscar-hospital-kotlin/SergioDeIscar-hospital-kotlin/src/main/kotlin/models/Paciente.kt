package models

import enums.TipoPaciente
import java.time.LocalDate

class Paciente(val dni: String, val nombre: String, val fechaNacimiento: LocalDate = LocalDate.now(), val tipo: TipoPaciente) {
    var fechaIgreso: LocalDate = LocalDate.now()
        private set
    var fechaAlta: LocalDate = LocalDate.now()
        private set

    fun setFechaIngreso(date: LocalDate){
        fechaIgreso = date
    }
    fun setFechaAlta(date: LocalDate){
        fechaAlta = date
    }

    override fun hashCode(): Int {
        return dni.hashCode() + fechaNacimiento.hashCode() + fechaIgreso.hashCode() + fechaAlta.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        return other is Paciente && other.dni == dni && other.fechaIgreso == fechaIgreso && other.fechaAlta == fechaAlta
    }
}