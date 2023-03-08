package FunctionalITV

import FunctionalITV.repositories.ITVrepo

fun main() {
    val repoITV = ITVrepo()

    println("=== getAll ===")
    repoITV.getAll().forEach { println(it) }
    println()

    println("=== getOnlyCars ===")
    repoITV.getOnlyCars().forEach { println(it) }
    println()

    println("=== getMoreModern ===")
    println(repoITV.getMoreModern())
    println()

    println("=== getLessKm ===")
    println(repoITV.getLessKm())
    println()

    println("=== searchByBrand ===")
    repoITV.searchByBrand("Toyota").forEach { println(it) }
    println()

    println("=== getAverageKmAllMoto ===")
    println(repoITV.getAverageKmAllMoto())
    println()

    println("=== getCarMoreAncientWithMoreTwoDoors ===")
    println(repoITV.getCarMoreAncientWithMoreTwoDoors())
    println()

    println("=== getNumByBrand ===")
    repoITV.getNumByBrand().forEach { println("${it.key}:${it.value}") }
    println()

    println("=== getNumAptosByVehicle ===")
    repoITV.getNumAptosByVehicle().forEach { println("Apto ${it.key}:${it.value}") }
    println()

    println("=== getByVehicleAverageYearFabricate ===")
    repoITV.getByVehicleAverageYearFabricate().forEach { println(it) }
    println()

    println("=== getAllGroupByBrand ===")
    repoITV.getAllGroupByBrand().forEach { println("${it.key} -> ${it.value}") }
    println()

    println("=== getAllOrderByYearFabricate ===")
    repoITV.getAllOrderByYearFabricate().forEach { println(it) }
    println()

    println("=== getAllOrderByBrandDesc ===")
    repoITV.getAllOrderByBrandDesc().forEach { println(it) }
    println()

    println("=== getAllGroupByBrandAndSortedByKmDesc ===")
    repoITV.getAllGroupByBrandAndSortedByKmDesc().forEach { println("${it.key} -> ${it.value}") }
    println()
}