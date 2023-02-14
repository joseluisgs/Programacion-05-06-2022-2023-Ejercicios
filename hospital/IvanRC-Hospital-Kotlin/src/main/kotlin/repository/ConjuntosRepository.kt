package repository

import enums.TipoDePaciente
import models.Paciente
import java.time.LocalDate

class ConjuntosRepository(val maxCapacity: Int): HospitalRepository<Paciente, String> {
    val patients = linkedSetOf<Paciente>()

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
        val list = mutableListOf<Paciente>()
        when(type){
            TipoDePaciente.NORMAL -> {
                for(patient in patients){
                    if(patient.tipo == TipoDePaciente.NORMAL) list.add(patient)
                }
            }
            TipoDePaciente.URGENCIA -> {
                for(patient in patients){
                    if(patient.tipo == TipoDePaciente.URGENCIA) list.add(patient)
                }
            }
        }
        return list.toList()
    }

    override fun getNumberOfPatientsByType(type: TipoDePaciente): Int {
        var contador = 0
        when(type){
            TipoDePaciente.NORMAL -> {
                for(patient in patients){
                    if(patient.tipo == TipoDePaciente.NORMAL) contador++
                }
            }
            TipoDePaciente.URGENCIA -> {
                for(patient in patients){
                    if(patient.tipo == TipoDePaciente.URGENCIA) contador++
                }
            }
        }
        return contador
    }

    override fun sortPatientsByFechaIngreso(): List<Paciente> {
        return patients.sortedWith(){ p1, p2 -> p1.fechaIngreso.compareTo(p2.fechaIngreso)}.toList()
    }

    override fun sortPatientsByName(): List<Paciente> {
        return patients.toSortedSet{ p1, p2 -> p1.nombre.compareTo(p2.nombre)}.toList()
    }

    override fun getPatientsByDni(id: String): Paciente? {
        for(patient in patients){
            if(patient.dni == id) return patient
        }
        return null
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