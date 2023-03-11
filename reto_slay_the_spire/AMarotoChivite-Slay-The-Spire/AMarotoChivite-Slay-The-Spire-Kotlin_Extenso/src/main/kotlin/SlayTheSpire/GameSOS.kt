package SlayTheSpire

import SlayTheSpire.filters.filterToMove_S_SO_SE
import SlayTheSpire.filters.firstDecisionPlayer
import SlayTheSpire.models.Area.Battle
import SlayTheSpire.models.Area.FireUpgrade
import SlayTheSpire.models.Area.Map
import SlayTheSpire.models.Area.Store
import SlayTheSpire.models.Bag.RepoBag
import SlayTheSpire.models.Characters.Enemy
import SlayTheSpire.models.Characters.PrincipalChar
import SlayTheSpire.models.Deck.RepoDeck

fun GameSlayOfSpire(generalMap: Map, player: PrincipalChar, repoDeck: RepoDeck, repoBag: RepoBag) {

    // Iniciamos el juego hasta que ganemos o muramos
    var countRoad = 0
    var countInitBox = 0

    while (true) {
        println("=== CAMINO RESTANTE HASTA EL JEFE: ${generalMap.matrix.size - countRoad} ===")

        // Aplicamos doble buffer //
        // Clonamos para tener un mapa de lectura antes del movimiento, gracias al mapeo, copiando cada elemento
        val lectureMap: Array<Array<Any>> = generalMap.matrix.map { it.copyOf() }.toTypedArray()

        // Mapa para actualizar, que después del movimiento tendremos que asignar al mapa general
        val updateMap: Array<Array<Any>> = generalMap.matrix.map { it.copyOf() }.toTypedArray()

        while (true) {
            // Seleccionamos decisión (movimiento o STATUS)
            val selectedToMove: String = firstDecisionPlayer(generalMap, player)

            // Encuentro posición del jugador (Si nos encontramos en la primera elección podrá elegir la casilla que quiera
            var backPositionPlayer: String = "0"
            if (countInitBox == 0) {
                backPositionPlayer = selectedToMove
                countInitBox++
            } else {
                // Para el resto del juego que no sea la primera casilla
                for (i in lectureMap.indices) {
                    for (j in 0 until lectureMap[i].size) {
                        if (lectureMap[i][j] is PrincipalChar) {
                            backPositionPlayer = (j + 1).toString()
                        }
                    }
                }
            }

            if (selectedToMove == "STATUS") {
                player.printAll()
            } else if (filterToMove_S_SO_SE(selectedToMove, backPositionPlayer, generalMap)) {
                // Si supera el filtro movemos el personaje y actualizamos el mapa con el movimiento

                // Mantengo la ruta que ha ido eligiendo el personaje
                for (i in 0 until generalMap.matrix.size) {
                    for (j in 0 until generalMap.matrix[i].size) {
                        if (i <= countRoad && generalMap.matrix[i][j] is PrincipalChar) {
                            updateMap[i][j] = Any()
                        }
                    }
                }

                //Actualizamos solo el mapa update, para poder posteriormente comparar con el de lectura
                updateMap[countRoad][selectedToMove.toInt() - 1] = player
                break
            }
        }

        // Para iniciar la acción comparando la posición del jugador con el mapa original, gracias al doble buffer
        for (i in updateMap.indices) {
            for (j in 0 until updateMap[i].size) {
                if (updateMap[i][j] is PrincipalChar && lectureMap[i][j] is FireUpgrade) {
                    FireUpgrade.initFireUpgrade(player)
                    break
                }
                if (updateMap[i][j] is PrincipalChar && lectureMap[i][j] is Battle) {
                    Battle.instanceBattle(player, 1)
                    break
                }
                if (updateMap[i][j] is PrincipalChar && lectureMap[i][j] is Store) {
                    Store.initStore(player)
                    break
                }
                if (updateMap[i][j] is PrincipalChar && lectureMap[i][j] is Enemy.Boss) {
                    Battle.instanceBattle(player, 2)
                    break
                }
            }
        }

        // Al terminar todas las acciones, actualizamos el mapa general
        generalMap.matrix = updateMap
        // Pasamos a la siguiente fila/piso del mapa
        countRoad++
    }
}





