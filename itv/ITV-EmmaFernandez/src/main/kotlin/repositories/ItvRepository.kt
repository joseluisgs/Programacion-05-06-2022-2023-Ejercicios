package repositories

import models.Coche
import models.Vehiculo

interface ItvRepository {
    fun getAll(): List<Vehiculo>
    fun getCoches(): List<Coche>
    fun getVehiculoMasModerno(): Vehiculo
    fun getVehiculoMenosKilometros(): Vehiculo
    fun getMediaKilometrosMotos(): Double
    fun getCocheMasAntiguoMasDosPuertas(): Coche
    fun getNumVehiculosPorTipo(): Map<String, Int>
    fun getAptosPorTipo(): Map<String,Int>
    fun getMediaAñoPorTipo(): Map<String, Int>
    fun getVehiculosPorMarcas(): Map<String, List<Vehiculo>>
    fun getVehiculosOrdenAño(): List<Vehiculo>
    fun getMarcasDescendente(): List<String>
    fun getVehiculosPorMarcasOrdenKmDescendente(): Map<String, List<Vehiculo>>
}