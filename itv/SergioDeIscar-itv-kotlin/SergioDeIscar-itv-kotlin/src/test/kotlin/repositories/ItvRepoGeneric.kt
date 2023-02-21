package repositories

import interfaces.ItvExtension
import models.Coche
import models.Moto
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Test
import java.time.LocalDate
import kotlin.test.*

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
abstract class ItvRepoGeneric {
    private lateinit var repository: ItvExtension
    private lateinit var repositoryShort: ItvExtension

    @BeforeEach
    fun setUp(){
        repository = createRepoEmpty()
        repositoryShort = createRepoShort()
    }

    abstract fun createRepoEmpty(): ItvExtension
    abstract fun createRepoShort(): ItvExtension

    private val vehiculos = arrayOf(
        Coche("1234ABC", "Audi", LocalDate.now().minusYears(5),
            true, 150000, 5), // 0
        Moto("1234ABD", "Ford", LocalDate.now().minusDays(40),
            false, 24000, 600), // 1
        Coche("1234ABE", "Pepe", LocalDate.now().minusYears(2),
            true, 125000, 7), // 2
        Moto("1234ABF", "Audi", LocalDate.now().minusDays(20),
            true, 74000, 125) // 3
    )

    //region CRUD
    @Order(1)
    @Test
    fun saveTest(){
        assertAll(
            { assertEquals(vehiculos[0], repository.save(vehiculos[0])) },

            { assertEquals(vehiculos[0], repositoryShort.save(vehiculos[0])) },
            { assertEquals(vehiculos[1], repositoryShort.save(vehiculos[1])) },
            { assertEquals(vehiculos[2], repositoryShort.save(vehiculos[2])) },
            { assertNull(repositoryShort.save(vehiculos[3])) }
        )
    }

    @Order(2)
    @Test
    fun saveAllTest(){
        repository.saveAll(vehiculos.toList())
        repositoryShort.saveAll(vehiculos.toList())
        assertAll(
            { assertContentEquals(vehiculos, repository.getAll().toTypedArray()) },
            { assertContentEquals(vehiculos.copyOfRange(0,3), repositoryShort.getAll().toTypedArray()) }
        )
    }

    @Order(3)
    @Test
    fun estaLlenoTest(){
        repository.saveAll(vehiculos.toList())
        repositoryShort.saveAll(vehiculos.toList())
        assertAll(
            { assertFalse { repository.isFull() } },
            { assertTrue { repositoryShort.isFull() } }
        )
    }

    @Order(3)
    @Test
    fun findTest(){
        repository.saveAll(vehiculos.copyOfRange(0,2).toList())
        assertAll(
            { assertEquals(vehiculos[0], repository.find(vehiculos[0].matricula)) },
            { assertEquals(vehiculos[1], repository.find(vehiculos[1].matricula)) },
            { assertNull(repository.find(vehiculos[2].matricula)) }
        )
    }

    @Order(3)
    @Test
    fun deleteTest(){
        repository.saveAll(vehiculos.copyOfRange(0,2).toList())
        assertAll(
            { assertEquals(vehiculos[0], repository.delete(vehiculos[0].matricula)) },
            { assertEquals(1, repository.getAll().size) },
            { assertEquals(vehiculos[1], repository.delete(vehiculos[1].matricula)) },
            { assertNull(repository.delete(vehiculos[2].matricula)) }
        )
    }
    //endregion

    //region Queries
    @Order(3)
    @Test
    fun getCochesTest(){
        repository.saveAll(vehiculos.toList())
        val response = repository.getCoches()
        assertAll(
            { assertEquals(vehiculos[0], response[0]) },
            { assertEquals(vehiculos[2], response[1]) },
            { assertEquals(2, response.size) }
        )
    }

    @Order(3)
    @Test
    fun getCocheMaxModernoTest(){
        repository.saveAll(vehiculos.toList())
        assertEquals(vehiculos[0], repository.getCocheMaxModerno())
    }

    @Order(3)
    @Test
    fun getVehiculoMinKilometrosTest(){
        repository.saveAll(vehiculos.toList())
        assertEquals(vehiculos[1], repository.getVehiculoMinKilometros())
    }

    @Order(3)
    @Test
    fun getMediaKilometrosMotosTest(){
        repository.saveAll(vehiculos.toList())
        //assertEquals(vehiculos.map { it.kilometro }.average(), repository.getMediaKilometrosMotos())
        assertEquals((vehiculos.map { it.kilometro }.sum() / vehiculos.size).toDouble() , repository.getMediaKilometrosMotos())
    }

    @Order(3)
    @Test
    fun getOldestCocheTest(){
        repository.saveAll(vehiculos.toList())
        assertEquals(vehiculos[0], repository.getOldestCoche())
    }

    @Order(3)
    @Test
    fun getCountVehiculosTest(){
        repository.saveAll(vehiculos.toList())
        val expected = mapOf("Coche" to 2, "Moto" to 2)
        assertEquals(expected, repository.getCountVehiculos())
    }

    @Order(3)
    @Test
    fun getCountVehiculosAptosTest(){
        repository.saveAll(vehiculos.toList())
        val expected = mapOf("Coche" to 2, "Moto" to 1)
        assertEquals(expected, repository.getCountVehiculosAptos())
    }

    @Order(3)
    @Test
    fun getMediaVehiculosAnioTest(){
        repository.saveAll(vehiculos.toList())
        val coche = ((vehiculos[0].anio.year + vehiculos[2].anio.year).toDouble() / 2)
        val moto = ((vehiculos[1].anio.year + vehiculos[3].anio.year).toDouble() /2)
        val expected = mapOf("Coche" to coche, "Moto" to moto)
        assertEquals(expected, repository.getMediaVehiculosAnio())
    }

    @Order(3)
    @Test
    fun groupVehiculosByMarcaTest(){
        repository.saveAll(vehiculos.toList())
        val expected = mapOf(
            "Audi" to listOf(vehiculos[0], vehiculos[3]),
            "Ford" to listOf(vehiculos[1]),
            "Pepe" to listOf(vehiculos[2])
        )
        assertEquals(expected, repository.groupVehiculosByMarca())
    }

    @Order(3)
    @Test
    fun sortVehiculosByAnioTest(){
        repository.saveAll(vehiculos.toList())
        val expected = listOf(vehiculos[0], vehiculos[2], vehiculos[1], vehiculos[3])
        assertEquals(expected, repository.sortVehiculosByAnio())
    }

    @Order(3)
    @Test
    fun sortDesVehiculosByMarcaTest(){
        repository.saveAll(vehiculos.toList())
        val expected = listOf(vehiculos[2], vehiculos[1], vehiculos[0], vehiculos[3])
        assertEquals(expected, repository.sortDesVehiculosByMarca())
    }

    @Order(3)
    @Test
    fun groupVehiculosByMarcaSortByKilometroTest(){
        repository.saveAll(vehiculos.toList())
        val expected = mapOf(
            "Audi" to listOf(vehiculos[3], vehiculos[0]),
            "Ford" to listOf(vehiculos[1]),
            "Pepe" to listOf(vehiculos[2])
        )
        assertEquals(expected, repository.groupVehiculosByMarcaSortByKilometro())
    }
    //endregion
}