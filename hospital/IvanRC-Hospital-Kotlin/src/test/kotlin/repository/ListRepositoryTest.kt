package repository

import enums.TipoDePaciente
import models.Paciente
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import java.time.LocalDate

internal class ListRepositoryTest {

    val repository = ListRepository(3)

    val misPacientes = listOf<Paciente>(Paciente("12345678Z", "Iván", LocalDate.of(2004,8,1), TipoDePaciente.NORMAL,LocalDate.now()),
        Paciente("87654321A", "Manuel", LocalDate.of(2012,4,17), TipoDePaciente.URGENCIA,LocalDate.of(2023,2,14)))

    @BeforeEach
    fun setUp(){
        deleteAll()
        createAll(misPacientes)
    }

    private fun createAll(entities: List<Paciente>) {
        for (patient in entities) {
            repository.introduce(patient)
        }
    }

    private fun deleteAll() {
        for (patient in repository.getListOfPatients()) {
            repository.giveAlta(patient.dni)
        }
    }

    @Test
    fun isHospitalFull() {
        assertFalse(repository.isHospitalFull())
    }

    @Test
    fun getListOfPatients() {
        val pacientes = repository.getListOfPatients()
        assertAll(
            {assertEquals(2, pacientes.size)},
            {assertEquals(misPacientes[0], pacientes[0])},
            {assertEquals(misPacientes[1], pacientes[1])}
        )
    }

    @Test
    fun getNumberOfPatients() {
        var pacientes = repository.getListOfPatients()
        assertEquals(2, pacientes.size)
        deleteAll()
        pacientes = repository.getListOfPatients()
        assertEquals(0, pacientes.size)
    }

    @Test
    fun getListOfPatientsByType() {
        val normales = repository.getListOfPatientsByType(TipoDePaciente.NORMAL)
        assertAll(
            { assertEquals(1, normales.size)},
            { assertEquals(misPacientes[0], normales[0])}
        )
        val urgentes = repository.getListOfPatientsByType(TipoDePaciente.URGENCIA)
        assertAll(
            { assertEquals(1, urgentes.size)},
            { assertEquals(misPacientes[1], urgentes[0])}
        )
    }

    @Test
    fun getNumberOfPatientsByType() {
        val normales = repository.getNumberOfPatientsByType(TipoDePaciente.NORMAL)
        assertEquals(1, normales)
        val urgentes = repository.getNumberOfPatientsByType(TipoDePaciente.URGENCIA)
        assertEquals(1, urgentes)
    }

    @Test
    fun sortPatientsByFechaIngreso() {
        val sortedByDate = repository.sortPatientsByFechaIngreso()
        assertAll(
            { assertEquals(2, sortedByDate.size)},
            { assertEquals(misPacientes[0], sortedByDate[0])},
            { assertEquals(misPacientes[1], sortedByDate[1])}
        )
    }

    @Test
    fun sortPatientsByName() {
        val sortedByName = repository.sortPatientsByName()
        assertAll(
            { assertEquals(2, sortedByName.size)},
            { assertEquals(misPacientes[0], sortedByName[0])},
            { assertEquals(misPacientes[1], sortedByName[1])}
        )
    }

    @Test
    fun getPatientsByDni() {
        assertAll(
            { assertEquals(misPacientes[0], repository.getPatientsByDni("12345678Z"))},
            {assertNull(repository.getPatientsByDni("99999999A"))}
        )
    }

    @Test
    fun giveAlta() {
        val date = LocalDate.now()
        assertAll(
            { assertEquals(Paciente("12345678Z", "Iván", LocalDate.of(2004,8,1), TipoDePaciente.NORMAL,LocalDate.now(), date),
                repository.giveAlta("12345678Z")
            ) },
            { assertNull(repository.giveAlta("142336143615745274r275284"))}
        )
    }

    @Test
    fun introduce() {
        val entity = Paciente("9876321Z", "IvánRC", LocalDate.of(2004,8,1), TipoDePaciente.URGENCIA,LocalDate.now())
        val entityExtra = Paciente("14356478G", "ElQueSobra", LocalDate.of(2004,8,1), TipoDePaciente.URGENCIA,LocalDate.now())
        assertAll(
            { assertEquals(entity, repository.introduce(entity))},
            {assertNull(repository.introduce(entityExtra))}
        )
    }
}