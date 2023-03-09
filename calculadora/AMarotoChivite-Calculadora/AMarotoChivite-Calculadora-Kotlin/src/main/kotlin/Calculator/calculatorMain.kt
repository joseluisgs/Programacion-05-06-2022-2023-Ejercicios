package Calculator

import kotlin.math.roundToInt

fun main() {
    // Instancio mi almacén de mis operaciones
    val storageOperationsInt = initStorageOperationsInt()
    val storageOperationsDouble = initStorageOperationsDouble()

    simpleCalculator(storageOperationsInt, storageOperationsDouble)
}


/**
 * Calculadora con solo 4 operaciones (+,-,*,/)
 * para practicar la programación funcional, solo se pueden realizar operaciones
 * siendo los dos valores del mismo tipo
 */
fun simpleCalculator(
    storageOperationsInt: Array<(Int, Int) -> Int>,
    storageOperationsDouble: Array<(Double, Double) -> Double>
) {

    // Elegimos dos números para operar y el tipo de NUMBER que utilizaremos
    val arrayThreeNum = readNumbers()
    val typeOperation = arrayThreeNum[2].toInt()

    // Elegimos la operación deseada
    if (typeOperation == 1) {
        val operationInt = selectOperationInt(storageOperationsInt)
        // Aplicamos la operación con los números elegidos al principio
        val result = operationInt(arrayThreeNum[0] as Int, arrayThreeNum[1] as Int)
        println("El resultado es $result")
    } else {
        val operationDouble = selectOperationDouble(storageOperationsDouble)
        // Aplicamos la operación con los números elegidos al principio
        val result =
            (operationDouble(arrayThreeNum[0] as Double, arrayThreeNum[1] as Double) * 100.0).roundToInt() / 100.0
        println("El resultado es $result")
    }

}




