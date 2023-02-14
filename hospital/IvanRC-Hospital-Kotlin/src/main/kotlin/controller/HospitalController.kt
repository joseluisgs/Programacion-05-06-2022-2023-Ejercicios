package controller

import enums.TipoDePaciente
import exceptions.PacienteBadRquestException
import exceptions.PacienteNotFoundException
import exceptions.PacientesAreEmptyException
import exceptions.PacientesAreFullException
import models.Paciente
import repository.HospitalRepository

class HospitalController(
    val repository: HospitalRepository<Paciente, String>
) {
    fun isHospitalFull(): Boolean{
        return repository.isHospitalFull()
    }

    fun introduce(entity: Paciente): Paciente{
        isEntityValid(entity)
        return repository.introduce(entity)?: throw PacientesAreFullException("Error del sistema: No caben más pacientes en el hospital.")
    }

    fun giveAlta(id: String): Paciente{
        isHospitalEmpty()
        return repository.giveAlta(id)?: throw PacienteNotFoundException("Error del sistema: No se encontró a paciente con el dni introducido.")
    }

    fun getListOfPatients(): List<Paciente>{
        isHospitalEmpty()
        return repository.getListOfPatients()
    }

    fun getNumberOfPatients(): Int{
        return repository.getNumberOfPatients()
    }

    fun getPatientsByDni(id: String): Paciente{
        isHospitalEmpty()
        isIdValid(id)
        return repository.getPatientsByDni(id)?: throw PacienteNotFoundException("Error del sistema: No se encontró a paciente con el dni introducido.")
    }

    fun getListOfPatientsByType(type: TipoDePaciente): List<Paciente>{
        isHospitalEmpty()
        return repository.getListOfPatientsByType(type)
    }

    fun getNumberOfPatientsByType(type: TipoDePaciente): Int{
        return repository.getNumberOfPatientsByType(type)
    }

    fun sortPatientsByFechaIngreso(): List<Paciente>{
        isHospitalEmpty()
        return repository.sortPatientsByFechaIngreso()
    }

    fun sortPatientsByName(): List<Paciente>{
        isHospitalEmpty()
        return repository.sortPatientsByName()
    }

    fun isHospitalEmpty(): Boolean {
        require(repository.getNumberOfPatients() > 0){
            throw PacientesAreEmptyException("Error del sistema: No hay ningún paciente en el hospital.")
        }
        return false
    }

    fun isIdValid(id: String): Boolean {
        require(id.isNotEmpty()){
            throw PacienteBadRquestException("Error del sistema: El dni introducido no puede estar vacio.")
        }
        val regex = Regex("[0-9]{8}[A-Z]")
        require(id.matches(regex)){
            throw PacienteBadRquestException("""Error del sistema: El dni de cumplir el patrón adecuado, por ejemplo: "12345678Z".""")
        }
        return true
    }

    fun isEntityValid(entity: Paciente): Boolean {
        isIdValid(entity.dni)
        require(entity.nombre.isNotEmpty()){
            throw PacienteBadRquestException("Error del sistema: El nombre introducido no puede estar vacio.")
        }
        return true
    }

}