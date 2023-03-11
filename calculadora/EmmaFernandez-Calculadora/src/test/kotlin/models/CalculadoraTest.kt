package models

import exceptions.DividirPorCeroException
import exceptions.FormatoException
import exceptions.OperacionException
import exceptions.OperandoException
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.assertThrows

class CalculadoraTest {

    private val calc = Calculadora
    @Test
    fun procesarOperacion() {
        assertEquals(3.0, calc.procesarOperacion("1 + 2"))
        assertEquals(1.0, calc.procesarOperacion("2 - 1"))
        assertEquals(2.0, calc.procesarOperacion("1 * 2"))
        assertEquals(2.0, calc.procesarOperacion("4 / 2"))
        assertEquals(1.0, calc.procesarOperacion("5 % 2"))
    }

    @Test
    fun operacionInvalida() {
        val res = assertThrows<OperacionException> { calc.procesarOperacion("1 a 2") }
        assertEquals("Operación inválida: a, usar: + - * / %", res.message)
    }

    @Test
    fun operandoInvalido() {
        var res = assertThrows<OperandoException> { calc.procesarOperacion("a + 2") }
        assertEquals("Operando inválido: a, usar número entero o decimal", res.message)

        res = assertThrows<OperandoException> { calc.procesarOperacion("1 + b") }
        assertEquals("Operando inválido: b, usar número entero o decimal", res.message)
    }

    @Test
    fun formatoInvalido() {
        val res = assertThrows<FormatoException> { calc.procesarOperacion("1 + 2 + 3") }
        assertEquals("Usar formato: n <op> m (ej: 1 + 2)", res.message)
    }

    @Test
    fun dividirPorCero() {
        val res = assertThrows<DividirPorCeroException> { calc.procesarOperacion("1 / 0") }
        assertEquals("Operando inválido: 0, no se puede dividir por 0", res.message)
    }
}