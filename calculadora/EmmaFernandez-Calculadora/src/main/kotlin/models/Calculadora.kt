package models

import exceptions.DividirPorCeroException
import exceptions.FormatoException
import exceptions.OperacionException
import exceptions.OperandoException

object Calculadora {
    fun procesarOperacion(input: String): Double {
        val splitInput = input.split(" ")
        comprobarInput(splitInput)
        val op1 = splitInput[0].toDouble()
        val op2 = splitInput[2].toDouble()
        val operation = when (splitInput[1]) {
            "+" -> { x: Double, y: Double -> x + y }
            "-" -> { x: Double, y: Double -> x - y }
            "*" -> { x: Double, y: Double -> x * y }
            "/" -> { x: Double, y: Double -> x / y }
            else -> { x: Double, y: Double -> x % y }
        }
        return operar(op1, op2, operation)
    }

    private fun operar(op1: Double, op2: Double, operation: (Double, Double) -> Double) = operation(op1, op2)

    private fun comprobarInput(splitInput: List<String>) {
        val operaciones = setOf("+", "-", "*", "/", "%")
        if (splitInput.size != 3)
            throw FormatoException("Usar formato: n <op> m (ej: 1 + 2)")
        if (splitInput[1] !in operaciones)
            throw OperacionException("Operación inválida: ${splitInput[1]}, usar: + - * / %")
        if (splitInput[0].toDoubleOrNull() == null)
            throw OperandoException("Operando inválido: ${splitInput[0]}, usar número entero o decimal")
        if (splitInput[2].toDoubleOrNull() == null)
            throw OperandoException("Operando inválido: ${splitInput[2]}, usar número entero o decimal")
        if (splitInput[1] == "/" && splitInput[2] == "0")
            throw DividirPorCeroException("Operando inválido: ${splitInput[2]}, no se puede dividir por 0")
    }
}