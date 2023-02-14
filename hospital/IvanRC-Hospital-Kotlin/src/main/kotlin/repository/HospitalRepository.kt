package repository

import enums.TipoDePaciente

interface HospitalRepository<T, ID> {
    fun isHospitalFull(): Boolean
    fun introduce(entity: T): T?
    fun giveAlta(id: ID): T?
    fun getListOfPatients(): List<T>
    fun getNumberOfPatients(): Int
    fun getPatientsByDni(id: ID): T?
    fun getListOfPatientsByType(type: TipoDePaciente): List<T>
    fun getNumberOfPatientsByType(type: TipoDePaciente): Int
    fun sortPatientsByFechaIngreso(): List<T>
    fun sortPatientsByName(): List<T>
}