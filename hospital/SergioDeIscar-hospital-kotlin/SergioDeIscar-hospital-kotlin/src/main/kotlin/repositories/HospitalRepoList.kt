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
        // Tres formas de hacerlo
        //return pacientes.toSortedSet{ p1, p2 -> p1.fechaIgreso.compareTo(p2.fechaIgreso) }.toList()
        return pacientes.sortedBy { it.fechaIgreso }
        //return orderBy { p1, p2 -> p1.fechaIgreso.compareTo(p2.fechaIgreso) }
    }

    override fun pacientesOrdeNombre(): List<Paciente> {
        return orderBy { p1, p2 -> p1.nombre.compareTo(p2.nombre) }
    }

    override fun pacientesOrdeDNI(): List<Paciente> {
        return orderBy { p1, p2 -> p1.dni.compareTo(p2.dni) }
    }

    private fun orderBy(orden: (Paciente, Paciente) -> Int): List<Paciente> {
        val list = pacientes.toMutableList()
        list.sortWith(orden)
        return list.toList()
    }

    override fun pacientesPorTipo(tipo: TipoPaciente): List<Paciente> {
        val list = mutableListOf<Paciente>()
        for (i in pacientes){
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

        for (i in pacientes.indices) {
            if (paciente.dni != pacientes[i].dni) continue
            pacientes[i] = paciente
            return paciente
        }
        pacientes.add(paciente)
        return paciente
    }

    override fun find(dni: String): Paciente? {
        for (i in pacientes){
            if (i.dni == dni) return i
        }
        return null
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
        for (i in array){
            save(i)
        }
    }
}