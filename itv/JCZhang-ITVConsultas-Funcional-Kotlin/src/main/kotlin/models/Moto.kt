package models

class Moto(marca: String, fabricationYear: Int, apto: Boolean, numKm: Int, val cilindrada: Int): Vehicles(marca, fabricationYear, apto,
    numKm
) {
    override fun toString(): String {
        return "Moto(marca='$marca', fabricationYear='$fabricationYear', apto=$apto, numKm=$numKm, cilindrada = $cilindrada)"
    }

}