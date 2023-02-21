package models

class Moto(
    marca: String,
    año: Int,
    apto: Boolean,
    kilometros: Int,
    val cilindrada: Int
) : Vehiculo(marca, año, apto, kilometros)