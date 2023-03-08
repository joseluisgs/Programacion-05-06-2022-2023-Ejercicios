package controllers

import enums.TipoPaciente
import exceptions.DNINotFoundException
import exceptions.NotValidDNIException
import exceptions.PacienteBadRequestException
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import models.Paciente

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.assertThrows
import repositories.HospitalRepository

import java.time.LocalDate
import kotlin.test.assertEquals
import kotlin.test.assertTrue


internal class HospitalControllerTest {

    @MockK
    private lateinit var repo: HospitalRepository<Paciente, String>

    @InjectMockKs
    private lateinit var controller: HospitalController

    //añado unos pacientes por defecto para poder empezar a trabajar con ellos
    private val paciente1 = Paciente(
        DNI = "12345678A",
        nombre = "Juan Alberto",
        fechaIngreso = LocalDate.of(2004, 10, 14),
        fechaAlta = null,
        fechaNacimiento = LocalDate.of(1953, 12, 31),
        tipo = TipoPaciente.NORMAL
    )
    private val paciente2 = Paciente(
        DNI = "12345678B",
        nombre = "Juan Antonio",
        fechaIngreso = LocalDate.of(2003, 3, 16),
        fechaAlta = null,
        fechaNacimiento = LocalDate.of(1954, 2, 15),
        tipo = TipoPaciente.NORMAL
    )
    private val paciente3 = Paciente(
        DNI = "12345678C",
        nombre = "Juan José",
        fechaIngreso = LocalDate.of(2005, 12, 28),
        fechaAlta = null,
        fechaNacimiento = LocalDate.of(1963, 8, 26),
        tipo = TipoPaciente.NORMAL
    )
    private val paciente4 = Paciente(
        DNI = "12345678D",
        nombre = "Juan Pablo",
        fechaIngreso = LocalDate.of(2007, 9, 8),
        fechaAlta = null,
        fechaNacimiento = LocalDate.of(1975, 1, 5),
        tipo = TipoPaciente.URGENCIA
    )

    val pacientesList = listOf(paciente1, paciente2, paciente3, paciente4)

    init {
        MockKAnnotations.init(this)
    }

    @Test
    fun ingresar() {
        every { repo.ingresar(pacientesList[0]) } returns pacientesList[0]
        assertEquals(pacientesList[0], controller.ingresar(pacientesList[0]))
    }

    @Test
    fun darAlta() {
        every { repo.darAlta(pacientesList[0].DNI) } returns pacientesList[0]
        assertEquals(pacientesList[0], controller.darAlta(pacientesList[0].DNI))
    }

    @Test
    fun isHospitalLleno() {
        every { repo.isHospitalLleno() } returns false
        assertEquals(false, controller.isHospitalLleno())
    }

    @Test
    fun numPacientes() {
        every { repo.numPacientes() } returns 4
        assertEquals(4, controller.numPacientes())
    }

    @Test
    fun todosLosPacientes() {
        every { repo.todosLosPacientes() } returns pacientesList
        assertEquals(pacientesList, controller.todosLosPacientes())
    }

    @Test
    fun orderPacientesByFechaDeIngreso() {
        every { repo.orderPacientesByFechaDeIngreso() } returns pacientesList.sortedWith() { p1, p2 ->
            p1?.fechaIngreso!!.compareTo(
                p2!!.fechaIngreso
            )
        }.toList()
        assertEquals(
            pacientesList.sortedWith() { p1, p2 -> p1?.fechaIngreso!!.compareTo(p2!!.fechaIngreso) }.toList(),
            controller.orderPacientesByFechaDeIngreso()
        )
    }

    @Test
    fun buscarPacientesPorDNI() {
        every { repo.buscarPacientePorDNI(pacientesList[1].DNI) } returns pacientesList[1]
        assertEquals(pacientesList[1], controller.buscarPacientesPorDNI(pacientesList[1].DNI))

    }

    @Test
    fun pacientesPorTipo() {
        every { repo.pacientesPorTipo(TipoPaciente.NORMAL) } returns listOf(paciente4)
        assertEquals(listOf(paciente4), controller.pacientesPorTipo(TipoPaciente.NORMAL))
        every { repo.pacientesPorTipo(TipoPaciente.URGENCIA) } returns listOf(paciente1, paciente2, paciente3)
        assertEquals(listOf(paciente1, paciente2, paciente3), controller.pacientesPorTipo(TipoPaciente.URGENCIA))
    }

    @Test
    fun numPacientesPorTipo() {
        every { repo.numPacientesPorTipo(TipoPaciente.NORMAL) } returns 3
        assertEquals(3, controller.numPacientesPorTipo(TipoPaciente.NORMAL))
        every { repo.numPacientesPorTipo(TipoPaciente.URGENCIA) } returns 1
        assertEquals(1, controller.numPacientesPorTipo(TipoPaciente.URGENCIA))
    }

    @Test
    fun isPatientValidTest() {
        val message = assertThrows<PacienteBadRequestException> {
            controller.isPatientValid(
                Paciente(
                    DNI = "12345678A",
                    nombre = "",
                    fechaIngreso = LocalDate.of(2004, 10, 14),
                    fechaAlta = null,
                    fechaNacimiento = LocalDate.of(1953, 12, 31),
                    tipo = TipoPaciente.NORMAL
                )
            )
        }
        assertEquals("El nombre del paciente no puede estar vacío", message.message)

    }

    @Test
    fun isDniValid() {
        val message1 = assertThrows<DNINotFoundException> {
            controller.isDniValid("")
        }
        val message2 = assertThrows<NotValidDNIException> {
            controller.isDniValid("1234567S")
        }
        val message3 = assertThrows<NotValidDNIException> {
            controller.isDniValid("1234567S")
        }
        val message4 = assertThrows<NotValidDNIException> {
            controller.isDniValid("S")
        }
        val message5 = assertThrows<NotValidDNIException> {
            controller.isDniValid("123546787A")
        }
        val message6 = assertThrows<NotValidDNIException> {
            controller.isDniValid("12345678a")
        }
        val message7 = assertThrows<NotValidDNIException> {
            controller.isDniValid("&%$·")
        }



        assertAll(
            { assertEquals("No se ha encontrado el DNI del paciente nuevo", message1.message) },
            { assertEquals("El dni introducido no cumple con los requisitos necesarios", message2.message) },
            { assertEquals("El dni introducido no cumple con los requisitos necesarios", message3.message) },
            { assertEquals("El dni introducido no cumple con los requisitos necesarios", message4.message) },
            { assertEquals("El dni introducido no cumple con los requisitos necesarios", message5.message) },
            { assertEquals("El dni introducido no cumple con los requisitos necesarios", message6.message) },
            { assertEquals("El dni introducido no cumple con los requisitos necesarios", message7.message) },
            { assertTrue(controller.isDniValid("12345678A")) }
        )

    }
}