import models.Coche
import models.Moto
import models.Vehicles


fun main() {
    val v1 = Coche("Toyota", 1985, true, 65.16, 1)
    val v2 = Coche("Renault", 2023, false, 6552.98, 5)
    val v3 = Coche("Peugeot", 1895, true, 1745.41, 9)
    val v4 = Coche("Renault", 1785, false, 4565.47, 2)
    val v5 = Coche("Ford", 2028, true, 56425.25, 6)
    val v6 = Coche("Citroen", 2020, false, 4560.45, 4)
    val v7 = Moto("Toyota", 1943, true, 1245.74, 8)
    val v8 = Moto("Audi", 1235, false, 22.12, 35)
    val v9 = Moto("Ferrari", 1865, false, 426.45, 15)
    val v10 = Moto("Bugatti", 2025, true, 457.65, 2)
    val lista = listOf(v1, v2, v3, v4, v5, v6, v7, v8, v9, v10)

    selectAll(lista)    //Imprime toda la lista
    selectCoches(lista)     //Imprime solo los coches
    mostModernVehicle(lista)        // imprime el coche más moderno
    lessKilometerVehicle(lista)     // Imprime el vehiculo con menos kilómetros
    motoAverageKilometer(lista)     //hace la media de los kilómetros de las motos
    oldestCarWithMoreThan2Doors(lista)      // imprime el coche más antiguo ocn más de 2 puertas
    numOfVehiclesByType(lista)       // imprime el número de Vehículos por tipo
    numOfAptVehiclesByType(lista)       // Imprime el número de vehículos aptos por tipo
    averageYearByType(lista)        // Imprime la media de los años por tipo
    groupVehiclesByMarca(lista)   //agrupa los vehículos por marca
    orderVehiclesByYear(lista)      // ordena los vehículos por año
    orderVehicleByMarcaDescending(lista)     //ordena los vehículos por marca descendientemente
    OrderByNumKilometerGroupedVehiclesByMarca(lista)        //ordena por marca descendientemente los vehículos agrupados por num KM
}

fun selectAll(lista: List<Vehicles>): List<Vehicles> {
    return lista
}
fun selectCoches(lista: List<Vehicles>): List<Coche> {
    return lista.filterIsInstance<Coche>()
}
fun mostModernVehicle(lista: List<Vehicles>): Vehicles {
    return lista.maxBy { it.fabricationYear }
}
fun lessKilometerVehicle(lista: List<Vehicles>): Vehicles {
    return lista.minBy { it.numKm }
}
fun OrderByNumKilometerGroupedVehiclesByMarca(lista: List<Vehicles>): Map<String, List<Vehicles>> {
    return lista.sortedByDescending { it.numKm }.groupBy { it.marca }
}
fun orderVehicleByMarcaDescending(lista: List<Vehicles>): List<Vehicles> {
    return lista.sortedByDescending { it.marca }
}
fun orderVehiclesByYear(lista: List<Vehicles>): List<Vehicles> {
    return lista.sortedBy { it.fabricationYear }
}
fun groupVehiclesByMarca(lista: List<Vehicles>): Map<String, List<Vehicles>> {
    return lista.groupBy { it.marca }
}
fun averageYearByType(lista: List<Vehicles>): Map<String, Double> {
    return lista.groupBy { if (it is Moto) "Moto" else "Coche" }.mapValues { it.value.map { it.fabricationYear }.average() }
}
fun numOfAptVehiclesByType(lista: List<Vehicles>): Map<String, Int> {
    return lista.groupBy{if(it is Moto) "Moto" else "Coche"}.mapValues { it.value.map { it.apto }.count() }
}
fun numOfVehiclesByType(lista: List<Vehicles>) {
    return println(lista.groupBy{if(it is Moto) "Moto" else "Coche"}.mapValues { it.value.map { it }.count() })
}
fun oldestCarWithMoreThan2Doors(lista: List<Vehicles>): Coche {
    return lista.filterIsInstance<Coche>().filter { it.numPuertas > 2 }.minBy { it.fabricationYear }
}
fun motoAverageKilometer(lista: List<Vehicles>): Double {
    return (lista.filterIsInstance<Moto>().map { (it.numKm)}.sum()) / lista.filterIsInstance<Moto>().count()
}










