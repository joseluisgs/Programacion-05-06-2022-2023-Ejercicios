package interfaces

import models.Vehiculo

interface ItvExtension: CrudRepository<Vehiculo, String> {
    fun isFull():Boolean
    fun getCoches(): List<Vehiculo>
    fun getCocheMaxModerno(): Vehiculo
    fun getVehiculoMinKilometros(): Vehiculo
    fun getMediaKilometrosMotos(): Double
    fun getOldestCoche(): Vehiculo
    fun getCountVehiculos(): Map<String, Int>
    fun getCountVehiculosAptos(): Map<String, Int>
    fun getMediaVehiculosAnio(): Map<String, Double>
    fun groupVehiculosByMarca(): Map<String, List<Vehiculo>>
    fun sortVehiculosByAnio(): List<Vehiculo>
    fun sortDesVehiculosByMarca(): List<Vehiculo>
    fun groupVehiculosByMarcaSortByKilometro(): Map<String, List<Vehiculo>>
}