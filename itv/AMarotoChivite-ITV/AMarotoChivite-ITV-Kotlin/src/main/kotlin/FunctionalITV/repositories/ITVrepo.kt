package FunctionalITV.repositories

import FunctionalITV.base.ICrudITV
import FunctionalITV.models.Car
import FunctionalITV.models.Moto
import FunctionalITV.models.Vehicle
import kotlin.math.roundToInt

class ITVrepo : ICrudITV<Vehicle> {

    private val repository = mutableListOf<Vehicle>()

    init {
        repository.add(Car("Toyota", 1972, true, 320, 2))
        repository.add(Car("Toyota", 1980, true, 330, 2))
        repository.add(Car("Citroen", 1975, false, 200, 4))
        repository.add(Car("Mazda", 1969, true, 500, 3))
        repository.add(Moto("Yamaha", 2015, true, 150, 4))
        repository.add(Moto("Yamaha", 2012, false, 145, 4))
        repository.add(Moto("Honda", 1999, false, 350, 3))
        repository.add(Moto("BMW", 2001, true, 300, 6))
    }

    override fun getAll(): List<Vehicle> {
        return repository.toList()
    }

    override fun getOnlyCars(): List<Car> {
        return repository.filter { it::class.simpleName == Car::class.simpleName } as List<Car>
        /*=== Otra opción ===
        return repository.filterIsInstance<Car>()
         */
    }

    override fun getMoreModern(): Vehicle {
        return repository.sortedByDescending { it.anyo }.first()
        /*
        === Otra opción ===
        return repository.maxByOrNull { it.anyo }!!
        */
    }

    override fun getLessKm(): Vehicle {
        return repository.sortedBy { it.km }.first()
        /*
        === Otra opción ===
        return repository.minByOrNull { it.km }!!
         */
    }

    override fun searchByBrand(brandToSearch: String): List<Vehicle> {
        return repository.filter { it.brand == brandToSearch }
    }

    override fun getAverageKmAllMoto(): Double {
        val motoList = repository.filter { it::class.simpleName == Moto::class.simpleName }
        val kmMotoList = motoList.map { it.km }
        return kmMotoList.average()
    }

    override fun getCarMoreAncientWithMoreTwoDoors(): Vehicle {
        val carList = repository.filter { it::class.simpleName == Car::class.simpleName }
        // val carlist = repository.filterIsInstance<Car>()
        return carList
            .filter { (it as Car).numDoors > 2 }
            .minBy { it.anyo }
    }

    override fun getNumByBrand(): Map<String, Int> {
        val mapGroupByBrand = repository.groupBy { it.brand }
        return mapGroupByBrand.mapValues { it.value.size }
    }

    override fun getNumAptosByVehicle(): Map<String, Int> {
        val aptosList = repository.filter { it.apto }
        val group = aptosList.groupBy { it::class.simpleName.toString() }
        return group.mapValues { it.value.size }
    }

    override fun getByVehicleAverageYearFabricate(): Map<String, Int> {
        return repository
            .groupBy { it::class.simpleName.toString() }
            .mapValues { it -> it.value.map { it.anyo }.average().roundToInt() }

        /*
        === Otra opción 1 ===
        return repository
            .groupBy { it::class.simpleName.toString() }
            .mapValues { (_, v) -> v.map { it.anyo }.average().roundToInt() }
         */

        /*
        === Otra opción 2 ===
        return repository
            .groupBy { it::class.simpleName.toString() }
            .mapValues { it.value.map { it.anyo }.average().roundToInt() }
         */
    }

    override fun getAllGroupByBrand(): Map<String, List<Vehicle>> {
        return repository.groupBy { it.brand }
    }

    override fun getAllOrderByYearFabricate(): List<Vehicle> {
        return repository.sortedBy { it.anyo }
    }

    override fun getAllOrderByBrandDesc(): List<Vehicle> {
        return repository.sortedByDescending { it.brand }
    }

    override fun getAllGroupByBrandAndSortedByKmDesc(): Map<String, List<Vehicle>> {
        return repository
            .sortedByDescending { it.km }
            .groupBy { it.brand }
    }
}