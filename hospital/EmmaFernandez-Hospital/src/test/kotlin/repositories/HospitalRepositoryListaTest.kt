package repositories

import models.Paciente
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import java.time.LocalDate

class HospitalRepositoryListaTest {
    private lateinit var hospital: HospitalRepositoryLista
    private lateinit var hospitalVacio: HospitalRepositoryLista
    private val pacientes = listOf(
        Paciente("11111111A", "Ronaldo", LocalDate.of(2000, 1, 1)),
        Paciente("22222222B", "María", LocalDate.of(2000, 1, 1), tipoPaciente = Paciente.TipoPaciente.URGENCIA),
        Paciente("33333333C", "Felipe", LocalDate.of(2000, 1, 1), tipoPaciente = Paciente.TipoPaciente.URGENCIA)
    )

    @BeforeEach
    fun setUp() {
        hospital = HospitalRepositoryLista(3)
        hospitalVacio = HospitalRepositoryLista(3)
        pacientes.forEach { hospital.ingresar(it) }
    }

    @Test
    fun ingresar() {
        assertAll(
            { assertEquals(pacientes[0], hospitalVacio.ingresar(pacientes[0])) },
            { assertNull(hospitalVacio.ingresar(pacientes[0])) },
            { assertNull(hospital.ingresar(pacientes[0])) }
        )
    }

    @Test
    fun darAlta() {
        assertAll(
            { assertEquals(pacientes[0], hospital.darAlta(pacientes[0].dni)) },
            { assertNull(hospital.darAlta(pacientes[0].dni)) }
        )
    }

    @Test
    fun estaCompleto() {
        assertAll(
            { assertFalse(hospitalVacio.estaCompleto()) },
            { assertTrue(hospital.estaCompleto()) }
        )
    }

    @Test
    fun numPacientes() {
        assertAll(
            { assertEquals(3, hospital.numPacientes()) },
            { assertEquals(0, hospitalVacio.numPacientes()) }
        )
    }

    @Test
    fun obtenerTodosPacientes() {
        assertAll(
            { assertEquals(pacientes, hospital.obtenerTodosPacientes()) },
            { assertEquals(emptyList<Paciente>(), hospitalVacio.obtenerTodosPacientes()) }
        )
    }

    @Test
    fun pacientePorDni() {
        assertAll(
            { assertEquals(pacientes[0], hospital.pacientePorDni(pacientes[0].dni)) },
            { assertNull(hospitalVacio.pacientePorDni(pacientes[0].dni)) }
        )
    }

    @Test
    fun pacientesOrdenadosFechaIngreso() {
        assertAll(
            { assertEquals(pacientes, hospital.pacientesOrdenadosFechaIngreso()) },
            { assertEquals(emptyList<Paciente>(), hospitalVacio.pacientesOrdenadosFechaIngreso()) }
        )
    }

    @Test
    fun pacientesOrdenadosNombre() {
        assertAll(
            { assertEquals(listOf(pacientes[2], pacientes[1], pacientes[0]), hospital.pacientesOrdenadosNombre()) },
            { assertEquals(emptyList<Paciente>(), hospitalVacio.pacientesOrdenadosNombre()) }
        )
    }

    @Test
    fun pacientesPorTipo() {
        assertAll(
            { assertEquals(listOf(pacientes[0]), hospital.pacientesPorTipo(Paciente.TipoPaciente.NORMAL)) },
            { assertEquals(listOf(pacientes[1], pacientes[2]), hospital.pacientesPorTipo(Paciente.TipoPaciente.URGENCIA)) },
            { assertEquals(emptyList<Paciente>(), hospitalVacio.pacientesPorTipo(Paciente.TipoPaciente.NORMAL)) },
            { assertEquals(emptyList<Paciente>(), hospitalVacio.pacientesPorTipo(Paciente.TipoPaciente.URGENCIA)) }
        )
    }

    @Test
    fun numPacientesPorTipo() {
        assertAll(
            { assertEquals(1, hospital.numPacientesPorTipo(Paciente.TipoPaciente.NORMAL)) },
            { assertEquals(2, hospital.numPacientesPorTipo(Paciente.TipoPaciente.URGENCIA)) },
            { assertEquals(0, hospitalVacio.numPacientesPorTipo(Paciente.TipoPaciente.NORMAL)) },
            { assertEquals(0, hospitalVacio.numPacientesPorTipo(Paciente.TipoPaciente.URGENCIA)) }
        )
    }
}