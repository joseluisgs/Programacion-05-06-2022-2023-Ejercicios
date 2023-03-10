package repositories

import enums.tipoPaciente

interface HospitalRepository<T, ID> {
    fun ingresar(): T?
    fun getAll(): List<T>
    fun getByDNI(DNI: ID): T?
    fun darAlta(entity: T): T?
    fun numPacientes(): Int
    fun isHospitalCompleto(): Boolean
    fun orderByFechaIngreso(): List<T>
    fun orderByNombre(): List<T>
    fun pacientesPorTipo(tipo: tipoPaciente): List<T>
    fun numPacientesTipo(tipo: tipoPaciente): Int
}