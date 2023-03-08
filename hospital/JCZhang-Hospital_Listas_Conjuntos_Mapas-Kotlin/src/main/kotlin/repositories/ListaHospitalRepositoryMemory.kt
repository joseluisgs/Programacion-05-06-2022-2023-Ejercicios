package repositories

import enums.TipoPaciente
import exceptions.DNINotFoundException
import exceptions.TypeNotFoundException
import models.Paciente
import java.time.LocalDate

class ListaHospitalRepositoryMemory : HospitalRepository<Paciente, String> {

    val listaPacientes = mutableListOf<Paciente>()


    override fun ingresar(newPatient: Paciente): Paciente {
        if (!isHospitalLleno()){
            listaPacientes.add(newPatient)
            newPatient.fechaIngreso = LocalDate.now()
            return newPatient
        }
        return newPatient
    }

    override fun darAlta(dni: String): Paciente? {
        val paciente = buscarPacientePorDNI(dni)
        if (paciente != null){
            listaPacientes.remove(paciente)
            paciente.fechaAlta = LocalDate.now()
            return paciente
        }else{
            return paciente
        }

    }

    override fun isHospitalLleno(): Boolean {
        return listaPacientes.size >= 50
    }

    override fun numPacientes(): Int {

        // con programacion normal

//        var pacientesCount = 0
//        for (i in listaPacientes.indices) {
//            if (listaPacientes[i] != null) {
//                pacientesCount++
//            }
//        }
//        return pacientesCount


        // con programacion funcional
        return listaPacientes.count()
    }

    override fun todosLosPacientes(): MutableList<Paciente> {
        return listaPacientes
    }

    override fun orderPacientesByFechaDeIngreso(): List<Paciente?> {
        return listaPacientes.sortedWith(){p1, p2 -> p1?.fechaIngreso!!.compareTo(p2!!.fechaIngreso)}.toList()
    }

    override fun buscarPacientePorDNI(dni: String): Paciente? {
        return listaPacientes.find{it.DNI == dni }
    }

    override fun pacientesPorTipo(tipoPaciente: TipoPaciente): List<Paciente> {
        return listaPacientes.filter { it.tipo == tipoPaciente }.toList()
    }

    override fun numPacientesPorTipo(tipoPaciente: TipoPaciente): Int {
        return listaPacientes.count { it.tipo == tipoPaciente }
    }
}