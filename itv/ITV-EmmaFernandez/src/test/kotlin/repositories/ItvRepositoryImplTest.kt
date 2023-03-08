package repositories

import models.Coche
import models.Moto
import org.junit.jupiter.api.Assertions.assertAll
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.BeforeEach

class ItvRepositoryImplTest {

    private lateinit var repository: ItvRepositoryImpl
    private val vehiculos = listOf(
        Coche("Marca1", 2000, true, 15000, 4),
        Coche("Marca2", 2010, false, 10000, 2),
        Moto("Marca1", 2005, false, 20000, 125),
        Moto("Marca3", 2020, true, 25000, 150)
    )

    @BeforeEach
    fun setup() {
        val v = vehiculos.toMutableList()
        repository = ItvRepositoryImpl(v)
    }

    @Test
    fun getAll() {
        assertEquals(vehiculos, repository.getAll())
    }

    @Test
    fun getCoches() {
        assertEquals(vehiculos.subList(0, 2), repository.getCoches())
    }

    @Test
    fun getVehiculoMasModerno() {
        assertEquals(vehiculos[3], repository.getVehiculoMasModerno())
    }

    @Test
    fun getVehiculosMarca() {
        assertAll(
            { assertEquals(listOf(vehiculos[0], vehiculos[2]), repository.getVehiculosMarca("Marca1")) },
            { assertEquals(listOf(vehiculos[1]), repository.getVehiculosMarca("Marca2")) },
            { assertEquals(listOf(vehiculos[3]), repository.getVehiculosMarca("Marca3")) }
        )
    }

    @Test
    fun getVehiculoMenosKilometros() {
        assertEquals(vehiculos[1], repository.getVehiculoMenosKilometros())
    }

    @Test
    fun getMediaKilometrosMotos() {
        assertEquals(22500.0, repository.getMediaKilometrosMotos())
    }

    @Test
    fun getCocheMasAntiguoMasDosPuertas() {
        assertEquals(vehiculos[0], repository.getCocheMasAntiguoMasDosPuertas())
    }

    @Test
    fun getNumVehiculosPorTipo() {
        val res = mapOf("Coche" to 2, "Moto" to 2)
        assertEquals(res, repository.getNumVehiculosPorTipo())
    }

    @Test
    fun getAptosPorTipo() {
        val res = mapOf("Coche" to 1, "Moto" to 1)
        assertEquals(res, repository.getAptosPorTipo())
    }

    @Test
    fun getMediaAñoPorTipo() {
        val res = mapOf("Coche" to 2005, "Moto" to 2012)
        assertEquals(res, repository.getMediaAñoPorTipo())
    }

    @Test
    fun getVehiculosPorMarcas() {
        val res = mapOf("Marca1" to listOf(vehiculos[0], vehiculos[2]),
            "Marca2" to listOf(vehiculos[1]), "Marca3" to listOf(vehiculos[3]))
        assertEquals(res, repository.getVehiculosPorMarcas())
    }

    @Test
    fun getVehiculosOrdenAño() {
        val res = listOf(vehiculos[0], vehiculos[2], vehiculos[1], vehiculos[3])
        assertEquals(res, repository.getVehiculosOrdenAño())
    }

    @Test
    fun getMarcasDescendente() {
        assertEquals(listOf("Marca3", "Marca2", "Marca1"), repository.getMarcasDescendente())
    }

    @Test
    fun getVehiculosPorMarcasOrdenKmDescendente() {
        val res = mapOf("Marca1" to listOf(vehiculos[2], vehiculos[0]),
            "Marca2" to listOf(vehiculos[1]), "Marca3" to listOf(vehiculos[3]))
        assertEquals(res, repository.getVehiculosPorMarcasOrdenKmDescendente())
    }
}