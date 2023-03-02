package repositories

import models.PNormal
import models.PUrgencia
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class hospitalRepositoryMemoryListasTest {

    private val hospitalRepository = hospitalRepositoryMemoryListas()

    private val misPacientesLista = mutableListOf(
        PUrgencia("79192293G", "Sandra Moya", "1980-09-08", "2007-12-10", "2008-12-15"),
        PNormal("98397416N", "Pedro Picapiedra", "1979-01-31", "1981-03-21", "2000-20-03"),
        PNormal("27453044Z", "Celia Gallego", "1956-05-24", "1971-03-29", "1976-27-05"),
        PUrgencia("55755084W", "Miguel Ibañez", "1973-05-25", "2000-01-01", "2001-09-28"),
        PUrgencia("79192293G", "Sandra Moya", "1980-09-08", "2007-12-10", "2008-12-15")
    )

    @Test
    fun isCompletoListas() {
        val solucion = hospitalRepository.isCompletoListas()
        assertFalse(solucion)
    }

    @Test
    fun ingresarListas() {
    }

    @Test
    fun numPacientesListasTest() {
        val sol = hospitalRepository.numPacientesListas()
        val expect = misPacientesLista.size
        assertEquals(sol, expect)
    }

    @Test
    fun todosPacientesListas() {
        val solucion = hospitalRepository.todosPacientesListas()
        assertEquals(solucion[0].nombre, misPacientesLista[0].nombre)
    }

    @Test
    fun pacientesPorDniListas() {
        val solucion = hospitalRepository.pacientesPorDniListas()
        val expect = misPacientesLista.sortedBy { it.dni }


        assertEquals(misPacientesLista[2].nombre, solucion[0].nombre)
    }

    @Test
    fun pacientesOrderFechaIngresoListas() {
        val solucion = hospitalRepository.pacientesOrderFechaIngresoListas()
        val expect = misPacientesLista.sortedBy { it.fIngreso }
        assertEquals(solucion[0].nombre, expect[0].nombre)
        assertEquals(misPacientesLista[2].nombre, expect[0].nombre)
    }

    @Test
    fun pacientesPorNombreIngresoListas() {
        val solucion = hospitalRepository.pacientesPorNombreIngresoListas()
        val expect = misPacientesLista.sortedBy { it.nombre }
        assertEquals(solucion[0].nombre, expect[0].nombre)
        assertEquals(misPacientesLista[2].nombre, expect[0].nombre)
    }

    @Test
    fun pacientesPorTipoListas() {
        val solucion = hospitalRepository.pacientesPorTipoListas()
        assertEquals(solucion[0].nombre, misPacientesLista[1].nombre)
    }

    @Test
    fun numPacientesPorTipoListas() {
        val solucion = hospitalRepository.numPacientesPorTipoListas()
        val expect = "Paciente tipo Normal: 2, Urgencia: 3, Otro: 0"
        assertEquals(solucion, expect)
    }
}