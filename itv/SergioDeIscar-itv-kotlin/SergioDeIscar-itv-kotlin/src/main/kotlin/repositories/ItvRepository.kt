package repositories

import interfaces.ItvExtension
import models.Vehiculo

class ItvRepository(private val maxSize: Int = 10): ItvExtension {
    private val vehiculos = mutableMapOf<String, Vehiculo>()
    override fun isFull():Boolean{
        return vehiculos.size >= maxSize
    }

    override fun getCoches(): List<Vehiculo> {
        return vehiculos.filterValues { it::class.simpleName == "Coche" }.values.toList()
    }

    override fun getCocheMaxModerno(): Vehiculo {
        return vehiculos.values.minBy { it.anio }
    }

    override fun getVehiculoMinKilometros(): Vehiculo {
        return vehiculos.values.minBy { it.kilometro }
    }

    override fun getMediaKilometrosMotos(): Double {
        return vehiculos.values.map { it.kilometro }.average()
    }

    override fun getOldestCoche(): Vehiculo {
        return vehiculos.values.filter { it::class.simpleName == "Coche" }.minBy { it.anio }
    }

    override fun getCountVehiculos(): Map<String, Int> {
        return vehiculos.values.groupBy{ it::class.simpleName ?: "_" }.mapValues { it.value.size }
    }

    override fun getCountVehiculosAptos(): Map<String, Int> {
        return vehiculos.filterValues { it.apto }.values.groupBy{ it::class.simpleName ?: "_" }.mapValues { it.value.size }
    }

    override fun getMediaVehiculosAnio(): Map<String, Double> {
        return vehiculos.values.groupBy{ it::class.simpleName ?: "_" }.mapValues { it.value.map { it.anio.year }.average() }
    }

    override fun groupVehiculosByMarca(): Map<String, List<Vehiculo>> {
        return vehiculos.values.groupBy { it.marca }
    }

    override fun sortVehiculosByAnio(): List<Vehiculo> {
        return vehiculos.values.sortedBy { it.anio }
    }

    override fun sortDesVehiculosByMarca(): List<Vehiculo> {
        return vehiculos.values.sortedByDescending { it.marca }
    }

    override fun groupVehiculosByMarcaSortByKilometro(): Map<String, List<Vehiculo>> {
        return vehiculos.values.groupBy { it.marca }.mapValues { it.value.sortedBy { it.kilometro } }
    }

    override fun save(t: Vehiculo): Vehiculo? {
        if (isFull()) return null
        vehiculos[t.matricula] = t
        return t
    }

    override fun find(id: String): Vehiculo? {
        return vehiculos[id]
    }

    override fun delete(id: String): Vehiculo? {
        return vehiculos.remove(id)
    }

    override fun getAll(): List<Vehiculo> {
        return vehiculos.values.toList()
    }

    override fun saveAll(list: List<Vehiculo>) {
        if (isFull()) return
        list.forEach{ save(it) }
    }
}