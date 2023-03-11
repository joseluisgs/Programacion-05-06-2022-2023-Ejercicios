package repositories

import models.Paciente
import java.time.LocalDate

class HospitalRepositoryConjunto(
    override val capacidadMaxima: Int
) : HospitalRepository {
    private val hospital = mutableSetOf<Paciente>()

    override fun ingresar(paciente: Paciente): Paciente? {
        return if (!estaCompleto()) {
            paciente.fechaIngreso = LocalDate.now()
            when (hospital.add(paciente)) {
                true -> paciente
                false -> null
            }
        } else null
    }

    override fun darAlta(dni: String): Paciente? {
        val paciente = pacientePorDni(dni)
        if (paciente != null) hospital.remove(paciente)
        return paciente
    }

    override fun estaCompleto(): Boolean {
        return hospital.size == capacidadMaxima
    }

    override fun numPacientes(): Int {
        return hospital.size
    }

    override fun obtenerTodosPacientes(): List<Paciente> {
        return hospital.toList()
    }

    override fun pacientePorDni(dni: String): Paciente? {
        return hospital.find { it.dni == dni }
    }

    override fun pacientesOrdenadosFechaIngreso(): List<Paciente> {
        return hospital.sortedBy { it.fechaIngreso }
    }

    override fun pacientesOrdenadosNombre(): List<Paciente> {
        return hospital.sortedBy { it.nombre }
    }

    override fun pacientesPorTipo(tipo: Paciente.TipoPaciente): List<Paciente> {
        return hospital.filter { it.tipoPaciente == tipo }
    }

    override fun numPacientesPorTipo(tipo: Paciente.TipoPaciente): Int {
        return hospital.count { it.tipoPaciente == tipo }
    }
}