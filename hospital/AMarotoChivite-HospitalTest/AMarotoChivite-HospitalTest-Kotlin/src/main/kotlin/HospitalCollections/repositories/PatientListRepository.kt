package HospitalCollections.repositories

import HospitalCollections.base.IHospitalRepository
import HospitalCollections.models.Patient
import HospitalCollections.models.Patient.TypePatient

class PatientListRepository : IHospitalRepository {
    private val dataBase = ArrayList<Patient?>()

    override fun getAll(): List<Patient?> {
        return dataBase
    }

    override fun getById(id: String?): Patient? {
        for (patient in dataBase) {
            if (patient!!.getDni() == id) {
                return patient
            }
        }
        return null
    }

    override fun save(entity: Patient?): Patient? {
        // Update
        for (i in dataBase.indices) {
            if (entity != null) {
                if (dataBase[i] != null && dataBase[i]!!.getDni() == entity.getDni()) {
                    dataBase[i] = entity
                    return entity
                }
            }
        }
        // Add
        dataBase.add(entity)
        return entity
    }

    override fun deleteById(id: String?): Patient? {
        for (i in dataBase.indices) {
            if (dataBase[i] != null && dataBase[i]!!.getDni() == id) {
                val patientDeleted = dataBase[i]
                dataBase[i] = null
                return patientDeleted
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
                if (patient!!.type == TypePatient.NORMAL) {
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
            return dataBase.sortedWith(compareBy { it!!.getDateEnter() })
        }

    override val allOrderByName: List<Patient?>
        get() {
            // Ordenar sobre la marcha sin comparator, con programación funcional
            return dataBase.sortedWith(compareBy { it!!.getName() })
        }

    override val allOrderByType: List<Patient?>
        get() {
            // Ordenar sobre la marcha sin comparator, con programación funcional
            return dataBase.sortedWith(compareBy { it!!.type })
        }
}