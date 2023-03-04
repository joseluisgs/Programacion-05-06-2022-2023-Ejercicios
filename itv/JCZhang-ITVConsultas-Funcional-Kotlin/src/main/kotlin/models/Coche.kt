package models

class Coche(marca: String, fabricationYear: Int, apto: Boolean, numKm: Double, val numPuertas:Int): Vehicles(marca, fabricationYear, apto,
    numKm
) {
    override fun toString(): String {
        return "Coche(marca='$marca', fabricationYear='$fabricationYear', apto=$apto, numKm=$numKm, numPuertas = $numPuertas)"
    }
}