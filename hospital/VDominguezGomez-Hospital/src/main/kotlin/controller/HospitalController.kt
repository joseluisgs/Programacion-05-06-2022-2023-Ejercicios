package controller

import enums.tipoPaciente
import models.Paciente
import repositories.HospitalRepository
import `typealias`.ListaPacientes
import java.lang.Exception

class HospitalController(
    private val repository: HospitalRepository<Paciente, String>
) {

    fun ingresar(): Paciente {
        return repository.ingresar()
            ?: throw Exception("El aforo del hospital está completo")
    }

    fun getAll(): ListaPacientes {
        return repository.getAll()
    }

    fun getByDNI(DNI: String): Paciente {
        return repository.getByDNI(DNI)
            ?: throw Exception("No se ha encontrado el paciente con DNI \"$DNI\"")
    }

    fun darAlta(entity: Paciente): Paciente {
        return repository.darAlta(entity)
            ?: throw Exception("No se encuentra el paciente")
    }

    fun numPacientes(): Int {
        return repository.numPacientes()
    }

    fun isHospitalCompleto(): Boolean {
        return repository.isHospitalCompleto()
    }

    fun orderByFechaIngreso(): ListaPacientes {
        return repository.orderByFechaIngreso()
    }

    fun orderByNombre(): ListaPacientes {
        return repository.orderByNombre()
    }

    fun pacientesPorTipo(tipo: tipoPaciente): ListaPacientes {
        return repository.pacientesPorTipo(tipo)
    }

    fun numPacientesTipo(tipo: tipoPaciente): Int {
        return repository.numPacientesTipo(tipo)
    }

}