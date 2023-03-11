package repositories

import models.Paciente

interface HospitalRepository {
    val capacidadMaxima: Int

    fun ingresar(paciente: Paciente): Paciente?
    fun darAlta(dni: String): Paciente?
    fun estaCompleto(): Boolean
    fun numPacientes(): Int
    fun obtenerTodosPacientes(): List<Paciente>
    fun pacientePorDni(dni: String): Paciente?
    fun pacientesOrdenadosFechaIngreso(): List<Paciente>
    fun pacientesOrdenadosNombre(): List<Paciente>
    fun pacientesPorTipo(tipo: Paciente.TipoPaciente): List<Paciente>
    fun numPacientesPorTipo(tipo: Paciente.TipoPaciente): Int
}