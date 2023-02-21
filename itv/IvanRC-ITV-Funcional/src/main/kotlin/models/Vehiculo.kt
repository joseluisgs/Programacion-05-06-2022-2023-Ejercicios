package models

import java.time.LocalDate

sealed class Vehiculo(
    val modelo: String,
    val kilometros: Int,
    val añoMatriculacion: Int,
    val Apto: Boolean
)
class Coche(
    modelo: String,
    kilometros: Int,
    añoMatriculacion: Int,
    Apto: Boolean,
    val numeroPuertas: Int
): Vehiculo(modelo, kilometros, añoMatriculacion, Apto){
    override fun toString(): String {
        return "Coche(modelo='$modelo', kilometros=$kilometros, añoMatriculacion=$añoMatriculacion, Apto=$Apto, numeroPuertas=$numeroPuertas)"
    }
}
class Moto(
    modelo: String,
    kilometros: Int,
    añoMatriculacion: Int,
    Apto: Boolean,
    val cilidrada: Int
): Vehiculo(modelo, kilometros, añoMatriculacion, Apto){
    override fun toString(): String {
        return "Moto(modelo='$modelo', kilometros=$kilometros, añoMatriculacion=$añoMatriculacion, Apto=$Apto, cilindrada=$cilidrada)"
    }
}