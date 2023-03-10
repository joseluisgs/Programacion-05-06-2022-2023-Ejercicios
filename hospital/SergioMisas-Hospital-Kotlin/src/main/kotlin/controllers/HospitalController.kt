package controllers

import exceptions.HospitalLLenoException
import exceptions.HospitalVacioException
import exceptions.PacienteNoEncontradoException
import exceptions.TipoPacienteException
import models.Paciente
import repositories.HospitalListaRepository
import repositories.HospitalRepository

class HospitalController(repo: HospitalRepository<Paciente>) {
    val repository = repo

    fun estaCompleto(): Boolean {
        return repository.estaCompleto()
    }

    fun obtenerTodosPacientes(): List<Paciente> {
        if (repository.obtenerTodosPacientes().isEmpty()) throw HospitalVacioException("Hospital está vacío")
        return repository.obtenerTodosPacientes()
    }

    fun pacientesOrdenFechaIngreso(): List<Paciente> {
        if (repository.pacientesOrdenFechaIngreso().isEmpty()) throw HospitalVacioException("Hospital está vacío")
        return repository.pacientesOrdenFechaIngreso()
    }

    fun pacientesOrdenNombre(): List<Paciente> {
        if (repository.pacientesOrdenNombre().isEmpty()) throw HospitalVacioException("Hospital está vacío")
        return repository.pacientesOrdenNombre()
    }

    fun buscarPacienteDni(dni: String): Paciente {
        return repository.buscarPacienteDni(dni) ?: throw PacienteNoEncontradoException("Paciente no encontrado")
    }

    fun buscarPacientePorTipo(tipoPaciente: Paciente.TipoPaciente): List<Paciente> {
        if (repository.buscarPacientePorTipo(tipoPaciente)
                .isEmpty()
        ) throw PacienteNoEncontradoException("Paciente no encontrado")
        return repository.buscarPacientePorTipo(tipoPaciente)
    }

    fun numPacientesPorTipo(tipoPaciente: Paciente.TipoPaciente): Int {
        if (repository.numPacientesPorTipo(tipoPaciente) == 0) throw TipoPacienteException("Tipo paciente no encontrado")
        return repository.numPacientesPorTipo(tipoPaciente)
    }

    fun darDeAlta(paciente: Paciente): Paciente {
        return repository.darDeAlta(paciente) ?: throw PacienteNoEncontradoException("Paciente no encontrado")
    }

    fun ingresarPaciente(paciente: Paciente): Paciente {
        return repository.ingresarPaciente(paciente) ?: throw HospitalLLenoException("Hospital lleno")
    }
}