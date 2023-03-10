package controller

import exceptions.NoExistenVehiculosException
import models.Vehiculo
import repositories.itvRepository
import `typealias`.ListaVehiculos
import `typealias`.MapaMarcaVehiculos

class itvController(private val repository: itvRepository<Vehiculo>) {

    fun mostrarTodos(): ListaVehiculos {
        return repository.mostrarTodos()
    }

    fun mostrarCoches(): ListaVehiculos {
        return repository.mostrarCoches()
    }

    fun mostrarMotos(): ListaVehiculos {
        return repository.mostrarMotos()
    }

    fun vehiculoMasModerno(): Vehiculo? {
        return repository.vehiculoMasModerno()
            ?: throw NoExistenVehiculosException()
    }

    fun vehiculoMenosKilometraje(): Vehiculo? {
        return repository.vehiculoMenosKilometraje()
            ?: throw NoExistenVehiculosException()
    }

    fun mediaKilometrajeMotos(): Double {
        return repository.mediaKilometrajeMotos()
    }

    fun vehiculoMasAntiguoConMasDeDosPuertas(): Vehiculo {
        return repository.vehiculoMasAntiguoConMasDeDosPuertas()
    }

    fun numVehiculosTipo(): Map<String?, Int> {
        return repository.numVehiculosTipo()
    }

    fun numMotos(): Int {
        return repository.numMotos()
    }

    fun numCoches(): Int {
        return repository.numCoches()
    }

    fun numVehiculosAptos(): Map<String?, Int> {
        return repository.numVehiculosAptos()
    }

    fun numCochesAptos(): Int {
        return repository.numCochesAptos()
    }

    fun numMotosAptas(): Int {
        return repository.numMotosAptas()
    }

    fun mediaAnyosFabricacionVehiculos(): Map<String?, Double> {
        return repository.mediaAnyosFabricacionVehiculos()
    }

    fun mediaAnyosFabricacionCoches(): Double {
        return repository.mediaAnyosFabricacionCoches()
    }

    fun mediaAnyosFabricacionMotos(): Double {
        return repository.mediaAnyosFabricacionMotos()
    }

    fun vehiculosPorMarca(): MapaMarcaVehiculos {
        return repository.vehiculosPorMarca()
    }

    fun vehiculosOrdenadosAnyo(): ListaVehiculos {
        return repository.vehiculosOrdenadosAnyo()
    }

    fun ordenarMarcasDescendente(): List<String> {
        return repository.ordenarMarcasDescendente()
    }

    fun vehiculosPorMarcaOrdenadoKmDescendente(): MapaMarcaVehiculos {
        return repository.vehiculosPorMarcaOrdenadoKmDescendente()
    }

}