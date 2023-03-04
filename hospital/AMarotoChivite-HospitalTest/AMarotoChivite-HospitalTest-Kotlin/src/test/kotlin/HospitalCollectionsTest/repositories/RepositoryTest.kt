package HospitalCollectionsTest.repositories

import HospitalCollections.base.IHospitalRepository
import HospitalCollections.models.Patient
import HospitalCollections.utils.randomData.randomDni
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

abstract class RepositoryTest {

    // Interfaz comun de las implementaciones de todos los repositorios
    protected abstract var repoToEspecify: IHospitalRepository

    @Test
    fun testGetAllReturnsAllPatients() {
        val patient1 = Patient("12345678A", "John Doe", "1990-07-10", "2000-01-01", Patient.TypePatient.NORMAL)
        val patient2 = Patient("87654321B", "Jane Doe", "1990-07-10", "2000-01-01", Patient.TypePatient.URGENT)
        repoToEspecify.save(patient1)
        repoToEspecify.save(patient2)
        val allPatients: List<Patient?> = repoToEspecify.getAll()
        assertEquals(2, allPatients.size)
        assertTrue(allPatients.contains(patient1))
        assertTrue(allPatients.contains(patient2))
    }

    @Test
    fun testSaveAndGetById() {
        val patient1 = Patient("12345678A", "John Doe", "1990-07-10", "2000-01-01", Patient.TypePatient.NORMAL)
        repoToEspecify.save(patient1)
        val savedPatient = repoToEspecify.getById("12345678A")
        assertEquals(patient1, savedPatient)
    }

    @Test
    fun testDeleteById() {
        val patient1 = Patient("87654321B", "Jane Doe", "1990-07-10", "2000-01-01", Patient.TypePatient.URGENT)
        repoToEspecify.save(patient1)
        val deletedPatient = repoToEspecify.deleteById("87654321B")
        assertEquals(patient1, deletedPatient)
        // Verifico que se ha borrado nuevamente
        val deletedPatientAgain = repoToEspecify.deleteById("87654321B")
        assertNull(deletedPatientAgain)
    }

    @Test
    fun testIsFull() {
        // Caso no está completo
        assertFalse(repoToEspecify.isFull())
        val patient1 = Patient("87654321B", "Jane Doe", "1990-07-10", "2000-01-01", Patient.TypePatient.URGENT)
        repoToEspecify.save(patient1)
        assertFalse(repoToEspecify.isFull())

        // Caso está completo
        for (i in 0..10) {
            val randomId: String = randomDni()
            repoToEspecify.save(Patient(randomId, "John Doe", "2000-01-01", "2000-01-01", Patient.TypePatient.NORMAL))
        }
        assertTrue(repoToEspecify.isFull())
    }

    @Test
    fun testGetNumPacients() {
        assertEquals(0, repoToEspecify.numPacients)
        val patient1 = Patient("33333333C", "David Lee", "1990-07-10", "2000-01-01", Patient.TypePatient.NORMAL)
        repoToEspecify.save(patient1)
        assertEquals(1, repoToEspecify.numPacients)
    }

    @Test
    fun testGetNumByType() {
        repoToEspecify.save(Patient("123", "Juan", "1990-07-10", "2000-01-01", Patient.TypePatient.URGENT))
        repoToEspecify.save(Patient("456", "Pedro", "1990-07-10", "2000-01-01", Patient.TypePatient.NORMAL))
        repoToEspecify.save(Patient("789", "María", "1990-07-10", "2000-01-01", Patient.TypePatient.URGENT))

        // Índice 0 = Normal
        // Índice 0 = Urgente
        val counts = repoToEspecify.numByType
        assertEquals(2, counts.size)
        assertEquals(1, counts[0])
        assertEquals(2, counts[1])
    }

    @Test
    fun testGetAllOrderByEnterDate() {
        val patient1 = Patient("11111111A", "Mike Smith", "1980-07-10", "2000-01-01", Patient.TypePatient.NORMAL)
        val patient2 = Patient("22222222B", "Sarah Johnson", "1980-07-10", "2001-01-01", Patient.TypePatient.URGENT)
        // Primero introduzco el pacient2, ya que, es el que debería estar en la posición segunda posteriormente
        repoToEspecify.save(patient2)
        repoToEspecify.save(patient1)
        val orderedList = repoToEspecify.allOrderByEnterDate
        assertEquals(patient1, orderedList[0])
        assertEquals(patient2, orderedList[1])
    }

    @Test
    fun testGetAllOrderByName() {
        val patient1 = Patient("11111111A", "Mike Smith", "1980-07-10", "2000-01-01", Patient.TypePatient.NORMAL)
        val patient2 = Patient("22222222B", "Sarah Johnson", "1980-07-10", "2001-01-01", Patient.TypePatient.URGENT)
        // Primero introduzco el pacient2, ya que, es el que debería estar en la posición segunda posteriormente
        repoToEspecify.save(patient2)
        repoToEspecify.save(patient1)
        val orderedList = repoToEspecify.allOrderByName
        assertEquals(patient1, orderedList[0])
        assertEquals(patient2, orderedList[1])
    }

    @Test
    fun testGetAllOrderByType() {
        val patient1 = Patient("11111111A", "Mike Smith", "1980-07-10", "2000-01-01", Patient.TypePatient.NORMAL)
        val patient2 = Patient("22222222B", "Sarah Johnson", "1980-07-10", "2001-01-01", Patient.TypePatient.URGENT)
        // Primero introduzco el pacient2, ya que, es el que debería estar en la posición segunda posteriormente
        repoToEspecify.save(patient2)
        repoToEspecify.save(patient1)
        val orderedList = repoToEspecify.allOrderByEnterDate
        assertEquals(patient1, orderedList[0])
        assertEquals(patient2, orderedList[1])
    }
}