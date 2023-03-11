package SlayTheSpire.filters

import SlayTheSpire.models.Area.Map
import SlayTheSpire.models.Characters.PrincipalChar

fun firstDecisionPlayer(generalMap: Map, player: PrincipalChar): String {
    generalMap.print()
    println("")
    println(player)
    println("")
    println("Elige la casilla a la que quieras desplazarte...")
    println("Mínimo: 1 y Máximo: ${generalMap.matrix[0].size}")
    println("Escribe STATUS, si quieres saber tu estado general...\"")
    return readln()
}

fun checkSelection(decision: String, maxSize: Int): Boolean {
    val regex = Regex("\\d+")
    if (!regex.matches(decision)) {
        println("Debe ser un número!")
        println("")
        return false
    }

    // Filtro para no rebosar el mínimo y máximo
    if (decision.toInt() < 1 || decision.toInt() > maxSize) {
        println("Sólo puede ser mínimo 1 y máximo $maxSize!")
        return false
    }

    // Filtro caso no hay posible interacción
    if (maxSize < 0) {
        println("No hay ningún objeto!")
        return false
    }
    return true
}

/**
 * Filtro del movimiento del personaje en las direcciones Sur, Sureste y Suroeste
 */
fun filterToMove_S_SO_SE(nextBox: String, backPositionPlayerString: String, map: Map): Boolean {

    // Filtro numérico
    val r = Regex("\\d+")
    if (!r.matches(nextBox)) {
        println("Debe ser un número!")
        println("")
        return false
    }
    val r2 = Regex("\\d+")
    if (!r2.matches(backPositionPlayerString)) {
        println("Debe ser un número!")
        println("")
        return false
    }

    // Ya pasado el filtro y sabiendo que es numérico
    val backPositionPlayer = backPositionPlayerString.toInt()

    if (backPositionPlayer == 1) {
        // Filtro movimiento S y SE -> Caso columna = 0
        val regex = Regex("[1-2]")
        if (!regex.matches(nextBox)) {
            println("Sólo puede ser mínimo 1 y máximo 2!")
            println("")
            return false
        }
    } else if (backPositionPlayer == map.matrix[0].size) {
        // Filtro movimiento S y SO -> Caso columna = máxima
        val regex = Regex("[${map.matrix[0].size - 1}-${map.matrix[0].size}]")
        if (!regex.matches(nextBox)) {
            println("Sólo puede ser mínimo ${map.matrix[0].size - 1} y máximo ${map.matrix[0].size}!")
            println("")
            return false
        }
    } else {
        // Filtro en casos intermedios
        val regex = Regex("[${backPositionPlayer - 1}-${backPositionPlayer + 1}]")
        if (!regex.matches(nextBox)) {
            println("Sólo puede ser mínimo ${backPositionPlayer - 1} y máximo ${backPositionPlayer + 1}!")
            println("")
            return false
        }
    }

    return true
}
