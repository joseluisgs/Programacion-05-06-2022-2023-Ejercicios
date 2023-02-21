package models


class Coche(
    marca: String,
    año: Int,
    apto: Boolean,
    kilometros: Int,
    val numPuertas: Int
) : Vehiculo(marca, año, apto, kilometros)