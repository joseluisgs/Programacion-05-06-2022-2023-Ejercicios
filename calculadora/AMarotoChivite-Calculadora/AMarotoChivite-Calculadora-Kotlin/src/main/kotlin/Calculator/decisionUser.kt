package Calculator

/**
 * Seleccionamos la operación que desee el usuario, una vez se seleccione
 * podremos utilizar la variable donde se ha almacenado nuestra operación
 * y ya actuar como una función estándar con sus parámetros
 * @param storageOperationsInt para poder elegir todas las operaciones que hemos registrado
 * @return la función que ha elegido el usuario que se va a almacenar en la variable
 * @throws en caso de que la entrada del usuario no corresponda al filtro
 */
fun selectOperationDouble(
    storageOperationsDouble: Array<(Double, Double) -> Double>
): (Double, Double) -> Double {
    println("Ingrese la operación que desea realizar (+, -, *, /): ")
    return when (readlnOrNull()) {
        "+" -> storageOperationsDouble[0]
        "-" -> storageOperationsDouble[1]
        "*" -> storageOperationsDouble[2]
        "/" -> storageOperationsDouble[3]
        else -> throw IllegalArgumentException("Operación inválida")
    }
}

/**
 * Seleccionamos la operación que desee el usuario, una vez se seleccione
 * podremos utilizar la variable donde se ha almacenado nuestra operación
 * y ya actuar como una función estándar con sus parámetros
 * @param storageOperationsInt para poder elegir todas las operaciones que hemos registrado
 * @return la función que ha elegido el usuario que se va a almacenar en la variable
 * @throws en caso de que la entrada del usuario no corresponda al filtro
 */
fun selectOperationInt(
    storageOperationsInt: Array<(Int, Int) -> Int>
): (Int, Int) -> Int {
    println("Ingrese la operación que desea realizar (+, -, *, /): ")
    return when (readlnOrNull()) {
        "+" -> storageOperationsInt[0]
        "-" -> storageOperationsInt[1]
        "*" -> storageOperationsInt[2]
        "/" -> storageOperationsInt[3]
        else -> throw IllegalArgumentException("Operación inválida")
    }
}

/**
 * Elección de dos valores para poder operar posteriormente cuando elijamos una operación
 * @return almacén de los dos valores
 */
fun readNumbers(): Array<Number> {
    when (getNumberTypeFilter()) {
        1 -> {
            println("Ingrese el primer número: ")
            val a = readlnOrNull()?.toInt() ?: throw IllegalArgumentException("Valor inválido!")
            println("Ingrese el segundo número: ")
            val b = readlnOrNull()?.toInt() ?: throw IllegalArgumentException("Valor inválido!")
            return arrayOf(a, b, 1)
        }

        else -> {
            println("Ingrese el primer número: ")
            val a = readlnOrNull()?.toDouble() ?: throw IllegalArgumentException("Valor inválido!")
            println("Ingrese el segundo número: ")
            val b = readlnOrNull()?.toDouble() ?: throw IllegalArgumentException("Valor inválido!")
            return arrayOf(a, b, 2)
        }
    }
}

/**
 * Para filtrar el tipo de dato que querremos operar
 * @return 1 si es entero, 2 si es decimal
 */
fun getNumberTypeFilter(): Int {
    println("Seleccione el tipo de número (1-Entero, 2-Decimal): ")
    return when (readlnOrNull()?.toInt() ?: 0) {
        1 -> 1
        2 -> 2
        else -> throw IllegalArgumentException("Selección inválida!")
    }
}