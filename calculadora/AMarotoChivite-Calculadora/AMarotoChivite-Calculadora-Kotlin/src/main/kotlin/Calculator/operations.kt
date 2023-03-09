package Calculator

/**
 * Iniciamos el almacén donde tendremos las operaciones que deseemos
 * @return almacén con las operaciones de decimales
 */
fun initStorageOperationsDouble(): Array<(Double, Double) -> Double> {
    //Operaciones (funciones nombradas)
    val sum: (Double, Double) -> Double = { a: Double, b: Double -> a + b }
    val substract: (Double, Double) -> Double = { a: Double, b: Double -> a - b }
    val mult: (Double, Double) -> Double = { a: Double, b: Double -> a * b }
    val div: (Double, Double) -> Double = { a: Double, b: Double -> a / b }

    // Si queremos más operaciones en nuestra calculadora simplemente
    // agregamos más...

    return arrayOf(sum, substract, div, mult)
}

/**
 * Iniciamos el almacén donde tendremos las operaciones que deseemos
 * @return almacén con las operaciones de enteros
 */
fun initStorageOperationsInt(): Array<(Int, Int) -> Int> {
    //Operaciones (funciones nombradas)
    val sum: (Int, Int) -> Int = { a: Int, b: Int -> a + b }
    val substract: (Int, Int) -> Int = { a: Int, b: Int -> a - b }
    val mult: (Int, Int) -> Int = { a: Int, b: Int -> a * b }
    val div: (Int, Int) -> Int = { a: Int, b: Int -> a / b }

    // Si queremos más operaciones en nuestra calculadora simplemente
    // agregamos más...

    return arrayOf(sum, substract, div, mult)
}