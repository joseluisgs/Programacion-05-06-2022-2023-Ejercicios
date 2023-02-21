import models.Coche
import models.Moto
import models.Vehicles


fun main() {
    val v1 = Coche("Toyota", 1985, true, 65, 1)
    val v2 = Coche("Renault", 2023, false, 6552, 5)
    val v3 = Coche("Peugeot", 1895, true, 1745, 9)
    val v4 = Coche("Renault", 1785, false, 4565, 2)
    val v5 = Coche("Ford", 2028, true, 56425, 6)
    val v6 = Coche("Citroen", 2020, false, 4560, 4)
    val v7 = Moto("Toyota", 1943, true, 1245, 8)
    val v8 = Moto("Audi", 1235, false, 22, 35)
    val v9 = Moto("Ferrari", 1865, false, 426, 15)
    val v10 = Moto("Bugatti", 2025, true, 457, 2)
    val lista = listOf(v1, v2, v3, v4, v5, v6, v7, v8, v9, v10)



    selectAll(lista)    //Imprime toda la lista
    println()
    println()
    selectCoches(lista)     //Imprime solo los coches
    println()
    println()
    mostModernVehicle(lista)        // imprime el coche más moderno
    println()
    println()
    lessKilometerVehicle(lista)     // Imprime el vehiculo con menos kilómetros
    println()
    println()
    motoAverageKilometer(lista)     //hace la media de los kilómetros de las motos
    println()
    println()
    oldestCarWithMoreThan2Doors(lista)      // imprime el coche más antiguo ocn más de 2 puertas
    println()
    println()
    numOfVehiclesByType(lista)       // imprime el número de Vehículos por tipo
    println()
    println()
    numOfAptVehiclesByType(lista)       // Imprime el número de vehículos aptos por tipo
    println()
    println()
    averageYearByType(lista)        // Imprime la media de los años por tipo
    println()
    println()
    groupVehiclesByMarca(lista)   //agrupa los vehículos por marca
    println()
    println()
    orderVehiclesByYear(lista)      // ordena los vehículos por año
    println()
    println()
    orderVehicleByMarcaDescending(lista)     //ordena los vehículos por marca descendientemente
    println()
    println()
    OrderByNumKilometerGroupedVehiclesByMarca(lista)        //ordena por marca descendientemente los vehículos agrupados por num KM
}

fun OrderByNumKilometerGroupedVehiclesByMarca(lista: List<Vehicles>) {
    return println(lista.sortedByDescending { it.numKm }.groupBy { it.marca })
}

fun orderVehicleByMarcaDescending(lista: List<Vehicles>) {
    return println(lista.sortedByDescending { it.marca })
}

fun orderVehiclesByYear(lista: List<Vehicles>) {
    return println(lista.sortedBy { it.fabricationYear })
}

fun groupVehiclesByMarca(lista: List<Vehicles>) {
    return println(lista.groupBy { it.marca })
}

fun averageYearByType(lista: List<Vehicles>) {

    val mediaMoto = (lista.filterIsInstance<Moto>().map { it.fabricationYear }.sum()) / lista.filterIsInstance<Moto>().map { it.fabricationYear }.count()
    val mediaCoches = (lista.filterIsInstance<Coche>().map { it.fabricationYear }.sum()) / lista.filterIsInstance<Coche>().map { it.fabricationYear }.count()

    return println("La media de años de Coches es de $mediaCoches, el de moto es de $mediaMoto")
}

fun numOfAptVehiclesByType(lista: List<Vehicles>) {
    val numCochesAptos = lista.filterIsInstance<Coche>().count { it.apto }
    val numMotosAptas = lista.filterIsInstance<Moto>().count { it.apto }

    return println("Hay $numCochesAptos coches aptos y $numMotosAptas motos aptas")
}

fun numOfVehiclesByType(lista: List<Vehicles>) {
    val numCoches = lista.filterIsInstance<Coche>().count()
    val numMotos = lista.filterIsInstance<Moto>().count()

    return println("Hay $numCoches coches y $numMotos motos")
}

fun oldestCarWithMoreThan2Doors(lista: List<Vehicles>) {
    return println(lista.filterIsInstance<Coche>().filter { it.numPuertas > 2 }.minBy { it.fabricationYear }.toString())
}

fun motoAverageKilometer(lista: List<Vehicles>) {
    return println((lista.filterIsInstance<Moto>().map { (it.numKm)}.sum())/ lista.filterIsInstance<Moto>().count())
}

fun lessKilometerVehicle(lista: List<Vehicles>) {
    return println(lista.minBy { it.numKm })
}


fun selectAll(lista: List<Vehicles>): List<Vehicles> {
    for (i in lista.indices){
        println(lista[i])
    }
    return lista
}

fun selectCoches(lista: List<Vehicles>) {

    return println(lista.filterIsInstance<Coche>())
}

fun mostModernVehicle(lista: List<Vehicles>) {
    return println(lista.maxBy { it.fabricationYear })
}

