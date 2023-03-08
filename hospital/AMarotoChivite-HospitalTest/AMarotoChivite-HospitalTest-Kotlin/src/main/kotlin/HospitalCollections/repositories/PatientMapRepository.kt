package HospitalCollections.repositories

import HospitalCollections.base.IHospitalRepository
import HospitalCollections.models.Patient
import HospitalCollections.models.Patient.TypePatient
import java.util.*

class PatientMapRepository : IHospitalRepository {
    // El mapa ordena por defecto por la clave
    private val dataBase = TreeMap<String?, Patient?>()

    // Pasamos a lista los valores del mapa
    override fun getAll(): List<Patient?> {
        return dataBase.values.toList()
    }

    override fun getById(id: String?): Patient? {
        return dataBase[id]
    }

    override fun save(entity: Patient?): Patient {
        dataBase[entity!!.getDni()] = entity
        return entity
    }

    override fun deleteById(id: String?): Patient? {
        val patientDeleted = dataBase[id]
        dataBase.remove(id)
        return patientDeleted
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
            val values: Collection<Patient?> = dataBase.values
            for (patient in values) {
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
            val orderedPatients: List<Patient?> = ArrayList(dataBase.values)
            orderedPatients.sortedBy { it!!.getDateEnter() }
            return orderedPatients
        }

    override val allOrderByName: List<Patient?>
        get() {
            // Ordenar sobre la marcha sin comparator, con programación funcional
            val orderedPatients: List<Patient?> = ArrayList(dataBase.values)
            orderedPatients.sortedBy { it!!.getName() }
            return orderedPatients
        }

    override val allOrderByType: List<Patient?>
        get() {
            // Ordenar sobre la marcha sin comparator, con programación funcional
            val orderedPatients: List<Patient?> = ArrayList(dataBase.values)
            orderedPatients.sortedBy { it!!.type }
            return orderedPatients
        }
}