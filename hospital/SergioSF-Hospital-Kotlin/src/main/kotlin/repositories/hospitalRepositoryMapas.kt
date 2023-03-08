package repositories

import models.Pacientes

interface hospitalRepositoryMapas {
    fun isCompletoMapas(): Boolean
    fun ingresarMapas(opcion: Int): Pacientes
    fun numPacientesMapas(): Int
    fun todosPacientesMapas(): MutableMap<String, Pacientes>
    fun pacientesPorDniMapas(): MutableMap<String, Pacientes>
    fun pacientesOrderFechaIngresoMapas(): MutableMap<String, Pacientes>
    fun pacientesPorNombreIngresoMapas(): MutableMap<String, Pacientes>
    fun pacientesPorTipoMapas(): MutableMap<String, Pacientes>
    fun numPacientesPorTipoMapas(): String
}