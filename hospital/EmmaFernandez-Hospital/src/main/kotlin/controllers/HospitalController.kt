package controllers

import exceptions.HospitalVacioException
import exceptions.IngresoException
import exceptions.PacienteNotFoundException
import models.Paciente
import repositories.HospitalRepository

class HospitalController(private val hospitalRepository: HospitalRepository) {
    fun ingresar(paciente: Paciente): Paciente {
        return hospitalRepository.ingresar(paciente)
            ?: throw IngresoException("No se ha podido ingresar el paciente")
    }

    fun darAlta(dni: String): Paciente? {
        return hospitalRepository.darAlta(dni)
            ?: throw PacienteNotFoundException("No se ha encontrado el paciente con dni $dni")
    }

    fun estaCompleto(): Boolean {
        return hospitalRepository.estaCompleto()
    }
    fun numPacientes(): Int {
        return hospitalRepository.numPacientes()
    }
    fun obtenerTodosPacientes(): List<Paciente> {
        return hospitalRepository.obtenerTodosPacientes()
            .also { if (it.isEmpty()) throw HospitalVacioException("No se han encontrado pacientes") }
    }
    fun pacientePorDni(dni: String): Paciente? {
        return hospitalRepository.pacientePorDni(dni)
            ?: throw PacienteNotFoundException("No se ha encontrado el paciente con dni $dni")
    }

    fun pacientesOrdenadosFechaIngreso(): List<Paciente> {
        return hospitalRepository.pacientesOrdenadosFechaIngreso()
            .also { if (it.isEmpty()) throw HospitalVacioException("No se han encontrado pacientes") }
    }
    fun pacientesOrdenadosNombre(): List<Paciente> {
        return hospitalRepository.pacientesOrdenadosNombre()
            .also { if (it.isEmpty()) throw HospitalVacioException("No se han encontrado pacientes") }
    }
    fun pacientesPorTipo(tipo: Paciente.TipoPaciente): List<Paciente> {
        return hospitalRepository.pacientesPorTipo(tipo)
            .also { if (it.isEmpty()) throw PacienteNotFoundException("No se han encontrado pacientes de tipo $tipo") }
    }

    fun numPacientesPorTipo(tipo: Paciente.TipoPaciente): Int {
        return hospitalRepository.numPacientesPorTipo(tipo)
            .also { if (it == 0) throw PacienteNotFoundException("No se han encontrado pacientes de tipo $tipo") }
    }
}