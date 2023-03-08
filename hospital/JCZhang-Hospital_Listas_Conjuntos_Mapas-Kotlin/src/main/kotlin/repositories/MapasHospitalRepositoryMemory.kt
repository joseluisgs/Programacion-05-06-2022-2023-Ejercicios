package repositories

import enums.TipoPaciente
import exceptions.TypeNotFoundException

import models.Paciente
import java.time.LocalDate

class MapasHospitalRepositoryMemory: HospitalRepository<Paciente, String> {
    val mapaPacientes = mutableMapOf<String, Paciente>()

    override fun ingresar(newPatient: Paciente): Paciente? {
        if (!isHospitalLleno()){
            mapaPacientes[newPatient.DNI] = newPatient
            newPatient.fechaIngreso = LocalDate.now()
            return newPatient
        }
        return newPatient
    }

    override fun darAlta(dni: String): Paciente? {
        val paciente = buscarPacientePorDNI(dni)
        if (paciente != null){
            mapaPacientes.remove(paciente.DNI)
            return paciente
        }else  {
            return paciente
        }
    }

    override fun isHospitalLleno(): Boolean {
        return mapaPacientes.size >= 50
    }

    override fun numPacientes(): Int {
        return mapaPacientes.keys.count()
    }

    override fun todosLosPacientes(): List<Paciente?> {
        return mapaPacientes.values.toList()
    }

    override fun orderPacientesByFechaDeIngreso(): List<Paciente?> {
        return mapaPacientes.values.toList().sortedWith(){p1, p2 -> p1?.fechaIngreso!!.compareTo(p2!!.fechaIngreso)}.toList()
    }

    override fun buscarPacientePorDNI(dni: String): Paciente? {
        return mapaPacientes[dni]
    }

    override fun pacientesPorTipo(tipoPaciente: TipoPaciente): List<Paciente> {
       return mapaPacientes.values.toList().filter { it.tipo == tipoPaciente }
    }

    override fun numPacientesPorTipo(tipoPaciente: TipoPaciente): Int {
        return mapaPacientes.values.toList().filter { it.tipo == tipoPaciente }.count()
    }
}