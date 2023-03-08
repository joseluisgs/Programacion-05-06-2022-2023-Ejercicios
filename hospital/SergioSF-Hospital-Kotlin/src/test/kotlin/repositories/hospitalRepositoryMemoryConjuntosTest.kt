package repositories

import models.*
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import java.util.*

class hospitalRepositoryMemoryConjuntosTest {

    private val hospitalRepository = hospitalRepositoryMemoryConjuntos()

    private val pacienteNormal0 = PNormal("98397416N", "Pedro Picapiedra", "1979-01-31", "1981-03-21", "2000-20-03")
    private val pacienteNormal1 = PNormal("27453044Z", "Celia Gallego", "1956-05-24", "1971-03-29", "1976-27-05")
    private val pacienteUrgencia0 = PUrgencia("55755084W", "Miguel Ibañez", "1973-05-25", "2000-01-01", "2001-09-28")
    private val pacienteUrgencia1 = PUrgencia("79192293G", "Sandra Moya", "1980-09-08", "2007-12-10", "2008-12-15")

    private val conjuntoPacientes = mutableSetOf( pacienteUrgencia1 ,pacienteNormal0, pacienteNormal1, pacienteUrgencia0, pacienteUrgencia1)

    @Test
    fun isCompletoConjuntos() {
        val solucion = hospitalRepository.isCompletoConjuntos()
        assertFalse(solucion)
    }

    @Test
    fun ingresarConjuntos() {
    }

    @Test
    fun numPacientesConjuntos() {
        val solucion = hospitalRepository.numPacientesConjuntos()
        assertEquals(conjuntoPacientes.size, solucion)
    }

    @Test
    fun todosPacientesConjuntos() {
        val solucion = hospitalRepository.todosPacientesConjuntos()
        assertEquals(conjuntoPacientes.first().nombre, solucion.first().nombre)
    }

    @Test
    fun pacientesPorDniConjuntos() {
        val solucion = hospitalRepository.pacientesPorDniConjuntos()
        val expected = TreeSet<Pacientes> ( PacienteComparatorDNI() )
        expected.addAll(conjuntoPacientes)
        assertEquals(expected.first().nombre, solucion.first().nombre)
    }

    @Test
    fun pacientesOrderFechaIngresoConjuntos() {
        val solucion = hospitalRepository.pacientesOrderFechaIngresoConjuntos()
        val expected = TreeSet<Pacientes> ( PacienteComparatorFIngreso() )
        expected.addAll(conjuntoPacientes)
        assertEquals(expected.first().nombre, solucion.first().nombre)
    }

    @Test
    fun pacientesPorNombreIngresoConjuntos() {
        val solucion = hospitalRepository.pacientesPorNombreIngresoConjuntos()
        val expected = TreeSet<Pacientes> ( PacienteComparatorNombre() )
        expected.addAll(conjuntoPacientes)
        assertEquals(expected.first().nombre, solucion.first().nombre)
    }

    @Test
    fun pacientesPorTipoConjuntos() {
        val solucion = hospitalRepository.pacientesPorTipoConjuntos()
        val expected = TreeSet<Pacientes> ( PacienteComparatorTipo() )
        expected.addAll(conjuntoPacientes)
        assertEquals(expected.first().nombre, solucion.first().nombre)
    }

    @Test
    fun numPacientesPorTipoConjuntos() {
        val solucion = hospitalRepository.numPacientesPorTipoConjuntos()
        val expect = "Paciente tipo Normal: 2, Urgencia: 2, Otro: 0"
        assertEquals(expect, solucion)
    }
}