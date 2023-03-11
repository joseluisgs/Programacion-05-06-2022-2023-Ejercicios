package controllers

import exceptions.HospitalVacioException
import exceptions.IngresoException
import exceptions.PacienteNotFoundException
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import models.Paciente
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import repositories.HospitalRepository
import java.time.LocalDate

@ExtendWith(MockKExtension::class)
class HospitalControllerTest {
    @MockK
    private lateinit var hospitalRepository: HospitalRepository

    @InjectMockKs
    private lateinit var hospitalController: HospitalController

    private val pacientes = listOf(
        Paciente("11111111A", "Ronaldo", LocalDate.of(2000, 1, 1)),
        Paciente("22222222B", "María", LocalDate.of(2000, 1, 1), tipoPaciente = Paciente.TipoPaciente.URGENCIA),
        Paciente("33333333C", "Felipe", LocalDate.of(2000, 1, 1), tipoPaciente = Paciente.TipoPaciente.URGENCIA)
    )

    @Test
    fun ingresar() {
        every { hospitalRepository.ingresar(any()) } returns pacientes[0]

        assertEquals(pacientes[0], hospitalController.ingresar(pacientes[0]))

        verify { hospitalRepository.ingresar(any()) }
    }

    @Test
    fun ingresoException() {
        every { hospitalRepository.ingresar(any()) } returns null

        val res = assertThrows<IngresoException> { hospitalController.ingresar(pacientes[0]) }
        assertEquals("No se ha podido ingresar el paciente", res.message)

        verify { hospitalRepository.ingresar(any()) }
    }

    @Test
    fun darAlta() {
        every { hospitalRepository.darAlta(any()) } returns pacientes[0]

        assertEquals(pacientes[0], hospitalController.darAlta(pacientes[0].dni))

        verify { hospitalRepository.darAlta(any()) }
    }

    @Test
    fun darAltaPacienteNotFound() {
        every { hospitalRepository.darAlta(any()) } returns null

        val res = assertThrows<PacienteNotFoundException> { hospitalController.darAlta(pacientes[0].dni) }
        assertEquals("No se ha encontrado el paciente con dni ${pacientes[0].dni}", res.message)

        verify { hospitalRepository.darAlta(any()) }
    }

    @Test
    fun estaCompleto() {
        every { hospitalRepository.estaCompleto() } returns true

        assertTrue(hospitalController.estaCompleto())

        every { hospitalRepository.estaCompleto() } returns false

        assertFalse(hospitalController.estaCompleto())

        verify { hospitalRepository.estaCompleto() }
    }

    @Test
    fun numPacientes() {
        every { hospitalRepository.numPacientes() } returns 1

        assertEquals(1, hospitalController.numPacientes())

        verify { hospitalRepository.numPacientes() }
    }

    @Test
    fun obtenerTodosPacientes() {
        every { hospitalRepository.obtenerTodosPacientes() } returns pacientes

        assertEquals(pacientes, hospitalController.obtenerTodosPacientes())

        verify { hospitalRepository.obtenerTodosPacientes() }
    }

    @Test
    fun obtenerTodosPacientesVacio() {
        every { hospitalRepository.obtenerTodosPacientes() } returns listOf()

        val res = assertThrows<HospitalVacioException> { hospitalController.obtenerTodosPacientes() }
        assertEquals("No se han encontrado pacientes", res.message)

        verify { hospitalRepository.obtenerTodosPacientes() }
    }

    @Test
    fun pacientePorDni() {
        every { hospitalRepository.pacientePorDni(any()) } returns pacientes[0]

        assertEquals(pacientes[0], hospitalController.pacientePorDni(pacientes[0].dni))

        verify { hospitalRepository.pacientePorDni(any()) }
    }

    @Test
    fun pacientePorDniNotFound() {
        every { hospitalRepository.pacientePorDni(any()) } returns null

        val res = assertThrows<PacienteNotFoundException> { hospitalController.pacientePorDni(pacientes[0].dni) }
        assertEquals("No se ha encontrado el paciente con dni ${pacientes[0].dni}", res.message)

        verify { hospitalRepository.pacientePorDni(any()) }
    }

    @Test
    fun pacientesOrdenadosFechaIngreso() {
        every { hospitalRepository.pacientesOrdenadosFechaIngreso() } returns pacientes

        assertEquals(pacientes, hospitalController.pacientesOrdenadosFechaIngreso())

        verify { hospitalRepository.pacientesOrdenadosFechaIngreso() }
    }

    @Test
    fun pacientesOrdenadosFechaIngresoVacio() {
        every { hospitalRepository.pacientesOrdenadosFechaIngreso() } returns listOf()

        val res = assertThrows<HospitalVacioException> { hospitalController.pacientesOrdenadosFechaIngreso() }
        assertEquals("No se han encontrado pacientes", res.message)

        verify { hospitalRepository.pacientesOrdenadosFechaIngreso() }
    }

    @Test
    fun pacientesOrdenadosNombre() {
        every { hospitalRepository.pacientesOrdenadosNombre() } returns pacientes

        assertEquals(pacientes, hospitalController.pacientesOrdenadosNombre())

        verify { hospitalRepository.pacientesOrdenadosNombre() }
    }

    @Test
    fun pacientesOrdenadosNombreVacio() {
        every { hospitalRepository.pacientesOrdenadosNombre() } returns listOf()

        val res = assertThrows<HospitalVacioException> { hospitalController.pacientesOrdenadosNombre() }
        assertEquals("No se han encontrado pacientes", res.message)

        verify { hospitalRepository.pacientesOrdenadosNombre() }
    }

    @Test
    fun pacientesPorTipo() {
        every { hospitalRepository.pacientesPorTipo(any()) } returns pacientes

        assertEquals(pacientes, hospitalController.pacientesPorTipo(Paciente.TipoPaciente.URGENCIA))

        verify { hospitalRepository.pacientesPorTipo(any()) }
    }

    @Test
    fun pacientesPorTipoNotFound() {
        every { hospitalRepository.pacientesPorTipo(any()) } returns listOf()

        val res = assertThrows<PacienteNotFoundException> { hospitalController.pacientesPorTipo(Paciente.TipoPaciente.URGENCIA) }
        assertEquals("No se han encontrado pacientes de tipo ${Paciente.TipoPaciente.URGENCIA}", res.message)

        verify { hospitalRepository.pacientesPorTipo(any()) }
    }

    @Test
    fun numPacientesPorTipo() {
        every { hospitalRepository.numPacientesPorTipo(any()) } returns 1

        assertEquals(1, hospitalController.numPacientesPorTipo(Paciente.TipoPaciente.URGENCIA))

        verify { hospitalRepository.numPacientesPorTipo(any()) }
    }

    @Test
    fun numPacientesPorTipoNotFound() {
        every { hospitalRepository.numPacientesPorTipo(any()) } returns 0

        val res = assertThrows<PacienteNotFoundException> { hospitalController.numPacientesPorTipo(Paciente.TipoPaciente.URGENCIA) }
        assertEquals("No se han encontrado pacientes de tipo ${Paciente.TipoPaciente.URGENCIA}", res.message)

        verify { hospitalRepository.numPacientesPorTipo(any()) }
    }
}