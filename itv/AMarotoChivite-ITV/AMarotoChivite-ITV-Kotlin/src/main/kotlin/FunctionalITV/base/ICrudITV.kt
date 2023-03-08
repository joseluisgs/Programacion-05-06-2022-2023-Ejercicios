package FunctionalITV.base

import FunctionalITV.models.Vehicle

interface ICrudITV<T> {
    fun getAll(): List<T>

    fun getOnlyCars(): List<FunctionalITV.models.Car>

    fun getMoreModern(): T

    fun getLessKm(): T

    fun searchByBrand(brandToSearch: String): List<T>

    fun getAverageKmAllMoto(): Double

    fun getCarMoreAncientWithMoreTwoDoors(): T

    fun getNumByBrand(): Map<String, Int>

    fun getNumAptosByVehicle(): Map<String, Int>

    fun getByVehicleAverageYearFabricate(): Map<String, Int>

    fun getAllGroupByBrand(): Map<String, List<Vehicle>>

    fun getAllOrderByYearFabricate(): List<T>

    fun getAllOrderByBrandDesc(): List<T>

    fun getAllGroupByBrandAndSortedByKmDesc(): Map<String, List<Vehicle>>
}