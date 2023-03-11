package repositories

import models.Paciente

interface HospitalRepository <T>{
    val maxOcupacion: Int
    fun ingresarPaciente(paciente: T): T?
    fun darDeAlta(paciente: T): T?
    fun estaCompleto(): Boolean
    fun obtenerTodosPacientes(): List<T>
    fun pacientesOrdenFechaIngreso (): List<T>
    fun pacientesOrdenNombre(): List<T>
    fun buscarPacienteDni(dni: String): T?
    fun buscarPacientePorTipo(tipoPaciente: Paciente.TipoPaciente): List<T>
    fun numPacientesPorTipo(tipoPaciente: Paciente.TipoPaciente): Int
}