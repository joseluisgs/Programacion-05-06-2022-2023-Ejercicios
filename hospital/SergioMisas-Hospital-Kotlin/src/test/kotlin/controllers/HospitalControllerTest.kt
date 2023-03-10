package controllers

import exceptions.HospitalLLenoException
import exceptions.HospitalVacioException
import exceptions.PacienteNoEncontradoException
import exceptions.TipoPacienteException
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import models.Paciente
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import repositories.HospitalListaRepository
import java.time.LocalDate

@ExtendWith(MockKExtension::class)
class HospitalControllerTest {

    @MockK
    private lateinit var repo: HospitalListaRepository

    @InjectMockKs
    private lateinit var controller: HospitalController

    val listaPaciente = listOf(
        Paciente(
            "12345678I",
            "Pepito",
            LocalDate.of(2017, 2, 1),
            LocalDate.now(),
            LocalDate.of(2000, 4, 1),
            Paciente.TipoPaciente.URGENCIA
        ), Paciente(
            "11111678I",
            "Clara",
            LocalDate.now(),
            LocalDate.now(),
            LocalDate.of(2008, 5, 2),
            Paciente.TipoPaciente.NORMAL
        )
    )

    @Test
    fun estaCompleto() {
        every { repo.estaCompleto() } returns true
        val res = controller.estaCompleto()
        assertTrue(res)
        verify { repo.estaCompleto() }
    }

    @Test
    fun obtenerTodosPacientes() {
        every { repo.obtenerTodosPacientes() } returns listOf(listaPaciente.first())
        assertEquals(listOf(listaPaciente.first()), controller.obtenerTodosPacientes())
        verify { repo.obtenerTodosPacientes() }
    }

    @Test
    fun obtenerTodosPacientesVacio() {
        every { repo.obtenerTodosPacientes() } returns listOf()
        val res = assertThrows<HospitalVacioException> { controller.obtenerTodosPacientes() }
        assertEquals("Hospital está vacío", res.message)
        verify { repo.obtenerTodosPacientes() }
    }

    @Test
    fun pacientesOrdenFechaIngreso() {
        every { repo.pacientesOrdenFechaIngreso() } returns listOf(listaPaciente.first())
        assertEquals(listOf(listaPaciente.first()), controller.pacientesOrdenFechaIngreso())
        verify { repo.pacientesOrdenFechaIngreso() }
    }

    @Test
    fun pacientesFechaIngresoVacio() {
        every { repo.pacientesOrdenFechaIngreso() } returns listOf()
        val res = assertThrows<HospitalVacioException> { controller.pacientesOrdenFechaIngreso() }
        assertEquals("Hospital está vacío", res.message)
        verify { repo.pacientesOrdenFechaIngreso() }
    }

    @Test
    fun pacientesOrdenNombre() {
        every { repo.pacientesOrdenNombre() } returns listOf(listaPaciente.first())
        assertEquals(listOf(listaPaciente.first()), controller.pacientesOrdenNombre())
        verify { repo.pacientesOrdenNombre() }
    }

    @Test
    fun pacientesOrdenNombreVacio() {
        every { repo.pacientesOrdenNombre() } returns listOf()
        val res = assertThrows<HospitalVacioException> { controller.pacientesOrdenNombre() }
        assertEquals("Hospital está vacío", res.message)
        verify { repo.pacientesOrdenNombre() }
    }

    @Test
    fun buscarPacienteDni() {
        every { repo.buscarPacienteDni(any()) } returns listaPaciente.first()
        assertEquals(listaPaciente.first(), controller.buscarPacienteDni("12345678I"))
        verify { repo.buscarPacienteDni(any()) }
    }

    @Test
    fun buscarPacienteDniNotFound() {
        every { repo.buscarPacienteDni(any()) } returns null
        val res = assertThrows<PacienteNoEncontradoException> { controller.buscarPacienteDni("") }
        assertEquals("Paciente no encontrado", res.message)
        verify { repo.buscarPacienteDni(any()) }
    }

    @Test
    fun buscarPacientePorTipo() {
        every { repo.buscarPacientePorTipo(any()) } returns listOf(listaPaciente.first())
        assertEquals(listOf(listaPaciente.first()), controller.buscarPacientePorTipo(Paciente.TipoPaciente.URGENCIA))
        verify { repo.buscarPacientePorTipo(any()) }
    }

    @Test
    fun buscarPacientePorTipoNotFound() {
        every { repo.buscarPacientePorTipo(any()) } returns listOf()
        val res =
            assertThrows<PacienteNoEncontradoException> { controller.buscarPacientePorTipo(Paciente.TipoPaciente.URGENCIA) }
        assertEquals("Paciente no encontrado", res.message)
        verify { repo.buscarPacientePorTipo(any()) }
    }

    @Test
    fun numPacientesPorTipo() {
        every { repo.numPacientesPorTipo(any()) } returns 1
        assertEquals(1, controller.numPacientesPorTipo(Paciente.TipoPaciente.URGENCIA))
        verify { repo.numPacientesPorTipo(any()) }
    }

    @Test
    fun numPacientesPorTipoCero() {
        every { repo.numPacientesPorTipo(any()) } returns 0
        val res = assertThrows<TipoPacienteException> { controller.numPacientesPorTipo(Paciente.TipoPaciente.URGENCIA) }
        assertEquals("Tipo paciente no encontrado", res.message)
        verify { repo.numPacientesPorTipo(any()) }
    }

    @Test
    fun darDeAlta() {
        every { repo.darDeAlta(any()) } returns listaPaciente.first()
        assertEquals(listaPaciente.first(), controller.darDeAlta(listaPaciente.first()))
        verify { repo.darDeAlta(any()) }
    }

    @Test
    fun darDeAltaNotFound() {
        every { repo.darDeAlta(any()) } returns null
        val res = assertThrows<PacienteNoEncontradoException> { controller.darDeAlta(listaPaciente.first()) }
        assertEquals("Paciente no encontrado", res.message)
        verify { repo.darDeAlta(any()) }
    }

    @Test
    fun ingresarPaciente() {
        every { repo.ingresarPaciente(any()) } returns listaPaciente.first()
        assertEquals(listaPaciente.first(), controller.ingresarPaciente(listaPaciente.first()))
        verify { repo.ingresarPaciente(any()) }
    }

    @Test
    fun ingresarPacienteCompleto() {
        every { repo.ingresarPaciente(any()) } returns null
        val res = assertThrows<HospitalLLenoException> { controller.ingresarPaciente(listaPaciente.first()) }
        assertEquals("Hospital lleno", res.message)
        verify { repo.ingresarPaciente(any()) }
    }
}