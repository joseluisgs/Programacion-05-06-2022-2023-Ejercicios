package repositories

import models.Paciente

class HospitalRepositoryMapa(
    override val capacidadMaxima: Int
) : HospitalRepository {
    private val hospital = mutableMapOf<String, Paciente>()

    override fun ingresar(paciente: Paciente): Paciente? {
        return if (!estaCompleto()) {
            when (hospital.put(paciente.dni, paciente)) {
                null -> paciente
                else -> null
            }
        } else null
    }

    override fun darAlta(dni: String): Paciente? {
        return hospital.remove(dni)
    }

    override fun estaCompleto(): Boolean {
        return hospital.size == capacidadMaxima
    }

    override fun numPacientes(): Int {
        return hospital.size
    }

    override fun obtenerTodosPacientes(): List<Paciente> {
        return hospital.values.toList()
    }

    override fun pacientePorDni(dni: String): Paciente? {
        return hospital[dni]
    }

    override fun pacientesOrdenadosFechaIngreso(): List<Paciente> {
        return hospital.values.sortedBy { it.fechaIngreso }
    }

    override fun pacientesOrdenadosNombre(): List<Paciente> {
        return hospital.values.sortedBy { it.nombre }
    }

    override fun pacientesPorTipo(tipo: Paciente.TipoPaciente): List<Paciente> {
        return hospital.values.filter { it.tipoPaciente == tipo }
    }

    override fun numPacientesPorTipo(tipo: Paciente.TipoPaciente): Int {
        return hospital.values.count { it.tipoPaciente == tipo }
    }
}