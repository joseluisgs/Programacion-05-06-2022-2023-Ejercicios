package controllers

import exceptions.*
import interfaces.ItvExtension
import models.*

class ItvController(private val repository: ItvExtension): ItvExtension {
    override fun isFull(): Boolean {
        return repository.isFull()
    }

    override fun getCoches(): List<Vehiculo> {
        return repository.getCoches()
    }

    override fun getCocheMaxModerno(): Vehiculo {
        return repository.getCocheMaxModerno()
    }

    override fun getVehiculoMinKilometros(): Vehiculo {
        return repository.getVehiculoMinKilometros()
    }

    override fun findByMarca(marca: String): List<Vehiculo> {
        return repository.findByMarca(marca)
    }

    override fun getMediaKilometrosMotos(): Double {
        return repository.getMediaKilometrosMotos()
    }

    override fun getOldestCoche(): Vehiculo {
        return repository.getOldestCoche()
    }

    override fun getOldestCoche2Puertas(): Vehiculo {
        return repository.getOldestCoche2Puertas()
    }

    override fun getCountVehiculos(): Map<String, Int> {
        return repository.getCountVehiculos()
    }

    override fun getCountVehiculosAptos(): Map<String, Int> {
        return repository.getCountVehiculosAptos()
    }

    override fun getMediaVehiculosAnio(): Map<String, Double> {
        return repository.getMediaVehiculosAnio()
    }

    override fun groupVehiculosByMarca(): Map<String, List<Vehiculo>> {
        return repository.groupVehiculosByMarca()
    }

    override fun sortVehiculosByAnio(): List<Vehiculo> {
        return repository.sortVehiculosByAnio()
    }

    override fun sortDesVehiculosByMarca(): List<Vehiculo> {
        return repository.sortDesVehiculosByMarca()
    }

    override fun groupVehiculosByMarcaSortByKilometro(): Map<String, List<Vehiculo>> {
        return repository.groupVehiculosByMarcaSortByKilometro()
    }

    override fun save(t: Vehiculo): Vehiculo? {
        checkVehicle(t)
        return repository.save(t)
    }

    override fun find(id: String): Vehiculo? {
        checkMatricula(id)
        return repository.find(id)
    }

    override fun delete(id: String): Vehiculo? {
        checkMatricula(id)
        return repository.delete(id)
    }

    override fun getAll(): List<Vehiculo> {
        return repository.getAll()
    }

    override fun saveAll(list: List<Vehiculo>) {
        list.forEach { checkVehicle(it) }
        repository.saveAll(list)
    }

    private fun checkVehicle(vehiculo: Vehiculo) {
        checkMatricula(vehiculo.matricula)
        require(checkNum(vehiculo.kilometro)) { throw ItvKilometroException() }
        require(checkNum(vehiculo.anio.year, 1900, 2023)) { throw ItvAnioException() }

        when(vehiculo){
            is Coche -> require(checkNum(vehiculo.numPuertas, max = 7)) { throw ItvPlazasException() }
            is Moto -> require(checkNum(vehiculo.cilindrada, min = 100)) { throw ItvCilindradaException() }
        }
    }
    private fun checkMatricula(matricula: String){
        require(Regex("^[0-9]{4}[A-Z]{3}$").matches(matricula)) { throw ItvMatriculaException() }
    }
    private fun checkNum(num: Int, min: Int = 0, max: Int = Int.MAX_VALUE): Boolean{
        return num >= min && num <= max
    }
}