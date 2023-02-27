package FunctionalITV

import FunctionalITV.repositories.ITVrepo

fun main() {
    val repoITV = ITVrepo()

    repoITV.getAll().forEach { println(it) }
    println()

    repoITV.getOnlyCars().forEach { println(it) }
    println()

    println(repoITV.getMoreModern())
    println()

    println(repoITV.getLessKm())
    println()

    repoITV.searchByBrand("Toyota").forEach { println(it) }
    println()

    println(repoITV.getAverageKmAllMoto())
    println()

    println(repoITV.getCarMoreAncientWithMoreTwoDoors())
    println()

    repoITV.getNumByBrand().forEach { println("${it.key}:${it.value}") }
    println()

    repoITV.getNumAptosByVehicle().forEach { println("Apto ${it.key}:${it.value}") }
    println()

    repoITV.getByVehicleAverageYearFabricate().forEach { println(it) }
    println()

    repoITV.getAllGroupByBrand().forEach { println("${it.key} -> ${it.value}") }
    println()

    repoITV.getAllOrderByYearFabricate().forEach { println(it) }
    println()

    repoITV.getAllOrderByBrandDesc().forEach { println(it) }
    println()

    repoITV.getAllGroupByBrandAndSortedByKmDesc().forEach { println("${it.key} -> ${it.value}") }
    println()
}