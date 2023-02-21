package models


abstract class Vehicles(val marca: String, val fabricationYear: Int, val apto: Boolean, val numKm: Int) {

    override fun toString(): String {
        return "Vehicles(marca='$marca', fabricationYear='$fabricationYear', apto=$apto, numKm=$numKm)"
    }
}