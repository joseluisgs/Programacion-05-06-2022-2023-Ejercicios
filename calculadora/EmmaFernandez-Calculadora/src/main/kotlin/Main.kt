import models.Calculadora

fun main() {
    val calc = Calculadora
    var input = ""
    do {
        println("CALCULADORA FUNCIONAL")
        println("=====================\n")
        println("Introduce una operación (ej: 1 + 2) o 'exit' para salir")
        print("> ")
        input = readln()
        if (input != "exit") {
            val resultado = try {
                calc.procesarOperacion(input)
            } catch (e: Exception) {
                println(e.message)
                null
            }
            println(if (resultado != null) "$input = $resultado\n" else "Error en la operación\n")
        }
    } while (input != "exit")
}