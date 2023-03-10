package controller

import enums.tipoPaciente
import factories.PacientesFactory.apellidos
import factories.PacientesFactory.nombres
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import models.Paciente
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import repositories.HospitalRepository
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockKExtension::class)
class HospitalControllerTest {
    @MockK
    private lateinit var repository: HospitalRepository<Paciente, String>
    @InjectMockKs
    private lateinit var controller: HospitalController

    private var pacientes = arrayListOf<Paciente>(
        Paciente("58496751Y", "Laura", "Díaz", "28-11-1995", Triple(11, 2, 2023), tipoPaciente.NORMAL),
        Paciente("84951625P", "Victor", "Solis", "19-03-1986", Triple(11, 2, 2023), tipoPaciente.URGENCIA),
        Paciente("45962587A", "Sonia", "Martín", "14-03-1977", Triple(12, 2, 2023), tipoPaciente.NORMAL)
    )
    private var ordenNombre = arrayListOf<Paciente>(
        Paciente("58496751Y", "Laura", "Díaz", "28-11-1995", Triple(11, 2, 2023), tipoPaciente.NORMAL),
        Paciente("45962587A", "Sonia", "Martín", "14-03-1977", Triple(12, 2, 2023), tipoPaciente.NORMAL),
        Paciente("84951625P", "Victor", "Solis", "19-03-1986", Triple(11, 2, 2023), tipoPaciente.URGENCIA),
    )
    private val pacienteRandom = Paciente("59847851L", "Sonia", "Díaz", "15-12-1991", Triple(14, 2, 2023), tipoPaciente.NORMAL)
    private val misPacientesNormales = arrayListOf<Paciente>(
        Paciente("58496751Y", "Laura", "Díaz", "28-11-1995", Triple(11, 2, 2023), tipoPaciente.NORMAL),
        Paciente("45962587A", "Sonia", "Martín", "14-03-1977", Triple(12, 2, 2023), tipoPaciente.NORMAL)
    )
    private val misPacientesUrgencias = arrayListOf<Paciente>(Paciente("84951625P", "Victor", "Solis", "19-03-1986", Triple(11, 2, 2023), tipoPaciente.URGENCIA))

    @Test
    fun ingresar() {
        every { repository.ingresar() } returns pacienteRandom

        val paciente = controller.ingresar()
        assertAll(
            { assertEquals(pacienteRandom.DNI.length, paciente.DNI.length) },
            { assertTrue { paciente.nombre in nombres } },
            { assertTrue { paciente.apellido in apellidos } },
            { assertEquals(pacienteRandom.fechaNacimiento.length, paciente.fechaNacimiento.length) }
        )

        verify { repository.ingresar() }
    }

    @Test
    fun ingresarFallido() {
        every { repository.ingresar() } returns null

        val exc = assertThrows<Exception> {
            controller.ingresar()
        }

        assertEquals("El aforo del hospital está completo", exc.message)
    }

    @Test
    fun getAll() {
        every { repository.getAll() } returns pacientes

        val lista = controller.getAll()
        assertAll(
            { assertEquals(pacientes, lista) },
            { assertEquals(pacientes.size, lista.size) }
        )

        verify { repository.getAll() }
    }

    @Test
    fun getByDNI() {
        every { repository.getByDNI("84951625P") } returns pacientes[1]

        val paciente = controller.getByDNI("84951625P")

        assertEquals(pacientes[1], paciente)

        verify { repository.getByDNI("84951625P") }
    }

    @Test
    fun getByDNINotFound() {
        every { repository.getByDNI("54978921L") } returns null

        val exc = assertThrows<Exception> {
            controller.getByDNI("54978921L")
        }

        assertEquals("No se ha encontrado el paciente con DNI \"54978921L\"", exc.message)

        verify { repository.getByDNI("54978921L") }
    }

    @Test
    fun darAlta() {
        every { repository.darAlta(pacientes[0]) } returns pacientes[0]

        val paciente = controller.darAlta(pacientes[0])

        assertAll(
            { assertEquals(pacientes[0].nombre, paciente.nombre) },
            { assertEquals(pacientes[0].apellido, paciente.apellido) },
            { assertEquals(pacientes[0].DNI, paciente.DNI) },
            { assertEquals(pacientes[0].fechaNacimiento, paciente.fechaNacimiento) },
            { assertEquals(pacientes[0].fechaIngreso, paciente.fechaIngreso) },
            { assertEquals(pacientes[0].tipoPaciente, paciente.tipoPaciente) }
        )

        verify { repository.darAlta(pacientes[0]) }
    }

    @Test
    fun darAltaFallido() {
        every { repository.darAlta(pacientes[0]) } returns null

        val exc = assertThrows<Exception> {
            controller.darAlta(pacientes[0])
        }

        assertEquals("No se encuentra el paciente", exc.message)

        verify { repository.darAlta(pacientes[0]) }
    }

    @Test
    fun numPacientes() {
        every { repository.numPacientes() } returns 3

        val num = controller.numPacientes()

        assertEquals(3, num)

        verify { repository.numPacientes() }
    }

    @Test
    fun isHospitalCompleto() {
        every { repository.isHospitalCompleto() } returns false

        val completo = controller.isHospitalCompleto()

        assertFalse(completo)

        verify { repository.isHospitalCompleto() }
    }

    @Test
    fun orderByFechaIngreso() {
        every { repository.orderByFechaIngreso() } returns pacientes

        val ordenado = controller.orderByFechaIngreso()

        assertEquals(pacientes, ordenado)

        verify { repository.orderByFechaIngreso() }
    }

    @Test
    fun orderByNombre() {
        every { repository.orderByNombre() } returns ordenNombre

        val ordenado = controller.orderByNombre()

        assertEquals(ordenNombre, ordenado)

        verify { repository.orderByNombre() }
    }

    @Test
    fun pacientesPorTipo() {
        every { repository.pacientesPorTipo(tipoPaciente.NORMAL) } returns misPacientesNormales
        every { repository.pacientesPorTipo(tipoPaciente.URGENCIA) } returns misPacientesUrgencias

        val pacientesNormales = controller.pacientesPorTipo(tipoPaciente.NORMAL)
        val pacientesUrgencias = controller.pacientesPorTipo(tipoPaciente.URGENCIA)

        assertAll(
            { assertEquals(misPacientesNormales, pacientesNormales) },
            { assertEquals(misPacientesUrgencias, pacientesUrgencias) }
        )

        verify { repository.pacientesPorTipo(tipoPaciente.NORMAL) }
        verify { repository.pacientesPorTipo(tipoPaciente.URGENCIA) }
    }

    @Test
    fun numPacientesTipo() {
        every { repository.numPacientesTipo(tipoPaciente.NORMAL) } returns 2
        every { repository.numPacientesTipo(tipoPaciente.URGENCIA) } returns 1

        assertAll(
            { assertEquals(2, controller.numPacientesTipo(tipoPaciente.NORMAL)) },
            { assertEquals(1, controller.numPacientesTipo(tipoPaciente.URGENCIA)) }
        )

        verify { repository.numPacientesTipo(tipoPaciente.NORMAL) }
        verify { repository.numPacientesTipo(tipoPaciente.URGENCIA) }
    }
}