package controler

import exceptions.OpcionInvalida
import exceptions.OptionNull
import exceptions.pacientesExeptions
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import models.*
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.assertThrows
import repositories.hospitalRepositoryConjuntos
import repositories.hospitalRepositoryListas
import repositories.hospitalRepositoryMapas
import java.util.*

class HospitalControllerTest {
    @MockK
    private lateinit var repositoryList: hospitalRepositoryListas
    @MockK
    private lateinit var repositoryConjuntos: hospitalRepositoryConjuntos
    @MockK
    private lateinit var repositoryMapas: hospitalRepositoryMapas

    @InjectMockKs
    private lateinit var hospitalController: HospitalController

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)
        hospitalController = HospitalController(repositoryList, repositoryConjuntos, repositoryMapas)
    }

    private val misPacientesLista = mutableListOf(
        PUrgencia("79192293G", "Sandra Moya", "1980-09-08", "2007-12-10", "2008-12-15"),
        PNormal("98397416N", "Pedro Picapiedra", "1979-01-31", "1981-03-21", "2000-20-03"),
        PNormal("27453044Z", "Celia Gallego", "1956-05-24", "1971-03-29", "1976-27-05"),
        PUrgencia("55755084W", "Miguel Ibañez", "1973-05-25", "2000-01-01", "2001-09-28"),
        PUrgencia("79192293G", "Sandra Moya", "1980-09-08", "2007-12-10", "2008-12-15")
    )

    private val pacienteNormal0 = PNormal("98397416N", "Pedro Picapiedra", "1979-01-31", "1981-03-21", "2000-20-03")
    private val pacienteNormal1 = PNormal("27453044Z", "Celia Gallego", "1956-05-24", "1971-03-29", "1976-27-05")
    private val pacienteUrgencia0 = PUrgencia("55755084W", "Miguel Ibañez", "1973-05-25", "2000-01-01", "2001-09-28")
    private val pacienteUrgencia1 = PUrgencia("79192293G", "Sandra Moya", "1980-09-08", "2007-12-10", "2008-12-15")

    private val misPacientesConjuntos = mutableSetOf( pacienteUrgencia1 ,pacienteNormal0, pacienteNormal1, pacienteUrgencia0, pacienteUrgencia1)

    private val pacienteNormal0M = PNormal("98397416N", "Pedro Picapiedra", "1979-01-31", "1981-03-21", "2000-20-03")
    private val pacienteNormal1M = PNormal("27453044Z", "Celia Gallego", "1956-05-24", "1971-03-29", "1976-27-05")
    private val pacienteUrgencia0M = PUrgencia("55755084W", "Miguel Ibañez", "1973-05-25", "2000-01-01", "2001-09-28")
    private val pacienteUrgencia1M = PUrgencia("79192293G", "Sandra Moya", "1980-09-08", "2007-12-10", "2008-12-15")

    private var misPacientesMapa = mutableMapOf(pacienteNormal0M.dni to pacienteNormal0M, pacienteNormal1M.dni to pacienteNormal1M, pacienteUrgencia0M.dni to pacienteUrgencia0M, pacienteUrgencia1M.dni to pacienteUrgencia1M)

    @Test
    fun isCompletoListas() {
        every { hospitalController.isCompletoListas() } returns false

        val tamñoMáximo: Boolean = misPacientesLista.size >= 50
        val resultado = hospitalController.isCompletoListas()

        assertEquals(tamñoMáximo, resultado)
        assertFalse(hospitalController.isCompletoListas())
    }
    @Test
    fun ingresarListas() {
        val exceptionResult = assertThrows<OpcionInvalida>{
            hospitalController.ingresarListas(5)
        }

        assertEquals("La opcion, 5, no es válida escoge un número válido", exceptionResult.message)
    }

    @Test
    fun numPacientesListas() {
        every { hospitalController.numPacientesListas() } returns misPacientesLista.size

        assertEquals(misPacientesLista.size, hospitalController.numPacientesListas())
    }

    @Test
    fun todosPacientesListas() {
        every { hospitalController.todosPacientesListas() } returns misPacientesLista
        val sol = hospitalController.todosPacientesListas()

        assertEquals(misPacientesLista, hospitalController.todosPacientesListas())
        assertEquals(misPacientesLista[1], sol[1])
        assertEquals(misPacientesLista[4], sol[4])
    }

    @Test
    fun pacientesPorDniListas() {
        every { hospitalController.pacientesPorDniListas() } returns misPacientesLista.sortedBy { it.dni }
        val sol = hospitalController.pacientesPorDniListas()

        assertEquals(misPacientesLista.sortedBy { it.dni }, hospitalController.pacientesPorDniListas())
        assertEquals(misPacientesLista[2], sol[0])
    }

    @Test
    fun pacientesOrdeFechaIngresoListas() {
        every { hospitalController.pacientesOrdeFechaIngresoListas() } returns misPacientesLista.sortedBy { it.fIngreso }
        val sol = hospitalController.pacientesOrdeFechaIngresoListas()

        assertEquals(misPacientesLista.sortedBy { it.fIngreso }, hospitalController.pacientesOrdeFechaIngresoListas())
        assertEquals(misPacientesLista[2], sol[0])
    }

    @Test
    fun pacientesPorNombreIngresoListas() {
        every { hospitalController.pacientesPorNombreIngresoListas() } returns misPacientesLista.sortedBy { it.nombre }
        val sol = hospitalController.pacientesPorNombreIngresoListas()

        assertEquals(misPacientesLista.sortedBy { it.nombre }, hospitalController.pacientesPorNombreIngresoListas())
        assertEquals(misPacientesLista[2], sol[0])
    }

    @Test
    fun pacientesPorTipoListas() {
        every { hospitalController.pacientesPorTipoListas() } returns misPacientesLista.sortedBy { it is PNormal }
        val sol = hospitalController.pacientesPorTipoListas()

        assertEquals(misPacientesLista.sortedBy { it is PNormal }, hospitalController.pacientesPorTipoListas())
        assertEquals(misPacientesLista[0], sol[0])
    }

    @Test
    fun numPacientesPorTipoListas() {
        every { hospitalController.numPacientesPorTipoListas() } returns "Paciente tipo Normal: 2, Urgencia: 3, Otro: 0"

        assertEquals("Paciente tipo Normal: 2, Urgencia: 3, Otro: 0", hospitalController.numPacientesPorTipoListas())
    }

    @Test
    fun isCompletoConjuntos() {
        every { hospitalController.isCompletoConjuntos() } returns false

        val tamñoMáximo: Boolean = misPacientesConjuntos.size >= 50
        val resultado = hospitalController.isCompletoConjuntos()

        assertEquals(tamñoMáximo, resultado)
        assertFalse(hospitalController.isCompletoConjuntos())
    }

    @Test
    fun ingresarConjuntos() {
        val exceptionResult = assertThrows<OpcionInvalida>{
            hospitalController.ingresarConjuntos(5)
        }

        assertEquals("La opcion, 5, no es válida escoge un número válido", exceptionResult.message)
    }

    @Test
    fun numPacientesConjuntos() {
        every { hospitalController.numPacientesConjuntos() } returns misPacientesConjuntos.size

        assertEquals(misPacientesConjuntos.size, hospitalController.numPacientesConjuntos())
    }

    @Test
    fun todosPacientesConjuntos() {
        every { hospitalController.todosPacientesConjuntos() } returns misPacientesConjuntos

        assertEquals(misPacientesConjuntos, hospitalController.todosPacientesConjuntos())
    }

    @Test
    fun pacientesPorDniConjuntos() {
        val conjuntoPacientesSolucion = TreeSet<Pacientes> ( PacienteComparatorDNI() )
        every { hospitalController.pacientesPorDniConjuntos() } returns conjuntoPacientesSolucion
        val sol = hospitalController.pacientesPorDniConjuntos()

        assertEquals(conjuntoPacientesSolucion, hospitalController.pacientesPorDniConjuntos())
    }

    @Test
    fun pacientesOrdeFechaIngresoConjuntos() {
        val conjuntoPacientesSolucion = TreeSet<Pacientes> ( PacienteComparatorFIngreso() )
        every { hospitalController.pacientesOrdeFechaIngresoConjuntos() } returns conjuntoPacientesSolucion
        val sol = hospitalController.pacientesOrdeFechaIngresoConjuntos()

        assertEquals(sol, hospitalController.pacientesOrdeFechaIngresoConjuntos())
    }

    @Test
    fun pacientesPorNombreIngresoConjuntos() {
        val conjuntoPacientesSolucion = TreeSet<Pacientes> ( PacienteComparatorNombre() )
        every { hospitalController.pacientesPorNombreIngresoConjuntos() } returns conjuntoPacientesSolucion
        val sol = hospitalController.pacientesPorNombreIngresoConjuntos()

        assertEquals(conjuntoPacientesSolucion, hospitalController.pacientesPorNombreIngresoConjuntos())
    }

    @Test
    fun pacientesPorTipoConjuntos() {
        val conjuntoPacientesSolucion = TreeSet<Pacientes> ( PacienteComparatorTipo() )
        every { hospitalController.pacientesPorTipoConjuntos() } returns conjuntoPacientesSolucion
        val sol = hospitalController.pacientesPorTipoConjuntos()

        assertEquals(conjuntoPacientesSolucion, hospitalController.pacientesPorTipoConjuntos())
    }

    @Test
    fun numPacientesPorTipoConjuntos() {
        every { hospitalController.numPacientesPorTipoConjuntos() } returns "Paciente tipo Normal: 2, Urgencia: 2, Otro: 0"

        assertEquals("Paciente tipo Normal: 2, Urgencia: 2, Otro: 0", hospitalController.numPacientesPorTipoConjuntos())
    }

    @Test
    fun isCompletoMapas() {
        every { hospitalController.isCompletoMapas() } returns false
        val tamñoMáximo: Boolean = misPacientesConjuntos.size >= 50
        val resultado = hospitalController.isCompletoMapas()

        assertEquals(tamñoMáximo, resultado)
        assertFalse(hospitalController.isCompletoMapas())
    }

    @Test
    fun ingresarMapas() {
        val exceptionResult = assertThrows<OpcionInvalida>{
            hospitalController.ingresarMapas(5)
        }

        assertEquals("La opcion, 5, no es válida escoge un número válido", exceptionResult.message)
    }

    @Test
    fun numPacientesMapas() {
        every { hospitalController.numPacientesMapas() } returns  misPacientesMapa.size
    }

    @Test
    fun todosPacientesMapas() {
        every { hospitalController.todosPacientesMapas() } returns misPacientesMapa
        val solucion = hospitalController.todosPacientesMapas()
        assertEquals(misPacientesMapa, solucion)
    }

    @Test
    fun pacientesPorDniMapas() {
        val mapaPacientesSolucion = TreeMap<String, Pacientes> ( compareBy { misPacientesMapa[it]!!.dni } )
        mapaPacientesSolucion.putAll(misPacientesMapa)
        every { hospitalController.pacientesPorDniMapas() } returns mapaPacientesSolucion
        val solucion = hospitalController.pacientesPorDniMapas()

        assertEquals(solucion[pacienteNormal1M.dni], misPacientesMapa[pacienteNormal1M.dni])
    }

    @Test
    fun pacientesOrdeFechaIngresoMapas() {
        val mapaPacientesSolucion = TreeMap<String, Pacientes> ( compareBy { misPacientesMapa[it]!!.fIngreso } )
        mapaPacientesSolucion.putAll(misPacientesMapa)
        every { hospitalController.pacientesOrdeFechaIngresoMapas() } returns mapaPacientesSolucion
        val solucion = hospitalController.pacientesOrdeFechaIngresoMapas()

        assertEquals(solucion[pacienteNormal1M.dni], misPacientesMapa[pacienteNormal1M.dni])
    }

    @Test
    fun pacientesPorNombreIngresoMapas() {
        val mapaPacientesSolucion = TreeMap<String, Pacientes> ( compareBy { misPacientesMapa[it]!!.nombre } )
        mapaPacientesSolucion.putAll(misPacientesMapa)
        every { hospitalController.pacientesPorNombreIngresoMapas() } returns mapaPacientesSolucion
        val solucion = hospitalController.pacientesPorNombreIngresoMapas()

        assertEquals(solucion[pacienteNormal1M.dni], misPacientesMapa[pacienteNormal1M.dni])
    }

    @Test
    fun pacientesPorTipoMapas() {
        val exceptionResult = assertThrows<OpcionInvalida>{
            hospitalController.ingresarMapas(5)
        }

        assertEquals("La opcion, 5, no es válida escoge un número válido", exceptionResult.message)
    }

    @Test
    fun numPacientesPorTipoMapas() {
        val solucion = "Paciente tipo Normal: 2, Urgencia: 2"
        every { hospitalController.numPacientesPorTipoMapas() } returns solucion
        assertEquals(solucion, hospitalController.numPacientesPorTipoMapas())
    }
}