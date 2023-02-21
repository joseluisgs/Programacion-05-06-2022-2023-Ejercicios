package repository

import enums.TipoDePaciente
import models.Paciente
import java.time.LocalDate
import java.util.*

class ListRepository(val maxCapacity: Int): HospitalRepository<Paciente, String> {
    val patients = mutableListOf<Paciente>()

    override fun isHospitalFull(): Boolean {
        return maxCapacity == patients.size
    }

    override fun getListOfPatients(): List<Paciente> {
        return patients.toList()
    }

    override fun getNumberOfPatients(): Int {
        return patients.size
    }

    override fun getListOfPatientsByType(type: TipoDePaciente): List<Paciente> {
        return patients.filter { it.tipo == type }.toList()
    }

    override fun getNumberOfPatientsByType(type: TipoDePaciente): Int {
        return patients.filter { it.tipo == type }.size
    }

    override fun sortPatientsByFechaIngreso(): List<Paciente> {
        // Con esto funciona correctamente la ordenación
        /**
        for(i in 1..patients.size-1){
            for(j in 1..patients.size-1-i){
                if(j < patients.size && j+1 < patients.size){
                    if(patients[j].fechaIngreso > patients[j+1].fechaIngreso){
                        val auxiliar = patients[j]
                        patients[j] = patients[j+1]
                        patients[j+1] = auxiliar
                    }
                }
            }
        }
        return patients.toList()
        */
        // Con esto no funciona la ordenación
        /**
        val lista = TreeSet<Paciente>{ o1, o2 -> o1.fechaIngreso.compareTo(o2.fechaIngreso) }
        lista.addAll(patients.toList())
        return lista.toList()
        */
        // Con esto funciona correctamente la ordenación, y es más corto que la burbuja
        return patients.sortedWith{ p1, p2 -> p1.fechaIngreso.compareTo(p2.fechaIngreso)}.toList()
    }

    override fun sortPatientsByName(): List<Paciente> {
        return patients.toSortedSet{ p1, p2 -> p1.nombre.compareTo(p2.nombre)}.toList()
    }

    override fun getPatientsByDni(id: String): Paciente? {
        return patients.find { it.dni == id }
    }

    override fun giveAlta(id: String): Paciente? {
        val patient = getPatientsByDni(id)
        if (patient != null) {
            if(patient.fechaAlta == null){
                patients.remove(patient)
                patient.fechaAlta = LocalDate.now()
                return patient
            }else{
                patients.remove(patient)
                return patient
            }
        }
        return null
    }

    override fun introduce(entity: Paciente): Paciente? {
        if(!isHospitalFull()){
            val patient = getPatientsByDni(entity.dni)
            if(patient == null){
                patients.add(entity)
                return entity
            }else{
                patients.remove(patient)
                patients.add(entity)
            }
        }
        return null
    }
}