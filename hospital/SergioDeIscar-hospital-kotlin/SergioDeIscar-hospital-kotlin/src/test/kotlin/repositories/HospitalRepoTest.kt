package repositories

import enums.TipoPaciente
import interfaces.HospitalExtension
import models.Paciente
import org.junit.jupiter.api.*
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals
import kotlin.test.assertNull

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
abstract class HospitalRepoTest {
    private val pacientes = arrayOf(
        Paciente("30609298R", "Ztest", tipo = TipoPaciente.NORMAL),
        Paciente("42298425E", "Atest", tipo = TipoPaciente.NORMAL),
        Paciente("00972994W", "Etest", tipo = TipoPaciente.NORMAL),

        Paciente("07932195R", "Ttest", tipo = TipoPaciente.URGENCIA),
        Paciente("AAAAAAAAA", "Itest", tipo = TipoPaciente.URGENCIA), // DNI ERROR
    )

    private lateinit var repoEmpty: HospitalExtension
    private lateinit var repoShort: HospitalExtension

    @BeforeEach
    fun setUp(){
        repoEmpty = createRepoEmpty()
        repoShort = createRepoShort()
    }

    abstract fun createRepoEmpty(): HospitalExtension
    abstract fun createRepoShort(): HospitalExtension

    // Sé que no es recomendable que los test tengan orden,
    // pero como uso las propias funciones en otro test tengo que asegurarme de que funciona

    @Order(1)
    @Test
    fun saveTest(){
        assertAll(
            { assertEquals(pacientes[0], repoEmpty.save(pacientes[0])) },

            { assertEquals(pacientes[0], repoShort.save(pacientes[0])) },
            { assertEquals(pacientes[1], repoShort.save(pacientes[1])) },
            { assertEquals(pacientes[2], repoShort.save(pacientes[2])) },

            { assertNull(repoShort.save(pacientes[3])) } // Está lleno
        )
    }

    @Order(2)
    @Test
    fun saveAllTest(){
        repoEmpty.saveAll(pacientes.toList())
        repoShort.saveAll(pacientes.toList())
        assertAll(
            { assertContentEquals(pacientes, repoEmpty.getAll().toTypedArray()) },
            { assertContentEquals(pacientes.copyOfRange(0,3), repoShort.getAll().toTypedArray()) }
        )
    }

    @Order(2)
    @Test
    fun findTest(){
        repoEmpty.save(pacientes[0])
        repoEmpty.save(pacientes[1])

        assertAll(
            { assertEquals(pacientes[0], repoEmpty.find(pacientes[0].dni)) },
            { assertEquals(pacientes[1], repoEmpty.find(pacientes[1].dni)) },
            { assertNull(repoEmpty.find(pacientes[2].dni)) }
        )
    }

    @Order(2)
    @Test
    fun deleteTest(){
        repoEmpty.save(pacientes[0])
        repoEmpty.save(pacientes[1])

        assertAll(
            { assertEquals(pacientes[0], repoEmpty.delete(pacientes[0].dni)) },
            { assertEquals(1, repoEmpty.numPacientes()) },
            { assertEquals(pacientes[1], repoEmpty.delete(pacientes[1].dni)) },
            { assertEquals(0, repoEmpty.numPacientes()) },
            { assertNull(repoEmpty.delete(pacientes[2].dni)) }
        )
    }

    @Order(3)
    @Test
    fun numPacientesPorTipoTest(){
        repoEmpty.saveAll(pacientes.toList())
        assertAll(
            { assertEquals(3, repoEmpty.numPacientePorTipo(TipoPaciente.NORMAL)) },
            { assertEquals(2, repoEmpty.numPacientePorTipo(TipoPaciente.URGENCIA)) }
        )
    }

    @Order(3)
    @Test
    fun pacientesPorTipoTest(){
        repoEmpty.saveAll(pacientes.toList())
        assertAll(
            { assertContentEquals(pacientes.copyOfRange(0,3), repoEmpty.pacientesPorTipo(TipoPaciente.NORMAL).toTypedArray()) },
            { assertContentEquals(pacientes.copyOfRange(3,5), repoEmpty.pacientesPorTipo(TipoPaciente.URGENCIA).toTypedArray()) }
        )
    }

    @Order(3)
    @Test
    fun pacientesOrdeFechaIngresoTest(){
        repoEmpty.saveAll(pacientes.toList())
        assertAll(
            { assertContentEquals(pacientes, repoEmpty.pacientesOrdeFechaIngreso().toTypedArray()) }
        )
    }

    @Order(3)
    @Test
    fun pacientesOrdeNombreTest(){
        val shortArray = arrayOf(
            pacientes[1],
            pacientes[2],
            pacientes[4],
            pacientes[3],
            pacientes[0],

        )
        repoEmpty.saveAll(pacientes.toList())
        assertAll(
            { assertContentEquals(shortArray, repoEmpty.pacientesOrdeNombre().toTypedArray()) }
        )
    }

    @Order(3)
    @Test
    fun pacienteOrdeDNITest(){
        val shortArray = arrayOf(
            pacientes[2],
            pacientes[3],
            pacientes[0],
            pacientes[1],
            pacientes[4],
        )
        repoEmpty.saveAll(pacientes.toList())
        assertAll(
            { assertContentEquals(shortArray, repoEmpty.pacientesOrdeDNI().toTypedArray()) }
        )
    }
}