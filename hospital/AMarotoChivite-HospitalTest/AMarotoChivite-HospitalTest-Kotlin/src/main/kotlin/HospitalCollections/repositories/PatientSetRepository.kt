package HospitalCollections.repositories

import HospitalCollections.base.IHospitalRepository
import HospitalCollections.models.Patient
import HospitalCollections.models.Patient.TypePatient
import java.util.*

class PatientSetRepository : IHospitalRepository {
    // Es un conjunto si valores repetidos, pero ordenados de forma por defecto, elegiré por DNI
    private val dataBase = TreeSet<Patient>(compareBy { it.getDni() })

    // Pasamos a lista el conjunto
    override fun getAll(): List<Patient?> {
        return dataBase.toList()
    }

    override fun getById(id: String?): Patient? {
        for (patient in dataBase) {
            if (patient.getDni() == id) {
                return patient
            }
        }
        return null
    }

    override fun save(entity: Patient?): Patient {
        dataBase.add(entity!!)
        return entity
    }

    override fun deleteById(id: String?): Patient? {
        // Únicamente podemos recorrer con un for-each, ya que por índices no podemos al ser un conjunto
        for (patient in dataBase) {
            if (patient.getDni() == id) {
                dataBase.remove(patient)
                return patient
            }
        }
        return null
    }

    override fun isFull(): Boolean {
        val maxSize = 10
        return dataBase.size >= maxSize
    }

    override val numPacients: Int
        get() = dataBase.size
    override val numByType: IntArray
        get() {
            var countNormal = 0
            var countUrgent = 0
            for (patient in dataBase) {
                if (patient.type == TypePatient.NORMAL) {
                    countNormal++
                } else if (patient.type == TypePatient.URGENT) {
                    countUrgent++
                }
            }
            val counts = IntArray(2)
            counts[0] = countNormal
            counts[1] = countUrgent
            return counts
        }
    override val allOrderByEnterDate: List<Patient?>
        get() {
            // Ordenar sobre la marcha sin comparator, con programación funcional
            val orderedPatients: List<Patient?> = dataBase.toList()
            orderedPatients.sortedBy { it!!.getDateEnter() }
            return orderedPatients
        }
    override val allOrderByName: List<Patient?>
        get() {
            // Ordenar sobre la marcha sin comparator, con programación funcional
            val orderedPatients: List<Patient?> = dataBase.toList()
            orderedPatients.sortedBy { it!!.getName() }
            return orderedPatients
        }
    override val allOrderByType: List<Patient?>
        get() {
            // Ordenar sobre la marcha sin comparator, con programación funcional
            val orderedPatients: List<Patient?> = dataBase.toList()
            orderedPatients.sortedBy { it!!.type }
            return orderedPatients
        }
}