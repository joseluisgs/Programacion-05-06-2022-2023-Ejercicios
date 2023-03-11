import models.Calculadora
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class MainKtTest {

    val a = 5.0
    val b = 2.0
    val op = 1





    @Test
    fun ejecutarOperacion() {
        assertAll(
            { assertEquals(ejecutarOperacion(ArrayList(arrayListOf(a, b, op.toDouble()))), 7.0) },
            { assertEquals(ejecutarOperacion(ArrayList(arrayListOf(a, b, 2.0))), 3.0) },
            { assertEquals(ejecutarOperacion(ArrayList(arrayListOf(a, b, 3.0))), 10.0) },
            { assertEquals(ejecutarOperacion(ArrayList(arrayListOf(a, b, 4.0))), 2.5) }
        )
    }
}