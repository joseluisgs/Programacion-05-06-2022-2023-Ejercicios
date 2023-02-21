package repositories

import models.Coche
import models.Moto
import models.Vehiculo

class ItvRepositoryImpl(repository: MutableList<Vehiculo>? = null) : ItvRepository {
    private val repository = repository ?: mutableListOf<Vehiculo>()

    override fun getAll(): List<Vehiculo> {
        return repository.toList()
    }

    override fun getCoches(): List<Coche> {
        return repository
            .filter { it is Coche }
            .toList() as List<Coche>
    }

    override fun getVehiculoMasModerno(): Vehiculo {
        return repository.maxBy { it.año }
    }

    override fun getVehiculoMenosKilometros(): Vehiculo {
        return repository.minBy { it.kilometros }
    }

    override fun getMediaKilometrosMotos(): Double {
        return repository
            .filter { it is Moto }
            .map { it.kilometros }
            .average()
    }

    override fun getCocheMasAntiguoMasDosPuertas(): Coche {
        return repository
            .filter { it is Coche && it.numPuertas > 2 }
            .minBy { it.año } as Coche
    }

    override fun getNumVehiculosPorTipo(): Map<String, Int> {
        return repository
            .groupBy { it.javaClass.simpleName }
            .mapValues { it.value.size }
    }

    override fun getAptosPorTipo(): Map<String, Int> {
        return repository
            .filter { it.apto }
            .groupBy { it.javaClass.simpleName }
            .mapValues { it.value.size }
    }

    override fun getMediaAñoPorTipo(): Map<String, Int> {
        return repository
            .groupBy { it.javaClass.simpleName }
            .mapValues { it ->
                it.value
                    .map { it.año }
                    .average()
                    .toInt()
            }
    }

    override fun getVehiculosPorMarcas(): Map<String, List<Vehiculo>> {
        return repository.groupBy { it.marca }
    }

    override fun getVehiculosOrdenAño(): List<Vehiculo> {
        return repository.sortedBy { it.año }
    }

    override fun getMarcasDescendente(): List<String> {
        return repository
            .map { it.marca }
            .distinct()
            .sortedDescending()
    }

    override fun getVehiculosPorMarcasOrdenKmDescendente(): Map<String, List<Vehiculo>> {
        return repository
            .groupBy { it.marca }
            .mapValues { it ->
                it.value
                    .sortedByDescending { it.kilometros }
            }
    }
}