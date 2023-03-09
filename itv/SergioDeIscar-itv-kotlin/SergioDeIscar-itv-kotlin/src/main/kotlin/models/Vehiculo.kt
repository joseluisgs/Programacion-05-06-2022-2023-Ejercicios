package models

import java.time.LocalDate

sealed class Vehiculo(val matricula: String, val marca: String, val anio: LocalDate, val apto: Boolean, val kilometro: Int)
class Coche(matricula: String, marca: String, anio: LocalDate, apto: Boolean, kilometro: Int, val numPuertas: Int):
    Vehiculo(matricula, marca, anio, apto, kilometro)
class Moto(matricula: String, marca: String, anio: LocalDate, apto: Boolean, kilometro: Int, val cilindrada: Int):
    Vehiculo(matricula, marca, anio, apto, kilometro)