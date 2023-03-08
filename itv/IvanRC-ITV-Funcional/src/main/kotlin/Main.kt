import models.Coche
import models.Moto
import models.Vehiculo
import kotlin.math.roundToInt
import kotlin.math.roundToLong

fun main(args: Array<String>) {
    val misVehiculos = listOf<Vehiculo>(
        Coche("Seat", 135, 1975, true, 4),
        Moto("Toyota", 12,  1845, false, 6),
        Moto("Cytroen", 453,  2023, true, 12),
        Coche("Toyota", 275,  1989, false, 2),
        Moto("Peugeot", 157,  2014, true, 8),
        Coche("Seat", 1256,  2004, false, 6)
    )

    //Mostrar todos lo vehiculos:
    misVehiculos.forEach{ println(it) }
    println()

    //Mostrar solo los coches:
    misVehiculos.filterIsInstance<Coche>().forEach { println(it) }
    println()

    //Mostrar el vehiculo más moderno
    println(misVehiculos.maxBy { it.añoMatriculacion })
    println()

    //Mostrar el vehiculo con menos kilometros:
    println(misVehiculos.minBy { it.kilometros })
    println()

    //Mostrar la media de kilometros de las motos:
    println(String.format("%.2f", misVehiculos.filterIsInstance<Moto>().map{it.kilometros}.average()))
    println()

    //Mostrar el coche más antigüo con más de 2 puertas:
    println(misVehiculos.filter { it is Coche && it.numeroPuertas > 2 }.minBy { it.añoMatriculacion })
    println()

    //Mostrar el número de vehículos, por cada tipo:
    val (coches, motos) = misVehiculos.partition { it is Coche }
    println("Hay un total de ${coches.size} Coches.")
    println("Hay un total de ${motos.size} Motos.")
    println()

    //Mostrar número de vehiculos de cada tipo que encima son aptos:
    val (coches1, motos1) = misVehiculos.partition { it is Coche }
    println("Hay un total de ${coches1.filter{it.Apto}.size} Coches aptos.")
    println("Hay un total de ${motos1.filter{it.Apto}.size} Motos aptos.")
    println()

    //Mostrar por cada tipo de vehículo la media de año de fabricación:
    val (coches2, motos2) = misVehiculos.partition { it is Coche }
    println("La media de años de matriculacion de los Coches es: ${coches2.map{it.añoMatriculacion}.average().roundToInt()}.")
    println("La media de años de matriculacion de las Motos es: ${motos2.map{it.añoMatriculacion}.average().roundToInt()}.")
    println()

    //Mostrar la agrupación de vehículos por su modelo:
    misVehiculos.groupBy { it.modelo }.forEach{
        println("${it.key}-${it.value}")
    }
    println()

    //Mostrar los vehículos ordenados por su año de matriculación:
    misVehiculos.sortedBy { it.añoMatriculacion }.forEach { println(it) }
    println()

    //Mostrar todas los modelos de vehículos, ordenadas descendientemente:
    misVehiculos.map { it.modelo }.toSortedSet{m1,m2 -> m2.compareTo(m1)}.forEach { println(it) }
    println()

    //Mostrar los vehículos agrupados por modelo, y ordenados según su número de kilometros:
    misVehiculos.groupBy { it.modelo }.forEach{
        println("${it.key}-${it.value.sortedWith { o1,o2 -> o2.kilometros-o1.kilometros }}")
    }
    println()
}