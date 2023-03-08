package controllers

import enums.TipoPaciente
import exceptions.DNINotFoundException
import exceptions.NotValidDNIException
import exceptions.PacienteBadRequestException
import models.Paciente
import repositories.HospitalRepository

// al constructor le inyecto el repositorio
class HospitalController(val repo: HospitalRepository<Paciente, String>) {

    fun ingresar(newPatient: Paciente): Paciente? {
        (isPatientValid(newPatient))
        return repo.ingresar(newPatient)
    }

    fun darAlta(dni: String): Paciente? {
        isDniValid(dni)
        return repo.darAlta(dni)
    }

    fun isHospitalLleno(): Boolean {
        return repo.isHospitalLleno()
    }

    fun numPacientes(): Int {
        return repo.numPacientes()
    }

    fun todosLosPacientes(): List<Paciente?> {
        return repo.todosLosPacientes()
    }

    fun orderPacientesByFechaDeIngreso(): List<Paciente?> {
        return repo.orderPacientesByFechaDeIngreso()
    }

    fun buscarPacientesPorDNI(dni: String): Paciente? {
        isDniValid(dni)
        return repo.buscarPacientePorDNI(dni)
    }

    fun pacientesPorTipo(tipoPaciente: TipoPaciente): List<Paciente?> {
        return repo.pacientesPorTipo(tipoPaciente)
    }

    fun numPacientesPorTipo(tipoPaciente: TipoPaciente): Int {
        return repo.numPacientesPorTipo(tipoPaciente)
    }

    /**
     * @param newPatient le paso el nuevo paciente que se va a introducir para que la filtre
     */
    fun isPatientValid(newPatient: Paciente): Boolean {
        val dni = newPatient.DNI
        isDniValid(dni)
        require(newPatient.nombre.isNotEmpty()) {
            throw PacienteBadRequestException("El nombre del paciente no puede estar vacío")
        }
        return true
    }

    /**
     * Función refactorizada para validar el dni
     * @param dni Es el dni que se le pasa por parametro para que la función la pueda filtrar
     */
    fun isDniValid(dni: String): Boolean {
        require(dni.isNotEmpty()) {
            throw DNINotFoundException("No se ha encontrado el DNI del paciente nuevo")
        }
        require(dni.matches(regex = "[0-9]{8}+[A-Z]".toRegex())) {
            throw NotValidDNIException("El dni introducido no cumple con los requisitos necesarios")
        }
        return true
    }
}