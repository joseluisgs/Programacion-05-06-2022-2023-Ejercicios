package interfaces

import enums.TipoPaciente
import models.Paciente

interface HospitalExtension: CrudRepository<Paciente, String> {
    fun estaCompleto(): Boolean
    fun numPacientes(): Int

    fun pacientesOrdeFechaIngreso(): List<Paciente>
    fun pacientesOrdeNombre(): List<Paciente>
    fun pacientesOrdeDNI(): List<Paciente>

    fun pacientesPorTipo(tipo: TipoPaciente): List<Paciente>
    fun numPacientePorTipo(tipo: TipoPaciente): Int
}