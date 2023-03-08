package repositories

import enums.TipoPaciente
import models.Paciente

interface HospitalRepository<T, ID> {
    fun ingresar(newPatient: T): T?
    fun darAlta(id: ID): T?
    fun isHospitalLleno(): Boolean
    fun numPacientes(): Int
    fun todosLosPacientes(): List<T?>
    fun orderPacientesByFechaDeIngreso(): List<T?>
    fun buscarPacientePorDNI(dni: ID): T?
    fun pacientesPorTipo(tipoPaciente: TipoPaciente): List<T?>
    fun numPacientesPorTipo(tipoPaciente: TipoPaciente): Int
}