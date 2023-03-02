package repositories

import models.Pacientes

interface hospitalRepositoryConjuntos {
    fun isCompletoConjuntos(): Boolean
    fun ingresarConjuntos(opcion: Int): Pacientes
    fun numPacientesConjuntos(): Int
    fun todosPacientesConjuntos(): MutableSet<Pacientes>
    fun pacientesPorDniConjuntos(): MutableSet<Pacientes>
    fun pacientesOrderFechaIngresoConjuntos(): MutableSet<Pacientes>
    fun pacientesPorNombreIngresoConjuntos(): MutableSet<Pacientes>
    fun pacientesPorTipoConjuntos(): MutableSet<Pacientes>
    fun numPacientesPorTipoConjuntos(): String
}