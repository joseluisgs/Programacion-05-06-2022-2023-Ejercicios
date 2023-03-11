package SlayTheSpire.models.Area

import SlayTheSpire.models.Characters.Enemy
import SlayTheSpire.models.Characters.PrincipalChar

class Map(
    private val sizeRow: Int,
) {
    private val sizeColumn: Int = 4
    var matrix: Array<Array<Any>> = Array(sizeRow) { Array(sizeColumn) { Any() } }

    init {
        generateMap(matrix)
    }

    private fun generateMap(matrix: Array<Array<Any>>) {
        for (i in 0 until matrix.size) {
            do {
                // Para repetir siempre si hay más de un fuego o una tienda
                var countFire = 0
                var countStore = 0
                for (j in 0 until matrix[i].size) {
                    if (i == 0) {
                        // Generamos batallas distintas en la primera fila
                        matrix[0][j] = Battle()
                    } else if (i == matrix.size - 1) {
                        // Generamos el mismo Boss en la última fila
                        matrix[i][j] = Enemy.Boss(0, 0, 0, 0, "para tener el jefe indicado", 0)
                    } else {
                        val probBox: Int = (0..2).random()
                        if (probBox == 0) {
                            matrix[i][j] = FireUpgrade()
                            countFire++
                        }
                        if (probBox == 1) {
                            matrix[i][j] = Store(null, null)
                            countStore++
                        }
                        if (probBox == 2) {
                            matrix[i][j] = Battle()
                        }
                    }
                }
            } while (countFire >= 2 || countStore >= 2)
        }
    }

    fun print() {
        for (i in matrix.indices) {
            for (j in 0 until matrix[i].size) {
                if (j == matrix[i].size - 1) {
                    // Salto de linea
                    if (matrix[i][j] is Battle) {
                        println("✊")
                    } else if (matrix[i][j] is FireUpgrade) {
                        println("\uD83D\uDD25")
                    } else if (matrix[i][j] is Store) {
                        println("\uD83E\uDE99")
                    } else if (matrix[i][j] is PrincipalChar) {
                        println("\uD83E\uDD13")
                    } else if ((matrix[i][j] is Enemy.Boss)) {
                        println("\uD83D\uDC80")
                    } else {
                        println("✅")
                    }
                } else {
                    if (matrix[i][j] is Battle) {
                        print("✊" + " ")
                    } else if (matrix[i][j] is FireUpgrade) {
                        print("\uD83D\uDD25" + " ")
                    } else if (matrix[i][j] is Store) {
                        print("\uD83E\uDE99" + " ")
                    } else if (matrix[i][j] is PrincipalChar) {
                        print("\uD83E\uDD13" + " ")
                    } else if ((matrix[i][j] is Enemy.Boss)) {
                        print("\uD83D\uDC80" + " ")
                    } else {
                        print("✅" + " ")
                    }
                }
            }
        }
        println("----------------------------------")
        println("LEYENDA: \n✊ -> (Batalla) \n\uD83E\uDE99 -> (Tienda) \n\uD83D\uDD25 -> (Hoguera) \n\uD83E\uDD13 -> (Ubicación actual) \n\uD83D\uDC80 -> (Boss)")
    }
}