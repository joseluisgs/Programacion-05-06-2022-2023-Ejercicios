package repositories

import exceptions.NoExistenVehiculosException
import exceptions.VehiculoException
import models.Coche
import models.Moto
import models.Vehiculo
import `typealias`.ListaVehiculos
import `typealias`.MapaMarcaVehiculos

class itvRepositoryFunctions: itvRepository<Vehiculo> {

    var misVehiculos: Sequence<Vehiculo> = sequenceOf(
        Coche("Peugeot", 2007, true, 120000, 4),
        Moto("Seat", 2013, true, 90000, 1750),
        Coche("Seat", 2009, false, 160000, 2),
        Moto("BMW", 2019, true, 30000, 1860),
        Moto("Peugeot", 2011, false, 140000, 1790),
        Coche("Peugeot", 2016, true, 125000, 5),
        Coche("BMW", 2003, false, 210000, 2)
    )

    override fun mostrarTodos(): ListaVehiculos {
        return misVehiculos.toList()
    }

    override fun mostrarCoches(): ListaVehiculos {
        return misVehiculos.filter { it is Coche }.toList()
    }

    override fun mostrarMotos(): ListaVehiculos {
        return misVehiculos.filter { it is Moto }.toList()
    }

    override fun vehiculoMasModerno(): Vehiculo? {
        return misVehiculos.maxBy { it.anyoFabricacion }
    }

    override fun vehiculoMenosKilometraje(): Vehiculo? {
        return misVehiculos.minBy { it.kilometraje }
    }

    override fun mediaKilometrajeMotos(): Double {
        return misVehiculos.filter { it is Moto }
            .map { it.kilometraje }
            .average()
    }

    override fun vehiculoMasAntiguoConMasDeDosPuertas(): Vehiculo {
        return misVehiculos.filter { it is Coche && it.numPuertas > 2 }
            .minBy { it.anyoFabricacion }
    }

    override fun numVehiculosTipo(): Map<String?, Int> {
        return misVehiculos.groupBy { it::class.simpleName }
            .mapValues { it.value.count() }
    }

    override fun numMotos(): Int {
        return misVehiculos.filter { it is Moto }.count()
    }

    override fun numCoches(): Int {
        return  misVehiculos.filter { it is Coche }.count()
    }

    override fun numVehiculosAptos(): Map<String?, Int> {
        return misVehiculos.groupBy { it::class.simpleName }
            .mapValues { it.value.count { it.isApto } }
    }

    override fun numCochesAptos(): Int {
        return misVehiculos.filter { it is Coche && it.isApto }.count()
    }

    override fun numMotosAptas(): Int {
        return misVehiculos.filter { it is Moto && it.isApto }.count()
    }

    override fun mediaAnyosFabricacionVehiculos(): Map<String?, Double> {
        return misVehiculos.groupBy { it::class.simpleName }
            .mapValues { it.value.map { it.anyoFabricacion } }
            .mapValues { it.value.average() }
    }

    override fun mediaAnyosFabricacionCoches(): Double {
        return misVehiculos.filter { it is Coche }
            .sumOf { it.anyoFabricacion }.toDouble()
            .div(numCoches())
    }

    override fun mediaAnyosFabricacionMotos(): Double {
        return misVehiculos.filter { it is Moto }
            .sumOf { it.anyoFabricacion }.toDouble()
            .div(numMotos())
    }

    override fun vehiculosPorMarca(): MapaMarcaVehiculos {
        return misVehiculos.groupBy { it.marca }
    }

    override fun vehiculosOrdenadosAnyo(): ListaVehiculos {
        return misVehiculos.sortedBy { it.anyoFabricacion }.toList()
    }

    override fun ordenarMarcasDescendente(): List<String> {
        return vehiculosPorMarca().keys
            .sortedDescending()
    }

    override fun vehiculosPorMarcaOrdenadoKmDescendente(): MapaMarcaVehiculos {
        return vehiculosPorMarca().mapValues { it.value.sortedByDescending { it.anyoFabricacion } }
    }

}