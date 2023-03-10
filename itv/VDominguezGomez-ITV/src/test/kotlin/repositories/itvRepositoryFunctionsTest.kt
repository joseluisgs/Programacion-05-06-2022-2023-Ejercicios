package repositories

import models.Coche
import models.Moto
import models.Vehiculo
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.assertAll
import `typealias`.ListaVehiculos
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class itvRepositoryFunctionsTest {

    private lateinit var repository: itvRepository<Vehiculo>

    var misVehiculos: Sequence<Vehiculo> = sequenceOf(
        Coche("Peugeot", 2007, true, 120000, 4),
        Moto("Seat", 2013, true, 90000, 1750),
        Coche("Seat", 2009, false, 160000, 2),
        Moto("BMW", 2019, true, 30000, 1860),
        Moto("Peugeot", 2011, false, 140000, 1790),
        Coche("Peugeot", 2016, true, 125000, 5),
        Coche("BMW", 2003, false, 210000, 2)
    )

    @BeforeEach
    fun inicio() {
        repository = itvRepositoryFunctions()
    }

    @Test
    fun mostrarTodosTest() {
        val lista = repository.mostrarTodos()

        assertAll(
            { assertEquals(misVehiculos.toList(), lista) },
            { assertTrue { lista.size == 7 } },
            { assertEquals(misVehiculos.first(), lista.first()) },
            { assertTrue { misVehiculos.last() == lista.last() } }
        )
    }

    @Test
    fun mostrarCochesTest() {
        val lista = repository.mostrarCoches()
        val miLista = listOf<Vehiculo>(
            Coche("Peugeot", 2007, true, 120000, 4),
            Coche("Seat", 2009, false, 160000, 2),
            Coche("Peugeot", 2016, true, 125000, 5),
            Coche("BMW", 2003, false, 210000, 2)
        )

        assertAll(
            { assertTrue(lista.size == 4) },
            { assertEquals(miLista, lista) },
            { assertTrue(lista.contains(Coche("Seat", 2009, false, 160000, 2))) }
        )
    }

    @Test
    fun mostrarMotosTest() {
        val lista = repository.mostrarMotos()
        val miLista = listOf<Vehiculo>(
            Moto("Seat", 2013, true, 90000, 1750),
            Moto("BMW", 2019, true, 30000, 1860),
            Moto("Peugeot", 2011, false, 140000, 1790),
        )

        assertAll(
            { assertTrue(lista.size == 3) },
            { assertEquals(miLista, lista) },
            { assertTrue(lista.contains(Moto("BMW", 2019, true, 30000, 1860))) }
        )
    }

    @Test
    fun vehiculoMasModernoTest() {
        val vehiculo = repository.vehiculoMasModerno()
        val miVehiculo = misVehiculos.toList()[3]

        assertEquals(miVehiculo, vehiculo)
    }

    @Test
    fun vehiculoMenosKilometrajeTest() {
        val vehiculo = repository.vehiculoMenosKilometraje()
        val miVehiculo = misVehiculos.toList()[3]

        assertEquals(miVehiculo, vehiculo)
    }

    @Test
    fun mediaKilometrajeMotosTest() {
        val media = repository.mediaKilometrajeMotos()
        val miMedia = (90000 + 30000 + 140000).toDouble()/3

        assertEquals(miMedia, media)
    }

    @Test
    fun vehiculoMasAntiguoConMasDeDosPuertasTest() {
        val vehiculo = repository.vehiculoMasAntiguoConMasDeDosPuertas()
        val miVehiculo = misVehiculos.toList()[0]

        assertEquals(miVehiculo, vehiculo)
    }

    @Test
    fun numVehiculosTipoTest() {
        val mapa = repository.numVehiculosTipo()
        val miMapa = mapOf<String?, Int>(
            Pair("Coche", 4),
            Pair("Moto", 3)
        )

        assertEquals(miMapa, mapa)
    }

    @Test
    fun numMotosTest() {
        val num = repository.numMotos()

        assertTrue { num == 3 }
    }

    @Test
    fun numCochesTest() {
        val num = repository.numCoches()

        assertTrue { num == 4 }
    }

    @Test
    fun numVehiculosAptosTest() {
        val mapa = repository.numVehiculosAptos()
        val miMapa = mapOf<String?, Int>(
            Pair("Coche", 2),
            Pair("Moto", 2)
        )

        assertEquals(miMapa, mapa)
    }

    @Test
    fun numCochesAptosTest() {
        val num = repository.numCochesAptos()

        assertTrue { num == 2 }
    }

    @Test
    fun numMotosAptasTest() {
        val num = repository.numMotosAptas()

        assertTrue { num == 2 }
    }

    @Test
    fun mediaAnyosFabricacionVehiculosTest() {
        val mapa = repository.mediaAnyosFabricacionVehiculos()
        val miMapa = mapOf<String?, Double>(
            Pair("Coche", (2003 + 2007 + 2009 + 2016).toDouble()/4),
            Pair("Moto", (2011 + 2013 + 2019).toDouble()/3)
        )

        assertEquals(miMapa, mapa)
    }

    @Test
    fun mediaAnyosFabricacionCochesTest() {
        val num: Double = repository.mediaAnyosFabricacionCoches()

        assertTrue { num == (2003 + 2007 + 2009 + 2016).toDouble()/4 }
    }

    @Test
    fun mediaAnyosFabricacionMotosTest() {
        val num: Double = repository.mediaAnyosFabricacionMotos()

        assertTrue { num == (2011 + 2013 + 2019).toDouble()/3 }
    }

    @Test
    fun vehiculosPorMarcaTest() {
        val mapa = repository.vehiculosPorMarca()
        val miMapa = mapOf<String, ListaVehiculos>(
            Pair("Peugeot", listOf(
                Coche("Peugeot", 2007, true, 120000, 4),
                Moto("Peugeot", 2011, false, 140000, 1790),
                Coche("Peugeot", 2016, true, 125000, 5)
                )),
            Pair("Seat", listOf(
                Moto("Seat", 2013, true, 90000, 1750),
                Coche("Seat", 2009, false, 160000, 2)
                )),
            Pair("BMW", listOf(
                Moto("BMW", 2019, true, 30000, 1860),
                Coche("BMW", 2003, false, 210000, 2)
                ))
        )

        assertEquals(miMapa, mapa)
    }

    @Test
    fun vehiculosOrdenadosAnyoTest() {
        val lista = repository.vehiculosOrdenadosAnyo()
        val miLista = listOf<Vehiculo>(
            Coche("BMW", 2003, false, 210000, 2),
            Coche("Peugeot", 2007, true, 120000, 4),
            Coche("Seat", 2009, false, 160000, 2),
            Moto("Peugeot", 2011, false, 140000, 1790),
            Moto("Seat", 2013, true, 90000, 1750),
            Coche("Peugeot", 2016, true, 125000, 5),
            Moto("BMW", 2019, true, 30000, 1860)
            )

        assertEquals(miLista, lista)
    }

    @Test
    fun ordenarMarcasDescendenteTest() {
        val lista = repository.ordenarMarcasDescendente()
        val miLista = listOf("Seat", "Peugeot", "BMW")

        assertEquals(miLista, lista)
    }

    @Test
    fun vehiculosPorMarcaOrdenadoKmDescendente() {
        val mapa = repository.vehiculosPorMarcaOrdenadoKmDescendente()
        val miMapa = mapOf<String, ListaVehiculos>(
            Pair("Peugeot", listOf(
                Coche("Peugeot", 2016, true, 125000, 5),
                Moto("Peugeot", 2011, false, 140000, 1790),
                Coche("Peugeot", 2007, true, 120000, 4)
            )),
            Pair("Seat", listOf(
                Moto("Seat", 2013, true, 90000, 1750),
                Coche("Seat", 2009, false, 160000, 2)
            )),
            Pair("BMW", listOf(
                Moto("BMW", 2019, true, 30000, 1860),
                Coche("BMW", 2003, false, 210000, 2)
            ))
        )

        assertEquals(miMapa, mapa)
    }

}