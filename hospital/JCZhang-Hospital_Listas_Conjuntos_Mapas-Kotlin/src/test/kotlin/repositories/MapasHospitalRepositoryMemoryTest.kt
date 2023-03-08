package repositories

import enums.TipoPaciente
import models.Paciente
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import java.time.LocalDate

internal class MapasHospitalRepositoryMemoryTest {

    val repo = MapasHospitalRepositoryMemory()
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

    val pacientesMapa = mutableMapOf<String, Paciente>()


    @BeforeEach
    fun setUp() {
        pacientesMapa[paciente1.DNI] = paciente1
        pacientesMapa[paciente2.DNI] = paciente2
        pacientesMapa[paciente3.DNI] = paciente3
        pacientesMapa[paciente4.DNI] = paciente4
        darAltaAll()
        ingresarAll()
    }

    private fun ingresarAll() {
        for(i in pacientesMapa.values){
            repo.ingresar(i)
        }
    }

    private fun darAltaAll() {
        for (i in pacientesMapa.values)
            repo.darAlta(i.DNI)
    }

    @Test
    fun ingresar() {
        assertEquals(paciente1, repo.ingresar(paciente1))
    }

    @Test
    fun darAlta() {
        assertEquals(paciente1, repo.darAlta("12345678A"))
    }

    @Test
    fun isHospitalLleno() {
        assertEquals(false, repo.isHospitalLleno())
    }

    @Test
    fun numPacientes() {
        assertEquals(4, repo.numPacientes())
    }

    @Test
    fun todosLosPacientes() {
        assertEquals(listOf(paciente1, paciente2, paciente3, paciente4), repo.todosLosPacientes())
    }

    @Test
    fun orderPacientesByFechaDeIngreso() {
        assertEquals(4, repo.orderPacientesByFechaDeIngreso().size)
        // en este caso no cambia el orden, ya que la fecha de ingreso de todos es la misma al inicializar la lista al pricipio de los test
        assertEquals(listOf(paciente1, paciente2, paciente3, paciente4), repo.orderPacientesByFechaDeIngreso())
    }

    @Test
    fun buscarPacientePorDNI() {
        assertEquals(paciente2, repo.buscarPacientePorDNI("12345678B"))
    }
    @Test
    fun pacientesPorTipo() {
        assertEquals(listOf(paciente1, paciente2, paciente3), repo.pacientesPorTipo(TipoPaciente.NORMAL))
        assertEquals(listOf(paciente4), repo.pacientesPorTipo(TipoPaciente.URGENCIA))
    }

    @Test
    fun numPacientesPorTipo() {
        assertEquals(3, repo.numPacientesPorTipo(TipoPaciente.NORMAL))
        assertEquals(1, repo.numPacientesPorTipo(TipoPaciente.URGENCIA))
    }
}