package repositories

import models.Paciente
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import java.time.LocalDate
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue

class HospitalConjuntoRepositoryTest {
    private lateinit var repo: HospitalConjuntoRepository
    private lateinit var repoLleno: HospitalConjuntoRepository
    private val lista = listOf(
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

    @BeforeEach
    fun setUp() {
        repo = HospitalConjuntoRepository(1)
        repoLleno = HospitalConjuntoRepository(2)
        lista.forEach { repoLleno.ingresarPaciente(it) }
    }

    @Test
    fun estaCompleto() {
        assertAll(
            { assertTrue(repoLleno.estaCompleto()) },
            { assertFalse(repo.estaCompleto()) }
        )
    }

    @Test
    fun obtenerTodosPacientes() {
        assertAll(
            { assertEquals(lista, repoLleno.obtenerTodosPacientes()) },
            { assertEquals(listOf(), repo.obtenerTodosPacientes()) }

        )
    }

    @Test
    fun pacientesOrdenFechaIngreso() {
        assertAll(
            { assertEquals(lista, repoLleno.pacientesOrdenFechaIngreso()) },
            { assertEquals(listOf(), repo.pacientesOrdenFechaIngreso()) }

        )
    }

    @Test
    fun pacientesOrdenNombre() {
        assertAll(
            { assertEquals(lista, repoLleno.pacientesOrdenFechaIngreso()) },
            { assertEquals(listOf(), repo.pacientesOrdenFechaIngreso()) }

        )
    }

    @Test
    fun buscarPacienteDni() {
        assertAll(
            { assertEquals(lista.first(), repoLleno.buscarPacienteDni("12345678I")) },
            { assertNull(repo.buscarPacienteDni("12345678I")) },
            { assertNull(repo.buscarPacienteDni("12335678I")) }
        )
    }

    @Test
    fun buscarPacientePorTipo() {
        assertAll(
            { assertEquals(listOf(lista.first()), repoLleno.buscarPacientePorTipo(Paciente.TipoPaciente.URGENCIA)) },
            { assertEquals(listOf(), repo.buscarPacientePorTipo(Paciente.TipoPaciente.NORMAL)) }
        )
    }

    @Test
    fun numPacientesPorTipo() {
        assertAll(
            { assertEquals(1, repoLleno.numPacientesPorTipo(Paciente.TipoPaciente.NORMAL)) },
            { assertEquals(0, repo.numPacientesPorTipo(Paciente.TipoPaciente.NORMAL)) }
        )
    }

    @Test
    fun darDeAlta() {
        assertAll(
            { assertEquals(lista.first(), repoLleno.darDeAlta(lista.first())) },
            { assertNull(repo.darDeAlta(lista.first())) }
        )
    }

    @Test
    fun ingresarPaciente() {
        assertAll(
            { assertEquals(lista.first(), repo.ingresarPaciente(lista.first())) },
            { assertNull(repo.ingresarPaciente(lista.first())) }
        )
    }
}