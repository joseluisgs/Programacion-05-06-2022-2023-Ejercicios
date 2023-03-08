package controller

import enums.TipoDePaciente
import exceptions.PacienteBadRquestException
import exceptions.PacienteNotFoundException
import exceptions.PacientesAreEmptyException
import exceptions.PacientesAreFullException
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import models.Paciente
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.assertThrows
import repository.HospitalRepository
import java.time.LocalDate

internal class HospitalControllerTest {

    @MockK
    private lateinit var repository: HospitalRepository<Paciente, String>

    @InjectMockKs
    private lateinit var controller: HospitalController

    val misPacientes = listOf<Paciente>(Paciente("87654321A", "Manuel", LocalDate.of(2012,4,17), TipoDePaciente.URGENCIA, LocalDate.of(2023,2,14)), Paciente("12345678Z", "Iván", LocalDate.of(2004,8,1), TipoDePaciente.NORMAL,
        LocalDate.now()))


    init{
        MockKAnnotations.init(this)
    }

    @Test
    fun hospitalIsNotFull() {
        every{ repository.isHospitalFull() } returns false
        assertFalse(controller.isHospitalFull())
    }

    @Test
    fun hospitalIsFull(){
        every{ repository.isHospitalFull() } returns true
        assertTrue(controller.isHospitalFull())
    }

    @Test
    fun introduce() {
        every { repository.introduce(misPacientes[0]) } returns misPacientes[0]
        assertEquals(misPacientes[0], controller.introduce(misPacientes[0]))
    }

    @Test
    fun introduceButHospitalFull(){
        every { repository.introduce(misPacientes[0]) } returns null
        val message = assertThrows<PacientesAreFullException> { controller.introduce(misPacientes[0]) }
        assertEquals("Error del sistema: No caben más pacientes en el hospital.", message.message)
    }

    @Test
    fun giveAlta() {
        val patient = Paciente("87654321A", "Manuel", LocalDate.of(2012,4,17), TipoDePaciente.URGENCIA, LocalDate.of(2023,2,14), LocalDate.now())
        every { repository.getNumberOfPatients() } returns 2
        every { repository.giveAlta(patient.dni) }returns patient
        assertEquals(patient, controller.giveAlta(patient.dni))
    }

    @Test
    fun giveAltaButNotFound(){
        every { repository.getNumberOfPatients() } returns 2
        every { repository.giveAlta(misPacientes[0].dni) }returns null
        val message = assertThrows<PacienteNotFoundException> { controller.giveAlta(misPacientes[0].dni) }
        assertEquals("Error del sistema: No se encontró a paciente con el dni introducido.", message.message)
    }

    @Test
    fun getListOfPatients() {
        every { repository.getNumberOfPatients() } returns 2
        every { repository.getListOfPatients() } returns misPacientes
        val repo = controller.getListOfPatients()
        assertAll(
            {assertEquals(2, repo.size)},
            { assertEquals(misPacientes[0], repo[0])},
            { assertEquals(misPacientes[1], repo[1])}
        )
    }

    @Test
    fun getNumberOfPatients() {
        every { repository.getNumberOfPatients() } returns 2
        every { repository.getNumberOfPatients() } returns 2
        assertEquals(2, controller.getNumberOfPatients())
    }

    @Test
    fun getPatientsByDni() {
        every { repository.getNumberOfPatients() } returns 2
        every { repository.getPatientsByDni(misPacientes[0].dni) } returns misPacientes[0]
        assertEquals(misPacientes[0], controller.getPatientsByDni(misPacientes[0].dni))
    }

    @Test
    fun getListOfPatientsByType() {
        every { repository.getNumberOfPatients() } returns 2
        every { repository.getListOfPatientsByType(TipoDePaciente.NORMAL) } returns listOf(misPacientes[0])
        every { repository.getListOfPatientsByType(TipoDePaciente.URGENCIA) } returns listOf(misPacientes[1])
        assertAll(
            { assertEquals(misPacientes[0], controller.getListOfPatientsByType(TipoDePaciente.NORMAL)[0])},
            { assertEquals(misPacientes[1], controller.getListOfPatientsByType(TipoDePaciente.URGENCIA)[0])}
        )
    }

    @Test
    fun getNumberOfPatientsByType() {
        every { repository.getNumberOfPatients() } returns 2
        every { repository.getNumberOfPatientsByType(TipoDePaciente.NORMAL) } returns 1
        every { repository.getNumberOfPatientsByType(TipoDePaciente.URGENCIA) } returns 1
        assertAll(
            { assertEquals(1, controller.getNumberOfPatientsByType(TipoDePaciente.NORMAL))},
            { assertEquals(1, controller.getNumberOfPatientsByType(TipoDePaciente.NORMAL))}
        )
    }

    @Test
    fun sortPatientsByFechaIngreso() {
        val misPacientesSorted = listOf<Paciente>(misPacientes[1], misPacientes[0])
        every { repository.getNumberOfPatients() } returns 2
        every { repository.sortPatientsByFechaIngreso() }returns misPacientesSorted
        assertAll(
            { assertEquals(misPacientes.size, misPacientesSorted.size)},
            { assertEquals(misPacientes[0], misPacientesSorted[1])},
            { assertEquals(misPacientes[1], misPacientesSorted[0])}
        )
    }

    @Test
    fun sortPatientsByName() {
        val misPacientesSorted = listOf<Paciente>(misPacientes[1], misPacientes[0])
        every { repository.getNumberOfPatients() } returns 2
        every { repository.sortPatientsByName() }returns misPacientesSorted
        assertAll(
            { assertEquals(misPacientes.size, misPacientesSorted.size)},
            { assertEquals(misPacientes[0], misPacientesSorted[1])},
            { assertEquals(misPacientes[1], misPacientesSorted[0])}
        )
    }

    @Test
    fun isHospitalEmpty() {
        every { repository.getNumberOfPatients() } returns 2
        assertFalse(controller.isHospitalEmpty())
    }

    @Test
    fun hospitalIsNotEmpty(){
        every { repository.getNumberOfPatients() } returns 0
        val message = assertThrows<PacientesAreEmptyException>{
            controller.isHospitalEmpty()
        }
        assertEquals("Error del sistema: No hay ningún paciente en el hospital.", message.message)
    }

    @Test
    fun isIdValid() {
        assertTrue(controller.isIdValid("12345678M"))
    }

    @Test
    fun idIsNotValid(){
        val message1 = assertThrows<PacienteBadRquestException>{
            controller.isIdValid("")
        }
        val message2 = assertThrows<PacienteBadRquestException>{
            controller.isIdValid("3yhcu4804iuuoiho3f")
        }
        assertEquals("Error del sistema: El dni introducido no puede estar vacio.", message1.message)
        assertEquals("""Error del sistema: El dni de cumplir el patrón adecuado, por ejemplo: "12345678Z".""", message2.message)
    }

    @Test
    fun entityIsValid() {
        assertTrue(controller.isEntityValid(misPacientes[0]))
    }

    @Test
    fun entityIsNotValid(){
        val message = assertThrows<PacienteBadRquestException>{
            controller.isEntityValid(Paciente("12345678M", "", LocalDate.of(2006, 12, 31), TipoDePaciente.NORMAL, LocalDate.now()))
        }
        assertEquals("Error del sistema: El nombre introducido no puede estar vacio.", message.message)
    }
}