package repositories

import enums.TipoPaciente
import exceptions.DNINotFoundException
import exceptions.TypeNotFoundException
import models.Paciente
import java.time.LocalDate


class ConjuntosHospitalRepositoryMemory : HospitalRepository<Paciente, String>{
    val conjuntoPacientes = mutableSetOf<Paciente?>()

    override fun ingresar(newPatient: Paciente): Paciente {
        if (!isHospitalLleno()){
            conjuntoPacientes.add(newPatient)
            newPatient.fechaIngreso = LocalDate.now()
            return newPatient
        }
        return newPatient
    }

    override fun darAlta(dni: String): Paciente? {
        val paciente = buscarPacientePorDNI(dni)
        if (paciente != null){
            conjuntoPacientes.remove(paciente)
            return paciente
        }else  {
            return paciente
        }
    }

    override fun isHospitalLleno(): Boolean {
        return conjuntoPacientes.size >= 50
    }

    override fun numPacientes(): Int {
        return conjuntoPacientes.count()
    }

    override fun todosLosPacientes(): List<Paciente?> {
        return conjuntoPacientes.toList()
    }

    override fun orderPacientesByFechaDeIngreso(): List<Paciente?> {
        return conjuntoPacientes.sortedWith { p1, p2 -> p1?.fechaIngreso!!.compareTo(p2!!.fechaIngreso)}.toList()
    }

    override fun buscarPacientePorDNI(dni: String): Paciente? {
        return conjuntoPacientes.find { it?.DNI == dni }
    }

    override fun pacientesPorTipo(tipoPaciente: TipoPaciente): List<Paciente?> {
        return conjuntoPacientes.filter { it?.tipo == tipoPaciente }.toList()
    }

    override fun numPacientesPorTipo(tipoPaciente: TipoPaciente): Int {
        return conjuntoPacientes.count { it?.tipo == tipoPaciente }
    }
}
