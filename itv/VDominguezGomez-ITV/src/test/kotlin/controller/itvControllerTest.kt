package controller

import exceptions.NoExistenVehiculosException
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import models.Coche
import models.Moto
import models.Vehiculo
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import repositories.itvRepository
import `typealias`.ListaVehiculos
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockKExtension::class)
class itvControllerTest {
    @MockK
    private lateinit var repository: itvRepository<Vehiculo>
    @InjectMockKs
    private lateinit var controller: itvController

    var misVehiculos: Sequence<Vehiculo> = sequenceOf(
        Coche("Peugeot", 2007, true, 120000, 4),
        Moto("Seat", 2013, true, 90000, 1750),
        Coche("Seat", 2009, false, 160000, 2),
        Moto("BMW", 2019, true, 30000, 1860),
        Moto("Peugeot", 2011, false, 140000, 1790),
        Coche("Peugeot", 2016, true, 125000, 5),
        Coche("BMW", 2003, false, 210000, 2)
    )

    val listaCoches = listOf<Vehiculo>(
        Coche("Peugeot", 2007, true, 120000, 4),
        Coche("Seat", 2009, false, 160000, 2),
        Coche("Peugeot", 2016, true, 125000, 5),
        Coche("BMW", 2003, false, 210000, 2)
    )

    val listaMotos = listOf<Vehiculo>(
        Moto("Seat", 2013, true, 90000, 1750),
        Moto("BMW", 2019, true, 30000, 1860),
        Moto("Peugeot", 2011, false, 140000, 1790),
    )

    val vehiculosPorTipo = mapOf<String?, Int>(
        Pair("Coche", 4),
        Pair("Moto", 3)
    )

    val vehiculosAptos = mapOf<String?, Int>(
        Pair("Coche", 2),
        Pair("Moto", 2)
    )

    val mediasFabricacion = mapOf<String?, Double>(
        Pair("Coche", (2003 + 2007 + 2009 + 2016).toDouble()/4),
        Pair("Moto", (2011 + 2013 + 2019).toDouble()/3)
    )

