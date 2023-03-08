package models

class PNormal(dni: String, nombre: String, fNacimiento: String, fIngreso: String, fAlta: String): Pacientes(dni, nombre, fNacimiento, fIngreso, fAlta), PacienteNormal {
    override fun toString(): String {
        return super.toString()
    }
}