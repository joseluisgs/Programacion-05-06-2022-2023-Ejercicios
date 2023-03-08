package HospitalCollections.controllers

import HospitalCollections.base.IHospitalRepository
import HospitalCollections.exceptions.FullRepositoryException
import HospitalCollections.exceptions.PatientBadRequestException
import HospitalCollections.exceptions.PatientNotFoundException
import HospitalCollections.exceptions.TypeRepositoryException
import HospitalCollections.models.Patient
import HospitalCollections.repositories.PatientListRepository
import HospitalCollections.repositories.PatientMapRepository
import HospitalCollections.repositories.PatientSetRepository

class PatientController(
    private val patientListRepository: PatientListRepository?,
    private val patientSetRepository: PatientSetRepository?,
    private val patientMapRepository: PatientMapRepository?
) : IHospitalRepository {

    private var typeRepo: TypeRepo? = null

    private fun checkMaxCapacity(typeRepo: TypeRepo?) {

        when (typeRepo) {
            TypeRepo.LIST -> {
                if (patientListRepository!!.isFull()) {
                    throw FullRepositoryException("La base de datos está llena, no se pueden guardar más pacientes.")
                }
            }

            TypeRepo.SET -> {
                if (patientSetRepository!!.isFull()) {
                    throw FullRepositoryException("La base de datos está llena, no se pueden guardar más pacientes.")
                }
            }

            TypeRepo.MAP -> {
                if (patientMapRepository!!.isFull()) {
                    throw FullRepositoryException("La base de datos está llena, no se pueden guardar más pacientes.")
                }
            }

            null -> throw FullRepositoryException("La base no existe!")

        }
    }

    /*
    Para cambiar el tipo de repositorio que controlaremos
     */
    fun setTypeRepo(typeRepo: TypeRepo?) {
        this.typeRepo = typeRepo
    }

    fun getTypeRepo(typeRepo: TypeRepo?): TypeRepo? {
        return this.typeRepo
    }

    override fun getAll(): List<Patient?> {
        return when (typeRepo) {
            TypeRepo.LIST -> patientListRepository!!.getAll()
            TypeRepo.SET -> patientSetRepository!!.getAll()
            TypeRepo.MAP -> patientMapRepository!!.getAll()
            null -> throw PatientNotFoundException("No existe ningún paciente")
        }
    }

    override fun getById(id: String?): Patient {
        var patient: Patient? = null
        when (typeRepo) {
            TypeRepo.LIST -> {
                patient = patientListRepository!!.getById(id)
            }

            TypeRepo.SET -> {
                patient = patientSetRepository!!.getById(id)

            }

            TypeRepo.MAP -> {
                patient = patientMapRepository!!.getById(id)
            }

            else -> {
                throw TypeRepositoryException("Debes asignar un tipo de repositorio!")
            }
        }
        if (patient == null) {
            throw PatientNotFoundException("El paciente con DNI $id no se encuentra en la base de datos.")
        }
        return patient
    }

    override fun save(entity: Patient?): Patient? {
        // Comprobamos si tiene los datos correctors
        if (entity == null) {
            throw PatientNotFoundException("No has asignado ningún paciente para guardar")
        }
        checkCorrectPatient(entity)

        // Comprobamos que se puedan introducir más objetos
        checkMaxCapacity(typeRepo)
        return when (typeRepo) {
            TypeRepo.LIST -> patientListRepository!!.save(entity)
            TypeRepo.SET -> patientSetRepository!!.save(entity)
            TypeRepo.MAP -> patientMapRepository!!.save(entity)
            null -> throw TypeRepositoryException("Debes asignar un tipo de repositorio!")
        }
    }

    override fun deleteById(id: String?): Patient {
        var patient: Patient? = null
        when (typeRepo) {
            TypeRepo.LIST -> patient = patientListRepository!!.deleteById(id)
            TypeRepo.SET -> patient = patientSetRepository!!.deleteById(id)
            TypeRepo.MAP -> patient = patientMapRepository!!.deleteById(id)
            null -> throw TypeRepositoryException("Debes asignar un tipo de repositorio!")
        }
        if (patient == null) {
            throw PatientNotFoundException("El paciente con DNI $id no se encuentra en la base de datos.")
        }
        return patient
    }

    override fun isFull(): Boolean {
        return when (typeRepo) {
            TypeRepo.LIST -> patientListRepository!!.isFull()
            TypeRepo.SET -> patientSetRepository!!.isFull()
            TypeRepo.MAP -> patientMapRepository!!.isFull()
            null -> throw TypeRepositoryException("Debes asignar un tipo de repositorio!")
        }
    }

    override val numPacients: Int
        get() = when (typeRepo) {
            TypeRepo.LIST -> patientListRepository!!.numPacients
            TypeRepo.SET -> patientSetRepository!!.numPacients
            TypeRepo.MAP -> patientMapRepository!!.numPacients
            null -> throw TypeRepositoryException("Debes asignar un tipo de repositorio!")
        }
    override val numByType: IntArray
        get() = when (typeRepo) {
            TypeRepo.LIST -> patientListRepository!!.numByType
            TypeRepo.SET -> patientSetRepository!!.numByType
            TypeRepo.MAP -> patientMapRepository!!.numByType
            null -> throw TypeRepositoryException("Debes asignar un tipo de repositorio!")
        }
    override val allOrderByEnterDate: List<Patient?>
        get() = when (typeRepo) {
            TypeRepo.LIST -> patientListRepository!!.allOrderByEnterDate
            TypeRepo.SET -> patientSetRepository!!.allOrderByEnterDate
            TypeRepo.MAP -> patientMapRepository!!.allOrderByEnterDate
            null -> throw TypeRepositoryException("Debes asignar un tipo de repositorio!")
        }
    override val allOrderByName: List<Patient?>
        get() = when (typeRepo) {
            TypeRepo.LIST -> patientListRepository!!.allOrderByName
            TypeRepo.SET -> patientSetRepository!!.allOrderByName
            TypeRepo.MAP -> patientMapRepository!!.allOrderByName
            null -> throw TypeRepositoryException("Debes asignar un tipo de repositorio!")
        }
    override val allOrderByType: List<Patient?>
        get() = when (typeRepo) {
            TypeRepo.LIST -> patientListRepository!!.allOrderByType
            TypeRepo.SET -> patientSetRepository!!.allOrderByType
            TypeRepo.MAP -> patientMapRepository!!.allOrderByType
            null -> throw TypeRepositoryException("Debes asignar un tipo de repositorio!")
        }

    private fun checkCorrectPatient(patient: Patient) {
        if (patient.getDni() == null || patient.getDni() == "") {
            throw PatientBadRequestException("El DNI no puede estar vacío")
        }
        if (!filterDni(patient.getDni())) {
            throw PatientBadRequestException("El DNI debe tener 8 número y una letra: (12345678A)!")
        }
        if (patient.getName() == null || patient.getName() == "") {
            throw PatientBadRequestException("El nombre no puede estar vacío")
        }
        if (!filterName(patient.getName())) {
            throw PatientBadRequestException("El nombre debe tener entre 3 y 20 caracteres")
        }
        if (patient.getDateNac() == null || patient.getDateNac() == "") {
            throw PatientBadRequestException("La fecha de nacimiento no puede estar vacía")
        }
        if (!filterDateIso(patient.getDateNac())) {
            throw PatientBadRequestException("La fecha debe estar en formato (aaaa-mm-dd)")
        }
        if (patient.type == null) {
            throw PatientBadRequestException("El tipo del paciente no puede estar vacío")
        }
        if (patient.getDateEnter() == null || patient.getDateEnter() == "") {
            throw PatientBadRequestException("La fecha de entrada no puede estar vacía")
        }
        if (!filterDateIso(patient.getDateEnter())) {
            throw PatientBadRequestException("La fecha debe estar en formato (aaaa-mm-dd)")
        }
    }

    enum class TypeRepo {
        LIST, SET, MAP
    }

    companion object {
        private fun filterDni(dni: String?): Boolean {
            val regex = "[0-9]{8}[A-Za-z]"
            return dni!!.matches(regex.toRegex())
        }

        private fun filterName(name: String?): Boolean {
            val regex = "[a-zA-Z ]{3,20}"
            return name!!.matches(regex.toRegex())
        }

        private fun filterDateIso(dateIso: String?): Boolean {
            val regex = "\\d{4}-\\d{2}-\\d{2}"
            return dateIso!!.matches(regex.toRegex())
        }
    }
}