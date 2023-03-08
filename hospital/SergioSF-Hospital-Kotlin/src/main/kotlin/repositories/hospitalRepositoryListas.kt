package repositories

import models.Pacientes

interface hospitalRepositoryListas{
    fun isCompletoListas(): Boolean
    fun ingresarListas(opcion: Int): Pacientes
//    fun darAlta(): T
    fun numPacientesListas(): Int
    fun todosPacientesListas(): List<Pacientes>
    fun pacientesPorDniListas(): List<Pacientes>
    fun pacientesOrderFechaIngresoListas(): List<Pacientes>
    fun pacientesPorNombreIngresoListas(): List<Pacientes>
    fun pacientesPorTipoListas(): List<Pacientes>
    fun numPacientesPorTipoListas(): String
}