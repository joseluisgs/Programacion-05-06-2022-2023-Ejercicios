package FunctionalITV.models

sealed class Vehicle(
    val brand: String,
    val anyo: Int,
    val apto: Boolean,
    val km: Int
)

class Moto(
    brand: String,
    anyo: Int,
    apto: Boolean,
    km: Int,
    val cilindra: Int
) : Vehicle(brand, anyo, apto, km) {
    override fun toString(): String {
        return "Moto(brand='$brand', anyo=$anyo, apto=$apto, km=$km, cilindra=$cilindra)"
    }
}

class Car(
    brand: String,
    anyo: Int,
    apto: Boolean,
    km: Int,
    val numDoors: Int
) : Vehicle(brand, anyo, apto, km) {
    override fun toString(): String {
        return "Car(brand='$brand', anyo=$anyo, apto=$apto, km=$km, numDoors=$numDoors)"
    }
}