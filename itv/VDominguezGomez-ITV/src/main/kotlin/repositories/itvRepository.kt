package repositories


interface itvRepository<T> {

    fun mostrarTodos(): List<T>
    fun mostrarCoches(): List<T>
    fun mostrarMotos(): List<T>
    fun vehiculoMasModerno(): T?
    fun vehiculoMenosKilometraje(): T?
    fun mediaKilometrajeMotos(): Double
    fun vehiculoMasAntiguoConMasDeDosPuertas(): T
    fun numVehiculosTipo():Map<String?, Int>
    fun numMotos(): Int
    fun numCoches(): Int
    fun numVehiculosAptos(): Map<String?, Int>
    fun numCochesAptos(): Int
    fun numMotosAptas(): Int
    fun mediaAnyosFabricacionVehiculos(): Map<String?, Double>
    fun mediaAnyosFabricacionCoches(): Double
    fun mediaAnyosFabricacionMotos(): Double
    fun vehiculosPorMarca(): Map<String, List<T>>
    fun vehiculosOrdenadosAnyo(): List<T>
    fun ordenarMarcasDescendente(): List<String>
    fun vehiculosPorMarcaOrdenadoKmDescendente(): Map<String, List<T>>

}