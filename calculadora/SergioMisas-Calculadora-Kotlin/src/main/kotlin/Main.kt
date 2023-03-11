import models.Calculadora
import kotlin.system.exitProcess

fun main() {
    do {
        val parametros = seleccionParametros()
        val resultadoOperacion = ejecutarOperacion(parametros)
        informeResultado(parametros, resultadoOperacion)
    } while (true)
}


fun seleccionParametros(): ArrayList<Double> {
    var a = 0.0
    var b = 0.0
    var op = 0
    do {
        println("Selecciona el primer número")
        a = readln().toDoubleOrNull() ?: 0.0
        if (a == 0.0) println("Entrada no correcta o seleccionada 0.0")
        println("Selecciona el segundo número")
        b = readln().toDoubleOrNull() ?: 0.0
        if (b == 0.0) println("Entrada no correcta o seleccionada 0.0")
        println("Selecciona la operación a realizar")
        println("[1] Suma \n[2] Resta \n[3] Multiplicación \n[4] División \n[0] Salir del programa")
        op = readln().toIntOrNull() ?: -1
        if (op !in (0..4)) op = -1
        if (op == -1) println("Operación no válida, vuelve a seleccionar los parámetros")
        if (op == 0) exitProcess(0)
    } while ((a == 0.0 && b == 0.0) || op == -1)
    return ArrayList(arrayListOf(a, b, op.toDouble()))
}

fun ejecutarOperacion(parametros: ArrayList<Double>): Double {
    val calculadora = Calculadora
    when (parametros.last().toInt()) {
        1 -> return calculadora.suma(parametros.first(), parametros[1])
        2 -> return calculadora.resta(parametros.first(), parametros[1])
        3 -> return calculadora.multiplicacion(parametros.first(), parametros[1])
        4 -> return calculadora.division(parametros.first(), parametros[1])
    }
    exitProcess(0)
}

fun informeResultado(parametros: ArrayList<Double>, resultado: Double) {
    val operador: String =
        when (parametros.last().toInt()) {
            1 -> "+"
            2 -> "-"
            3 -> "*"
            else -> {
                "/"
            }
        }

    println("${parametros.first()} $operador ${parametros[1]} = $resultado \n")
}