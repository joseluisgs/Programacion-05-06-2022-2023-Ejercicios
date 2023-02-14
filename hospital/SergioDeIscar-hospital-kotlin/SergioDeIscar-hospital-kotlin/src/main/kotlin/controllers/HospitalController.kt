package controllers

import enums.TipoPaciente
import exceptions.*
import interfaces.HospitalExtension
import models.Paciente

class HospitalController(
    private val repository: HospitalExtension // Inyección de dependencias, se le pasa el repositorio
): HospitalExtension {
    override fun estaCompleto(): Boolean {
        return repository.estaCompleto()
    }

    override fun numPacientes(): Int {
        return repository.numPacientes()
    }

    override fun pacientesOrdeFechaIngreso(): List<Paciente> {
        return repository.pacientesOrdeFechaIngreso()
    }

    override fun pacientesOrdeNombre(): List<Paciente> {
        return repository.pacientesOrdeNombre()
    }

    override fun pacientesOrdeDNI(): List<Paciente> {
        return repository.pacientesOrdeDNI()
    }

    override fun pacientesPorTipo(tipo: TipoPaciente): List<Paciente> {
        return repository.pacientesPorTipo(tipo)
    }

    override fun numPacientePorTipo(tipo: TipoPaciente): Int {
        return repository.numPacientePorTipo(tipo)
    }

    override fun save(paciente: Paciente): Paciente? {
        require(!repository.estaCompleto()) { throw HospitalFullException() }
        require(checkDni(paciente.dni)) {throw DniNotValidException() }
        return repository.save(paciente)
    }

    override fun find(dni: String): Paciente? {
        require(checkDni(dni)) {throw DniNotValidException() }
        return repository.find(dni)
    }

    override fun delete(dni: String): Paciente? {
        require(checkDni(dni)) {throw DniNotValidException() }
        return repository.delete(dni)
    }

    override fun getAll(): List<Paciente> {
        return repository.getAll()
    }

    override fun saveAll(pacientes: List<Paciente>) {
        return repository.saveAll(pacientes)
    }

    private fun checkDni(dni: String): Boolean {
        return Regex("^[0-9]{8}[A-Z]$").matches(dni)
    }
}