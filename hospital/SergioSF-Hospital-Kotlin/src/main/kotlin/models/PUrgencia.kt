package models

class PUrgencia(dni: String, nombre: String, fNacimiento: String, fIngreso: String, fAlta: String): Pacientes(dni, nombre, fNacimiento, fIngreso, fAlta), PacienteUrgencia {
    override fun toString(): String {
        return super.toString()
    }
}