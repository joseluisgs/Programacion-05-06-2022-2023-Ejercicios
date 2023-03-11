package repositories

import models.Paciente
import java.time.LocalDate

class HospitalListaRepository(override val maxOcupacion: Int = 50) : HospitalRepository<Paciente> {
    val hospital: MutableList<Paciente> = mutableListOf<Paciente>()

    override fun estaCompleto(): Boolean {
        return maxOcupacion == hospital.size
    }

    override fun obtenerTodosPacientes(): List<Paciente> {
        return hospital.toList()
    }

    override fun pacientesOrdenFechaIngreso(): List<Paciente> {
        return hospital.sortedBy { it.fechaIngreso }

    }

    override fun pacientesOrdenNombre(): List<Paciente> {
        return hospital.sortedBy { it.nombre }
    }

    override fun buscarPacienteDni(dni: String): Paciente? {
        hospital.forEach {
            if (it.dni == dni) return it
        }
        return null
    }

    override fun buscarPacientePorTipo(tipoPaciente: Paciente.TipoPaciente): List<Paciente> {
        val list: MutableList<Paciente> = mutableListOf()
        hospital.forEach {
            when (it.tipoPaciente) {
                tipoPaciente -> list.add(it)
                else -> {}
            }
        }
        return list.toList()
    }

    override fun numPacientesPorTipo(tipoPaciente: Paciente.TipoPaciente): Int {
        var numPacienteTipo = 0
        hospital.forEach {
            if (it.tipoPaciente == tipoPaciente) numPacienteTipo++
        }
        return numPacienteTipo
    }

    override fun darDeAlta(paciente: Paciente): Paciente? {
        hospital.forEach {
            if (it == paciente) {
                paciente.fechaAlta = LocalDate.now()
                hospital.remove(paciente)
                return paciente
            }
        }
        return null
    }

    override fun ingresarPaciente(paciente: Paciente): Paciente? {
        if (!estaCompleto()){
            hospital.forEach {
                if (it == paciente) return null
            }
            hospital.add(paciente)
        }
        else return null
        return paciente
    }

}