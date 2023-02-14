package repositories

import enums.TipoPaciente
import interfaces.HospitalExtension
import models.Paciente
import java.time.LocalDate

class HospitalRepoMapa(private val maxSize: Int = 50): HospitalExtension {
    // LinkedHashMap, por el mismo motivo que en el conjunto, un TreeMap sería más ineficiente
    private val pacientes = mutableMapOf<String, Paciente>()

    override fun estaCompleto(): Boolean {
        return pacientes.size >= maxSize
    }

    override fun numPacientes(): Int {
        return pacientes.size
    }

    override fun pacientesOrdeFechaIngreso(): List<Paciente> {
        return pacientes.values.sortedBy { it.fechaIgreso }
    }

    override fun pacientesOrdeNombre(): List<Paciente> {
        return pacientes.values.sortedBy { it.nombre }
    }

    override fun pacientesOrdeDNI(): List<Paciente> {
        return pacientes.values.sortedBy { it.dni }
    }

    override fun pacientesPorTipo(tipo: TipoPaciente): List<Paciente> {
        //return pacientes.values.filter { it.tipo == tipo }
        val list = mutableListOf<Paciente>()
        for (i in pacientes.values){
            if (i.tipo == tipo) list.add(i)
        }
        return list.toList()
    }

    override fun numPacientePorTipo(tipo: TipoPaciente): Int {
        return pacientesPorTipo(tipo).count()
    }

    override fun save(paciente: Paciente): Paciente? {
        if (estaCompleto()) return null
        paciente.setFechaIngreso(LocalDate.now())
        pacientes[paciente.dni] = paciente
        return paciente
    }

    override fun find(dni: String): Paciente? {
        return pacientes[dni]
    }

    override fun delete(dni: String): Paciente? {
        val paciente = pacientes[dni] ?: return null
        paciente.setFechaAlta(LocalDate.now())
        return pacientes.remove(dni)
    }

    override fun getAll(): List<Paciente> {
        return pacientes.values.toList()
    }

    override fun saveAll(pacientes: List<Paciente>) {
        if (estaCompleto()) return
        for (i in pacientes){
            save(i)
        }
    }
}