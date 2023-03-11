package repositories

import models.Paciente
import java.time.LocalDate

class HospitalMapaRepository(override val maxOcupacion: Int = 50) : HospitalRepository<Paciente> {
    val hospital: MutableMap<String, Paciente> = HashMap()

    override fun estaCompleto(): Boolean {
        return maxOcupacion == hospital.size
    }

    override fun obtenerTodosPacientes(): List<Paciente> {
        return hospital.values.toList()
    }

    override fun pacientesOrdenFechaIngreso(): List<Paciente> {
        return hospital.values.toList().sortedBy { it.fechaIngreso }
    }

    override fun pacientesOrdenNombre(): List<Paciente> {
        return hospital.values.toList().sortedBy { it.nombre }
    }

    override fun buscarPacienteDni(dni: String): Paciente? {
        hospital.forEach {
            if (it.key == dni) return it.value
        }
        return null
    }

    override fun buscarPacientePorTipo(tipoPaciente: Paciente.TipoPaciente): List<Paciente> {
        val list: MutableList<Paciente> = mutableListOf()
        hospital.forEach {
            when (it.value.tipoPaciente) {
                tipoPaciente -> list.add(it.value)
                else -> {}
            }
        }
        return list.toList()
    }

    override fun numPacientesPorTipo(tipoPaciente: Paciente.TipoPaciente): Int {
        var numPacienteTipo = 0
        hospital.forEach {
            if (it.value.tipoPaciente == tipoPaciente) numPacienteTipo++
        }
        return numPacienteTipo
    }

    override fun darDeAlta(paciente: Paciente): Paciente? {
        hospital.forEach {
            if (it.value == paciente) {
                paciente.fechaAlta = LocalDate.now()
                hospital.remove(paciente.dni , paciente)
                return paciente
            }
        }
        return null
    }

    override fun ingresarPaciente(paciente: Paciente): Paciente? {
        if (!estaCompleto()){
            hospital.forEach {
                if (it.value == paciente) return null
            }
            hospital.put(paciente.dni, paciente)
        }
        else return null
        return paciente
    }

}