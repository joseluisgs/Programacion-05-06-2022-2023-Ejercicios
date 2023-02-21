package repository

import enums.TipoDePaciente
import models.Paciente
import java.time.LocalDate

class MapRepository(val maxCapacity: Int): HospitalRepository<Paciente, String> {
    val patients = linkedMapOf<String, Paciente>()

    override fun isHospitalFull(): Boolean {
        return maxCapacity == patients.size
    }

    override fun getListOfPatients(): List<Paciente> {
        return patients.values.toList()
    }

    override fun getNumberOfPatients(): Int {
        return patients.size
    }

    override fun getListOfPatientsByType(type: TipoDePaciente): List<Paciente> {
        return patients.values.filter { it.tipo == type }.toList()
    }

    override fun getNumberOfPatientsByType(type: TipoDePaciente): Int {
        return patients.values.filter { it.tipo == type }.size
    }

    override fun sortPatientsByFechaIngreso(): List<Paciente> {
        return patients.values.sortedWith(){ p1, p2 -> p1.fechaIngreso.compareTo(p2.fechaIngreso)}.toList()
    }

    override fun sortPatientsByName(): List<Paciente> {
        return patients.values.toSortedSet{p1,p2 -> p1.nombre.compareTo(p2.nombre)}.toList()
    }

    override fun getPatientsByDni(id: String): Paciente? {
        return patients.getOrDefault(id, null)
    }

    override fun giveAlta(id: String): Paciente? {
        val patient = getPatientsByDni(id)
        if (patient != null) {
            if(patient.fechaAlta == null){
                patients.remove(id)
                patient.fechaAlta = LocalDate.now()
                return patient
            }else{
                patients.remove(id)
                return patient
            }
        }
        return null
    }

    override fun introduce(entity: Paciente): Paciente? {
        if(!isHospitalFull()) {
            patients[entity.dni] = entity
            return entity
        }
        return null
    }
}