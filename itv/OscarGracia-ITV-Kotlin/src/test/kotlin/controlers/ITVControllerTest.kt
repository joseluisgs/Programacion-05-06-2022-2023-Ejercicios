package controlers

import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import models.*
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import repositories.IItvRepository
import repositories.ItvRepository

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockKExtension::class)
internal class ITVControllerTest {

    @MockK
    private lateinit var repository: ItvRepository
    @InjectMockKs
    private lateinit var controller:ITVController
    init {
        MockKAnnotations.init(this)
    }

    var listaVehiculos:Array<Vehiculo?> = arrayOf(
        Camion(1500,"1234-FDW","Sk38",TipoMotor.Electrico, kilometrosVehiculo = 612320,
        "2010-01-01","2022-01-17",apto = false),
        Motocicleta(1000,"4634-FDW","C43",TipoMotor.Gasoleo, kilometrosVehiculo = 12320,
            "2015-06-11","2022-08-11",apto = true),
        Automovil(4,"1234-FDW","Corola",TipoMotor.Gasolina, kilometrosVehiculo = 20,
            "2000-11-07","2022-02-17",apto = false)
    )


    @Test
    fun listarvehiculos() {
        every { repository.listarvehiculos() } returns listaVehiculos
        var salida = controller.listarvehiculos()
        assertAll(
            {assertEquals(salida[0]!!.matricula,"1234-FDW")},
            {assertEquals(salida[1],listaVehiculos[1])},
            {assertEquals(salida[2],listaVehiculos[2])}
        )
        verify { repository.listarvehiculos() }
    }

    @Test
    fun listarVehiculosRevisados() {
        every { repository.listarVehiculosRevisados() } returns Array(1){listaVehiculos[1]}
        var salida = controller.listarVehiculosRevisados()

            assertEquals(salida[0],listaVehiculos[1])

        verify { repository.listarVehiculosRevisados() }
    }

    @Test
    fun listarVehiculosRevisadosDescartados() {
        every { repository.listarVehiculosRevisadosDescartados() } returns arrayOf(listaVehiculos[0],listaVehiculos[2])
        var salida = controller.listarVehiculosRevisadosDescartados()
        assertAll(
            {assertEquals(salida[0],listaVehiculos[0])},
            {assertEquals(salida[1],listaVehiculos[2])}
        )
        verify { repository.listarVehiculosRevisadosDescartados() }
    }
    @Test
    fun getInfoVehiculo() {
        every { repository.getInfoVehiculo("1234-FDW") } returns listaVehiculos[0]
        var salida = controller.getInfoVehiculo("1234-FDW")
        assertAll(
            {assertEquals(salida!!.apto,listaVehiculos[0]!!.apto)},
            {assertEquals(salida!!.kilometrosVehiculo,listaVehiculos[0]!!.kilometrosVehiculo)},
            {assertEquals(salida!!.modelo,listaVehiculos[0]!!.modelo)},
            {assertEquals(salida!!.matricula,listaVehiculos[0]!!.matricula)},
            {assertEquals(salida!!.fechaMatriculacion,listaVehiculos[0]!!.fechaMatriculacion)},
            {assertEquals(salida!!.fechaUltimaRevision,listaVehiculos[0]!!.fechaUltimaRevision)},
        )
        verify { repository.getInfoVehiculo("1234-FDW") }
    }


    @Test
    fun actualizarVehiculo() {
        var autoModificado:Automovil? = Automovil(4,"1234-FDW","Corola",TipoMotor.Gasolina,
            kilometrosVehiculo = 1220,
            "2000-11-07","2022-02-17",apto = true)

        every { repository.actualizarVehiculo("1234-FDW","2022-02-17",apto = true)
        } returns autoModificado
        var salida = controller.actualizarVehiculo("1234-FDW","2022-02-17",apto = true)
        assertAll(
            { assertNotEquals(salida!!.apto,listaVehiculos[0]!!.apto) },
            {assertEquals(salida!!.kilometrosVehiculo,listaVehiculos[0]!!.kilometrosVehiculo)},
            {assertEquals(salida!!.modelo,listaVehiculos[1]!!.modelo)},
            {assertEquals(salida!!.matricula,listaVehiculos[1]!!.matricula)},
            {assertEquals(salida!!.fechaMatriculacion,listaVehiculos[1]!!.fechaMatriculacion)},
            { assertNotEquals(salida!!.fechaUltimaRevision,listaVehiculos[1]!!.fechaUltimaRevision) },
        )
        verify { repository.actualizarVehiculo("1234-FDW","2022-02-17",apto = true) }
    }

    @Test
    fun listarporTipo() {
        every { repository.listarporTipo("CAMION") } returns Array(1){listaVehiculos[0]}
        var salida = controller.listarporTipo("CAMION")

        assertEquals(salida[0],listaVehiculos[0])

        verify { repository.listarporTipo("CAMION") }
    }
    }

    @Test
    fun filtroEntrada() {
        TODO()
    }
