package controlers

import exceptions.PacienteError
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import models.Paciente
import models.TipoPaciente
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import repositories.IHospitalRepository

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockKExtension::class)
internal class MedicosControlerTest {
    @MockK
    private lateinit var repositorio: IHospitalRepository

    @InjectMockKs
    private lateinit var controller: MedicosControler

    init {
        MockKAnnotations.init(this)
    }

    var listaTest = mutableListOf(
        Paciente(
            dni = "53454692F",
            nombre = "Oscar Gracia ",
            fechaNacimiento = "1986-06-22",
            fechaIngreso = "2022-02-15",
            tipoPaciente = TipoPaciente.A
        ),
        Paciente(
            dni = "43443292D",
            nombre = "Jeromin Perez",
            fechaNacimiento = "1956-03-12",
            fechaIngreso = "2023-02-15",
            tipoPaciente = TipoPaciente.B
        ),
        Paciente(
            dni = "523487486R",
            nombre = "JoseFina",
            fechaNacimiento = "1986-01-18",
            fechaIngreso = "2023-02-14",
            tipoPaciente = TipoPaciente.A
        )
    )

    var pacienteParaIngreso: Paciente = Paciente(
        dni = "12345678F", nombre = "Anabel", fechaNacimiento = "1990-09-24",
        fechaIngreso = "2022-03-07", tipoPaciente = TipoPaciente.D
    )
    var pacienteParaIngresoSinNombre: Paciente = Paciente(
        dni = "12345678F", nombre = "", fechaNacimiento = "1990-09-24",
        fechaIngreso = "2022-03-07", tipoPaciente = TipoPaciente.D
    )

    @Test
    fun alta() {
        var pacienteTest = Paciente(
            dni = "53454692F",
            nombre = "Oscar Gracia ",
            fechaNacimiento = "1986-06-22",
            fechaIngreso = "2022-02-15",
            tipoPaciente = TipoPaciente.A
        )

        every { repositorio.alta(pacienteTest, "2020-02-02") } returns pacienteTest
        var salida = controller.alta(pacienteTest, "2020-02-02")

        assertEquals(salida, pacienteTest)

        verify { repositorio.alta(pacienteTest, "2020-02-02") }
    }

    @Test
    fun estaCompleto() {
        every { repositorio.estaCompleto() } returns false
        assertFalse(controller.estaCompleto())
        verify { repositorio.estaCompleto() }
    }

    @Test
    fun numeroPacientes() {
        every { repositorio.numeroPacientes() } returns 3
        assertEquals(controller.numeroPacientes(), 3)
        verify { repositorio.numeroPacientes() }
    }

    @Test
    fun obtenertodosPacientes() {
        every { repositorio.obtenertodosPacientes() } returns listaTest
        assertEquals(controller.obtenertodosPacientes().toString(), listaTest.toString())
        verify { repositorio.obtenertodosPacientes() }
    }

    @Test
    fun pacientePorDni() {
        every { repositorio.pacientePorDni("43443292D") } returns listaTest[1]
        assertEquals(controller.pacientePorDni("43443292D"), listaTest[1])
        verify { repositorio.pacientePorDni("43443292D") }
    }

    @Test
    fun pacientesOrdenadosFechaIngreso() {
        var listaOrdendaTest = mutableListOf(
            Paciente(
                dni = "53454692F",
                nombre = "Oscar Gracia ",
                fechaNacimiento = "1986-06-22",
                fechaIngreso = "2022-02-15",
                tipoPaciente = TipoPaciente.A
            ), Paciente(
                dni = "523487486R",
                nombre = "JoseFina",
                fechaNacimiento = "1986-01-18",
                fechaIngreso = "2023-02-14",
                tipoPaciente = TipoPaciente.A
            ),
            Paciente(
                dni = "43443292D",
                nombre = "Jeromin Perez",
                fechaNacimiento = "1956-03-12",
                fechaIngreso = "2023-02-15",
                tipoPaciente = TipoPaciente.B
            )
        ).toString()
        every { repositorio.pacientesOrdenadosFechaIngreso() } returns listaOrdendaTest
        assertEquals(controller.pacientesOrdenadosFechaIngreso(), listaOrdendaTest)
        verify { repositorio.pacientesOrdenadosFechaIngreso() }
    }

    @Test
    fun pacientesOrdenadosPorNombre() {
        var listaOrdendaTest = mutableListOf(
            Paciente(
                dni = "53454692F",
                nombre = "Oscar Gracia ",
                fechaNacimiento = "1986-06-22",
                fechaIngreso = "2022-02-15",
                tipoPaciente = TipoPaciente.A
            ), Paciente(
                dni = "523487486R",
                nombre = "JoseFina",
                fechaNacimiento = "1986-01-18",
                fechaIngreso = "2023-02-14",
                tipoPaciente = TipoPaciente.A
            ),
            Paciente(
                dni = "43443292D",
                nombre = "Jeromin Perez",
                fechaNacimiento = "1956-03-12",
                fechaIngreso = "2023-02-15",
                tipoPaciente = TipoPaciente.B
            )
        )
        every { repositorio.pacientesOrdenadosPorNombre() } returns listaOrdendaTest
        assertEquals(controller.pacientesOrdenadosPorNombre(), listaOrdendaTest)
        verify { repositorio.pacientesOrdenadosPorNombre() }
    }

    @Test
    fun pacientesPorTipo() {
        var listaFiltradaTest = mutableListOf(
            Paciente(
                dni = "53454692F",
                nombre = "Oscar Gracia ",
                fechaNacimiento = "1986-06-22",
                fechaIngreso = "2022-02-15",
                tipoPaciente = TipoPaciente.A
            ), Paciente(
                dni = "523487486R",
                nombre = "JoseFina",
                fechaNacimiento = "1986-01-18",
                fechaIngreso = "2023-02-14",
                tipoPaciente = TipoPaciente.A
            )
        )
        every { repositorio.pacientesPorTipo(TipoPaciente.A) } returns listaFiltradaTest
        assertEquals(controller.pacientesPorTipo(TipoPaciente.A), listaFiltradaTest)
        verify { repositorio.pacientesPorTipo(TipoPaciente.A) }
    }

    @Test
    fun numPacientesPorTipo() {
        every { repositorio.numPacientesPorTipo(TipoPaciente.A) } returns 2
        assertEquals(controller.numPacientesPorTipo(TipoPaciente.A), 2)
        verify { repositorio.numPacientesPorTipo(TipoPaciente.A) }
    }


}