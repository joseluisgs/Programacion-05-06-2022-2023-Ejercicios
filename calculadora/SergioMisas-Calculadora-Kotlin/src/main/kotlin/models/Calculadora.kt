package models

object Calculadora {
    fun operar(a: Double, b: Double, op: (Double, Double) -> Double) = op(a, b)

    val suma = { a: Double, b: Double -> a + b }
    val resta = { a: Double, b: Double -> a - b }
    val multiplicacion = { a: Double, b: Double -> a * b }
    val division = { a: Double, b: Double -> a / b }
}