    val vehiculosPorMarca = mapOf<String, ListaVehiculos>(
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

    val ordenadosAnyo = listOf<Vehiculo>(
        Coche("BMW", 2003, false, 210000, 2),
        Coche("Peugeot", 2007, true, 120000, 4),
        Coche("Seat", 2009, false, 160000, 2),
        Moto("Peugeot", 2011, false, 140000, 1790),
        Moto("Seat", 2013, true, 90000, 1750),
        Coche("Peugeot", 2016, true, 125000, 5),
        Moto("BMW", 2019, true, 30000, 1860)
    )

    val vehiculosPorMarcaOrdenadosKm = mapOf<String, ListaVehiculos>(
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

    @Test
    fun mostrarTodosTest() {
        every { repository.mostrarTodos() } returns misVehiculos.toList()

        val lista = controller.mostrarTodos()

        assertAll(
            { assertEquals(misVehiculos.toList(), lista) },
            { assertTrue { lista.size == 7 } }
        )

        verify { repository.mostrarTodos() }
    }

    @Test
    fun mostrarCochesTest() {
        every { repository.mostrarCoches() } returns listaCoches

        val lista = controller.mostrarCoches()

        assertAll(
            { assertTrue { lista.size == 4 } },
            { assertTrue { listaCoches == lista } }
        )

        verify { repository.mostrarCoches() }
    }

    @Test
    fun mostrarMotosTest() {
        every { repository.mostrarMotos() } returns listaMotos

        val lista = controller.mostrarMotos()

        assertAll(
            { assertTrue { lista.size == 3 } },
            { assertTrue { listaMotos == lista } }
        )

        verify { repository.mostrarMotos() }
    }

    @Test
    fun vehiculoMasModernoTest() {
        every { repository.vehiculoMasModerno() } returns misVehiculos.toList()[3]

        val vehiculo = controller.vehiculoMasModerno()

        assertEquals(misVehiculos.toList()[3], vehiculo)

        verify { repository.vehiculoMasModerno() }
    }

    @Test
    fun vehiculoMasModernoNoEncontradoTest() {
        every { repository.vehiculoMasModerno() } returns null

        val exc = assertThrows<NoExistenVehiculosException> {
            controller.vehiculoMasModerno()
        }

        assertEquals("No existen vehiculos en la base de datos", exc.message)

        verify { repository.vehiculoMasModerno() }
    }

    @Test
    fun vehiculoMenosKilometrajeTest() {
        every { repository.vehiculoMenosKilometraje() } returns misVehiculos.toList()[3]

        val vehiculo = controller.vehiculoMenosKilometraje()

        assertEquals(misVehiculos.toList()[3], vehiculo)

        verify { repository.vehiculoMenosKilometraje() }
    }

    @Test
    fun vehiculoMenosKilometrajeNoEncontradoTest() {
        every { repository.vehiculoMenosKilometraje() } returns null

        val exc = assertThrows<NoExistenVehiculosException> {
            controller.vehiculoMenosKilometraje()
        }

        assertEquals("No existen vehiculos en la base de datos", exc.message)

        verify { repository.vehiculoMenosKilometraje() }
    }

    @Test
    fun mediaKilometrajeMotosTest() {
        every { repository.mediaKilometrajeMotos() } returns (90000 + 30000 + 140000).toDouble()/3

        val media = controller.mediaKilometrajeMotos()

        assertEquals((90000 + 30000 + 140000).toDouble()/3, media)

        verify { repository.mediaKilometrajeMotos() }
    }

    @Test
    fun vehiculoMasAntiguoConMasDeDosPuertasTest() {
        every { repository.vehiculoMasAntiguoConMasDeDosPuertas() } returns misVehiculos.toList()[0]

        val vehiculo = controller.vehiculoMasAntiguoConMasDeDosPuertas()

        assertEquals(misVehiculos.toList()[0], vehiculo)

        verify { repository.vehiculoMasAntiguoConMasDeDosPuertas() }
    }

    @Test
    fun numVehiculosTipoTest() {
        every { repository.numVehiculosTipo() } returns vehiculosPorTipo

        val mapa = controller.numVehiculosTipo()

        assertEquals(vehiculosPorTipo, mapa)

        verify { repository.numVehiculosTipo() }
    }

    @Test
    fun numMotosTest() {
        every { repository.numMotos() } returns 3

        val motos = controller.numMotos()

        assertTrue { motos == 3 }

        verify { repository.numMotos() }
    }

    @Test
    fun numCochesTest() {
        every { repository.numCoches() } returns 4

        val coches = controller.numCoches()

        assertTrue { coches == 4 }

        verify { repository.numCoches() }
    }

    @Test
    fun numVehiculosAptosTest() {
        every { repository.numVehiculosAptos() } returns vehiculosAptos

        val mapa = controller.numVehiculosAptos()

        assertEquals(vehiculosAptos, mapa)

        verify { repository.numVehiculosAptos() }
    }

    @Test
    fun numCochesAptosTest() {
        every { repository.numCochesAptos() } returns 2

        val cochesAptos = controller.numCochesAptos()

        assertTrue { cochesAptos == 2 }

        verify { repository.numCochesAptos() }
    }

    @Test
    fun numMotosAptasTest() {
        every { repository.numMotosAptas() } returns 2

        val motosAptas = controller.numMotosAptas()

        assertTrue { motosAptas == 2 }

        verify { repository.numMotosAptas() }
    }

    @Test
    fun mediaAnyosFabricacionVehiculosTest() {
        every { repository.mediaAnyosFabricacionVehiculos() } returns mediasFabricacion

        val medias = controller.mediaAnyosFabricacionVehiculos()

        assertEquals(mediasFabricacion, medias)

        verify { repository.mediaAnyosFabricacionVehiculos() }
    }

    @Test
    fun mediaAnyosFabricacionCochesTest() {
        every { repository.mediaAnyosFabricacionCoches() } returns (2011 + 2013 + 2019).toDouble()/3

        val media = controller.mediaAnyosFabricacionCoches()

        assertTrue { media == (2011 + 2013 + 2019).toDouble()/3 }

        verify { repository.mediaAnyosFabricacionCoches() }
    }

    @Test
    fun mediaAnyosFabricacionMotosTest() {
        every { repository.mediaAnyosFabricacionMotos() } returns (2011 + 2013 + 2019).toDouble()/3

        val media = controller.mediaAnyosFabricacionMotos()

        assertTrue { media == (2011 + 2013 + 2019).toDouble()/3 }

        verify { repository.mediaAnyosFabricacionMotos() }
    }

    @Test
    fun vehiculosPorMarcaTest() {
        every { repository.vehiculosPorMarca() } returns vehiculosPorMarca

        val mapa = controller.vehiculosPorMarca()

        assertEquals(vehiculosPorMarca, mapa)

        verify { repository.vehiculosPorMarca() }
    }

    @Test
    fun vehiculosOrdenadosAnyoTest() {
        every { repository.vehiculosOrdenadosAnyo() } returns ordenadosAnyo

        val lista = controller.vehiculosOrdenadosAnyo()

        assertEquals(ordenadosAnyo, lista)

        verify { repository.vehiculosOrdenadosAnyo() }
    }

    @Test
    fun ordenarMarcasDescendenteTest() {
        every { repository.ordenarMarcasDescendente() } returns listOf("Seat", "Peugeot", "BMW")

        val lista = controller.ordenarMarcasDescendente()

        assertEquals(listOf("Seat", "Peugeot", "BMW"), lista)

        verify { repository.ordenarMarcasDescendente() }
    }

    @Test
    fun vehiculosPorMarcaOrdenadoKmDescendente() {
        every { repository.vehiculosPorMarcaOrdenadoKmDescendente() } returns vehiculosPorMarcaOrdenadosKm

        val mapa = controller.vehiculosPorMarcaOrdenadoKmDescendente()

        assertEquals(vehiculosPorMarcaOrdenadosKm, mapa)

        verify { repository.vehiculosPorMarcaOrdenadoKmDescendente() }
    }


}