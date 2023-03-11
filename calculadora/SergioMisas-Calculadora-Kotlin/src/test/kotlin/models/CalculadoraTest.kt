package models

import org.junit.jupiter.api.Assertions.assertAll
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class CalculadoraTest {

    val calculador = Calculadora
    val a = 5.0
    val b = 2.0
    val suma = { a: Double, b: Double -> a + b }
    val resta = { a: Double, b: Double -> a - b }
    val multiplicacion = { a: Double, b: Double -> a * b }
    val division = { a: Double, b: Double -> a / b }

    @Test
    fun operar() {
        assertAll(
            { assertEquals(7.0, calculador.operar(a, b, suma)) },
            { assertEquals(3.0, calculador.operar(a, b, resta)) },
            { assertEquals(10.0, calculador.operar(a, b, multiplicacion)) },
            { assertEquals(2.5, calculador.operar(a, b, division)) }
        )
    }
}