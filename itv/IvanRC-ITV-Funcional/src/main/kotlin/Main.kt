import models.Coche
import models.Moto
import models.Vehiculo

fun main(args: Array<String>) {
    val misVehiculos = listOf<Vehiculo>(
        Coche("Seat", 135, 1975, true, 4),
        Moto("Toyota", 12,  1845, false, 6),
        Moto("Cytroen", 453,  2023, true, 12),
        Coche("Toyota", 275,  1989, false, 2),
        Moto("Peugeot", 157,  2014, true, 8),
        Coche("Seat", 1256,  2004, false, 6)
    )

    //Mostrar todos los vehciulos:
    val lista = misVehiculos
    lista.forEach{println(it)}
    println()

    //mostrar solo coches:
    val lista1 = misVehiculos.filter{it is Coche}
    lista1.forEach{println(it)}
    println()

    //mostrar vehículo más moderno:
    val vehiculo = misVehiculos.maxBy{ it.añoMatriculacion }
    println(vehiculo)
    println()

    //sacar vehículo con menos kilómetros:
    val vehiculo1 = misVehiculos.minBy{ it.kilometros }
    println(vehiculo1)
    println()

    //buscar por marca
    val lista2 = misVehiculos.filter { it.modelo == "Toyota" }
    lista2.forEach{println(it)}
    println()

    //sacar media kilómetros motos
    val mediaKilometrosMotos = misVehiculos.filter { it is Moto }.map {it.kilometros}.average()
    println(mediaKilometrosMotos)
    println()

    //sacar coche más antiguo con más de dos puertas:
    val vehiculo2 = misVehiculos.filter { it is Coche && it.numeroPuertas > 2}.minBy { it.añoMatriculacion }
    println(vehiculo2)
    println()

    //obtener número de vehículos de cada tipo:
    val mapa = misVehiculos.groupBy { if(it is Coche) "Coche" else "Moto" }.mapValues { it.value.map { it }.count()}
    mapa.keys.forEach {
        println("$it----${mapa[it]}")
    }
    println()

    //obtener número de vehículos de cada tipo que son aptos
    val mapa1 = misVehiculos.filter { it.Apto }.groupBy { if(it is Coche) "Coche" else "Moto" }.mapValues { it.value.map {it}.count() }
    mapa1.keys.forEach {
        println("$it----${mapa1[it]}")
    }
    println()

    //obtener por cada tipo la media del año de fabricación
    val mapa2 = misVehiculos.groupBy { if(it is Coche) "Coche" else "Moto" }.mapValues { it.value.map { it.añoMatriculacion }.average()}
    mapa2.keys.forEach {
        println("$it----${mapa2[it]}")
    }
    println()

    //obtener vehículos agrupados por marca:
    val mapa3 = misVehiculos.groupBy { it.modelo }
    mapa3.keys.forEach {
        println("$it----${mapa3[it]}")
    }
    println()

    //mostrar todos los vehículos ordenados por año:
    val lista3 = misVehiculos.sortedWith { v1, v2 -> v1.añoMatriculacion - v2.añoMatriculacion }
    lista3.forEach(System.out::println)
    println()

    //obtener marcas ordenadas descendentemente:
    val lista4 = misVehiculos.map { it.modelo }.distinct().sortedByDescending { it }
    lista4.forEach(System.out::println)
    println()

    //obtener los vehículos agrupados por marca y ordenados por kilómetros descendentemente:
    val mapa4 = misVehiculos.sortedByDescending { it.kilometros }.groupBy { it.modelo }
    mapa4.keys.forEach {
        println("$it----${mapa4[it]}")
    }
    println()
}