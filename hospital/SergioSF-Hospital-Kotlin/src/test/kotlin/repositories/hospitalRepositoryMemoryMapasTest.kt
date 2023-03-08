package repositories

import models.PNormal
import models.PUrgencia
import models.Pacientes
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import java.util.*

class hospitalRepositoryMemoryMapasTest {

    private val hospitalRepository = hospitalRepositoryMemoryMapas()

    private val pacienteNormal0 = PNormal("98397416N", "Pedro Picapiedra", "1979-01-31", "1981-03-21", "2000-20-03")
    private val pacienteNormal1 = PNormal("27453044Z", "Celia Gallego", "1956-05-24", "1971-03-29", "1976-27-05")
    private val pacienteUrgencia0 = PUrgencia("55755084W", "Miguel Ibañez", "1973-05-25", "2000-01-01", "2001-09-28")
    private val pacienteUrgencia1 = PUrgencia("79192293G", "Sandra Moya", "1980-09-08", "2007-12-10", "2008-12-15")

    private var mapaPacientes = mutableMapOf(pacienteNormal0.dni to pacienteNormal0, pacienteNormal1.dni to pacienteNormal1, pacienteUrgencia0.dni to pacienteUrgencia0, pacienteUrgencia1.dni to pacienteUrgencia1)

    @Test
    fun isCompletoMapas() {
        val solucion = hospitalRepository.isCompletoMapas()
        assertFalse(solucion)
    }

    @Test
    fun ingresarMapas() {
    }

    @Test
    fun numPacientesMapas() {
        val sol = hospitalRepository.numPacientesMapas()
        val expect = mapaPacientes.size
        assertEquals(sol, expect)
    }

    @Test
    fun todosPacientesMapas() {
        val solucion = hospitalRepository.todosPacientesMapas()
        val expect: MutableMap<String, Pacientes> = mapaPacientes

        assertEquals(mapaPacientes[pacienteNormal0.dni]?.nombre , solucion[pacienteNormal0.dni]?.nombre)
        assertEquals(mapaPacientes.firstNotNullOf { it.value.nombre } , solucion.firstNotNullOf { it.value.nombre })
    }

    @Test
    fun pacientesPorDniMapas() {
        val solucion = hospitalRepository.pacientesPorDniMapas()
        val expected = TreeMap<String, Pacientes> ( compareBy { mapaPacientes[it]!!.dni } )
        expected.putAll(mapaPacientes)
        assertEquals(expected.firstNotNullOf { it.value.nombre }, solucion.firstNotNullOf { it.value.nombre })
    }

    @Test
    fun pacientesOrderFechaIngresoMapas() {
        val solucion = hospitalRepository.pacientesOrderFechaIngresoMapas()
        val expected = TreeMap<String, Pacientes> ( compareBy { mapaPacientes[it]!!.fIngreso } )
        expected.putAll(mapaPacientes)
        assertEquals(expected.firstNotNullOf { it.value.nombre }, solucion.firstNotNullOf { it.value.nombre })
    }

    @Test
    fun pacientesPorNombreIngresoMapas() {
        val solucion = hospitalRepository.pacientesPorNombreIngresoMapas()
        val expected = TreeMap<String, Pacientes> ( compareBy { mapaPacientes[it]!!.nombre } )
        expected.putAll(mapaPacientes)
        assertEquals(expected.firstNotNullOf { it.value.nombre }, solucion.firstNotNullOf { it.value.nombre })
    }

    @Test
    fun pacientesPorTipoMapas() {
        val solucion = hospitalRepository.pacientesPorTipoMapas()
        assertEquals(mapaPacientes[pacienteUrgencia0.dni]?.nombre, solucion.firstNotNullOf { it.value.nombre })
    }

    @Test
    fun numPacientesPorTipoMapas() {
        val solucion = hospitalRepository.numPacientesPorTipoMapas()
        val expect = "Paciente tipo Normal: 2, Urgencia: 2"
        assertEquals(solucion, expect)
    }
}