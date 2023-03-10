package repositories

import enums.tipoPaciente
import models.Paciente
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.assertAll
import java.net.URLEncoder
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class HospitalRepositoryListTest {

    private lateinit var repository: HospitalRepository<Paciente, String>

    val nombres = arrayOf(
        "Lucas",
        "Sonia",
        "Pedro",
        "Jose Miguel",
        "Rebeca",
        "Natalia",
        "Lucía",
        "Fernando",
        "Víctor",
        "Juan",
        "Hernán",
        "Nuria",
        "Pablo",
        "Rosa",
        "Pedro"
    )
    val apellidos = arrayOf(
        "Pérez",
        "Martín",
        "Dominguez",
        "González",
        "Paz",
        "Maestre",
        "Poza",
        "Gulán",
        "Díaz",
        "Solis",
        "Fuente",
        "Gómez",
        "Mirlo",
        "Quintana",
        "Pérez"
    )

    @BeforeEach
    fun inicio() {
        repository = HospitalRepositoryList()
    }

    @Test
    fun ingresar() {
        val paciente = repository.ingresar()
        val DNIRegex = Regex("[0-9]{8}[A-Z]")
        assertAll(
            { assertTrue { nombres.contains(paciente?.nombre) } },
            { assertTrue { apellidos.contains(paciente?.apellido) } },
            { assertTrue { DNIRegex.matches(paciente?.DNI!!.subSequence(0..8)) } },
            { assertTrue { paciente?.fechaIngreso?.first in 1..31 } },
            { assertTrue { paciente?.fechaIngreso?.second in 1..12 } },
            { assertTrue { paciente?.fechaIngreso?.third!! >= 2023 } },
            { assertTrue { paciente?.tipoPaciente == tipoPaciente.NORMAL || paciente?.tipoPaciente == tipoPaciente.URGENCIA } }
        )
    }

    @Test
    fun ingresarSiHospitalLleno() {
        repeat(27) {repository.ingresar()}
        val paciente = repository.ingresar()

        assertEquals(null, paciente)
    }

    @Test
    fun getAll() {
        val pacientes = arrayListOf<Paciente>(
            Paciente("58496751Y", "Laura", "Díaz", "28-11-1995", Triple(11, 2, 2023), tipoPaciente.NORMAL),
            Paciente("84951625P", "Victor", "Solis", "19-03-1986", Triple(11, 2, 2023), tipoPaciente.URGENCIA),
            Paciente("45962587A", "Sonia", "Martín", "14-03-1977", Triple(12, 2, 2023), tipoPaciente.NORMAL)
        ).toList()
        assertEquals(pacientes, repository.getAll())
    }

    @Test
    fun getByDNI() {
        val dni = "58496751Y"
        val paciente = repository.getByDNI(dni)

        assertEquals(Paciente("58496751Y", "Laura", "Díaz", "28-11-1995", Triple(11, 2, 2023), tipoPaciente.NORMAL), paciente)
    }

    @Test
    fun getByDNINotFound() {
        val dni = "59861539J"
        val paciente = repository.getByDNI(dni)

        assertNull(paciente)
    }

    @Test
    fun darAlta() {
        val paciente = repository.darAlta(Paciente("58496751Y", "Laura", "Díaz", "28-11-1995", Triple(11, 2, 2023), tipoPaciente.NORMAL))

        assertEquals(Paciente("58496751Y", "Laura", "Díaz", "28-11-1995", Triple(11, 2, 2023), tipoPaciente.NORMAL), paciente)
    }

    @Test
    fun darAltaSiNoExistePaciente() {
        val paciente = repository.darAlta(Paciente("58496751Y", "Pikachu", "Díaz", "28-11-1995", Triple(11, 2, 2023), tipoPaciente.NORMAL))

        assertNull(paciente)
    }

    @Test
    fun numPacientes() {
        val num = repository.numPacientes()

        assertEquals(3, num)
    }

    @Test
    fun isHospitalCompleto() {
        val isCompleto = repository.isHospitalCompleto()

        assertFalse(isCompleto)
    }

    @Test
    fun orderByFechaIngreso() {
        val pacientes = arrayListOf<Paciente>(
            Paciente("58496751Y", "Laura", "Díaz", "28-11-1995", Triple(11, 2, 2023), tipoPaciente.NORMAL),
            Paciente("84951625P", "Victor", "Solis", "19-03-1986", Triple(11, 2, 2023), tipoPaciente.URGENCIA),
            Paciente("45962587A", "Sonia", "Martín", "14-03-1977", Triple(12, 2, 2023), tipoPaciente.NORMAL)
        ).toList()
        val ordenada = repository.orderByFechaIngreso()

        assertEquals(pacientes, ordenada)
    }

    @Test
    fun orderByNombre() {
        val pacientes = arrayListOf<Paciente>(
            Paciente("58496751Y", "Laura", "Díaz", "28-11-1995", Triple(11, 2, 2023), tipoPaciente.NORMAL),
            Paciente("45962587A", "Sonia", "Martín", "14-03-1977", Triple(12, 2, 2023), tipoPaciente.NORMAL),
            Paciente("84951625P", "Victor", "Solis", "19-03-1986", Triple(11, 2, 2023), tipoPaciente.URGENCIA)
        ).toList()
        val ordenada = repository.orderByNombre()

        assertEquals(pacientes, ordenada)
    }

    @Test
    fun pacientesPorTipo() {
        val misPacientesNormales = arrayListOf<Paciente>(
            Paciente("58496751Y", "Laura", "Díaz", "28-11-1995", Triple(11, 2, 2023), tipoPaciente.NORMAL),
            Paciente("45962587A", "Sonia", "Martín", "14-03-1977", Triple(12, 2, 2023), tipoPaciente.NORMAL)
        ).toList()
        val misPacientesUrgencias = arrayListOf<Paciente>(Paciente("84951625P", "Victor", "Solis", "19-03-1986", Triple(11, 2, 2023), tipoPaciente.URGENCIA))
        val pacientesNormales = repository.pacientesPorTipo(tipoPaciente.NORMAL)
        val pacientesUrgencias = repository.pacientesPorTipo(tipoPaciente.URGENCIA)

        assertAll(
            { assertEquals(misPacientesNormales, pacientesNormales) },
            { assertEquals(misPacientesUrgencias, pacientesUrgencias) }
        )
    }

    @Test
    fun numPacientesTipo() {
        val pacientesNormales = repository.numPacientesTipo(tipoPaciente.NORMAL)
        val pacientesUrgencias = repository.numPacientesTipo(tipoPaciente.URGENCIA)

        assertAll(
            { assertEquals(2, pacientesNormales) },
            { assertEquals(1, pacientesUrgencias) }
        )
    }
}