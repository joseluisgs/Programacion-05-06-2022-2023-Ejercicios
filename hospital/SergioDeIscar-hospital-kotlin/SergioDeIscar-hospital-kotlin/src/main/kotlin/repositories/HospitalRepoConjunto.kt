package repositories

import enums.TipoPaciente
import interfaces.HospitalExtension
import models.Paciente
import java.time.LocalDate

class HospitalRepoConjunto(private val maxSize: Int = 50): HospitalExtension {
    // LinkedHashSet, aunque me lo pidan ordenado hacerlo con un TreeSet ralentiza el proceso
    // y lo puedo devolver ordenado cuando me lo pidan
    private val pacientes = mutableSetOf<Paciente>()

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
        //Dos formas de hacerlo
        return pacientes.filter { it.tipo == tipo }
        /*val list = mutableListOf<Paciente>()
        for (i in pacientes){
            if (i.tipo == tipo) list.add(i)
        }
        return list.toList()*/
    }

    override fun numPacientePorTipo(tipo: TipoPaciente): Int {
        return pacientesPorTipo(tipo).count()
    }

    /**
     * Si el paciente ya existe lo actualiza, si no lo añade
     */
    override fun save(paciente: Paciente): Paciente? {
        if (estaCompleto()) return null
        paciente.setFechaIngreso(LocalDate.now())
        pacientes.add(paciente)
        return paciente
    }

    override fun find(dni: String): Paciente? {
        // Programación funcional, mejor modo de hacerlo
        return pacientes.find { it.dni == dni }

        // For each, mas lento
        /*for (i in pacientes){
            if (i.dni == dni) return i
        }
        return null*/
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

    override fun saveAll(pacientes: List<Paciente>) {
        if (estaCompleto()) return
        pacientes.forEach{ save(it) }
    }
}