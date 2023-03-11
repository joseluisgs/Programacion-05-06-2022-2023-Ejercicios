package SlayTheSpire.models.Area

import SlayTheSpire.models.Characters.PrincipalChar
import SlayTheSpire.models.Deck.Card

class FireUpgrade {
    companion object {
        fun initFireUpgrade(player: PrincipalChar) {
            while (true) {
                println("")
                println("\uD83D\uDD25 Elige que hacer en la hoguera...")
                println("MEJORAR -> Mejora una carta que elijas")
                println("SANAR -> Consigues 30 de vida")
                val decision: String = readln()

                if (checkDecision(decision)) {
                    if (decision == "SANAR") {
                        player.life = player.life + 30
                    }

                    if (decision == "MEJORAR") {
                        while (true) {
                            println("=== TU MAZO ===")
                            val myDeck = player.getDeck()
                            for (i in myDeck.indices) {
                                println("${i + 1}: ${myDeck[i]}")
                            }
                            println()

                            println("Elige una carta para mejorar...")
                            val selectedPositionCard: String = readln()
                            println()

                            println("Elige el tipo de mejora...")
                            println("MANA -> Menos coste de maná (-1)")
                            println("STAT -> Mejora de Stat (+3 al daño) (+1 al veneno) (+4 a la curación)")
                            val selectedTypeUpgrade: String = readln()
                            println()

                            // Si supera el filtro, mejoramos la carta y la demostramos
                            if (filterSelectedCard(
                                    selectedPositionCard,
                                    myDeck, selectedTypeUpgrade
                                )
                            ) {
                                val selectedCard: Card = myDeck[selectedPositionCard.toInt() - 1]
                                if (player.upgradeCard(selectedCard, selectedTypeUpgrade)) {
                                    // Cambiamos el nombre
                                    val sb = StringBuilder(selectedCard.name)
                                    sb.append("+")
                                    selectedCard.name = sb.toString()

                                    println("Carta mejorada: $selectedCard")
                                    break
                                }

                            }
                        }
                    }
                    // Si se eligió bien con anterioridad salimos del bucle una vez terminemos una de las dos opciones
                    break
                }
            }
        }

        private fun checkDecision(decision: String): Boolean {
            val regex = Regex("(MEJORAR|SANAR)")
            if (!regex.matches(decision)) {
                println("Sólo puede ser: MEJORAR o SANAR")
                println("")
                return false
            }
            return true
        }

        private fun filterSelectedCard(
            selectedPositionCard: String,
            myDeck: List<Card>,
            selectedTypeUpgrade: String
        ): Boolean {
            val maxSize: Int = myDeck.size
            val regex = Regex("\\d+")
            if (!regex.matches(selectedPositionCard)) {
                println("Debe ser un número!")
                println("")
                return false
            }
            if (selectedPositionCard.toInt() < 1) {
                println("Debe ser como Mínimo 1 y Máximo $maxSize")
            }
            if (selectedPositionCard.toInt() > maxSize) {
                println("Debe ser como Mínimo 1 y Máximo $maxSize")
            }
            val regex1 = Regex("(MANA|STAT)")
            if (!regex1.matches(selectedTypeUpgrade)) {
                println("Sólo puede ser: MANA o STAT")
                println("")
                return false
            }
            if (selectedTypeUpgrade == "MANA" && myDeck[0].mana < 0) {
                println("No puede reducir el maná menos de 0!")
                println("")
                return false
            }
            return true
        }
    }
}
