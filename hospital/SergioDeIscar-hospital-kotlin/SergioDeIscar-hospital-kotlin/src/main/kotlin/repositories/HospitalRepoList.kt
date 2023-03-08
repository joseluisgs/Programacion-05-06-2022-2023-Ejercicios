package repositories

import enums.TipoPaciente
import interfaces.HospitalExtension
import models.Paciente
import java.time.LocalDate

class HospitalRepoList(private val maxSize: Int = 50): HospitalExtension {
    private val pacientes = mutableListOf<Paciente>() // ArrayList ya que la salida es salteada
    override fun estaCompleto(): Boolean {
        return pacientes.size >= maxSize
    }

    override fun numPacientes(): Int {
        return pacientes.size
    }

    override fun pacientesOrdeFechaIngreso(): List<Paciente> {
        return pacientes.sortedBy { it.fechaIgreso }
    }

    override fun pacientesOrdeNombre(): List<Paciente> {
        return pacientes.sortedBy { it.nombre }
    }

    override fun pacientesOrdeDNI(): List<Paciente> {
        return pacientes.sortedBy { it.dni }
    }

    override fun pacientesPorTipo(tipo: TipoPaciente): List<Paciente> {
        return pacientes.filter { it.tipo == tipo }
    }

    override fun numPacientePorTipo(tipo: TipoPaciente): Int {
        return pacientesPorTipo(tipo).count()
    }

    override fun save(paciente: Paciente): Paciente? {
        if (estaCompleto()) return null

        paciente.setFechaIngreso(LocalDate.now())

        pacientes.find { it.dni == paciente.dni }?.let {
            pacientes.remove(it)
            pacientes.add(paciente)
            return paciente
        } ?: run {
            pacientes.add(paciente)
            return paciente
        }
    }

    override fun find(dni: String): Paciente? {
        return pacientes.find { it.dni == dni }
    }

    override fun delete(dni: String): Paciente? {
        val paciente = find(dni) ?: return null
        paciente.setFechaAlta(LocalDate.now())
        pacientes.remove(paciente)
        return paciente
    }

    override fun getAll(): List<Paciente> {
        return pacientes.toList()
    }

    override fun saveAll(array: List<Paciente>) {
        if (estaCompleto()) return
        array.forEach{ save(it) }
    }
}