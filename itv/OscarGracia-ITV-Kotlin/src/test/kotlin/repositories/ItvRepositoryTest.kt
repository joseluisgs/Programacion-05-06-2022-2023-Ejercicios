package repositories

import controlers.ITVController
import models.*
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach

internal class ItvRepositoryTest {
        private  var repository: ItvRepository =ItvRepository()

        var listaVehiculos: Array<Vehiculo?> = arrayOf(
            Camion(
                1500, "1234-FDW", "Sk38", TipoMotor.Electrico, kilometrosVehiculo = 612320,
                "2010-01-01", "2022-01-17", apto = false
            ),
            Motocicleta(
                1000, "434-FDW", "C43", TipoMotor.Gasoleo, kilometrosVehiculo = 12320,
                "2015-06-11", "2022-08-11", apto = true
            ),
            Automovil(
                4, "1234-FDW", "Corola", TipoMotor.Gasolina, kilometrosVehiculo = 20,
                "2000-11-07", "2022-02-17", apto = false
            )
        )

        @Test
        fun listarvehiculos() {

            var salida = repository.listarvehiculos()

            Assertions.assertAll(
                { Assertions.assertEquals(salida[0], listaVehiculos[0]) },
                { Assertions.assertEquals(salida[1], listaVehiculos[1]) },
                { Assertions.assertEquals(salida[2], listaVehiculos[2]) }
            )
        }

        @Test
        fun listarVehiculosRevisados() {
            var salida = repository.listarVehiculosRevisados()
            Assertions.assertEquals(salida[0], listaVehiculos[1])
        }

            @Test
            fun listarVehiculosRevisadosDescartados() {
                var salida = repository.listarVehiculosRevisadosDescartados()
                Assertions.assertAll(
                    { Assertions.assertEquals(salida[0], listaVehiculos[0]) },
                    { Assertions.assertEquals(salida[2], listaVehiculos[2]) }
                )
            }

            @Test
            fun getInfoVehiculo() {
                var salida = repository.getInfoVehiculo("1234-FDW")
                Assertions.assertAll(
                    { Assertions.assertEquals(salida!!.apto, listaVehiculos[0]!!.apto) },
                    { Assertions.assertEquals(salida!!.kilometrosVehiculo, listaVehiculos[1]!!.kilometrosVehiculo) },
                    { Assertions.assertEquals(salida!!.modelo, listaVehiculos[1]!!.modelo) },
                    { Assertions.assertEquals(salida!!.matricula, listaVehiculos[1]!!.matricula) },
                    { Assertions.assertEquals(salida!!.fechaMatriculacion, listaVehiculos[1]!!.fechaMatriculacion) },
                    { Assertions.assertEquals(salida!!.fechaUltimaRevision, listaVehiculos[1]!!.fechaUltimaRevision) },
                )
            }


            @Test
            fun actualizarVehiculo() {
                var autoModificado: Automovil? = Automovil(
                    4, "1234-FDW", "Corola", TipoMotor.Gasolina,
                    kilometrosVehiculo = 1220,
                    "2000-11-07", "2022-02-17", apto = true
                )

                var salida = repository.actualizarVehiculo("1234-FDW", "2022-02-17", apto = true)
                Assertions.assertAll(
                    { Assertions.assertNotEquals(salida!!.apto, listaVehiculos[0]!!.apto) },
                    { Assertions.assertEquals(salida!!.kilometrosVehiculo, listaVehiculos[1]!!.kilometrosVehiculo) },
                    { Assertions.assertEquals(salida!!.modelo, listaVehiculos[1]!!.modelo) },
                    { Assertions.assertEquals(salida!!.matricula, listaVehiculos[1]!!.matricula) },
                    { Assertions.assertEquals(salida!!.fechaMatriculacion, listaVehiculos[1]!!.fechaMatriculacion) },
                    {
                        Assertions.assertNotEquals(
                            salida!!.fechaUltimaRevision,
                            listaVehiculos[1]!!.fechaUltimaRevision
                        )
                    },
                )
            }

            @Test
            fun listarporTipo() {
                var salida = repository.listarporTipo("CAMION")
                Assertions.assertEquals(salida[0], listaVehiculos[0])
            }
        }

