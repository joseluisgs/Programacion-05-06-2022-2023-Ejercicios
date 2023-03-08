package repositories

import models.Paciente
import models.TipoPaciente
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class RepositoryListTest {
    @BeforeEach
    fun inicio() {
        repositorio = RepositoryList()
    }
    var repositorio = RepositoryList()
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

    @Test
    fun ingresar() {
        repositorio.ingresar(pacienteParaIngreso)
        var salida = repositorio.pacientePorDni("12345678F")
        assertEquals(salida, pacienteParaIngreso)
    }

        @Test
        fun alta() {
            var pacientesalida = repositorio.pacientePorDni("53454692F")
            if (pacientesalida != null) {
                repositorio.alta(pacientesalida,"2020-02-02")
            }
            pacientesalida = repositorio.pacientePorDni("53454692F")
            assertEquals(pacientesalida!!.fechaAlta, "2020-02-02")
        }

        @Test
        fun estaCompleto() {
            assertEquals(repositorio.estaCompleto(), false)
        }

        @Test
        fun numeroPacientes() {
            assertEquals(repositorio.numeroPacientes(), 3)
        }

        @Test
        fun obtenertodosPacientes() {
            var listaSalida = repositorio.obtenertodosPacientes().toString()

            assertEquals(listaSalida, listaTest.toString())
        }

        @Test
        fun pacientesOrdenadosFechaIngreso() {
          repositorio.pacientesOrdenadosFechaIngreso()
            var salida = repositorio.obtenertodosPacientes()
            var listaOrdendaTest =mutableListOf(
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
                assertEquals(salida.toString(), listaTest.toString())
        }

        @Test
        fun pacientesOrdenadosPorNombre() {
            repositorio.pacientesOrdenadosPorNombre()
            var salida = repositorio.obtenertodosPacientes()
            var listaOrdendaTest =mutableListOf(
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
            assertEquals(salida.toString(), listaTest.toString())
        }

        @Test
        fun pacientesPorTipo() {
            var salida = repositorio.pacientesPorTipo(TipoPaciente.A)
            var listaFiltradaTest =mutableListOf(
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
            assertEquals(salida.toString(), listaFiltradaTest.toString())
        }

        @Test
        fun numPacientesPorTipo() {
            var salida = repositorio.numPacientesPorTipo(TipoPaciente.A)
            assertEquals(salida, 2)

        }

        @Test
        fun borrarpaciente() {
           var pacienteParaBorrar= repositorio.pacientePorDni("523487486R")
            if (pacienteParaBorrar != null) {
                repositorio.borrarpaciente(pacienteParaBorrar)
            }
            var salida = repositorio.obtenertodosPacientes().toString()
            var listaDespuesBorradoTest =mutableListOf(
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
                )
            ).toString()
            assertEquals(salida, listaDespuesBorradoTest)
        }
    }
